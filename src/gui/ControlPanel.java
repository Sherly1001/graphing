package gui;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import javax.imageio.ImageIO;

import event.*;
import graph.IntegratedGraph;
import org.graphstream.graph.Edge;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class ControlPanel extends JPanel {
	private IntegratedGraph graph;
	String previousNode;

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
		List<List<Edge>> paths = null;
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
		JButton runButton = new JButton("Find Path");

		try {
			Image start = ImageIO.read(new File("images/start.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
			runButton.setIcon(new ImageIcon(start));
		} catch (Exception e) {
		}

		runButton.setBounds(30, 120, 190, 35);
		add(runButton);

		JLabel selectLabel = new JLabel("Select route:");
		selectLabel.setBounds(10, 170, 100, 25);
		add(selectLabel);
		JComboBox cb = new JComboBox();

		cb.setBounds(90, 170, 140, 25);
		add(cb);

		JButton nextButton = new JButton("Next");
		JButton backButton = new JButton("Back");
		try {
			Image next = ImageIO.read(new File("images/next.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
			nextButton.setIcon(new ImageIcon(next));
			Image back = ImageIO.read(new File("images/back.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
			backButton.setIcon(new ImageIcon(back));
		} catch (Exception e) {
		}

		backButton.setBounds(20, 220, 100, 35);
		add(backButton);
		nextButton.setBounds(140, 220, 100, 35);
		add(nextButton);

		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.NEXT_NODE));
			}
		});

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.PERIOUS_NODE));
			}
		});

		JLabel nodeNext = new JLabel("Select node:");
		nodeNext.setBounds(10, 270, 150, 25);
		add(nodeNext);

		JComboBox cb1 = new JComboBox();

		cb1.setBounds(90, 270, 140, 25);
		add(cb1);

		JButton nextNode = new JButton("Next node");

		try {
			Image next = ImageIO.read(new File("images/next.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
			nextNode.setIcon(new ImageIcon(next));
		} catch (Exception e) {
		}

		nextNode.setBounds(30, 315, 190, 35);
		add(nextNode);

		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				previousNode = tf1.getText();

				LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.INFO, "Finding paths."));
				List<List<Edge>> paths = graph.findAllPath(tf1.getText(), tf2.getText());
				cb.removeAllItems();
				for (int i = 0; i < paths.size(); ++i) {
					cb.addItem("route " + (i + 1));
				}
				cb1.removeAllItems();
				ArrayList<String> nextNode = new ArrayList<String>();
				for (List<Edge> path : paths) {
					if (!nextNode.contains(path.get(0).getNode1().toString()))
						cb1.addItem(path.get(0).getNode1().toString());
					nextNode.add(path.get(0).getNode1().toString());
				}
				LogEvent.emitLogEvent(
						new LogEvent(LogEvent.Cause.FIND_PATH, tf1.getText() + "|" + tf2.getText(), paths));
			}
		});

		cb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.SELECT_ROUTE, e.getItem() + ""));
				}
			}
		});
		nextNode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!previousNode.equals(cb1.getSelectedItem().toString())) {
				graph.getNode(previousNode).getEdgeToward(graph.getNode(cb1.getSelectedItem().toString()))
						.setAttribute("ui.class", "red");
				previousNode = cb1.getSelectedItem().toString();
					graph.getNode(cb1.getSelectedItem().toString()).setAttribute("ui.class", "marked");
				}
				if (!cb1.getSelectedItem().toString().equals(tf2.getText())) {
					List<List<Edge>> paths = graph.findAllPath(cb1.getSelectedItem().toString(), tf2.getText());
					cb1.removeAllItems();
					ArrayList<String> nextNode = new ArrayList<String>();
					for (List<Edge> path : paths) {
						if (!nextNode.contains(path.get(0).getNode1().toString()))
							cb1.addItem(path.get(0).getNode1().toString());
						nextNode.add(path.get(0).getNode1().toString());
					}
				}
			}
		});
	}

}
