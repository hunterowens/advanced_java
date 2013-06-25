import java.io.*;
import java.net.*;

public class StoreClient2{
    int port;
    String host;
    java.util.Scanner scan = new java.util.Scanner(System.in);

    public static void main(String[] args) throws Exception{
	new StoreClient2("localhost", 1111).go();
    }

    StoreClient2(String host, int port){
	this.host = host;
	this.port = port;
    }

    public void go() throws Exception{
	//create a socket connection to server
	Socket s = new Socket(this.host, this.port);
	BufferedReader serverIn = new BufferedReader
	    (new InputStreamReader(s.getInputStream()));
	PrintWriter serverOut = new PrintWriter
	    (s.getOutputStream(), true);
	//loop to get user input
	while (true){
	    System.out.print("STORE >> ");
	    String input = scan.nextLine();
	    if (input.equals("exit")) break;
	    //this is a "dumb client", it simply passes
	    //passes user input to the server in raw form
	    serverOut.println(input);
	    //this is still a "dumb client" so it only prints
	    //verbatim what the server sends it.
	    String reply = serverIn.readLine();
	    System.out.println(reply);
	}
	serverOut.close();
	serverIn.close();
	s.close();
    }
}

