package test.yangliu.zendesksearch.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An class with dynamic attributes
 * Save attributes name as String
 * @author Yang Liu
 *
 */
public class DynamicAttributesClass {
	// The map stores attributes pair of name and value
	private Map<String, Object> attributeMap = new HashMap<String, Object>();

	// Id of each item
	private String _id;

	// Fields list of the object
	// Update when new value added
	private List<String> fieldsList = new ArrayList<String>();

	public void addValue(String key, Object value) {
		// update fields list
		if (!attributeMap.containsKey(key))
			fieldsList.add(key);
		// put into map
		attributeMap.put(key, value);
		// add id
		if (key.equals("_id"))
			_id = value.toString();
	}

	public Object getValue(String key) {
		return attributeMap.get(key);
	}

	public String[] getAttributes() {
		String[] strArr = new String[fieldsList.size()];
		return fieldsList.toArray(strArr);
	}

	public String toString() {
		String str = "";
		str += "_id: " + _id;
		for (String key : attributeMap.keySet()) {
			if (!key.equals("_id"))
				str += "\n" + key + ": " + attributeMap.get(key);
		}
		return str;
	}

	public String getId() {
		return _id;
	}

	public void setId(String id) {
		this._id = id;
	}
}
