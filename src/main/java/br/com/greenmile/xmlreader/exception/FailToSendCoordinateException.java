package br.com.greenmile.xmlreader.exception;

public class FailToSendCoordinateException extends Exception {

	
	private static final long serialVersionUID = -2961648517773898128L;
	
	public FailToSendCoordinateException(String msg) {
		super(msg);
	}
	
	public FailToSendCoordinateException() {
		super("Falha ao se comunicar com o backend");
	}

}
