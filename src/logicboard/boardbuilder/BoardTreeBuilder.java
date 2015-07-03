package logicboard.boardbuilder;

import logicboard.LogicBoard;

/**
 * Created by CptAmerica on 7/2/15.
 */
public class BoardTreeBuilder {


    public LogicBoard buildTree(String exprs, LogicBoard bd){
        BoardTree<String> tree = new BoardTree<String>(bd);
        BinaryNode<String> temp = tree.root;


        for(String s : exprs.split(" ")){
            if(s.equalsIgnoreCase("(")){
                if(temp.getLeftChild() == null){
                    //Instantiate right child, pass in reference to it's parent
                    temp.setLeftChild(new BinaryNode(temp));
                    temp = temp.getLeftChild();
                }
                else{
                    //Instantiate right child, pass in reference to it's parent
                    temp.setRightChild(new BinaryNode(temp));
                    temp = temp.getRightChild();
                }
            }
            else if(s.equalsIgnoreCase(")")){
                temp = temp.getParent();
            }

            //IF AN OPERATOR:    (AND == *, OR == +, NOT == ')
            else if(s.equalsIgnoreCase("+")){
                temp.setElement("+");
            }
            else if(s.equalsIgnoreCase("*")){
                temp.setElement("*");
            }
            else if(s.equalsIgnoreCase("'")){
                temp.setElement("'");
            }


            else{
                //IF GateID
                if(temp.getLeftChild() == null){
                    temp.setLeftChild(new BinaryNode(temp));
                    temp.getLeftChild().setElement(s);
                }
                else{
                    if(temp.getRightChild() == null){
                        temp.setRightChild(new BinaryNode(temp));
                        temp.getRightChild().setElement(s);
                    }
                }
            }
        }

        tree.evaluateTree();
        //System.out.println("Board output gateID: "+exprsOutput);
        return tree.getBoard();
    }

    public LogicBoard buildTree(String exprs, String[] inputs, String boardName){
        BoardTree<String> tree = new BoardTree<String>(inputs,boardName);
        BinaryNode<String> temp = tree.root;


        for(String s : exprs.split(" ")){
            if(s.equalsIgnoreCase("(")){
                if(temp.getLeftChild() == null){
                    //Instantiate right child, pass in reference to it's parent
                    temp.setLeftChild(new BinaryNode(temp));
                    temp = temp.getLeftChild();
                }
                else{
                    //Instantiate right child, pass in reference to it's parent
                    temp.setRightChild(new BinaryNode(temp));
                    temp = temp.getRightChild();
                }
            }
            else if(s.equalsIgnoreCase(")")){
                temp = temp.getParent();
            }

            //IF AN OPERATOR:    (AND == *, OR == +, NOT == ')
            else if(s.equalsIgnoreCase("+")){
                temp.setElement("+");
            }
            else if(s.equalsIgnoreCase("*")){
                temp.setElement("*");
            }
            else if(s.equalsIgnoreCase("'")){
                temp.setElement("'");
            }


            else{
                //IF GateID
                if(temp.getLeftChild() == null){
                    temp.setLeftChild(new BinaryNode(temp));
                    temp.getLeftChild().setElement(s);
                }
                else{
                    if(temp.getRightChild() == null){
                        temp.setRightChild(new BinaryNode(temp));
                        temp.getRightChild().setElement(s);
                    }
                }
            }
        }

        tree.evaluateTree();
        //System.out.println("Board output gateID: "+exprsOutput);
        return tree.getBoard();
    }


}
