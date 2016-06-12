/////////////////////////////////////////////////////////////////////////////////////
// This is the PokerGUI class, which is responsible for creating and showing the GUI 
/////////////////////////////////////////////////////////////////////////////////////

package cs242.cryptobiotic.dice;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class PokerGUI implements ActionListener
{
	// A count variable for the result text box.
	int count = 1;

	PokerDice poker = new PokerDice();

	// Create the frame
	JFrame frame; // = new JFrame("Poker Dice");

	// Create the Panels, and the container panel to later be put inside the
	// frame.
	JPanel container;
	JScrollPane resultsFrame;
	// JPanel resultsFrame;
	JPanel buttonFrame;
	JPanel dContainer;

	JTextArea textArea = new JTextArea(15, 30);
	JTextField d1_text = new JTextField("-");
	JTextField d2_text = new JTextField("-");
	JTextField d3_text = new JTextField("-");
	JTextField d4_text = new JTextField("-");
	JTextField d5_text = new JTextField("-");

	public void createAndShowGUI()
	{

		// Create the frame
		JFrame frame = new JFrame("Poker Dice");
		frame.setBackground(Color.gray);
		ImageIcon img = new ImageIcon("die.png");
		frame.setIconImage(img.getImage());

		// Create the Panels, and the container panel to later be put inside the
		// frame.
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		JScrollPane resultsFrame = new JScrollPane(textArea);
		// JPanel resultsFrame = new JPanel();
		JPanel buttonFrame = new JPanel();
		// sets the frame close action.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create components and put them in the panels, put those panels in the
		// container, and that container in the frame.

		// Create button and add it to the buttonFrame.
		JButton button = new JButton("Roll");
		button.setMnemonic(KeyEvent.VK_I);
		button.addActionListener(this);
		buttonFrame.add(button);

		// Create text area and place it into resultsFrame.
		textArea.setFont(new Font("Serif", Font.PLAIN, 16));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		// textArea.setPreferredSize(new Dimension(400, 150));
		// resultsFrame.add(textArea);

		// Add resultsFrame and buttonFrame to the container frame that contains
		// a BoxLayout on the Y axis.
		container.add(createDie());
		// container.add(dContainer);
		container.add(buttonFrame);
		container.add(resultsFrame);
		frame.add(container, BorderLayout.CENTER);

		// Size the frame.
		frame.pack();
		frame.setResizable(false);

		// Make sure it's visible.
		frame.setVisible(true);

	}

	public Component createDie()
	{

		JPanel d1 = new JPanel();
		JPanel d2 = new JPanel();
		JPanel d3 = new JPanel();
		JPanel d4 = new JPanel();
		JPanel d5 = new JPanel();

		JPanel dContainer = new JPanel();
		dContainer.setLayout(new BoxLayout(dContainer, BoxLayout.X_AXIS));
		// JTextField d1_text = new JTextField("-");
		d1_text.setHorizontalAlignment(JTextField.CENTER);
		d1_text.setBackground(Color.white);
		d1_text.setFont(new Font("Arial", Font.PLAIN, 32));
		d1_text.setEditable(false);
		// JTextField d2_text = new JTextField("-");
		d2_text.setHorizontalAlignment(JTextField.CENTER);
		d2_text.setBackground(Color.white);
		d2_text.setFont(new Font("Arial", Font.PLAIN, 32));
		d2_text.setEditable(false);
		// JTextField d3_text = new JTextField("-");
		d3_text.setHorizontalAlignment(JTextField.CENTER);
		d3_text.setBackground(Color.white);
		d3_text.setFont(new Font("Arial", Font.PLAIN, 32));
		d3_text.setEditable(false);
		// JTextField d4_text = new JTextField("-");
		d4_text.setHorizontalAlignment(JTextField.CENTER);
		d4_text.setBackground(Color.white);
		d4_text.setFont(new Font("Arial", Font.PLAIN, 32));
		d4_text.setEditable(false);
		// JTextField d5_text = new JTextField("-");
		d5_text.setHorizontalAlignment(JTextField.CENTER);
		d5_text.setBackground(Color.white);
		d5_text.setFont(new Font("Arial", Font.PLAIN, 32));
		d5_text.setEditable(false);

		d1_text.setPreferredSize(new Dimension(50, 50));
		d2_text.setPreferredSize(new Dimension(50, 50));
		d3_text.setPreferredSize(new Dimension(50, 50));
		d4_text.setPreferredSize(new Dimension(50, 50));
		d5_text.setPreferredSize(new Dimension(50, 50));

		d1.add(d1_text);
		d2.add(d2_text);
		d3.add(d3_text);
		d4.add(d4_text);
		d5.add(d5_text);

		dContainer.add(d1);
		dContainer.add(d2);
		dContainer.add(d3);
		dContainer.add(d4);
		dContainer.add(d5);

		return dContainer;
	}

	/**
	 * The actionPerformed is the action listener for the GUI. When the roll
	 * button is clicked it calls the arrange_and_check function and also
	 * creates a sound that plays on each roll
	 */
	public void actionPerformed(ActionEvent e)
	{
		poker.arrange_and_check();
		setFaces(poker.diceArray[0].top, poker.diceArray[1].top,
				poker.diceArray[2].top, poker.diceArray[3].top,
				poker.diceArray[4].top);
		try
		{
			File sound = new File("roll.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(sound);
			Clip audioClip = AudioSystem.getClip();
			audioClip.open(audioIn);
			audioClip.start();
		} catch (UnsupportedAudioFileException exc)
		{
			exc.printStackTrace();
		} catch (IOException exc)
		{
			exc.printStackTrace();
		} catch (LineUnavailableException exc)
		{
			exc.printStackTrace();
		}

	}

	/**
	 * The setFaces function takes the topface of each die and uses toString to
	 * set it to the text field to be displayed
	 * 
	 * @param d1_top
	 * @param d2_top
	 * @param d3_top
	 * @param d4_top
	 * @param d5_top
	 */
	public void setFaces(int d1_top, int d2_top, int d3_top, int d4_top,
			int d5_top)
	{
		// String numberString = Integer.toString(number);
		d1_text.setText(Integer.toString(d1_top));
		d2_text.setText(Integer.toString(d2_top));
		d3_text.setText(Integer.toString(d3_top));
		d4_text.setText(Integer.toString(d4_top));
		d5_text.setText(Integer.toString(d5_top));
	}

	/**
	 * The setResult function displays the type of roll for the corresponding
	 * roll It sets a counter so every roll is kept track of.
	 */
	public void setResult(String result)
	{
		textArea.setText((count + ". ") + (result + "\n") + textArea.getText());
		textArea.setCaretPosition(0);
		count++;
	}

	/**
	 * The drow function creates and draws the GUI.
	 */
	public void draw()
	{
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.

		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				createAndShowGUI();
			}
		});
	}
}
