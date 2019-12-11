package com.payapplite.payapplitesample.api;

import com.payapplite.payapplitesample.api.imp.PlApiSpringRest;
import com.payapplite.payapplitesample.api.types.PlClientEngine;

public class PayappLiteApiFactory
{
	/**
	 * API 를 생성한다.
	 * @param clientEngine 클라이언트 엔진 타입
	 * @return 페이앱 라이트 API
	 */
	public static PayappLiteApi createApi (PlClientEngine clientEngine)
	{
		if (clientEngine == PlClientEngine.SPRING_REST)
			return new PlApiSpringRest ();

		throw new IllegalArgumentException ();
	}
}
