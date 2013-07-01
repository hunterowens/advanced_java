import java.net.*;
import java.io.*;

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

	ssock = new ServerSocket(port);   /* serversocket listen sfor connections */

	System.out.println("TicTacToeServer running on port " + port 
			   + " ... waiting for connections");
 
	for (int i = 0; i <=1; ++i){            /* for each player... */
	    Socket csock = ssock.accept();      /* block on accept ...*/
	    player[i] = new Player(csock, game);/* this sends notification to client */
	}

	int current = 0;                       /* current turn's id */
        int other = 1;                         /* waiting player id */

	/* main game loop */
	while (!game.winner()){        
	    int move = player[current].readMove();
	    byte movex = (byte) (move % 3); 
	    byte movey = (byte) (move / 3);
	    boolean validMove = game.placeMark( movex , movey, (byte) current);
	    if (validMove)
		player[other].sendMove(move);
	    else
		continue;
	    int tmp = other; other = current; current = tmp;
	}
	/* end main game loop */
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
