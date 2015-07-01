package logicboard.boardbuilder;


import logicboard.boardbuilder.exceptions.ElementNotFoundException;

import java.util.Iterator;

/**
 * Created by CptAmerica on 4/22/15.
 */
public class LinkedBinaryTree<T> implements BinaryTreeADT<T> {


    protected BinaryNode<T> root;
    protected int modCount;


    /**
     * No-args constructor
     */
    public LinkedBinaryTree(){
        this.root = new BinaryNode<T>();
    }



    @Override
    public T getRootElement() {
        return (T)root;
    }

    @Override
    public boolean isEmpty() {
        boolean isEmpty = false;

        if(root.rightChild == null && root.leftChild == null){
           isEmpty = true;
        }
        return isEmpty;
    }

    @Override
    public int size() {

        int count  = 0;

        if(root != null){
            if(root.getElement() != null){
                count++;
            }
        }

        //Recursive method counts all elements in the tree.
        count = countEl(root.getLeftChild(), root.getRightChild());


            return count;
    }

    /**
     * Recursive method counts the amount of elements in the binaryTree.
     * @param left The left child of the root
     * @param right The right child of the root
     * @return returns the number of elements in the tree.
     */
    private int countEl(BinaryNode<T> left, BinaryNode<T> right){

        int count = 0;

        if(left != null){
            if(left.getElement() != null){
                count += countEl(left.getLeftChild(), left.getRightChild());
            }
        }
        else{
            return 1;
        }

        if(right != null){
            if(right.getElement() != null){
                count += countEl(right.getLeftChild(), right.getRightChild());
            }
        }
        else{
            return 1;
        }


        return count;
    }

    @Override
    public boolean contains(T targetElement) {
        return false;
    }

    @Override
    public T find(T targetElement) {

        BinaryNode<T> current = findNode(targetElement, root);

        if(current == null){
            throw new ElementNotFoundException("LinkedBinaryTree");
        }


        return (current.getElement());
    }

    private BinaryNode<T> findNode(T targetElement, BinaryNode<T> next){

        if(next == null){
            return null;
        }

        if(next.getElement().equals(targetElement)){
            return next;
        }

        BinaryNode<T> temp = findNode(targetElement, next.getLeftChild());

        if(temp == null){
            temp = findNode(targetElement, next.getRightChild());
        }

        return temp;
    }



    //TODO: vv These Methods Below! vv

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Iterator<T> iteratorInOrder() {
        return null;
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        return null;
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        return null;
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        return null;
    }


}
