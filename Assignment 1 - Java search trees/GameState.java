import java.util.ArrayList;

/*
     Instances of the class GameState represent states that can arise in the sliding block puzzle.
 */

public class GameState {
    final char[] board;                                     //pos 1=corn, 2=goose, 3=fox, 4=farmer
    private char farmer;
    static final char[] INITIAL_BOARD = {'R','R','R','R'};  //pos 1=corn, 2=goose, 3=fox, 4=farmer
    static final char[] GOAL_BOARD = {'L','L','L','L'};     //pos 1=corn, 2=goose, 3=fox, 4=farmer
    static final int BOARD_SIZE = 4;
        
    /*
        GameState is a constructor that takes a char array holding a board configuration as argument.
     */
    public GameState(char[] board) {
        this.board = board;
        this.farmer=board[3];
    }

    /*
        clone returns a new GameState with the same board configuration as the current GameState.
     */    public GameState clone() {
        char[] clonedBoard = new char[BOARD_SIZE];
        System.arraycopy(this.board, 0, clonedBoard, 0, BOARD_SIZE);
        return new GameState(clonedBoard);
    }

    public char getFarmer() {
        return farmer;
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
        movePossible determines if the move is valid. The goose cannot be left with he corn and the fox cannot be left with the goose.
        pos 1=corn, 2=goose, 3=fox, 4=farmer
     */
    public boolean movePossible(GameState gs){                          //move possible filters out illegal moves.
        if(gs.board[0]==gs.board[1]){                                   //if corn and goose are on the same side of the river..
            if(gs.board[0]==gs.board[3]&&gs.board[1]==gs.board[3]){     //if the farmer is also there..
                return true;                                            //return true
            }
            else{
                return false;                                           //otherwise return false as the goose will eat the corn is left alone
            }
        }
        else if(gs.board[1]==gs.board[2]){                              //if the goose and fox are on the same side of the river..
            if(gs.board[1]==gs.board[3]&&gs.board[2]==gs.board[3]){     //if the farmer is also there..
                return true;                                            //return true
            }
            else{
                return false;                                           //otherwise return false as the fox will eat the goose if left alone
            }
        }
        else{
            return true;                                                //if these conditions arent met, the move will be legal so return true
        }
    }

    /*
        possibleMoves returns a list of all GameStates that can be reached in a single move from the current GameState.
        pos 1=corn, 2=goose, 3=fox, 4=farmer
     */
    public ArrayList<GameState> possibleMoves() {
        ArrayList<GameState> moves = new ArrayList<GameState>();            //all possible moves
        for (int start = 0; start < BOARD_SIZE-1; start++) {        //for all postions ont the board
            GameState newState = this.clone();                      //clones this gamestate into newState var
            if (board[start] == this.farmer) {                      //if the item at start postion is on the same side as the farmer, he can take them across
                if (board[start] == 'L') {                          //if both on left, set both to right side as theyve now travelled
                    newState.board[3] = 'R';                        //farmer position set to R
                    newState.farmer = 'R';                          //farmer var set to R
                    newState.board[start] = 'R';                    //item thats moving position set to R
                } else if (board[start] == 'R') {                   //if both on right, set both to left as theyve now travelled
                    newState.board[3] = 'L';                        //farmer position set to L
                    newState.farmer = 'L';                          //farmer position set to R
                    newState.board[start] = 'L';                    //farmer position set to R
                }
            }
            else {                                                  //if no items on the side of the farmer, the farmer can travell across alone
                if (this.farmer == 'L') {                           //if farmer is on the left
                    newState.board[3] = 'R';                        //set farmer position to R
                    newState.farmer='R';                            //set farmer var to R
                } else {
                    newState.board[3] = 'L';                        //set farmer position to L
                    newState.farmer='L';                            //set farmer var to L
                }
            }
            if (movePossible(newState)) {                           //use function movePossible to compare locations and work out if th emove is legal (e.g. corn and goose not left alone together)
                moves.add(newState);                                //if they are able to move, and the new gamestate has been setup, add to moves.
                }
        }
        return moves;                                               //return arraylist of type GameState containing all the legal moves possible
    }

}

