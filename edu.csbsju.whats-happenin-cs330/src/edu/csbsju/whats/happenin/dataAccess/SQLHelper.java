package edu.csbsju.whats.happenin.dataAccess;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ParseException;
import android.os.AsyncTask;
import edu.csbsju.whats.happenin.User;

public class SQLHelper {


	public static User getUserByUsername(String username) throws InterruptedException, ExecutionException, TimeoutException{



		User user = null;
		RequestTask task = new RequestTask();
		task.execute("http://www.users.csbsju.edu/~ajthom/cs330/user.php", username);
		task.get(2000, TimeUnit.MILLISECONDS);
		String result = task.getJsonData();
		//result = "[{'user_id':'2','name':'Andrew Zurn','email':'awzurn@csbsju.edu','password':'hello','username':'awzurn'}]";
		InputStream is = null;
		StringBuilder sb = null;
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		List<User> r = new ArrayList<User>();
		try{
			JSONArray jArray = new JSONArray(result);
			JSONObject json_data=null;
			for(int i=0;i<jArray.length(); i++) {
				json_data = jArray.getJSONObject(i);
				user = new User();
				user.setUserId(json_data.getInt("user_id"));
				user.setUsername(json_data.getString("username"));
				user.setEmailAddress(json_data.getString("email"));
				user.setName(json_data.getString("name"));
				user.setPassword(json_data.getString("password"));
			}
		}
		catch(JSONException e1){

		} catch (ParseException e1) {

		}

//		user = new User();
//		user.setUserId(0);
//		user.setPassword("hello");
//		user.setUsername("ajthom");
//		user.setName("Andrew Thom");

		return user;

	}

	private static Context getBaseContext() {
		// TODO Auto-generated method stub
		return null;
	}

}
