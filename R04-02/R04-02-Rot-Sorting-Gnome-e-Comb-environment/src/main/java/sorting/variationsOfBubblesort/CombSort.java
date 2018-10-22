package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The combsort algoritm.
 */
public class CombSort<T extends Comparable<T>> extends AbstractSorting<T> {
	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		
		// gap com tamanho do array
		int gap  = array.length;
		// variavel para checar se houve troca
		boolean houveTroca = true;
		
		//while sera executado ate que gap seja maior que 1 ou se houver troca
		while (gap > 1 || houveTroca) {
			
			if (gap>1) {
				gap = (int)(gap/1.25);
			}
			
			int contador =0;
			houveTroca = false;
			// executar um while enquanto gap + contador for menor que o tamanho do array
			while (contador + gap < array.length) {
				if (array[contador].compareTo(array[contador + gap]) > 0) {
					Util.swap(array, contador, contador + gap);
					houveTroca = true;
				}
				contador ++;
			}
			
		}
		
	}
}
