

import java.awt.EventQueue;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingWorker.StateValue;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.Panel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.SwingConstants;

public class GUI {

	//private
	private JTextField textMinWords;
	private JTextField textMaxWords;
	private Font font;
	@SuppressWarnings("rawtypes")
	private JComboBox selectGameMode;
	@SuppressWarnings("rawtypes")
	private JComboBox selectMaxAllowedTime;	
	@SuppressWarnings("rawtypes")
	private JComboBox selectWordLength;


	//package
	JFrame frame;
	@SuppressWarnings("rawtypes")
	JTabbedPane tabStrip;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
		//main window
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 524, 590);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		//Font
		font = null;
		InputStream fontFile = getClass().getResourceAsStream(Config.FontFile);
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, 24);
		} catch (FontFormatException | IOException e1) {
			e1.printStackTrace();
		}
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);

		//init welcome panel
		JPanel panelWelcome = new JPanel();
		panelWelcome.setBackground(Color.GREEN);
		panelWelcome.setBounds(10, 51, 424, 431);
		panelWelcome.setLayout(null);

		//number of Game Modes to generate
		selectGameMode = new JComboBox(Config.OptionsGameModes);
		selectGameMode.setBounds(280, 211, 100, 25);
		panelWelcome.add(selectGameMode);

		//label # puzzles to generate
		JLabel lblGameModesToGen = new JLabel("Select Game Mode: ");
		lblGameModesToGen.setForeground(Color.BLACK);
		lblGameModesToGen.setBounds(150, 211, 151, 14);
		panelWelcome.add(lblGameModesToGen);

		//init config panel
		JPanel panelConfig = new JPanel();
		panelConfig.setBackground(Color.GREEN);
		panelConfig.setBounds(10, 51, 424, 431);
		panelConfig.setLayout(null);

		//tab strip
		tabStrip = new JTabbedPane(JTabbedPane.TOP);
		tabStrip.setBorder(null);
		tabStrip.setBounds(10, 11, 498, 539);
		tabStrip.addTab( "Welcome", panelWelcome );
		tabStrip.addTab( "Config", panelConfig );
		frame.getContentPane().add(tabStrip);

		//------------------------------------------------------
		//---------------------CONFIG PANEL---------------------
		//------------------------------------------------------

		//Word Length
		Config.populateGridOptions();
		selectWordLength = new JComboBox(Config.OptionsWordLength);
		selectWordLength.setBounds(280, 90, 66, 20);
		panelConfig.add(selectWordLength);
		selectWordLength.setSelectedIndex(0);

		//minimum words
		textMinWords = new JTextField();
		textMinWords.setText("10");
		textMinWords.setBounds(280, 125, 28, 20);
		panelConfig.add(textMinWords);
		textMinWords.setColumns(10);

		//maximum words
		textMaxWords = new JTextField();
		textMaxWords.setText("10");
		textMaxWords.setBounds(318, 125, 28, 20);
		panelConfig.add(textMaxWords);
		textMaxWords.setColumns(10);

		//max elapsed time
		selectMaxAllowedTime = new JComboBox(Config.OptionsWaitTime);
		selectMaxAllowedTime.setBounds(280, 175, 66, 20);
		panelConfig.add(selectMaxAllowedTime);

		//label rows / columns
		JLabel lblRows = new JLabel("Word Length: ");
		lblRows.setForeground(Color.DARK_GRAY);
		lblRows.setBounds(119, 90, 151, 14);
		panelConfig.add(lblRows);

		//label min max words
		JLabel lblMinMaxWords = new JLabel("Min / Max Words Placed: ");
		lblMinMaxWords.setForeground(Color.DARK_GRAY);
		lblMinMaxWords.setBounds(119, 125, 151, 14);
		panelConfig.add(lblMinMaxWords);

		//label time elapsed
		JLabel lblElapsedTime = new JLabel("Max Elapsed Time (Secs)");
		lblElapsedTime.setForeground(Color.DARK_GRAY);
		lblElapsedTime.setBounds(119, 175, 151, 14);
		panelConfig.add(lblElapsedTime);

		//label config tab header
		JLabel lblConfigHeader = new JLabel("Setup Configuration");
		lblConfigHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfigHeader.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 29));
		lblConfigHeader.setBounds(0, 25, 493, 35);
		panelConfig.add(lblConfigHeader);

		//button generate puzzles
		JButton btnPlay = new JButton("Let's Play!");
		btnPlay.setBounds(100, 250, 140, 50);
		btnPlay.setVisible(true);
		panelConfig.add(btnPlay);
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
			}

		});

		//generate this puzzle button
		JButton btnGenerateHTML = new JButton("Generate HTML");
		btnGenerateHTML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnGenerateHTML.setBounds(250, 250, 140, 50);
		panelConfig.add(btnGenerateHTML);
		panelConfig.setVisible(true);
		panelConfig.setLayout(null);


		//crossword logo image icon
		ImageIcon crosswordsIcon = new ImageIcon(getClass().getResource(Config.AppIcon32));
		JLabel cwIconLabel = new JLabel("");
		cwIconLabel.setBounds(95, 25, 32, 32);
		cwIconLabel.setIcon(crosswordsIcon);
		cwIconLabel.setVisible(true);
		panelWelcome.add(cwIconLabel);


		//label welcome header
		JLabel lblWelcomeHeader = new JLabel("Rebus!");
		lblWelcomeHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeHeader.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 29));
		lblWelcomeHeader.setBounds(35, 25, 458, 29);
		panelWelcome.add(lblWelcomeHeader);

	}
}