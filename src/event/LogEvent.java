package event;

import java.util.List;

import org.graphstream.graph.Edge;

import java.util.ArrayList;

public class LogEvent {
	public enum Cause {
		INFO, ERROR, LOAD_FILE, EXPORT_IMAGE, FIND_PATH, OPEN_FILE, SELECT_ROUTE, NEXT_NODE, PERIOUS_NODE, RUN, STOP
	}

	private static final List<LogListener> listeners = new ArrayList<>();
	public Cause cause;
	public String message;
	public List<List<Edge>> paths;

	public LogEvent(Cause cause) {
		this.message = "";
		this.cause = cause;
	}

	public LogEvent(Cause cause, String message) {
		this.cause = cause;
		this.message = message;
	}

	public LogEvent(Cause cause, String message, List<List<Edge>> paths) {
		this.cause = cause;
		this.message = message;
		this.paths = paths;
	}

	public static void emitLogEvent(LogEvent event) {
		for (LogListener listener : listeners) {
			listener.run(event);
		}
	}

	public static void addLogListener(LogListener listener) {
		listeners.add(listener);
	}

	public static void removeLogListener(LogListener listener) {
		listeners.remove(listener);
	}
}
