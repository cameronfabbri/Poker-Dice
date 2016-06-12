/////////////////////////////////////////////////////////////////////////////////////
// This is the LoadedDie class, which handles the roll call for when a Die is loaded. 
/////////////////////////////////////////////////////////////////////////////////////


package cs242.cryptobiotic.dice;

public class LoadedDie extends Die
{
	private int myloadFactor;
	private int myloadedSide;

	// Default Constructor
	public LoadedDie()
	{
		this(8, 8, 0);
	}

	LoadedDie(int n, int loadedSide, int loadFactor)
	{
		top = 1;
		number_sides = n;

		this.myloadFactor = loadFactor;
		this.myloadedSide = loadedSide;
		if (number_sides < 4)
		{
			throw new NumberFormatException();
			// throw an exception, catch in main
		}

		if ((loadedSide < 1) || (loadedSide > n))
		{
			throw new NumberFormatException();
		}

		if ((loadFactor < 0) || (loadFactor > 100))
		{
			throw new NumberFormatException();
		}

	}

	/**
	 * The roll function rolls the dice based on the load factor that was chosen.
	 */
	public int roll()
	{
		top = random_die.nextInt(100) + 1;
		if ((top < myloadFactor) || (top == myloadFactor))
		{
			top = myloadedSide;
		}

		if (top > myloadedSide)
		{
			top = random_die.nextInt(number_sides);
			while (top == number_sides)
			{
				top = random_die.nextInt(number_sides);
			}
		}

		return top;
	}
}
