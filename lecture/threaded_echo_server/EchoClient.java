import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.InetAddress; 

public class EchoClient{
    InetAddress addr = null;
    int port;
 
    EchoClient(InetAddress addr, int port){
    this.addr = addr;
    this.port = port;
    }
 
    public void start() throws Exception{
    //create a socket connection to our EchoServer program
    Socket sock = null;
    boolean done = false;
 
    try{
        sock = new Socket(this.addr,this.port);
    }
    //represents every case where Socket connection fails
    catch(IOException ioe){
        ioe.printStackTrace();
    }
    //create input and output streams to server
    Scanner      in = new Scanner(sock.getInputStream());
    PrintWriter out = new PrintWriter(sock.getOutputStream(),true);
     
    //create Scanner for key input
    Scanner    keyIn = new Scanner(System.in);
    //print initial server announcement
    System.out.println(in.nextLine());
 
    while (!done){
        //Show key input prompt
        System.out.print(">> ");
        //read key input
        String userInput = keyIn.nextLine();
        //send to server
        out.println(userInput);
        //if user typed in "exit" then shut down
        if (userInput.equalsIgnoreCase("exit")){
        done = true;
        System.out.println("Connection closed to server");
        }
        else{
        String serverReply = in.nextLine();
        System.out.println(serverReply);
        }
    }
    sock.close();
    }
    public static void main(String[] args) throws Exception{
    EchoClient client = new EchoClient(InetAddress.getLocalHost(),8189);
    client.start();
 
 
    }
 
 
}