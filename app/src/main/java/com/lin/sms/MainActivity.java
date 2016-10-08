package com.lin.sms;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;


public class MainActivity extends AppCompatActivity {

    private Button mBtnBindPhone;
    private String APPKEY ="172b8d5399133";
    private String APPSECRETE="9e3e8cb50298fe484590ff082e6c2a01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1.初始化sdk
        SMSSDK.initSDK(this,APPKEY,APPSECRETE);
        //2.到清单文件中配置信息 （添加网络相关权限以及一个activity信息）


        mBtnBindPhone= (Button) findViewById(R.id.btn_bind_phone);
         //设置点击事件
        mBtnBindPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //注册手机号
                RegisterPage registerPage=new RegisterPage();

                //注册回调事件
                registerPage.setRegisterCallback(new EventHandler(){
                    @Override
                    //事件完成后
                    public void afterEvent(int event, int result, Object data) {
                        //判断结果是否已经完成
                        if(result==SMSSDK.RESULT_COMPLETE){//解析完成
                            //获取数据data
                            HashMap<String,Object> maps= (HashMap<String, Object>) data;//数据强转
                            //国家
                            String country= (String) maps.get("country");

                            //手机号码
                            String phone= (String) maps.get("phone");

                            submitUserInfo(country,phone);
                        }

                    }
                });

                //显示注册界用下载的inde.xml文档中的show()方法
                registerPage.show(MainActivity.this);

            }
        });

    }

    //新建提交方法 提交用户信息到服务器在监听中返回结果
    public void submitUserInfo(String country,String phone){
        Random r=new Random();//获得一个随机数
        String uid=Math.abs(r.nextInt())+"";
        String nickName="MyApp";
        SMSSDK.submitUserInfo(uid,nickName,null,country,phone);
    }

}
