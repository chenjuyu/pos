package com.xi.pos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xi.pos.model.User;
import com.xi.pos.tool.GsonTools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity  {
	
	public static String Home;
	
	private EditText editText1;
	private 	EditText editText3;
	private  Button btn;
	private static  TextView textView2;
	private static  SharedPreferences sp;  
    public static User u; 
    private static String text;
    private static Handler mHandler;
	String  username;
	 String  password;
	 boolean isLogin;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      	
        Home="http://192.168.1.103:8080/testmybatis/user/ret.do?";//换成自己的地址
        
        editText1=(EditText)findViewById(R.id.editText1);
        editText3=(EditText)findViewById(R.id.editText3);
        textView2=(TextView) findViewById(R.id.textView2);
        btn =(Button)findViewById(R.id.button1); 
        
        //获得实例对象  
        
   	 sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);  
        btn.setOnClickListener(new View.OnClickListener(){
        	@Override
        	   public void onClick(View arg0) {	 
       	          username=editText1.getText().toString();
       	          password=editText3.getText().toString();
       	        
       	        
       	        Log.i("用户名：",username);
       	        Log.i("密码：",password);
       	     new Thread(new Runnable() {//通过线程发送请求
       	    	@Override
       	    	public void run() {
       	    	// TODO Auto-generated method stub
       	    		loginByPost(username,password); //测试帐号123  密码123
       	    	/*  if (loginByPost(username,password).equals(null))
       	       	  {
       	 
       	       	       Toast.makeText(MainActivity.this, "用户不存在或者密码错误", Toast.LENGTH_SHORT).show();  
       	       	        // textview.setText("你点击了Button");   
       	       	       return;
       	               
       	       	   } */
       	    	}
       	         }).start();
       	       
       	 
       	     
       	     
       	  mHandler =new Handler(){
       			public void handleMessage(android.os.Message msg) {
       				//textView2.setText((String)msg.obj);
       			  Toast.makeText(MainActivity.this, (String)msg.obj, Toast.LENGTH_SHORT).show(); 
       			  isLogin=sp.getBoolean("isLogin", false);  
       	       	  
       	       	  Log.i("isLogin:",String.valueOf(isLogin));   
       	       	  
       	       	  if (isLogin){
       	       		  
       	       	 Intent intent= new Intent(); 	  
       	         Bundle bundle=new Bundle();
       	         
       	         System.out.println(String.valueOf(sp.getString("userid", null)));
       	         
       	        // return ;
       	         
       	        bundle.putString("userid", sp.getString("userid", null));
       	       	intent.putExtra("bundle", bundle);//传参
       	     	intent.setClass(MainActivity.this, MenuActivity.class);	
       	      
       	     	startActivity(intent);	  
       	       	  }
       	       	  
       	       	  
       			};
       			};
  
       
       	  
       	  
       	  
        	
        	}
        	

        	
		
        });      
        
        
    }

    public static String loginByPost(String username,String password){    
        try {
            URL url = new URL(Home);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("GET");
            String data = "username="+URLEncoder.encode(username)+"&password="
                    +URLEncoder.encode(password);
            System.out.println(data);
            conn.setRequestProperty("Content=Type", "application/x-wwww-form-urlencoded");
            conn.setRequestProperty("Content-length", data.length()+"");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();//向服务器提交时，这句有问题，异常，待排查
            os.write(data.getBytes());
            int code = conn.getResponseCode();
            System.out.println(code);
            if (code == 200) {
                InputStream is = conn.getInputStream();
                 text =URLDecoder.decode(ReadInputStream(is), "UTF-8");//从网络获取数据
                //Log.i("text:",text);
             
                Message msg=new Message();
               
                msg.obj=text;
                Log.i("text:",text);
              //  text=JSONObject.toJSONString(text);
                Log.i("text:",text);
               // text=String.valueOf(text);
                
                sp.edit().clear().commit(); 
               if (!text.equals("用户不存在")){ 
            	      u= GsonTools.getUser(text, User.class);//解析json数据
                Log.i("数据：", u.toString());
                Log.i("用户ID：", u.getUserid());
                Log.i("用户编码：", u.getNo());
                Log.i("用户名称：", u.getUsername().toString());
                Log.i("用户所嘱部门ID：", u.getDepartmentid());
                if (u!=null){
                Editor editor = sp.edit();
                editor.putString("userid", u.getUserid());
                editor.putString("username", u.getUsername());
                editor.putString("no", u.getNo());
                editor.putString("password", u.getPassword());
                editor.putBoolean("isLogin", true);
                editor.commit();//保存用户名与密码，为自动登录
                } }
                mHandler.sendMessage(msg);
                return text;
            }else {
                return null;
            }
            
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("111111");
        } catch (ProtocolException e) {
            System.out.println("222222");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("33333");
            e.printStackTrace();
        }
        return null;
    }
 //读取输入流
 public static String ReadInputStream(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            is.close();
            baos.close();
            byte[] result = baos.toByteArray();
            return new String(result);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "将输入流转化为字符串失败";
        }
    }
 

    
   
}
