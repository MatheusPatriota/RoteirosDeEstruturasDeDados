package sorting.linearSorting;

import java.util.Arrays;

import sorting.AbstractSorting;

public class Main {

	public static void main(String[] args) {
		
		Integer[] arrayTeste = {  4, 9, 3, 4, 0, 5, 1, 4,-1};
		Integer[] arrayTeste2 = {6, 41, 32, 7, 26, 4, 37, 49,11, 18, 36,0 };
		
		Arrays.sort(arrayTeste2);
		AbstractSorting<Integer> implementation = new ExtendedCountingSort();
		
		implementation.sort(arrayTeste,0,arrayTeste.length-1);
		
		System.out.println(Arrays.toString(arrayTeste));
		System.out.println(arrayTeste2[7]);
	}

}
