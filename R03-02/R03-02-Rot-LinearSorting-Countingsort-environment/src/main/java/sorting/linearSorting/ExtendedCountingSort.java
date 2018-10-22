package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa do Counting Sort vista em sala. Desta vez este
 * algoritmo deve satisfazer os seguitnes requisitos: - Alocar o tamanho minimo
 * possivel para o array de contadores (C) - Ser capaz de ordenar arrays
 * contendo numeros negativos
 */
public class ExtendedCountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
	if (array.length > 0) {
			
			int maximo = array[0];
			int minimo = array[0];
			int offset = 1;
			
			// achar o maximo e minimo
			for (int i = 0; i < array.length; i++) {
				if (array[i] > maximo) {
					maximo = array[i];
				}
				else if (array[i] < minimo) {
					minimo = array[i];
				}
			}
			
			if (minimo <0) {
				offset = Math.abs(minimo)+1;
			}
			
			// arrayAuxiliar
			
			Integer[] arrayAuxiliar = new Integer[maximo + offset];
			
			
			//for para deixar o array com todos os 0
			
			for (int i = 0; i < arrayAuxiliar.length; i++) {
				arrayAuxiliar[i] = 0;
			}
			
			//soma +1 ao achar elemento
			
			for (int i = 0; i < array.length; i++) {
				arrayAuxiliar[array[i]] +=1;
			}
			
			// somatoria de todos os elementos
			
			for (int i = 1; i < arrayAuxiliar.length; i++) {
				arrayAuxiliar[i] += arrayAuxiliar[i-1];
			}
			
			//array final
			 
			Integer[] arrayFinal = new Integer[array.length];
			
			//ordenacao de traz pra frente
			
			for (int i = rightIndex; i >= 0; i--) {
				int aux =arrayAuxiliar[array[i]];
				arrayFinal[aux-1] = array[i];
				arrayAuxiliar[array[i]] -=1;
			}
			
			// inserindo no array original
			
			for (int i = 0; i < array.length; i++) {
				array[i] = arrayFinal[i];
			}
			
		}
			
	}

}
