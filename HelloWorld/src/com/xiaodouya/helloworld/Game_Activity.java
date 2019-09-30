package com.xiaodouya.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Game_Activity extends Activity {

	BallView bv;		//BallView对象引用
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);		//设置不显示标题
        getWindow().setFlags(								//设置为全屏模式
        		WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bv = new BallView(this);	//创建BallView对象
        setContentView(bv);			//将屏幕设置为BallView对象
    }

}
