package test.yangliu.zendesksearch.controllers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import test.yangliu.zendesksearch.models.DynamicAttributesClass;

/**
 * File IO class
 * Include static method of reading the json file
 * @author Yang Liu
 *
 */
public class FileIO {
	/**
	 * Read the json file
	 * @param fileName The file under root directory of the project
	 * @return Map<String, DynamicAttributesClass> A map of DynamicAttributesClass object with index of its id
	 */
	public static Map<String, DynamicAttributesClass> readJSONFile(String fileName){
		Map<String, DynamicAttributesClass> returnMap = new HashMap<String, DynamicAttributesClass>();
		JSONParser parser = new JSONParser();
		try {
			Object obj;
			obj = parser.parse(new FileReader(fileName));
			JSONArray jsonArray = (JSONArray) obj;
			Iterator iterator = jsonArray.iterator();
			// Build DynamicAttributesClass object of in the json file
			while (iterator.hasNext()) {
				DynamicAttributesClass dac = new DynamicAttributesClass();
				JSONObject jsonObj = (JSONObject)iterator.next();
				for(Object keyObj : jsonObj.keySet()){
					String key = (String)keyObj;
					dac.addValue(key, jsonObj.get(key));
				}
				returnMap.put(dac.getId(), dac);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returnMap;
	}
}
