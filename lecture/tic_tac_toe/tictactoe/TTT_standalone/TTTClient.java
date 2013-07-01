package lesson2.examples.tictactoe.TTT_standalone;

import  lesson2.examples.tictactoe.core.TicTacToe;
import  lesson2.examples.tictactoe.tools.ParserUtils;

public class TTTClient{
    TicTacToe game;
    static final byte MARK_X = 0;
    static final byte MARK_O = 1;
    byte currentMove;
    boolean winner = false;

    public TTTClient(TicTacToe game){
	this.game = game;
	currentMove = MARK_X;
    }

    public void go(){
	String input, action;
	String[] tokens;


	while (true){
	    System.out.print("TTT > ");
	    input = ParserUtils.getKeyInput();
	    tokens = ParserUtils.getTokens(input);
	    
	    action = tokens[0];

	    if (action.equalsIgnoreCase("restart")){
		System.out.println("starting new game ...");
		game.reset();
		continue;
	    }

	    if (action.equalsIgnoreCase("exit"))
		break;

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
		byte result = game.placeMark(col,row,currentMove);
		System.out.println(game.showBoard());
		if (result == TicTacToe.MOVE_INV){
		    System.out.println("error moving, try again");
		}
		else if (result == TicTacToe.MOVE_WIN){
		    System.out.println("WINNING MOVE: game over");
		    game.reset();
		}
		else{ // result == TicTacToe.MOVE_VAL
		    switchMoves();
		}
	    }

	    else if (action.equalsIgnoreCase("show")){
		System.out.println(game.showBoard());
	    }
	    
	    else {
		System.out.println("invalid command");
		continue;
	    }

	}
	
    }
    
    private void switchMoves(){
	if (currentMove == MARK_X)
	    currentMove = MARK_O;
	else if (currentMove == MARK_O)
	    currentMove = MARK_X;
    }


    public static void main(String[] args){
	TicTacToe game = new TicTacToe();
	TTTClient client = new TTTClient(game);
	client.go();
    }

}
