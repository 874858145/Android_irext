package com.example.lzw.android_irext;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.example.lzw.android_irext.Brand;

public class MyOKHttp {
    private static String irext_id = "";
    private static String irext_token = "";
    private static boolean irext_login = false;   //是否登陆判断

    private static List<Brand> brandList = new ArrayList<>();
    private static int brandFrom = 0;
    private static boolean getBrandFlag = false;

    public MyOKHttp(){

    }

    //获取irextid
    public static String getIrext_id()
    {
        if(irext_login)
            return irext_id;
        else
            return "";
    }

    //获取irexttoken
    public static String getIrext_token()
    {
        if(irext_login)
            return irext_token;
        else
            return "";
    }

    public static List<Brand> getBrandList()
    {
        if(irext_login) {
            if (getBrandFlag) {
                return brandList;
            }
        }
        return null;
    }

    public static int getBrandListSize()
    {
        return brandList.size();
    }

    public static int getBrandFrom() {
        return brandFrom;
    }

    public static int getMaxBrandFrom() {
        int temp = brandList.size();
        return temp/10;
    }

    public static void setBrandFrom(int brandFrom) {
        if(getBrandFlag)
            MyOKHttp.brandFrom = brandFrom;
        else
            MyOKHttp.brandFrom = 0;
    }

    //https请求电器品牌信息
    public static void httpsBrandList(final Activity activity,String categoryId,String from, String count)
    {
        if(irext_login)
        {
            if(!getBrandFlag)
                irextListBrands(activity,irext_id,irext_token,categoryId,from,count);
        }
    }

    public static void irextLogin(final Activity activity)
    {
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        String jsonStr = "{\"appKey\":\"9d6492e7d756ec8000193789ca141a13\"" +
                         ",\"appSecret\":\"5163018e44e91bd54e36294a20b48df1\"" +
                         ",\"appType\":\"2\"}";//json数据.
        RequestBody requestBody = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder()
                .url("https://irext.net/irext-server/app/app_login")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(activity, "请链接网络，稍后重试", Toast.LENGTH_SHORT).show();
                irext_login = false;
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){//回调的方法执行在子线程。
                    if(response.code()==200) {
                        try {
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append(response.body().string());
                            JSONObject person = new JSONObject(stringBuffer.toString());
                            JSONObject  entityObject = person.getJSONObject("entity");
                            irext_id = entityObject.getString("id");
                            irext_token = entityObject.getString("token");
                            irext_login = true;
                            //Log.d("kwwl", "id==" +);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else
                    {
                        irext_login = false;
                    }
                }
            }
        });
    }

    public static void irextListBrands(final Activity activity, String id, String token, String categoryId, String from, String count)
    {
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        String jsonStr = "{\"id\":\""+id+"\"" +
                         ",\"token\":\""+token+"\"" +
                         ",\"categoryId\":\""+categoryId+"\"" +
                         ",\"from\":\""+from+"\"" +
                         ",\"count\":\""+count+"\"}";//json数据.
        RequestBody requestBody = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder()
                .url("https://irext.net/irext-server/indexing/list_brands")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(activity, "请链接网络，稍后重试", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){//回调的方法执行在子线程。
                    if(response.code()==200) {
                        try {
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append(response.body().string());
                            JSONObject person = new JSONObject(stringBuffer.toString());
                            JSONArray  entityArray = person.getJSONArray("entity");
                            for (int i = 0; i < entityArray.length();i++) {
                                Brand tempBrand = Brand.sectionData(entityArray.getJSONObject(i));
                                if(tempBrand!=null)
                                {
                                    brandList.add(tempBrand);
                                }
                            }
                            getBrandFlag = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
