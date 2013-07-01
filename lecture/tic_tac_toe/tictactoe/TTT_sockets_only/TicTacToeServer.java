package lesson2.examples.tictactoe.TTT_sockets_only;

import java.net.*;
import java.io.*;
import  lesson2.examples.tictactoe.core.TicTacToe;
import  lesson2.examples.tictactoe.tools.ParserUtils;

public class TicTacToeServer{
    private int          port;  /* port that this server runs on */
    private ServerSocket ssock; /* ServerSocket object */
    private TicTacToe    game;  /* shared copy of tic-tac-toe game board */

    /* pass shared board and port to server */
    TicTacToeServer(int port, TicTacToe game){
	this.port = port;
	this.game = game;
    }
	
    public void go() throws Exception{
	Player[] player = new Player[2];  /* each Player in separate thread */

	ssock = new ServerSocket(port);   /* serversocket listens for connections */

	System.out.println("TicTacToeServer running on port " + port 
			   + " ... waiting for connections");
 
	for (int i = 0; i <=1; ++i){            /* for each player... */
	    Socket csock = ssock.accept();      /* block on accept ...*/
	    System.out.println("player " + i  + " connected");
	    player[i] = new Player(csock, game);/* this sends notification to client */
	}

	int current = 0;                       /* current turn's id */
        int other = 1;                         /* waiting player id */

	boolean gameOver = false;
	/* main game loop */
	while (!gameOver){
	    int move = player[current].readMove();
	    byte movex = (byte) (move % 3); 
	    byte movey = (byte) (move / 3);
	    int response  = game.placeMark( movex , movey, (byte) current);
	    if (response == 0){
		player[other].sendMove(move);
		int tmp = other; other = current; current = tmp;
	    }
	    else if (response == -1)
		continue;
	    else  //if response == 1
		gameOver = true;
	}
	/* end main game loop */
	player[0].sendMove(-1);
	player[1].sendMove(-1);
    }
    
    public static void main(String[] args){
	TicTacToe game = new TicTacToe();
	TicTacToeServer s = new TicTacToeServer(1111,game);
	try{
	    s.go();
	}
	catch(Exception e){
	    System.out.println(e);
	}
    }
}


/* 
   encapsulates functionality to readMove and sendMove 
*/
  
class Player{
    private Socket           sock = null;
    private DataInputStream  in   = null;
    private DataOutputStream out  = null;
    private TicTacToe        game = null;
    private static int playerNum = 0;

    Player(Socket csock, TicTacToe game) throws Exception{
	this.sock = csock;
	try{
	    in  = new DataInputStream(csock.getInputStream());
	    out = new DataOutputStream(csock.getOutputStream());
	}
	catch(Exception e){
	    System.out.println(e);
	}
	out.writeInt(playerNum);    // tell the player whether 'X' or 'O'
	++playerNum;
    }
    
    public int readMove() throws Exception{
	int move = in.readInt();
	return (move);
    }
    
    public void sendMove(int move) throws Exception{
	out.writeInt(move);
    }

}
