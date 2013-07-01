package lesson2.examples.tictactoe.TTT_pipes;

import  lesson2.examples.tictactoe.core.TicTacToe;
import  lesson2.examples.tictactoe.tools.ParserUtils;


import java.io.*;

public class PipedInputStreamDemo {
    
    public static void main(String[] args) throws Exception {
	TicTacToe game = new TicTacToe();

	// create a new Piped input and Output Stream
	PipedInputStream  pin[] = new PipedInputStream[2];
	PipedOutputStream pout[] = new PipedOutputStream[2];
	Player player[] = new Player[2];
	
	for (int i=0;i<=1;++i){
	    pin[i] = new PipedInputStream();	
	    pout[i] = new PipedOutputStream();	
	}
	
	System.out.println("Enter player 1 name: ");
	java.util.Scanner keyIn = new java.util.Scanner(System.in);
	String name = keyIn.nextLine();
	System.out.println("connect player " + name);
	player[0] = new Player(pin[0],pout[0],0);
    
	System.out.println("Enter player 2 name: ");
	name = keyIn.nextLine();
	player[1] = new Player(pin[1],pout[1],1);
	player[0].start(); player[1].start();

	while (true){
	    int current = 0;                       /* current turn's id */
	    int other = 1;                         /* waiting player id */
	    
	    boolean gameOver = false;
	    /* main game loop */
	    while (!gameOver){
		int move = pin[current].read();
		byte movex = (byte) (move % 3); 
		byte movey = (byte) (move / 3);
		int response  = game.placeMark( movex , movey, (byte) current);
		if (response == 0){
		    pout[other].write(move);
		    int tmp = other; other = current; current = tmp;
		}
		else if (response == -1)
		    continue;
		else  //if response == 1
		    gameOver = true;
	    }
	    /* end main game loop */
	}
    }
}

class Player extends Thread{
    private Board board = new Board();
    private PipedInputStream spin = null;
    private PipedOutputStream spout = null;
    private PipedInputStream cpin = null;
    private PipedOutputStream cpout = null;
    private int id;
    private char myMark, otherMark;
    private boolean myTurn;
    Player(PipedInputStream spin, PipedOutputStream spout, int id) throws Exception{
	this.spin = spin;
	this.spout = spout;
	cpin = new PipedInputStream();
	cpout= new PipedOutputStream();
	spin.connect(cpout);
	cpin.connect(spout);
	this.id = id;
	myMark = (id == 0 ? 'X' : 'O');
	otherMark = (myMark == 'O' ? 'X' : 'O');
	myTurn = (myMark == 'X' ? true : false);

    }

    public void run(){
	try{
	    /* main game loop */
	    while (true){
		if (myTurn){
		    int move = 0;
		    cpout.write(move);
		    board.placeMark(move, myMark);
		    board.show();
		}
		else{
		    int otherMove = cpin.read();
		    board.placeMark(otherMove, otherMark);
		    board.show();
		}
		myTurn = !myTurn;
	    }
	}
	catch( IOException e){
	    System.err.println(e);
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

