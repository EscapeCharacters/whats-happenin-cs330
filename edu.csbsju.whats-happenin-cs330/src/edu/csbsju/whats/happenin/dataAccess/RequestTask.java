package edu.csbsju.whats.happenin.dataAccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;

/**
* Gets information from the internet given a URL
* Create an instance of this class and then call the execute method with the URL in it.
* Then call getJsonData() method to get the JSON data from the class
*/
class RequestTask extends AsyncTask<String, String, String>{

	private String jsonData = "";
	
    @Override
    protected String doInBackground(String... uri) {
		String nextLine;
		URL url = null;
		URLConnection urlConn = null;
		InputStreamReader  inStream = null;
		BufferedReader buff = null;
		try{
			// Create the URL object that points
			// at the default file index.html
			url  = new URL(uri[0]);
			urlConn = url.openConnection();
			inStream = new InputStreamReader( 
					urlConn.getInputStream());
			buff= new BufferedReader(inStream);

			StringBuilder sb = new StringBuilder("");
			// Read and print the lines from uri[0]
			while (true){
				nextLine = buff.readLine();  
				if (nextLine !=null){
					sb.append(nextLine);
				}
				else{
					break;
				} 
			}
			jsonData = sb.toString();
			return jsonData;
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

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
    }

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
}