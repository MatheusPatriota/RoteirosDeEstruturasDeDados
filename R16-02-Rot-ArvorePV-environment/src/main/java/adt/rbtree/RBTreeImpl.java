package adt.rbtree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import adt.bst.BSTImpl;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T>
		implements RBTree<T> {

	public RBTreeImpl() {
		this.root = new RBNode<T>();
	}

	protected int blackHeight() {
		return blackHeight((RBNode<T> )this.root);
	}

	private int blackHeight(RBNode<T> node) {
		if (node.isEmpty()) {
			return 1;
		}
		else {
			if (node.getColour() == Colour.BLACK) {
				return 1 + Math.max(blackHeight((RBNode<T>) node.getLeft()),blackHeight((RBNode<T>) node.getRight()));
			}
			else {
				return Math.max(blackHeight((RBNode<T>) node.getLeft()),blackHeight((RBNode<T>) node.getRight()));
			}
		}
	}

	protected boolean verifyProperties() {
		boolean resp = verifyNodesColour() && verifyNILNodeColour()
				&& verifyRootColour() && verifyChildrenOfRedNodes()
				&& verifyBlackHeight();

		return resp;
	}

	/**
	 * The colour of each node of a RB tree is black or red. This is guaranteed
	 * by the type Colour.
	 */
	private boolean verifyNodesColour() {
		return true; // already implemented
	}

	/**
	 * The colour of the root must be black.
	 */
	private boolean verifyRootColour() {
		return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
																// implemented
	}

	/**
	 * This is guaranteed by the constructor.
	 */
	private boolean verifyNILNodeColour() {
		return true; // already implemented
	}

	/**
	 * Verifies the property for all RED nodes: the children of a red node must
	 * be BLACK.
	 */
	private boolean verifyChildrenOfRedNodes() {
		return verifyChildrenOfRedNodes((RBNode<T>) this.root);
	}

	private boolean verifyChildrenOfRedNodes(RBNode<T> node) {
		if (node.getColour() == Colour.RED) {
			return (((RBNode<T>) node.getLeft()).getColour() == Colour.BLACK && ((RBNode<T>) node.getRight()).getColour() == Colour.BLACK);
		}else {
			if (node.isEmpty()) {
				return true;
			}else {
				return (verifyChildrenOfRedNodes(((RBNode<T>) node.getLeft())) && verifyChildrenOfRedNodes((RBNode<T>) node.getRight()));
			}
		}
		
	}

	/**
	 * Verifies the black-height property from the root.
	 */
	private boolean verifyBlackHeight() {
		return blackHeight((RBNode<T>) this.root.getLeft()) ==  blackHeight((RBNode<T>) this.root.getRight());
	}

	@Override
	public void insert(T value) {
		//semelhante ao insert da BST comum, muda apenas a questao de setar a cor
		if (value == null) {
			return;
		}else {
			insertRecurssive((RBNode<T>)this.root,(RBNode<T>)this.root.getParent(),value);	
		}
		
		
	}

	private void insertRecurssive(RBNode<T> node, RBNode<T> parent, T value) {
		 if (node.isEmpty()) { // Inserindo o element em um node vazio, encontrado apos a recursao.
	            node.setData(value);
	            node.setLeft(new RBNode<T>());
	            node.setRight(new RBNode<T>());
	            node.setParent(parent);
	            node.setColour(Colour.RED);
	            fixUpCase1(node);
		 }
		 else {
			 if (node.getData().compareTo(value) > 0) {
					insertRecurssive((RBNode<T>) node.getLeft(),node,value);
		 	}else if(node.getData().compareTo(value) < 0)
					insertRecurssive((RBNode<T>) node.getRight(),node,value);
			}
		 
		
	}

	@Override
	public RBNode<T>[] rbPreOrder() {
		List<RBNode<T>> aux = new ArrayList<>();
		treeExtendedPreOrder((RBNode<T>) this.root, aux);
		@SuppressWarnings("unchecked")
		RBNode<T>[] array = (RBNode<T>[]) Array.newInstance(RBNode.class, aux.size());
		for (int i = 0; i < aux.size(); i++) {
			array[i] = aux.get(i);
		}
		return (RBNode<T>[]) array;
	}

	private void treeExtendedPreOrder(RBNode<T> node, List<RBNode<T>> array) {
		if (!node.isEmpty()) {
			array.add(node);
			treeExtendedPreOrder((RBNode<T>) node.getLeft(), array);
			treeExtendedPreOrder((RBNode<T>) node.getRight(), array);
		}
	}
	

	// FIXUP methods
	protected void fixUpCase1(RBNode<T> node) {
		if (node.equals(root)) {
			node.setColour(Colour.BLACK);
		} else {
			fixUpCase2(node);
		}
	}

	protected void fixUpCase2(RBNode<T> node) {
		RBNode<T> parent = ((RBNode<T>) node.getParent());
		if (parent.getColour() == Colour.BLACK) {
			return;
		} else {
			fixUpCase3(node);
		}
	}

	protected void fixUpCase3(RBNode<T> node) {
		RBNode<T> uncle = treeUncle(node);
		
		if (uncle.getColour() == Colour.RED) {
			RBNode<T> parent = (RBNode<T>) node.getParent();
			RBNode<T> grandParent = (RBNode<T>) parent.getParent();
			
			parent.setColour(Colour.BLACK);
			uncle.setColour(Colour.BLACK);
			grandParent.setColour(Colour.RED);
			
			fixUpCase1(grandParent);
		} else {
			fixUpCase4(node);
		}
	}
	
	private RBNode<T> treeUncle(RBNode<T> node) {
		RBNode<T> parent = (RBNode<T>) node.getParent();
		RBNode<T> grandParent = (RBNode<T>) parent.getParent();
		
		if (parent.equals(grandParent.getLeft())) {
			return (RBNode<T>) grandParent.getRight();
		} else {
			return (RBNode<T>) grandParent.getLeft();
		}
	}
	
	protected void fixUpCase4(RBNode<T> node) {
		RBNode<T> next = node;
		RBNode<T> parent = (RBNode<T>) node.getParent();
		RBNode<T> grandParent = (RBNode<T>) parent.getParent();
		
		if (grandParent.getLeft().equals(parent) && parent.getRight().equals(node)) {
			Util.leftRotation(parent);
			next = (RBNode<T>) node.getLeft();
		} else if (grandParent.getRight().equals(parent) && parent.getLeft().equals(node)) {
			Util.rightRotation(parent);
			next = (RBNode<T>) node.getRight();
		}
		
		fixUpCase5(next);
	}

	protected void fixUpCase5(RBNode<T> node) {
		RBNode<T> parent = (RBNode<T>) node.getParent();
		RBNode<T> grandParent = (RBNode<T>) parent.getParent();
		
		parent.setColour(Colour.BLACK);
		grandParent.setColour(Colour.RED);
		
		if (parent.getLeft().equals(node)) {
			Util.rightRotation(grandParent);
		} else {
			Util.leftRotation(grandParent);
		}
	}
}
