package com.payapplite.payapplitesample.api.data;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
@Setter
@Getter
@JsonInclude (JsonInclude.Include.NON_NULL)
public class PlApiRequestPaymentResult extends PlApiCommonResult
{
	/**
	 * 결제 URL
	 */
	private String payUrl;
}
