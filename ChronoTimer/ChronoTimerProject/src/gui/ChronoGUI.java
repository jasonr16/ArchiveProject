package gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.ScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.TextArea;
import javax.swing.JRadioButton;
import java.awt.TextField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.JToggleButton;
import javax.swing.ImageIcon;

public class ChronoGUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChronoGUI window = new ChronoGUI();
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
	public ChronoGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 899, 773);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel MainFramePanel = new JPanel();
		MainFramePanel.setBackground(Color.WHITE);
		frame.getContentPane().add(MainFramePanel, BorderLayout.CENTER);
		MainFramePanel.setLayout(null);
		
		JButton btnPower = new JButton("Power");
		btnPower.setBounds(32, 24, 103, 25);
		MainFramePanel.add(btnPower);
		
		JButton btnFunction = new JButton("Function");
		btnFunction.setBounds(38, 309, 97, 25);
		MainFramePanel.add(btnFunction);
		
		JButton btnSwap = new JButton("Swap");
		btnSwap.setBounds(38, 504, 97, 25);
		MainFramePanel.add(btnSwap);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(304, 309, 240, 220);
		MainFramePanel.add(scrollPane);
		
		JTextArea txtrQueueRunning = new JTextArea();
		txtrQueueRunning.setText("Queue / Running / Final Time");
		txtrQueueRunning.setBounds(304, 555, 240, 22);
		MainFramePanel.add(txtrQueueRunning);
		
		JButton button = new JButton("");
		button.setBounds(325, 94, 43, 25);
		MainFramePanel.add(button);
		
		JButton button_1 = new JButton("");
		button_1.setBounds(380, 94, 43, 25);
		MainFramePanel.add(button_1);
		
		JButton button_2 = new JButton("");
		button_2.setBounds(435, 94, 43, 25);
		MainFramePanel.add(button_2);
		
		JButton button_3 = new JButton("");
		button_3.setBounds(490, 94, 43, 25);
		MainFramePanel.add(button_3);
		
		JTextArea txtrChronotimer = new JTextArea();
		txtrChronotimer.setText("CHRONOTIMER 9001");
		txtrChronotimer.setBounds(359, 13, 143, 22);
		MainFramePanel.add(txtrChronotimer);
		
		JButton btnPrinterPwr = new JButton("Printer Pwr");
		btnPrinterPwr.setBounds(670, 24, 97, 25);
		MainFramePanel.add(btnPrinterPwr);
		
		ScrollPane printerTape = new ScrollPane();
		printerTape.setBounds(653, 108, 126, 141);
		MainFramePanel.add(printerTape);
		
		ScrollPane printerBaseArea = new ScrollPane();
		printerBaseArea.setBounds(639, 157, 153, 115);
		MainFramePanel.add(printerBaseArea);
		
		JButton button_4 = new JButton("");
		button_4.setBounds(325, 203, 43, 25);
		MainFramePanel.add(button_4);
		
		JButton button_5 = new JButton("");
		button_5.setBounds(380, 203, 43, 25);
		MainFramePanel.add(button_5);
		
		JButton button_6 = new JButton("");
		button_6.setBounds(435, 203, 43, 25);
		MainFramePanel.add(button_6);
		
		JButton button_7 = new JButton("");
		button_7.setBounds(490, 203, 43, 25);
		MainFramePanel.add(button_7);
		
		JRadioButton radioButton = new JRadioButton("New radio button");
		radioButton.setBounds(335, 128, 24, 25);
		MainFramePanel.add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("New radio button");
		radioButton_1.setBounds(390, 128, 24, 25);
		MainFramePanel.add(radioButton_1);
		
		JRadioButton radioButton_2 = new JRadioButton("New radio button");
		radioButton_2.setBounds(445, 128, 24, 25);
		MainFramePanel.add(radioButton_2);
		
		JRadioButton radioButton_3 = new JRadioButton("New radio button");
		radioButton_3.setBounds(500, 128, 24, 25);
		MainFramePanel.add(radioButton_3);
		
		JRadioButton radioButton_4 = new JRadioButton("New radio button");
		radioButton_4.setBounds(335, 247, 24, 25);
		MainFramePanel.add(radioButton_4);
		
		JRadioButton radioButton_5 = new JRadioButton("New radio button");
		radioButton_5.setBounds(390, 247, 24, 25);
		MainFramePanel.add(radioButton_5);
		
		JRadioButton radioButton_6 = new JRadioButton("New radio button");
		radioButton_6.setBounds(445, 247, 24, 25);
		MainFramePanel.add(radioButton_6);
		
		JRadioButton radioButton_7 = new JRadioButton("New radio button");
		radioButton_7.setBounds(500, 247, 24, 25);
		MainFramePanel.add(radioButton_7);
		
		JTextArea txtrStart = new JTextArea();
		txtrStart.setText("Start");
		txtrStart.setBounds(211, 97, 97, 22);
		MainFramePanel.add(txtrStart);
		
		JTextArea txtrFinish = new JTextArea();
		txtrFinish.setText("Finish");
		txtrFinish.setBounds(211, 206, 97, 22);
		MainFramePanel.add(txtrFinish);
		
		JTextArea txtrEnabledisable = new JTextArea();
		txtrEnabledisable.setText("Enable/Disable");
		txtrEnabledisable.setBounds(165, 129, 143, 22);
		MainFramePanel.add(txtrEnabledisable);
		
		JTextArea textArea = new JTextArea();
		textArea.setText("Enable/Disable");
		textArea.setBounds(165, 250, 143, 22);
		MainFramePanel.add(textArea);
		
		JButton btnNewButton = new JButton("1");
		btnNewButton.setBounds(619, 319, 48, 46);
		MainFramePanel.add(btnNewButton);
		
		JButton button_8 = new JButton("2");
		button_8.setBounds(670, 319, 48, 46);
		MainFramePanel.add(button_8);
		
		JButton button_9 = new JButton("3");
		button_9.setBounds(719, 319, 48, 46);
		MainFramePanel.add(button_9);
		
		JButton button_10 = new JButton("4");
		button_10.setBounds(619, 366, 48, 46);
		MainFramePanel.add(button_10);
		
		JButton button_11 = new JButton("5");
		button_11.setBounds(670, 366, 48, 46);
		MainFramePanel.add(button_11);
		
		JButton button_12 = new JButton("6");
		button_12.setBounds(719, 366, 48, 46);
		MainFramePanel.add(button_12);
		
		JButton button_13 = new JButton("7");
		button_13.setBounds(619, 414, 48, 46);
		MainFramePanel.add(button_13);
		
		JButton button_14 = new JButton("8");
		button_14.setBounds(670, 414, 48, 46);
		MainFramePanel.add(button_14);
		
		JButton button_15 = new JButton("9");
		button_15.setBounds(719, 414, 48, 46);
		MainFramePanel.add(button_15);
		
		JButton button_16 = new JButton("*");
		button_16.setBounds(619, 461, 48, 46);
		MainFramePanel.add(button_16);
		
		JButton button_17 = new JButton("0");
		button_17.setBounds(670, 461, 48, 46);
		MainFramePanel.add(button_17);
		
		JButton button_18 = new JButton("#");
		button_18.setBounds(719, 461, 48, 46);
		MainFramePanel.add(button_18);
		
		JPanel backview = new JPanel();
		backview.setBackground(Color.WHITE);
		backview.setBorder(new LineBorder(new Color(192, 192, 192), 2));
		backview.setBounds(0, 590, 881, 138);
		MainFramePanel.add(backview);
		backview.setLayout(null);
		
		JTextArea txtrChan = new JTextArea();
		txtrChan.setText("CHAN");
		txtrChan.setBounds(12, 13, 61, 22);
		backview.add(txtrChan);
		
		JRadioButton radioButton_8 = new JRadioButton("New radio button");
		radioButton_8.setBounds(99, 44, 24, 25);
		backview.add(radioButton_8);
		
		JRadioButton radioButton_9 = new JRadioButton("New radio button");
		radioButton_9.setBounds(144, 44, 24, 25);
		backview.add(radioButton_9);
		
		JRadioButton radioButton_10 = new JRadioButton("New radio button");
		radioButton_10.setBounds(195, 44, 24, 25);
		backview.add(radioButton_10);
		
		JRadioButton radioButton_11 = new JRadioButton("New radio button");
		radioButton_11.setBounds(247, 44, 24, 25);
		backview.add(radioButton_11);
		
		JRadioButton radioButton_12 = new JRadioButton("New radio button");
		radioButton_12.setBounds(99, 104, 24, 25);
		backview.add(radioButton_12);
		
		JRadioButton radioButton_13 = new JRadioButton("New radio button");
		radioButton_13.setBounds(144, 104, 24, 25);
		backview.add(radioButton_13);
		
		JRadioButton radioButton_14 = new JRadioButton("New radio button");
		radioButton_14.setBounds(195, 104, 24, 25);
		backview.add(radioButton_14);
		
		JRadioButton radioButton_15 = new JRadioButton("New radio button");
		radioButton_15.setBounds(247, 104, 24, 25);
		backview.add(radioButton_15);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setText("3");
		textArea_1.setBounds(150, 13, 12, 22);
		backview.add(textArea_1);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setText("5");
		textArea_2.setBounds(200, 13, 12, 22);
		backview.add(textArea_2);
		
		JTextArea textArea_3 = new JTextArea();
		textArea_3.setText("7");
		textArea_3.setBounds(252, 13, 12, 22);
		backview.add(textArea_3);
		
		JTextArea textArea_4 = new JTextArea();
		textArea_4.setText("1");
		textArea_4.setBounds(105, 13, 12, 22);
		backview.add(textArea_4);
		
		JTextArea textArea_5 = new JTextArea();
		textArea_5.setText("2");
		textArea_5.setBounds(105, 73, 12, 22);
		backview.add(textArea_5);
		
		JTextArea textArea_6 = new JTextArea();
		textArea_6.setText("4");
		textArea_6.setBounds(150, 73, 12, 22);
		backview.add(textArea_6);
		
		JTextArea textArea_7 = new JTextArea();
		textArea_7.setText("6");
		textArea_7.setBounds(201, 73, 12, 22);
		backview.add(textArea_7);
		
		JTextArea textArea_8 = new JTextArea();
		textArea_8.setText("8");
		textArea_8.setBounds(252, 73, 12, 22);
		backview.add(textArea_8);
		
		JTextArea txtrUsbPort = new JTextArea();
		txtrUsbPort.setText("USB PORT");
		txtrUsbPort.setBounds(429, 45, 83, 22);
		backview.add(txtrUsbPort);
		
		JToggleButton toggleButton = new JToggleButton("");
		toggleButton.setBounds(343, 44, 83, 25);
		backview.add(toggleButton);
		
		JButton leftArrow = new JButton("");
		leftArrow.setIcon(new ImageIcon(ChronoGUI.class.getResource("/gui/leftArrow.jpg")));
		leftArrow.setBounds(12, 340, 57, 61);
		MainFramePanel.add(leftArrow);
		
		JButton rightArrow = new JButton("");
		rightArrow.setIcon(new ImageIcon(ChronoGUI.class.getResource("/gui/rightArrow.jpg")));
		rightArrow.setBounds(78, 340, 57, 61);
		MainFramePanel.add(rightArrow);
		
		JButton downArrow = new JButton("");
		downArrow.setIcon(new ImageIcon(ChronoGUI.class.getResource("/gui/downArrow.jpg")));
		downArrow.setBounds(142, 340, 57, 61);
		MainFramePanel.add(downArrow);
		
		JButton upArrow = new JButton("");
		upArrow.setIcon(new ImageIcon(ChronoGUI.class.getResource("/gui/upArrow.jpg")));
		upArrow.setBounds(210, 340, 57, 61);
		MainFramePanel.add(upArrow);
	}
}
