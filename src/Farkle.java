
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;




public class Farkle implements ActionListener {

	JFrame frame = new JFrame("Farkle");
	
// trying to add side buttons for difficulty and for wild die option

	Container optionsContainer = new Container();





	
	Container diceContainer = new Container();

	JButton[] diceButtons = new JButton[6];

	ImageIcon[] imageIcons = new ImageIcon[6];

	Container buttonContainer = new Container();
	
	
	JRadioButton hardmode = new JRadioButton("hard");	

	JRadioButton easymode = new JRadioButton("easy");	

	JRadioButton wildDie = new JRadioButton("Wild Dice");
	


	int[] buttonState = new int[6];
	int[] dieValue = new int[6];
	
	final int HOTDICE = 0;
	final int SCOREDICE = 1;
	final int LOCKEDDICE = 2;

	// names of buttons
	JButton rollButton = new JButton("Roll");
	JButton scoreButton = new JButton("Score");
	JButton bankButton = new JButton("Bank");
	

	Container labelContainer = new Container();

	JLabel currentScoreLabel = new JLabel("Current Score: 0");
	JLabel totalScoreLabel = new JLabel("Total Score: 0");
	
	int currentScore = 0;
	int totalScore = 0;


	public Farkle() {

		ButtonGroup difficulty = new ButtonGroup();
		difficulty.add(hardmode);
		difficulty.add(easymode);



		
	optionsContainer.setLayout(new GridLayout(2,1));
	hardmode.setPreferredSize(new Dimension(100, 30)); 
		easymode.setPreferredSize(new Dimension(100, 30)); 
		optionsContainer.add(hardmode);
		optionsContainer.add(easymode);
		
		
		
		 // importing the pictures from files to java
		  
		
		frame.setSize(900,900);
		imageIcons[0] = new ImageIcon("./farkle dice/dice 1.png");
		imageIcons[1] = new ImageIcon("./farkle dice/dice 2.png");  
		imageIcons[2] = new ImageIcon("./farkle dice/dice 3.png");  
		imageIcons[3] = new ImageIcon("./farkle dice/dice 4.png");  
		imageIcons[4] = new ImageIcon("./farkle dice/dice 5.png");  
		imageIcons[5] = new ImageIcon("./farkle dice/dice 6.png");  
		
		// 2 rows, 3 columns
		diceContainer.setLayout(new GridLayout(2, 3));

		
		for (int i = 0; i < diceButtons.length; i++) {
			diceButtons[i] = new JButton();
			diceButtons[i].setIcon(imageIcons[i]);

			diceButtons[i].setEnabled(false);
			diceButtons[i].addActionListener(this);

			// changes color
			diceButtons[i].setBackground(Color.white);

			diceContainer.add(diceButtons[i]);
		}

		// one row with three buttons
		buttonContainer.setLayout(new GridLayout(1, 3));

		buttonContainer.add(rollButton);
		rollButton.addActionListener(this);
		

		buttonContainer.add(scoreButton);
		scoreButton.addActionListener(this);

		buttonContainer.add(bankButton);
		bankButton.addActionListener(this);

		//score and bank are disabled until user rolls
		scoreButton.setEnabled(false);
		bankButton.setEnabled(false);

		// creates the label container
		labelContainer.setLayout(new GridLayout(2, 1));
		labelContainer.add(currentScoreLabel);
	
		labelContainer.add(totalScoreLabel);
		
		


		// adds all the containers to the frame
		frame.setLayout(new BorderLayout());
		frame.add(diceContainer, BorderLayout.CENTER);
		frame.add(buttonContainer, BorderLayout.SOUTH);
		frame.add(labelContainer, BorderLayout.NORTH);
		frame.add(optionsContainer,BorderLayout.EAST);
	
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);

		// trynna make game end if player gets 10000 points
		if(totalScore >= 100){
			JOptionPane.showMessageDialog(frame, "You have won!");
		}

	}



	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if(e.getSource()== hardmode) {
			System.out.println("Hard Mode");
		}
		
		
		

		if(e.getSource()== easymode) {
			System.out.println("Easy Mode");
		}
		
		
		
		
		
		if (e.getSource().equals(rollButton)) {

		
			for (int i = 0; i < diceButtons.length; i++) {
				if (buttonState[i] == HOTDICE) {
					int choice = (int) (Math.random() * 6);
					dieValue[i] = choice;
					diceButtons[i].setIcon(imageIcons[choice]);
					diceButtons[i].setEnabled(true);
					rollButton.setEnabled(false);
					scoreButton.setEnabled(true);
					bankButton.setEnabled(true);
				}
			}
			

		} else if (e.getSource().equals(scoreButton)) {
			// score button
			// seven instead of six bc it is just easier that way
			int[] valueCount = new int[7];
			for (int i = 0; i < diceButtons.length; i++) {
				if (buttonState[i] == SCOREDICE) {

					// adds one to the value so its 1-6 instead of 0-5
					valueCount[dieValue[i] + 1]++;
				}
			}
			if ((valueCount[2] > 0 && valueCount[2] < 3) || (valueCount[3] > 0 && valueCount[3] < 3)
					|| (valueCount[4] > 0 && valueCount[4] < 3) || (valueCount[6] > 0 && valueCount[6] < 3)) {

				// invalid die selection
				JOptionPane.showMessageDialog(frame, "dice input is invalid");
			} else if (valueCount[1] == 0 && valueCount[2] == 0 && valueCount[3] == 0 && valueCount[4] == 0
					&& valueCount[5] == 0 && valueCount[6] == 0) {
				Object[] options = { "yes", "no" };
				int dialogChoice = JOptionPane.showOptionDialog(frame, "Give up score?", "Farkled", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				if (dialogChoice == JOptionPane.YES_OPTION) {
					currentScore = 0;
					
					currentScoreLabel.setText("Current Score: " + currentScore);
				
					resetDice();

				}
			}

			else {
				// this is if you get triples
				if (valueCount[1] >= 3) {
					currentScore += (valueCount[1] - 2) * 1000;
				}
				if (valueCount[2] >= 3) {
					currentScore += (valueCount[2] - 2) * 200;
				}
				if (valueCount[3] >= 3) {
					currentScore += (valueCount[3] - 2) * 300;
				}

				if (valueCount[4] >= 3) {
					currentScore += (valueCount[4] - 2) * 400;
				}
				if (valueCount[5] >= 3) {
					currentScore += (valueCount[5] - 2) * 500;
				}
				if (valueCount[6] >= 3) {
					currentScore += (valueCount[6] - 2) * 600;
				}
				
				
				
				// this is if u get less than 3 ones or fives
				if (valueCount[1] < 3) {
					currentScore += valueCount[1] * 100;
				}

				if (valueCount[5] < 3) {
					currentScore += valueCount[5] * 50;
				}
				currentScoreLabel.setText("Current Score: " + currentScore);
				for (int i = 0; i < diceButtons.length; i++) {
					if (buttonState[i] == SCOREDICE) {
						buttonState[i] = LOCKEDDICE;
						diceButtons[i].setBackground(Color.blue);

					}
					diceButtons[i].setEnabled(false);

				}
				int lockedCount = 0;
				for (int i = 0; i < diceButtons.length; i++) {
					if (buttonState[i] == LOCKEDDICE) {
						lockedCount++;
					}
				}
				if (lockedCount == 6) {
					for (int i = 0; i < diceButtons.length; i++) {
						buttonState[i] = HOTDICE;
						diceButtons[i].setBackground(Color.white);
					}

				}
				rollButton.setEnabled(true);
				scoreButton.setEnabled(false);
				bankButton.setEnabled(true);
			}

		}

		else if (e.getSource().equals(bankButton)) {
			// stop button
			totalScore += currentScore;
			currentScore = 0;
			currentScoreLabel.setText("Current Score: " + currentScore);
			totalScoreLabel.setText("Total Score: " + totalScore);
			
			resetDice();
		}

		else {
			for (int i = 0; i < diceButtons.length; i++) {
				if (e.getSource().equals(diceButtons[i])) {
					if (buttonState[i] == HOTDICE) {
						diceButtons[i].setBackground(Color.red);
						buttonState[i] = SCOREDICE;
					} else {
						diceButtons[i].setBackground(Color.white);
						buttonState[i] = HOTDICE;
					}
				}

			}
		}
	} // end of action thing

	


	public void resetDice() {
		for (int i = 0; i < diceButtons.length; i++) {
			diceButtons[i].setEnabled(false);
			buttonState[i] = HOTDICE;
			diceButtons[i].setBackground(Color.white);

		}
		rollButton.setEnabled(true);
		scoreButton.setEnabled(false);
		bankButton.setEnabled(false);
	}

	public void setLogo() {
	
		ImageIcon originalIcon = new ImageIcon("./farkle dice/logo.png");
		Image originalImage = originalIcon.getImage();
		Image scaledImage = originalImage.getScaledInstance(1500, 1500, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		frame.setIconImage(scaledIcon.getImage());

	}
	
	public static void main(String[] args) {

		Farkle game = new Farkle();
		game.setLogo();

		
	}


}
