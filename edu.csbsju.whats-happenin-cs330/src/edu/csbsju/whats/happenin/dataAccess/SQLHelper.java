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

	/**
	 * Gets a user by a username
	 * @param username The username of the user you want to return
	 * @return The user who is being returned
	 * @throws InterruptedException Thrown when there is a problem with the internet connection
	 * @throws ExecutionException Thrown when there is a problem with the internet connection
	 * @throws TimeoutException Thrown when there is a problem with the internet connection
	 */
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
	
	/**
	 * Gets a user given an ID number
	 * @param userId The user ID of the user desired
	 * @return The User object for the USER id, with status EMPTY if the user ID doesn't exist
	 */
	public static User getUserById(int userId) {
		User user = null;
		RequestTask task = new RequestTask();
		task.execute("http://www.users.csbsju.edu/~ajthom/cs330/userById.php?id="+userId);
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

	/**
	 * Gets the list of happenins
	 * @return An ArrayList of upcoming Happenins
	 * @throws InterruptedException Thrown when there is a problem with the internet connection
	 * @throws ExecutionException Thrown when there is a problem with the internet connection
	 * @throws TimeoutException Thrown when there is a problem with the internet connection
	 */
	public static ArrayList<Happenin> getHappenins() throws InterruptedException, ExecutionException, TimeoutException{
		Happenin happ = null;
		ArrayList<Happenin> happenins = null;
		RequestTask task = new RequestTask();
		task.execute("http://www.users.csbsju.edu/~ajthom/cs330/happenings.php");
		task.get(2000, TimeUnit.MILLISECONDS);
		String result = task.getJsonData();
		try{
			happenins = new ArrayList<Happenin>();
			JSONArray jsonArray = new JSONArray(result);
			JSONObject json_data=null;
			for(int i=0;i<jsonArray.length(); i++) {
				json_data = jsonArray.getJSONObject(i);
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
	
	/**
	 * Gets a happenin given a happenin ID
	 * @param happeninId The ID of the happenin desired
	 * @return The Happenin corresponding to the ID given, or a Happenin with Status
	 * EMPTY if the 
	 */
	public static Happenin getHappeninById(int happeninId){
		Happenin happenin = null;
		RequestTask task = new RequestTask();
		task.execute("http://www.users.csbsju.edu/~ajthom/cs330/happenin.php?id="+happeninId);
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
			JSONArray jsonArray = new JSONArray(result);
			JSONObject json_data=null;
			for(int i=0;i<jsonArray.length(); i++) {
				json_data = jsonArray.getJSONObject(i);
				happenin = new Happenin();
				happenin.setName(json_data.getString("name"));
				happenin.setDescription(json_data.getString("description"));
				happenin.setLocation(json_data.getString("location"));
				happenin.setId(json_data.getInt("id"));
				happenin.setStartTime(parseMySqlDate(json_data.getString("startDateTime")));
				happenin.setEndTime(parseMySqlDate(json_data.getString("endDateTime")));
			}
		}
		catch(JSONException e1){

		} catch (ParseException e1) {

		}
		
		if(happenin == null){
			happenin = new Happenin();
			happenin.setStatus(Happenin.Status.EMPTY);
		}
		
		return happenin;
	}
	
	/** Gets a list of comments for a given happenin ID */
	public static ArrayList<Comment> getCommentsByHappeninId(int id) throws InterruptedException, ExecutionException, TimeoutException{
		Comment comment = null;
		ArrayList<Comment> comments = null;
		RequestTask task = new RequestTask();
		task.execute("http://www.users.csbsju.edu/~ajthom/cs330/comment.php?id="+id);
		task.get(2000, TimeUnit.MILLISECONDS);
		String result = task.getJsonData();
		try{
			comments = new ArrayList<Comment>();
			JSONArray jsonArray = new JSONArray(result);
			JSONObject json_data=null;
			for(int i=0;i<jsonArray.length(); i++) {
				json_data = jsonArray.getJSONObject(i);
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
			comment = new Comment();
			comment.setComment("No comments yet");
			comment.setUserId(0);
			comments.add(comment);
		}
		return comments;
	}
	
	/** Gets an ArrayList of Ratings for a given happenin ID */
	public static ArrayList<Rating> getRatingsByHappeninId(int happeninId) {
		Rating rating = null;
		ArrayList<Rating> ratings = null;
		RequestTask task = new RequestTask();
		task.execute("http://www.users.csbsju.edu/~ajthom/cs330/ratingsByHappenin.php?happId="+happeninId);
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
			ratings = new ArrayList<Rating>();
			JSONArray jsonArray = new JSONArray(result);
			JSONObject json_data=null;
			for(int i=0;i<jsonArray.length(); i++) {
				json_data = jsonArray.getJSONObject(i);
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
	
	/** Inserts a comment */
	public static void insertComment(int userId, int happeninId, String comment){
		RequestTask task = new RequestTask();
		String fixedComment = comment.replaceAll(" ", "+");
		task.execute("http://www.users.csbsju.edu/~ajthom/cs330/insertComment.php?happid="+happeninId+"&comment="+fixedComment+"&userid="+userId);
	}
	
	/** Inserts a new user */
	public static void createUser(String username, String password, String email, String name){
		RequestTask task = new RequestTask();
		String fixedName = name.replaceAll(" ", "+");
		task.execute("http://www.users.csbsju.edu/~ajthom/cs330/userCreate.php?name="+fixedName+"&password="+password+"&email="+email+"&username="+username);
	}
	
	/** Inserts a new rating 
	 * @throws TimeoutException 
	 * @throws ExecutionException 
	 * @throws InterruptedException */
	public static void createRating(int rating, int userId, int happeninId) throws InterruptedException, ExecutionException, TimeoutException{
		RequestTask task = new RequestTask();
		task.execute("http://www.users.csbsju.edu/~ajthom/cs330/insertRating.php?userid="+userId+"&happid="+happeninId+"&rating="+rating);
		task.get(2000, TimeUnit.MILLISECONDS);
	}
	
	public static void insertHappenin(String name, String description, DateTime startDateTime, DateTime endDateTime, String location) throws InterruptedException, ExecutionException, TimeoutException{
		String happeninName = name.replaceAll("'", "''");
		happeninName = happeninName.replaceAll(" ", "+");
		String happeninDescription = description.replaceAll("'", "''");
		happeninDescription = happeninDescription.replaceAll(" ", "+");
		String happeninLocation = location.replaceAll("'", "''");
		happeninLocation = happeninLocation.replaceAll(" ", "+");
		
		int startYear = startDateTime.year().get();
		int startMonth = startDateTime.monthOfYear().get();
		int startDay = startDateTime.dayOfMonth().get();
		int startHour = startDateTime.hourOfDay().get();
		int startMinute = startDateTime.minuteOfHour().get();
		int startSecond = startDateTime.secondOfMinute().get();
		String startDate = ""+startYear+"-"+startMonth+"-"+startDay+"+"+startHour+":"+startMinute+":"+startSecond;
		
		int endYear = endDateTime.year().get();
		int endMonth = endDateTime.monthOfYear().get();
		int endDay = endDateTime.dayOfMonth().get();
		int endHour = endDateTime.hourOfDay().get();
		int endMinute = endDateTime.minuteOfHour().get();
		int endSecond = endDateTime.secondOfMinute().get();
		String endDate = ""+endYear+"-"+endMonth+"-"+endDay+"+"+endHour+":"+endMinute+":"+endSecond;
		
		RequestTask task = new RequestTask();
		task.execute("http://www.users.csbsju.edu/~ajthom/cs330/insertHappenin.php?name="+happeninName+"&description="+happeninDescription+"&startDateTime="+startDate+"&endDateTime="+endDate+"&location="+happeninLocation+"");
		task.get(2000, TimeUnit.MILLISECONDS);
	}
	
	public static void updatePassword(int userId, String newPassword) throws InterruptedException, ExecutionException, TimeoutException{
		RequestTask task = new RequestTask();
		task.execute("http://www.users.csbsju.edu/~ajthom/cs330/updatePassword.php?userid="+userId+"&password="+newPassword);
		task.get(2000, TimeUnit.MILLISECONDS);
	}
	
	/** This parses the MySQL DATETIME string into a DateTime object in Java */
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
