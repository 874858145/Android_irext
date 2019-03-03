package com.example.lzw.android_irext;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lzw.android_irext.MyOKHttp;

public class ElectricalAddActivity extends AppCompatActivity{
    private String TAG = this.getClass().getSimpleName();
    private LinearLayout addElectricalNameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrical_add);

        MyOKHttp.irextLogin(ElectricalAddActivity.this);
        addElectricalNameView = (LinearLayout) findViewById(R.id.ll_addElecticalView);

        //默认添加一个Item
        addViewItem(null);
    }

    public void addElectricalonClick(View view){
        addViewItem(view);
    }

    /**
     * Item排序
     */
    private void sortElectricalViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addElectricalNameView.getChildCount()-1; i++) {
            final View childAt = addElectricalNameView.getChildAt(i);
            final TextView tv_ElectricalName = (TextView) childAt.findViewById(R.id.tv_electricalName);
            tv_ElectricalName.setClickable(true);
            tv_ElectricalName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到品牌选择界面
                    startActivity(new Intent("com.lzw.ElectricalTypeActivity"));
                }
            });
            final Button btn_remove = (Button) childAt.findViewById(R.id.btn_addElectrical);
            btn_remove.setText("删除");
            btn_remove.setTag("remove");//设置删除标记
            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //从LinearLayout容器中删除当前点击到的ViewItem
                    addElectricalNameView.removeView(childAt);
                }
            });
        }
    }

    //添加ViewItem
    private void addViewItem(View view) {
        if (addElectricalNameView.getChildCount() == 0) {//如果一个都没有，就添加一个
            View electEvaluateView = View.inflate(this, R.layout.item_electrical_evaluate, null);
            addElectricalNameView.addView(electEvaluateView);
        } else if (((String) view.getTag()).equals("add")) {//如果有一个以上的Item,点击为添加的Item则添加
            View electEvaluateView = View.inflate(this, R.layout.item_electrical_evaluate, null);
            addElectricalNameView.addView(electEvaluateView);
            sortElectricalViewItem();
        }
    }

    public void EspConfigOnClick(View view)
    {
        startActivity(new Intent("com.lzw.EspConnectActivity"));
    }
}
