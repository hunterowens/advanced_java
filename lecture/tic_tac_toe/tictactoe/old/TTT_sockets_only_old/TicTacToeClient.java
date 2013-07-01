import java.net.*;
import java.io.*;

public class TicTacToeClient{
    public static void main(String[] args){
	String hostname = "fullerton.cs.uchicago.edu";
	Board board = new Board();

	if (args.length > 0){
	    hostname = args[0];
	}

	DataOutputStream networkOut = null;
	DataInputStream  networkIn  = null;
	BufferedReader   userIn     = null;

	try{
	    Socket s = new Socket(hostname, 1111);
	    networkIn = new DataInputStream(s.getInputStream());
	    networkOut= new DataOutputStream(s.getOutputStream());
	    userIn    = new BufferedReader
		(new InputStreamReader
		    (System.in));

	    System.out.println("Connected to echo server");
	    int playerNum = networkIn.readInt();        //server tells 'X' or 'O'
	    char myMark = (playerNum == 0 ? 'X' : 'O');
	    char otherMark = (myMark == 'O' ? 'X' : 'O');
	    boolean myTurn = (myMark == 'X' ? true : false);

	    /* main game loop */
	    while (true){
		if (myTurn){
		    System.out.print("Enter move [0-8]: ");
		    int move = Integer.parseInt(userIn.readLine());
		    networkOut.writeInt(move);
		    board.placeMark(move, myMark);
		    board.show();
		}
		else{
		    System.out.print("waiting for opponent move ...");
		    int otherMove = networkIn.readInt();
		    board.placeMark(otherMove, otherMark);
		    board.show();
		}
		myTurn = !myTurn;
	    }
	}
	catch( IOException e){
	    System.err.println(e);
	}
	
	finally {
	    try{
		if (networkIn != null) networkIn.close();
		if (networkOut != null) networkOut.close();
	    }
	    catch(IOException e){}
	}
    }
}

/* 
   represents abstraction of tictactoe board 
   - public void placeMark(int place, char mark)
   - public void show()
*/
class Board{
    private char board[][] = new char[3][3]; 
    
    Board(){
	for (int i = 0; i < 3; ++i)
	    for (int j = 0; j < 3; ++j)
		board[i][j] = '-';
    }


    public void placeMark(int place, char mark){
	board[place % 3][place / 3] = mark;
    }

    public void show(){
	System.out.print("\n");
	for (int i = 0 ; i < 3 ; i++){
	    for (int j = 0 ; j < 3 ; j++){
		System.out.print(board[i][j]);
	    }
	    System.out.print("\n");
	}
    }
}
