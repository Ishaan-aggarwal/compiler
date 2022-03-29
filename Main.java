/*Names: Ishaan Aggarwal, Luke Blaydes
* Project Phase 2.1: parser for expressions
*/

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;

public class Main {
    private static boolean isError = false;

    public static void main(String[] args) {

        BufferedReader reader;
        ArrayList<Token> tokens = new ArrayList<Token>(); // contains all of our tokens after parsing
        try {
            FileWriter f = new FileWriter(args[1]);
            reader = new BufferedReader(new FileReader(args[0]));
            try {
                String input = reader.readLine(); // recieves a single line from the input file
                while (input != null) {

                    // we parse the tokens on the appearance of a space, as well as
                    // before and after a special character appears
                    Scanner sc = new Scanner(input);
                    ArrayList<Token> temp = sc.recv();
                    for (Token tok : temp)
                        tokens.add(tok);

                    input = reader.readLine();
                }

                f.write("Tokens:\n\n");
                System.out.println(tokens);
                for (Token tok : tokens) {
                    if (!tok.getType().contains("ERROR")) {
                        f.write(tok.getToken() + " : " + tok.getType() + "\n");
                    } else {
                        System.out.println("Found error");
                        System.out.println(tok);
                        f.write(tok.getToken() + " : " + tok.getType() + "\n");
                        isError = true;
                        break;
                    }
                }

                if (isError) {
                    System.exit(1);
                }

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error occurred");
            }

            f.write("\nAST: \n\n");

            System.out.println(tokens);
            Tree parseTree = new Tree(tokens, f);
            try {
                parseTree.preOrderPrint(parseTree.root, 0);
            } catch (Exception err) {
                System.out.println("Error Writing in Parsing Class");
            }
            f.close();
        } catch (Exception err) {
            System.out.println("Error opening files");
        }
    }

}