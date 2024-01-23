import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mashape.unirest.http.exceptions.UnirestException;


public class TriviaGUI {
	private JFrame frame;
	private static JLabel questionLabel;
	private static JLabel difficultyLabel;
	
	private static JPanel buttonPanel;
	private static JButton buttonCorrect;
	private static JButton buttonIncorrectOne;
	private static JButton buttonIncorrectTwo;
	private static JButton buttonIncorrectThree;
	
	
	private static JButton[] buttonsIncorrect = new JButton[3];
	
	private static JButton newQuestion;
	
	
	
	public TriviaGUI() {
		frame = new JFrame("Trivia");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		frame.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2));
		
		Container contentPane = frame.getContentPane();
		
		questionLabel = new JLabel();
		difficultyLabel = new JLabel();
		buttonCorrect = new JButton();
		buttonIncorrectOne = new JButton();
		buttonIncorrectTwo = new JButton();
		buttonIncorrectThree = new JButton();
		
		buttonCorrect.addActionListener(new ButtonClickedCorrect());
		
		buttonIncorrectOne.addActionListener(new ButtonClickedIncorrect());
		buttonIncorrectTwo.addActionListener(new ButtonClickedIncorrect());
		buttonIncorrectThree.addActionListener(new ButtonClickedIncorrect());
		
		buttonPanel = new JPanel();
		
				
		buttonsIncorrect[0] = buttonIncorrectOne;
		buttonsIncorrect[1] = buttonIncorrectTwo;
		buttonsIncorrect[2] = buttonIncorrectThree;
				
		for (JButton button : buttonsIncorrect) {
			button.setPreferredSize(new Dimension((int) frame.getPreferredSize().getWidth() / 8, (int) frame.getPreferredSize().getHeight() / 8));
			buttonPanel.add(button);
		}
		buttonCorrect.setPreferredSize(new Dimension((int) frame.getPreferredSize().getWidth() / 8, (int) frame.getPreferredSize().getHeight() / 8));
		buttonPanel.add(buttonCorrect);
		
		newQuestion = new JButton("New Question");
		newQuestion.addActionListener(new NewQuestion());
			
		JPanel fullPanel = new JPanel();
		//fullPanel.setLayout(new BoxLayout(fullPanel, BoxLayout.Y_AXIS));
		
		fullPanel.add(questionLabel);
		fullPanel.add(buttonPanel);
		fullPanel.add(newQuestion);
		fullPanel.add(difficultyLabel, BorderLayout.CENTER);
		
		contentPane.add(fullPanel);
	}

	public void display() {
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void updateQuestion(String question, String difficulty, String correctAnswer, String[] incorrectAnswers) {
		questionLabel.setText(question);
		difficultyLabel.setText(difficulty);
		buttonCorrect.setText(correctAnswer);
		for (int i = 0; i < incorrectAnswers.length; i++) {
			buttonsIncorrect[i].setText(incorrectAnswers[i]);
		}
		shuffleButtons(incorrectAnswers.length);
	}
	
	public static void shuffleButtons(int numIncorrectButtons) {
		
		// TODO: Keep True/False ordered as such
		// TODO: Bug with buttons on first loadup sometimes	
		// TODO: Better layout
		
		for (Component button : buttonPanel.getComponents()) {
			button.setBackground(null);
		}
		buttonPanel.removeAll();
		int correctButtonPlace = (int) (Math.random() * (numIncorrectButtons + 1));
		int currIncorrectButton = 0;
		for (int i = 0; i < numIncorrectButtons + 1; i++) {
			if (i == correctButtonPlace) {
				buttonPanel.add(buttonCorrect);
			} else {
				buttonPanel.add(buttonsIncorrect[currIncorrectButton]);
				currIncorrectButton++;
			}
		}
	}
	
	public class ButtonClickedCorrect implements ActionListener {
		
		ButtonClickedCorrect() {
			super();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			((JButton) e.getSource()).setBackground(Color.GREEN);
		}		
	}
	
	public class ButtonClickedIncorrect implements ActionListener {
		
		ButtonClickedIncorrect() {
			super();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			((JButton) e.getSource()).setBackground(Color.RED);
		}		
	}
	
	public class NewQuestion implements ActionListener {
		
		NewQuestion() {
			super();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				TriviaUtils.newQuestion();
			} catch (UnirestException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
	}
}
