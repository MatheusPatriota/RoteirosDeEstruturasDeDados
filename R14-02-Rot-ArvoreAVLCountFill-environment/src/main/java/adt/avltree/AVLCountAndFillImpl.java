package adt.avltree;

import adt.bst.BSTNode;

public class AVLCountAndFillImpl<T extends Comparable<T>> extends
		AVLTreeImpl<T> implements AVLCountAndFill<T> {

	private int LLcounter;
	private int LRcounter;
	private int RRcounter;
	private int RLcounter;

	public AVLCountAndFillImpl() {
		
	}

	@Override
	public int LLcount() {
		return LLcounter;
	}

	@Override
	public int LRcount() {
		return LRcounter;
	}

	@Override
	public int RRcount() {
		return RRcounter;
	}

	@Override
	public int RLcount() {
		return RLcounter;
	}
	
	@Override
	public void rebalance(BSTNode<T> node) {
		int balance = calculateBalance(node);
        // criar duas sub arvores
        BSTNode<T> subArvoreDireita = (BSTNode<T>) node.getRight();
        BSTNode<T> subArvoreEsquerda = (BSTNode<T>) node.getLeft();
        if (balance > 1) {
            if (calculateBalance(subArvoreEsquerda) >= 0) {
                rightRotationAux(node);
                LLcounter++;
            } else {
                doubleRightRotation(node);
                LRcounter++;
            }
        } else if (balance < -1) {
            if (calculateBalance(subArvoreDireita) < 0) {
                leftRotationAux(node);
                RRcounter++;
            } else {
                doubleLeftRotation(node);
                RLcounter++;
            }
        }
	}
	@Override
	public void fillWithoutRebalance(T[] array) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

}
