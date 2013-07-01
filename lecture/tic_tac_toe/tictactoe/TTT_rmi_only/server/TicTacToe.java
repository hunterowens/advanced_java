import java.rmi.*;
import java.rmi.server.*;
import java.net.*;
import java.io.*;
import java.security.Permission;

public class TicTacToe extends UnicastRemoteObject implements TicTacToeInterface{
    private static final int numPlayers = 2;
    private byte[][] board;
    private Player[] player = new Player[numPlayers];
    private static int rows = 3, cols = 3;
    private static byte MARK_X = 0;
    private static byte MARK_O = 1;
    private static byte MARK_NULL = -1;
    private int playerNum= 0;
    private int currentPlayer = 0;
    private int waitingPlayer = 1;
    private TTTClientInterface client[] = new TTTClientInterface[2];
    private static int count = 0;

    public TicTacToe() throws RemoteException{
	//	super();
	//initialize board with -1's!

	board = new byte[rows][cols];
	for (int i=0;i<rows;i++)
	    for (int j=0;j<cols;j++)
		board[i][j] = -1;
    }
    
    //register client with game
    //only two players may connect
    //failure is indicated by -1
    public int register(String client) throws RemoteException{
	if (playerNum > 1)
	    return (-1);
	else{
	    player[playerNum] = new Player(playerNum);
	    playerNum++;
	}
	return(playerNum-1);
    }

    //called by client to pass instance of remote self to server for callback
    //method otherPlayerMoved
    public void setPlayer(TTTClientInterface client) throws RemoteException{
	this.client[count++] = client;
    }

    //clear the board
    public void reset() throws RemoteException{
	for (int i=0;i<rows;i++)
	    for (int j=0;j<cols;j++)
		board[i][j] = -1;
    }
	
    //set a mark on the board
    public boolean placeMark(byte row, byte col, int playerID) throws RemoteException{
	if (playerID != currentPlayer)
	    return(false);

	boolean markOK = checkMark(row,col);
	if (markOK){
	    byte markType = (byte) (currentPlayer == 0 ? 0 : 1);
	    board[row][col] = markType;
	    player[currentPlayer].send(client[waitingPlayer]);
	    swapPlayers();
	    return(true);
	}
	else
	    return(false);
    }

	    
    public String showBoard() throws RemoteException{
	StringBuffer s = new StringBuffer();

	for (int i = 0 ; i < rows ; i++){
	    for (int j = 0 ; j < cols ; j++){
		if (board[i][j] == 0){
		    s.append("X\t");
		}
		else if (board[i][j] == 1){
		    s.append("O\t");
		}
		else 
		    s.append("-\t");
	    }
	    s.append("\n");
	}
	return(s.toString());
    }

    public boolean checkWinner() throws RemoteException{
	byte[] aRow = new byte[cols];
	byte[] aCol = new byte[rows];
	int i,j;

	for (i = 0; i < rows; i++){
	    for (j = 0; j < cols; j++){
		aRow[j] = board[i][j];
	    }
	    boolean isSame = checkSame(aRow);
	    if (isSame) return(true);
	}

	for (j = 0; j < cols; j++){
	    for (i = 0; i < rows; i++){
		aCol[i] = board[i][j];
	    }
	    boolean isSame = checkSame(aCol);
	    if (isSame) return(true);
	}

	//check diagonal
	for (i = 0; i < rows; i++){
	    aRow[i] = board[i][i];
	}
	boolean isSame = checkSame(aRow);
	if (isSame) return(true);

	return(false);
    }	

    private boolean checkSame(byte[] arr){
	for (int i=0;i<arr.length-1;i++){
	    if ( (arr[i] != arr[i+1]) ||
		 (arr[i] == MARK_NULL) )
		return(false);
	}
	return(true);
    }

    private boolean checkMark(byte row, byte col){
	boolean acceptMark;

	if ( (row >= rows || row < 0) ||
	     (col >= cols || col < 0)){
	    return(false);
	}
	else if ( board[row][col] != -1)
	    return(false);
	else
	    return(true);
    }

    private void swapPlayers(){
	int tmp = currentPlayer;
	currentPlayer = waitingPlayer;
	waitingPlayer = tmp;
    }

    public static void main(String[] args) throws Exception{
    }
}


class Player{
    public int id;

    Player(int id){
	this.id = id;
    }

    public void send(TTTClientInterface client) throws RemoteException{
	client.otherPlayerMoved( 1 );
    }
    

}

