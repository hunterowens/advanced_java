package lesson2.examples.tictactoe.tools;

//this is our first class which does not have a main
//method. Its purpose it to serve as a warehouse of 
//related methods that can be accessed from any main
//method.
//This class contains methods useful for writing a parser.
// 1. public static String getKeyInput(){
//      blocks program until user enters zero or
//      more characters followed by a "enter"
//      and returns input to calling program
// 2. public static String[] getTokens(String input){
//      takes String and breaks into tokens defined
//      by one or more white spaces and return array
//      of String each element of which is an individual
//      token
import java.io.*;
import java.util.*;
public class ParserUtils{
        public static String getKeyInput(){
	String input = null;
	try{
	    BufferedReader in = 
		new BufferedReader(new InputStreamReader(System.in));
	    input = in.readLine();

	}
	catch (IOException ioe){
	    System.out.println(ioe);
	}
	return input;
    }

    public static String[] getTokens(String input){
	int i = 0;
	StringTokenizer st = new StringTokenizer(input);
	int numTokens = st.countTokens();
	String[] tokenList = new String[numTokens];
	while (st.hasMoreTokens()){
	    tokenList[i] = st.nextToken();
	    i++;
	}
	return(tokenList);
    }
}

