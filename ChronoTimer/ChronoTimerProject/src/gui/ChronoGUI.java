package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

import chronoTimerMain.simulator.hardwareHandler.ChronoHardwareHandler;
import javax.swing.JTextField;

public class ChronoGUI {
	private String keypadEntry = "";
	private int numberArgumentsRemaining;
	private JFrame frame;
	private ChronoHardwareHandler hardware;
	String[] args=new String[2];
	String timestamp="";
	String command;
	private JComboBox<String> channelType;
	private ScrollPane displayPane;
	private ScrollPane printerPane;
	private JTextField keypadText;

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
		hardware = new ChronoHardwareHandler();
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
		MainFramePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		MainFramePanel.setBackground(Color.WHITE);
		frame.getContentPane().add(MainFramePanel, BorderLayout.CENTER);
		MainFramePanel.setLayout(null);
		
		JButton btnPower = new JButton("Power");
		btnPower.addMouseListener(new MouseAdapter() {//TODO TEMPLATE Example - JASON
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("power", "", "");
			}
		});
		btnPower.setBounds(32, 24, 103, 25);
		MainFramePanel.add(btnPower);
		//btnFunction.addActionListener(new commandListener("Function"));
		
		JButton btnSwap = new JButton("Swap");
		btnSwap.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("swap", "", "");
			}
		});
		btnSwap.setBounds(38, 504, 97, 25);
		MainFramePanel.add(btnSwap);
		//btnSwap.addActionListener(new commandListener("Swap"));
		
		ScrollPane displayScrollPane = new ScrollPane();
		displayScrollPane.setBounds(325, 309, 177, 220);
		MainFramePanel.add(displayScrollPane);
		displayPane = displayScrollPane;
		
		JTextArea txtQueueRunning = new JTextArea();
		txtQueueRunning.setEditable(false);
		txtQueueRunning.setText("Queue / Running / Final Time");
		txtQueueRunning.setBounds(304, 555, 240, 22);
		MainFramePanel.add(txtQueueRunning);
		
		JButton btnstart1 = new JButton("");
		btnstart1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("start", "1", "");
			}
		});
		btnstart1.setBounds(325, 94, 43, 25);
		MainFramePanel.add(btnstart1);
		//btnstart1.addActionListener(new manualStartFin('1'));
		
		JButton btnstart3 = new JButton("");
		btnstart3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("start", "3", "");
			}
		});
		btnstart3.setBounds(380, 94, 43, 25);
		MainFramePanel.add(btnstart3);
		//btnstart3.addActionListener(new manualStartFin('3'));
		
		JButton btnstart5 = new JButton("");
		btnstart5.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("start", "5", "");
			}
		});
		btnstart5.setBounds(435, 94, 43, 25);
		MainFramePanel.add(btnstart5);
		//btnstart5.addActionListener(new manualStartFin('5'));
		
		JButton btnstart7 = new JButton("");
		btnstart7.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("start", "7", "");
			}
		});
		btnstart7.setBounds(490, 94, 43, 25);
		MainFramePanel.add(btnstart7);
		//btnstart7.addActionListener(new manualStartFin('7'));
		
		JTextArea txtrChronotimer = new JTextArea();
		txtrChronotimer.setText("CHRONOTIMER 9001");
		txtrChronotimer.setBounds(359, 13, 143, 22);
		txtrChronotimer.setEditable(false);
		MainFramePanel.add(txtrChronotimer);
		
		JButton btnPrinterPwr = new JButton("Printer Pwr");
		btnPrinterPwr.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("print", "", "");
				
			}
		});
		btnPrinterPwr.setBounds(653, 24, 126, 25);
		MainFramePanel.add(btnPrinterPwr);
//		btnPrinterPwr.addActionListener(new commandListener("Printer Pwr"));
		
		ScrollPane printerTapePane = new ScrollPane();
		printerTapePane.setBounds(604, 74, 234, 175);
		MainFramePanel.add(printerTapePane);
		printerPane = printerTapePane;
		
		
		ScrollPane printerBaseAreaPane = new ScrollPane();
		printerBaseAreaPane.setBounds(590, 157, 263, 115);
		MainFramePanel.add(printerBaseAreaPane);
		
		JButton btnfinish2 = new JButton("");
		btnfinish2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("finish", "2", "");
			}
		});
		btnfinish2.setBounds(325, 203, 43, 25);
		MainFramePanel.add(btnfinish2);
		//btnfinish2.addActionListener(new manualStartFin('2'));
				
		JButton btnfinish4 = new JButton("");
		btnfinish4.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("finish", "2", "");
			}
		});
		btnfinish4.setBounds(380, 203, 43, 25);
		MainFramePanel.add(btnfinish4);
		//btnfinish4.addActionListener(new manualStartFin('4'));
		
		JButton btnfinish6 = new JButton("");
		btnfinish6.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("finish", "2", "");
			}
		});
		btnfinish6.setBounds(435, 203, 43, 25);
		MainFramePanel.add(btnfinish6);
		//btnfinish6.addActionListener(new manualStartFin('6'));
		
		JButton btnfinish8 = new JButton("");
		btnfinish8.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("finish", "2", "");
			}
		});
		btnfinish8.setBounds(490, 203, 43, 25);
		MainFramePanel.add(btnfinish8);
		//btnfinish8.addActionListener(new manualStartFin('8'));
		
		JRadioButton radbtnenable1 = new JRadioButton("New radio button");
		radbtnenable1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sendToHardware("tog", "1", "");
			}
		});
		radbtnenable1.setBounds(335, 128, 20, 25);
		MainFramePanel.add(radbtnenable1);
		//radbtnenable1.addActionListener(new toggleListner('1',true) );
		
		JRadioButton radbtnenable3 = new JRadioButton("New radio button");
		radbtnenable3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sendToHardware("tog", "3", "");
			}
		});
		radbtnenable3.setBounds(390, 128, 20, 25);
		MainFramePanel.add(radbtnenable3);
		//radbtnenable1.addActionListener(new toggleListner('3',true) );
		
		JRadioButton radbtnenable5 = new JRadioButton("New radio button");
		radbtnenable5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sendToHardware("tog", "5", "");
			}
		});
		radbtnenable5.setBounds(445, 128, 20, 25);
		MainFramePanel.add(radbtnenable5);
		//radbtnenable1.addActionListener(new toggleListner('5',true) );
		
		JRadioButton radbtnenable7 = new JRadioButton("New radio button");
		radbtnenable7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sendToHardware("tog", "7", "");
			}
		});
		radbtnenable7.setBounds(500, 128, 20, 25);
		MainFramePanel.add(radbtnenable7);
		//radbtnenable1.addActionListener(new toggleListner('7',true) );
		
		JRadioButton radbtnenable2 = new JRadioButton("New radio button");
		radbtnenable2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sendToHardware("tog", "2", "");
			}
		});
		radbtnenable2.setBounds(335, 247, 20, 25);
		MainFramePanel.add(radbtnenable2);
		//radbtnenable1.addActionListener(new toggleListner('2',true) );
		
		JRadioButton radbtnenable4 = new JRadioButton("New radio button");
		radbtnenable4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sendToHardware("tog", "4", "");
			}
		});
		radbtnenable4.setBounds(390, 247, 20, 25);
		MainFramePanel.add(radbtnenable4);
		//radbtnenable1.addActionListener(new toggleListner('4',true) );
		
		JRadioButton radbtnenable6 = new JRadioButton("New radio button");
		radbtnenable6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sendToHardware("tog", "6", "");
			}
		});
		radbtnenable6.setBounds(445, 247, 20, 25);
		MainFramePanel.add(radbtnenable6);
		//radbtnenable1.addActionListener(new toggleListner('6',true) );
		
		JRadioButton radbtnenable8 = new JRadioButton("New radio button");
		radbtnenable8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sendToHardware("tog", "8", "");
			}
		});
		radbtnenable8.setBounds(500, 247, 20, 25);
		MainFramePanel.add(radbtnenable8);
		//radbtnenable1.addActionListener(new toggleListner('8',true) );
		
		JTextArea txtrStart = new JTextArea();
		txtrStart.setEditable(false);
		txtrStart.setText("Start");
		txtrStart.setBounds(211, 97, 97, 22);
		MainFramePanel.add(txtrStart);
		
		JTextArea txtrFinish = new JTextArea();
		txtrFinish.setEditable(false);
		txtrFinish.setText("Finish");
		txtrFinish.setBounds(211, 206, 97, 22);
		MainFramePanel.add(txtrFinish);
		
		JTextArea txtrEnabledisable = new JTextArea();
		txtrEnabledisable.setEditable(false);
		txtrEnabledisable.setText("Enable/Disable");
		txtrEnabledisable.setBounds(165, 129, 143, 22);
		MainFramePanel.add(txtrEnabledisable);
		
		JTextArea textED2 = new JTextArea();
		textED2.setEditable(false);
		textED2.setText("Enable/Disable");
		textED2.setBounds(165, 250, 143, 22);
		MainFramePanel.add(textED2);
		
		JButton btnkeypad1 = new JButton("1");
		btnkeypad1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				keypadEntry += "1";
				updateKeypad(keypadEntry);
			}
		});
		btnkeypad1.setBounds(619, 319, 48, 46);
		MainFramePanel.add(btnkeypad1);
		//btnkeypad1.addActionListener(new optionsListener('1'));
		
		JButton btnkeypad2 = new JButton("2");
		btnkeypad2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				keypadEntry += "2";
				updateKeypad(keypadEntry);
			}
		});
		btnkeypad2.setBounds(670, 319, 48, 46);
		MainFramePanel.add(btnkeypad2);
		//btnkeypad2.addActionListener(new optionsListener('2'));
		
		JButton btnkeypad3 = new JButton("3");
		btnkeypad3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				keypadEntry += "3";
				updateKeypad(keypadEntry);
			}
		});
		btnkeypad3.setBounds(719, 319, 48, 46);
		MainFramePanel.add(btnkeypad3);
		//btnkeypad3.addActionListener(new optionsListener('3'));
		
		JButton btnkeypad4 = new JButton("4");
		btnkeypad4.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				keypadEntry += "4";
				updateKeypad(keypadEntry);
			}
		});
		btnkeypad4.setBounds(619, 366, 48, 46);
		MainFramePanel.add(btnkeypad4);
		//btnkeypad4.addActionListener(new optionsListener('4'));
		
		JButton btnkeypad5 = new JButton("5");
		btnkeypad5.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				keypadEntry += "5";
				updateKeypad(keypadEntry);
			}
		});
		btnkeypad5.setBounds(670, 366, 48, 46);
		MainFramePanel.add(btnkeypad5);
		//btnkeypad5.addActionListener(new optionsListener('5'));
		
		JButton btnkeypad6 = new JButton("6");
		btnkeypad6.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				keypadEntry += "6";
				updateKeypad(keypadEntry);
			}
		});
		btnkeypad6.setBounds(719, 366, 48, 46);
		MainFramePanel.add(btnkeypad6);
		//btnkeypad6.addActionListener(new optionsListener('6'));
		
		JButton btnkeypad7 = new JButton("7");
		btnkeypad7.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				keypadEntry += "7";
				updateKeypad(keypadEntry);
			}
		});
		btnkeypad7.setBounds(619, 414, 48, 46);
		MainFramePanel.add(btnkeypad7);
		//btnkeypad7.addActionListener(new optionsListener('7'));
				
		JButton btnkeypad8 = new JButton("8");
		btnkeypad8.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				keypadEntry += "8";
				updateKeypad(keypadEntry);
			}
		});
		btnkeypad8.setBounds(670, 414, 48, 46);
		MainFramePanel.add(btnkeypad8);
		//btnkeypad8.addActionListener(new optionsListener('8'));
		
		JButton btnkeypad9 = new JButton("9");
		btnkeypad9.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				keypadEntry += "9";
				updateKeypad(keypadEntry);
			}
		});
		btnkeypad9.setBounds(719, 414, 48, 46);
		MainFramePanel.add(btnkeypad9);
		//btnkeypad9.addActionListener(new optionsListener('9'));
		
		JButton btnkeypadstar = new JButton("*");
		btnkeypadstar.setBounds(619, 461, 48, 46);
		MainFramePanel.add(btnkeypadstar);
		//btnkeypadstar.addActionListener(new optionsListener('*'));
		
		
		JButton btnkeypad0 = new JButton("0");
		btnkeypad0.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				keypadEntry += "0";
				updateKeypad(keypadEntry);
			}
		});
		btnkeypad0.setBounds(670, 461, 48, 46);
		MainFramePanel.add(btnkeypad0);
		//btnkeypad0.addActionListener(new optionsListener('0'));
		
		JButton btnkeypadpound = new JButton("#");
		btnkeypadpound.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("num", keypadEntry, "");//keypad is used to add racers to start.# enters racer command
				keypadEntry = "";
				updateKeypad(keypadEntry);
			}
		});
		btnkeypadpound.setBounds(719, 461, 48, 46);
		MainFramePanel.add(btnkeypadpound);
		//btnkeypadpound.addActionListener(new optionsListener('#'));
		
		JPanel backviewpanel = new JPanel();
		backviewpanel.setBackground(Color.WHITE);
		backviewpanel.setBorder(new LineBorder(new Color(192, 192, 192), 2));
		backviewpanel.setBounds(0, 590, 881, 138);
		MainFramePanel.add(backviewpanel);
		backviewpanel.setLayout(null);
		
		JTextArea txtrChan = new JTextArea();
		txtrChan.setEditable(false);
		txtrChan.setText("CHANNEL");
		txtrChan.setBounds(12, 13, 61, 22);
		backviewpanel.add(txtrChan);
		
		JRadioButton chan1 = new JRadioButton("New radio button");
		chan1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String type = channelType.getSelectedItem().toString();
				sendToHardware("conn", type, "1");
			}
		});
		chan1.setBounds(131, 44, 20, 25);
		backviewpanel.add(chan1);
		//chan1.addActionListener(new toggleListner('1',false));
		
		JRadioButton chan3 = new JRadioButton("New radio button");
		chan3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String type = channelType.getSelectedItem().toString();
				sendToHardware("conn", type, "3");
			}
		});
		chan3.setBounds(176, 44, 20, 25);
		backviewpanel.add(chan3);
	//	chan3.addActionListener(new toggleListner('3',false));
		
		JRadioButton chan5 = new JRadioButton("New radio button");
		chan5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String type = channelType.getSelectedItem().toString();
				sendToHardware("conn", type, "5");
			}
		});
		chan5.setBounds(227, 44, 20, 25);
		backviewpanel.add(chan5);
		//chan5.addActionListener(new toggleListner('5',false));
		
		JRadioButton chan7 = new JRadioButton("New radio button");
		chan7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String type = channelType.getSelectedItem().toString();
				sendToHardware("conn", type, "7");
			}
		});
		chan7.setBounds(279, 44, 20, 25);
		backviewpanel.add(chan7);
		//chan7.addActionListener(new toggleListner('7',false));
		
		JRadioButton chan2 = new JRadioButton("New radio button");
		chan2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String type = channelType.getSelectedItem().toString();
				sendToHardware("conn", type, "2");
			}
		});
		chan2.setBounds(131, 104, 20, 25);
		backviewpanel.add(chan2);
//		chan2.addActionListener(new toggleListner('2',false));
		
		JRadioButton chan4 = new JRadioButton("New radio button");
		chan4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String type = channelType.getSelectedItem().toString();
				sendToHardware("conn", type, "4");
			}
		});
		chan4.setBounds(176, 104, 20, 25);
		backviewpanel.add(chan4);
//		chan4.addActionListener(new toggleListner('4',false));
		
		JRadioButton chan6 = new JRadioButton("New radio button");
		chan6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String type = channelType.getSelectedItem().toString();
				sendToHardware("conn", type, "6");
			}
		});
		chan6.setBounds(227, 104, 20, 25);
		backviewpanel.add(chan6);
//		chan6.addActionListener(new toggleListner('6',false));
		
		JRadioButton chan8 = new JRadioButton("New radio button");
		chan8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String type = channelType.getSelectedItem().toString();
				sendToHardware("conn", type, "8");
			}
		});
		chan8.setBounds(279, 104, 20, 25);
		backviewpanel.add(chan8);
//		chan8.addActionListener(new toggleListner('8',false));
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		textArea_1.setText("3");
		textArea_1.setBounds(176, 13, 12, 22);
		backviewpanel.add(textArea_1);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setEditable(false);
		textArea_2.setText("5");
		textArea_2.setBounds(227, 13, 12, 22);
		backviewpanel.add(textArea_2);
		
		JTextArea textArea_3 = new JTextArea();
		textArea_3.setEditable(false);
		textArea_3.setText("7");
		textArea_3.setBounds(279, 13, 12, 22);
		backviewpanel.add(textArea_3);
		
		JTextArea textArea_4 = new JTextArea();
		textArea_4.setEditable(false);
		textArea_4.setText("1");
		textArea_4.setBounds(131, 13, 12, 22);
		backviewpanel.add(textArea_4);
		
		JTextArea textArea_5 = new JTextArea();
		textArea_5.setEditable(false);
		textArea_5.setText("2");
		textArea_5.setBounds(131, 73, 12, 22);
		backviewpanel.add(textArea_5);
		
		JTextArea textArea_6 = new JTextArea();
		textArea_6.setEditable(false);
		textArea_6.setText("4");
		textArea_6.setBounds(176, 73, 12, 22);
		backviewpanel.add(textArea_6);
		
		JTextArea textArea_7 = new JTextArea();
		textArea_7.setEditable(false);
		textArea_7.setText("6");
		textArea_7.setBounds(227, 73, 12, 22);
		backviewpanel.add(textArea_7);
		
		JTextArea textArea_8 = new JTextArea();
		textArea_8.setEditable(false);
		textArea_8.setText("8");
		textArea_8.setBounds(279, 73, 12, 22);
		backviewpanel.add(textArea_8);
		
		JTextArea txtrUsbPort = new JTextArea();
		txtrUsbPort.setEditable(false);
		txtrUsbPort.setText("USB PORT");
		txtrUsbPort.setBounds(478, 45, 83, 22);
		backviewpanel.add(txtrUsbPort);
		
		JToggleButton usbBooleantoggle = new JToggleButton("");
		usbBooleantoggle.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(usbBooleantoggle.isSelected())
					sendToHardware("export", "", "");
			}
		});
		usbBooleantoggle.setBounds(392, 44, 83, 25);
		backviewpanel.add(usbBooleantoggle);
		
		JComboBox<String> sensortypebox = new JComboBox<String>();//Changed to be compatible with WindowBuilderPro - JASON
		sensortypebox.setModel(new DefaultComboBoxModel<String>(new String[] {"Eye", "Gate", "Pad"}));
		sensortypebox.setBounds(12, 45, 79, 22);
		backviewpanel.add(sensortypebox);
		channelType = sensortypebox;
		
		JTextArea starttxt1 = new JTextArea();
		starttxt1.setEditable(false);
		starttxt1.setText("1");
		starttxt1.setBounds(335, 59, 24, 22);
		MainFramePanel.add(starttxt1);
		
		JTextArea starttxt3 = new JTextArea();
		starttxt3.setEditable(false);
		starttxt3.setText("3");
		starttxt3.setBounds(390, 59, 24, 22);
		MainFramePanel.add(starttxt3);
		
		JTextArea starttxt5 = new JTextArea();
		starttxt5.setEditable(false);
		starttxt5.setText("5");
		starttxt5.setBounds(445, 59, 24, 22);
		MainFramePanel.add(starttxt5);
		
		JTextArea starttxt7 = new JTextArea();
		starttxt7.setEditable(false);
		starttxt7.setText("7");
		starttxt7.setBounds(500, 59, 24, 22);
		MainFramePanel.add(starttxt7);
		
		JTextArea starttxt2 = new JTextArea();
		starttxt2.setEditable(false);
		starttxt2.setText("2");
		starttxt2.setBounds(335, 168, 24, 22);
		MainFramePanel.add(starttxt2);
		
		JTextArea starttxt4 = new JTextArea();
		starttxt4.setEditable(false);
		starttxt4.setText("4");
		starttxt4.setBounds(390, 168, 24, 22);
		MainFramePanel.add(starttxt4);
		
		JTextArea starttxt6 = new JTextArea();
		starttxt6.setEditable(false);
		starttxt6.setText("6");
		starttxt6.setBounds(445, 168, 24, 22);
		MainFramePanel.add(starttxt6);
		
		JTextArea starttxt8 = new JTextArea();
		starttxt8.setEditable(false);
		starttxt8.setText("8");
		starttxt8.setBounds(500, 168, 24, 22);
		MainFramePanel.add(starttxt8);
		
		keypadText = new JTextField();
		keypadText.setBounds(651, 531, 87, 22);
		MainFramePanel.add(keypadText);
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
				break;
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
	public void sendToHardware(String cmd, String arg1, String arg2) {//sends GUI commands to hardwareHandler and updates GUI displays
		String [] updates = hardware.inputFromSimulator(cmd, new String[] {arg1, arg2}, "");
		if(!updates[0].equals("")) {
			updateDisplay(updates[0]);
		}
		if(!updates[1].equals("")) {
			updatePrinter(updates[1]);
		}
	}
	public void updateDisplay(String s) {//Updates display scrollpane - JASON
		displayPane.removeAll();
		displayPane.add(new JTextArea(s));
		displayPane.repaint();
	}
	public void updatePrinter(String s) {//Updates printer tape - JASON
		printerPane.removeAll();
		printerPane.add(new JTextArea(s));
		printerPane.repaint();
	}
	public void updateKeypad(String s) {
		keypadText.setText(s);
	}
}
