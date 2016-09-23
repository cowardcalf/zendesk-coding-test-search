package test.yangliu.zendesksearch.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Store the results of searched patterns
 * The pattern includes type, field, search value and result list
 * @author Yang Liu
 *
 */
public class SearchResultMap {
	private Map<String, Map<String, Map<String, List<DynamicAttributesClass>>>> resultsMap = new HashMap<String, Map<String, Map<String, List<DynamicAttributesClass>>>>();
	
	/**
	 * Save results by object type, search field and value, list of results
	 * @param type type of object
	 * @param searchValue search field and value
	 * @param list search results list
	 */
	public void updateResultsMap(String type, SearchValue searchValue, List<DynamicAttributesClass> list){
		Map map = resultsMap;
		// result map
		if(!map.containsKey(type))
			map.put(type, new HashMap<String, Map<String,List<DynamicAttributesClass>>>());
		// type map
		map = (Map)map.get(type);
		if(!map.containsKey(searchValue.getField()))
			map.put(searchValue.getField(), new HashMap<String,List<DynamicAttributesClass>>());
		// field map
		map = (Map)map.get(searchValue.getField());
		if(!map.containsKey(searchValue.getValue()))
			map.put(searchValue.getValue(), list);
	}
	
	public List<DynamicAttributesClass> findInResultMap(String type, SearchValue searchValue){
		List<DynamicAttributesClass> list = null;
		Map map = resultsMap;
		// type map
		map = (Map)map.get(type);
		if(map != null){
			// field map
			map = (Map)map.get(searchValue.getField());
			if(map != null){
				// value map
				list = (List)map.get(searchValue.getValue());
			}
		}
		return list;
	}
}
