package gui;

import java.awt.*;
import java.awt.LayoutManager;
import java.io.IOException;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.graphstream.graph.Edge;

import event.LogEvent;
import event.LogListener;
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
				if (e.cause == LogEvent.Cause.FIND_PATH) {
					String[] inputs = e.message.split("\\|");
					System.out.println("INFO: From " + inputs[0] + " to " + inputs[1]);
					String text = "";
					try {
						for (List<Edge> path : graph.findAllPath(inputs[0], inputs[1])) {
							for (Edge edge : path) {
								text += edge + " ";
							}
							text += "\n";
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
