package com.isaia.utility;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IDConverterTest {

	@Test
	public void testCreateUniqueID() {
		assertEquals("odOPh9", IDConverter.INSTANCE.createUniqueID(12879879875L));
	}

	@Test
	public void testGetDictionaryKeyFromUniqueID() {
		Long dictionaryKeyFromUniqueID = IDConverter.INSTANCE.getDictionaryKeyFromUniqueID("odOPh9");
		long expected = 12879879875l;
		assertEquals(expected, dictionaryKeyFromUniqueID.longValue());
	}

}
