package gui;

import java.io.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import javax.imageio.ImageIO;

import event.*;
import graph.IntegratedGraph;
import org.graphstream.graph.Edge;

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

		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.INFO, "Finding paths."));
				List<List<Edge>> paths = graph.findAllPath(tf1.getText(), tf2.getText());
				cb.removeAllItems();
				for (int i = 0; i < paths.size(); ++i) {
					cb.addItem("route " + (i + 1));
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

		JButton autoButton = new JButton("Run");

		try {
			Image start = ImageIO.read(new File("images/start.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
			runButton.setIcon(new ImageIcon(start));
		} catch (Exception e) {
		}

		autoButton.setBounds(30, 280, 80, 40);
		add(autoButton);

		autoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.RUN));
			}
		});
		
		JButton stopButton = new JButton("Stop");
		stopButton.setBounds(150, 280, 80, 40);
		add(stopButton);

		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.STOP));
			}
		});
	}

}
