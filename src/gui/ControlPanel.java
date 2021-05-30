package gui;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import event.LogEvent;
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
		JButton button1 = new JButton("Export image");
		button1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.EXPORT_IMAGE));
			}
		});
		add(button1);

		JTextField fromNode = new JTextField(10);
		JTextField toNode = new JTextField(10);

		this.add(new JLabel("From Node :\n"));
		this.add(fromNode);

		this.add(new JLabel("To Node :\n"));
		this.add(toNode);

		JButton button2 = new JButton("Find path");
		button2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String from = fromNode.getText();
				String to = toNode.getText();
				System.out.println("INFO: From " + from + " to " + to);
				LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.FIND_PATH));
				try {
					graph.findAllPath(from, to);
				} catch (Exception notFoundPath) {
					System.out.println(notFoundPath.getMessage());
				}
			}
		});
		add(button2);
	}

}
