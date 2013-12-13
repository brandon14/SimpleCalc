/** Name: Brandon Clothier
 *  Homework: Homework #8
 *  Date: 11/26/2013
 *  Description: This program will render and display a fully functional calculator
 *               based on the layout of the Windows 7 standard calculator. It uses
 *               the BigDecimal class to do the calculations and JFrames and the Swing
 *               library to render the UI.
 *  
 */
package com.brandon14.Homework8.SimpleCalc;

/**
 * This class houses the main method that gets invoked when the program is fist ran.
 * 
 * @author brandon
 *
 */
public class Homework8
{
	/**
	 * This is the main method to the Calculator program for Homework #8.
	 * 
	 * @param args command line arguments stored in an array of Strings.
	 */
	public static void main(String[] args)
	{
		//Create a new SimpleCalcFrame Object passing the Window Title to it.
		SimpleCalcFrame myCalc = new SimpleCalcFrame("bCalc");
	}
}
