package tw.org.iii.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edittext);
        webView = findViewById(R.id.webView);
        initWebView();
    }

    private void initWebView() {
        WebViewClient webViewClient = new WebViewClient();
        webView.setWebViewClient(webViewClient);
        //網頁相關設定
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); //開啟javascript
        //開啟縮放功能
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        //webSettings.setDisplayZoomControls(true);
        webView.addJavascriptInterface(new MyJS(), "DCH");
        webView.loadUrl("file:///android_asset/bootstrap.html");
//        webView.loadUrl("https://www.iii.org.tw");
    }

    //預設案返回會結束程式，寫條件讓網站可以按返回變成上一頁
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.v("DCH", "keyCode=" +keyCode);
        if (keyCode == 4 && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void lottery(View view) {
//        webView.loadUrl("javascript:test1()");
        webView.loadUrl("javascript:test2("+ editText.getText().toString()+")");
    }

    public class MyJS {
        @JavascriptInterface
        //網頁呈現的都是字串
        public void callFromJS(String yourname) {
            Log.v("DCH", "OK");
            editText.setText(yourname);
        }
    }
}
