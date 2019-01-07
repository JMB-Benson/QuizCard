package FlashCard;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class QuizCardPlayer {
	private JTextArea display;
	private JTextArea answer;
	private ArrayList<QuizCard> cardList;
	private QuizCard currentCard;
	private int currentCardIndex;
	private JFrame frame;
	private JButton nextButton;
	private boolean isShowAnswer;

	public static void main(String[] args) {
		QuizCardPlayer reader = new QuizCardPlayer();
		reader.go();
		

	}

	public void go() {
		frame = new JFrame("Quiz Card PLayer");
		JPanel mainPanel = new JPanel();
		Font bigFont = new Font("sanserif",Font.BOLD,24);
		
		display = new JTextArea(10,20);
		display.setFont(bigFont);
		display.setLineWrap(true);
		display.setEditable(false);
		
		JScrollPane qScroller = new JScrollPane(display);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		nextButton = new JButton("show Question");
		mainPanel.add(qScroller);
		mainPanel.add(nextButton);
		nextButton.addActionListener(new NextCardListener());;
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadMenuItem =new JMenuItem("Load Card set");
		loadMenuItem.addActionListener(new OpenMenuListener());
		fileMenu.add(loadMenuItem);
		menuBar.add(fileMenu);
		
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(640,500);
		frame.setVisible(true);
		
		
	}
	public class  NextCardListener implements ActionListener {

		
		public void actionPerformed(ActionEvent arg0) {
			if (isShowAnswer) {
				display.setText(currentCard.getAnswer());
				nextButton.setText("next Card");
				isShowAnswer = false;
			}
			else {
				if (currentCardIndex < cardList.size()) {
					showNextCard();
				}
				else {
					display.setText("that was the last Card");
					nextButton.setEnabled(false);
				}
			}
			
		}
		
	}
	public class OpenMenuListener implements ActionListener{

		
		public void actionPerformed(ActionEvent ev) {
			JFileChooser fileOpen = new JFileChooser();
			fileOpen.showOpenDialog(frame);
			loadFile(fileOpen.getSelectedFile());
			
		}
		
	}
	private void showNextCard() {
		currentCard = cardList.get(currentCardIndex);
		currentCardIndex++;
		display.setText(currentCard.getQuestion());
		nextButton.setText("show answer");;
		isShowAnswer = true;
	}
	private void loadFile(File file) {
		cardList = new ArrayList<QuizCard>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine())!= null) {
				makeCard(line);
				
			}
		}catch (Exception ex) {
			System.out.println("couldent read the file");
			ex.printStackTrace();
		}
		showNextCard();
	}
	
	private void makeCard(String lineToParse) {
		String[] results = lineToParse.split("/");
		QuizCard card = new QuizCard(results[0],results[1]);
		cardList.add(card);
		System.out.println("made a card");
	}

}
