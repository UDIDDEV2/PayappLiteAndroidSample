package com.payapplite.payapplitesample.api.imp;

import com.payapplite.payapplitesample.api.PayappLiteApi;
import com.payapplite.payapplitesample.api.PayappLiteException;
import com.payapplite.payapplitesample.api.data.PlApiRequestPayment;
import com.payapplite.payapplitesample.api.data.PlApiRequestPaymentResult;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * 페이앱 라이트 스프링 Rest Version
 */
public class PlApiSpringRest implements PayappLiteApi
{
	private RestTemplate restTemplate;

	public PlApiSpringRest ()
	{
		restTemplate = new RestTemplate ();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter ());
	}

	@Override
	public PlApiRequestPaymentResult requestPayment (PlApiRequestPayment request) throws PayappLiteException
	{
		HttpEntity<PlApiRequestPayment> requestEntity = new HttpEntity<>(request);

		try {
			return restTemplate.exchange (PayappLiteApi.PAYAPP_LITE_URL.concat ("/oapi/payment/request"),
					HttpMethod.POST, requestEntity, PlApiRequestPaymentResult.class).getBody ();
		}
		catch (RestClientException ex)
		{
			throw new PayappLiteException (ex.getMessage (), PayappLiteException.PayappLiteExceptionType.SERVER_ERROR);
		}
	}
}
