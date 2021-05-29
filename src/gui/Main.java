package gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import org.graphstream.graph.*;
import org.graphstream.ui.swing_viewer.*;
import org.graphstream.ui.view.Viewer;

import event.LogEvent;
import event.LogListener;
import graph.IntegratedGraph;

public class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	private IntegratedGraph graph = new IntegratedGraph("Graphing", false, true);

	private Container ctn = getContentPane();
	private ControlPanel controlPanel = new ControlPanel(graph);
	private LogPanel logPanel = new LogPanel(graph);

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main();
			}
		});
	}

	public Main() {
		System.setProperty("org.graphstream.ui", "swing");
		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");

		graph.setAttribute("ui.quality");
		graph.setAttribute("ui.antialias");
		graph.setAttribute("ui.stylesheet", "url('file://bin/gui/graph.css')");

		LogEvent.addLogListener(new LogListener() {
			@Override
			public void run(LogEvent e) {
				// TODO Auto-generated method stub
				if (e.cause == LogEvent.Cause.INFO) {
					System.out.println("INFO: " + e.message);
				}
			}
		});

		Viewer viewer = graph.display();
		try {
			graph.loadFromFile("inputs/input.txt");
		} catch (Exception e) {
			// TODO: handle exception
		}
		graph.findAllPath("2", "12");

		for (Node n : graph) {
			n.setAttribute("ui.label", n.getId());
		}
		
		JMenuBar menuBar = new JMenuBar();
	    final JPanel panel1 = new JPanel();
	    final JPanel panel2 = new JPanel();
	    final JPanel panel3 = new JPanel();
	    final JTextArea planetDescription = new JTextArea();
	      
	    JMenu fileMenu = new JMenu("File");
	    JMenu editMenu = new JMenu("Edit"); 
	    JMenu aboutMenu = new JMenu("About");
	    JMenu linkMenu = new JMenu("Links");
	    fileMenu.setBounds(0, 0, 100, 20);
		//tao cac item
        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.setMnemonic(KeyEvent.VK_N);
        newMenuItem.setActionCommand("New");

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setActionCommand("Open");

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setActionCommand("Save");

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand("Exit");

      	JMenuItem cutMenuItem = new JMenuItem("Cut");
      	cutMenuItem.setActionCommand("Cut");

      	JMenuItem copyMenuItem = new JMenuItem("Copy");
      	copyMenuItem.setActionCommand("Copy");
      
      	JMenuItem pasteMenuItem = new JMenuItem("Paste");
      	pasteMenuItem.setActionCommand("Paste");
      	fileMenu.add(newMenuItem);
      	fileMenu.add(openMenuItem);
      	fileMenu.add(saveMenuItem);
      	fileMenu.addSeparator();
      	fileMenu.add(exitMenuItem);  
      	editMenu.add(cutMenuItem);
      	editMenu.add(copyMenuItem);
      	editMenu.add(pasteMenuItem);
      	menuBar.add(fileMenu);
      	menuBar.add(editMenu);
      	menuBar.add(aboutMenu);       
      	menuBar.add(linkMenu);
      	setJMenuBar(menuBar);
      	
      	panel1.setLayout(new GridBagLayout());
      	panel1.setAlignmentX(Component.LEFT_ALIGNMENT);
      	panel1.setPreferredSize(new Dimension(600, 400));
      	panel1.setMaximumSize(new Dimension(1000, 600));
      	panel1.setMinimumSize(new Dimension(500, 300));
      	
      	panel2.setLayout(new GridBagLayout());
      	panel2.setAlignmentX(Component.CENTER_ALIGNMENT);
      	panel2.setPreferredSize(new Dimension(250, 400));
      	panel2.setMaximumSize(new Dimension(400, 600));
      	panel2.setMinimumSize(new Dimension(250, 400));
      	
      	panel3.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		ViewPanel view = (ViewPanel) viewer.getDefaultView();
		view.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "graph"));

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 30;
		gbc.weighty = 70;
		panel1.add(view, gbc);

		panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Controller"));
		panel2.setBackground(Color.white);
	    
		panel2.setLayout(null);
		String nodes[] = {"1","2","3","4","5"};  
	    JComboBox cb1 = new JComboBox(nodes);
	    cb1.setBounds(50, 40, 150, 25);
	    panel2.add(cb1);
	    JComboBox cb2 = new JComboBox(nodes);
	    cb2.setBounds(50, 80, 150, 25);
	    panel2.add(cb2);
	    JButton runButton = new JButton();
	    runButton.setText("Run");
	    try{
	    	Image image = ImageIO.read(new File("C:\\Users\\admin\\git\\graphing\\src\\gui\\images.jpg")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
	    	runButton.setIcon(new ImageIcon(image));
	    } 
	    catch (Exception e) {
	    }		
		runButton.setBounds(40, 120, 80, 35);
		panel2.add(runButton);
		JButton stopButton = new JButton("Stop");
		stopButton.setBounds(150, 120, 80, 35);
		panel2.add(stopButton);
		
		logPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "logs"));

		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.weighty = 30;
		panel3.add(logPanel, gbc);
		
 		JSplitPane innerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel1, panel2);
	    innerPane.setContinuousLayout(true);
	    innerPane.setOneTouchExpandable(true);
	    JSplitPane outerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, innerPane, panel3);
	    outerPane.setContinuousLayout(true);
	    outerPane.setOneTouchExpandable(true);

	    ctn.add(outerPane, BorderLayout.CENTER);
		setVisible(true);
		setSize(900, 640);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
