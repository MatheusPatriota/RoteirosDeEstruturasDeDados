package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 *
 * Performs consistency validations within a AVL Tree instance
 *
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {

    // TODO Do not forget: you must override the methods insert and remove
    // conveniently.

    @Override
    public void insert(T element) {
        if (element != null) {
            insertRecurssive(root, element);
        }

    }

    private void insertRecurssive(BSTNode<T> node, T element) {
        if (node.isEmpty()) {
            node.setData(element);
            node.setLeft(new BSTNode<>());
            node.setRight(new BSTNode<>());
            node.getLeft().setParent(node);
            node.getRight().setParent(node);
        } else {
            if (node.getData().compareTo(element) > 0) {
                insertRecurssive((BSTNode<T>) node.getLeft(), element);
            } else if (node.getData().compareTo(element) < 0) {
                insertRecurssive((BSTNode<T>) node.getRight(), element);
            }
            rebalance(node);
        }
    }

    @Override
    public void remove(T element) {
        if (element != null) {
            BSTNode<T> node = search(element);
            if (!node.isEmpty()) {
                removeRecursive(node);
            }
        }
    }

    private void removeRecursive(BSTNode<T> node) {
        BSTNode<T> parent = (BSTNode<T>) node.getParent();
        boolean hasOneChild = node.getLeft().isEmpty() ^ node.getRight().isEmpty();
        if (node.isLeaf()) {
            node.setData(null);
            rebalanceUp(node);
        } else if (hasOneChild) {
            if (parent != null) {
                if (!node.getLeft().isEmpty()) {
                    if (isLeftChild(node, parent)) {
                        parent.setLeft(node.getLeft());
                        node.getLeft().setParent(parent);
                    } else {
                        parent.setRight(node.getLeft());
                        node.getLeft().setParent(parent);
                    }
                } else {
                    if (isLeftChild(node, parent)) {
                        parent.setLeft(node.getRight());
                        node.getRight().setParent(parent);
                    } else {
                        parent.setRight(node.getRight());
                        node.getRight().setParent(parent);
                    }
                }
            } else {
                if (node.getLeft() == null || node.getLeft().isEmpty())
                    root = (BSTNode<T>) node.getRight();
                else
                    root = (BSTNode<T>) node.getLeft();
                root.setParent(null);
                rebalanceUp(node);
            }
        } else {
            BSTNode<T> nodeAux = sucessor(node.getData());
            T aux = node.getData();
            node.setData(nodeAux.getData());
            nodeAux.setData(aux);
            removeRecursive(nodeAux);
        }
    }

    private boolean isLeftChild(BSTNode<T> node, BSTNode<T> parent) {
        return parent.getLeft().equals(node);
    }

    private boolean isRightChild(BSTNode<T> node, BSTNode<T> parent) {
        return parent.getRight().equals(node);
    }

    // AUXILIARY
    protected int calculateBalance(BSTNode<T> node) {
        return height((BSTNode<T>) node.getLeft()) - height((BSTNode<T>) node.getRight());
    }

    // AUXILIARY
    protected void rebalance(BSTNode<T> node) {
        int balance = calculateBalance(node);
        // criar duas sub arvores
        BSTNode<T> subArvoreDireita = (BSTNode<T>) node.getRight();
        BSTNode<T> subArvoreEsquerda = (BSTNode<T>) node.getLeft();
        if (balance > 1) {
            if (calculateBalance(subArvoreEsquerda) >= 0) {
                rightRotationAux(node);
            } else {
                doubleRightRotation(node);
            }
        } else if (balance < -1) {
            if (calculateBalance(subArvoreDireita) < 0) {
                leftRotationAux(node);
            } else {
                doubleLeftRotation(node);
            }
        }
    }

    protected void leftRotationAux(BSTNode<T> node) {
        BSTNode<T> auxiliar = Util.leftRotation(node);
        if (node.equals(root)) {
            root = (BSTNode<T>) node.getParent();
        } else {
            if (auxiliar.getParent().getRight().equals(node)) {
                auxiliar.getParent().setRight(auxiliar);
            } else {
                auxiliar.getParent().setLeft(auxiliar);
            }
        }
    }

    protected void rightRotationAux(BSTNode<T> node) {
        BSTNode<T> auxiliar = Util.rightRotation(node);
        if (node.equals(root)) {
            root = (BSTNode<T>) node.getParent();
        } else {
            if (auxiliar.getParent().getRight().equals(node)) {
                auxiliar.getParent().setRight(auxiliar);
            } else {
                auxiliar.getParent().setLeft(auxiliar);
            }
        }
    }

    protected void doubleRightRotation(BSTNode<T> node) {
        leftRotationAux((BSTNode<T>) node.getLeft());
        rightRotationAux(node);
    }

    protected void doubleLeftRotation(BSTNode<T> node) {
        rightRotationAux((BSTNode<T>) node.getRight());
        leftRotationAux(node);

    }

    // AUXILIARY
    protected void rebalanceUp(BSTNode<T> node) {
        BSTNode<T> parent = (BSTNode<T>) node.getParent();
        while (parent != null) {
            rebalance(parent);
            parent = ((BSTNode<T>) parent.getParent());
        }
    }
}
