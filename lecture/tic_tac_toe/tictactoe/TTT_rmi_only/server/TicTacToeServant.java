
import java.rmi.*;
import java.rmi.server.*;

public class TicTacToeServant{
    public TicTacToeServant(){
	try {
	    TicTacToe game = new TicTacToe();
	    //1099 is default rmi port and doesn't need to be specified
	    //localhost is default server and doesn't need to be specified
	    //	    Naming.rebind("//localhost:1099/tic-tac-toe", game);
	    Naming.rebind("tic-tac-toe", game);
	    System.out.println("tic-tac-toe server bound");
	} catch (Exception e) {
	    System.err.println("Problem occurred: " + e.getMessage());
	    e.printStackTrace();
	}
	
    }	
    public static void main(String[] args) {
	TicTacToeServant ttts = new TicTacToeServant();
    }
}
