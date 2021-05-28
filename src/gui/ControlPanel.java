package gui;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

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
		JButton button = new JButton("Export image");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.EXPORT_IMAGE));
			}
		});
		add(button);
	}

}
