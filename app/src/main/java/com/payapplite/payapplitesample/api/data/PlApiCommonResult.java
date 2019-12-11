package com.payapplite.payapplitesample.api.data;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude (JsonInclude.Include.NON_NULL)
public class PlApiCommonResult
{
	/**
	 * 성공 여부
	 */
	private boolean success;

	/**
	 * 에러 메시지
	 */
	private String errorMessage;

	/**
	 * VAR1
	 */
	private String var1;

	/**
	 * VAR2
	 */
	private String var2;

}
