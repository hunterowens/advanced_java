import java.rmi.Remote;
import java.rmi.RemoteException;

interface TicTacToeInterface extends Remote{
    public int register(String host ) throws RemoteException;
    public void setPlayer(TTTClientInterface client) throws RemoteException;
    public void reset() throws RemoteException;
    public boolean placeMark(byte row, byte col, int playerID) throws RemoteException;
    public String showBoard() throws RemoteException;
    public boolean checkWinner() throws RemoteException;
}
    
	
