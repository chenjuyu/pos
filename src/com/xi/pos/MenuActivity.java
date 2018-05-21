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
	   //����ͼ������  
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
	
    //����ͼ���·�����������  
    private String[] name = {  
            "O2O�տ�",  
            "������ѯ",  
            "ת��",  
            "�ֻ���ֵ",  
            "���ÿ�����",  
            "ˮ��ú",  
            "Υ�´���",  
            "��ݲ�ѯ",  
            "����"  
    };  



    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.menu);  
  
        GridView gridview = (GridView) findViewById(R.id.gridview);  
        int length = imageRes.length;  
  
        //���ɶ�̬���飬����ת������  
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();  
        for (int i = 0; i < length; i++) {  
            HashMap<String, Object> map = new HashMap<String, Object>();  
            map.put("ItemImage", imageRes[i]);//���ͼ����Դ��ID  
            map.put("ItemText", name[i]);//�������ItemText  
            lstImageItem.add(map);  
        }  
        //������������ImageItem �붯̬�����Ԫ�����Ӧ  
        SimpleAdapter saImageItems = new SimpleAdapter(this,   
                lstImageItem,//������Դ  
                R.layout.item,//item��XMLʵ��  
  
                //��̬������ImageItem��Ӧ������  
                new String[]{"ItemImage", "ItemText"},  
  
                //ImageItem��XML�ļ������һ��ImageView,����TextView ID  
                new int[]{R.id.img_shoukuan, R.id.txt_shoukuan});  
        //��Ӳ�����ʾ  
        gridview.setAdapter(saImageItems);  
        
        
        //��ȡ����
        Intent intent=this.getIntent();
        Bundle b=intent.getBundleExtra("bundle");
        userid=String.valueOf(b.getString("userid"));
        Log.i("�û�ID��", userid);
        
        
        //�����Ϣ����  
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			 Toast.makeText(MenuActivity.this,name[position],Toast.LENGTH_LONG).show();  
			 if (name[position] =="���ÿ�����") {
				 Intent i= new Intent(); 	  
       	         Bundle b1=new Bundle();
       	       // bundle.putString("userid", sp.getString("userid", null));
       	        
       	         b1.putString("userid", userid);
     	       	i.putExtra("bundle", b1);//����
     	     	i.setClass(MenuActivity.this,SalesReportActivity.class);	
     	      
     	     	startActivity(i);	
				
				 
				 
			 } 
			 
		}  
        });  
        
      
        
    }  

}
