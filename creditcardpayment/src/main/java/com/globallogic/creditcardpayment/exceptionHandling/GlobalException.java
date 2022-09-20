package com.globallogic.creditcardpayment.exceptionHandling;
import org.springframework.stereotype.Component;

@Component
public class GlobalException extends RuntimeException {
 
	private static final long serialVersionUID=1L;
	private String errorCode;
	private String errorMessage;
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "GlobalException [errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
	}
	public GlobalException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GlobalException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
}