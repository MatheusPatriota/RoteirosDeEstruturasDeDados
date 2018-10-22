package adt.hashtable.closed;

import java.util.LinkedList;

import adt.hashtable.hashfunction.HashFunction;
import adt.hashtable.hashfunction.HashFunctionClosedAddress;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionDivisionMethod;
import adt.hashtable.hashfunction.HashFunctionFactory;
import adt.hashtable.hashfunction.HashFunctionMultiplicationMethod;
import util.Util;

public class HashtableClosedAddressImpl<T> extends AbstractHashtableClosedAddress<T> {

   /**
    * A hash table with closed address works with a hash function with closed
    * address. Such a function can follow one of these methods: DIVISION or
    * MULTIPLICATION. In the DIVISION method, it is useful to change the size
    * of the table to an integer that is prime. This can be achieved by
    * producing such a prime number that is bigger and close to the desired
    * size.
    * 
    * For doing that, you have auxiliary methods: Util.isPrime and
    * getPrimeAbove as documented bellow.
    * 
    * The length of the internal table must be the immediate prime number
    * greater than the given size (or the given size, if it is already prime). 
    * For example, if size=10 then the length must
    * be 11. If size=20, the length must be 23. You must implement this idea in
    * the auxiliary method getPrimeAbove(int size) and use it.
    * 
    * @param desiredSize
    * @param method
    */

   private HashFunctionClosedAddressMethod isThisAHack;

   @SuppressWarnings({ "rawtypes", "unchecked" })
   public HashtableClosedAddressImpl(int desiredSize, HashFunctionClosedAddressMethod method) {
      int realSize = desiredSize;
      isThisAHack = method;
      if (method == HashFunctionClosedAddressMethod.DIVISION) {
         realSize = this.getPrimeAbove(desiredSize); // real size must the
         // the immediate prime
         // above
      }
      initiateInternalTable(realSize);
      HashFunction function = HashFunctionFactory.createHashFunction(method, realSize);
      this.hashFunction = function;
   }

   // AUXILIARY
   /**
    * It returns the prime number that is closest (and greater) to the given
    * number.
    * If the given number is prime, it is returned. 
    * You can use the method Util.isPrime to check if a number is
    * prime.
    */
   int getPrimeAbove(int number) {
      if (Util.isPrime(number)) {
         return number;

      } else {
         return getPrimeAbove(number + 1);
      }
   }

   @Override
   public void insert(T element) {
      HashFunctionClosedAddress metodo;
      if (this.isThisAHack == HashFunctionClosedAddressMethod.DIVISION) {
         metodo = new HashFunctionDivisionMethod<T>(capacity());
      } else {
         metodo = new HashFunctionMultiplicationMethod<>(capacity());
      }

      LinkedList<T> node = new LinkedList<T>();
      int index = metodo.hash(element);

      if (table[index] == null) {
         node.add(element);
         table[index] = node;
         this.elements++;
         return;
      }

      if (table[index] instanceof LinkedList) {
         node = (LinkedList<T>) table[metodo.hash(element)];

         T[] array = (T[]) node.toArray();
         for (int i = 0; i < array.length; i++) {
            if (array[i] == null && !array[i].equals(element)) {
               node.add(element);
               break;
            }
         }

         this.elements++;
         COLLISIONS++;

      }

   }

   @Override
   public void remove(T element) {
      HashFunctionClosedAddress metodo;
      if (this.isThisAHack == HashFunctionClosedAddressMethod.DIVISION) {
         metodo = new HashFunctionDivisionMethod<T>(capacity());
      } else {
         metodo = new HashFunctionMultiplicationMethod<>(capacity());
      }

      int index = metodo.hash(element);
      LinkedList<T> node = (LinkedList<T>) table[index];

      if (!(table[index] == null)) {
         if (node.size() == 1) {
            if (node.getFirst().equals(element)) {
               table[index] = null;
               this.elements--;
            }
            return;
         }

         T[] array = (T[]) table[index];
         for (int i = 0; i < array.length; i++) {
            if (array[i].equals(element)) {
               node.remove(i);

               break;
            }
         }

      }

      this.elements--;
   }

   @Override
   public T search(T element) {
      HashFunctionClosedAddress metodo;
      if (this.isThisAHack == HashFunctionClosedAddressMethod.DIVISION) {
         metodo = new HashFunctionDivisionMethod<T>(capacity());
      } else {
         metodo = new HashFunctionMultiplicationMethod<>(capacity());
      }

      int index = metodo.hash(element);
      LinkedList<T> node = (LinkedList<T>) table[index];
      T result = null;
      if (!(table[index] == null)) {

         if (node.size() == 1) {
            if (node.getFirst().equals(element)) {
               result = (T) table[index];

            }

         }

         T[] array = (T[]) table[index];
         for (int i = 0; i < array.length; i++) {
            if (array[i].equals(element)) {
               result = array[i];
               break;
            }
         }
      }
      return result;
   }

   @Override
   public int indexOf(T element) {
      int valorIndex = -1;
      if (this.isThisAHack == HashFunctionClosedAddressMethod.DIVISION) {
         HashFunctionClosedAddress<T> auxiliar = new HashFunctionDivisionMethod<>(capacity());
         if (table[auxiliar.hash(element)] != null) {
            valorIndex = auxiliar.hash(element);
         }

      } else {
         HashFunctionClosedAddress<T> auxiliar = new HashFunctionMultiplicationMethod<>(capacity());
         if (table[auxiliar.hash(element)] != null) {
            valorIndex = auxiliar.hash(element);
         }
      }
      return valorIndex;
   }
}
