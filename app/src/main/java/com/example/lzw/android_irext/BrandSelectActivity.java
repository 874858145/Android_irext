package com.example.lzw.android_irext;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lzw.android_irext.MyOKHttp;
import com.example.lzw.android_irext.Brand;

import java.util.List;

public class BrandSelectActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    private LinearLayout brandSelectView;
    List<Brand> bs_brandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_select);

        brandSelectView = (LinearLayout) findViewById(R.id.ll_brandSelectView);
        bs_brandList = MyOKHttp.getBrandList();

        showBrandList(MyOKHttp.getBrandFrom());
    }

    public void LastBrandOnClick(View view)
    {
        int tempBrandFrom = MyOKHttp.getBrandFrom();
        if(tempBrandFrom>0)
            MyOKHttp.setBrandFrom(tempBrandFrom-1);

        showBrandList(MyOKHttp.getBrandFrom());
    }

    public void NextBrandOnClick(View view)
    {
        int tempBrandFrom = MyOKHttp.getBrandFrom();
        if(tempBrandFrom<MyOKHttp.getMaxBrandFrom())
            MyOKHttp.setBrandFrom(tempBrandFrom+1);
        showBrandList(MyOKHttp.getBrandFrom());
    }

    private void showBrandList(int brandFrom)
    {
        brandSelectView.removeAllViews();
        if( (MyOKHttp.getBrandListSize()-(brandFrom*10+10))>0 )
        {
            for(int i=0;i<10;i++)
            {
                final View v_brandName = View.inflate(this, R.layout.item_brand_name, null);
                TextView tv_brandName = (TextView)v_brandName.findViewById(R.id.tv_brandName);
                Brand bs_brand = bs_brandList.get(MyOKHttp.getBrandFrom()*10+i);
                tv_brandName.setText(bs_brand.getName());
                tv_brandName.setClickable(true);
                tv_brandName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到品牌选择界面
                        startActivity(new Intent("com.lzw.ElectricalControlActivity"));
                    }
                });
                brandSelectView.addView(v_brandName);
            }
        }
        else
        {
            for(int i=MyOKHttp.getBrandFrom()*10;i<MyOKHttp.getBrandListSize();i++)
            {
                final View v_brandName = View.inflate(this, R.layout.item_brand_name, null);
                TextView tv_brandName = (TextView)v_brandName.findViewById(R.id.tv_brandName);
                Brand bs_brand = bs_brandList.get(i);
                tv_brandName.setText(bs_brand.getName());
                tv_brandName.setClickable(true);
                tv_brandName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到品牌选择界面
                        startActivity(new Intent("com.lzw.ElectricalControlActivity"));
                    }
                });
                brandSelectView.addView(v_brandName);
            }
        }
    }
}
