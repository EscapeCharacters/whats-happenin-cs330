package edu.csbsju.whats.happenin.dataAccess;

import java.net.*;
import java.io.*;
public class WebSiteReader {
	public static String websiteLoad (String uri){
		String nextLine;
		URL url = null;
		URLConnection urlConn = null;
		InputStreamReader  inStream = null;
		BufferedReader buff = null;
		try{
			// Create the URL object that points
			// at the default file index.html
			url  = new URL(uri );
			urlConn = url.openConnection();
			inStream = new InputStreamReader( 
					urlConn.getInputStream());
			buff= new BufferedReader(inStream);

			StringBuilder sb = new StringBuilder("");
			// Read and print the lines from index.html
			while (true){
				nextLine =buff.readLine();  
				if (nextLine !=null){
					sb.append(nextLine);
				}
				else{
					break;
				} 
			}
			return sb.toString();
		} catch(MalformedURLException e){
			System.out.println("Please check the URL:" + 
					e.toString() );
			return "";
		} catch(IOException  e1){
			System.out.println("Can't read  from the Internet: "+ 
					e1.toString() ); 
			return "";
		}
	}
}