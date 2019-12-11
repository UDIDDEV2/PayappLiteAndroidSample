package com.payapplite.payapplitesample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.payapplite.payapplitesample.api.PayappLiteApi;
import com.payapplite.payapplitesample.api.PayappLiteApiFactory;
import com.payapplite.payapplitesample.api.PayappLiteException;
import com.payapplite.payapplitesample.api.data.PlApiRequestPayment;
import com.payapplite.payapplitesample.api.data.PlApiRequestPaymentResult;
import com.payapplite.payapplitesample.api.types.PlClientEngine;

import org.springframework.util.StringUtils;

import lombok.SneakyThrows;

public class PaymentRequestActivity extends AppCompatActivity
{
	private PayappLiteApi liteApi;

	private TextView txMemberID;

	private TextView txPrice;

	private TextView txTaxPrice;

	private TextView txTaxFreePrice;

	private TextView txGoodName;

	private TextView txMobile;

	private TextView txCustomName;

	private TextView txCustomNo;

	private TextView txFeedbackurl;

	private TextView txVar1;

	private TextView txVar2;

	private TextView txSendSms;

	private TextView txReturnurl;
	private ProgressBar pBar;

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_payment_request);

		// API 객체를 생성함.
		liteApi = PayappLiteApiFactory.createApi (PlClientEngine.SPRING_REST);

		txMemberID = findViewById (R.id.txMemberID);
		txPrice = findViewById (R.id.txPrice);
		txTaxPrice = findViewById (R.id.txTaxPrice);
		txTaxFreePrice = findViewById (R.id.txTaxFreePrice);
		txGoodName = findViewById (R.id.txGoodName);
		txMobile = findViewById (R.id.txMobile);
		txCustomName = findViewById (R.id.txCustomName);
		txCustomNo = findViewById (R.id.txCustomNo);
		txFeedbackurl = findViewById (R.id.txFeedbackurl);
		txVar1 = findViewById (R.id.txVar1);
		txVar2 = findViewById (R.id.txVar2);
		txSendSms = findViewById (R.id.txSendSms);
		txReturnurl = findViewById (R.id.txReturnurl);

		pBar = findViewById (R.id.pBar);

		txPrice.setText ("1000");
		txGoodName.setText (getString(R.string.testGood));
		txSendSms.setText ("N");
		txReturnurl.setText ("javascript:void(androidObject.complete())");

		// 결제 요청 클릭
		findViewById (R.id.btnRequest).setOnClickListener ((e) -> requestPaymentAction());
	}

	private void requestPaymentAction ()
	{
		if (!StringUtils.hasText (txMemberID.getText ()))
		{
			showErrorToast ("판매자 아이디");
			return;
		}

		if (!StringUtils.hasText (txGoodName.getText ()))
		{
			showErrorToast ("상품명");
			return;
		}

		if (!StringUtils.hasText (txPrice.getText ()) && Integer.parseInt (txPrice.getText ().toString ()) < 1000)
		{
			showErrorToast ("결제금액");
			return;
		}

		if (!StringUtils.hasText (txMobile.getText ()))
		{
			showErrorToast ("핸드폰 번호");
			return;
		}


		PlApiRequestPayment plApiRequestPayment = new PlApiRequestPayment ();

		plApiRequestPayment.setMemberID (txMemberID.getText ().toString ());
		plApiRequestPayment.setPrice (Long.parseLong (txPrice.getText ().toString ()));

		if (StringUtils.hasText (txTaxPrice.getText ()))
			plApiRequestPayment.setTaxPrice (Long.parseLong (txTaxPrice.getText ().toString ()));

		if (StringUtils.hasText (txTaxFreePrice.getText ()))
			plApiRequestPayment.setTaxFreePrice (Long.parseLong (txTaxFreePrice.getText ().toString ()));

		plApiRequestPayment.setGoodName (txGoodName.getText ().toString ());
		plApiRequestPayment.setMobile (txMobile.getText ().toString ());
		plApiRequestPayment.setCustomNo (txCustomNo.getText ().toString ());
		plApiRequestPayment.setCustomName (txCustomName.getText ().toString ());
		plApiRequestPayment.setFeedbackurl (txFeedbackurl.getText ().toString ());
		plApiRequestPayment.setReturnurl (txReturnurl.getText ().toString ());
		plApiRequestPayment.setVar1 (txVar1.getText ().toString ());
		plApiRequestPayment.setVar2 (txVar2.getText ().toString ());
		plApiRequestPayment.setSmsuse (txSendSms.getText ().toString ());

		SendRequestPaymentTask task = new SendRequestPaymentTask (liteApi, (result) -> {
			pBar.setVisibility (View.GONE);
			if (result.isSuccess ())
			{
				Toast.makeText (PaymentRequestActivity.this, R.string.successPaymentRequest, Toast.LENGTH_SHORT).show ();

				Log.d ("PaymentRequestActivity", "PayUrl : " + result.getPayUrl ());

				Intent i = new Intent (this, WebViewActivity.class);

				String payUrl = result.getPayUrl ();

				// 테스트 코드
				payUrl = payUrl.replaceAll ("localhost", "192.168.4.1");

				i.putExtra ("url", payUrl);

				startActivity (i);
			}
			else
				Toast.makeText (PaymentRequestActivity.this, R.string.failPaymentRequest, Toast.LENGTH_SHORT).show ();
		}, (exception) -> {
			pBar.setVisibility (View.GONE);
			Toast.makeText (PaymentRequestActivity.this, R.string.failPaymentRequest, Toast.LENGTH_SHORT).show ();
		});

		pBar.setVisibility (View.VISIBLE);

		task.execute (plApiRequestPayment);
	}

	private void showErrorToast (String errorFld)
	{
		Toast.makeText (this, errorFld + "을(를) 입력해주세요", Toast.LENGTH_SHORT).show ();
	}

	public interface DoneAction
	{
		void done(PlApiRequestPaymentResult result);
	}

	public interface ExceptionAction
	{
		void exception(PayappLiteException ex);
	}

	public class SendRequestPaymentTask extends AsyncTask<PlApiRequestPayment, Void, PlApiRequestPaymentResult>
	{
		private PayappLiteApi liteApi;

		private DoneAction mListener;

		private ExceptionAction mException;

		private PayappLiteException exception;

		public SendRequestPaymentTask (PayappLiteApi liteApi, DoneAction mListener, ExceptionAction mException)
		{
			this.liteApi = liteApi;
			this.mListener = mListener;
			this.mException = mException;
		}

		@Override
		protected PlApiRequestPaymentResult doInBackground (PlApiRequestPayment... params)
		{
			try {
				return liteApi.requestPayment (params[0]);
			} catch (PayappLiteException e) {
				this.exception = e;
				return null;
			}
		}

		@Override
		protected void onPostExecute(PlApiRequestPaymentResult s) {

			if (s == null)
			{
				if (mException != null)
					mException.exception (this.exception);
			}
			else if (mListener != null)
				mListener.done (s);
		}
	}
}
