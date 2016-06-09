import java.util.Scanner;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class Soln
{
    static int N;
    static String First = "";
    static String Second = "";
    static ArrayList<String> Characters = new ArrayList<String>();      // array of N! combinations, in order
    static int Answer = -1;

    public static void printTree() {
        System.out.println("tree = ");
        for (String number : Characters) {
            System.out.println("  " + number);
        }
    }

    public static void treeHelper(Map treeNode, String pastChars, String charSet) {
        if (charSet.length() == 2) {
            String first = "" + charSet.charAt(0);
            String second = "" + charSet.charAt(1);
            String currChar = "" + pastChars.charAt(pastChars.length()-1);

            treeNode.put(currChar, new String[2]);
            ((String[])treeNode.get(currChar))[0] = first + second;
            ((String[])treeNode.get(currChar))[1] = second + first;

            Characters.add(pastChars + first + second);
            Characters.add(pastChars + second + first);
        } else {
            for (String c : charSet.split("")) {
                treeNode.put(c, null);
                treeHelper(treeNode, pastChars + c, charSet.replace(c, ""));
            }
        }
    }

    // leaves consist of all N! combination of characters.
    // root is an N dimensional Array. 
    public static void tree() {
        String charSet = "";
        for (int i = 0; i < N; i++) {
            charSet += (char) (i + 97);
        }

        Map tree = new HashMap();
        for (String c : charSet.split("")) {
            tree.put(c, new HashMap());
            Map treeNode = (Map)tree.get(c);

            treeHelper(treeNode, c, charSet.replace(c, ""));    // recurse
        }
    }

    public static void abracadabra() {
        tree();
        //printTree();

        // ensure Answer is a positive number
        if (Second.compareTo(First) < 0) {
            Answer = Characters.indexOf(First) - Characters.indexOf(Second) - 1;
        } else {
            Answer = Characters.indexOf(Second) - Characters.indexOf(First) - 1;
        }
    }

    /* Answers to 1_input.txt:
     *  #1 4
     *  #2 6
     *  #3 4
     *  #4 76
     *  #5 10606
     */
    public static void main(String args[]) throws Exception 
    {
        String filename = args[0];
        System.setIn(new FileInputStream(filename + "_input.txt"));

        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int test_case = 1; test_case <= T; ++test_case) {
            N = sc.nextInt();
            First = sc.next();
            Second = sc.next();

            abracadabra();

            System.out.println("#" + test_case + " "  + Answer);
        }
    }
}