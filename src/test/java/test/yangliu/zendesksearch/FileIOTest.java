package test.yangliu.zendesksearch;

import java.util.Map;

import junit.framework.TestCase;

import org.json.simple.JSONArray;
import org.junit.Test;

import test.yangliu.zendesksearch.controllers.FileIO;
import test.yangliu.zendesksearch.models.DynamicAttributesClass;

public class FileIOTest extends TestCase{
	@Test
    public static void test() {
		Map<String, DynamicAttributesClass> map = FileIO.readJSONFile("test.json");
		
		DynamicAttributesClass dacExpect1 = new DynamicAttributesClass();
		DynamicAttributesClass dacExpect2 = new DynamicAttributesClass();
		
		dacExpect1.addValue("_id", 123);
		dacExpect1.addValue("field1", true);
		dacExpect1.addValue("field2", "test1");
		JSONArray arr1 = new JSONArray();
		arr1.add("a");
		arr1.add("b");
		arr1.add("c");
		dacExpect1.addValue("field3", arr1);
		
		dacExpect2.addValue("_id", 456);
		dacExpect2.addValue("field1", false);
		dacExpect2.addValue("field2", "test2");
		JSONArray arr2 = new JSONArray();
		arr2.add("x");
		arr2.add("y");
		arr2.add("z");
		dacExpect2.addValue("field3", arr2);
		
		assertEquals("The map includes obj1 with same id", dacExpect1.getId(), ((DynamicAttributesClass)map.get("123")).getId());
		assertEquals("The map includes obj1 with same value of field1", dacExpect1.getValue("field1"), ((DynamicAttributesClass)map.get("123")).getValue("field1"));
		assertEquals("The map includes obj1 with same value of field2", dacExpect1.getValue("field2"), ((DynamicAttributesClass)map.get("123")).getValue("field2"));
		assertEquals("The map includes obj1 with same value of field3", dacExpect1.getValue("field3").toString(), ((DynamicAttributesClass)map.get("123")).getValue("field3").toString());

		assertEquals("The map includes obj2 with same id", dacExpect2.getId(), ((DynamicAttributesClass)map.get("456")).getId());
		assertEquals("The map includes obj2 with same value of field1", dacExpect2.getValue("field1"), ((DynamicAttributesClass)map.get("456")).getValue("field1"));
		assertEquals("The map includes obj2 with same value of field2", dacExpect2.getValue("field2"), ((DynamicAttributesClass)map.get("456")).getValue("field2"));
		assertEquals("The map includes obj2 with same value of field3", dacExpect2.getValue("field3").toString(), ((DynamicAttributesClass)map.get("456")).getValue("field3").toString());

	}
}
