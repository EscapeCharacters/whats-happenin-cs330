package edu.csbsju.whats.happenin.test;

import junit.framework.TestCase;
import edu.csbsju.whats.happenin.User;

public class TestUser extends TestCase {
	private User user;

	public TestUser(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		user = new User("Andrew Zurn", "awzurn", "pass123", "awzurn@csbsju.edu");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testUser() {
		if(user == null){
			fail("The current state of the user is null.");
		}
	}

	public void testGetUsername() {
		assertEquals("The Username should be awzurn", "awzurn", user.getUsername());
	}

	public void testSetUsername() {
		user.setUsername("azurn91");
		assertEquals("The Username should be azurn91", "azurn91", user.getUsername());
	}

	public void testGetPassword() {
		assertEquals("The password of the user should be pass123", "pass123", user.getPassword());
	}

	public void testSetPassword() {
		user.setPassword("Pass123");
		assertEquals("The password of the user should be Pass123", "Pass123", user.getPassword());
	}
	
	public void testGetSetUserId() {
		user.setUserId(5);
		assertEquals("The UserId of the user should be 5", 5, user.getUserId());
	}

	public void testGetEmailAddress() {
		assertEquals("The email of the user should be awzurn@csbsju.edu", "awzurn@csbsju.edu", user.getEmailAddress());
	}
	
	public void testSetEmailAddress() {
		user.setEmailAddress("azurn@gmail.com");
		assertEquals("The email of the user should be azurn@gmail.com", "azurn@gmail.com", user.getEmailAddress());
	}

	public void testGetName() {
		assertEquals("The name of the user should be Andrew Zurn", "Andrew Zurn", user.getName());
	}

	public void testSetName() {
		user.setName("Andy Zurn");
		assertEquals("The name of the user should be Andy Zurn", "Andy Zurn", user.getName());
	}

}
