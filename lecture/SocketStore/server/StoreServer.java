import java.net.*;
import java.io.*;
import java.util.StringTokenizer;

public class StoreServer{
    private ServerSocket ss;
    private static final int PORT = 1111;
    private Store store;

    StoreServer(Store store){
	this.store = store;
    }

    public void go(){
	Socket s;
	System.out.println("Server listening on port " + PORT);
	try{
	    ss = new ServerSocket(PORT);
	}
	catch(IOException ioe){
	    System.err.println("Error starting server on port " + PORT);
	    System.out.println(ioe);
	}

	while (true){
	    try{
		s = ss.accept();
	    }
	    catch(IOException ioe){
		System.out.println("Error getting connection from client");
		System.out.println(ioe);
		continue;
	    }
	    Thread t = new ClientConversationThread(s, store);
	    t.start();
	}
    }

    public static void main(String[] args){
	System.out.println("starting storeserver ...");
	new StoreServer(new Store("autosale", 5)).go();
	
	System.out.println("Server shutting down ...");
    }
}

class ClientConversationThread extends Thread{
    Socket s;
    Store store;

    ClientConversationThread(Socket s, Store store){
	this.s = s;
	this.store = store;
    }
    
    public void run(){
	String input = "";
	BufferedReader in = null;
	PrintWriter out = null;

	try{
	    in = new BufferedReader
		(new InputStreamReader(s.getInputStream()));
	    out = new PrintWriter(s.getOutputStream(),true);
	}
	catch(IOException ioe){
	    System.out.println("error occurred setting up i/o streams");
	    System.out.println(ioe);
	}

	try{
	    while( (input = in.readLine()) != null){
		if (input.equals("exit"))
		    break;
		String reply = handle(input);
		out.println(reply);
	    } 
	}
	catch(IOException ioe){
	    System.out.println("connection closed");
	    return;
	}
    }

    private String handle(String input){
	String reply = "";
	//	String[] tokens = ParserUtils.getTokens(input);
	StringTokenizer st = new StringTokenizer(input);
	String action = st.nextToken();

	if (action.equalsIgnoreCase("list")){
	    String list[] = this.store.getListing();
	    for (int i = 0; i < list.length; ++i){
		reply += list[i] + "\t";
	    }
	}
	if (action.equalsIgnoreCase("purchase")){
	    String item = st.nextToken();
	    int tmpReply = store.purchase(item);
	    if (tmpReply == 1)
		reply = "purchase successful";
	    else
		reply = "none available purchase";
	}
	return(reply);
    }


}


