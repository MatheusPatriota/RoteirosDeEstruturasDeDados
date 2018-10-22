package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;
import adt.hashtable.hashfunction.HashFunctionOpenAddress;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends
		AbstractHashtableOpenAddress<T> {
	HashFunctionClosedAddressMethod metodo;
	public HashtableOpenAddressLinearProbingImpl(int size,
			HashFunctionClosedAddressMethod method) {
		super(size);
		metodo = method;
		hashFunction = new HashFunctionLinearProbing<T>(size, method);
		this.initiateInternalTable(size);
	}

	@Override
	public void insert(T element) {
		int probe = 0;
		HashFunctionOpenAddress<T> auxiliar = new HashFunctionLinearProbing<>(capacity(), metodo);
		int index = auxiliar.hash(element, probe);
		if (table[index] == null || table[index] == new DELETED()) {
			table[index] = element;
		}else {
			
			for (int i = 0; i < table.length; i++) {
				COLLISIONS++;
				probe ++;
				index = auxiliar.hash(element, probe);
				if (table[index] == null || table[index] == new DELETED()) {
					table[index] = element;
					break;
				}
			}
		}
		elements++;
	}

	@Override
	public void remove(T element) {
		int probe = 0;
		HashFunctionOpenAddress<T> auxiliar = new HashFunctionLinearProbing<>(capacity(), metodo);
		int index = auxiliar.hash(element, probe);
		if (table[index].equals(element)) {
			table[index] = new DELETED();
			elements--;
         }else {
        	 for (int i = 0; i < table.length; i++) {
 				probe ++;
 				index = auxiliar.hash(element, probe); 
 				if (table[index] == null) {
					break;
				}else {
					
					if (table[index].equals(element)) {
	 					table[index] = new DELETED();
	 					elements--;
	 					break;
	 				}
				}
 				
        	 }
         }
	}

	@Override
	public T search(T element) {
		T result = null;
		int probe = 0;
		HashFunctionOpenAddress<T> auxiliar = new HashFunctionLinearProbing<>(capacity(), metodo);
		int index = auxiliar.hash(element, probe);
		if (table[index] != null && table[index].equals(element)) {
			result = (T) table[index];
         }else {
        	 for (int i = 0; i < table.length; i++) {
				probe ++;
				index = auxiliar.hash(element, probe);
				if (table[index] == null) {
					break;
				}else {
					if (table[index].equals(element)) {
						result = (T) table[index];
						break;
					}
				}
			}
         }
		return result;
	}

	@Override
	public int indexOf(T element) {
		int indexValue = -1;
		int probe = 0;
		HashFunctionOpenAddress<T> auxiliar = new HashFunctionLinearProbing<>(capacity(), metodo);
		int index = auxiliar.hash(element, probe);
		if (table[index].equals(element)) {
            indexValue = index;
         }else {
        	 for (int i = 0; i < table.length; i++) {
 				probe ++;
 				index = auxiliar.hash(element, probe); ;
 				if (table[index].equals(element)) {
 					indexValue = index;
 					break;
 				}
        	 }
         }
        	 
		return indexValue;
	}

}
