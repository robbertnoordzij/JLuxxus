package nl.robbertnoordzij.luxxus.events.events;

public class ExceptionEvent extends AbstractEvent {
	private Exception exception;
	
	public ExceptionEvent(Exception exception) {
		this.exception = exception;
	}
	
	public Exception getException() {
		return exception;
	}
}
