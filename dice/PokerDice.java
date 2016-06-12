///////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////
// This is the PokerDice class which is used to create the game logic for rolling the 
// dice. The dice are kept in an array which then gets rolled.  This also contains the
// main class which handles the errors and runs the program.
//////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////

package cs242.cryptobiotic.dice;

import java.util.Arrays;

import javax.swing.*;

/*import javax.sound.sampled.*;
 import java.io.*;
 import javax.swing.*;
 import java.applet.*;
 import java.net.*;*/

public class PokerDice
{
	Die die1;
	Die die2;
	Die die3;
	Die die4;
	Die die5;

	boolean fullhouse = false;

	String load_factor_input;
	String input;
	String loadedSideString;

	int counter = 0;
	int loadedSide;
	int default_sides;
	int intdefaultOr_No;
	int input_number_of_sides = 6;

	Die[] diceArray = new Die[5];

	static PokerGUI gui = new PokerGUI();

	public static void main(String[] args)
	{
		int n_sides=13;
		/*
		int index = 0;

		String n_sides_ = JOptionPane
				.showInputDialog("How many sides do you want the dice to have? (Enter '0' for the default of 6)");
		// This if statement doesn't work
		if (n_sides_ == null)
		{
			throw new NumberFormatException();
		}
		int n_sides = Integer.parseInt(n_sides_);

		if (n_sides == 0)
		{
			n_sides = 6;
		}

		String default_sides_input = JOptionPane
				.showInputDialog("Do you want any loaded dice? (1 for yes, 0 for no)");
		int loadedOr_no = Integer.parseInt(default_sides_input);

		try
		{
			if (loadedOr_no == 1)
			{
				String num_loaded_ = JOptionPane
						.showInputDialog("How many loaded dice do you want?");
				int num_loaded = Integer.parseInt(num_loaded_);
				if (num_loaded > 5)
				{
					throw new NumberFormatException();
				} else
				{
					for (int i = index; i < num_loaded; i++)
					{
						String loaded_side_ = JOptionPane
								.showInputDialog("What side do you want to be loaded on die number "
										+ (i + 1) + "?");
						int loaded_side = Integer.parseInt(loaded_side_);
						if (loaded_side > n_sides)
						{
							throw new NumberFormatException();
						}
						String loaded_weight_ = JOptionPane
								.showInputDialog("What weight do you want to set the load to on die number "
										+ (i + 1) + "? (0-100)");
						int loaded_weight = Integer.parseInt(loaded_weight_);
						gui.poker.set_Array(i, n_sides, loaded_side,
								loaded_weight);
						index = i;
					}
				}
			}
			if (gui.poker.diceArray[index] != null)
				index++;
			for (int i = index; i < 5; i++)
			{
				gui.poker.set_Array(i, n_sides);
			}
		} catch (NumberFormatException exc)
		{
			System.out.println("Invalid number.");
			System.exit(0);
		}*/
		for (int i = 0; i < 5; i++)
			{
				gui.poker.set_Array(i, n_sides);
			}
		gui.draw();
	}

	public void set_Array(int element, int sides)
	{
		diceArray[element] = new Die(sides);
	}

	public void set_Array(int element, int sides, int load_side, int load_factor)
	{
		diceArray[element] = new LoadedDie(sides, load_side, load_factor);
	}

	public void arrange_and_check()
	{
		// PokerDice poker = new PokerDice();
		rearrange();
		two_ofaKind();
		three_ofaKind();
		four_ofaKind();
		five_ofaKind();
		two_pairs();
		// threeInaRow();
		kHigh();
		call();
	}

	public void rearrange()
	{
		/**
		 * The rearrange function creates an array of 5 slots that each hold the
		 * top face of the three different dice. The array is then sorted using
		 * the sort function.
		 */

		// Roll the dice with custom numbers of sides.
		for (Die i : diceArray)
		{
			i.roll();
		}

		// Automatically calls compareTo to sort the array (we won't be needing the rearrange for this project.
		//Arrays.sort(diceArray);

		for (Die i : diceArray)
		{
			System.out.println(i.top);
		}
	}

	public boolean two_ofaKind()
	{
		/**
		 * This method checks to see if two of the dice are the same by checking
		 * the first with the second, first with the third, and the second with
		 * the third. If one of those cases comes up true, it returns true. If
		 * there are not two dice that are the same, it returns false.
		 */
		// if there are three in a row rearrange the dice before testing this
		if ((five_ofaKind() == true) || (four_ofaKind() == true) || (three_ofaKind() == true)
				|| (two_pairs() == true))
		{
			return false;
		} else
		{

			if ((diceArray[0].top == diceArray[1].top)
					|| (diceArray[0].top == diceArray[2].top)
					|| (diceArray[0].top == diceArray[3].top)
					|| (diceArray[0].top == diceArray[4].top)
					|| (diceArray[1].top == diceArray[2].top)
					|| (diceArray[1].top == diceArray[3].top)
					|| (diceArray[1].top == diceArray[4].top)
					|| (diceArray[2].top == diceArray[3].top)
					|| (diceArray[2].top == diceArray[4].top)
					|| (diceArray[3].top == diceArray[4].top))

			{
				fullhouse = false;
				return true;
			} else
			{
				return false;
			}
		}
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////
	public boolean three_ofaKind()
	{
		/**
		 * This method checks to see if three of the dice have the same topface.
		 * If they do, it returns true.
		 */
		// if there are three in a row rearrange the dice before testing this
		if ((five_ofaKind() == true) || (four_ofaKind() == true)) //|| (two_ofaKind() == true))
		{
			// do nothing because five of a kind or four of a kind will be
			// called
			return false;
		}
		else
		{
			if ((diceArray[0].top == diceArray[1].top)
			&& (diceArray[1].top == diceArray[2].top)
			|| (diceArray[0].top == diceArray[1].top)
			&& (diceArray[1].top == diceArray[3].top)
			|| (diceArray[0].top == diceArray[1].top)
			&& (diceArray[1].top == diceArray[4].top)
			|| (diceArray[0].top == diceArray[2].top)
			&& (diceArray[2].top == diceArray[3].top)
			|| (diceArray[0].top == diceArray[2].top)
			&& (diceArray[2].top == diceArray[4].top)
			|| (diceArray[0].top == diceArray[3].top)
			&& (diceArray[3].top == diceArray[4].top)
			|| (diceArray[1].top == diceArray[3].top)
			&& (diceArray[3].top == diceArray[4].top)
			|| (diceArray[2].top == diceArray[3].top)
			&& (diceArray[3].top == diceArray[4].top))
			{
				if (two_ofaKind() == true)
				{
					fullhouse = true;
					return false;
				} 
				else
				{
					fullhouse = false;
					return true;
				}
			} 
			else
			{
				return false;
			}
		}
	}

	public boolean four_ofaKind()
	{
		/**
		 * This method checks to see if all three of the dice have the same
		 * topface. If they do, it returns true.
		 */
		// if there are three in a row rearrange the dice before testing this
		if (five_ofaKind() == true)
		{
			return false;
		} else
		{

			if ((diceArray[0].top == diceArray[1].top)
					&& (diceArray[1].top == diceArray[2].top)
					&& (diceArray[2].top == diceArray[3].top)
					|| (diceArray[0].top == diceArray[1].top)
					&& (diceArray[1].top == diceArray[2].top)
					&& (diceArray[2].top == diceArray[4].top)
					|| (diceArray[0].top == diceArray[1].top)
					&& (diceArray[1].top == diceArray[3].top)
					&& (diceArray[3].top == diceArray[4].top)
					|| (diceArray[0].top == diceArray[2].top)
					&& (diceArray[2].top == diceArray[3].top)
					&& (diceArray[3].top == diceArray[4].top)
					|| (diceArray[1].top == diceArray[2].top)
					&& (diceArray[2].top == diceArray[3].top)
					&& (diceArray[3].top == diceArray[4].top))
			{
				fullhouse = false;
				return true;
			} else
			{
				return false;
			}
		}
	}

	public boolean five_ofaKind()
	{
		/**
		 * This method checks to see if all five of the dice have the same
		 * topface. If they do, it returns true.
		 */
		// if there are three in a row rearrange the dice before testing this
		if ((diceArray[0].top == diceArray[1].top)
				&& (diceArray[1].top == diceArray[2].top)
				&& (diceArray[2].top == diceArray[3].top)
				&& (diceArray[3].top == diceArray[4].top))
		{
			fullhouse = false;
			return true;
		} else
		{
			return false;
		}
	}

	public boolean two_pairs()
	{
		if ((four_ofaKind() == true) || (five_ofaKind() == true))
		{
			return false;
		} else
		{

			if ((diceArray[0].top == diceArray[1].top)
					&& (diceArray[2].top == diceArray[3].top)
					|| (diceArray[0].top == diceArray[1].top)
					&& (diceArray[3].top == diceArray[4].top)
					|| (diceArray[1].top == diceArray[2].top)
					&& (diceArray[3].top == diceArray[4].top))
			{
				fullhouse = false;
				return true;
			} else
			{
				return false;
			}
		}
	}

	/*
	 * Don't think we need three in a row
	 * 
	 * public boolean threeInaRow() { /** This method checks to see if the three
	 * dice are in a row by subtracting the second from the third and the first
	 * from the second. If both of those return a 1, then the method returns
	 * true. If not, it returns false.
	 */
	// Find largest, then second largest, then smallest then subtract them
	// from each other
	/*
	 * if ((diceArray[2].top - diceArray[1].top == 1) && (diceArray[1].top -
	 * diceArray[0].top == 1)) { return true; } else { return false; } }
	 */

	// //////////////////////////////////////////////////////////////////////////////////////////////

	public void kHigh()
	{
		/**
		 * This method checks if any of the other methods returned true. If none
		 * of them did, then this function runs and returns the largest of the
		 * dice.
		 */
		if ((five_ofaKind() == false) && (four_ofaKind() == false)
				&& (fullhouse == false) && (two_pairs() == false)
				&& /* (threeInaRow() == false) && */(two_ofaKind() == false)
				&& (three_ofaKind() == false))
		{
			System.out.println("K-High is " + diceArray[4].top);
			gui.setResult("K-High is " + diceArray[4].top);
		}
	}

	public void call()
	{
		/**
		 * The call method checks to see whether or not all the functions
		 * returned true or false. If they returned true, then they are ran
		 * respectively. It also checks whether or not two of a kind and three
		 * of a kind are true, so if three dice are the same it doesn't say they
		 * are two of a kind as well.
		 */
		
		if ((two_pairs() == true) && (three_ofaKind() == true))
		{
			fullhouse = true;
		}

		if (three_ofaKind() == true)
		{
			if (fullhouse == true)
			{
				//do nothing
			}
			else
			{
				System.out.println("Three of a kind");
				gui.setResult("Three of a kind");
			}
		}

		if (two_ofaKind() == true)
		{
			if (fullhouse == true)
			{
				// do nothing
			}
			else
			{
				System.out.println("Two of a kind");
				gui.setResult("Two of a kind");
			}
		}

		if (four_ofaKind() == true)
		{
			System.out.println("Four of a kind");
			gui.setResult("Four of a kind");
		}

		if (five_ofaKind() == true)
		{
			System.out.println("Five of a kind");
			gui.setResult("Five of a kind");
		}

		if (two_pairs() == true)
		{
			if (fullhouse == true)
			{
				// do nothing
			}
			else
			{
				System.out.println("Two pairs");
				gui.setResult("Two pairs");
			}
		}

		if (fullhouse == true)
		{
			System.out.println("Full house");
			gui.setResult("Full House");
		}

		/*
		 * if (threeInaRow() == true) { System.out.println("Three in a row");
		 * gui.setResult("Three in a row"); }
		 */

	}
}
