package test.yangliu.zendesksearch;

import java.util.ArrayList;

import org.junit.Test;

import test.yangliu.zendesksearch.models.DynamicAttributesClass;
import test.yangliu.zendesksearch.models.SearchResultMap;
import test.yangliu.zendesksearch.models.SearchValue;
import junit.framework.TestCase;

public class SearchResultMapTest extends TestCase{
	
	@Test
    public static void testAddAndGetResult() {
       
		ArrayList<DynamicAttributesClass> list = new ArrayList<DynamicAttributesClass>();
		
		DynamicAttributesClass dac1 = new DynamicAttributesClass();
		DynamicAttributesClass dac2 = new DynamicAttributesClass();
		DynamicAttributesClass dac3 = new DynamicAttributesClass();
		
		dac1.addValue("_id", 1);
		dac2.addValue("_id", 2);
		dac3.addValue("_id", 3);
		
		dac1.addValue("testField", "abc");
		dac2.addValue("testField", "abc");
		dac3.addValue("testField", "xyz");
		
		list.add(dac1);
		list.add(dac2);
		
		SearchResultMap map = new SearchResultMap();
		
		map.updateResultsMap("typeTest", new SearchValue("testField", "abc"), list);
		
        assertEquals("Get result by corret parameters", list, map.findInResultMap("typeTest", new SearchValue("testField", "abc")));
        assertEquals("Get result by wrong type", null, map.findInResultMap("wrongType", new SearchValue("testField", "abc")));
        assertEquals("Get result by wrong field", null, map.findInResultMap("typeTest", new SearchValue("wrongField", "abc")));
        assertEquals("Get result by wrong value", null, map.findInResultMap("typeTest", new SearchValue("testField", "xyz")));

    }
}
