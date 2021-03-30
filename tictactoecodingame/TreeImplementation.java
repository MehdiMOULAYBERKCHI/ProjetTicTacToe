package tictactoecodingame;


public class TreeImplementation {

    public class State {
        Plateau plateau;
        int playerNo;
        int visitCount;
        double winScore;

        // copy constructor, getters, and setters

        //Todo
        public List<State> getAllPossibleStates() {
            // constructs a list of all possible states from current state
        }
        //Todo
        public void randomPlay() {
        /* get a list of all possible positions on the board and
           play a random move */
        }
    }

    public class Node {
        State state;
        Node parent;
        List<Node> childArray;
        // setters and getters
    }

    public class Tree {
        Node root;
    }
}
