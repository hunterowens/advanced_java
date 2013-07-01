import java.rmi.Remote;
import java.rmi.RemoteException;

interface TTTClientInterface extends Remote{
    public void showBoard(byte[][] board) throws RemoteException;
    public void otherPlayerMoved(int move) throws RemoteException;
}
	
