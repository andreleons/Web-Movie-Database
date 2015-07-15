package rebus;

import java.awt.EventQueue;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Map.Entry;

import javax.swing.SwingConstants;

import java.awt.Dimension;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class GUI {

	private Font font;
	@SuppressWarnings("rawtypes")
	private JComboBox selectGameMode;
	@SuppressWarnings("rawtypes")
	private JComboBox wordBankWordStrengthMax;
	@SuppressWarnings("rawtypes")
	private JComboBox selectWordLength;
	Rebus rebus = new Rebus();

	// package
	JFrame frame;
	@SuppressWarnings("rawtypes")
	JTabbedPane tabStrip;
	private JTextField wordBankSizeTextField;
	private JTextField solutionBankSizeTextField;
	private JTextField solutionWordTextFieldAdmin;
	private JTextArea solutionWordTextAreaAdmin;

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		// main window
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 623, 593);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// Font
		font = null;
		InputStream fontFile = getClass().getResourceAsStream(Config.FontFile);
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(
					Font.PLAIN, 24);
		} catch (FontFormatException | IOException e1) {
			e1.printStackTrace();
		}
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		ge.registerFont(font);

		// tab strip
		tabStrip = new JTabbedPane(JTabbedPane.TOP);
		tabStrip.setBorder(null);
		tabStrip.setBounds(10, 11, 597, 539);
		frame.getContentPane().add(tabStrip);

		// init welcome panel
		JPanel panelWelcome = new JPanel();
		panelWelcome.setBackground(Color.GRAY);
		panelWelcome.setBounds(10, 51, 424, 431);
		panelWelcome.setLayout(null);

		// number of Game Modes to generate
		selectGameMode = new JComboBox(populateGameModes());
		selectGameMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.rebusX = (selectGameMode.getSelectedIndex() + 1);
				System.out.println("Rebus: " + Config.rebusX + " selected");
				updateWordBankSize();
			}
		});
		selectGameMode.setBounds(257, 84, 100, 25);
		panelWelcome.add(selectGameMode);

		// label # puzzles to generate
		JLabel lblGameModesToGen = new JLabel("Select Game Mode: ");
		lblGameModesToGen.setForeground(Color.BLACK);
		lblGameModesToGen.setBounds(113, 89, 151, 14);
		panelWelcome.add(lblGameModesToGen);
		tabStrip.addTab("Play", panelWelcome);

		// crossword logo image icon
		// ImageIcon crosswordsIcon = new
		// ImageIcon(getClass().getResource(Config.AppIcon32));
		JLabel cwIconLabel = new JLabel("");
		cwIconLabel.setBounds(95, 25, 32, 32);
		// cwIconLabel.setIcon(crosswordsIcon);
		cwIconLabel.setVisible(true);
		panelWelcome.add(cwIconLabel);

		// label welcome header
		JLabel lblWelcomeHeader = new JLabel("Rebus!");
		lblWelcomeHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeHeader
				.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 29));
		lblWelcomeHeader.setBounds(0, 25, 458, 29);
		panelWelcome.add(lblWelcomeHeader);

		JButton letsPlayButton1 = new JButton("Let's Play!");
		letsPlayButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rebus.pickSolutionWord();
				rebus.findGameWords();
				solutionWordTextAreaAdmin.setText("");
				if (Config.LANGUAGE.equals("En")) {
					solutionWordTextFieldAdmin.setText(Config.solutionBigWord
							.getEnglish());
					for (int i = 0; i < Config.gameBigWords.size(); i++) {
						if (!Config.gameBigWords.get(i).getEnglish().equals("")) {
							solutionWordTextAreaAdmin
									.append(Config.gameBigWords.get(i)
											.getEnglish() + "\n");
						}
					}
				} else {
					solutionWordTextFieldAdmin.setText(Config.solutionBigWord
							.getTelugu());
					for (int i = 0; i < Config.gameBigWords.size(); i++) {
						if (!Config.gameBigWords.get(i).getEnglish().equals("")) {
							solutionWordTextAreaAdmin
									.append(Config.gameBigWords.get(i)
											.getTelugu() + "\n");
						}
					}
				}

			}
		});
		letsPlayButton1.setBounds(105, 379, 131, 56);
		panelWelcome.add(letsPlayButton1);

		JButton generateHTML1 = new JButton("Generate HTML");
		generateHTML1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				HtmlOutputProducer.openHtml();
			
			}

		});

		generateHTML1.setBounds(279, 377, 131, 61);
		panelWelcome.add(generateHTML1);

		// init config panel
		JPanel panelConfig = new JPanel();
		panelConfig.setBackground(Color.GRAY);
		panelConfig.setBounds(10, 51, 424, 431);
		panelConfig.setLayout(null);
		tabStrip.addTab("Config", panelConfig);

		// ------------------------------------------------------
		// ---------------------CONFIG PANEL---------------------
		// ------------------------------------------------------

		// Word Length
		selectWordLength = new JComboBox(populateWordMaxLengthBox());
		selectWordLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.wordBankMaxLength = (Integer) selectWordLength
						.getSelectedIndex();
				System.out.println("Selected new word bank max size of "
						+ Config.wordBankMaxLength);
				updateWordBankSize();
			}
		});
		selectWordLength.setBounds(191, 218, 66, 20);
		panelConfig.add(selectWordLength);

		// label time elapsed
		JLabel wordStrengthLabel = new JLabel("Word Strength");
		wordStrengthLabel.setForeground(Color.DARK_GRAY);
		wordStrengthLabel.setBounds(10, 252, 151, 14);
		panelConfig.add(wordStrengthLabel);

		// label config tab header
		JLabel lblConfigHeader = new JLabel("Setup Configuration");
		lblConfigHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfigHeader
				.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 29));
		lblConfigHeader.setBounds(0, 25, 493, 35);
		panelConfig.add(lblConfigHeader);
		panelConfig.setVisible(true);
		panelConfig.setLayout(null);

		wordBankSizeTextField = new JTextField();
		wordBankSizeTextField.setEditable(false);
		wordBankSizeTextField.setBounds(191, 155, 66, 20);
		panelConfig.add(wordBankSizeTextField);
		wordBankSizeTextField.setColumns(10);

		JLabel wordBankSizeLabel = new JLabel("Size of Current Word Pool");
		wordBankSizeLabel.setBounds(10, 148, 151, 27);
		panelConfig.add(wordBankSizeLabel);

		JLabel topicLabel = new JLabel("Topic");
		topicLabel.setBounds(10, 190, 101, 14);
		panelConfig.add(topicLabel);

		JComboBox topicComboBox = new JComboBox(populateTopicBox());
		topicComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.wordBankTopic = (String) topicComboBox.getSelectedItem();
				System.out.println("Selected " + Config.wordBankTopic
						+ " as the new word bank topic");
				updateWordBankSize();
			}
		});
		topicComboBox.setBounds(106, 187, 151, 20);
		panelConfig.add(topicComboBox);

		JLabel solutionLengthLabel = new JLabel("Solution Length");
		solutionLengthLabel.setBounds(296, 221, 101, 14);
		panelConfig.add(solutionLengthLabel);

		JComboBox solutionLengthComboBox = new JComboBox(
				populateSolutionLengthBox());
		solutionLengthComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.solutionLength = (Integer) solutionLengthComboBox
						.getSelectedIndex();
				System.out.println("Selected new solution length of "
						+ Config.solutionLength);
				updateSolutionBankSize();
			}
		});
		solutionLengthComboBox.setBounds(488, 217, 75, 20);
		panelConfig.add(solutionLengthComboBox);

		JLabel languageLabel = new JLabel("Language");
		languageLabel.setBounds(210, 82, 89, 14);
		panelConfig.add(languageLabel);

		JComboBox languageComboBox = new JComboBox(populateLanguageBox());
		languageComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String language = (String) languageComboBox.getSelectedItem();
				Config.LANGUAGE = language.substring(0, 2);
				System.out.println("Selected " + Config.LANGUAGE
						+ " as the new Language");
				updateSolutionBankSize();
				updateWordBankSize();
			}
		});
		languageComboBox.setBounds(296, 79, 81, 20);
		panelConfig.add(languageComboBox);

		JLabel solutionBankSizeLabel = new JLabel(
				"Size of Current Solution Pool");
		solutionBankSizeLabel.setBounds(296, 154, 157, 14);
		panelConfig.add(solutionBankSizeLabel);

		solutionBankSizeTextField = new JTextField();
		solutionBankSizeTextField.setEditable(false);
		solutionBankSizeTextField.setBounds(488, 151, 75, 20);
		panelConfig.add(solutionBankSizeTextField);
		solutionBankSizeTextField.setColumns(10);

		JLabel solutionTopicLabel = new JLabel("Topic");
		solutionTopicLabel.setBounds(296, 190, 104, 14);
		panelConfig.add(solutionTopicLabel);

		JComboBox solutionTopicComboBox = new JComboBox(
				populateSolutionTopics());
		solutionTopicComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.solutionBankTopic = (String) solutionTopicComboBox
						.getSelectedItem();
				System.out.println("Selected " + Config.solutionBankTopic
						+ " as the new solution bank topic");
				updateSolutionBankSize();
			}
		});
		solutionTopicComboBox.setBounds(428, 184, 135, 20);
		panelConfig.add(solutionTopicComboBox);

		JLabel lblSolutionWordStrength = new JLabel("Word Strength");
		lblSolutionWordStrength.setBounds(296, 252, 135, 14);
		panelConfig.add(lblSolutionWordStrength);

		JComboBox solutionBankWordStrengthMin = new JComboBox(
				populateWordStrengths());
		solutionBankWordStrengthMin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.solutionBankWordStrengthMin = (Integer) solutionBankWordStrengthMin
						.getSelectedItem();
				System.out.println("Selected "
						+ Config.solutionBankWordStrengthMin
						+ " as the new solution bank min word strength");
				updateSolutionBankSize();
			}
		});
		solutionBankWordStrengthMin.setBounds(428, 249, 65, 20);
		panelConfig.add(solutionBankWordStrengthMin);

		JComboBox solutionBankWordStrengthMax = new JComboBox(
				populateWordStrengths());
		solutionBankWordStrengthMax.setSelectedIndex(9);
		solutionBankWordStrengthMax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.solutionBankWordStrengthMax = (Integer) solutionBankWordStrengthMax
						.getSelectedItem();
				System.out.println("Selected "
						+ Config.solutionBankWordStrengthMax
						+ " as the new solution bank max word strength");
				updateSolutionBankSize();
			}
		});
		solutionBankWordStrengthMax.setBounds(503, 249, 60, 20);
		panelConfig.add(solutionBankWordStrengthMax);

		JComboBox wordBankWordStrengthMin = new JComboBox(
				populateWordStrengths());
		wordBankWordStrengthMin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.wordBankWordStrengthMin = (Integer) wordBankWordStrengthMin
						.getSelectedItem();
				System.out.println("Selected " + Config.wordBankWordStrengthMin
						+ " as the new word bank min word strength");
				updateWordBankSize();
			}
		});
		wordBankWordStrengthMin.setBounds(107, 249, 75, 20);
		panelConfig.add(wordBankWordStrengthMin);

		// max elapsed time
		wordBankWordStrengthMax = new JComboBox(populateWordStrengths());
		wordBankWordStrengthMax.setSelectedIndex(9);
		wordBankWordStrengthMax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.wordBankWordStrengthMax = (Integer) wordBankWordStrengthMax
						.getSelectedItem();
				System.out.println("Selected " + Config.wordBankWordStrengthMax
						+ " as the new word bank max word strength");
				updateWordBankSize();
			}
		});
		wordBankWordStrengthMax.setBounds(191, 249, 66, 20);
		panelConfig.add(wordBankWordStrengthMax);

		// label rows / columns
		JLabel lblRows = new JLabel("Max Word Length: ");
		lblRows.setForeground(Color.DARK_GRAY);
		lblRows.setBounds(10, 223, 151, 14);
		panelConfig.add(lblRows);

		JLabel lblWordBankOptions = new JLabel("Word Bank Options");
		lblWordBankOptions.setFont(new Font("David", Font.BOLD | Font.ITALIC,
				21));
		lblWordBankOptions.setBounds(51, 115, 196, 29);
		panelConfig.add(lblWordBankOptions);

		JLabel lblSolutionBankOptions = new JLabel("Solution Bank Options");
		lblSolutionBankOptions.setFont(new Font("David", Font.BOLD
				| Font.ITALIC, 21));
		lblSolutionBankOptions.setBounds(330, 114, 233, 29);
		panelConfig.add(lblSolutionBankOptions);

		updateWordBankSize();
		updateSolutionBankSize();

		// init Admin Panel
		JPanel panelAdmin = new JPanel();
		panelAdmin.setBackground(Color.GRAY);
		panelAdmin.setBounds(10, 51, 424, 431);
		panelAdmin.setLayout(null);
		tabStrip.addTab("Admin", panelAdmin);

		JLabel solutionWordLabelAdmin = new JLabel("Solution Word");
		solutionWordLabelAdmin.setBounds(10, 99, 87, 26);
		panelAdmin.add(solutionWordLabelAdmin);

		JLabel lblAdminPanel = new JLabel("Admin Panel");
		lblAdminPanel.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdminPanel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 29));
		lblAdminPanel.setBounds(54, 27, 493, 35);
		panelAdmin.add(lblAdminPanel);

		solutionWordTextFieldAdmin = new JTextField();
		solutionWordTextFieldAdmin.setBounds(105, 102, 99, 20);
		panelAdmin.add(solutionWordTextFieldAdmin);
		solutionWordTextFieldAdmin.setColumns(10);

		JButton solutionWordButtonAdmin = new JButton("Generate Game Words");
		solutionWordButtonAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = "";
				try {
					word = solutionWordTextFieldAdmin.getText();
				} catch (NullPointerException error) {
					System.out.println("Solution word can't be empty");
					error.printStackTrace();
				}
				WordProcessor wp = new WordProcessor(word);
				BigWord bigWord = new BigWord();
				if (Config.LANGUAGE.equals("En")) {
					bigWord.setEnglish(word);
				} else {
					bigWord.setTelugu(word);
				}
				Config.solutionWord = wp.getLogicalChars();
				Config.solutionBigWord = bigWord;
				rebus.findGameWords();
				solutionWordTextAreaAdmin.setText("");
				if (Config.LANGUAGE.equals("En")) {
					solutionWordTextFieldAdmin.setText(Config.solutionBigWord
							.getEnglish());
					for (int i = 0; i < Config.gameBigWords.size(); i++) {
						if (!Config.gameBigWords.get(i).getEnglish().equals("")) {
							solutionWordTextAreaAdmin
									.append(Config.gameBigWords.get(i)
											.getEnglish() + "\n");
						}
					}
				} else {
					solutionWordTextFieldAdmin.setText(Config.solutionBigWord
							.getTelugu());
					for (int i = 0; i < Config.gameBigWords.size(); i++) {
						if (!Config.gameBigWords.get(i).getEnglish().equals("")) {
							solutionWordTextAreaAdmin
									.append(Config.gameBigWords.get(i)
											.getTelugu() + "\n");
						}
					}
				}
			}
		});
		solutionWordButtonAdmin.setBounds(398, 101, 184, 23);
		panelAdmin.add(solutionWordButtonAdmin);

		JLabel lblGameWords = new JLabel("Game Words");
		lblGameWords.setBounds(54, 263, 99, 14);
		panelAdmin.add(lblGameWords);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(245, 331, -11, -8);
		panelAdmin.add(textArea);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(163, 165, 327, 272);
		panelAdmin.add(scrollPane);

		solutionWordTextAreaAdmin = new JTextArea();
		scrollPane.setViewportView(solutionWordTextAreaAdmin);

		JButton btnNewButton_1 = new JButton("Generate Solution Word");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rebus.pickSolutionWord();
				if (Config.LANGUAGE.equals("En")) {
					solutionWordTextFieldAdmin.setText(Config.solutionBigWord
							.getEnglish());
				} else {
					solutionWordTextFieldAdmin.setText(Config.solutionBigWord
							.getTelugu());
				}
			}
		});
		btnNewButton_1.setBounds(214, 101, 174, 23);
		panelAdmin.add(btnNewButton_1);

		JButton btnNewButton = new JButton("Finalize Changes to Puzzle");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = "";
				try {
					word = solutionWordTextFieldAdmin.getText();
				} catch (NullPointerException error) {
					System.out.println("Solution word can't be empty");
					error.printStackTrace();
				}
				WordProcessor wp = new WordProcessor(word);
				BigWord bigWord = new BigWord();
				if (Config.LANGUAGE.equals("En")) {
					bigWord.setEnglish(word);
				} else {
					bigWord.setTelugu(word);
				}
				Config.solutionWord = wp.getLogicalChars();
				Config.solutionBigWord = bigWord;
				Config.GAME_WORDS.clear();
				Config.gameBigWords.clear();
				if (Config.LANGUAGE.equals("En")) {
					for (String line : solutionWordTextAreaAdmin.getText()
							.split("\\n")) {
						BigWord temp = new BigWord();
						temp.setEnglish(line);
						Config.gameBigWords.add(temp);
						// System.out.println("Adding " + temp.getEnglish());
						WordProcessor wordProccessor = new WordProcessor(line);
						Config.GAME_WORDS.add(wordProccessor.getLogicalChars());
					}
				} else {
					for (String line : solutionWordTextAreaAdmin.getText()
							.split("\\n")) {
						BigWord temp = new BigWord();
						temp.setTelugu(line);
						Config.gameBigWords.add(temp);
						// System.out.println("Adding " + temp.getTelugu());
						WordProcessor wordProccessor = new WordProcessor(line);
						Config.GAME_WORDS.add(wordProccessor.getLogicalChars());
					}
				}
				System.out.println("updated game solution and puzzle words");
			}
		});
		btnNewButton.setBounds(225, 465, 216, 35);
		panelAdmin.add(btnNewButton);
	}

	public Vector<String> populateGameModes() {
		Vector<String> retVal = new Vector<String>();
		retVal.add("Rebus 1");
		retVal.add("Rebus 2");
		retVal.add("Rebus 3");
		retVal.add("Rebus 4");
		retVal.add("Rebus 5");
		retVal.add("Rebus N");
		return retVal;
	}

	/**
	 * Generates a new Config.gameCollectionWordBank based on the current
	 * settings Updates the field that display the size of the current
	 * collection
	 */
	public void updateWordBankSize() {
		rebus.generateWordBank();
		wordBankSizeTextField.setText(String
				.valueOf(Config.gameCollectionWordBank.size()));
	}

	/**
	 * Generates a new Config.gameCollectionSolutionBank based on the current
	 * settings Updates the field that display the size of the current
	 * collection
	 */
	public void updateSolutionBankSize() {
		rebus.generateSolutionBank();
		solutionBankSizeTextField.setText(String
				.valueOf(Config.gameCollectionSolutionBank.size()));
	}

	/**
	 * Populates the topic list for the word bank
	 *
	 *
	 * @return
	 */
	private Object[] populateTopicBox() {
		ArrayList<String> topicStrings = new ArrayList<String>();
		topicStrings.add("Any");
		Hashtable<String, ArrayList<BigWord>> selects = Config.entireCollection
				.getBigWordsTopicsTable();
		for (Entry<String, ArrayList<BigWord>> entry : selects.entrySet()) {
			String key = entry.getKey();
			topicStrings.add(key);
		}
		return topicStrings.toArray();
	}

	/**
	 * Populates the topic list for the solution bank
	 *
	 *
	 * @return
	 */
	private Object[] populateSolutionTopics() {
		ArrayList<String> topicStrings = new ArrayList<String>();
		topicStrings.add("Any");
		Hashtable<String, ArrayList<BigWord>> selects = Config.entireCollection
				.getBigWordsTopicsTable();
		for (Entry<String, ArrayList<BigWord>> entry : selects.entrySet()) {
			String key = entry.getKey();
			topicStrings.add(key);
		}
		return topicStrings.toArray();
	}

	private Vector<Object> populateSolutionLengthBox() {
		Vector<Object> retVal = new Vector<Object>();
		retVal.add("Any");
		for (int i = 1; i < 30; i++) {
			retVal.add(i);
		}
		return retVal;
	}

	private Vector<Object> populateWordMaxLengthBox() {
		Vector<Object> retVal = new Vector<Object>();
		retVal.add("Any");
		for (int i = 1; i < 30; i++) {
			retVal.add(i);
		}
		return retVal;
	}

	public Vector<String> populateLanguageBox() {
		Vector<String> retVal = new Vector<String>();
		retVal.add("English");
		retVal.add("Telugu");
		return retVal;
	}

	private Vector<Integer> populateWordStrengths() {
		Vector<Integer> retVal = new Vector<Integer>();
		for (int i = 1; i < 11; i++) {
			retVal.add(i);
		}
		return retVal;
	}
}