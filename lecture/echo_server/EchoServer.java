import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;
 
public class EchoServer{
    private int port;
 
    EchoServer(int port){
    this.port = port;
    }
 
    public void start() throws Exception{
    boolean done = false;
    ServerSocket ss = new ServerSocket(port);
    System.out.println("Server running on port " + this.port);
    //socket blocks until it receives a socket connection
    //from a client
    Socket        s = ss.accept();
    System.out.println("connection from " + s);
    Scanner      in = new Scanner(s.getInputStream());
    PrintWriter out = new PrintWriter(s.getOutputStream(),true);
    out.println("Welcome to the echo server. Enter exit end");
    while (!done){
        String input = in.nextLine();
        if (input.equalsIgnoreCase("exit")){
        done = true;
        }
        else{
        out.println(input);
        }
    }
    s.close();
    ss.close();
    }
 
    //create an instance of an EchoServer and start it
    public static void main(String[] args)throws Exception{
    EchoServer echo = new EchoServer(8189);
    echo.start();
    }
 
 
 
}