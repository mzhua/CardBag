package com.wonders.xlab.cardbag.ui.cardsearch;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.wonders.xlab.cardbag.base.BaseModel;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.entity.CardSearchEntity;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * TODO
 * 是否需要有我们自己的默认搜索???
 * Created by hua on 16/8/31.
 */

class CardSearchModel extends BaseModel implements CardSearchContract.Model {
    private final String URL_PLACE_HOLDER_CARD_NAME_VALUE = "[card_name_value]";
    private final String HTTP_URL_CARD_SEARCH = "https://api.leancloud.cn/1.1/cloudQuery?cql=select * from cards where card_name regexp \"[card_name_value].*\"";
    private OkHttpClient mOkHttpClient;
    private Request.Builder mBuilder;
    private Call mCall;
    private final Gson mGson;

    CardSearchModel() {
        this.mOkHttpClient = new OkHttpClient();
        mBuilder = new Request.Builder()
                .addHeader("X-LC-Id", "27bHde5iXz1tTpQmbwRcenxg-gzGzoHsz")
                .addHeader("X-LC-Key", "THX77SeFRWJpGzL4QXwlWSCQ")
                .addHeader("Content-Type", "application/json");
        mGson = new Gson();
    }

    @Override
    public void searchByCardName(String cardName, final Callback<List<CardSearchEntity.ResultsEntity>> callback) {
        setupCall(cardName);
        mCall.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFail(new DefaultException(e));
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                final String result = response.body().string();
                final CardSearchEntity cardSearchEntity = mGson.fromJson(result, CardSearchEntity.class);

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (cardSearchEntity == null) {
                                callback.onFail(new DefaultException("搜索失败"));
                            } else {
                                if (cardSearchEntity.getCode() == 200 || cardSearchEntity.getCode() == 0) {
                                    callback.onSuccess(cardSearchEntity.getResults());
                                } else {
                                    callback.onFail(new DefaultException(cardSearchEntity.getError()));
                                }
                            }
                        } catch (JsonSyntaxException e) {
                            callback.onFail(new DefaultException(e));
                        } finally {
                            response.close();
                        }

                    }
                });
            }
        });
    }

    private void setupCall(String cardName) {
        if (mCall != null && !mCall.isCanceled()) {
            mCall.cancel();
        }
        mCall = mOkHttpClient.newCall(mBuilder.url(assembleCardSearchUrl(cardName)).build());
    }

    private String assembleCardSearchUrl(String cardName) {
        return HTTP_URL_CARD_SEARCH.replace(URL_PLACE_HOLDER_CARD_NAME_VALUE, cardName);
    }

}
