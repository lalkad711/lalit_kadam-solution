package com.ubs.opsit.interviews.impl;

import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ubs.opsit.interviews.TimeConverter;

/**
 * 
 * @author Lalitkumar Kadam
 * 
 * This class is a time converter utility converting HH:MM:SS to Berlin-Clock format time.
 *
 */
public class BerlinClockTimeConverterImpl implements TimeConverter {

	/**
	 * Interfacing method
	 */
	@Override
	public String convertTime(String aTime) {
		
		int[] timeArray = Stream.of(aTime.split(":")).mapToInt(Integer::parseInt).toArray();

		return Stream.of(getSecondsIndicator(timeArray[2]), getHrsLevel1(timeArray[0]),
							getHrsLevel2(timeArray[0]), getMinsLevel1(timeArray[1]), getMinsLevel2(timeArray[1]))
							.collect(Collectors.joining("\r\n"));
	}
	
	/**
	 * This method shows the status of the seconds lamp
	 * Y - Yellow, O - Off
	 * 
	 * @param secs
	 * @return
	 * 
	 */
	public String getSecondsIndicator(int secs) {
		return (secs % 2) == 0 ? "Y" : "O";
	}
	
	/**
	 * This method is used resolve the status of the Level1 (Top Level) Hours lamp
	 * @param hrs
	 * @return
	 */
	public String getHrsLevel1(int hrs) {
		StringBuffer out = new StringBuffer();		
		processTimeData(() -> hrs/5, 
						(i) -> out.append("R"), 
						() -> 4 - (hrs/5), 
						() -> out.append("O"));
		
		return out.toString();
	}
	
	/**
	 * This method is resolve the status of the Level2 (Second Level) Hours lamp
	 * @param hrs
	 * @return
	 */
	public String getHrsLevel2(int hrs) {
		StringBuffer out = new StringBuffer();
		processTimeData(() -> hrs%5, 
						(i) -> out.append("R"), 
						() -> 4 - (hrs%5), 
						() -> out.append("O"));
		return out.toString();
	}
	
	/**
	 * This method is used resolve the status of the Level1 (Top Level) Minutes lamp
	 * @param mins
	 * @return
	 */
	public String getMinsLevel1(int mins) {
		StringBuffer out = new StringBuffer();
		processTimeData(() -> mins/5, 
						(i) -> {
							if ((i + 1) % 3 == 0)
								out.append("R");
							else
								out.append("Y");
							return out;
						}, 
						() -> 11 - (mins/5), 
						() -> out.append("O"));
		
		return out.toString();
	}
	
	/**
	 * This method is resolve the status of the Level2 (Second Level) Minutes lamp
	 * @param mins
	 * @return
	 */
	public String getMinsLevel2(int mins) {
		StringBuffer out = new StringBuffer();
		processTimeData(() -> mins%5, 
						(i) -> out.append("Y"), 
						() -> 4 - (mins%5), 
						() -> out.append("O"));
		
		return out.toString();
	}
	
	/**
	 * This is a skeletal method which executes the supplied lambdas in a certain manner
	 * 
	 * @param cond1 - Condition one
	 * @param op1 - Operation one
	 * @param cond2 - Condition two
	 * @param op2 - Operation two
	 */
	private void processTimeData(IntSupplier cond1, Function<Integer, StringBuffer> op1, 
			IntSupplier cond2, Supplier<StringBuffer> op2) {

		for (int i = 0; i < cond1.getAsInt(); i++)
			op1.apply(i);
		
		for (int i = 0; i < cond2.getAsInt(); i++)
			op2.get();
	}

}
