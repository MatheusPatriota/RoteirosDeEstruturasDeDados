package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The implementation of the algorithm must be in-place!
 */
public class GnomeSort<T extends Comparable<T>> extends AbstractSorting<T> {
	public void sort(T[] array, int leftIndex, int rightIndex) {
		
		int contador = 1;
		
		while (contador < array.length) {
			
			if (array[contador].compareTo(array[contador - 1]) >= 0) {
				contador ++;
			}
			else {
				
				Util.swap(array, contador, contador-1);
				
				if (contador > 1) {
					contador --;
				}
				else {
					contador ++;
				}
			}
		}
	}
}
