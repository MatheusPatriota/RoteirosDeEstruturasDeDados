package adt.bst;

import adt.bt.BTNode;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		
		int height = -1;
		BTNode<T> aux1 = root;
		BTNode<T> aux2 = root;
		while(!aux1.isEmpty() || !aux2.isEmpty()) {
			height++;
			aux1.getLeft();
			aux2.getRight();
			
		}
		return height;
	}

	@Override
	public BSTNode<T> search(T element) {
		return searchRecursiveForBST(root, element);
	}
	
	private BSTNode<T> searchRecursiveForBST(BSTNode<T> root2, T element) {
		
		if (root2.isEmpty() || root2.getData().equals(element)) {
			return root2;
		}
		if (root.getData().compareTo(element)> 0) {
			BSTNode aux = (BSTNode) root2.getLeft();
			return searchRecursiveForBST(aux, element);
		}else {
			BSTNode aux = (BSTNode) root2.getRight();
			return searchRecursiveForBST(aux, element);
		}
	}


	@Override
	public void insert(T element) {
		treeInsert(root,element);
	}

	private void treeInsert(BSTNode<T> root2, T element) {

		if (root2.isEmpty()) {
			BSTNode<T> aux = new BSTNode<>();
			root2.setData(element);
			root2.setLeft(aux);
			root2.setRight(aux);	
		}else {
			if (element.compareTo(root2.getData())<0) {
				treeInsert((BSTNode)root2.getLeft(), element);
			}else if(element.compareTo(root2.getData())>0){
				treeInsert((BSTNode)root2.getRight(), element);
				
			}
		}
		
	}

	@Override
	public BSTNode<T> maximum() {
		BSTNode aux = root;
		while (!aux.getRight().isEmpty()) {
			aux = (BSTNode) aux.getRight();
		}
		return aux;
	}

	@Override
	public BSTNode<T> minimum() {
		BSTNode aux = root;
		while (!aux.getLeft().isEmpty()) {
			aux = (BSTNode) aux.getLeft();
		}
		return aux;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public void remove(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] preOrder() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] order() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] postOrder() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
