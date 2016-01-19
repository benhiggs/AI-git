import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

/*
   Solver is a class that contains the methods used to search for and print solutions
   plus the data structures needed for the search.
 */

public class Solver {

    ArrayList<Node> unexpanded = new ArrayList<Node>(); 
    ArrayList<Node> expanded = new ArrayList<Node>();   
    Node rootNode;                                   

    /*
       Solver is a constructor that sets up an instance of the class with a node corresponding
       to the initial state as the root node.
     */
    public Solver(char[] initialBoard) {
        GameState initialState = new GameState(initialBoard);
        rootNode = new Node(initialState);
    }

    /*
       The method solve searches for a solution. Depth first search this time instead of breath first
       The Printwriter argument is used to specify where the output should be directed.
     */
    public void solve(PrintWriter output) {                                         //takes the output format as an arguement, so that it can be output to the textfile.
        unexpanded.add(rootNode);                                                   //adds the current rootnode Node object to the unexpanded arraylist.
        while (unexpanded.size() > 0) {                                             //loop whilst the arraylist holding the unexpanded nodes is populated
            Node n = unexpanded.get((unexpanded.size()-1));                           //Node object n equals the last member of the unexpanded arraylist
            expanded.add(n);                                                        //adds the node n to the exppanded arraylist
            unexpanded.remove((unexpanded.size()-1));                                                   //removes the current node n, which is also the first object,from the unexpanded arraylist
            if (n.state.isGoal()) {                                                 //if the node n matches the goal state, (if the boards are the same)
                reportSolution(n, output);                                          //the solution is reported using the reportsolution method where the node and output are supplied
                return;                                                             //breaks the while loop as the solution has been found
            } else {                                                                //otherwise
                ArrayList<GameState> moveList = n.state.possibleMoves();            //move list array initiated and populated with the possible moves from state n
                for (GameState gs : moveList) {                                     //for each state in possible moves, use the temporary GameState var gs
                    if ((Node.findNodeWithState(unexpanded, gs) == null) &&         //if the current gamestate is not in the unexpanded arraylist and...
                       (Node.findNodeWithState(expanded, gs) == null)) {            //the current gamestate is not in the expanded arraylist (nothing is returned from the function findNodeWithState)
                        int newCost = n.getCost() + 1;                              //returns the cost of exploring that node, and saves is as int newCost
                        Node newNode = new Node(gs, n, newCost);                    //newNode is creates as type Node, which consists of the gamestate from moveList, the unexpanded node and the cost of exploration
                        unexpanded.add(newNode);                                    //this newNode is added to the unexpanded list
                    }

                }
            }
        }
        output.println("No solution found");                                        //outputs no solution found if nothing is found.
    }

    /*
       printSolution is a recursive method that prints all the states in a solution.
     */
    public void printSolution(Node n, PrintWriter output) {
        if (n.parent != null) printSolution(n.parent, output);
        output.println(n.state);
    }

    /*
       reportSolution prints the solution together with statistics on the number of moves
       and the number of expanded and unexpanded nodes.
     */
    public void reportSolution(Node n, PrintWriter output) {
        output.println("Solution found!");
        printSolution(n, output);
        output.println(n.getCost() + " Moves");
        output.println("Nodes expanded: " + this.expanded.size());
        output.println("Nodes unexpanded: " + this.unexpanded.size());
        output.println();
    }


    public static void main(String[] args) throws Exception {
        Solver problem = new Solver(GameState.INITIAL_BOARD);  
        File outFile = new File("output.txt");                 
        PrintWriter output = new PrintWriter(outFile);         
        problem.solve(output);                                 
        output.close();                                        
    }
    
}
