package adt.skipList;


@SuppressWarnings("unchecked")
public class SkipListImpl<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int maxHeight;

	protected double PROBABILITY = 0.5;

	@SuppressWarnings("rawtypes")
	public SkipListImpl(int maxHeight) {
		this.maxHeight = maxHeight;
		root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		for (int i = 0; i < maxHeight; i++) {
			root.forward[i] = NIL;
		}
	}

	
	@Override
	public void insert(int key, T newValue, int height) {
		
		if (newValue == null || height < 0 || height > maxHeight) {
			throw new RuntimeException();
		}
		
		SkipListNode<T>[] update = makeArray(maxHeight);
		SkipListNode<T> auxRoot = root;
		
		auxRoot = searchAuxiliar(key, update, auxRoot);
		
		if (auxRoot.getKey() == key) {
			auxRoot.setValue(newValue);
		} else {
			int nivel = height;
			
			if (nivel > maxHeight) {
				for (int i = maxHeight; i < nivel; i++) {
					update[i] = root;
				}
				maxHeight = nivel;
			}
			
			SkipListNode<T> newNode = new SkipListNode<T>(key, nivel, newValue);
			
			for (int i = 0; i < nivel; i++) {
				newNode.forward[i] = update[i].getForward(i);
				update[i].forward[i] = newNode;
			}
		}
		
		
	}

	
	
	@Override
	public void remove(int key) {
		
		SkipListNode<T>[] update = makeArray(maxHeight);
		SkipListNode<T> auxRoot = root;
		
		auxRoot = searchAuxiliar(key, update, auxRoot);
		
		if (auxRoot.getKey() == key) {
			for (int i = 0; i < maxHeight; i++) {
				if (update[i].getForward(i) != auxRoot) {
					break;
				}
				update[i].forward[i] = auxRoot.getForward(i);
			}
		} else {
			while (maxHeight > 1 && root.forward[maxHeight - 1].equals(NIL)) {
				maxHeight--;
			}
		}
	}

	@Override
	public int height() {
		return maxHeight;
	}

	@Override
	public SkipListNode<T> search(int key) {
		
		SkipListNode<T> aux = root;
		
		for (int i = maxHeight-1; i >= 0 ; i--) {
			while (aux.getForward(i).getKey() < key) {
				aux = aux.getForward(i);
			}
		}
		
		aux = aux.forward[0];
		
		if (aux.key == key) {
			return aux;
		}
		else {
			return null;
		}
	}

	@Override
	public int size() {
		                                           
		SkipListNode<T> aux = root.forward[0];
		int size = 0;
		
		while (aux.value != null) {
			size++;
			aux = aux.forward[0];
		}
		
		return size;
	}

	@Override
	public SkipListNode<T>[] toArray() {
		
		SkipListNode<T> auxRoot = root;
		SkipListNode<T>[] array = makeArray(this.size() + 2);
		
		int i = 0;
		while (auxRoot != null) {
			array[i++] = auxRoot;
			auxRoot = auxRoot.forward[0];
		}
		
		return array;
	}

	
	// Metodos Auxiliares utilizados
	
	private SkipListNode<T> searchAuxiliar(int key, SkipListNode<T>[] update, SkipListNode<T> auxRoot) {
		
		for (int i = maxHeight - 1; i >= 0; i--) {
			while (auxRoot.getForward(i).getKey() < key) {
				auxRoot = auxRoot.getForward(i);
			}
			
			update[i] = auxRoot;
		}
		
		auxRoot = auxRoot.forward[0];
		return auxRoot;
	}

	private SkipListNode<T>[] makeArray(int size) {
		
		SkipListNode<T>[] array = (SkipListNode<T>[]) new SkipListNode[size];
		
		return array;
	}

}
