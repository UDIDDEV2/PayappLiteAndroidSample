package com.payapplite.payapplitesample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_main);

		findViewById (R.id.btnRequestPayment).setOnClickListener ((e) -> {
			Intent i = new Intent (this, PaymentRequestActivity.class);

			startActivity (i);
		});
	}
}
