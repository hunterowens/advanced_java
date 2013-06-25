import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;
 
public class ThreadedEchoServer{
    private int port;
 
    ThreadedEchoServer(int port){
    this.port = port;
    }
 
    public void go() throws Exception{
    ServerSocket ss = new ServerSocket(port);
    System.out.println("Server running on port " + this.port);
    while (true){
        //socket blocks until it receives a socket connection
        Socket        s = ss.accept();
        //now we spawn a thread to handle this client interaction
        Thread t = new Thread(new ChattingServer(s));
        t.start();
        //main thread goes back to 
    }
    }
 
    //create an instance of an EchoServer and start it
    public static void main(String[] args)throws Exception{
    ThreadedEchoServer echo = new ThreadedEchoServer(8189);
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
        Scanner      in = new Scanner(sock.getInputStream());
        PrintWriter out = new PrintWriter(sock.getOutputStream(),true);
        out.println("Welcome to the echo server. Enter exit end");
        while (!done){
        String input = in.nextLine();
        if (input.equalsIgnoreCase("exit")){
            done = true;
        }
        else{
            System.out.println(input);
            out.println(input);
        }
        }
        sock.close();
    }
    catch(Exception e){
        e.printStackTrace();
    }
     
    }
}