package com.zhaojin.cachingrealm_rxjava_retrofit_2.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhaojin.cachingrealm_rxjava_retrofit_2.R;

public class DetailDialog extends Dialog {

    private ImageView img_original;
    private WebView web_view;
    private int type;
    private Activity ctx;
    private String url;

    public DetailDialog(Activity context, int type, String url) {
        super(context);
        ctx = context;
        this.type = type;
        this.url = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_detail);
        img_original = findViewById(R.id.img_original);
        if(type ==1){
            Glide.with(ctx).load(url).into(img_original);
        }else{
            img_original.setVisibility(View.GONE);
            web_view.setVisibility(View.VISIBLE);
            web_view.loadUrl(url);
        }
    }

}