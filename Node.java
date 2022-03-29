/*Names: Ishaan Aggarwal, Luke Blaydes
* Project Phase 2.1: parser for expressions
*/

import java.util.*;

public class Node {

  public Node left;
  public Node right;
  public Node middle;
  public ArrayList<Node> children;
  
  public Token value;
  public Node(Token token){
    value = token;
    left = null;
    middle = null;
    right = null;
    //children = new ArrayList<Node>();
    
  }

  public Node(Token token, Node t1, Node t2 ){
    value = token;
    left = t1;
    middle = null;
    right = t2;
      /*
    children = new ArrayList<Node>();
    children.add(left);
    children.add(right);
      */
  }

  public Node(Token token, Node t1, Node t2, Node t3){
      value = token;
      left = t1;
      middle = t2;
      right = t3;
     /* children = new ArrayList<Node>();
    children.add(left);
    children.add(right);
    children.add(middle); */
  }

  public Node(Token token, ArrayList<Node> list){
      value = token;
      children = list;
  }

  public ArrayList<Node> getChildren(){
      return children;
  }

  public String toString(){
      if(value != null)
        return value.getToken();
      else
        return "";
  }
}