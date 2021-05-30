package gui;

import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

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
		JTextPane textPane = new JTextPane();
		StyledDocument doc = textPane.getStyledDocument();
		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setBorder(null);
		// scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

		SimpleAttributeSet error = new SimpleAttributeSet();
		StyleConstants.setForeground(error, Color.RED);

		setLayout(new GridLayout(1, 1));
		add(scrollPane);
		LogEvent.addLogListener(new LogListener() {
			@Override
			public void run(LogEvent e) {
				if (e.cause == LogEvent.Cause.INFO) {
					try {
						doc.insertString(doc.getLength(), "INFO: " + e.message + "\n", null);
					} catch (Exception exception) {
						// TODO: handle exception
					}
				} else if (e.cause == LogEvent.Cause.ERROR) {
					try {
						doc.insertString(doc.getLength(), "ERROR: " + e.message + "\n", error);
					} catch (Exception exception) {
						// TODO: handle exception
					}
				}
			}
		});
	}
}
