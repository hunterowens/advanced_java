import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

import java.net.*;
import java.rmi.Naming;
import  lesson2.examples.tictactoe.tools.ParserUtils;

public class TTTClient extends UnicastRemoteObject implements TTTClientInterface{

    TicTacToeInterface game;
    static final byte MARK_X = 0;
    static final byte MARK_O = 1;
    int playerID = -1;
    boolean winner = false;

    public TTTClient() throws RemoteException{}

    public TTTClient(TicTacToeInterface game, int id) throws RemoteException{
	this.game = game;
	this.playerID = id;
    }


    public void go() throws Exception{
	String input, action;
	String[] tokens;


	while (true){
	    System.out.print("TTT > ");
	    input = ParserUtils.getKeyInput();
	    tokens = ParserUtils.getTokens(input);
	    if (tokens.length == 0){
		continue;
	    }
	    action = tokens[0];
	    
	    if (action.equalsIgnoreCase("exit"))
		System.exit(1);

	    else if (action.equalsIgnoreCase("move")){
		byte row, col;
		try{
		    col = Byte.parseByte(tokens[1]);
		    row = Byte.parseByte(tokens[2]);
		}
		catch(Exception e){
		    System.out.println(e.getMessage());
		    System.out.println("usage: move row col");
		    continue;
		}
		boolean verify = game.placeMark(col,row, playerID);
		System.out.println(verify);
		if (!verify){
		    System.out.println("error moving, try again");
		}
		else{
		    System.out.println();
		    System.out.println(game.showBoard());
		    winner = game.checkWinner();
		    if (winner){
			System.out.println("WINNING MOVE: game over");
			System.out.println(game.showBoard());
			game.reset();
		    }
		}
		
	    }

	    else if (action.equalsIgnoreCase("show")){
		System.out.println(game.showBoard());
	    }
	    
	    else if (action.equalsIgnoreCase("restart")){
		game.reset();
	    }

	    else {
		System.out.println("invalid command");
		continue;
	    }


	}
	
    }

    //this method is remote and called by server    
    public void otherPlayerMoved(int move) throws RemoteException{
	System.out.println(game.showBoard());
    }

    public void showBoard(byte board[][]) throws RemoteException{}

    public static void main(String[] args){
	int myMark;

	if (args.length == 0){
	    System.out.println("must enter your name");
	    System.exit(1);
	}

	String myName = args[0];

	try{
	    TicTacToeInterface game = (TicTacToeInterface) 
		java.rmi.Naming.lookup("tic-tac-toe");
	    int status = game.register(myName);
	    TTTClient client = new TTTClient(game,status);
	    game.setPlayer(client);

	    if (status == -1){
		System.out.println("Two players already registered");
		System.exit(1);
	    }


	    myMark = ( status == 0 ? MARK_X : MARK_O );

	    client.go(); //game thread

	}
	catch (Exception e){System.out.println(e);}

    }

}


