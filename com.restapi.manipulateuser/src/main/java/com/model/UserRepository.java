package com.model;

import java.util.HashMap;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * This class is the in memory database which uses a hashMap.
 * 
 * @author priyankarupani
 *
 */
//@Service annotation is used in your service layer and annotates 
//classes that perform service tasks, often you don't use it but in many case you use this annotation to represent a best practice. 
@Service
public class UserRepository {

	private HashMap<String, User> userMap = new HashMap<String, User>();

	/**
	 * adds a user to the in memory hashMap
	 * 
	 * @param id
	 * @param user
	 */
	public void addUser(String id, User user) {
		if (ObjectUtils.allNotNull(id, user)) {
			userMap.put(id, user);
		}
	}

	/**
	 * gets a user based on the id from hashMap, returns null if not present.
	 * 
	 * @param id
	 * @return
	 */
	public User getUser(String id) {
		User user = null;
		if (ObjectUtils.allNotNull(id)) {
			user = userMap.get(id);
		}
		return user;
	}

	/**
	 * delete the user from the hashMap using the id.
	 * 
	 * @param id
	 */

	public void deleteUserByID(String id) {
		if (ObjectUtils.allNotNull(id)) {
			userMap.remove(id);
		}
	}

	/**
	 * deletes all users from the hashMap.
	 */
	public void deleteAllUsers() {
		userMap.clear();
	}

	/**
	 * updates the user in the hashMap.
	 * 
	 * @param id
	 * @param currentUser
	 * @param userObj
	 */
	public void updateUser(String id, User currentUser, User userObj) {

		if (ObjectUtils.allNotNull(id, currentUser, userObj)) {
			if (ObjectUtils.allNotNull(userObj.getName()))
				currentUser.setName(userObj.getName());
			if (ObjectUtils.allNotNull(userObj.getAge()))
				currentUser.setAge(userObj.getAge());
			if (ObjectUtils.allNotNull(userObj.getGender()))
				currentUser.setGender(userObj.getGender());
			userMap.put(id, currentUser);

		}

	}
}
