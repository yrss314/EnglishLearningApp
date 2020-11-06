package com.example.englishbala;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BaiduHelper {//如打开https://api.weibo.com/2/users/show.json?access_token=2.008lXyjH03TV3Zd214aeb568DQGb7C&uid=7092294417可以获得我的接口调用得到的数据
    private static final String TAG = "BaiduHelper";

    public static final String API_KEY = "f5NKQi3sY4me8Yr4vb3VjuwL";

    public static final String SECRET_KEY = "iRDFSRUzgyPiooBLhUZx2RRLtteGRwaE";

    public static final String GRANT_TYPE = "client_credentials";

    public static final String URL_GET_ASSESS_TOKEN = "https://aip.baidubce.com/oauth/2.0/token";

    public static String assessToken;

    //----------------------------------------------------------

    public static final String URL_GET_OCR = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
    //public static final String URL_GET_OCR = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";

    public static void getAssessToken() {
        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", GRANT_TYPE)
                .add("client_id", API_KEY)
                .add("client_secret", SECRET_KEY)
                .build();
        Request request = new Request.Builder()
                .post(formBody)
                .url(URL_GET_ASSESS_TOKEN)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Gson resultGson = new Gson();
                        JsonAccess jsonAccess = resultGson.fromJson(response.body().string(), JsonAccess.class);
                        assessToken = jsonAccess.getAccess_token();
                        Log.d(TAG, assessToken);
                    }
                });
    }



}
