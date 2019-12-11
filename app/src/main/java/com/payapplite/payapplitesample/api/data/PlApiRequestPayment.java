package com.payapplite.payapplitesample.api.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PlApiRequestPayment
{
	/**
	 * 회원 아이디
	 */
	private String memberID;

	/**
	 * 결제 금액
	 */
	private Long price;

	/**
	 * 과세 금액
	 */
	private Long taxPrice;

	/**
	 * 면세 금액
	 */
	private Long taxFreePrice;

	/**
	 * 상품명
	 */
	private String goodName;

	/**
	 * 구매자 전화번호
	 */
	private String mobile;

	/**
	 * 결제 출처
	 */
	protected String whereFrom;

	/**
	 * 고객명
	 */
	protected String customName;

	/**
	 * 고객 번호
	 */
	protected String customNo;

	/**
	 * 결제완료 후 결과값을 리턴받을 고객사 URL
	 */
	private String feedbackurl;

	/**
	 * 임의 사용 변수 1
	 */
	private String var1;

	/**
	 * 임의 사용 변수 2
	 */
	private String var2;

	/**
	 * 결제요청 SMS 발송여부 (n: SMS 발송안함)
	 */
	private String smsuse;

	/**
	 * 결제완료 후 이동할 링크 URL (매출전표 페이지에서 "확인"버튼 클릭시 이동)
	 */
	private String returnurl;

	/**
	 * E-mail
	 */
	private String email;

	/**
	 * 이메일 수신여부
	 */
	private String recvemail;

	/**
	 * 판매자 메모
	 */
	private String sellerMemo;
}
