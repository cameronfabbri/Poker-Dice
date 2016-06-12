///////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////
// This is the die class which is used to create a die with a random top face value
// It has all of the basic methods that all classes should have
///////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////

package cs242.cryptobiotic.dice;
import java.util.Random;
import java.lang.Comparable;

@SuppressWarnings("rawtypes")
public class Die implements Comparable
{
	protected int top;
	protected int number_sides = 6;

	protected static Random random_die = new Random();

	/**
	 * The dice class is a virtual die which can be rolled by the Test Die class
	 **/

	// default constructor
	public Die()
	{
		/**
		 * The default constructor sets the sides to size 8.
		 */
		this(6);
	}

	// Constructor setting the number of sides the die has
	public Die(int n)
	{
		/**
		 * If the number of sides is set to anything less than 4, an error is thrown 
		 */
		
		if (n < 5)
		{
			throw new NumberFormatException();
			// throw an exception, catch in main
		}

		number_sides = n;
		top = 1;
	}

	// roll the dice
	public int roll()
	{
		/**
		 * The roll method rolls the die and returns the number on top
		 **/

		top = random_die.nextInt(number_sides) + 1;
		return top;
	}

	public int getTop()
	{
		/**
		 * The method top returns what is on the top face
		 **/

		return top;
	}

	public boolean equals(Object o)
	{
		/**
		 * The method equals() checks to see if two die are equal or not.
		 **/
		
		if (o == this)
			return true;
		if (!(o instanceof Die))
			return false;
		Die other = (Die) o;
		if ((other.number_sides == this.number_sides)
				&& (other.top == this.top))
		{
			return true;
		} else
			return false;
	}

	public String toString()
	{
		/**
		 * The method toString() prints out desired information about an object
		 * to a string
		 **/

		return "The number of sides is " + number_sides + " the top face is " + top; 
	}

	public int hashCode()
	{
		/**
		 * Generates a hash code for a given object based on the top face and
		 * number of sides
		 **/

		int result = 17;
		result = 37 * result + top;
		result = 37 * result + number_sides;

		return result;
	}

	public int compareTo(Object o)
	{

		/**
		 * compareTo() compares two dice, and returns either a 1, -1, or 0
		 * depending on if they are equal
		 **/
		Die other = (Die) o;

		if ((this.top) < (other.top))
		{
			return -1;
		}
		if ((this.top) > (other.top))
		{
			return 1;
		}

		if ((this.number_sides) > (other.number_sides))
		{
			return 1;
		}
		if ((this.number_sides) < (other.number_sides))
		{
			return -1;
		}

		return 0;
	}

}
