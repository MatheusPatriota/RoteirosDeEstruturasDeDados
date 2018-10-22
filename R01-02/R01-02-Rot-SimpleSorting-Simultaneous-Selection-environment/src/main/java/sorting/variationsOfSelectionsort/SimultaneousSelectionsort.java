package sorting.variationsOfSelectionsort;

import sorting.AbstractSorting;
import util.Util;

/**
 * This algorithm applies two selection sorts simultaneously. In a same
 * iteration, a selection sort pushes the greatest elements to the right and
 * another selection sort pushes the smallest elements to the left. At the end
 * of the first iteration, the smallest element is in the first position (index
 * 0) and the greatest element is the last position (index N-1). The next
 * iteration does the same from index 1 to index N-2. And so on. The execution
 * continues until the array is completely ordered.
 */
public class SimultaneousSelectionsort<T extends Comparable<T>> extends
		AbstractSorting<T> {
	public void sort(T[] array, int leftIndex, int rightIndex) {
		
		int fim = rightIndex;
		for (int i = leftIndex; i <= fim; i++) {
			int min = i;
			int max = i;
			for (int j = i; j <= fim; j++) {
				if (array[j].compareTo(array[min]) < 0) {
					min = j;
				}
			}
			Util.swap(array, i, min);
			for (int k = i; k <= fim; k++) {
				if (array[k].compareTo(array[max]) > 0) {
					max = k;
				}
			}
			Util.swap(array, fim, max);
			
			
			fim --;
		}
	}
}
