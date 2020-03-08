package com.zhaojin.cachingrealm_rxjava_retrofit_2.ui.activity;

import android.os.Bundle;

import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhaojin.cachingrealm_rxjava_retrofit_2.R;
import com.zhaojin.cachingrealm_rxjava_retrofit_2.db.models.ImagesRealm;
import com.zhaojin.cachingrealm_rxjava_retrofit_2.services.core.ApiClient;
import com.zhaojin.cachingrealm_rxjava_retrofit_2.services.response.ImageRes;
import com.zhaojin.cachingrealm_rxjava_retrofit_2.services.response.ImageResponse;
import com.zhaojin.cachingrealm_rxjava_retrofit_2.ui.adapter.ImageAdapter;
import com.zhaojin.cachingrealm_rxjava_retrofit_2.utils.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private XRecyclerView imageList;
    private ImageAdapter imageAdapter;
    private List<ImageResponse> imageRespons;
    private Button btn_search;
    private EditText tv_content;

    private Realm realmUI;

    private int page = 1, pageSize = 10;
    private String x = "contextualwebsearch-websearch-v1.p.rapidapi.com" , y = "8019f3be09msh5db27ffc4ee81e4p1d5856jsn92b2ecee66f6";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
    }

    private void initComponent() {
        realmUI = Realm.getDefaultInstance();
        imageList =  findViewById(R.id.list);
        btn_search = findViewById(R.id.btn_search);
        tv_content = findViewById(R.id.tv_content);
        imageList.setLayoutManager(new LinearLayoutManager(this));
        imageList.setRefreshProgressStyle(ProgressStyle.SysProgress);
        imageList.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        imageList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(() -> {
                    page=1;
                        fetchDataWithoutCaching();
                },500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(() -> {
                    page++;
                    fetchDataWithoutCaching();
                },500);
            }
        });
        imageRespons = new ArrayList<>();
        imageAdapter = new ImageAdapter(imageRespons, this);
        imageList.setAdapter(imageAdapter);
        btn_search.setOnClickListener(v->{
            if(tv_content.getText().toString().length()>2) {
                List<ImageResponse> res = findAllInDb(Realm.getDefaultInstance(),tv_content.getText().toString());
                if(res.size()==0) {
                    imageList.refresh();
                }else{
                    Toast.makeText(MainActivity.this, "From Local db", Toast.LENGTH_SHORT).show();
                    display(res);
                }
            }else{
                Toast.makeText(MainActivity.this, "Length should be more than 2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchDataWithoutCaching() {
        Observable<ImageRes> observable = ApiClient.getService().getImageList(x,y,page, pageSize, tv_content.getText().toString(), true);
        observable
                .delay(1L, java.util.concurrent.TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(va-> writeToDB(Realm.getDefaultInstance(),va.getValue()))
                .observeOn(Schedulers.computation())
                .map(this::readAllFromDB)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::display, this::processError);
    }


    private void processError(Throwable throwable) {
        imageList.refreshComplete();
        imageList.loadMoreComplete();
        Toast.makeText(this, "Check your network connection!!", Toast.LENGTH_SHORT).show();
        Log.e(throwable);
    }

    private void display(List<ImageResponse> imageRespons) {
        this.imageRespons.clear();
        for (int i = 0; i < imageRespons.size(); i++) {
            this.imageRespons.add(imageRespons.get(i));
        }
        imageAdapter.notifyDataSetChanged();
        imageList.refreshComplete();
        imageList.loadMoreComplete();
    }

    private ImagesRealm findInDb(Realm realm, String search) {
        return realm.where(ImagesRealm.class).equalTo("url", search).findFirst();
    }

    private List<ImageResponse> findAllInDb(Realm realm, String search) {
        List<ImagesRealm> imagesRealms = realm.where(ImagesRealm.class).equalTo("search", search).findAll();
        List<ImageResponse> list = new ArrayList<>();
        for (int i = 0; i < imagesRealms.size(); i++) {
            ImageResponse imageResponse = new ImageResponse();
            imageResponse.setUrl(imagesRealms.get(i).getUrl());
            imageResponse.setThumbnail(imagesRealms.get(i).getThumbnail());
            imageResponse.setImageWebSearchUrl(imagesRealms.get(i).getImageWebSearchUrl());
            imageResponse.setName(imagesRealms.get(i).getName());
            imageResponse.setTitle(imagesRealms.get(i).getTitle());
            list.add(imageResponse);
        }
        return list;
    }

    private List<ImageResponse> readAllFromDB(List<ImageResponse> imageRespons) {
        return findAllInDb(Realm.getDefaultInstance(), tv_content.getText().toString());
    }

    private List<ImageResponse> writeToDB(Realm realm, List<ImageResponse> imageRespons) {

        realm.executeTransaction(transactionRealm -> {
            for (int i = 0; i < imageRespons.size(); i++) {
                ImageResponse imageResponse = imageRespons.get(i);
                android.util.Log.e("response_search", imageResponse.getUrl());
                ImagesRealm imagesRealm = findInDb(transactionRealm, imageResponse.getUrl());
                if (imagesRealm == null) {
                    imagesRealm = transactionRealm.createObject(ImagesRealm.class, imageResponse.getUrl());
                }
                imagesRealm.setName(imageResponse.getName());
                imagesRealm.setThumbnail(imageResponse.getThumbnail());
                imagesRealm.setImageWebSearchUrl(imageResponse.getImageWebSearchUrl());
                imagesRealm.setTitle(imageResponse.getTitle());
                imagesRealm.setSearch(tv_content.getText().toString());
            }
        });
        realm.close();
        return imageRespons;

    }

    @Override protected void onDestroy() {
        super.onDestroy();
        realmUI.close();
    }
}
