package gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.ScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.TextArea;
import javax.swing.JRadioButton;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import chronoTimerMain.simulator.hardwareHandler.ChronoHardwareHandler;

import javax.swing.JToggleButton;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ChronoGUI {
	private int numberArgumentsRemaining;
	private JFrame frame;
	private ChronoHardwareHandler hardware;
	String[] args=new String[2];
	String timestamp="";
	String command;
	private JComboBox<String> channelType;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ChronoGUI window = new ChronoGUI();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

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
		btnPower.addActionListener(new commandListener("Power"));
		
		JButton btnFunction = new JButton("Function");
		btnFunction.setBounds(32, 294, 97, 25);
		MainFramePanel.add(btnFunction);
		btnFunction.addActionListener(new commandListener("Function"));
		
		JButton btnSwap = new JButton("Swap");
		btnSwap.setBounds(38, 504, 97, 25);
		MainFramePanel.add(btnSwap);
		btnSwap.addActionListener(new commandListener("Swap"));
		
		ScrollPane displayScrollPane = new ScrollPane();
		displayScrollPane.setBounds(304, 309, 240, 220);
		MainFramePanel.add(displayScrollPane);
		
		JTextArea txtQueueRunning = new JTextArea();
		txtQueueRunning.setText("Queue / Running / Final Time");
		txtQueueRunning.setBounds(304, 555, 240, 22);
		MainFramePanel.add(txtQueueRunning);
		
		JButton btnstart1 = new JButton("");
		btnstart1.setBounds(325, 94, 43, 25);
		MainFramePanel.add(btnstart1);
		btnstart1.addActionListener(new manualStartFin('1'));
		
		JButton btnstart3 = new JButton("");
		btnstart3.setBounds(380, 94, 43, 25);
		MainFramePanel.add(btnstart3);
		btnstart3.addActionListener(new manualStartFin('3'));
		
		JButton btnstart5 = new JButton("");
		btnstart5.setBounds(435, 94, 43, 25);
		MainFramePanel.add(btnstart5);
		btnstart5.addActionListener(new manualStartFin('5'));
		
		JButton btnstart7 = new JButton("");
		btnstart7.setBounds(490, 94, 43, 25);
		MainFramePanel.add(btnstart7);
		btnstart7.addActionListener(new manualStartFin('7'));
		
		JTextArea txtrChronotimer = new JTextArea();
		txtrChronotimer.setText("CHRONOTIMER 9001");
		txtrChronotimer.setBounds(359, 13, 143, 22);
		MainFramePanel.add(txtrChronotimer);
		
		JButton btnPrinterPwr = new JButton("Printer Pwr");
		btnPrinterPwr.setBounds(653, 24, 126, 25);
		MainFramePanel.add(btnPrinterPwr);
		btnPrinterPwr.addActionListener(new commandListener("Printer Pwr"));
		
		ScrollPane printerTapePane = new ScrollPane();
		printerTapePane.setBounds(653, 108, 126, 141);
		MainFramePanel.add(printerTapePane);
		
		
		ScrollPane printerBaseAreaPane = new ScrollPane();
		printerBaseAreaPane.setBounds(639, 157, 153, 115);
		MainFramePanel.add(printerBaseAreaPane);
		
		JButton btnfinish2 = new JButton("");
		btnfinish2.setBounds(325, 203, 43, 25);
		MainFramePanel.add(btnfinish2);
		btnfinish2.addActionListener(new manualStartFin('2'));
				
		JButton btnfinish4 = new JButton("");
		btnfinish4.setBounds(380, 203, 43, 25);
		MainFramePanel.add(btnfinish4);
		btnfinish4.addActionListener(new manualStartFin('4'));
		
		JButton btnfinish6 = new JButton("");
		btnfinish6.setBounds(435, 203, 43, 25);
		MainFramePanel.add(btnfinish6);
		btnfinish6.addActionListener(new manualStartFin('6'));
		
		JButton btnfinish8 = new JButton("");
		btnfinish8.setBounds(490, 203, 43, 25);
		MainFramePanel.add(btnfinish8);
		btnfinish8.addActionListener(new manualStartFin('8'));
		
		JRadioButton radbtnenable1 = new JRadioButton("New radio button");
		radbtnenable1.setBounds(335, 128, 20, 25);
		MainFramePanel.add(radbtnenable1);
		radbtnenable1.addActionListener(new toggleListner('1',true) );
		
		JRadioButton radbtnenable3 = new JRadioButton("New radio button");
		radbtnenable3.setBounds(390, 128, 20, 25);
		MainFramePanel.add(radbtnenable3);
		radbtnenable1.addActionListener(new toggleListner('3',true) );
		
		JRadioButton radbtnenable5 = new JRadioButton("New radio button");
		radbtnenable5.setBounds(445, 128, 20, 25);
		MainFramePanel.add(radbtnenable5);
		radbtnenable1.addActionListener(new toggleListner('5',true) );
		
		JRadioButton radbtnenable7 = new JRadioButton("New radio button");
		radbtnenable7.setBounds(500, 128, 20, 25);
		MainFramePanel.add(radbtnenable7);
		radbtnenable1.addActionListener(new toggleListner('7',true) );
		
		JRadioButton radbtnenable2 = new JRadioButton("New radio button");
		radbtnenable2.setBounds(335, 247, 20, 25);
		MainFramePanel.add(radbtnenable2);
		radbtnenable1.addActionListener(new toggleListner('2',true) );
		
		JRadioButton radbtnenable4 = new JRadioButton("New radio button");
		radbtnenable4.setBounds(390, 247, 20, 25);
		MainFramePanel.add(radbtnenable4);
		radbtnenable1.addActionListener(new toggleListner('4',true) );
		
		JRadioButton radbtnenable6 = new JRadioButton("New radio button");
		radbtnenable6.setBounds(445, 247, 20, 25);
		MainFramePanel.add(radbtnenable6);
		radbtnenable1.addActionListener(new toggleListner('6',true) );
		
		JRadioButton radbtnenable8 = new JRadioButton("New radio button");
		radbtnenable8.setBounds(500, 247, 20, 25);
		MainFramePanel.add(radbtnenable8);
		radbtnenable1.addActionListener(new toggleListner('8',true) );
		
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
		
		JTextArea textED2 = new JTextArea();
		textED2.setText("Enable/Disable");
		textED2.setBounds(165, 250, 143, 22);
		MainFramePanel.add(textED2);
		
		JButton btnkeypad1 = new JButton("1");
		btnkeypad1.setBounds(619, 319, 48, 46);
		MainFramePanel.add(btnkeypad1);
		btnkeypad1.addActionListener(new optionsListener('1'));
		
		JButton btnkeypad2 = new JButton("2");
		btnkeypad2.setBounds(670, 319, 48, 46);
		MainFramePanel.add(btnkeypad2);
		btnkeypad2.addActionListener(new optionsListener('2'));
		
		JButton btnkeypad3 = new JButton("3");
		btnkeypad3.setBounds(719, 319, 48, 46);
		MainFramePanel.add(btnkeypad3);
		btnkeypad3.addActionListener(new optionsListener('3'));
		
		JButton btnkeypad4 = new JButton("4");
		btnkeypad4.setBounds(619, 366, 48, 46);
		MainFramePanel.add(btnkeypad4);
		btnkeypad4.addActionListener(new optionsListener('4'));
		
		JButton btnkeypad5 = new JButton("5");
		btnkeypad5.setBounds(670, 366, 48, 46);
		MainFramePanel.add(btnkeypad5);
		btnkeypad5.addActionListener(new optionsListener('5'));
		
		JButton btnkeypad6 = new JButton("6");
		btnkeypad6.setBounds(719, 366, 48, 46);
		MainFramePanel.add(btnkeypad6);
		btnkeypad6.addActionListener(new optionsListener('6'));
		
		JButton btnkeypad7 = new JButton("7");
		btnkeypad7.setBounds(619, 414, 48, 46);
		MainFramePanel.add(btnkeypad7);
		btnkeypad7.addActionListener(new optionsListener('7'));
				
		JButton btnkeypad8 = new JButton("8");
		btnkeypad8.setBounds(670, 414, 48, 46);
		MainFramePanel.add(btnkeypad8);
		btnkeypad8.addActionListener(new optionsListener('8'));
		
		JButton btnkeypad9 = new JButton("9");
		btnkeypad9.setBounds(719, 414, 48, 46);
		MainFramePanel.add(btnkeypad9);
		btnkeypad9.addActionListener(new optionsListener('9'));
		
		JButton btnkeypadstar = new JButton("*");
		btnkeypadstar.setBounds(619, 461, 48, 46);
		MainFramePanel.add(btnkeypadstar);
		btnkeypadstar.addActionListener(new optionsListener('*'));
		
		
		JButton btnkeypad0 = new JButton("0");
		btnkeypad0.setBounds(670, 461, 48, 46);
		MainFramePanel.add(btnkeypad0);
		btnkeypad0.addActionListener(new optionsListener('0'));
		
		JButton btnkeypadpound = new JButton("#");
		btnkeypadpound.setBounds(719, 461, 48, 46);
		MainFramePanel.add(btnkeypadpound);
		btnkeypadpound.addActionListener(new optionsListener('#'));
		
		JPanel backviewpanel = new JPanel();
		backviewpanel.setBackground(Color.WHITE);
		backviewpanel.setBorder(new LineBorder(new Color(192, 192, 192), 2));
		backviewpanel.setBounds(0, 590, 881, 138);
		MainFramePanel.add(backviewpanel);
		backviewpanel.setLayout(null);
		
		JTextArea txtrChan = new JTextArea();
		txtrChan.setText("CHAN");
		txtrChan.setBounds(12, 13, 61, 22);
		backviewpanel.add(txtrChan);
		
		JRadioButton radioButton_8 = new JRadioButton("New radio button");
		radioButton_8.setBounds(99, 44, 20, 25);
		backviewpanel.add(radioButton_8);
		radioButton_8.addActionListener(new toggleListner('1',false));
		
		JRadioButton radioButton_9 = new JRadioButton("New radio button");
		radioButton_9.setBounds(144, 44, 20, 25);
		backviewpanel.add(radioButton_9);
		radioButton_9.addActionListener(new toggleListner('3',false));
		
		JRadioButton radioButton_10 = new JRadioButton("New radio button");
		radioButton_10.setBounds(195, 44, 20, 25);
		backviewpanel.add(radioButton_10);
		radioButton_10.addActionListener(new toggleListner('5',false));
		
		JRadioButton radioButton_11 = new JRadioButton("New radio button");
		radioButton_11.setBounds(247, 44, 20, 25);
		backviewpanel.add(radioButton_11);
		radioButton_11.addActionListener(new toggleListner('7',false));
		
		JRadioButton radioButton_12 = new JRadioButton("New radio button");
		radioButton_12.setBounds(99, 104, 20, 25);
		backviewpanel.add(radioButton_12);
		radioButton_12.addActionListener(new toggleListner('2',false));
		
		JRadioButton radioButton_13 = new JRadioButton("New radio button");
		radioButton_13.setBounds(144, 104, 20, 25);
		backviewpanel.add(radioButton_13);
		radioButton_13.addActionListener(new toggleListner('4',false));
		
		JRadioButton radioButton_14 = new JRadioButton("New radio button");
		radioButton_14.setBounds(195, 104, 20, 25);
		backviewpanel.add(radioButton_14);
		radioButton_14.addActionListener(new toggleListner('6',false));
		
		JRadioButton radioButton_15 = new JRadioButton("New radio button");
		radioButton_15.setBounds(247, 104, 20, 25);
		backviewpanel.add(radioButton_15);
		radioButton_15.addActionListener(new toggleListner('8',false));
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setText("3");
		textArea_1.setBounds(144, 13, 12, 22);
		backviewpanel.add(textArea_1);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setText("5");
		textArea_2.setBounds(195, 13, 12, 22);
		backviewpanel.add(textArea_2);
		
		JTextArea textArea_3 = new JTextArea();
		textArea_3.setText("7");
		textArea_3.setBounds(247, 13, 12, 22);
		backviewpanel.add(textArea_3);
		
		JTextArea textArea_4 = new JTextArea();
		textArea_4.setText("1");
		textArea_4.setBounds(99, 13, 12, 22);
		backviewpanel.add(textArea_4);
		
		JTextArea textArea_5 = new JTextArea();
		textArea_5.setText("2");
		textArea_5.setBounds(99, 73, 12, 22);
		backviewpanel.add(textArea_5);
		
		JTextArea textArea_6 = new JTextArea();
		textArea_6.setText("4");
		textArea_6.setBounds(144, 73, 12, 22);
		backviewpanel.add(textArea_6);
		
		JTextArea textArea_7 = new JTextArea();
		textArea_7.setText("6");
		textArea_7.setBounds(195, 73, 12, 22);
		backviewpanel.add(textArea_7);
		
		JTextArea textArea_8 = new JTextArea();
		textArea_8.setText("8");
		textArea_8.setBounds(247, 73, 12, 22);
		backviewpanel.add(textArea_8);
		
		JTextArea txtrUsbPort = new JTextArea();
		txtrUsbPort.setText("USB PORT");
		txtrUsbPort.setBounds(429, 45, 83, 22);
		backviewpanel.add(txtrUsbPort);
		
		JToggleButton toggleButton = new JToggleButton("");
		toggleButton.setBounds(343, 44, 83, 25);
		backviewpanel.add(toggleButton);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Eye", "Gate", "Pad"}));
		comboBox.setBounds(110, 13, 20, 22);
		backviewpanel.add(comboBox);
		
		JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Eye", "Gate", "Pad"}));
		comboBox_1.setBounds(155, 13, 20, 22);
		backviewpanel.add(comboBox_1);
		
		JComboBox<String> comboBox_2 = new JComboBox<String>();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"Eye", "Gate", "Pad"}));
		comboBox_2.setBounds(207, 13, 20, 22);
		backviewpanel.add(comboBox_2);
		
		JComboBox<String> comboBox_3 = new JComboBox<String>();
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"Eye", "Gate", "Pad"}));
		comboBox_3.setBounds(257, 13, 20, 22);
		backviewpanel.add(comboBox_3);
		
		JComboBox<String> comboBox_4 = new JComboBox<String>();
		comboBox_4.setModel(new DefaultComboBoxModel(new String[] {"Eye", "Gate", "Pad"}));
		comboBox_4.setBounds(109, 73, 20, 22);
		backviewpanel.add(comboBox_4);
		
		JComboBox<String> comboBox_5 = new JComboBox<String>();
		comboBox_5.setModel(new DefaultComboBoxModel(new String[] {"Eye", "Gate", "Pad"}));
		comboBox_5.setBounds(154, 73, 20, 22);
		backviewpanel.add(comboBox_5);
		
		JComboBox<String> comboBox_6 = new JComboBox<String>();
		comboBox_6.setModel(new DefaultComboBoxModel(new String[] {"Eye", "Gate", "Pad"}));
		comboBox_6.setBounds(205, 73, 20, 22);
		backviewpanel.add(comboBox_6);
		
		JComboBox<String> comboBox_7 = new JComboBox<String>();
		comboBox_7.setModel(new DefaultComboBoxModel(new String[] {"Eye", "Gate", "Pad"}));
		comboBox_7.setBounds(257, 73, 20, 22);
		backviewpanel.add(comboBox_7);
		
		JButton btnleftArrow = new JButton("");
		btnleftArrow.setIcon(new ImageIcon(ChronoGUI.class.getResource("/gui/leftArrow.jpg")));
		btnleftArrow.setBounds(12, 340, 57, 61);
		MainFramePanel.add(btnleftArrow);
		
		JButton btnrightArrow = new JButton("");
		btnrightArrow.setIcon(new ImageIcon(ChronoGUI.class.getResource("/gui/rightArrow.jpg")));
		btnrightArrow.setBounds(78, 340, 57, 61);
		MainFramePanel.add(btnrightArrow);
		
		JButton btndownArrow = new JButton("");
		btndownArrow.setIcon(new ImageIcon(ChronoGUI.class.getResource("/gui/downArrow.jpg")));
		btndownArrow.setBounds(142, 340, 57, 61);
		MainFramePanel.add(btndownArrow);
		
		JButton btnupArrow = new JButton("");
		btnupArrow.setIcon(new ImageIcon(ChronoGUI.class.getResource("/gui/upArrow.jpg")));
		btnupArrow.setBounds(210, 340, 57, 61);
		MainFramePanel.add(btnupArrow);
		
		JTextArea starttxt1 = new JTextArea();
		starttxt1.setText("1");
		starttxt1.setBounds(335, 59, 24, 22);
		MainFramePanel.add(starttxt1);
		
		JTextArea starttxt3 = new JTextArea();
		starttxt3.setText("3");
		starttxt3.setBounds(390, 59, 24, 22);
		MainFramePanel.add(starttxt3);
		
		JTextArea starttxt5 = new JTextArea();
		starttxt5.setText("5");
		starttxt5.setBounds(445, 59, 24, 22);
		MainFramePanel.add(starttxt5);
		
		JTextArea starttxt7 = new JTextArea();
		starttxt7.setText("7");
		starttxt7.setBounds(500, 59, 24, 22);
		MainFramePanel.add(starttxt7);
		
		JTextArea starttxt2 = new JTextArea();
		starttxt2.setText("2");
		starttxt2.setBounds(335, 168, 24, 22);
		MainFramePanel.add(starttxt2);
		
		JTextArea starttxt4 = new JTextArea();
		starttxt4.setText("4");
		starttxt4.setBounds(390, 168, 24, 22);
		MainFramePanel.add(starttxt4);
		
		JTextArea starttxt6 = new JTextArea();
		starttxt6.setText("6");
		starttxt6.setBounds(445, 168, 24, 22);
		MainFramePanel.add(starttxt6);
		
		JTextArea starttxt8 = new JTextArea();
		starttxt8.setText("8");
		starttxt8.setBounds(500, 168, 24, 22);
		MainFramePanel.add(starttxt8);
		frame.setVisible(true);
	}
	//TODO
	public class commandListener implements ActionListener {
		
		public commandListener(String command){
			ChronoGUI.this.command=command;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO finish this shit
			switch(command){
			case "Power":
				numberArgumentsRemaining=0;
				args[0]="";
				args[1]="";
				hardware.inputFromSimulator(command,args, "");
				break;
			case "Function"://??? No idea
			case "Swap":
				numberArgumentsRemaining=0;
				args[0]="";
				args[1]="";
				hardware.inputFromSimulator(command, args, timestamp);
				break;
			case "Printer Pwr":

			}

		}
	//TODO implement printer tape etc
	}
	public class optionsListener implements ActionListener{
		char option;
		public optionsListener(char x){
			option=x;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO check
			if(numberArgumentsRemaining!=0){
				switch(option){
				case '#':
					if(numberArgumentsRemaining==1){
						hardware.inputFromSimulator(command, args, timestamp);
					}else{
						args[2-numberArgumentsRemaining--]+=option;
						
					}break;
				default:
					args[2-numberArgumentsRemaining]=""+option;
					break;
				}
			}
		}
		
	}
	public class toggleListner implements ActionListener{
		boolean toggle;
		public toggleListner(char c,boolean t){
			toggle=t;
			args[0]="" +c;
			args[1]="";
		}
		//TODO: check
		@Override
		public void actionPerformed(ActionEvent e) {
			if(toggle){
			hardware.inputFromSimulator("TOGGLE", args, timestamp);
			}else{
				args[1]=(String) ChronoGUI.this.channelType.getSelectedItem();
				hardware.inputFromSimulator("CONN", args, timestamp);
			}
		}
		
	}
	public class manualStartFin implements ActionListener{
		public manualStartFin(char c){ 
			args[0]=""+c;
			args[1]="";
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			hardware.inputFromSimulator("TRIG", args, timestamp);
			
		}
		
	}
}
