package com.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.model.User;

/**
 * This class has the utility methods to handle JSON parsing.
 * 
 * @author priyankarupani
 *
 */
public class JSONUtils {

	/**
	 * converts the user object to json string
	 * 
	 * @param user
	 * @return
	 */
	public static String toJSON(User user) {
		Gson gson = new Gson();
		return gson.toJson(user);
	}

	/**
	 * converts the json string to user object
	 * 
	 * @param jsonInput
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static User toObject(String jsonInput) throws UnsupportedEncodingException {

		String input = URLDecoder.decode(jsonInput, ApplicationUtils.UTF8);
		Gson gson = new Gson();
		return gson.fromJson(input, User.class);
	}

	/**
	 * checks if the input string is a valid well-formed JSON
	 * 
	 * @param jsonInput
	 * @return
	 */
	public static boolean isValidJSON(String jsonInput) {
		try {
			String input = URLDecoder.decode(jsonInput, ApplicationUtils.UTF8);
			JsonElement element = new JsonParser().parse(input);
			if (element.isJsonNull())
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
