package gui;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.graphstream.graph.Edge;

import event.LogEvent;

public class ScheduledTasks extends TimerTask {

	private String name;
	private List<Edge> selectedRoute;
	private int move[];

	public ScheduledTasks(String n, List<Edge> selectedRoute, int move[]) {
		this.name = n;
		this.selectedRoute = selectedRoute;
		this.move = move;
	}

	@Override
	public void run() {
		if (selectedRoute == null || selectedRoute.size() <= 0) {
			LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.ERROR, "no route selected"));
			this.cancel();
		} else {
			if (move[0] < selectedRoute.size()) {
				Edge currentEdge = selectedRoute.get(move[0]);
				currentEdge.setAttribute("ui.class", "red");
				currentEdge.getTargetNode().setAttribute("ui.class", "marked");
				move[0] += 1;
			} else {
				this.cancel();
			}
		}
		if ("Run path".equalsIgnoreCase(name)) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
