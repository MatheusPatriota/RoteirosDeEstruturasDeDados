package adt.heap;

import util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * O comportamento de qualquer heap é definido pelo heapify. Neste caso o
 * heapify dessa heap deve comparar os elementos e colocar o maior sempre no
 * topo. Ou seja, admitindo um comparador normal (responde corretamente 3 > 2),
 * essa heap deixa os elementos maiores no topo. Essa comparação não é feita 
 * diretamente com os elementos armazenados, mas sim usando um comparator. 
 * Dessa forma, dependendo do comparator, a heap pode funcionar como uma max-heap 
 * ou min-heap.
 */
public class HeapImpl<T extends Comparable<T>> implements Heap<T> {

    protected T[] heap;
    protected int index = -1;
    /**
     * O comparador é utilizado para fazer as comparações da heap. O ideal é
     * mudar apenas o comparator e mandar reordenar a heap usando esse
     * comparator. Assim os metodos da heap não precisam saber se vai funcionar
     * como max-heap ou min-heap.
     */
    protected Comparator<T> comparator;

    private static final int INITIAL_SIZE = 20;
    private static final int INCREASING_FACTOR = 10;

    /**
     * Construtor da classe. Note que de inicio a heap funciona como uma
     * min-heap.
     */
    @SuppressWarnings("unchecked")
    public HeapImpl(Comparator<T> comparator) {
        this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
        this.comparator = comparator;
    }

    // /////////////////// METODOS IMPLEMENTADOS
    private int parent(int i) {
        return (i - 1) / 2;
    }

    /**
     * Deve retornar o indice que representa o filho a esquerda do elemento
     * indexado pela posicao i no vetor
     */
    private int left(int i) {
        return (i * 2 + 1);
    }

    /**
     * Deve retornar o indice que representa o filho a direita do elemento
     * indexado pela posicao i no vetor
     */
    private int right(int i) {
        return (i * 2 + 1) + 1;
    }

    @Override
    public boolean isEmpty() {
        return (index == -1);
    }

    @Override
    public T[] toArray() {
        ArrayList<T> resp = new ArrayList<T>();
        for (T elem : this.heap) {
            if (elem != null) {
                resp.add(elem);
            }
        }
        return (T[]) resp.toArray(new Comparable[0]);
    }

    // ///////////// METODOS A IMPLEMENTAR

    /**
     * Valida o invariante de uma heap a partir de determinada posicao, que pode
     * ser a raiz da heap ou de uma sub-heap. O heapify deve colocar os maiores
     * (comparados usando o comparator) elementos na parte de cima da heap.
     */
    private void heapify(int position) {
        heapifyAux(heap, position);
    }

    private void heapifyAux(T[] heap, int position) {
        int left = left(position);
        int right = right(position);
        int largest = position;

        if (left < size() && comparator.compare(heap[left],heap[largest]) > 0) {
            largest = left;
        }
        if (right < size() && comparator.compare(heap[right],heap[largest]) > 0) {
            largest = right;
        }
        if (largest != position) {
            Util.swap(heap, position, largest);
            heapifyAux(heap, largest);
        }
    }

    @Override
    public void insert(T element) {
        // ESSE CODIGO E PARA A HEAP CRESCER SE FOR PRECISO. NAO MODIFIQUE
        if (index == heap.length - 1) {
            heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
        }
        // /////////////////////////////////////////////////////////////////
        insertAux(heap, element);

    }

    private void insertAux(T[] heap, T element) {
        int aux = size();
        while (aux > 0 && comparator.compare(heap[parent(aux)],element) < 0) {
            heap[aux] = heap[parent(aux)];
            aux = parent(aux);
        } 
        heap[aux] = element;

    }

    @Override
    public void buildHeap(T[] array) {
        this.heap = array;

        for (int i = (heap.length / 2) - 1; i >= 0; i--) {
            heapify(i);
        }
    }

    @Override
    public T extractRootElement() {
        if (size() < 1){
            throw new IllegalArgumentException("heap underflow");
        }
        T max = heap[0];
        heap[0] = heap[size()-1];
        heap[size()-1] = null;
        this.index --;
        heapifyAux(heap,0);
        return max;

    }

    @Override
    public T rootElement() {
       return heap[0];
    }

    @Override
    public T[] heapsort(T[] array) {
    	T[] result  = (T[]) new Comparable[array.length];

    	for (int i  = 0; i < array.length; i++){
    	    buildHeap(array);
    	    result[i] = extractRootElement();
        }
        if (result[0].compareTo(result[1])> 0){
            T[] inverse = (T[]) new Comparable[result.length];
            for (int i = 0; i < result.length; i++) {
                inverse[i] = result[result.length - 1 - i];
            }
            result = inverse;
        }
        this.index = -1;
    	return result;
    }

    @Override
    public int size() {
        int size =0;
        for (int i =0 ; i < heap.length; i++) {
            if (heap[i] != null){
                size++;
            }
        }if (size > 0) {
        	this.index = size;
		}
        
        return  size;
    }

    public Comparator<T> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public T[] getHeap() {
        return heap;
    }
}