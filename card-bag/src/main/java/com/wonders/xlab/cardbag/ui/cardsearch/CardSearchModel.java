package com.wonders.xlab.cardbag.ui.cardsearch;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.wonders.xlab.cardbag.base.BaseModel;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.entity.CardSearchEntity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.wonders.xlab.cardbag.CBag.HTTP_API_CARDS_SEARCH;

/**
 * Created by hua on 16/8/31.
 */

public class CardSearchModel extends BaseModel implements CardSearchContract.Model {

    private OkHttpClient mOkHttpClient;
    private Request.Builder mBuilder;
    private Call mCall;
    private final Gson mGson;

    public CardSearchModel(OkHttpClient mOkHttpClient) {
        this.mOkHttpClient = mOkHttpClient;

        mBuilder = new Request.Builder()
                .addHeader("X-LC-Id", "27bHde5iXz1tTpQmbwRcenxg-gzGzoHsz")
                .addHeader("X-LC-Key", "THX77SeFRWJpGzL4QXwlWSCQ")
                .addHeader("Content-Type", "application/json");
        mGson = new Gson();
    }

    @Override
    public void searchByCardName(String cardName, final Callback<CardSearchEntity> callback) {
        if (mCall != null && !mCall.isCanceled()) {
            mCall.cancel();
        }

        mCall = mOkHttpClient.newCall(mBuilder.url(HTTP_API_CARDS_SEARCH.replace("[card_name_value]", cardName)).build());
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
                                    callback.onSuccess(cardSearchEntity);
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
}
