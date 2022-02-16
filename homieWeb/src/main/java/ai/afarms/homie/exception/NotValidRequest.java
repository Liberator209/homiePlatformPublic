package ai.afarms.homie.exception;

public class NotValidRequest extends Exception{
	private final int code;
	
	public NotValidRequest(String errorMessage, int code) {
		super(errorMessage);
		this.code = code;
	}
	
	public NotValidRequest(String errorMessage) {
		this(errorMessage, 100);
	}
	
	public int getCode() {
		return code;
	}
}
