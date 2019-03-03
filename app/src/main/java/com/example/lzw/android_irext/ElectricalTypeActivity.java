package com.example.lzw.android_irext;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.lzw.android_irext.MyUDPUtils;
import com.example.lzw.android_irext.MyGlobalThreadPool;
import com.example.lzw.android_irext.EspIPAddress;

import java.net.InetAddress;
import java.util.List;

public class ElectricalTypeActivity extends AppCompatActivity {
    private MyUDPUtils myUDPUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrical_type);

        myUDPUtils = new MyUDPUtils();
        MyGlobalThreadPool.getGlobalThreadPool().execute(myUDPUtils);
    }

    public void ACButtonOnClick(View view){
        MyOKHttp.httpsBrandList(ElectricalTypeActivity.this,"1","0","500");
        if(MyOKHttp.getBrandList()==null)
            Toast.makeText(getApplicationContext(),"加载中，请稍后",Toast.LENGTH_LONG).show();
        else
            startActivity(new Intent("com.lzw.BrandSelectActivity"));
    }

    public void TVButtonOnClick(View view){
        if(EspIPAddress.isGetAddressFlag())
        {
            String testStr = "hello sir!!";
            List<InetAddress> tempAddressList = EspIPAddress.getmInetAddressList();
            for(int i=0;i<tempAddressList.size();i++)
            {
                myUDPUtils.send(testStr.getBytes(),testStr.length(),tempAddressList.get(i).getHostAddress());
            }
            Toast.makeText(getApplicationContext(),"发送："+testStr,Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(),"未获取到ip地址",Toast.LENGTH_LONG).show();
        }
    }
}
