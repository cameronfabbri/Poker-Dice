/////////////////////////////////////////////////////////////////////////////////////
// This is the PokerGUI class, which is responsible for creating and showing the GUI 
/////////////////////////////////////////////////////////////////////////////////////

package cs242.cryptobiotic.dice;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class PokerGUI implements ActionListener
{

	int player1points = 0;
	int player2points = 0;

	int player1high = 0;
	int player2high = 0;
	
	// A count variable for the result text box.
	int count = 1;
	int player = 0;
	int turn = -1;

	PokerDice poker = new PokerDice();

	Timer timer = new Timer();
	// Create the frame
	JFrame frame;
	JFrame wframe;
	// Create the Panels, and the container panel to later be put inside the
	// frame.
	JPanel container;
	JPanel win_container;
	
	JScrollPane resultsFrame;
	JPanel buttonFrame;
	JPanel dContainer;


	JTextArea textArea = new JTextArea(15, 30);
	JTextPane winnerText = new JTextPane();

	JButton button;

	JTextPane d1_text = new JTextPane();
	JCheckBox d1_check = new JCheckBox();
	JTextPane d2_text = new JTextPane();
	JCheckBox d2_check = new JCheckBox();
	JTextPane d3_text = new JTextPane();
	JCheckBox d3_check = new JCheckBox();
	JTextPane d4_text = new JTextPane();
	JCheckBox d4_check = new JCheckBox();
	JTextPane d5_text = new JTextPane();
	JCheckBox d5_check = new JCheckBox();

	public void createWinnerPanel(String winner)
	{
		/**
		 * The createWinnerPanel creates a new window that displays the winner and their winning hand
		 */
		// Create the frame
		wframe = new JFrame("Winning Frame");
		wframe.setBackground(Color.gray);
		
		JTextPane player1 = new JTextPane();
		player1.setText(poker.player_one);
		player1.setEditable(false);
		JTextPane player2 = new JTextPane();
		player2.setEditable(false);
		player2.setText(poker.player_two);

		// Create the Panels, and the container panel to later be put inside the
		// frame.
		win_container = new JPanel();
		win_container.setLayout(new BoxLayout(win_container, BoxLayout.Y_AXIS));
		// sets the frame close action.
		wframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create components and put them in the panels, put those panels in the
		// container, and that container in the frame.

		wframe.setBackground(Color.gray);
		winnerText.setFont(new Font("Serif", Font.PLAIN, 22));
		
		winnerText.setEditable(false);
		winnerText.setPreferredSize(new Dimension(50, 50));

		winnerText.setAlignmentX(winnerText.CENTER_ALIGNMENT);

	

		if (winner.equals("player1"))
		{
			winnerText.setText(poker.player_one + " wins!");
		} else if (winner.equals("player2"))
		{
			winnerText.setText(poker.player_two + " wins!");
		} else
			winnerText.setText("Tie game!");
		win_container.add(player1);
		win_container.add(winningHand("player1"));
		win_container.add(player2);
		win_container.add(winningHand("player2"));
		win_container.add(winnerText);
		wframe.add(win_container, BorderLayout.CENTER);

		// Size the frame.
		wframe.pack();
		wframe.setResizable(false);


		win_container.setBackground(Color.black);	
	
		// Make sure it's visible.
		wframe.setVisible(true);

    		timer.schedule(new change_red(),0, 1000);
    		timer.schedule(new change_white(),500, 1000);

	}

	public void createAndShowGUI()
	{
		/**
		 * The createAndShowGUI method creates the gui and all the buttons
		 */
		
		d1_text.insertIcon(poker.RedBack);
		d2_text.insertIcon(poker.RedBack);
		d3_text.insertIcon(poker.RedBack);
		d4_text.insertIcon(poker.RedBack);
		d5_text.insertIcon(poker.RedBack);

		showCheck(false);

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
		button = new JButton("Click for " + poker.player_one + "'s turn");
		button.setMnemonic(KeyEvent.VK_I);
		button.addActionListener(this);
		buttonFrame.add(button);

		// Create text area and place it into resultsFrame.
		textArea.setFont(new Font("Serif", Font.PLAIN, 16));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

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

	public Component winningHand(String player)
	{
		JPanel wContainer = new JPanel();

		JPanel d1 = new JPanel();
		JPanel d2 = new JPanel();
		JPanel d3 = new JPanel();
		JPanel d4 = new JPanel();
		JPanel d5 = new JPanel();
		
		JTextPane d1_text = new JTextPane();		
		JTextPane d2_text = new JTextPane();		
		JTextPane d3_text = new JTextPane();		
		JTextPane d4_text = new JTextPane();		
		JTextPane d5_text = new JTextPane();		

		JPanel dContainer = new JPanel();
		dContainer.setLayout(new BoxLayout(dContainer, BoxLayout.X_AXIS));
		d1_text.setBackground(Color.white);
		d1_text.setFont(new Font("Arial", Font.PLAIN, 32));
		d1_text.setEditable(false);
		d2_text.setBackground(Color.white);
		d2_text.setFont(new Font("Arial", Font.PLAIN, 32));
		d2_text.setEditable(false);
		d3_text.setBackground(Color.white);
		d3_text.setFont(new Font("Arial", Font.PLAIN, 32));
		d3_text.setEditable(false);
		d4_text.setBackground(Color.white);
		d4_text.setFont(new Font("Arial", Font.PLAIN, 32));
		d4_text.setEditable(false);
		d5_text.setBackground(Color.white);
		d5_text.setFont(new Font("Arial", Font.PLAIN, 32));
		d5_text.setEditable(false);

		d1_text.setPreferredSize(new Dimension(74, 99));
		d2_text.setPreferredSize(new Dimension(74, 99));
		d3_text.setPreferredSize(new Dimension(74, 99));
		d4_text.setPreferredSize(new Dimension(74, 99));
		d5_text.setPreferredSize(new Dimension(74, 99));

		d1.add(d1_text);
		d2.add(d2_text);
		d3.add(d3_text);
		d4.add(d4_text);
		d5.add(d5_text);

		wContainer.add(d1);
		wContainer.add(d2);
		wContainer.add(d3);
		wContainer.add(d4);
		wContainer.add(d5);
		
		if(player.equals("player1")){
		d1_text.setText("");
                d2_text.setText("");
                d3_text.setText("");
                d4_text.setText("");
                d5_text.setText("");

                d1_text.insertIcon(poker.toCard(poker.p1finalArray[0]));
                d2_text.insertIcon(poker.toCard(poker.p1finalArray[1]));
                d3_text.insertIcon(poker.toCard(poker.p1finalArray[2]));
                d4_text.insertIcon(poker.toCard(poker.p1finalArray[3]));
                d5_text.insertIcon(poker.toCard(poker.p1finalArray[4]));
		}		
		else if(player.equals("player2")){
		d1_text.setText("");
                d2_text.setText("");
                d3_text.setText("");
                d4_text.setText("");
                d5_text.setText("");

                d1_text.insertIcon(poker.toCard(poker.p2finalArray[0]));
                d2_text.insertIcon(poker.toCard(poker.p2finalArray[1]));
                d3_text.insertIcon(poker.toCard(poker.p2finalArray[2]));
                d4_text.insertIcon(poker.toCard(poker.p2finalArray[3]));
                d5_text.insertIcon(poker.toCard(poker.p2finalArray[4]));
		}

		return wContainer;
	}
	
	public Component createDie()
	{
		JPanel d1cardcheck = new JPanel();
		d1cardcheck.setLayout(new BoxLayout(d1cardcheck, BoxLayout.Y_AXIS));
		d1_check.setAlignmentX(d1_check.CENTER_ALIGNMENT);
		JPanel d2cardcheck = new JPanel();
		d2cardcheck.setLayout(new BoxLayout(d2cardcheck, BoxLayout.Y_AXIS));
		d2_check.setAlignmentX(d2_check.CENTER_ALIGNMENT);
		JPanel d3cardcheck = new JPanel();
		d3cardcheck.setLayout(new BoxLayout(d3cardcheck, BoxLayout.Y_AXIS));
		d3_check.setAlignmentX(d3_check.CENTER_ALIGNMENT);
		JPanel d4cardcheck = new JPanel();
		d4cardcheck.setLayout(new BoxLayout(d4cardcheck, BoxLayout.Y_AXIS));
		d4_check.setAlignmentX(d4_check.CENTER_ALIGNMENT);
		JPanel d5cardcheck = new JPanel();
		d5cardcheck.setLayout(new BoxLayout(d5cardcheck, BoxLayout.Y_AXIS));
		d5_check.setAlignmentX(d5_check.CENTER_ALIGNMENT);

		JPanel d1 = new JPanel();
		JPanel d2 = new JPanel();
		JPanel d3 = new JPanel();
		JPanel d4 = new JPanel();
		JPanel d5 = new JPanel();

		JPanel dContainer = new JPanel();
		dContainer.setLayout(new BoxLayout(dContainer, BoxLayout.X_AXIS));
		d1_text.setBackground(Color.white);
		d1_text.setFont(new Font("Arial", Font.PLAIN, 32));
		d1_text.setEditable(false);
		d2_text.setBackground(Color.white);
		d2_text.setFont(new Font("Arial", Font.PLAIN, 32));
		d2_text.setEditable(false);
		d3_text.setBackground(Color.white);
		d3_text.setFont(new Font("Arial", Font.PLAIN, 32));
		d3_text.setEditable(false);
		d4_text.setBackground(Color.white);
		d4_text.setFont(new Font("Arial", Font.PLAIN, 32));
		d4_text.setEditable(false);
		d5_text.setBackground(Color.white);
		d5_text.setFont(new Font("Arial", Font.PLAIN, 32));
		d5_text.setEditable(false);

		d1_text.setPreferredSize(new Dimension(74, 99));
		d2_text.setPreferredSize(new Dimension(74, 99));
		d3_text.setPreferredSize(new Dimension(74, 99));
		d4_text.setPreferredSize(new Dimension(74, 99));
		d5_text.setPreferredSize(new Dimension(74, 99));

		d1cardcheck.add(d1_text);
		d1cardcheck.add(d1_check);
		d2cardcheck.add(d2_text);
		d2cardcheck.add(d2_check);
		d3cardcheck.add(d3_text);
		d3cardcheck.add(d3_check);
		d4cardcheck.add(d4_text);
		d4cardcheck.add(d4_check);
		d5cardcheck.add(d5_text);
		d5cardcheck.add(d5_check);

		d1.add(d1cardcheck);
		d2.add(d2cardcheck);
		d3.add(d3cardcheck);
		d4.add(d4cardcheck);
		d5.add(d5cardcheck);

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
		checkGame(turn);
		setCheck(false);
		++turn;

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
	public void setFaces(Die diceArray[])
	{

		d1_text.setText("");
		d2_text.setText("");
		d3_text.setText("");
		d4_text.setText("");
		d5_text.setText("");

		d1_text.insertIcon(poker.toCard(diceArray[0]));
		d2_text.insertIcon(poker.toCard(diceArray[1]));
		d3_text.insertIcon(poker.toCard(diceArray[2]));
		d4_text.insertIcon(poker.toCard(diceArray[3]));
		d5_text.insertIcon(poker.toCard(diceArray[4]));
	}
	
	public void reRoll(String player){
	poker.arrange_and_check(d1_check.isSelected(),
                                d2_check.isSelected(), d3_check.isSelected(),
                                d4_check.isSelected(), d5_check.isSelected(), player);
        setFaces(poker.diceArray);
	}

	public void checkGame(int turn)
	{
		/**
		 * The checkGame method counts the turns and makes it so each player only has two.
		 * It also counts the points each player gets and sets the winner. 
		 */
		
		if (turn == -1)
		{
			button.setText("Roll");
		}
		if (turn == 0)
		{
			showCheck(true);
			reRoll("player1");	
		}

		if (turn == 1)
		{
			showCheck(false);
			button.setText("Click for " + poker.player_two + "'s turn");
			reRoll("player1");
		}

		else if (turn == 2)
		{
			showCheck(false);
			d1_text.setText("");
			d2_text.setText("");
			d3_text.setText("");
			d4_text.setText("");
			d5_text.setText("");
			d1_text.insertIcon(poker.BlueBack);
			d2_text.insertIcon(poker.BlueBack);
			d3_text.insertIcon(poker.BlueBack);
			d4_text.insertIcon(poker.BlueBack);
			d5_text.insertIcon(poker.BlueBack);
			
			for(int i = 0; i < 5; i++){
			        poker.p1finalArray[i].setTop(poker.diceArray[i].getTop());	
			}
			button.setText("Roll");
		} else if (turn == 3)
		{
			setCheck(false);
			showCheck(true);
			reRoll("player2");	
		} else if (turn == 4)
		{
			showCheck(false);
			reRoll("player2");	
			setFaces(poker.diceArray);
		
			for(int i = 0; i < 5; i++){
				poker.p2finalArray[i].setTop(poker.diceArray[i].getTop());
			}	
			button.setText("Click to see game results.");

		} else if (turn > 4)
			{
				createWinnerPanel(poker.checkWinner());
			}
	}

	public void setPlayer(int num)
	{
		this.player = num;
	}

	public void showCheck(boolean bool)
	{
		d1_check.setVisible(bool);
		d2_check.setVisible(bool);
		d3_check.setVisible(bool);
		d4_check.setVisible(bool);
		d5_check.setVisible(bool);
	}

	public void setCheck(boolean bool)
	{

		d1_check.setSelected(bool);
		d2_check.setSelected(bool);
		d3_check.setSelected(bool);
		d4_check.setSelected(bool);
		d5_check.setSelected(bool);
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


class change_red extends TimerTask {
    public void run() {
      	winnerText.setBackground(Color.red);
	
    }
}

class change_white extends TimerTask {
    public void run() {
      	winnerText.setBackground(Color.white);
	
    }
}
	/**
	 * The draw function creates and draws the GUI.
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
