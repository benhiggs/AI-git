import java.util.ArrayList;

/*
     Instances of the class GameState represent states that can arise in the sliding block puzzle.
 */

public class GameState {
    final char[] board;
    private int spacePos;
    static final char[] INITIAL_BOARD = {'B','B','B',' ','W','W','W'};
    static final char[] GOAL_BOARD = {'W','W','W',' ','B','B','B'};
    static final int BOARD_SIZE = 7;
        
    /*
        GameState is a constructor that takes a char array holding a board configuration as argument.
     */
    public GameState(char[] board) {
        this.board = board;
        for (int j = 0; j < BOARD_SIZE; j++){
            if (board[j] == ' ') {
                this.spacePos = j;
                break;
            }
        }
    }

    /*
        clone returns a new GameState with the same board configuration as the current GameState.
     */
    public GameState clone() {
        char[] clonedBoard = new char[BOARD_SIZE];
        System.arraycopy(this.board, 0, clonedBoard, 0, BOARD_SIZE);
        return new GameState(clonedBoard);
    }

    public int getSpacePos() {
        return spacePos;
    }

    /*
        toString returns the board configuration of the current GameState as a printable string.
    */
    public String toString() {
        String s = "[";
        for (char c : this.board) s = s + c;
        return s + "]";
    }

    /*
        isGoal returns true if and only if the board configuration of the current GameState is the goal
        configuration.
    */
    public boolean isGoal() {
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (this.board[j] != GOAL_BOARD[j]) return false;
        }
        return true;
    }

    /*
         sameBoard returns true if and only if the GameState supplied as argument has the same board
         configuration as the current GameState.
     */
    public boolean sameBoard (GameState gs) {
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (this.board[j] != gs.board[j]) return false;
        }
        return true;
    }

    /*
        possibleMoves returns a list of all GameStates that can be reached in a single move from the current GameState.
     */
    public ArrayList<GameState> possibleMoves() {                       //returns an arraylist of type GameState and takes no arguements
        ArrayList<GameState> moves = new ArrayList<GameState>();                //generate an empty arraylist of type GameState to hold multiple states that can be reached in a single move
        for (int start = 0; start < BOARD_SIZE; start++) {              //loop setup to iterate through for each position on the board
            if (start != this.spacePos) {                               //if the iterator is not in the same position as the empty space, which we cannot move, then execute code underneath
                int distance = Math.abs(this.spacePos - start);         //returns the gap between the current position and the blank tile space and stores it as an integer 'distance'
                if (distance <= 3) {                                    //if the distance between our current position and the blank tile space is less or equal to three
                    GameState newState = this.clone();                  //the current GameState object is cloned and a new object newState refers to this
                    newState.board[this.spacePos] = this.board[start];  //the start position on the current board is swapped into the current position in the newState board array
                    newState.board[start] = ' ';                        //the start position in the newState array is now the blank space
                    newState.spacePos = start;                          //and the space position is now the start space in the newState
                    moves.add(newState);                                //the new state is added to the moves array
                }
            }
        }
        return moves;                                                   //the moves arraylist is returned to the method that called this method
    }

}

