package com.ubs.opsit.interviews.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BerlinClockTimeConverterImplTest {

	private static BerlinClockTimeConverterImpl bcc;

	@BeforeClass
	public static void setup() {
		bcc = spy(new BerlinClockTimeConverterImpl());
	}

	@AfterClass
	public static void tearDown() {
		bcc = null;
	}

	@Test
	public void testConvertTime() {
		assertEquals("O\r\n" + 
				"RROO\r\n" + 
				"RRRO\r\n" + 
				"YYROOOOOOOO\r\n" + 
				"YYOO", bcc.convertTime("13:17:01"));
		
		assertEquals("Y\r\n" + 
				"OOOO\r\n" + 
				"OOOO\r\n" + 
				"OOOOOOOOOOO\r\n" + 
				"OOOO", bcc.convertTime("00:00:00"));
		
		assertEquals("O\r\n" + 
				"RRRR\r\n" + 
				"RRRO\r\n" + 
				"YYRYYRYYRYY\r\n" + 
				"YYYY", bcc.convertTime("23:59:59"));
		
		assertEquals("Y\r\n" + 
				"RRRR\r\n" + 
				"RRRR\r\n" + 
				"OOOOOOOOOOO\r\n" + 
				"OOOO", bcc.convertTime("24:00:00"));
		
		verify(bcc).convertTime("24:00:00");
		verify(bcc).convertTime("23:59:59");
		verify(bcc).convertTime("00:00:00");
		verify(bcc).convertTime("13:17:01");
		//verifying that method was not iteratively/otherwise called
		verify(bcc, times(4)).convertTime(anyString());
		//Resetting invocations since bcc is created only once for all tests
		reset(bcc);
	}

	@Test
	public void testGetSecondsIndicator() {
		assertEquals("O", bcc.getSecondsIndicator(01));
		assertEquals("Y", bcc.getSecondsIndicator(0));
		assertEquals("O", bcc.getSecondsIndicator(59));
		assertEquals("Y", bcc.getSecondsIndicator(12));
		
		verify(bcc).getSecondsIndicator(01);
		verify(bcc).getSecondsIndicator(0);
		verify(bcc).getSecondsIndicator(59);
		verify(bcc).getSecondsIndicator(12);
		//verifying that method was not iteratively/otherwise called
		verify(bcc, times(4)).getSecondsIndicator(anyInt());
		//Resetting invocations since bcc is created only once for all tests
		reset(bcc);
	}

	@Test
	public void testGetHrsLevel1() {
		assertEquals("RROO", bcc.getHrsLevel1(13));
		assertEquals("OOOO", bcc.getHrsLevel1(00));
		assertEquals("RRRR", bcc.getHrsLevel1(23));
		assertEquals("RRRR", bcc.getHrsLevel1(24));
		
		verify(bcc).getHrsLevel1(13);
		verify(bcc).getHrsLevel1(00);
		verify(bcc).getHrsLevel1(23);
		verify(bcc).getHrsLevel1(24);
		//verifying that method was not iteratively/otherwise called
		verify(bcc, times(4)).getHrsLevel1(anyInt());
		//Resetting invocations since bcc is created only once for all tests
		reset(bcc);
	}

	@Test
	public void testGetHrsLevel2() {
		assertEquals("RRRO", bcc.getHrsLevel2(13));
		assertEquals("OOOO", bcc.getHrsLevel2(00));
		assertEquals("RRRO", bcc.getHrsLevel2(23));
		assertEquals("RRRR", bcc.getHrsLevel2(24));
		
		verify(bcc).getHrsLevel2(13);
		verify(bcc).getHrsLevel2(00);
		verify(bcc).getHrsLevel2(23);
		verify(bcc).getHrsLevel2(24);
		//verifying that method was not iteratively/otherwise called
		verify(bcc, times(4)).getHrsLevel2(anyInt());
		//Resetting invocations since bcc is created only once for all tests
		reset(bcc);
	}

	@Test
	public void testGetMinsLevel1() {
		assertEquals("YYROOOOOOOO", bcc.getMinsLevel1(17));
		assertEquals("OOOOOOOOOOO", bcc.getMinsLevel1(00));
		assertEquals("YYRYYRYYRYY", bcc.getMinsLevel1(59));
		assertEquals("OOOOOOOOOOO", bcc.getMinsLevel1(00));
		
		verify(bcc).getMinsLevel1(17);
		verify(bcc, times(2)).getMinsLevel1(00);
		verify(bcc).getMinsLevel1(59);
		//verifying that method was not iteratively/otherwise called
		verify(bcc, times(4)).getMinsLevel1(anyInt());
		//Resetting invocations since bcc is created only once for all tests
		reset(bcc);
	}

	@Test
	public void testGetMinsLevel2() {
		assertEquals("YYOO", bcc.getMinsLevel2(17));
		assertEquals("OOOO", bcc.getMinsLevel2(00));
		assertEquals("YYYY", bcc.getMinsLevel2(59));
		assertEquals("OOOO", bcc.getMinsLevel2(00));
		
		verify(bcc).getMinsLevel2(17);
		verify(bcc, times(2)).getMinsLevel2(00);
		verify(bcc).getMinsLevel2(59);
		//verifying that method was not iteratively/otherwise called
		verify(bcc, times(4)).getMinsLevel2(anyInt());
		//Resetting invocations since bcc is created only once for all tests
		reset(bcc);
	}

}
