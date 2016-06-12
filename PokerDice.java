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

public class PokerDice
{
	Die die1;
	Die die2;
	Die die3;
	Die die4;
	Die die5;

	int store1 = -99;
	int store2 = -99;
	int store3 = -99;
	int store4 = -99;
	int store5 = -99;

	int p1points = 0;
	int p1high = 0;
	int p2points = 0;
	int p2high = 0;
	
	Icon Nine = new ImageIcon("9Spades.png");
	Icon Ten = new ImageIcon("10Spades.png");
	Icon Jack = new ImageIcon("JackSpades.png");
	Icon Queen = new ImageIcon("QueenSpades.png");
	Icon King = new ImageIcon("KingSpades.png");
	Icon Ace = new ImageIcon("AceSpades.png");
	Icon RedBack = new ImageIcon("BackRed.png");
	Icon BlueBack = new ImageIcon("BackBlue.png");

	boolean fullhouse = false;

	static String player_one;
	static String player_two;
	String load_factor_input;
	String input;
	String loadedSideString;

	int counter = 0;
	int loadedSide;
	int default_sides;
	int intdefaultOr_No;
	int input_number_of_sides = 6;

	Die[] diceArray = new Die[5];
	Die[] p1finalArray = new Die[5];
	Die[] p2finalArray = new Die[5];

	static PokerGUI gui = new PokerGUI();

	public static void main(String[] args)
	{
		int n_sides = 6;

		player_one = JOptionPane.showInputDialog("Enter in player one name:");

		player_two = JOptionPane.showInputDialog("Enter in player two name:");

		for (int i = 0; i < 5; i++)
		{
			gui.poker.set_Array(i, n_sides);
		}
		gui.draw();
		System.out.println("Back to Main.");
	}

	public void set_Array(int element, int sides)
	{
		/**
		 * The set_Array method creates the dice array for type Die
		 */
		diceArray[element] = new Die(sides);
		p1finalArray[element] = new Die(sides);
		p2finalArray[element] = new Die(sides);
	}

	public void set_Array(int element, int sides, int load_side, int load_factor)
	{
		/**
		 * the set_Array method overrides the other set_Array in case of a
		 * loaded die
		 */
		diceArray[element] = new LoadedDie(sides, load_side, load_factor);
	}

	public void arrange_and_check(boolean r1, boolean r2, boolean r3,
			boolean r4, boolean r5, String player ){

		/**
		 * the arrange_and_check method checks for the check boxes in the gui,
		 * then rolls passes those values to the rearrange function, and calls
		 * the rest of the functions as well
		 */
		// PokerDice poker = new PokerDice();
		rearrange(r1, r2, r3, r4, r5);
		two_ofaKind();
		three_ofaKind();
		four_ofaKind();
		five_ofaKind();
		two_pairs();
		// threeInaRow();
		kHigh();
		setHand(player);
	}

	public void rearrange(boolean r1, boolean r2, boolean r3, boolean r4,
			boolean r5)
	{
		/**
		 * The rearrange function creates an array of 5 slots that each hold the
		 * top face of the three different dice. It checks whether or not the
		 * check boxes are checked off, and rolls the ones that aren't.
		 * 
		 */

		// Roll the dice
		if (!r1)
			diceArray[0].roll();
		if (!r2)
			diceArray[1].roll();
		if (!r3)
			diceArray[2].roll();
		if (!r4)
			diceArray[3].roll();
		if (!r5)
			diceArray[4].roll();

		Arrays.sort(diceArray);

		store1 = diceArray[0].top;
		store2 = diceArray[1].top;
		store3 = diceArray[2].top;
		store4 = diceArray[3].top;
		store5 = diceArray[4].top;

		for (Die i : diceArray)
		{
			//System.out.println("Store 1: " + store1);
			System.out.println(i.top);
		}
	}

	public Icon toCard(Die die)
	{
		/**
		 * the toCard method sets the corresponding number to a card face of
		 * type Icon
		 */
		Icon card = null;

		if (die.top == 1)
		{
			card = Nine;
		} else if (die.top == 2)
		{
			card = Ten;
		} else if (die.top == 3)
		{
			card = Jack;
		} else if (die.top == 4)
		{
			card = Queen;
		} else if (die.top == 5)
		{
			card = King;
		} else if (die.top == 6)
		{
			card = Ace;
		}
		return card;
	}

	public boolean two_ofaKind()
	{
		/**
		 * This method checks to see if two of the dice are the same by checking
		 * the first with the second, first with the third, and the second with
		 * the third. If one of those cases comes up true, it returns true. If
		 * there are not two dice that are the same, it returns false.
		 */
		if ((five_ofaKind() == true) || (four_ofaKind() == true)
				|| (two_pairs() == true))// || (three_ofaKind() == true))
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
				return true;
			}
			 			 
		}
		return false;
	}

	public boolean three_ofaKind()
	{
		/**
		 * This method checks to see if three of the dice have the same topface.
		 * If they do, it returns true.
		 */
		// if there are three in a row rearrange the dice before testing this
		if ((five_ofaKind() == true) || (four_ofaKind() == true))// ||
																	// (two_ofaKind()
																	// == true))
		{
			// do nothing because five of a kind or four of a kind will be
			// called
			return false;
		} else
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
					|| (diceArray[1].top == diceArray[2].top)
					&& (diceArray[2].top == diceArray[3].top)
					|| (diceArray[1].top == diceArray[3].top)
					&& (diceArray[3].top == diceArray[4].top)
					|| (diceArray[1].top == diceArray[2].top)
					&& (diceArray[2].top == diceArray[4].top)
					|| (diceArray[2].top == diceArray[3].top)
					&& (diceArray[3].top == diceArray[4].top))
			{
				return true;
			}
		}

		return false;
	}

	public boolean four_ofaKind()
	{
		/**
		 * This method checks to see if all three of the dice have the same
		 * topface. If they do, it returns true.
		 */
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

		if ((diceArray[0].top == diceArray[1].top)
				&& (diceArray[1].top == diceArray[2].top)
				&& (diceArray[2].top == diceArray[3].top)
				&& (diceArray[3].top == diceArray[4].top))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean two_pairs()
	{
		/**
		 * the two_pairs function checks if there are two pairs of two. If there
		 * are it returns true.
		 */
		if ((diceArray[0].top == diceArray[1].top)
				&& (diceArray[2].top == diceArray[3].top)
				// ///////////////////////////////////////
				|| (diceArray[0].top == diceArray[1].top)
				&& (diceArray[2].top == diceArray[4].top)
				// ///////////////////////////////////////
				|| (diceArray[0].top == diceArray[1].top)
				&& (diceArray[3].top == diceArray[4].top)
				// ///////////////////////////////////////
				|| (diceArray[0].top == diceArray[2].top)
				&& (diceArray[3].top == diceArray[4].top)
				// ///////////////////////////////////////
				|| (diceArray[0].top == diceArray[2].top)
				&& (diceArray[1].top == diceArray[3].top)
				// ///////////////////////////////////////
				|| (diceArray[0].top == diceArray[2].top)
				&& (diceArray[1].top == diceArray[4].top)
				// ///////////////////////////////////////
				|| (diceArray[0].top == diceArray[3].top)
				&& (diceArray[1].top == diceArray[2].top)
				// ///////////////////////////////////////
				|| (diceArray[0].top == diceArray[3].top)
				&& (diceArray[1].top == diceArray[4].top)
				// ///////////////////////////////////////
				|| (diceArray[0].top == diceArray[3].top)
				&& (diceArray[2].top == diceArray[4].top)
				// ///////////////////////////////////////
				|| (diceArray[0].top == diceArray[4].top)
				&& (diceArray[1].top == diceArray[2].top)
				// ///////////////////////////////////////
				|| (diceArray[0].top == diceArray[4].top)
				&& (diceArray[1].top == diceArray[3].top)
				// ///////////////////////////////////////
				|| (diceArray[0].top == diceArray[4].top)
				&& (diceArray[2].top == diceArray[3].top)
				// ///////////////////////////////////////
				|| (diceArray[1].top == diceArray[4].top)
				&& (diceArray[2].top == diceArray[3].top)
				// ///////////////////////////////////////
				|| (diceArray[1].top == diceArray[4].top)
				&& (diceArray[0].top == diceArray[3].top)
				// ///////////////////////////////////////
				|| (diceArray[1].top == diceArray[4].top)
				&& (diceArray[0].top == diceArray[2].top)
				// ///////////////////////////////////////
				|| (diceArray[2].top == diceArray[4].top)
				&& (diceArray[1].top == diceArray[3].top)
				// ///////////////////////////////////////
				|| (diceArray[2].top == diceArray[4].top)
				&& (diceArray[0].top == diceArray[3].top)
				// ///////////////////////////////////////
				|| (diceArray[2].top == diceArray[4].top)
				&& (diceArray[0].top == diceArray[1].top)
				// ///////////////////////////////////////
				|| (diceArray[3].top == diceArray[4].top)
				&& (diceArray[1].top == diceArray[2].top)
				// ///////////////////////////////////////
				|| (diceArray[3].top == diceArray[4].top)
				&& (diceArray[0].top == diceArray[2].top)
				// ///////////////////////////////////////
				|| (diceArray[3].top == diceArray[4].top)
				&& (diceArray[0].top == diceArray[1].top))

		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean fullHouse()
	{
		/**
		 * the fullHouse function checks to see if there is two of a kind and
		 * three of a kind. If there are it returns true.
		 */
		if ((diceArray[0].top == diceArray[1].top)
				&& (diceArray[1].top == diceArray[2].top)
				&& (diceArray[3].top == diceArray[4].top)
				||
				// ///////////////////////////////////////
				(diceArray[0].top == diceArray[1].top)
				&& (diceArray[1].top == diceArray[3].top)
				&& (diceArray[2].top == diceArray[4].top)
				||
				// ///////////////////////////////////////
				(diceArray[0].top == diceArray[1].top)
				&& (diceArray[1].top == diceArray[4].top)
				&& (diceArray[2].top == diceArray[3].top)
				||
				// ///////////////////////////////////////
				(diceArray[0].top == diceArray[3].top)
				&& (diceArray[3].top == diceArray[4].top)
				&& (diceArray[1].top == diceArray[2].top)
				||
				// ///////////////////////////////////////
				(diceArray[2].top == diceArray[3].top)
				&& (diceArray[3].top == diceArray[4].top)
				&& (diceArray[0].top == diceArray[1].top)
				||
				// ///////////////////////////////////////
				(diceArray[1].top == diceArray[3].top)
				&& (diceArray[3].top == diceArray[4].top)
				&& (diceArray[0].top == diceArray[2].top)
				||
				// ///////////////////////////////////////
				(diceArray[1].top == diceArray[2].top)
				&& (diceArray[2].top == diceArray[4].top)
				&& (diceArray[0].top == diceArray[3].top)
				||
				// ///////////////////////////////////////
				(diceArray[1].top == diceArray[2].top)
				&& (diceArray[2].top == diceArray[3].top)
				&& (diceArray[0].top == diceArray[4].top)
				||
				// ///////////////////////////////////////
				(diceArray[0].top == diceArray[2].top)
				&& (diceArray[2].top == diceArray[3].top)
				&& (diceArray[1].top == diceArray[4].top)
				||
				// ///////////////////////////////////////
				(diceArray[0].top == diceArray[1].top)
				&& (diceArray[1].top == diceArray[3].top)
				&& (diceArray[2].top == diceArray[4].top))
		{
			return true;
		} else
			return false;
	}

	public String toName(Die die)
	{
		/**
		 * The method toName sets the name of the card for each corresponding
		 * number so it can be used to to see what card was high.
		 */
		String name = "";
		if (diceArray[4].top == 1)
		{
			name = "Nine";
		} else if (diceArray[4].top == 2)
		{
			name = "Ten";
		} else if (diceArray[4].top == 3)
		{
			name = "Jack";
		} else if (diceArray[4].top == 4)
		{
			name = "Queen";
		} else if (diceArray[4].top == 5)
		{
			name = "King";
		} else if (diceArray[4].top == 6)
		{
			name = "Ace";
		}
		return name;
	}
	
	public int getHighCardPoint(Die die)
	{
		return die.top;
	}

	public boolean kHigh()
	{
		/**
		 * This method checks if any of the other methods returned true. If none
		 * of them did, then this function runs and returns the largest of the
		 * dice.
		 */
		if ((five_ofaKind() == false) && (four_ofaKind() == false)
				&& (fullhouse == false) && (two_pairs() == false)
				&& (two_ofaKind() == false) && (three_ofaKind() == false))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public void setHand(String player)
	{
		/**
		 * The call method checks to see whether or not all the functions
		 * returned true or false. If they returned true, then they are ran
		 * respectively. Which ever function is true is set to the gui.
		 */

		if (five_ofaKind() == true)
		{
			if(player.equals("player1")){
			p1points = 7;
			p1high = getHighCardPoint(diceArray[4]);}
			else{ 
			p2points = 7;
			p2high = getHighCardPoint(diceArray[4]);}

			System.out.println("Five of a kind with " + toName(diceArray[4]) + " high.");
			gui.setResult("Five of a kind with " + toName(diceArray[4]) + " high.");

		} else if (four_ofaKind() == true)
		{
			if(player.equals("player1")){
			p1points = 6;
			p1high = getHighCardPoint(diceArray[4]);}
			else{ 
			p2points = 6;
			p2high = getHighCardPoint(diceArray[4]);}

			System.out.println("Four of a kind with " + toName(diceArray[4]) + " high.");
			gui.setResult("Four of a kind with " + toName(diceArray[4]) + " high.");
		} else if (fullHouse() == true)
		{
			if(player.equals("player1")){
			p1points = 5;
			p1high = getHighCardPoint(diceArray[4]);}
			else{ 
			p2points = 5;
			p2high = getHighCardPoint(diceArray[4]);}

			System.out.println("Full house with " + toName(diceArray[4]) + " high.");
			gui.setResult("Full House with " + toName(diceArray[4]) + " high.");
		} else if (three_ofaKind() == true)
		{
			if(player.equals("player1")){
			p1points = 4;
			p1high = getHighCardPoint(diceArray[4]);}
			else{ 
			p2points = 4;
			p2high = getHighCardPoint(diceArray[4]);}

			System.out.println("Three of a kind with " + toName(diceArray[4]) + " high.");
			gui.setResult("Three of a kind with " + toName(diceArray[4]) + " high.");

		} else if (two_pairs() == true)
		{
			if(player.equals("player1")){
                        p1points = 3;
                        p1high = getHighCardPoint(diceArray[4]);}
                        else{
                        p2points = 3;
                        p2high = getHighCardPoint(diceArray[4]);}

			System.out.println("Two pairs with " + toName(diceArray[4]) + " high.");
			gui.setResult("Two pairs with " + toName(diceArray[4]) + " high.");
		} else if (two_ofaKind() == true)
		{
			if(player.equals("player1")){
                        p1points = 2;
                        p1high = getHighCardPoint(diceArray[4]);}
                        else{
                        p2points = 2;
                        p2high = getHighCardPoint(diceArray[4]);}

			System.out.println("Two of a kind with " + toName(diceArray[4]) + " high.");
			gui.setResult("Two of a kind with " + toName(diceArray[4]) + " high.");
		} else if (kHigh() == true)
		{	
			if(player.equals("player1")){
                        p1points = 0;
                        p1high = getHighCardPoint(diceArray[4]);}
                        else{
                        p2points = 0;
                        p2high = getHighCardPoint(diceArray[4]);}	

			System.out.println("High card is " + toName(diceArray[4]));
			gui.setResult("High card is " + toName(diceArray[4]));
		}
	}
	
	public String checkWinner(){
		if (p1points > p2points)
			return "player1";
		else if (p2points > p1points)
			return "player2";
		else if (p1points == p2points){
			if (p1high > p2high)return "player1";
			else if (p2high > p1high)return "player2";
		}
	return "";
	}
}

