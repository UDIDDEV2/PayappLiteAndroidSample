package com.payapplite.payapplitesample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity
{
	private WebView webView;

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);

		setContentView (R.layout.activity_web_view);

		getSupportActionBar ().hide ();

		webView = findViewById (R.id.webView);

		String initUrl = null;

		if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("url"))
		{
			initUrl = getIntent().getExtras().getString("url");
		}

		webView.setWebChromeClient(new MyChromeClient(this));
		webView.setWebViewClient(new MyWebClient(this));

		WebSettings set = webView.getSettings();

		set.setJavaScriptEnabled(true);
		set.setBuiltInZoomControls(true);
		set.setSupportZoom(true);
		set.setAllowFileAccess(true);
		set.setAllowContentAccess(true);
		set.setDomStorageEnabled(true);

		webView.setHorizontalScrollBarEnabled(true);
		webView.setHorizontalScrollbarOverlay(true);
		webView.setHorizontalFadingEdgeEnabled(true);

		webView.addJavascriptInterface(new JsObject(), "androidObject");

		if (initUrl != null)
			webView.loadUrl (initUrl);
	}

	class JsObject
	{
		@JavascriptInterface
		public void complete()
		{
			WebViewActivity.this.finish ();
		}
	}

	class MyChromeClient extends WebChromeClient
	{
		WebViewActivity ma;

		MyChromeClient (WebViewActivity pa)
		{
			this.ma = pa;
		}

		@Override
		public void onCloseWindow (WebView window)
		{
			super.onCloseWindow (window);
			finish ();
		}

		@Override
		public boolean onJsConfirm (WebView view, String url,
		                            String message, final android.webkit.JsResult result)
		{
			new AlertDialog.Builder (view.getContext ())
					.setMessage (message)
					.setPositiveButton (android.R.string.ok,
							(dialog, which) -> result.confirm ())
					.setNegativeButton (android.R.string.cancel,
							(dialog, which) -> result.cancel ()).setCancelable (false).create ().show ();
			return true;
		}

		@Override
		public boolean onJsAlert (WebView view, String url, String message, final android.webkit.JsResult result)
		{
			new AlertDialog.Builder (view.getContext ())
					.setMessage (message)
					.setPositiveButton (android.R.string.ok,
							(dialog, which) -> result.confirm ())
					.setCancelable (false)
					.create ()
					.show ();

			return true;
		}
	}

	class MyWebClient extends WebViewClient
	{
		WebViewActivity ma;

		MyWebClient(WebViewActivity pa) {
			this.ma = pa;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.startsWith("http:") || url.startsWith("https:")) {
				return false;
			}
			// Otherwise allow the OS to handle it
			else if (url.startsWith("tel:")) {
				Intent tel = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
				startActivity(tel);
				return true;
			} else if (url.startsWith("mailto:")) {
				String body = "Enter your Question, Enquiry or Feedback below:\n\n";
				Intent mail = new Intent(Intent.ACTION_SEND);
				mail.setType("application/octet-stream");
				mail.putExtra(Intent.EXTRA_EMAIL, new String[]{"email address"});
				mail.putExtra(Intent.EXTRA_SUBJECT, "Subject");
				mail.putExtra(Intent.EXTRA_TEXT, body);
				startActivity(mail);
				return true;
			}
			return true;
		}
	}
}
