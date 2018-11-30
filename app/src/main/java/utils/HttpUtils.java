package utils;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    /*
     * Function  :   发送Post请求到服务器
     * Param     :   params请求体内容，encode编码格式
     */
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String MypostJson(String api, Object RequestJsonbean) throws IOException {
        /**
         * 返回的仍然是json格式
         */
        Gson gson = new Gson();
        String json = gson.toJson(RequestJsonbean);
        OkHttpClient client = new OkHttpClient();
        Log.d("debug", api+RequestJsonbean.toString());
        //json body
        MediaType media =MediaType.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.create(media, json);
        Request request = new Request.Builder()
                .url(api)
                .post(body).build();
        Response response = client.newCall(request).execute();
        Log.d("a", response.toString());

        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


}
