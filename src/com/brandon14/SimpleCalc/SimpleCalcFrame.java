/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2013 Brandon Clothier
 * 
 * @author Brandon Clothier
 */
package com.brandon14.SimpleCalc;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.BoundedRangeModel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This class contains all of the layouts for the calculator as well as the button Action
 * Listeners and the functions to perform the calculations and update the UI.
 * 
 * @author Brandon Clothier
 *
 */
public class SimpleCalcFrame extends JFrame implements ActionListener, DocumentListener {
	private static final long serialVersionUID = 610599451754598404L;
	
	private static JFrame calculatorWindow;
	
	private static final int HEIGHT_OF_WINDOW = 325;
	private static final int WIDTH_OF_WINDOW = 300;
	
	private static Font sansSerifRegular = new Font("Sans Serif", Font.BOLD, 16);
	private static Font sansSerifLarge = new Font("Sans Serif", Font.PLAIN, 18);
	
	private static JTextField displayArea = new JTextField();
	private static JTextField subDisplayArea = new JTextField();
	private static JScrollBar displayScrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
	private static JPanel displayPanel = new JPanel();
	private static String calculatorDisplay;
	
	private static JPanel rowOneButtons = new JPanel(new GridLayout(2,5));
	private static JPanel rowTwoButtons = new JPanel(new GridLayout(2,5));
	private static JPanel rowThreeButtons = new JPanel(new GridLayout(1,5));
	private static JPanel rowThreeOneButtons = new JPanel(new GridLayout(2,1));
	private static JPanel rowThreeTwoButtons = new JPanel(new GridLayout(2,1));
	private static JPanel rowThreeThreeButtons = new JPanel(new GridLayout(2,1));
	private static JPanel rowThreeFourButtons = new JPanel(new GridLayout(2,1));
	private static JPanel rowThreeFiveButtons = new JPanel(new GridLayout(1,1));
	
	private static JButton mcButton;
	private static JButton mrButton;
	private static JButton msButton;
	private static JButton mPlusButton;
	private static JButton mMinusButton;
	private static JButton deleteButton;
	private static JButton ceButton;
	private static JButton cButton;
	private static JButton plusMinusButton;
	private static JButton sqrtButton;
	
	private static JButton sevenButton;
	private static JButton eightButton;
	private static JButton nineButton;
	private static JButton divideButton;
	private static JButton percentButton;
	private static JButton fourButton;
	private static JButton fiveButton;
	private static JButton sixButton;
	private static JButton multiplyButton;
	private static JButton onexButton;
	
	private static JButton oneButton;
	private static JButton twoButton;
	private static JButton threeButton;
	private static JButton zeroButton;
	private static JButton minusButton;
	private static JButton equalsButton;
	private static JButton decimalButton;
	private static JButton plusButton;
	private static JButton powerButton;
	
	private static boolean firstEntry;
	private static byte repeatedLastOp;
	private static BigDecimal repeatedLastNum;
	private static boolean equals;
	
	/**
	 * 0 - no operator
	 * 1 - addition
	 * 2 - subtraction
	 * 3 - multiplication
	 * 4 - division
	 * 5 - power
	 * 6 - equals (for repeated equals)
	 */
	private static byte lastOperator;
	private static BigDecimal lastNumber;
	private static BigDecimal result;
	private static BigDecimal memory;
	
	/**
	 * The constructor for the calculator program will initialize the JFrame using the
	 * titleOfCalculator and set the options for it to no be resizeable, and set the dimensions
	 * using the final constants. It will also initialize the display layout and add the 
	 * textfield and the scrollbar to it. It will initialize all the calculator buttons and 
	 * add the interface ActionListener method to them. It initializes tooltips for each 
	 * button and adds them to the respective panels, then adds the panels to the
	 * JFrame.At the end it initialize's the flag variables and display variables to the
	 * required state, then sets the JFrame to be visible.
	 * 
	 * @param titleOfCalculator title of the calculator JFrame.
	 */
	public SimpleCalcFrame(String titleOfCalculator) {
		calculatorWindow = new JFrame(titleOfCalculator);
		calculatorWindow.setSize(WIDTH_OF_WINDOW, HEIGHT_OF_WINDOW);
		calculatorWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		calculatorWindow.setResizable(false);
		calculatorWindow.setLayout(new GridLayout(4,1));
		setDesign();
		
		displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
		BoundedRangeModel resultBRM = displayArea.getHorizontalVisibility();
		displayScrollBar.setModel(resultBRM);
		displayScrollBar.setVisible(false);
		displayPanel.add(subDisplayArea);
		displayPanel.add(displayArea);
		displayPanel.add(displayScrollBar);
		displayArea.setAutoscrolls(true);
		subDisplayArea.setAutoscrolls(true);
		displayArea.setEditable(false);
		subDisplayArea.setEditable(false);
		displayArea.setFont(sansSerifLarge);
		subDisplayArea.setFont(sansSerifRegular);
		displayArea.setHorizontalAlignment(JTextField.RIGHT);
		subDisplayArea.setHorizontalAlignment(JTextField.RIGHT);
		displayArea.getDocument().addDocumentListener(this);
		subDisplayArea.getDocument().addDocumentListener(this);
		
		mcButton = new JButton("MC");
		mrButton = new JButton("MR");
		msButton = new JButton("MS");
		mPlusButton = new JButton("M+");
		mMinusButton = new JButton("M-");
		deleteButton = new JButton("Del");
		ceButton = new JButton("CE");
		cButton = new JButton("C");
		plusMinusButton = new JButton("+/-");
		sqrtButton = new JButton("√");
		
		/**
		 * Add the interface ActionListener method to each button and
		 * create tooltips for each button.
		 */
		mcButton.addActionListener(this);
		mcButton.setToolTipText("Memory clear button. This button will "
								+ "clear the number stored in memory.");
		mrButton.addActionListener(this);
		mrButton.setToolTipText("Memory recall button. This button will "
								+ "recall the number in memory to the screen.");
		msButton.addActionListener(this);
		msButton.setToolTipText("Memory store button. This will store the "
								+ "current number on screen into the memory.");
		mPlusButton.addActionListener(this);
		mPlusButton.setToolTipText("Memory add button. This will add the "
								+ "current number on screen to the number in memory.");
		mMinusButton.addActionListener(this);
		mMinusButton.setToolTipText("Memory subtract button. This button will "
								+ "subtract the current number on screen from the number in memory.");
		deleteButton.addActionListener(this);
		deleteButton.setToolTipText("Delete button. This button will delete the last "
								+ "number/decimal point entered.");
		ceButton.addActionListener(this);
		ceButton.setToolTipText("Clear entry button. This will remove the current number on screen.");
		cButton.addActionListener(this);
		cButton.setToolTipText("Clear all button. This will clear any number on screen, and set"
								+ " everything back to the initial state. It will not clear the number "
								+ "stored with the memory buttons.");
		plusMinusButton.addActionListener(this);
		plusMinusButton.setToolTipText("Positive/negative button. Will take the current number on screen"
								+ " and negate it or make it positive.");
		sqrtButton.addActionListener(this);
		sqrtButton.setToolTipText("Square root button. This button will take the square root of the current"
								+ " number on screen.");
		
		rowOneButtons.add(mcButton);
		rowOneButtons.add(mrButton);
		rowOneButtons.add(msButton);
		rowOneButtons.add(mPlusButton);
		rowOneButtons.add(mMinusButton);
		rowOneButtons.add(deleteButton);
		rowOneButtons.add(ceButton);
		rowOneButtons.add(cButton);
		rowOneButtons.add(plusMinusButton);
		rowOneButtons.add(sqrtButton);
		
		sevenButton = new JButton("7");
		eightButton = new JButton("8");
		nineButton = new JButton("9");
		divideButton = new JButton("/");
		percentButton = new JButton("%");
		fourButton = new JButton("4");
		fiveButton = new JButton("5");
		sixButton = new JButton("6");
		multiplyButton = new JButton("*");
		onexButton = new JButton("1/x");
		
		/**
		 * Add the interface ActionListener method to each button and
		 * create tooltips for each button.
		 */
		sevenButton.addActionListener(this);
		sevenButton.setToolTipText("Seven button.");
		eightButton.addActionListener(this);
		eightButton.setToolTipText("Eight button.");
		nineButton.addActionListener(this);
		nineButton.setToolTipText("Nine button.");
		divideButton.addActionListener(this);
		divideButton.setToolTipText("Divide button.");
		percentButton.addActionListener(this);
		percentButton.setToolTipText("Percent button. This will divide the displayed number by 100.");
		fourButton.addActionListener(this);
		fourButton.setToolTipText("Four button.");
		fiveButton.addActionListener(this);
		fiveButton.setToolTipText("Five button.");
		sixButton.addActionListener(this);
		sixButton.setToolTipText("Six button.");
		multiplyButton.addActionListener(this);
		multiplyButton.setToolTipText("Multiply button.");
		onexButton.addActionListener(this);
		onexButton.setToolTipText("Reciprocal button. Will display the reciprocal"
								+ " of the current number displayed.");
		
		rowTwoButtons.add(sevenButton);
		rowTwoButtons.add(eightButton);
		rowTwoButtons.add(nineButton);
		rowTwoButtons.add(divideButton);
		rowTwoButtons.add(percentButton);
		rowTwoButtons.add(fourButton);
		rowTwoButtons.add(fiveButton);
		rowTwoButtons.add(sixButton);
		rowTwoButtons.add(multiplyButton);
		rowTwoButtons.add(onexButton);
		
		oneButton = new JButton("1");
		twoButton = new JButton("2");
		threeButton = new JButton("3");
		zeroButton = new JButton("0");
		minusButton = new JButton("-");
		equalsButton = new JButton("=");
		decimalButton = new JButton(".");
		plusButton = new JButton("+");
		powerButton = new JButton("<html>x<sup>a</sup><html>");
		
		/**
		 * Add the interface ActionListener method to each button and
		 * create tooltips for each button.
		 */
		oneButton.addActionListener(this);
		oneButton.setToolTipText("One button.");
		twoButton.addActionListener(this);
		twoButton.setToolTipText("Two button.");
		threeButton.addActionListener(this);
		threeButton.setToolTipText("Three button.");
		zeroButton.addActionListener(this);
		zeroButton.setToolTipText("Zero button.");
		minusButton.addActionListener(this);
		minusButton.setToolTipText("Minus button.");
		equalsButton.addActionListener(this);
		equalsButton.setToolTipText("Equals button.");
		decimalButton.addActionListener(this);
		decimalButton.setToolTipText("Decimal button.");
		plusButton.addActionListener(this);
		plusButton.setToolTipText("Plus button.");
		powerButton.addActionListener(this);
		powerButton.setToolTipText("Power button. Will raise the current number to "
								+ "the power of the number typed in afterwards.");
		
		rowThreeOneButtons.add(oneButton);
		rowThreeOneButtons.add(powerButton);
		
		rowThreeTwoButtons.add(twoButton);
		rowThreeTwoButtons.add(zeroButton);
		
		rowThreeThreeButtons.add(threeButton);
		rowThreeThreeButtons.add(decimalButton);
		
		rowThreeFourButtons.add(minusButton);
		rowThreeFourButtons.add(plusButton);
		
		rowThreeFiveButtons.add(equalsButton);
		equalsButton.setFont(sansSerifRegular);
		
		rowThreeButtons.add(rowThreeOneButtons);
		rowThreeButtons.add(rowThreeTwoButtons);
		rowThreeButtons.add(rowThreeThreeButtons);
		rowThreeButtons.add(rowThreeFourButtons);
		rowThreeButtons.add(rowThreeFiveButtons);
		
		calculatorWindow.add(displayPanel);
		calculatorWindow.add(rowOneButtons);
		calculatorWindow.add(rowTwoButtons);
		calculatorWindow.add(rowThreeButtons);
		
		calculatorDisplay = "0";
		
		firstEntry = true;
		equals = false;
		lastOperator = 0;
		lastNumber = BigDecimal.ZERO;
		result = BigDecimal.ZERO;
		memory = BigDecimal.ZERO;
		
		subDisplayArea.setText("");
		displayArea.setText(calculatorDisplay);
		calculatorWindow.setVisible(true);
	}
	
	/**
	 * Method to attempt to set the look and feel of the Swing Objects to the
	 * Nimbus look and feel included in the Java 1.7 JDK.
	 */
	public void setDesign() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {  
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to clear all from the screen, and the previous operations.
	 * This method will set the operator variable to zero, set both the
	 * result and lastNumber variables to zero, set the display string to display 0
	 * and set the equals flag to false and the firstEntry variable to true.
	 */
	private static void clear() {
		lastOperator = 0;
		equals = false;
		firstEntry = true;
		
		lastNumber = BigDecimal.ZERO;
		result = BigDecimal.ZERO;
		
		calculatorDisplay = "0";
		displayArea.setText(calculatorDisplay);
		subDisplayArea.setText("");
	}
	
	/**
	 * This method will only clear out the number displayed currently on the screen.
	 */
	private static void clearEntry() {
		calculatorDisplay = "0";
		displayArea.setText(calculatorDisplay);
		
		firstEntry = true;
	}
	
	/**
	 * This method will add the provided character (in String format) to the
	 * display string and update the display.
	 * 
	 * @param character The character desired to add to the display string.
	 */
	public void addToDisplay(String character) {
		/**
		 * If equals or firstEntry is true, then we need to only add the character passed to
		 * this method to the display String as there will already be a zero there. So we ignore the
		 * zero and just set the display string to the character passed to the method and set equals to false.
		 */
		if (equals || firstEntry) {
			calculatorDisplay = character;
			equals = false;
		}
		else
			calculatorDisplay += character;
		
		displayArea.setText(calculatorDisplay);
		
		firstEntry = false;
	}
	
	/**
	 * This method will negate the number showing on the display.
	 * 
	 * @throws NumberFormatException
	 */
	private static void calculateNegation() throws NumberFormatException {
		calculatorDisplay = displayArea.getText();
		
		if (firstEntry) {
			JOptionPane.showMessageDialog(null, "Error: You must input a number before an operator!", 
												"Error!", JOptionPane.ERROR_MESSAGE);
			clearEntry();
			
			return;
		}
		else {
			try {
				calculatorDisplay = new BigDecimal(calculatorDisplay).multiply(BigDecimal.valueOf(-1)).toEngineeringString();
			} catch (NumberFormatException exception) {
									System.out.println("Number format was invalid, INFINITE or NaN!");
				JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				
				clearEntry();
				
				return;
			}
		}
		
		displayArea.setText(calculatorDisplay);
	}
	
	/**
	 * This method will evaluate the squareRoot of the currently displaying number given
	 * that it is not negative.
	 * 
	 * @throws NumberFormatException
	 * @throws ArithmeticException
	 */
	private static void calculateSquareRoot() throws NumberFormatException, ArithmeticException {
		calculatorDisplay = displayArea.getText();
		
		if (firstEntry) {
			JOptionPane.showMessageDialog(null, "Error: You must input a number before an operator!", 
												"Error!", JOptionPane.ERROR_MESSAGE);
			clearEntry();
			
			return;
		}
		else {
			try {
				lastNumber = new BigDecimal(calculatorDisplay);
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "Error: Malformed expression!!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				
				calculatorDisplay = "0";
				displayArea.setText(calculatorDisplay);
				
				firstEntry = true;
				
				return;
			}
		}
		
		if (lastNumber.compareTo(BigDecimal.ZERO) < 0)
			JOptionPane.showMessageDialog(null, "Error: Cannot take the square root of a negative number", 
												"Error!", JOptionPane.ERROR_MESSAGE);
		else {
			try {
				lastNumber = BigDecimal.valueOf(Math.sqrt(lastNumber.doubleValue()));
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "Error: Infinite/NaN result!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				clear();
				
				lastNumber = BigDecimal.ZERO;
			}
		}
		
		calculatorDisplay = lastNumber.toEngineeringString();
		displayArea.setText(calculatorDisplay);
		
		equals = true;
	}
	
	/**
	 * This method will attempt to calculate the reciprocal of the currently displayed number.
	 * If the result ends up in a non-terminating decimal, it will round it to 10 decimal places.
	 * 
	 * @throws NumberFormatException
	 * @throws ArithmeticException
	 */
	private static void calculateReciprocal() throws NumberFormatException, ArithmeticException {
		calculatorDisplay = displayArea.getText();
		
		if (firstEntry) {
			JOptionPane.showMessageDialog(null, "Error: You must input a number before an operator!", 
												"Error!", JOptionPane.ERROR_MESSAGE);
			clearEntry();
			
			return;
		}
		else {
			try {
				lastNumber = new BigDecimal(calculatorDisplay);
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "Error: Malformed expression!!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				
				calculatorDisplay = "0";
				displayArea.setText(calculatorDisplay);
				
				firstEntry = true;
				
				return;
			}
		}
		
		if (!lastNumber.equals(BigDecimal.ZERO)) {
			try {
				lastNumber = BigDecimal.ONE.divide(lastNumber);
			} catch (ArithmeticException exception) {
				System.out.println("Non-terminating decimal answer. Rounding to 10 decimal places!");
			
				lastNumber = BigDecimal.ONE.divide(lastNumber, 10, RoundingMode.CEILING);
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "Error: Infinite/NaN result!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
			
				lastNumber = BigDecimal.ZERO;
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Error: Divide by zero!", "Error!", JOptionPane.ERROR_MESSAGE);
			clear();
		}
		
		calculatorDisplay = lastNumber.toEngineeringString();
		displayArea.setText(calculatorDisplay);
		
		equals = true;
	}
	
	/**
	 * This method will calculate the percentage value of the currently displayed number.
	 * 
	 * @throws NumberFormatException
	 */
	private static void calculatePercentage() throws NumberFormatException {
		calculatorDisplay = displayArea.getText();
		
		if (firstEntry) {
			JOptionPane.showMessageDialog(null, "Error: You must input a number before an operator!", 
												"Error!", JOptionPane.ERROR_MESSAGE);
			clearEntry();
			
			return;
		}
		else {
			try {
				lastNumber = new BigDecimal(calculatorDisplay);
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "Error: Malformed expression!!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				
				calculatorDisplay = "0";
				displayArea.setText(calculatorDisplay);
				
				firstEntry = true;
				
				return;
			}
		}
		
		try {
			lastNumber = lastNumber.divide(BigDecimal.valueOf(100));
		} catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(null, "Error: Infinite/NaN result!", 
					"Error!", JOptionPane.ERROR_MESSAGE);
			clear();
			
			lastNumber = BigDecimal.ZERO;
		}
		
		calculatorDisplay = lastNumber.toEngineeringString();
		displayArea.setText(calculatorDisplay);
		
		equals = true;
	}
	
	/**
	 * This method will store the current number in the display to the memory.
	 * 
	 * @throws NumberFormatException
	 */
	private static void memoryStore() throws NumberFormatException {
		calculatorDisplay = displayArea.getText();
		
		if (firstEntry) {
			JOptionPane.showMessageDialog(null, "Error: You must input a number before an operator!", 
												"Error!", JOptionPane.ERROR_MESSAGE);
			
			clearEntry();
			
			return;
		}
		else {
			try {
				memory = new BigDecimal(calculatorDisplay);
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "Error: Malformed expression!!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				
				calculatorDisplay = "0";
				displayArea.setText(calculatorDisplay);
				
				firstEntry = true;
				
				return;
			}
		}
		
		calculatorDisplay = "0";
		displayArea.setText(calculatorDisplay);
		
		firstEntry = true;
	}
	
	/**
	 * This method will add the current number into the number stored in memory.
	 * 
	 * @throws NumberFormatException
	 */
	private static void memoryAdd() throws NumberFormatException {
		calculatorDisplay = displayArea.getText();
		
		if (firstEntry) {
			JOptionPane.showMessageDialog(null, "Error: You must input a number before an operator!", 
												"Error!", JOptionPane.ERROR_MESSAGE);
			
			clearEntry();
			
			return;
		}
		else {
			try {
				memory = memory.add(new BigDecimal(calculatorDisplay));
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				
				memory = BigDecimal.ZERO;
			}
		}
		
		calculatorDisplay = "0";
		displayArea.setText(calculatorDisplay);
		
		firstEntry = true;
	}
	
	/**
	 * This method will subtract the current number from the number stored in memory.
	 * 
	 * @throws NumberFormatException
	 */
	private static void memoryMinus() throws NumberFormatException {
		calculatorDisplay = displayArea.getText();
		
		if (firstEntry) {
			JOptionPane.showMessageDialog(null, "Error: You must input a number before an operator!", 
												"Error!", JOptionPane.ERROR_MESSAGE);
			
			clearEntry();
			
			return;
		}
		else
		{
			try {
				memory = memory.subtract(new BigDecimal(calculatorDisplay));
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				
				memory = BigDecimal.ZERO;
			}
		}
		
		calculatorDisplay = "0";
		displayArea.setText(calculatorDisplay);
		
		firstEntry = true;
	}
	
	/**
	 * This method will process the last operator if there is one pending, and then queue up the operator
	 * that is passed to this method. It will only process the last operator if the lastOperator variable
	 * equals one or six (corresponding to no operation or a repeated equals operation).
	 * 
	 * @param operator byte number corresponding to the desired operation.
	 * @throws NumberFormatException
	 */
	private static void calculateOperator(byte operator) throws NumberFormatException {
		calculatorDisplay = displayArea.getText();
		BigDecimal currentNumber;
		
		if (firstEntry) {
			JOptionPane.showMessageDialog(null, "Error: You must input a number before an operator!", 
												"Error!", JOptionPane.ERROR_MESSAGE);
			clear();
			
			return;
		}
		/**
		 * Else we must try to set the currentNumber variable, and catch NumberFormatException
		 * to check for malformed expression (i.e. multiple decimal points, odd characters, etc.)
		 */
		else {
			try {
				currentNumber = new BigDecimal(calculatorDisplay);
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "Error: Malformed expression!!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				clear();
				
				return;
			}
		}
		
		switch (operator) {
			case 1:
				subDisplayArea.setText(currentNumber.toEngineeringString() + " +");
				break;
			case 2:
				subDisplayArea.setText(currentNumber.toEngineeringString() + " -");
				break;
			case 3:
				subDisplayArea.setText(currentNumber.toEngineeringString() + " *");
				break;
			case 4:
				subDisplayArea.setText(currentNumber.toEngineeringString() + " /");
				break;
			case 5:
				subDisplayArea.setText(currentNumber.toEngineeringString() + " ^");
				break;
		}
		
		/**
		 * If the last operator doesn't equal zero or six then we have a pending operation
		 * in queue, so we set the result equal to the the BigDecimal returned from the
		 * calculateLastOperator() method. And set the lastNumber variable to the result.
		 */
		if (lastOperator != 0 && lastOperator != 6){
			result = calculateLastOperator();
			lastNumber = result;
		}
		else
			lastNumber = currentNumber;
		
		calculatorDisplay = lastNumber.toEngineeringString();
		displayArea.setText(calculatorDisplay);
		
		lastOperator = operator;
		calculatorDisplay = "";
		displayArea.setText(calculatorDisplay);
	}
	
	/**
	 * This method calculates the pending operation when it is called from the calculateOperator or
	 * equals functions. It returns the BigDecimal result of the operation.
	 * 
	 * @return the BigDecimal value of the operation performed.
	 * @throws NumberFormatException
	 * @throws ArithmeticException
	 */
	private static BigDecimal calculateLastOperator() throws NumberFormatException, ArithmeticException {
		calculatorDisplay = displayArea.getText();
		BigDecimal currentNumber;
		
		if (firstEntry) {
			JOptionPane.showMessageDialog(null, "Error: You must input a number before an operator!", 
												"Error!", JOptionPane.ERROR_MESSAGE);
			clear();
			
			return BigDecimal.ZERO;
		}
		/**
		 * Else we must try to set the currentNumber variable, and catch NumberFormatException
		 * to check for malformed expression (i.e. multiple decimal points, odd characters, etc.)
		 */
		else {
			try {
				currentNumber = new BigDecimal(calculatorDisplay);
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "Error: Malformed expression!!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				clear();
				
				return BigDecimal.ZERO;
			}
		}
		
		if (lastOperator == 0)
			return currentNumber;
		
		/**
		 * If lastOperator equals 6, we are dealing with the repeated equals, so we must check what the
		 * repeatedLastOp variable is.
		 */
		if (lastOperator == 6){
			if (repeatedLastOp == 5) {
				try {
					result = new BigDecimal(Math.pow(currentNumber.doubleValue(), repeatedLastNum.doubleValue()));
				} catch (NumberFormatException exception) {
					System.out.println("Number format was invalid, INFINITE or NaN!");
					JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
							"Error!", JOptionPane.ERROR_MESSAGE);
					clear();
					
					result = BigDecimal.ZERO;
				}
			}
			if (repeatedLastOp == 4) {
				if (!currentNumber.equals(BigDecimal.ZERO)) {
					try {
						result = currentNumber.divide(repeatedLastNum);
					} catch (ArithmeticException exception) {
						System.out.println("Non-terminating decimal answer. Rounding to 10 decimal places!");
					
												result = currentNumber.divide(repeatedLastNum, 10, RoundingMode.CEILING);
					} catch (NumberFormatException exception) {
						System.out.println("Number format was invalid, INFINITE or NaN!");
						JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
								"Error!", JOptionPane.ERROR_MESSAGE);
						clear();
						
						result = BigDecimal.ZERO;
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Error: Divide by zero!", "Error!", JOptionPane.ERROR_MESSAGE);
					clear();
					
					return BigDecimal.ZERO;
				}
			}
			if (repeatedLastOp == 3) {
				try {
					result = currentNumber.multiply(repeatedLastNum);
				} catch (NumberFormatException exception) {
					System.out.println("Number format was invalid, INFINITE or NaN!");
					JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
							"Error!", JOptionPane.ERROR_MESSAGE);
					clear();
					
					result = BigDecimal.ZERO;
				}
			}
			if (repeatedLastOp == 2) {
				try {
					result = currentNumber.subtract(repeatedLastNum);
				} catch (NumberFormatException exception) {
					System.out.println("Number format was invalid, INFINITE or NaN!");
					JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
							"Error!", JOptionPane.ERROR_MESSAGE);
					clear();
					
					result = BigDecimal.ZERO;
				}
			}
			if (repeatedLastOp == 1) {
				try {
					result = currentNumber.add(repeatedLastNum);
				} catch (NumberFormatException exception)
				{
					System.out.println("Number format was invalid, INFINITE or NaN!");
					JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
							"Error!", JOptionPane.ERROR_MESSAGE);
					clear();
					
					result = BigDecimal.ZERO;
				}
			}
		}
		/**
		 * End of the repeatedEquals functions. The next ones are used for normal calculations.
		 */
		if (lastOperator == 5) {
			try {
				result = new BigDecimal(Math.pow(lastNumber.doubleValue(), currentNumber.doubleValue()));
			} catch (NumberFormatException exception) {
				System.out.println("Number format was invalid, INFINITE or NaN!");
				JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				clear();
				
				result = BigDecimal.ZERO;
			}
		}
		if (lastOperator == 4) {
			if (!currentNumber.equals(BigDecimal.ZERO)) {
				try {
					result = lastNumber.divide(currentNumber);
				} catch (ArithmeticException exception) {
					System.out.println("Non-terminating decimal answer. Rounding to 10 decimal places!");
				
					result = lastNumber.divide(currentNumber, 10, RoundingMode.CEILING);
				} catch (NumberFormatException exception) {
					System.out.println("Number format was invalid, INFINITE or NaN!");
					JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
							"Error!", JOptionPane.ERROR_MESSAGE);
					clear();
					
					result = BigDecimal.ZERO;
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Error: Divide by zero!", "Error!", JOptionPane.ERROR_MESSAGE);
				clear();
				
				return BigDecimal.ZERO;
			}
		}
		if (lastOperator == 3) {
			try {
				result = lastNumber.multiply(currentNumber);
			} catch (NumberFormatException exception) {
				System.out.println("Number format was invalid, INFINITE or NaN!");
				JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				clear();
				
				result = BigDecimal.ZERO;
			}
		}
		if (lastOperator == 2) {
			try {
				result = lastNumber.subtract(currentNumber);
			} catch (NumberFormatException exception) {
				System.out.println("Number format was invalid, INFINITE or NaN!");
				JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				clear();
				
				result = BigDecimal.ZERO;
			}
		}
		if (lastOperator == 1) {
			try {
				result = lastNumber.add(currentNumber);
			} catch (NumberFormatException exception) {
				System.out.println("Number format was invalid, INFINITE or NaN!");
				JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				clear();
				
				result = BigDecimal.ZERO;
			}
		}
		
		lastNumber = currentNumber;
		
		/**
		 * If we aren't dealing with repeated equals, set the repatedLastNum to currentNumber and the repeatedLastOp to
		 * the lastOperator. This will allow us to keep a cache of the last number used and the last operation used
		 * so that when the user repeatedly presses equals, with the repeatedEquals functions at the very top of this method,
		 * the last operation will be repeated with the last number entered.
		 */
		if (lastOperator != 6)
		{
			repeatedLastNum = currentNumber;
			repeatedLastOp = lastOperator;
		}
		return result;
	}

	@Override
	/**
	 * This is the ActionListener interface method that handles the ActionListener functions of all the buttons.
	 * We use if statements to check the source of the event and perform actions based on what button was pressed.
	 * 
	 * @param e ActionEvent sent to the method from the button.
	 * @throws NumberFormatException 
	 * @throws ArithmeticException
	 */
	public void actionPerformed(ActionEvent e) throws NumberFormatException, ArithmeticException
	{
		/**
		 * ================= Input Button Functions =================
		 */
		if (e.getSource() == zeroButton)
			addToDisplay("0");
		if (e.getSource() == oneButton)
			addToDisplay("1");
		if (e.getSource() == twoButton)
			addToDisplay("2");
		if (e.getSource() == threeButton)
			addToDisplay("3");
		if (e.getSource() == fourButton)
			addToDisplay("4");
		if (e.getSource() == fiveButton)
			addToDisplay("5");
		if (e.getSource() == sixButton)
			addToDisplay("6");
		if (e.getSource() == sevenButton)
			addToDisplay("7");
		if (e.getSource() == eightButton)
			addToDisplay("8");
		if (e.getSource() == nineButton)
			addToDisplay("9");
		if (e.getSource() == decimalButton)
			addToDisplay(".");
		
		/**
		 * ================= Positive/Negative Function =================
		 */
		if (e.getSource() == plusMinusButton)
			calculateNegation();
		
		/**
		 * ================= Special Functions =================
		 */
		if (e.getSource() == sqrtButton)
			calculateSquareRoot();
		if (e.getSource() == onexButton)
			calculateReciprocal();
		if (e.getSource() == percentButton)
			calculatePercentage();
		
		/**
		 * ================= Operational Functions =================
		 */
		if (e.getSource() == plusButton)
			calculateOperator((byte)1);
		if (e.getSource() == minusButton)
			calculateOperator((byte)2);
		if (e.getSource() == multiplyButton)
			calculateOperator((byte)3);
		if (e.getSource() == divideButton)
			calculateOperator((byte)4);
		if (e.getSource() == powerButton)
			calculateOperator((byte)5);
		
		/**
		 * ================= Equals Function =================
		 */
		if (e.getSource() == equalsButton) {
			result = calculateLastOperator();
			
			calculatorDisplay = result.toEngineeringString();
			displayArea.setText(calculatorDisplay);
			
			equals = true;
			lastOperator = 6;
			
			subDisplayArea.setText("");
		}
		
		/**
		 * ================= Memory Functions ================= 
		 */
		if (e.getSource() == mcButton)
			memory = BigDecimal.ZERO;
		if (e.getSource() == msButton)
			memoryStore();
		if (e.getSource() == mPlusButton)
			memoryAdd();
		if (e.getSource() == mMinusButton)
			memoryMinus();
		if (e.getSource() == mrButton) {
			calculatorDisplay = memory.toString();
			displayArea.setText(calculatorDisplay);
		}
		
		/**
		 * ================= Delete & Clear Functions =================
		 */
		/**
		 * Backspace button. If the string in the display's length is greater than 1 and
		 * the firstEntry boolean is false, then we must check to see if the length of the
		 * string is only one character. If it is, then simply make the string display only
		 * 0, else we need to remove the last character from the display. At the end e update
		 * display by using the displayArea.setText() method.
		 */
		if (e.getSource() == deleteButton) {
			if (!firstEntry && calculatorDisplay.length() > 0) {
				if (calculatorDisplay.length() == 1) {
					calculatorDisplay = "0";
					firstEntry = true;
				}
				else
					calculatorDisplay = calculatorDisplay.substring(0, calculatorDisplay.length()-1);
				displayArea.setText(calculatorDisplay);
			}
		}
		if (e.getSource() == cButton)
			clear();
		if(e.getSource() == ceButton)
			clearEntry();
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		if (displayArea.getText().length() > 25 || subDisplayArea.getText().length() > 25)
			displayScrollBar.setVisible(true);
		else
			displayScrollBar.setVisible(false);	
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		if (displayArea.getText().length() > 25 || subDisplayArea.getText().length() > 25)
			displayScrollBar.setVisible(true);
		else
			displayScrollBar.setVisible(false);	
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		if (displayArea.getText().length() > 25 || subDisplayArea.getText().length() > 25)
			displayScrollBar.setVisible(true);
		else
			displayScrollBar.setVisible(false);		
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		SimpleCalcFrame myCalc = new SimpleCalcFrame("bCalc");
	}
}