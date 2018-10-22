package sorting.divideAndConquer;

import sorting.AbstractSorting;
import util.Util;

/**
 * Quicksort is based on the divide-and-conquer paradigm. The algorithm chooses
 * a pivot element and rearranges the elements of the interval in such a way
 * that all elements lesser than the pivot go to the left part of the array and
 * all elements greater than the pivot, go to the right part of the array. Then
 * it recursively sorts the left and the right parts. Notice that if the list
 * has length == 1, it is already sorted.
 */
public class QuickSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (leftIndex >= rightIndex) {
			return;
		}
		
		int m = partition(array, leftIndex, rightIndex);
		sort(array, leftIndex, m-1);
		sort(array, m+1, rightIndex);
		
	}

	private int partition(T[] array, int leftIndex, int rightIndex) {
		
		int pivot = rightIndex;
		int leftCursor = leftIndex-1;
		int rightCursor = rightIndex;
		while (leftCursor < rightCursor) {
				while (array[++leftCursor].compareTo(array[pivot]) < 0);
				while (rightCursor > 0 && array[--rightCursor].compareTo(array[pivot]) > 0);
			if (leftCursor >= rightCursor) {
				break;
			}else {
				Util.swap(array, leftCursor, rightCursor);
			}

		}
		Util.swap(array, leftCursor, rightIndex);
		return leftCursor;
	}
}
	
