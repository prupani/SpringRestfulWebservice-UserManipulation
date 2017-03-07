package com.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.User;
import com.model.UserRepository;
import com.utils.ApplicationUtils;
import com.utils.JSONUtils;

/**
 * This class is the controller for the application. It handles all CRUD
 * requests.
 * 
 * @author priyankarupani
 *
 */

//@RestController is a convenience annotation that does nothing more than adding the @Controller and @ResponseBody annotations
//@Controller is used to mark classes as Spring MVC Controller.

@RestController

public class UserController {

	
	UserRepository usrRepo;

	@Autowired
	public void setUsrRepo(UserRepository usrRepo) {
		this.usrRepo = usrRepo;
	}

	//@RequestMapping annotation that specify what HTTP Request is handled by the controller and by its method.
	@RequestMapping("/")
	public String index() {
		return "User WebService up";
	}

	/**
	 * POST request to create a user from the input JSON string.
	 * 
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = ApplicationUtils.CREATE_USER, method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestBody String user) throws UnsupportedEncodingException {

		// generating random UID for every user and storing it as key in the
		// database.
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		try {
			// If input JSON string is null or is not valid, throw HTTP error.
			if (!ObjectUtils.allNotNull(user) || !JSONUtils.isValidJSON(user)) {
				return new ResponseEntity<String>("Please enter a valid JSON input", HttpStatus.BAD_REQUEST);
			}

			// Convert JSON to Java Object
			User userObj = JSONUtils.toObject(user);
			// add the User object in the database.
			usrRepo.addUser(id, userObj);
		} catch (Exception e) {
			return new ResponseEntity<String>("Error creating the user", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(id, HttpStatus.OK);
	}

	/**
	 * GET request which gets a user based on input parameter ID
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = ApplicationUtils.GET_USER, method = RequestMethod.GET)
	public ResponseEntity<String> getUser(@RequestParam(ApplicationUtils.ID) String id) {

		String json = null;
		try {
			// check if ID is null
			if (!ObjectUtils.allNotNull(id)) {
				return new ResponseEntity<String>("The input ID is null", HttpStatus.BAD_REQUEST);
			}

			//
			String decodeID = URLDecoder.decode(id, ApplicationUtils.UTF8);

			// get the user corresponding to the ID from the database.
			User user = usrRepo.getUser(decodeID);

			// if user is not present then return HTTP error.
			if (!ObjectUtils.allNotNull(user)) {
				return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
			}

			// Convert object to JSON string
			json = JSONUtils.toJSON(user);
		} catch (Exception e) {
			return new ResponseEntity<String>("Error getting the user", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	/**
	 * DELETE request to delete a user based on input ID.
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = ApplicationUtils.DELETE_USER, method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@RequestParam(ApplicationUtils.ID) String id) {
		try {
			// check if ID is null
			if (!ObjectUtils.allNotNull(id)) {
				return new ResponseEntity<String>("The input ID is null ", HttpStatus.BAD_REQUEST);
			}

			// get the user corresponding to the ID from the database.
			User user = usrRepo.getUser(id);
			// if user is not present then return HTTP error.
			if (!ObjectUtils.allNotNull(user)) {
				return new ResponseEntity<String>("User not found ", HttpStatus.NOT_FOUND);
			}

			// if user is found, then delete it.
			usrRepo.deleteUserByID(id);
		} catch (Exception e) {
			return new ResponseEntity<String>("Error deleting the user ", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("The user is deleted ", HttpStatus.OK);
	}

	/**
	 * Delete request to delete all the users from the database.
	 * 
	 * @return
	 */
	@RequestMapping(value = ApplicationUtils.DELETEALL_USERS, method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteAllUsers() {
		try {
			usrRepo.deleteAllUsers();
		} catch (Exception e) {
			return new ResponseEntity<String>("Error deleting the user ", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Deleted all Users ", HttpStatus.OK);
	}

	/**
	 * UPDATE request to update a user.
	 * 
	 * @param id
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = ApplicationUtils.UPDATE_USER, method = RequestMethod.PUT)
	public ResponseEntity<String> updateUser(@RequestParam(ApplicationUtils.ID) String id, @RequestBody String user)
			throws UnsupportedEncodingException {

		User currentUser = null;
		try {
			// check if the input user is null
			if (!ObjectUtils.allNotNull(user) || !JSONUtils.isValidJSON(user)) {
				return new ResponseEntity<String>("Please enter a valid JSON input", HttpStatus.BAD_REQUEST);
			}
			// Convert JSON to Java Object
			User userObj = JSONUtils.toObject(user);
			currentUser = usrRepo.getUser(id);
			//
			if (!ObjectUtils.allNotNull(currentUser)) {
				return new ResponseEntity<String>("User not found, please enter a valid ID", HttpStatus.NOT_FOUND);
			}
			//
			usrRepo.updateUser(id, currentUser, userObj);

		} catch (Exception e) {
			return new ResponseEntity<String>("Error updating the user", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("The user is updated ", HttpStatus.OK);

	}

}