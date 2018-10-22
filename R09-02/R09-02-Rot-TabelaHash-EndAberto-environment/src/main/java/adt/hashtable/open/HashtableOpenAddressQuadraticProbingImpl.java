package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionOpenAddress;
import adt.hashtable.hashfunction.HashFunctionQuadraticProbing;

public class HashtableOpenAddressQuadraticProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

   private int c1;
   private int c2;
   private HashFunctionClosedAddressMethod metodo;

   public HashtableOpenAddressQuadraticProbingImpl(int size, HashFunctionClosedAddressMethod method, int c1, int c2) {
      super(size);

      this.c1 = c1;
      this.c2 = c2;
      this.metodo = method;
      hashFunction = new HashFunctionQuadraticProbing<T>(size, method, c1, c2);
      this.initiateInternalTable(size);
   }

   @Override
   public void insert(T element) {
      int probe = 0;
      HashFunctionOpenAddress<T> auxiliar = new HashFunctionQuadraticProbing<>(capacity(), metodo, c1, c2);
      int index = auxiliar.hash(element, probe);
      if (table[index] == null || table[index] == new DELETED()) {
         table[index] = element;
      } else {

         for (int i = 0; i < table.length; i++) {
            COLLISIONS++;
            probe++;
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
      HashFunctionOpenAddress<T> auxiliar = new HashFunctionQuadraticProbing<>(capacity(), metodo, c1, c2);
      int index = auxiliar.hash(element, probe);
      if (table[index] != null && table[index].equals(element)) {
         table[index] = new DELETED();
         elements--;
      } else {
         for (int i = 0; i < table.length; i++) {
            probe++;
            index = auxiliar.hash(element, probe);
            if (table[index] == null) {
               break;
            } else {

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
      HashFunctionOpenAddress<T> auxiliar = new HashFunctionQuadraticProbing<>(capacity(), metodo, c1, c2);
      int index = auxiliar.hash(element, probe);
      if (table[index].equals(element)) {
         result = (T) table[index];
      } else {
         for (int i = 0; i < table.length; i++) {
            probe++;
            index = auxiliar.hash(element, probe);
            if (table[index] == null) {
               break;
            } else {
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
      HashFunctionOpenAddress<T> auxiliar = new HashFunctionQuadraticProbing<>(capacity(), metodo, c1, c2);
      int index = auxiliar.hash(element, probe);
      if (table[index].equals(element)) {
         indexValue = index;
      } else {
         for (int i = 0; i < table.length; i++) {
            probe++;
            index = auxiliar.hash(element, probe);
            ;
            if (table[index].equals(element)) {
               indexValue = index;
               break;
            }
         }
      }

      return indexValue;
   }
}
