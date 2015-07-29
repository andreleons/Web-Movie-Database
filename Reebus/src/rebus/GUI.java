package rebus;

import java.awt.EventQueue;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;

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
import javax.swing.JList;
import javax.swing.AbstractListModel;

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
	private JList<String> adminWordsJList;
	private JScrollPane scrollPane_1;

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
		frame.setBounds(100, 100, 793, 765);
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
		tabStrip.setBounds(10, 11, 767, 715);
		frame.getContentPane().add(tabStrip);

		// init welcome panel
		JPanel panelWelcome = new JPanel();
		panelWelcome.setBackground(Color.GRAY);
		panelWelcome.setBounds(10, 51, 424, 431);
		panelWelcome.setLayout(null);

		tabStrip.addTab("Welcome", panelWelcome);

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

		JButton generateHTML1 = new JButton("Generate HTML");
		generateHTML1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				HtmlOutputProducer.openHtml();

			}

		});

		generateHTML1.setBounds(279, 377, 131, 61);
		panelWelcome.add(generateHTML1);

		JLabel lblInstructions = new JLabel("Instructions");
		lblInstructions.setBounds(113, 150, 73, 25);
		panelWelcome.add(lblInstructions);

		JLabel label = new JLabel("1.");
		label.setBounds(113, 206, 46, 14);
		panelWelcome.add(label);

		JLabel label_1 = new JLabel("2.");
		label_1.setBounds(113, 254, 46, 14);
		panelWelcome.add(label_1);

		// init config panel
		JPanel panelConfig = new JPanel();
		panelConfig.setBackground(Color.GRAY);
		panelConfig.setBounds(10, 51, 424, 431);
		panelConfig.setLayout(null);
		tabStrip.addTab("Config", panelConfig);

		// ------------------------------------------------------
		// ---------------------CONFIG PANEL---------------------
		// ------------------------------------------------------

		// label # puzzles to generate
		JLabel lblGameModesToGen = new JLabel("Select Game Mode: ");
		lblGameModesToGen.setForeground(Color.BLACK);
		lblGameModesToGen.setBounds(138, 71, 151, 14);
		panelConfig.add(lblGameModesToGen);

		// number of Game Modes to generate
		selectGameMode = new JComboBox(populateGameModes());
		selectGameMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.rebusX = (selectGameMode.getSelectedIndex() + 1);
				System.out.println("Rebus: " + Config.rebusX + " selected");
				updateWordBankSize();
			}
		});
		selectGameMode.setBounds(299, 66, 78, 19);
		panelConfig.add(selectGameMode);

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
		selectWordLength.setBounds(205, 249, 66, 20);
		panelConfig.add(selectWordLength);

		// label time elapsed
		JLabel wordStrengthLabel = new JLabel("Word Strength");
		wordStrengthLabel.setForeground(Color.DARK_GRAY);
		wordStrengthLabel.setBounds(10, 294, 151, 14);
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
		wordBankSizeTextField.setBounds(205, 183, 66, 20);
		panelConfig.add(wordBankSizeTextField);
		wordBankSizeTextField.setColumns(10);

		JLabel wordBankSizeLabel = new JLabel("Size of Current Word Pool");
		wordBankSizeLabel.setBounds(10, 180, 151, 27);
		panelConfig.add(wordBankSizeLabel);

		JLabel topicLabel = new JLabel("Topic");
		topicLabel.setBounds(10, 218, 101, 14);
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
		topicComboBox.setBounds(121, 218, 151, 20);
		panelConfig.add(topicComboBox);

		JLabel solutionLengthLabel = new JLabel("Solution Length");
		solutionLengthLabel.setBounds(296, 252, 101, 14);
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
		solutionLengthComboBox.setBounds(488, 249, 75, 20);
		panelConfig.add(solutionLengthComboBox);

		JLabel languageLabel = new JLabel("Language");
		languageLabel.setBounds(138, 106, 89, 14);
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
		languageComboBox.setBounds(309, 103, 81, 20);
		panelConfig.add(languageComboBox);

		JLabel solutionBankSizeLabel = new JLabel(
				"Size of Current Solution Pool");
		solutionBankSizeLabel.setBounds(296, 186, 157, 14);
		panelConfig.add(solutionBankSizeLabel);

		solutionBankSizeTextField = new JTextField();
		solutionBankSizeTextField.setEditable(false);
		solutionBankSizeTextField.setBounds(488, 183, 75, 20);
		panelConfig.add(solutionBankSizeTextField);
		solutionBankSizeTextField.setColumns(10);

		JLabel solutionTopicLabel = new JLabel("Topic");
		solutionTopicLabel.setBounds(293, 218, 104, 14);
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
		solutionTopicComboBox.setBounds(428, 215, 135, 20);
		panelConfig.add(solutionTopicComboBox);

		JLabel lblSolutionWordStrength = new JLabel("Word Strength");
		lblSolutionWordStrength.setBounds(296, 294, 135, 14);
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
		solutionBankWordStrengthMin.setBounds(428, 291, 65, 20);
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
		solutionBankWordStrengthMax.setBounds(503, 291, 60, 20);
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
		wordBankWordStrengthMin.setBounds(121, 291, 75, 20);
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
		wordBankWordStrengthMax.setBounds(205, 291, 66, 20);
		panelConfig.add(wordBankWordStrengthMax);

		// label rows / columns
		JLabel lblRows = new JLabel("Max Word Length: ");
		lblRows.setForeground(Color.DARK_GRAY);
		lblRows.setBounds(10, 252, 151, 14);
		panelConfig.add(lblRows);

		JLabel lblWordBankOptions = new JLabel("Word Bank Options");
		lblWordBankOptions.setFont(new Font("David", Font.BOLD | Font.ITALIC,
				21));
		lblWordBankOptions.setBounds(50, 143, 196, 29);
		panelConfig.add(lblWordBankOptions);

		JLabel lblSolutionBankOptions = new JLabel("Solution Bank Options");
		lblSolutionBankOptions.setFont(new Font("David", Font.BOLD
				| Font.ITALIC, 21));
		lblSolutionBankOptions.setBounds(327, 146, 233, 29);
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
		solutionWordLabelAdmin.setBounds(8, 70, 87, 26);
		panelAdmin.add(solutionWordLabelAdmin);

		JLabel lblAdminPanel = new JLabel("Admin Panel");
		lblAdminPanel.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdminPanel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 29));
		lblAdminPanel.setBounds(54, 27, 493, 35);
		panelAdmin.add(lblAdminPanel);

		solutionWordTextFieldAdmin = new JTextField();
		solutionWordTextFieldAdmin.setBounds(105, 73, 99, 20);
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
				if (Config.LANGUAGE.equals("En")) {
					Config.solutionBigWord.setProcessedEnglish(wp.getLogicalChars());
				}
				else {
					Config.solutionBigWord.setProcessedTelegu(wp.getLogicalChars());
				}
				rebus.findGameWords();
				generateChosenWords();
				generateWordOptions();
			}
		});
		solutionWordButtonAdmin.setBounds(400, 73, 184, 23);
		panelAdmin.add(solutionWordButtonAdmin);

		JLabel lblGameWords = new JLabel("Game Words");
		lblGameWords.setBounds(10, 223, 99, 14);
		panelAdmin.add(lblGameWords);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(245, 331, -11, -8);
		panelAdmin.add(textArea);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
		.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane
		.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(92, 118, 184, 375);
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
				} 
				else {
					solutionWordTextFieldAdmin.setText(Config.solutionBigWord
							.getTelugu());
				}
				solutionWordButtonAdmin.doClick();
			}
		});
		btnNewButton_1.setBounds(216, 73, 174, 23);
		panelAdmin.add(btnNewButton_1);

		JButton btnNewButton = new JButton("Commit Manually Typed Entries");
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
						temp.setProcessedWord(line);
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
		btnNewButton.setBounds(146, 546, 216, 35);
		panelAdmin.add(btnNewButton);

		JButton letsPlayButton1 = new JButton("I'm Feeling Lucky!");
		letsPlayButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rebus.pickSolutionWord();
				rebus.findGameWords();
				solutionWordTextAreaAdmin.setText("");
				if (Config.LANGUAGE.equals("En")) {
					solutionWordTextFieldAdmin.setText(Config.solutionBigWord
							.getEnglish());
				} else {
					solutionWordTextFieldAdmin.setText(Config.solutionBigWord
							.getTelugu());
				}
				generateChosenWords();
				generateWordOptions();
			}
		});
		letsPlayButton1.setBounds(372, 535, 216, 56);
		panelAdmin.add(letsPlayButton1);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(470, 122, 282, 375);
		panelAdmin.add(scrollPane_1);

		adminWordsJList = new JList();
		scrollPane_1.setViewportView(adminWordsJList);
		
		JButton swapButton = new JButton("<<<  Swap In  <<<");
		swapButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String entireString = adminWordsJList.getSelectedValue().toString();
					System.out.println(entireString);
					int cutPoint = entireString.indexOf(" [");
					String word = "";
					word = entireString.substring(0, cutPoint);
					entireString = entireString.substring(cutPoint + 7, entireString.length());
					cutPoint = entireString.indexOf(",");
					int wordNumber = Integer.valueOf(entireString.substring(0, cutPoint));
					System.out.println(entireString);
					System.out.println(word);
					System.out.println(wordNumber);
					BigWord temp = new BigWord();
					temp.setProcessedWord(word);
					Config.gameBigWords.set(wordNumber, temp);
					generateChosenWords();
				} catch (NullPointerException exception) {
					JOptionPane.showMessageDialog (
							   null, "you must select a word on the right to swap in first" );
				}

			}
		});
		swapButton.setBounds(302, 236, 143, 23);
		panelAdmin.add(swapButton);
		
		JButton btnNewButton_2 = new JButton("Clear All Fields");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAllFields();
			}
		});
		btnNewButton_2.setBounds(156, 597, 206, 35);
		panelAdmin.add(btnNewButton_2);
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
	
	private void generateWordOptions() {
		Vector<String> options = new Vector<String>();
		for (int i = 0; i < Config.solutionWord.size(); i++) {
			for (int j = 0; j < Config.potentialGameWords.size(); j++) {
				if (Config.potentialGameWords.get(j).getProcessedEnglish().get(Config.rebusX - 1).equals(Config.solutionWord.get(i))) {
					options.add(Config.potentialGameWords.get(j)
							.getEnglish() + " [Word " + i + ", Rebus " + Config.rebusX + ", Letter " + Config.solutionWord.get(i) + "]\n");
				}
			}
		}
		adminWordsJList = new JList<String>(options);
		scrollPane_1.setViewportView(adminWordsJList);
	}
	
	private void generateChosenWords() {
		solutionWordTextAreaAdmin.setText("");
			solutionWordTextFieldAdmin.setText(Config.solutionBigWord.getEnglish());
			for (int i = 0; i < Config.gameBigWords.size(); i++) {
					solutionWordTextAreaAdmin.append(Config.gameBigWords.get(i).getProcessedWord() + "\n");
			}

	}
	
	private void clearAllFields() {
		solutionWordTextAreaAdmin.setText("");
		solutionWordTextFieldAdmin.setText("");
		adminWordsJList = new JList<String>();
		scrollPane_1.setViewportView(adminWordsJList);
	}
}