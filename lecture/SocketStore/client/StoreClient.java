import java.io.*;
import java.net.*;

public class StoreClient{
    int port;
    String host;

    public static void main(String[] args){
	new StoreClient("localhost", 1111).go();
    }

    StoreClient(String host, int port){
	this.host = host;
	this.port = port;
    }

    public void go(){
	Socket s = null;
	BufferedReader in = null;
	PrintWriter out = null;

	try{
	    s = new Socket(host, port);
	}
	catch(IOException ioe){
	    System.out.println("error connecting to host " + host);
	    System.out.println(ioe);
	}

	try{
	    in = new BufferedReader
		(new InputStreamReader(s.getInputStream()));
	    out = new PrintWriter(s.getOutputStream(),true);
	}	
	catch(IOException ioe){
	    System.out.println("error establishing streams");
	    System.out.println(ioe);
	}

	out.println("list");
	try{
	    System.out.println(in.readLine());
	}
	catch(Exception ioe){
	    System.out.println("error reading from server");
	    System.out.println(ioe);
	}
	finally{
	    try{
		s.close();
		in.close();
		out.close();
	    }
	    catch(IOException ioe){
	    }
	}
    }
}

