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

/**
 * This class contains all of the layouts for the calculator as well as the button Action
 * Listeners and the functions to perform the calculations and update the UI.
 * 
 * @author brandon
 *
 */
public class SimpleCalcFrame extends JFrame implements ActionListener
{
	//Auto-generated serialVersionUID.
	private static final long serialVersionUID = 610599451754598404L;
	
	//JFrame to display the UI in.
	private JFrame calculatorWindow;
	
	/**
	 * We declare the height and width of the window as
	 * final constant integers for use in the constructor.
	 */
	private final int HEIGHT_OF_WINDOW = 325;
	private final int WIDTH_OF_WINDOW = 300;
	
	/**
	 * We define the various fonts that will be used by the buttons
	 * and the textfield.
	 */
	private Font sansSerifRegular = new Font("Sans Serif", Font.BOLD, 16);
	private Font sansSerifLarge = new Font("Sans Serif", Font.PLAIN, 18);
	
	/**
	 * Create a JTextField, JScrollBar JPanel and a String that will all
	 * be used to create the display area of the calculator. We add the textfield
	 * and scroll bar to the JPanel, and the String will be what is display in the
	 * textfield.
	 */
	private JTextField displayArea = new JTextField();
	private JScrollBar displayScrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
	private JPanel displayPanel = new JPanel();
	private String calculatorDisplay;
	
	/**
	 * Create JPanel's for the row's of buttons. In order to get the double height equals
	 * button and maintain the other buttons normal heights, we must split the third row up
	 * into 5 separate JPanel's. We also use two rows of buttons in each row JPanel. We use
	 * GridLayouts on each panel to determine the number of rows of buttons and the number of
	 * buttons in each row for each JPanel.
	 */
	//2x5 GridLayout to house 10 buttons.
	private JPanel rowOneButtons = new JPanel(new GridLayout(2,5));
	//2x5 GridLayout to house 10 buttons.
	private JPanel rowTwoButtons = new JPanel(new GridLayout(2,5));
	//1x5 GridLayout to house 5 subrow JPanel's.
	private JPanel rowThreeButtons = new JPanel(new GridLayout(1,5));
	//2x1 layout to house 2 buttons on top of each other.
	private JPanel rowThreeOneButtons = new JPanel(new GridLayout(2,1));
	//2x1 layout to house 2 buttons on top of each other.
	private JPanel rowThreeTwoButtons = new JPanel(new GridLayout(2,1));
	//2x1 layout to house 2 buttons on top of each other.
	private JPanel rowThreeThreeButtons = new JPanel(new GridLayout(2,1));
	//2x1 layout to house 2 buttons on top of each other.
	private JPanel rowThreeFourButtons = new JPanel(new GridLayout(2,1));
	//1x1 layout to house 1 double height equals button.
	private JPanel rowThreeFiveButtons = new JPanel(new GridLayout(1,1));
	
	/**
	 * JButton's that will go on rowOne JPanel.
	 */
	//Memory clear button.
	private JButton mcButton;
	//Memory recall button.
	private JButton mrButton;
	//Memory store button.
	private JButton msButton;
	//Memory add button.
	private JButton mPlusButton;
	//Memory subtract button.
	private JButton mMinusButton;
	//Backspace button.
	private JButton deleteButton;
	//Clear entry button.
	private JButton ceButton;
	//Clear all button.
	private JButton cButton;
	//Positive negative button.
	private JButton plusMinusButton;
	//Square root button.
	private JButton sqrtButton;
	
	/**
	 * JButton's that will go on rowTwo JPanel.
	 */
	//7 button.
	private JButton sevenButton;
	//8 button.
	private JButton eightButton;
	//9 button.
	private JButton nineButton;
	//Divide button.
	private JButton divideButton;
	//Percentage button.
	private JButton percentButton;
	//4 button.
	private JButton fourButton;
	//5 button.
	private JButton fiveButton;
	//6 button.
	private JButton sixButton;
	//Multiplication button.
	private JButton multiplyButton;
	//Reciprocal button.
	private JButton onexButton;
	
	/**
	 * JButton's that will go on rowThree individual
	 * JPanel's
	 */
	//1 button.
	private JButton oneButton;
	//2 button.
	private JButton twoButton;
	//3 button.
	private JButton threeButton;
	//0 button.
	private JButton zeroButton;
	//Subtraction button.
	private JButton minusButton;
	//Equals button.
	private JButton equalsButton;
	//Decimal point button.
	private JButton decimalButton;
	//Addition button.
	private JButton plusButton;
	//Exponent button.
	private JButton powerButton;
	
	/**
	 * We need some additional variables to handle the calculations and
	 * set flags to help maintain the display and so on.
	 */
	//boolean to notify program if it is the first character being entered to the display textfield.
	private boolean firstEntry;
	//byte to store the operation to be performed in the event of repeated equals button clicks.
	private byte repeatedLastOp;
	//BigDecimal to store the last number entered in the event of repeated equals button clicks.
	private BigDecimal repeatedLastNum;
	//boolean flag to notify the program that an equals event has taken place.
	private boolean equals;
	//byte variable to store what type of operation is to take place.
	/**
	 * 0 - no operator
	 * 1 - addition
	 * 2 - subtraction
	 * 3 - multiplication
	 * 4 - division
	 * 5 - power
	 * 6 - equals (for repeated equals)
	 */
	private byte lastOperator;
	//BigDecimal to store the last number entered into.
	private BigDecimal lastNumber;
	//BigDecimal to store the result of the operation into.
	private BigDecimal result;
	//BigDecimal to store the memory number in for the memory functions.
	private BigDecimal memory;
	
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
	public SimpleCalcFrame(String titleOfCalculator)
	{
		//Initialize the JFrame using titleOfCalculator as the title.
		calculatorWindow = new JFrame(titleOfCalculator);
		//Set the height and width to the defined constants.
		calculatorWindow.setSize(WIDTH_OF_WINDOW, HEIGHT_OF_WINDOW);
		//Make it dispose the JFrame on close.
		calculatorWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//Make it un-resizeable.
		calculatorWindow.setResizable(false);
		//Create a 4x1 GridLayout to add the display area plus the 3 rows of buttons.
		calculatorWindow.setLayout(new GridLayout(4,1));
		//Attempt to set the design to the Nimbus look and feel.
		setDesign();
		
		//Create a BoxLayout on the display JPanel to add the text field and scrollbar.
		displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
		//Create a BoundedRangeModel from the displayArea to set the model on the scroll bar.
		BoundedRangeModel resultBRM = displayArea.getHorizontalVisibility();
		displayScrollBar.setModel(resultBRM);
		//Add the textfield and scroll bar to the display panel.
		displayPanel.add(displayArea);
		displayPanel.add(displayScrollBar);
		//Set the textfield to autoscroll.
		displayArea.setAutoscrolls(true);
		//Set the textfield to be un-editiable.
		displayArea.setEditable(false);
		//Set the textfield font to the sansSerifLarge font we created.
		displayArea.setFont(sansSerifLarge);
		//Align the textfield to the right.
		displayArea.setHorizontalAlignment(JTextField.RIGHT);
		
		//Initialize the JButtons that go on rowOne JPanel.
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
		
		//Add each button to the rowOneButtons JPanel.
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
		
		//Initialize the JButtons that go on rowTwo JPanel.
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
		
		//Add each button to the rowTwoButtons JPanel
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
		
		//Initialize the JButtons that go on rowThree JPanel.
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
		
		//Add one and exponent button to the rowThreeOne panel.
		rowThreeOneButtons.add(oneButton);
		rowThreeOneButtons.add(powerButton);
		
		//Add two and zero buttons to the rowThreeTwo panel.
		rowThreeTwoButtons.add(twoButton);
		rowThreeTwoButtons.add(zeroButton);
		
		//Add 3 and decimal button to the rowThreeThree panel.
		rowThreeThreeButtons.add(threeButton);
		rowThreeThreeButtons.add(decimalButton);
		
		//Add minus and plus button to the rowThreeFour panel.
		rowThreeFourButtons.add(minusButton);
		rowThreeFourButtons.add(plusButton);
		
		//Add the equals button to the rowThreeFive panel.
		rowThreeFiveButtons.add(equalsButton);
		equalsButton.setFont(sansSerifRegular);
		
		//Add all row three sub panels to the rowThreeButtons panel.
		rowThreeButtons.add(rowThreeOneButtons);
		rowThreeButtons.add(rowThreeTwoButtons);
		rowThreeButtons.add(rowThreeThreeButtons);
		rowThreeButtons.add(rowThreeFourButtons);
		rowThreeButtons.add(rowThreeFiveButtons);
		
		//Add the display panel, row one two and three panels to the JFrame.
		calculatorWindow.add(displayPanel);
		calculatorWindow.add(rowOneButtons);
		calculatorWindow.add(rowTwoButtons);
		calculatorWindow.add(rowThreeButtons);
		
		//Set the display string to 0.
		calculatorDisplay = "0";
		
		//Set the firstEntry boolean to true.
		firstEntry = true;
		//Set equals flag boolean to false.
		equals = false;
		//Set the lastOperator to 0 for no operator.
		lastOperator = 0;
		//Set the lastNumber, result and memory to zero.
		lastNumber = BigDecimal.ZERO;
		result = BigDecimal.ZERO;
		memory = BigDecimal.ZERO;
		
		//Set display area text to the display string.
		displayArea.setText(calculatorDisplay);
		//Make calculator JFrame visible.
		calculatorWindow.setVisible(true);
	}
	
	/**
	 * Method to attempt to set the look and feel of the Swing Objects to the
	 * Nimbus look and feel included in the Java 1.7 JDK.
	 */
	public void setDesign()
	{
		//Use a try catch block to catch any exception thrown.
		try 
		{
			//call the setLookAndFeel() method from the UIManager class.
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e)
		{  
			//Print out stack trace error message if we encounter an exception.
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to clear all from the screen, and the previous operations.
	 * This method will set the operator variable to zero, set both the
	 * result and lastNumber variables to zero, set the display string to display 0
	 * and set the equals flag to false and the firstEntry variable to true.
	 */
	private void clear()
	{
		//Set last operator to 0.
		lastOperator = 0;
		//Set equals flag to false.
		equals = false;
		//Set firstEntry flag to true.
		firstEntry = true;
		
		//Set lastNumber and result to zero.
		lastNumber = BigDecimal.ZERO;
		result = BigDecimal.ZERO;
		
		//Set display string to zero and update the display area with that string.
		calculatorDisplay = "0";
		displayArea.setText(calculatorDisplay);
	}
	
	/**
	 * This method will only clear out the number displayed currently on the screen.
	 */
	private void clearEntry()
	{
		//Set the display string to zero.
		calculatorDisplay = "0";
		//Update the display area textfield to the display string.
		displayArea.setText(calculatorDisplay);
		
		//Set firstEntry flag to true.
		firstEntry = true;
	}
	
	/**
	 * This method will add the provided character (in String format) to the
	 * display string and update the display.
	 * 
	 * @param character The character desired to add to the display string.
	 */
	public void addToDisplay(String character)
	{
		/**
		 * If equals or firstEntry is true, then we need to only add the character passed to
		 * this method to the display String as there will already be a zero there. So we ignore the
		 * zero and just set the display string to the character passed to the method and set equals to false.
		 */
		if (equals || firstEntry)
		{
			calculatorDisplay = character;
			equals = false;
		}
		//If neither is true, then we just add the character passed to the method to the display string.
		else
			calculatorDisplay += character;
		
		//Update the display textfield with the display string.
		displayArea.setText(calculatorDisplay);
		
		//Set first entry flag to false.
		firstEntry = false;
	}
	
	/**
	 * This method will process the last operator if there is one pending, and then queue up the operator
	 * that is passed to this method. It will only process the last operator if the lastOperator variable
	 * equals one or six (corresponding to no operation or a repeated equals operation).
	 * 
	 * @param operator byte number corresponding to the desired operation.
	 * @throws NumberFormatException
	 */
	private void calculateOperator(byte operator) throws NumberFormatException
	{
		//Set the display string to the text showed in the display textfield.
		calculatorDisplay = displayArea.getText();
		//Create a BigDecimal variable to store the current number in.
		BigDecimal currentNumber;
		
		//If firstEntry flag is true, show an error dialog saying you must input a number before the operator.
		if (firstEntry)
		{
			JOptionPane.showMessageDialog(null, "Error: You must input a number before an operator!", 
												"Error!", JOptionPane.ERROR_MESSAGE);
			//Clear the screen out
			clear();
			
			//Return so that no operation takes place.
			return;
		}
		/**
		 * Else we must try to set the currentNumber variable, and catch NumberFormatException
		 * to check for malformed expression (i.e. multiple decimal points, odd characters, etc.)
		 */
		else
		{
			try
			{
				//Try to set the currentNumber to the display string.
				currentNumber = new BigDecimal(calculatorDisplay);
			} catch (NumberFormatException exception)
			{
				//If we catch an exception, show a error dialog box and clear the screen out and return.
				JOptionPane.showMessageDialog(null, "Error: Malformed expression!!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				clear();
				
				return;
			}
		}
		
		/**
		 * If the last operator doesn't equal zero or six then we have a pending operation
		 * in queue, so we set the result equal to the the BigDecimal returned from the
		 * calculateLastOperator() method. And set the lastNumber variable to the result.
		 */
		if (lastOperator != 0 && lastOperator != 6)
		{
			result = calculateLastOperator();
			lastNumber = result;
		}
		//If there is no pending operator, set the lastNumber to the currentNumber.
		else
			lastNumber = currentNumber;
		
		//Set the calculator display string to the lastNumber by calling toEngineeringString()
		calculatorDisplay = lastNumber.toEngineeringString();
		//Update the display textfield using the display string.
		displayArea.setText(calculatorDisplay);
		
		//Set the lastOperator variable to the operator passed to it.
		lastOperator = operator;
		//Blank out the calculator display so you can enter a new number.
		calculatorDisplay = "";
	}
	
	/**
	 * This method calculates the pending operation when it is called from the calculateOperator or
	 * equals functions. It returns the BigDecimal result of the operation.
	 * 
	 * @return the BigDecimal value of the operation performed.
	 * @throws NumberFormatException
	 */
	private BigDecimal calculateLastOperator() throws NumberFormatException
	{
		//Pull in the displayed text and save it into the display string.
		calculatorDisplay = displayArea.getText();
		//Create a BigDecimal to store the current number in.
		BigDecimal currentNumber;
		
		//If firstEntry flag is true, show an error dialog saying you must input a number before the operator.
		if (firstEntry)
		{
			JOptionPane.showMessageDialog(null, "Error: You must input a number before an operator!", 
												"Error!", JOptionPane.ERROR_MESSAGE);
			//Clear the screen out.
			clear();
			
			//Return 0.
			return BigDecimal.ZERO;
		}
		/**
		 * Else we must try to set the currentNumber variable, and catch NumberFormatException
		 * to check for malformed expression (i.e. multiple decimal points, odd characters, etc.)
		 */
		else
		{
			try
			{
				//Try to set the currentNumber to the display string.
				currentNumber = new BigDecimal(calculatorDisplay);
			} catch (NumberFormatException exception)
			{
				//If we catch an exception, show a error dialog box and clear the screen out and return 0.
				JOptionPane.showMessageDialog(null, "Error: Malformed expression!!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				clear();
				
				return BigDecimal.ZERO;
			}
		}
		
		//If lastOperator equals 0 (no operation) return the currentNumber.
		if (lastOperator == 0)
			return currentNumber;
		
		/**
		 * If lastOperator equals 6, we are dealing with the repeated equals, so we must check what the
		 * repeatedLastOp variable is.
		 */
		if (lastOperator == 6)
		{
			//Power function.
			if (repeatedLastOp == 5)
			{
				//Use try catch to handle any infinite or NaN possibilities.
				try
				{
					//Set result equal to the currentNumber to the power of the repeatedLastNum value.
					result = new BigDecimal(Math.pow(currentNumber.doubleValue(), repeatedLastNum.doubleValue()));
				} catch (NumberFormatException exception)
				{
					//If we catch an exception, show a error dialog, clear out the display and set result to zero.
					System.out.println("Number format was invalid, INFINITE or NaN!");
					JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
							"Error!", JOptionPane.ERROR_MESSAGE);
					clear();
					
					result = BigDecimal.ZERO;
				}
			}
			//Division function.
			if (repeatedLastOp == 4)
			{
				//Check to make sure the currentNumber isn't zero.
				if (!currentNumber.equals(BigDecimal.ZERO))
				{
					//use a try/catch to check for non terminating decimal results.
					try
					{
						result = currentNumber.divide(repeatedLastNum);
					} catch (ArithmeticException exception)
					{
						//If we catch an ArithmeticException, we must round the result to 10 decimal places.
						System.out.println("Non-terminating decimal answer. Rounding to 10 decimal places!");
					
						//Round to 10 decimal places.
						result = currentNumber.divide(repeatedLastNum, 10, RoundingMode.CEILING);
					} catch (NumberFormatException exception)
					{
						//Catch NumberFormatException for infinite or NaN exceptions.
						System.out.println("Number format was invalid, INFINITE or NaN!");
						JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
								"Error!", JOptionPane.ERROR_MESSAGE);
						//Clear and set result to zero.
						clear();
						
						result = BigDecimal.ZERO;
					}
				}
				//Else we have a zero as a divisor, so display an error and then clear and return zero.
				else
				{
					JOptionPane.showMessageDialog(null, "Error: Divide by zero!", "Error!", JOptionPane.ERROR_MESSAGE);
					clear();
					
					return BigDecimal.ZERO;
				}
			}
			//Multiplication function.
			if (repeatedLastOp == 3)
			{
				//Use try catch for infinite or NaN results.
				try
				{
					result = currentNumber.multiply(repeatedLastNum);
				} catch (NumberFormatException exception)
				{
					//If we catch an exception, display a error dialog and clear and set result to zero.
					System.out.println("Number format was invalid, INFINITE or NaN!");
					JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
							"Error!", JOptionPane.ERROR_MESSAGE);
					clear();
					
					result = BigDecimal.ZERO;
				}
			}
			//Subtraction function.
			if (repeatedLastOp == 2)
			{
				//Use try catch for infinite or NaN results.
				try
				{
					result = currentNumber.subtract(repeatedLastNum);
				} catch (NumberFormatException exception)
				{
					//If we catch an exception, display a error dialog and clear and set result to zero.
					System.out.println("Number format was invalid, INFINITE or NaN!");
					JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
							"Error!", JOptionPane.ERROR_MESSAGE);
					clear();
					
					result = BigDecimal.ZERO;
				}
			}
			//Addition function.
			if (repeatedLastOp == 1)
			{
				//Use try catch for infinite or NaN results.
				try
				{
					result = currentNumber.add(repeatedLastNum);
				} catch (NumberFormatException exception)
				{
					//If we catch an exception, display a error dialog and clear and set result to zero.
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
		
		//Power function.
		if (lastOperator == 5)
		{
			//Use try catch to handle infinite or NaN results.
			try
			{
				result = new BigDecimal(Math.pow(lastNumber.doubleValue(), currentNumber.doubleValue()));
			} catch (NumberFormatException exception)
			{
				//If we catch an exception, display error dialog, clear and set result to zero.
				System.out.println("Number format was invalid, INFINITE or NaN!");
				JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				clear();
				
				result = BigDecimal.ZERO;
			}
		}
		//Division function.
		if (lastOperator == 4)
		{
			//Make sure the divisor isn't zero.
			if (!currentNumber.equals(BigDecimal.ZERO))
			{
				//Use try catch to handle infinite, NaN, or repeating decimal results.
				try
				{
					result = lastNumber.divide(currentNumber);
				} catch (ArithmeticException exception)
				{
					//If we catch an ArithmeticException, round to 10 decimal places.
					System.out.println("Non-terminating decimal answer. Rounding to 10 decimal places!");
				
					result = lastNumber.divide(currentNumber, 10, RoundingMode.CEILING);
				} catch (NumberFormatException exception)
				{
					//If we catch a NumberFormatException, display an error dialog, clear and set result to zero.
					System.out.println("Number format was invalid, INFINITE or NaN!");
					JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
							"Error!", JOptionPane.ERROR_MESSAGE);
					clear();
					
					result = BigDecimal.ZERO;
				}
			}
			//Else we have zero as a divisor, show error dialog, clear and return zero.
			else
			{
				JOptionPane.showMessageDialog(null, "Error: Divide by zero!", "Error!", JOptionPane.ERROR_MESSAGE);
				clear();
				
				return BigDecimal.ZERO;
			}
		}
		//Multiplication function.
		if (lastOperator == 3)
		{
			//Use try catch to handle infinite or NaN results.
			try
			{
				result = lastNumber.multiply(currentNumber);
			} catch (NumberFormatException exception)
			{
				//If we catch an exception, display an error dialog, clear and set result equal to zero.
				System.out.println("Number format was invalid, INFINITE or NaN!");
				JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				clear();
				
				result = BigDecimal.ZERO;
			}
		}
		//Subtraction function.
		if (lastOperator == 2)
		{
			//Use try catch to handle infinite or NaN results.
			try
			{
				result = lastNumber.subtract(currentNumber);
			} catch (NumberFormatException exception)
			{
				//If we catch an exception, display an error dialog, clear and set result to zero.
				System.out.println("Number format was invalid, INFINITE or NaN!");
				JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				clear();
				
				result = BigDecimal.ZERO;
			}
		}
		//Addition function.
		if (lastOperator == 1)
		{
			//Use try catch to handle infinite or NaN results.
			try
			{
				result = lastNumber.add(currentNumber);
			} catch (NumberFormatException exception)
			{
				//If we catch an exception, display an error dialog, clear and set result to zero.
				System.out.println("Number format was invalid, INFINITE or NaN!");
				JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				clear();
				
				result = BigDecimal.ZERO;
			}
		}
		
		//Set the lastNumber to the currentNumber.		
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
		//Return the result BigDecimal.
		return result;
	}

	@Override
	/**
	 * This is the ActionListener interface method that handles the ActionListener functions of all the buttons.
	 * We use if statements to check the source of the event and perform actions based on what button was pressed.
	 * 
	 * @param e ActionEvent sent to the method from the button.
	 * @throws NumberFormatException 
	 */
	public void actionPerformed(ActionEvent e) throws NumberFormatException
	{
		/**
		 * ================= Input Button Functions =================
		 */
		//For each input button, we call the addToDisplay function passing the button character to it.
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
		{
			//Pull in the current number displayed.
			calculatorDisplay = displayArea.getText();
			
			//Check to see if firstEntry is true, if so display an error dialog and clear the entry and return.
			if (firstEntry)
			{
				JOptionPane.showMessageDialog(null, "Error: You must input a number before an operator!", 
													"Error!", JOptionPane.ERROR_MESSAGE);
				clearEntry();
				
				return;
			}
			//Else we need to negate the current number displayed and redisplayed its negated value.
			else
			{
				//Use try catch to handle infinite or NaN results.
				try
				{
					//Multiply it by negative 1 to negate it.
					calculatorDisplay = new BigDecimal(calculatorDisplay).multiply(BigDecimal.valueOf(-1)).toString();
				} catch (NumberFormatException exception)
				{
					//If we catch an exception, display an error dialog, and then clear the entry and return.
					System.out.println("Number format was invalid, INFINITE or NaN!");
					JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
							"Error!", JOptionPane.ERROR_MESSAGE);
					
					clearEntry();
					
					return;
				}
			}
			
			//Update the display textfield.
			displayArea.setText(calculatorDisplay);
		}
		
		/**
		 * ================= Special Functions =================
		 */
		if (e.getSource() == sqrtButton)
		{
			calculatorDisplay = displayArea.getText();
			
			if (firstEntry)
			{
				JOptionPane.showMessageDialog(null, "Error: You must input a number before an operator!", 
													"Error!", JOptionPane.ERROR_MESSAGE);
				clearEntry();
				
				return;
			}
			else
			{
				try
				{
					lastNumber = new BigDecimal(calculatorDisplay);
				} catch (NumberFormatException exception)
				{
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
			else
			{
				try
				{
					lastNumber = BigDecimal.valueOf(Math.sqrt(lastNumber.doubleValue()));
				} catch (NumberFormatException exception)
				{
					JOptionPane.showMessageDialog(null, "Error: Infinite/NaN result!", 
							"Error!", JOptionPane.ERROR_MESSAGE);
					clear();
					
					lastNumber = BigDecimal.ZERO;
				}
			}
			
			calculatorDisplay = lastNumber.toString();
			displayArea.setText(calculatorDisplay);
			
			equals = true;
		}
		if (e.getSource() == onexButton)
		{
			calculatorDisplay = displayArea.getText();
			
			if (firstEntry)
			{
				JOptionPane.showMessageDialog(null, "Error: You must input a number before an operator!", 
													"Error!", JOptionPane.ERROR_MESSAGE);
				clearEntry();
				
				return;
			}
			else
			{
				try
				{
					lastNumber = new BigDecimal(calculatorDisplay);
				} catch (NumberFormatException exception)
				{
					JOptionPane.showMessageDialog(null, "Error: Malformed expression!!", 
							"Error!", JOptionPane.ERROR_MESSAGE);
					
					calculatorDisplay = "0";
					displayArea.setText(calculatorDisplay);
					
					firstEntry = true;
					
					return;
				}
			}
			
			if (!lastNumber.equals(BigDecimal.ZERO))
			{
				try
				{
					lastNumber = BigDecimal.ONE.divide(lastNumber);
				} catch (ArithmeticException exception)
				{
					System.out.println("Non-terminating decimal answer. Rounding to 10 decimal places!");
				
					lastNumber = BigDecimal.ONE.divide(lastNumber, 10, RoundingMode.CEILING);
				} catch (NumberFormatException exception)
				{
					JOptionPane.showMessageDialog(null, "Error: Infinite/NaN result!", 
							"Error!", JOptionPane.ERROR_MESSAGE);
				
					lastNumber = BigDecimal.ZERO;
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Error: Divide by zero!", "Error!", JOptionPane.ERROR_MESSAGE);
				clear();
			}
			
			calculatorDisplay = lastNumber.toString();
			displayArea.setText(calculatorDisplay);
			
			equals = true;
		}
		if (e.getSource() == percentButton)
		{
			calculatorDisplay = displayArea.getText();
			
			if (firstEntry)
			{
				JOptionPane.showMessageDialog(null, "Error: You must input a number before an operator!", 
													"Error!", JOptionPane.ERROR_MESSAGE);
				clearEntry();
				
				return;
			}
			else
			{
				try
				{
					lastNumber = new BigDecimal(calculatorDisplay);
				} catch (NumberFormatException exception)
				{
					JOptionPane.showMessageDialog(null, "Error: Malformed expression!!", 
							"Error!", JOptionPane.ERROR_MESSAGE);
					
					calculatorDisplay = "0";
					displayArea.setText(calculatorDisplay);
					
					firstEntry = true;
					
					return;
				}
			}
			
			try
			{
				lastNumber = lastNumber.divide(BigDecimal.valueOf(100));
			} catch (NumberFormatException exception)
			{
				JOptionPane.showMessageDialog(null, "Error: Infinite/NaN result!", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				clear();
				
				lastNumber = BigDecimal.ZERO;
			}
			
			calculatorDisplay = lastNumber.toString();
			displayArea.setText(calculatorDisplay);
			
			equals = true;
		}
		
		/**
		 * ================= Operational Functions =================
		 */
		//Call the calculateOperator() method passing the byte value corresponding to the desired operation.
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
		if (e.getSource() == equalsButton)
		{
			/**
			 * Set the result equal to the result from the calculateLastOperator() method so that we 
			 * take into account the pending operations.
			 */
			result = calculateLastOperator();
			
			//Set the display string to the result and update the display textfield.
			calculatorDisplay = result.toEngineeringString();
			displayArea.setText(calculatorDisplay);
			
			//Set equals flag to true.
			equals = true;
			//Set lastOperator to 6 in case of repeated equals pressing.
			lastOperator = 6;
		}
		
		/**
		 * ================= Memory Functions ================= 
		 */
		if (e.getSource() == mcButton)
			memory = BigDecimal.ZERO;
		if (e.getSource() == msButton)
		{
			calculatorDisplay = displayArea.getText();
			
			if (firstEntry)
			{
				JOptionPane.showMessageDialog(null, "Error: You must input a number before an operator!", 
													"Error!", JOptionPane.ERROR_MESSAGE);
				
				clearEntry();
				
				return;
			}
			else
			{
				try
				{
					memory = new BigDecimal(calculatorDisplay);
				} catch (NumberFormatException exception)
				{
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
		if (e.getSource() == mPlusButton)
		{
			calculatorDisplay = displayArea.getText();
			
			if (firstEntry)
			{
				JOptionPane.showMessageDialog(null, "Error: You must input a number before an operator!", 
													"Error!", JOptionPane.ERROR_MESSAGE);
				
				clearEntry();
				
				return;
			}
			else
			{
				try
				{
					memory = memory.add(new BigDecimal(calculatorDisplay));
				} catch (NumberFormatException exception)
				{
					JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
							"Error!", JOptionPane.ERROR_MESSAGE);
					
					memory = BigDecimal.ZERO;
				}
			}
			
			calculatorDisplay = "0";
			displayArea.setText(calculatorDisplay);
			
			firstEntry = true;
		}
		if (e.getSource() == mMinusButton)
		{
			calculatorDisplay = displayArea.getText();
			
			if (firstEntry)
			{
				JOptionPane.showMessageDialog(null, "Error: You must input a number before an operator!", 
													"Error!", JOptionPane.ERROR_MESSAGE);
				
				clearEntry();
				
				return;
			}
			else
			{
				try
				{
					memory = memory.subtract(new BigDecimal(calculatorDisplay));
				} catch (NumberFormatException exception)
				{
					JOptionPane.showMessageDialog(null, "Error: Invalid number format, or Infinite/NaN result!", 
							"Error!", JOptionPane.ERROR_MESSAGE);
					
					memory = BigDecimal.ZERO;
				}
			}
			
			calculatorDisplay = "0";
			displayArea.setText(calculatorDisplay);
			
			firstEntry = true;
		}
		//Memory recall button, set the display string to the value in stored in memory, then update display.
		if (e.getSource() == mrButton)
		{
			//Set the display string to the BigDecimal stored in memory.
			calculatorDisplay = memory.toString();
			//Update the displayArea.
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
		if (e.getSource() == deleteButton)
		{
			//Check firstEntry boolean and the length of the display string.
			if (!firstEntry && calculatorDisplay.length() > 0)
			{
				//If the length is one, just change it to display 0 and set firstEntry to true.
				if (calculatorDisplay.length() == 1)
				{
					calculatorDisplay = "0";
					firstEntry = true;
				}
				//Else remove the last character from the display string.
				else
					calculatorDisplay = calculatorDisplay.substring(0, calculatorDisplay.length()-1);
				//Update the displayArea with the new string.
				displayArea.setText(calculatorDisplay);
			}
		}
		//Clear all button. Call the clear() method.
		if (e.getSource() == cButton)
			clear();
		//Clear entry button, call the clearEntry() method.
		if(e.getSource() == ceButton)
			clearEntry();
	}
}