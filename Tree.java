/*Names: Ishaan Aggarwal, Luke Blaydes
* Project Phase 2.1: parser for expressions
*/

import java.util.ArrayList;
import java.util.Iterator;
import java.io.FileWriter;

public class Tree {
    public Node root;
    FileWriter f;
    ArrayList<Token> tokens;
    int i;
    Token next_token;

    public Tree(ArrayList<Token> tokens, FileWriter f) {
        this.f = f;
        this.tokens = tokens;
        i = 0;
        next_token = tokens.get(i);
        try {
            root = statement();
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    private void consume_token() {
        if (tokens.size() > i + 1)
            i++;
        next_token = tokens.get(i);
    }

    private Node element() throws Exception {
        Node one;
        if (next_token.getToken().contains("(")) {
            consume_token();
            one = expression();
            if (!next_token.getToken().contains(")")) {
                throw new Exception("Parsing Exception: Missing ending )");
            } else {
                consume_token();
                return one;
            }
        } else if (!next_token.getType().contains("NUMBER") && !next_token.getType().contains("IDENTIFIER")
                && !next_token.getType().contains("KEYWORD")) {
            throw new Exception("Parsing Exception: Not NUMBER / IDENTIFIER / KEYWORD");
        } else {
            Token t = next_token;
            consume_token();
            return new Node(t);
        }
    }

    private Node piece() throws Exception {
        Node one = element();
        //
        while (next_token.getToken().contains("/")) {
            Token t = next_token;
            consume_token();
            one = new Node(t, one, element());
        }
        return one;
    }

    private Node factor() throws Exception{
        Node one = piece();
        while(next_token.getToken().contains("*")){
            Token t = next_token;
            consume_token();
            one = new Node(t, one, piece());
        }
        return one;
    }

    private Node term() throws Exception{
        Node one = factor();
        while(next_token.getToken().contains("-")){
            Token t = next_token;
            consume_token();
            one = new Node(t, one, factor());
        }
        return one;
    }

    private Node expression() throws Exception {
        Node one = term();
        while (next_token.getToken().contains("+")) {
            Token t = next_token;
            consume_token();
            one = new Node(t, one, term());
        }
        return one;
    }

    public Node while_statement() throws Exception {
        Node one;
        Node two;
        consume_token();
        one = expression();
        if (next_token.getToken().contains("do")) {
            consume_token();
            two = statement();
            if (next_token.getToken().contains("endwhile")) {
                return new Node(new Token("WHILE-LOOP", ""), one, null, two);
            } else {
                throw new Exception("Parsing Exception: Missing endwhile");
            }
        } else {
            throw new Exception("Parsing Exception: Missing do in while statement");
        }   
    }

    public Node if_statement() throws Exception {
        Node one;
        Node two;
        Node three;
        consume_token();
        one = expression();
        if (next_token.getToken().contains("then")) {
            consume_token();
            two = statement();
            if (next_token.getToken().contains("else")) {
                consume_token();
                three = statement();
                if (next_token.getToken().contains("endif")) {
                    return new Node(new Token("IF-STATEMENT", ""), one, two, three);
                } else {
                    throw new Exception("Parsing Exception: Missing endif");
                }
            } else {
                throw new Exception("Parsing Exception: Missing else");
            }
        } else {
                throw new Exception("Parsing Exception: Missing then in If statement");
        }
    }

    // assignment ::= IDENTIFIER := expression
    public Node assignment() throws Exception {
        Node one;
        Token id = next_token;
        consume_token();
        if (next_token.getToken().contains(":=")) {
            Token sym = next_token;
            consume_token();
            one = expression();
            return new Node(sym, new Node(id), null, one);
        } else {
            throw new Exception("Parsing Error: Assignment not found");
        }
    }  

    public Node base_statement() throws Exception {
        System.out.println(next_token);
        Node one;
        if (next_token.getToken().contains("skip")) {
            Token t = next_token;
            consume_token();
            return new Node(t, null, null, null);
        } else if (next_token.getToken().contains("while")) {
            one = while_statement();
            return one;
        } else if (next_token.getToken().contains("if")) {
            one = if_statement();
            return one;
        } else if (next_token.getType().contains("IDENTIFIER")) {
            one = assignment();
            return one;
        } else {
            //System.out.println(next_token);
            throw new Exception("Parsing Exception: Cant find base statement");
        }
    }

    public Node statement() throws Exception {
        System.out.println(next_token);
        Node one = base_statement();
        while (next_token.getToken().contains(";")) {
            Token t = next_token;
            consume_token();
            one = new Node(t, one, null, base_statement());
        }
        return one;
    }

    // TODO
    public void preOrderPrint(Node n, int s) throws Exception {
        if (n == null)
            return;
        else {

            for (int i = 0; i < s; i++) {
                System.out.print("\t");
                f.write("\t");
            }
            f.write(n.value.getType() + " " + n.value.getToken() + "\n");
            System.out.println(n.value.getType() + " " + n.value.getToken());

            preOrderPrint(n.left, s+1);
            preOrderPrint(n.middle, s+1);
            preOrderPrint(n.right, s+1);
        }
    }

}