package test.yangliu.zendesksearch;

import junit.framework.TestCase;
import org.junit.Assert;

import org.junit.Test;

import test.yangliu.zendesksearch.models.DynamicAttributesClass;

public class DynamicAttributesClassTest extends TestCase{

	@Test
    public static void testAddAndGetValues() {
       		
		DynamicAttributesClass dac1 = new DynamicAttributesClass();
		DynamicAttributesClass dac2 = new DynamicAttributesClass();
		DynamicAttributesClass dac3 = new DynamicAttributesClass();
		
		dac1.addValue("_id", 1);
		dac2.addValue("_id", 2);
		dac3.addValue("_id", 3);
		
		dac1.addValue("testField", "abc");
		dac2.addValue("testField", "abc");
		dac3.addValue("testField", "xyz");
		
		assertEquals("Get id should be string", "1", dac1.getId());
		assertNotSame("Get id is not integer", 1, dac1.getId());
		assertEquals("Get testField value", "abc", dac1.getValue("testField"));
		assertEquals("Get testField value of another obj", "xyz", dac3.getValue("testField"));
		assertEquals("Get value by not existed field", null, dac1.getValue("wrongField"));
		Assert.assertArrayEquals("Get attributes", new String[]{"_id", "testField"}, dac1.getAttributes());
    }
}
