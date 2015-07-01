package logicboard.boardbuilder;

import java.util.Iterator;

/**
 * Created by CptAmerica on 4/22/15.
 */
public interface BinaryTreeADT<T> {

    /**
     * Returns a reference to the root of the binary tree.
     * @return a reference to the root of the binary tree
     */
    public T getRootElement();

    /**
     * Determines whether the tree is empty.
     * @return A boolean value depending on the trees reference.
     */
    public boolean isEmpty();

    /**
     * Returns the number of elements in the tree.
     * @return the number of elements in the tree.
     */
    public int size();

    /**
     * Determines whether the specified target is in the tree.
     * @param targetElement the target element to look for in the tree.
     * @return returns true if the target element was found in the tree. Returns false if the target element was not found in the tree.
     */
    public boolean contains(T targetElement);

    /**
     * Returns a reference to the specified target element if it is found.
     * @param targetElement the target element to find and get its reference.
     * @return Returns the reference of the target element if found in the tree.
     */
    public T find(T targetElement);

    /**
     * Iterator of the binary tree.
     * @return Returns an Iterator of the binary tree.
     */
    public Iterator<T> iterator();

    /**
     * Returns an iterator for an inorder traversal if the tree.
     * @return Returns an iterator of the tree.
     */
    public Iterator<T> iteratorInOrder();

    /**
     * Returns an iterator for a pre-order traversal of the tree,
     * @return an iterator of the tree.
     */
    public Iterator<T> iteratorPreOrder();

    /**
     * Returns an iterator for a post-order traversal of the tree.
     * @return Returns an iterator of the tree.
     */
    public Iterator<T> iteratorPostOrder();

    /**
     * Returns an iterator for a level-order traversal of the tree.
     * @return Returns an iterator of the tree.
     */
    public Iterator<T> iteratorLevelOrder();

    /**
     * Returns a string representation of the tree.
     * @return A String representation of the tree.
     */
    public String toString();


}
