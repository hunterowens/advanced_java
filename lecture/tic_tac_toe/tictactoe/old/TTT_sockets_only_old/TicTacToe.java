import java.util.*;

public class TicTacToe{
    private byte[][] board;
    private static final int nrow = 3, ncol = 3;
    public static final byte X = 0;
    public static final byte O = 1;
    public static final byte BLANK = -1;

    /** initilialize a 3x3 board to all blanks **/
    public TicTacToe(){
	//initialize board with BLANK's!
	board = new byte[][]{
	    {BLANK,BLANK,BLANK},
	    {BLANK,BLANK,BLANK},
	    {BLANK,BLANK,BLANK}};
    }

    /** rest the board to all blanks **/
    public void reset(){
	for (byte[] row : board)
	    for (byte square : row)
		square = BLANK;
    }
	

    /** Place a mark on the board **/
    public boolean placeMark(byte row, byte col, byte markType){
	boolean markOK = checkMark(row,col);
	if (markOK){
	    board[row][col] = markType;
	    return(true);
	}
	else
	    return(false);
    }

    /** return a formatted copy of the board at current state as String **/
    public String showBoard(){
	StringBuffer sb = new StringBuffer();
	for (byte[] row : board){
	    for (byte square : row){
		if (square == X){
		    sb.append("X\t");
		}
		else if (square == O){
		    sb.append("O\t");
		}
		else 
		    sb.append("-\t");
	    }
	    sb.append("\n");
	}
	return sb.toString();
    }

    /** brute force check to return whether there is a winner **/
    public boolean winner(){
	byte[] aRow = new byte[ncol];
	byte[] aCol = new byte[nrow];
	int i,j;

	for (i = 0; i < nrow; i++){
	    for (j = 0; j < ncol; j++){
		aRow[j] = board[i][j];
	    }
	    boolean isSame = checkSame(aRow);
	    if (isSame) return(true);
	}

	for (j = 0; j < ncol; j++){
	    for (i = 0; i < nrow; i++){
		aCol[i] = board[i][j];
	    }
	    boolean isSame = checkSame(aCol);
	    if (isSame) return(true);
	}

	//check diagonal
	for (i = 0; i < nrow; i++){
	    aRow[i] = board[i][i];
	}
	boolean isSame = checkSame(aRow);
	if (isSame) return(true);

	return(false);
    }	

    /** check whether two rows or columns are same **/
    private boolean checkSame(byte[] arr){
	for (int i=0;i<arr.length-1;i++){
	    if ( (arr[i] != arr[i+1]) ||
		 (arr[i] == BLANK) )
		return(false);
	}
	return(true);
    }

    /** check whether mark placement is valid **/
    private boolean checkMark(byte row, byte col){
	boolean acceptMark;

	if ( (row >= nrow || row < 0) ||
	     (col >= ncol || col < 0)){
	    return(false);
	}
	else if ( board[row][col] != BLANK)
	    return(false);
	else
	    return(true);
    }
	    
    public static void main(String[] args){
	TicTacToe game = new TicTacToe();
	ArrayList<Integer> locs = new ArrayList<Integer>(9);
	for (int i = 0; i < 9; ++i) locs.add(i);
	byte player = X;
	String symbol = "X";
	while (!game.winner()){
	    System.out.println(game.showBoard());
	    symbol = (player == X) ? "X" : "O";
	    System.out.println(symbol + " turn ...");

	    try{
		Thread.sleep(2000);
	    }
	    catch(InterruptedException ie){
		System.out.println(ie);
	    }
		
	    Collections.shuffle(locs);
	    int move = locs.get(0);
	    System.out.println("move: " + move);
	    locs.remove(0);
	    System.out.println(symbol + " chooses square " + "[" + (move % 3) + "," + (move/3) + "]\n\n");
	    game.placeMark( (byte) (move % 3), (byte) (move/3), player);
	    player = (player == X) ? O : X;
	}
	System.out.println(symbol + " WINS ...");
	System.out.println(game.showBoard());
    }
}




