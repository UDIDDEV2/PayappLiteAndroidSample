package com.payapplite.payapplitesample.api;

import com.payapplite.payapplitesample.api.data.PlApiRequestPayment;
import com.payapplite.payapplitesample.api.data.PlApiRequestPaymentResult;

/**
 * 페이앱 라이트 API
 */
public interface PayappLiteApi
{
	// String PAYAPP_LITE_URL = "http://192.168.4.1:9002";
	String PAYAPP_LITE_URL = "https://api.payapplite.com";

	/**
	 * 페이앱 라이트에 결제 요청을 시도한다.
	 * @param request 요청 데이터
	 * @return 요청 결과
	 * @exception PayappLiteException 기타 서버 오류 예외
	 */
	PlApiRequestPaymentResult requestPayment (PlApiRequestPayment request) throws PayappLiteException;
}
