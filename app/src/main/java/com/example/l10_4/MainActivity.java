package com.example.l10_4;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button haku;
    EditText url;
    String osoite = "";
    WebView web;
    String edellinen_sivu;
    String seuraava_sivu;
    int ticket=0;
    int ticket2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        haku = (Button)findViewById(R.id.button);
        url = (EditText)findViewById(R.id.editText);
        web = (WebView)findViewById(R.id.webview);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); // ei näppäimistö nosta sivua
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("http://google.com");


        //enter toimii hakunappina
        url.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ((keyEvent != null ) || (i == EditorInfo.IME_ACTION_DONE)) {

                    osoite = url.getText().toString();
                    web.loadUrl("http://"+osoite);
                    haku.performClick();
                }
                return false;
            }
        });
    }
    public void hae(View v){
        if(ticket == 1) {
            if (osoite.equals("index.html")) {
                edellinen_sivu = "file:///android_asset/index.html";
            }else {
                edellinen_sivu = web.getUrl();
            }
        }else{
            edellinen_sivu = "http://google.com";
        }


        osoite = url.getText().toString();
        web.loadUrl("http://"+osoite);
        url.setText("");
        if (osoite.equals("index.html")) {
            web.loadUrl("file:///android_asset/index.html");
        }
        ticket = 1;
        //sulje näppäimistö
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) { //tarttee try...catch koska kaatuu muuuten jos ei ole näppis auki ja painaa hakunappia
            System.out.println("oopps");
        }
    }
    public void refresh(View v){
        web.reload();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void muuta(View v){
        if (osoite.equals("index.html")) {
            //web.loadUrl("file:///android_asset/index.html");
            web.evaluateJavascript("javascript:initialize()", null);
        }else{
            Toast.makeText(getApplicationContext(),"You have to be on 'index.html' page",Toast.LENGTH_SHORT).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void jskutsu(View v){
        if (osoite.equals("index.html")) {
            //web.loadUrl("file:///android_asset/index.html");
            web.evaluateJavascript("javascript:shoutOut()", null);
        }else{
            Toast.makeText(getApplicationContext(),"You have to be on 'index.html' page",Toast.LENGTH_SHORT).show();
        }
    }
    public void edellinen(View v){
        /*if (web.canGoBack()) {
            web.goBack();
        }*/
        System.out.println(edellinen_sivu);
        if (ticket == 1){
            System.out.println(osoite);
            if (edellinen_sivu.equals("index.html")){
                web.loadUrl("file:///android_asset/index.html");
                seuraava_sivu =  web.getUrl();
            }else {
                seuraava_sivu =  web.getUrl();

                web.loadUrl(edellinen_sivu);

            }
            ticket2 = 1;
            ticket = 0;
        }
        else{
            Toast.makeText(getApplicationContext(),"No last page found",Toast.LENGTH_SHORT).show();
        }

    }
    public void seuraava(View v){
        /*if (web.canGoForward()) {
            web.goForward();
        }*/
        if ((ticket2 == 1)&& (osoite.isEmpty() == false)){
            if (osoite.equals("index.html")) {
                web.loadUrl("file:///android_asset/index.html");
            }else {
                web.loadUrl(seuraava_sivu);
            }
            ticket  = 1;
            ticket2 = 0;
        }else{
            Toast.makeText(getApplicationContext(),"No next page found",Toast.LENGTH_SHORT).show();
        }
    }

}
