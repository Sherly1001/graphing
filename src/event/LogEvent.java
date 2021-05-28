package event;

import java.util.List;
import java.util.ArrayList;

public class LogEvent {
	public enum Cause {
		INFO, ERROR, LOAD_FILE
	}

	private static final List<LogListener> listeners = new ArrayList<>();
	public Cause cause;
	public String message;

	public LogEvent(Cause cause) {
		this.message = "";
		this.cause = cause;
	}

	public LogEvent(Cause cause, String message) {
		this.cause = cause;
		this.message = message;
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
