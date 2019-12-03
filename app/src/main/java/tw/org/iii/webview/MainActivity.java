package tw.org.iii.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setDisplayZoomControls(true);
        webView.loadUrl("file:///android_asset/jquery_animate.html");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.v("DCH", "BackPredd");
    }
}
