package edu.csbsju.whats.happenin.dataAccess;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import edu.csbsju.whats.happenin.Comment;
import edu.csbsju.whats.happenin.Happenin;
import edu.csbsju.whats.happenin.Rating;
import edu.csbsju.whats.happenin.User;

public class SQLHelper {


	public static User getUserByUsername(String username) throws InterruptedException, ExecutionException, TimeoutException{
		User user = null;
		RequestTask task = new RequestTask();
		task.execute("http://www.users.csbsju.edu/~ajthom/cs330/user.php?name="+username);
		task.get(2000, TimeUnit.MILLISECONDS);
		String result = task.getJsonData();
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
		if(user == null){
			user = new User();
			user.setStatus(User.Status.EMPTY);
		}
		return user;

	}
	
	public static User getUserById(int id) {
		User user = null;
		RequestTask task = new RequestTask();
		task.execute("http://www.users.csbsju.edu/~ajthom/cs330/userById.php?id="+id);
		try {
			task.get(2000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = task.getJsonData();
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
		return user;

	}

	public static ArrayList<Happenin> getHappenins() throws InterruptedException, ExecutionException, TimeoutException{
		Happenin happ = null;
		ArrayList<Happenin> happenins = null;
		RequestTask task = new RequestTask();
		task.execute("http://www.users.csbsju.edu/~ajthom/cs330/happenings.php");
		task.get(2000, TimeUnit.MILLISECONDS);
		String result = task.getJsonData();
		try{
			happenins = new ArrayList<Happenin>();
			JSONArray jArray = new JSONArray(result);
			JSONObject json_data=null;
			for(int i=0;i<jArray.length(); i++) {
				json_data = jArray.getJSONObject(i);
				happ = new Happenin();
				happ.setName(json_data.getString("name"));
				happ.setDescription(json_data.getString("description"));
				happ.setLocation(json_data.getString("location"));
				happ.setId(json_data.getInt("id"));
				happenins.add(happ);
			}
		}
		catch(JSONException e1){

		} catch (ParseException e1) {

		}
		return happenins;
	}
	
	public static Happenin getHappeninById(int happId){
		Happenin happ = null;
		RequestTask task = new RequestTask();
		task.execute("http://www.users.csbsju.edu/~ajthom/cs330/happenin.php?id="+happId);
		try {
			task.get(2000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		String result = task.getJsonData();
		try{
			JSONArray jArray = new JSONArray(result);
			JSONObject json_data=null;
			for(int i=0;i<jArray.length(); i++) {
				json_data = jArray.getJSONObject(i);
				happ = new Happenin();
				happ.setName(json_data.getString("name"));
				happ.setDescription(json_data.getString("description"));
				happ.setLocation(json_data.getString("location"));
				happ.setId(json_data.getInt("id"));
				happ.setStartTime(parseMySqlDate(json_data.getString("startDateTime")));
				happ.setEndTime(parseMySqlDate(json_data.getString("endDateTime")));
			}
		}
		catch(JSONException e1){

		} catch (ParseException e1) {

		}
		
		if(happ == null){
			happ = new Happenin();
			happ.setStatus(Happenin.Status.EMPTY);
		}
		
		return happ;
	}
	
	public static ArrayList<Comment> getCommentsByHappeninId(int id) throws InterruptedException, ExecutionException, TimeoutException{
		Comment comment = null;
		ArrayList<Comment> comments = null;
		RequestTask task = new RequestTask();
		task.execute("http://www.users.csbsju.edu/~ajthom/cs330/comment.php?id="+id);
		task.get(2000, TimeUnit.MILLISECONDS);
		String result = task.getJsonData();
		try{
			comments = new ArrayList<Comment>();
			JSONArray jArray = new JSONArray(result);
			JSONObject json_data=null;
			for(int i=0;i<jArray.length(); i++) {
				json_data = jArray.getJSONObject(i);
				comment = new Comment();
				comment.setUserId(json_data.getInt("userId"));
				comment.setComment(json_data.getString("comment"));
				comment.setCommentId(json_data.getInt("id"));
				comment.setDateTime(parseMySqlDate(json_data.getString("postTime")));
				comments.add(comment);
			}
		}
		catch(JSONException e1){

		} catch (ParseException e1) {

		}
		if (comments.size() == 0){
			Comment c = new Comment();
			c.setComment("No comments yet");
			comments.add(c);
		}
		return comments;
	}
	
	public static ArrayList<Rating> getRatingsByHappeninId(int id) {
		Rating rating = null;
		ArrayList<Rating> ratings = null;
		RequestTask task = new RequestTask();
		task.execute("http://www.users.csbsju.edu/~ajthom/cs330/ratingsByHappenin.php?happId="+id);
		try {
			task.get(2000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = task.getJsonData();
		try{
			ratings = new ArrayList<Rating>();
			JSONArray jArray = new JSONArray(result);
			JSONObject json_data=null;
			for(int i=0;i<jArray.length(); i++) {
				json_data = jArray.getJSONObject(i);
				rating = new Rating();
				rating.setHappId(json_data.getInt("happId"));
				rating.setRating(json_data.getInt("rating"));
				rating.setId(json_data.getInt("id"));
				rating.setRateTime(parseMySqlDate(json_data.getString("rateTime")));
				ratings.add(rating);
			}
		}
		catch(JSONException e1){

		} catch (ParseException e1) {

		}
		return ratings;
	}
	
	public static void insertComment(int userId, int happId, String comment){
		RequestTask task = new RequestTask();
		String fixedComment = comment.replaceAll(" ", "+");
		task.execute("http://www.users.csbsju.edu/~ajthom/cs330/insertComment.php?happid="+happId+"&comment="+fixedComment+"&userId="+userId);
	}
	
	public static void createUser(String username, String password, String email, String name){
		RequestTask task = new RequestTask();
		String fixedName = name.replaceAll(" ", "+");
		task.execute("http://www.users.csbsju.edu/~ajthom/cs330/userCreate.php?name="+fixedName+"&password="+password+"&email="+email+"&username="+username);
	}
	
	public static DateTime parseMySqlDate(String dateString){
		String[] split1 = dateString.split(" ");//Separates the Date and Time
		String date = split1[0];
		String time = split1[1];
		String[] dateSplit = date.split("-");
		String[] timeSplit = time.split(":");
		int year = Integer.parseInt(dateSplit[0]);
		int month = Integer.parseInt(dateSplit[1]);
		int day = Integer.parseInt(dateSplit[2]);
		int hour = Integer.parseInt(timeSplit[0]);
		int minute = Integer.parseInt(timeSplit[1]);
		int second = Integer.parseInt(timeSplit[2]);
		DateTime dt = new DateTime(year, month, day, hour, minute, second);
		return dt;
	}


}
