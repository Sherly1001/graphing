package gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

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
	    fileMenu.setBounds(0, 0, 100, 20);

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setActionCommand("Open");

        JMenuItem exportMenuItem = new JMenuItem("Export");
        exportMenuItem.setActionCommand("Export");

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand("Exit");

      	try {
			Image file = ImageIO.read(new File("images\\file.png"))
					.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
			openMenuItem.setIcon(new ImageIcon(file));
			Image export = ImageIO.read(new File("images\\export.png"))
					.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
			exportMenuItem.setIcon(new ImageIcon(export));
			Image exit = ImageIO.read(new File("images\\exit.png"))
					.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
			exitMenuItem.setIcon(new ImageIcon(exit));
		} catch (Exception e) {
		}
      	fileMenu.add(openMenuItem);
      	fileMenu.add(exportMenuItem);
      	fileMenu.addSeparator();
      	fileMenu.add(exitMenuItem);  
      	menuBar.add(fileMenu);
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
		
		JLabel startLabel = new JLabel("Start node:");
		JLabel stopLabel = new JLabel("Stop node:");
		startLabel.setBounds(10, 40, 150, 25);
		stopLabel.setBounds(10, 80, 150, 25);
		panel2.add(startLabel);
		panel2.add(stopLabel);
		
	    JTextField tf1= new JTextField();
	    tf1.setBounds(80, 40, 150, 25);
	    panel2.add(tf1);
	    JTextField tf2 = new JTextField();
	    tf2.setBounds(80, 80, 150, 25);
	    panel2.add(tf2);
	    JButton runButton = new JButton("Run");
	    
		try {
			Image start = ImageIO.read(new File("images\\start.png"))
					.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
			runButton.setIcon(new ImageIcon(start));
		} catch (Exception e) {
		}
		 
		runButton.setBounds(30, 120, 190, 35);
		panel2.add(runButton);
		
		JLabel selectLabel = new JLabel("Select node:");
		selectLabel.setBounds(10, 170, 100, 25);
		panel2.add(selectLabel);
		String nodes[] = {"1","2","3","4","5"};  
		JComboBox cb = new JComboBox(nodes);
		cb.setBounds(90, 170, 140, 25);
		panel2.add(cb);
		    
		JButton nextButton = new JButton("Next");
		JButton backButton = new JButton("Back");
	    
		try {
			Image next = ImageIO.read(new File("images\\next.png"))
					.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
			nextButton.setIcon(new ImageIcon(next));
			Image back = ImageIO.read(new File("images\\back.png"))
					.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
			backButton.setIcon(new ImageIcon(back));
		} catch (Exception e) {
		}
		 
		nextButton.setBounds(20, 220, 100, 35);
		panel2.add(nextButton);
		backButton.setBounds(140, 220, 100, 35);
		panel2.add(backButton);
		
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
