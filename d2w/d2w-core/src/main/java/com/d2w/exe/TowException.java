package com.d2w.exe;

/**
 * Exception while processing.
 * 
 * @author Chengwei.Yan
 * 
 */
public class TowException extends Exception {
	private static final long serialVersionUID = 1L;

	public TowException(String msg) {
		super(msg);
	}

	public TowException(Throwable e) {
		super(e);
	}

}
