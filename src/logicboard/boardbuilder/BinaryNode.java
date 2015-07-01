package logicboard.boardbuilder;

/**
 * Created by CptAmerica on 4/22/15.
 */
public class BinaryNode<T> {

    protected BinaryNode<T> leftChild; //Points to left child
    protected BinaryNode<T> rightChild; //Points to right child
    private BinaryNode parent; //Points to Parent of this node
    private T element;

    /**
     * No-args Constructor. Sets both children to null, as well as its parent and element parameter.
     */
    public BinaryNode(){
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
        this.element = null;
    }

    /**
     * Create a BinaryNode object whith null parameters except for its parent.
     * @param parent
     */
    public BinaryNode(BinaryNode<T> parent ){
        this.leftChild = null;
        this.rightChild = null;
        this.parent = parent;
        this.element = null;
    }

    /**
     * Same as the no-args constructor, except you can assign an element to this objects element field.
     * @param element
     */
    public BinaryNode(T element ){
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
        this.element = element;
    }

    /**
     * Can set this objects parent and element fields the the two object references passed in this constructor's parameter.
     * @param parent
     * @param element
     */
    public BinaryNode(BinaryNode<T> parent, T element ){
        this.leftChild = null;
        this.rightChild = null;
        this.parent = parent;
        this.element = element;
    }

    public BinaryNode(T element, LinkedBinaryTree<T> left, LinkedBinaryTree<T> right){
        if(left == null) {
            this.leftChild = null;
        } else{
            this.leftChild = (BinaryNode<T>)left.getRootElement();
        }

        if(right == null) {
            this.rightChild = null;
        }else{
            this.rightChild = (BinaryNode<T>) right.getRootElement();
        }

        this.parent = parent;
        this.element = element;
    }
    /**
     * Constructor that sets all fields to the variables passed in to this constructors parameters.
     * @param parent the the reference variable of the node to set the nodes parent to.
     * @param element the referene variable of the element to set the nodes element to.
     * @param left the reference variable of the tree to set the left child of this node to.
     * @param right the reference variable of the tree to set the right child of this node to.
     */
    public BinaryNode(BinaryNode<T> parent, T element, LinkedBinaryTree<T> left, LinkedBinaryTree<T> right){
        if(left == null) {
            this.leftChild = null;
        } else{
            this.leftChild = (BinaryNode<T>)left.getRootElement();
        }

        if(right == null) {
            this.rightChild = null;
        }else{
            this.rightChild = (BinaryNode<T>) right.getRootElement();
        }

        this.parent = parent;
        this.element = element;
    }


    /**
     * Gets the element at this node.
     * @return the element at this node.
     */
    public T getElement(){
        return element;
    }

    /**
     * Sets the element at this node.
     * @param element the element to assign to reference variable for the element at this node.
     */
    public void setElement(T element){
        this.element = element;
    }
    /**
     * Returns a reference of the left child of this node.
     * @return a reference of the left child
     */
    public BinaryNode getLeftChild() {
        return leftChild;
    }

    /**
     * Sets the leftChild reference pointer to the reference variable passed into the parameter.
     * @param leftChild the reference variable to set the reference pointer to.
     */
    public void setLeftChild(BinaryNode leftChild) {
        this.leftChild = leftChild;
    }

    /**
     * Returns a reference of the right child of this node.
     * @return a reference of the right child of this node.
     */
    public BinaryNode getRightChild() {
        return rightChild;
    }


    /**
     * Sets the leftChild reference pointer to the reference variable passed into the parameter.
     * @param rightChild the reference variable to set the reference pointer to.
     */
    public void setRightChild(BinaryNode rightChild) {
        this.rightChild = rightChild;
    }

    /**
     * Returns a reference pointer of the parent of this node.
     * @return the reference pointer of the parent of this node.
     */
    public BinaryNode getParent() {
        return parent;
    }

}
