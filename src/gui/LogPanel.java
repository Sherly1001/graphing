package gui;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import org.graphstream.graph.*;

import event.*;
import graph.IntegratedGraph;

public class LogPanel extends JPanel {
	private IntegratedGraph graph;

	public LogPanel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LogPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public LogPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public LogPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public LogPanel(IntegratedGraph graph) {
		this.graph = graph;
		JTextArea textLog = new JTextArea();
		setLayout(new GridLayout(1, 1));
		add(textLog);
		LogEvent.addLogListener(new LogListener() {
			@Override
			public void run(LogEvent e) {
				int num = 1;
				if (e.cause == LogEvent.Cause.FIND_PATH) {
					String[] inputs = e.message.split("\\|");
					graph.getNode(inputs[0]).setAttribute("ui.class", "marked");
					System.out.println("INFO: From " + inputs[0] + " to " + inputs[1]);
					String text = "";
					try {
						for (List<Edge> path : graph.findAllPath(inputs[0], inputs[1])) {
							text += "Path " + num + ": ";
							for (Edge edge : path) {
								text += edge + " ";
							}
							text += "\n";
							num ++;
						}
						textLog.setText(text);
					} catch (Exception notFoundPath) {
						System.out.println(notFoundPath.getMessage());
					}
				}
			}
		});
	}
}
