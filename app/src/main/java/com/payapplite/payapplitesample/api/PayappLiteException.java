package com.payapplite.payapplitesample.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PayappLiteException extends Exception
{
	public enum PayappLiteExceptionType
	{
		SERVER_ERROR,
	}

	private final PayappLiteExceptionType exceptionType;

	public PayappLiteException (String message, PayappLiteExceptionType exceptionType)
	{
		super (message);
		this.exceptionType = exceptionType;
	}
}
