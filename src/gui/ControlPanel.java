package gui;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.imageio.ImageIO;

import event.*;
import graph.IntegratedGraph;

public class ControlPanel extends JPanel {
	private IntegratedGraph graph;

	public ControlPanel() {
		// TODO Auto-generated constructor stub
	}

	public ControlPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public ControlPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public ControlPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public ControlPanel(IntegratedGraph graph) {
		this.graph = graph;
		JLabel startLabel = new JLabel("Start node:");
		JLabel stopLabel = new JLabel("Stop node:");
		startLabel.setBounds(10, 40, 150, 25);
		stopLabel.setBounds(10, 80, 150, 25);
		add(startLabel);
		add(stopLabel);

		JTextField tf1 = new JTextField();
		JTextField tf2 = new JTextField();
		tf1.setBounds(80, 40, 150, 25);
		add(tf1);
		tf2.setBounds(80, 80, 150, 25);
		add(tf2);
		JButton findButton = new JButton("Find Path");

		try {
			Image start = ImageIO.read(new File("images/find.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
			findButton.setIcon(new ImageIcon(start));
		} catch (Exception e) {
		}

		findButton.setBounds(30, 120, 190, 35);
		add(findButton);

		JLabel selectLabel = new JLabel("Select path:");
		selectLabel.setBounds(10, 170, 100, 25);
		add(selectLabel);
		String paths[] = {};

		JComboBox cb = new JComboBox(paths);
		cb.addItem("Select path");
		cb.setBounds(90, 170, 140, 25);
		add(cb);

		JButton runButton = new JButton("Run");
		JButton resetButton = new JButton("Reset");
		try {
			Image run = ImageIO.read(new File("images/run.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
			runButton.setIcon(new ImageIcon(run));
			Image reset = ImageIO.read(new File("images/reset.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
			resetButton.setIcon(new ImageIcon(reset));
		} catch (Exception e) {
		}

		resetButton.setBounds(20, 210, 100, 35);
		add(resetButton);
		runButton.setBounds(140, 210, 100, 35);
		add(runButton);
		
		JLabel selectNodeLabel = new JLabel("Select node:");
		selectNodeLabel.setBounds(10, 260, 100, 25);
		add(selectNodeLabel);
		String nodes[] = {};
		JComboBox cbNodes = new JComboBox(nodes);
		cbNodes.addItem("Select node");
		cbNodes.setBounds(90, 260, 140, 25);
		add(cbNodes);
		
		JButton nextButton = new JButton("Next");
		JButton backButton = new JButton("Back");
		try {
			Image next = ImageIO.read(new File("images/next.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
			nextButton.setIcon(new ImageIcon(next));
			Image back = ImageIO.read(new File("images/back.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
			backButton.setIcon(new ImageIcon(back));
		} catch (Exception e) {
		}

		backButton.setBounds(20, 300, 100, 35);
		add(backButton);
		nextButton.setBounds(140, 300, 100, 35);
		add(nextButton);

		findButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.FIND_PATH, tf1.getText() + "|" + tf2.getText()));
				int numPath = graph.findAllPath(tf1.getText(), tf2.getText()).size();
				cb.removeAllItems();
				cb.addItem("Select Path");
				for (int i = 0; i < numPath; i++) {
					cb.addItem(i + 1);
				}
			}
		});

		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.NEXT_NODE, cb.getSelectedItem() + ""));
			}
		});
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.PERIOUS_NODE));
			}
		});

	}

}
