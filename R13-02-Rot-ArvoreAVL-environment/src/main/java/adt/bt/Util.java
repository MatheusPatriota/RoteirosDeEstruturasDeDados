package adt.bt;

import adt.bst.BSTNode;

public class Util {


	/**
	 * A rotacao a esquerda em node deve subir e retornar seu filho a direita
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {
		BSTNode<T> pivot = (BSTNode<T>) node.getRight();	// OS
		node.setRight(pivot.getLeft());
		pivot.setLeft(node);
		pivot.setRight(new BSTNode<>());
		pivot.setParent(null);
		node = pivot;
		pivot.getLeft().setParent(node);
        pivot.getRight().setParent(node);
		return pivot;
	}

	/**
	 * A rotacao a direita em node deve subir e retornar seu filho a esquerda
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {
		BSTNode<T> pivot = (BSTNode<T>) node.getLeft();	// OS
		node.setLeft(pivot.getRight());
		pivot.setRight(node);
		pivot.setParent(null);
		node = pivot;
		pivot.getLeft().setParent(node);
        pivot.getRight().setParent(node);
		return pivot;
	}

	

	public static <T extends Comparable<T>> T[] makeArrayOfComparable(int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Comparable[size];
		return array;
	}
}
