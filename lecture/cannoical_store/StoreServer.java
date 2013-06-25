import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;
 
public class StoreServer{
    private int port;
 
    StoreServer(int port){
    this.port = port;
    }
 
    public void go(){
    ServerSocket ss = new ServerSocket(port);//IOException
    System.out.println("Server running on port " + this.port);
    while (true){
        //socket blocks until it receives a socket connection
        Socket        s = ss.accept();//IOException
        //now we spawn a thread to handle this client interaction
        Thread t = new Thread(new ChattingServer(s));
        t.start();
        //main thread goes back to 
    }
    }
 
    //create an instance of an StoreServer and start it
    public static void main(String[] args){
    StoreServer echo = new StoreServer(8189);
    echo.go();
    }
}
 
//this is the Thread class that will carry out the
//conversation with any one client at a time. Since it
//implements Runnable we can have many simultaneous copies
class ChattingServer implements Runnable{
    Socket sock = null;
 
    ChattingServer(Socket s){
    this.sock = s;
    }
 
    public void run(){
    boolean done = false;
    System.out.println("connection from " + sock);
    try{
        //getInputStream() and getOutputStream() throw IOException
        Scanner      in = new Scanner(sock.getInputStream());
        PrintWriter out = new PrintWriter(sock.getOutputStream(),true);
        out.println("Welcome to the Store server. Enter exit end");
        while (!done){
        String input = in.nextLine();
        if (input.equalsIgnoreCase("exit")){
            done = true;
        }
        else{
            // here we want to add list and buy methods
            //which operate on Store object
            if (input.equals("list")){
            String output = store.list();
            out.println(output);
            }
            else if (input.startsWith("buy")){
            //tokenize arguments
            int ret = store.buy(arg1,arg2);
            }
        }
        }
        sock.close();//IOException
    }
    catch(Exception e){
        e.printStackTrace();
    }
     
    }
}