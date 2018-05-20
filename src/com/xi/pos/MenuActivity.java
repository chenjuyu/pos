package com.xi.pos;

import java.util.ArrayList;
import java.util.HashMap;
import com.xi.pos.model.User;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MenuActivity extends ActionBarActivity {
	
	
	
	
	String userid;
	   //定义图标数组  
    private int[] imageRes = {  
            R.drawable.o2o_16,  
            R.drawable.dingdan_16,  
            R.drawable.zhuanzhang_16,  
            R.drawable.telchongzhi_28,  
            R.drawable.card_30,  
            R.drawable.shuidian_31,  
            R.drawable.weizhang_39,  
            R.drawable.kuaidi_39,  
            R.drawable.jiahao_45
            };  
	
    //定义图标下方的名称数组  
    private String[] name = {  
            "O2O收款",  
            "订单查询",  
            "转账",  
            "手机充值",  
            "信用卡还款",  
            "水电煤",  
            "违章代缴",  
            "快递查询",  
            "更多"  
    };  



    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.menu);  
  
        GridView gridview = (GridView) findViewById(R.id.gridview);  
        int length = imageRes.length;  
  
        //生成动态数组，并且转入数据  
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();  
        for (int i = 0; i < length; i++) {  
            HashMap<String, Object> map = new HashMap<String, Object>();  
            map.put("ItemImage", imageRes[i]);//添加图像资源的ID  
            map.put("ItemText", name[i]);//按序号做ItemText  
            lstImageItem.add(map);  
        }  
        //生成适配器的ImageItem 与动态数组的元素相对应  
        SimpleAdapter saImageItems = new SimpleAdapter(this,   
                lstImageItem,//数据来源  
                R.layout.item,//item的XML实现  
  
                //动态数组与ImageItem对应的子项  
                new String[]{"ItemImage", "ItemText"},  
  
                //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
                new int[]{R.id.img_shoukuan, R.id.txt_shoukuan});  
        //添加并且显示  
        gridview.setAdapter(saImageItems);  
        
        
        //获取参数
        Intent intent=this.getIntent();
        Bundle b=intent.getBundleExtra("bundle");
        userid=String.valueOf(b.getString("userid"));
        Log.i("用户ID：", userid);
        
        
        //添加消息处理  
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			 Toast.makeText(MenuActivity.this,name[position],Toast.LENGTH_LONG).show();  
			 if (name[position] =="信用卡还款") {
				 Intent i= new Intent(); 	  
       	         Bundle b1=new Bundle();
       	       // bundle.putString("userid", sp.getString("userid", null));
       	        
       	         b1.putString("userid", userid);
     	       	i.putExtra("bundle", b1);//传参
     	     	i.setClass(MenuActivity.this,SalesReportActivity.class);	
     	      
     	     	startActivity(i);	
				
				 
				 
			 } 
			 
		}  
        });  
        
      
        
    }  

}
