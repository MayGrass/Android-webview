package tw.org.iii.webview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
    private LocationManager locationManager;
    private MyListener myListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    7777);
        }else{
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
        }else{
            finish();
        }
    }

    private void init() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        myListener = new MyListener();

        editText = findViewById(R.id.edittext);
        webView = findViewById(R.id.webView);
        initWebView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER
                , 0, 0,myListener);
    }

    private class MyListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            Log.v("DCH", lat + "x" + lng);
            webView.loadUrl("javascript: moveTo(" +lat+ "," +lng+ ")");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(myListener);
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
//        webView.loadUrl("file:///android_asset/bootstrap.html");
        webView.loadUrl("file:///android_asset/map.html");
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
