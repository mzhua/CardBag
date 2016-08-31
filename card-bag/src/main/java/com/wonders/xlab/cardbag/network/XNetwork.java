package com.wonders.xlab.cardbag.network;

import com.google.gson.Gson;

import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by hua on 16/8/31.
 */

public class XNetwork {
    private static XNetwork instance = null;

    private OkHttpClient mOkHttpClient;
    private Request.Builder mRequestBuilder;
    private Call mCall;
    private static Gson mGson;

    private Map<String,String> mHeaders;
    private String mUrl;

    private XNetwork() {
    }

    public static XNetwork getInstance() {
        synchronized (XNetwork.class) {
            if (instance == null) {
                instance = new XNetwork();
                mGson = new Gson();
            }
        }
        return instance;
    }

    public XNetwork client(OkHttpClient client) {
        this.mOkHttpClient = client;
        return this;
    }

    public XNetwork url(String url) {
        this.mUrl = url;
        return this;
    }

    public XNetwork addHeader(String name,String value) {
        mHeaders.put(name,value);
        return this;
    }

    public XNetwork removeHeader(String name) {
        mHeaders.remove(name);
        return this;
    }
}
