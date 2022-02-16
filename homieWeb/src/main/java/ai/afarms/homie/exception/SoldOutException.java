package ai.afarms.homie.exception;

public class SoldOutException extends Exception{
	private final int code;
	
	public SoldOutException(String errorMessage, int code) {
		super(errorMessage);
		this.code = code;
	}
	
	public SoldOutException(String errorMessage) {
		this(errorMessage, 100);
	}
	
	public int getCode() {
		return code;
	}
}
