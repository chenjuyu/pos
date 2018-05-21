package com.xi.pos;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.xi.pos.tool.CommonUtils;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SalesReportActivity extends Activity {
	
	private LineChartView lineChart;
	private EditText editText0;
	private Button button0;
	
	String[] date= {"5-23","5-22","6-22","5-23","5-22","2-22","5-22","4-22","9-22","10-22","11-22","12-22","1-22","6-22","5-23","5-22","2-22","5-22","4-22","9-22","10-22","11-22","12-22","4-22","9-22","10-22","11-22","zxc"};//X��ı�ע
	//int[] score =
 	float[] score =		{74,22,18,79,20,74,20,74,42,90,74,42,200,50,42,90,33,10,74,22,18,79,20,74,22,18,79,20};//ͼ�������
	
	
	//String [] date;
//	int[] score;
	
 	private List<PointValue> mPointValues = new ArrayList<PointValue>();
	private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
	
	 @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	       //setContentView(R.layout.salesreport);
	        setContentView(R.layout.sales);
	       
	       lineChart = (LineChartView)findViewById(R.id.line_chart);
	       
	     //  editText0 = (EditText)findViewById(R.id.content);
	     //  button0 = (Button)findViewById(R.id.sample_button0);
	       
	       
	       
	       httprequest();
	        getAxisXLables();//��ȡx��ı�ע
	        getAxisPoints();//��ȡ�����
	        initLineChart();//��ʼ��
	        
	        
	 }
	 
	 private  void DisplayChar(){
		 	 
		 
		 
		 
	 }
	 
	 
	 
	   private void httprequest(){
		   
		  //	JSONObject  json =new JSONObject();
		//	CommonUtils  tools =new CommonUtils();
			
		   new Thread(new Runnable() {//ͨ���̷߳�������
      	    	@Override
      	    	public void run() {
      	    	
      	    	  String str =CommonUtils.httpRequest("http://192.168.1.103:8080/testmybatis/possales/search.do","GET","date1=2016-04-01&date2=2017-08-13");
      	    	 Log.i("�������ݽ���1��", str);
      	    	  str =StringEscapeUtils.unescapeJava(str); //ȥ��ת���ַ� ����ҳ����������ת���ַ���
      	    	  str=str.substring(1,str.length()-1);  //ȥ��ǰ���" ��
      	    	  Log.i("�������ݽ�����", str);
      	    	JSONArray jsonarray =JSONArray.fromObject(str);
    		String array=""; 
    		String array1="";
      	    if (jsonarray.size()>0) {
    	        for(int i=0;i<jsonarray.size();i++){
    	    	 JSONObject job=	 jsonarray.getJSONObject(i);
    	    	  //System.out.println(json.get("DepartmentID"));
    	    	  //System.out.println(json.get("FactAmountSum"));
    	    	 array=array+String.valueOf(job.get("Date")).substring(5, String.valueOf(job.get("Date")).length());
    	    	 array=array+",";
    	    	 Log.i("array��ֵ ��",array);
    	    	 array1=array1+job.get("FactAmountSum")+",";
    	          } 	
    	        array =  array.substring(0,array.length()-1);
    	        array1 =  array1.substring(0,array1.length()-1);
      	    	
      	     }
      	    
      	    String[] datearray=array.split(",");
      	    date =new String[datearray.length];//���鳤��
      	    if (date.length>=0) {
      	    
      	   for(int i=0;i<datearray.length;i++){
      		  date[i]=datearray[i]; 		   
      		   
      	   } 	
      	    	
      	    	
      	    	
      	    }
      	    
      	   
      	    
      	    
      	    
      	    
      		String[] ary= array1.split(",");
  		    score  =new float[ary.length];
  		    
  		     for (int i=0 ;i<date.length;i++){
  		    	 
  		        Log.i("data��ֵ ��", date[i]);	 
  		     }
  		   
  		   
        		for (int i=0;i<ary.length;i++){   
        			score[i]=Float.valueOf(ary[i]);
        			 Log.i("score��ֵ ��", String.valueOf(score[i]));	 
        		}
        	
        		mPointValues.clear();
    	    	mAxisXValues.clear();
    	    	
        		getAxisXLables();//��ȡx��ı�ע
     	        getAxisPoints();//��ȡ�����
     	        initLineChart();//��ʼ��
      		  
      	  
    			}
      	         }).start();
		   
		
		  
		  /*
		  String array="";
			 String array1="";
			for (int i=0;i<jsonarray.size();i++){
				JSONObject job = jsonarray.getJSONObject(i);
				 array=array+job.get("Date")+",";
				 array1=array1+job.get("FactAmountSum")+",";
			}
			 array =  array.substring(0,array.length()-1);
			 array1 =  array1.substring(0,array.length()-1);
			 // date=new String[]{array}; 
			 // score =new int[]{Integer.valueOf(array1)};   
		   
		   */
		   
	   }
	    
	    /**
	     * ��ʼ��LineChart��һЩ����
	     */
	    private void initLineChart(){
	    	
	        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //���ߵ���ɫ
		    List<Line> lines = new ArrayList<Line>();    
		    line.setShape(ValueShape.CIRCLE);//����ͼ��ÿ�����ݵ����״  ������Բ�� �������� ��ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE��
		    line.setCubic(false);//�����Ƿ�ƽ��
//		    line.setStrokeWidth(3);//�����Ĵ�ϸ��Ĭ����3
			line.setFilled(false);//�Ƿ�������ߵ����
			line.setHasLabels(true);//���ߵ����������Ƿ���ϱ�ע
//			line.setHasLabelsOnlyForSelected(true);//�������������ʾ���ݣ����������line.setHasLabels(true);����Ч��
			line.setHasLines(true);//�Ƿ���ֱ����ʾ�����Ϊfalse ��û������ֻ�е���ʾ	
			line.setHasPoints(true);//�Ƿ���ʾԲ�� ���Ϊfalse ��û��ԭ��ֻ�е���ʾ	
		    lines.add(line);  
		    LineChartData data = new LineChartData();  
		    data.setLines(lines);  
		      
		    //������  
		    Axis axisX = new Axis(); //X��  
		    axisX.setHasTiltedLabels(true);  //X������������������б����ʾ����ֱ�ģ�true��б����ʾ 
//		    axisX.setTextColor(Color.WHITE);  //����������ɫ
		    axisX.setTextColor(Color.parseColor("#D6D6D9"));//��ɫ
		    
//		    axisX.setName("δ�����������");  //�������
		    axisX.setTextSize(11);//���������С
		    axisX.setMaxLabelChars(7); //��༸��X�����꣬��˼�������������X�������ݵĸ���7<=x<=mAxisValues.length
		    axisX.setValues(mAxisXValues);  //���X�����������
		    data.setAxisXBottom(axisX); //x ���ڵײ�     
//		    data.setAxisXTop(axisX);  //x ���ڶ���
		    axisX.setHasLines(true); //x ��ָ���
		    
		    
		    Axis axisY = new Axis();  //Y��  
		    axisY.setName("");//y���ע
		    axisY.setTextSize(11);//���������С
		    data.setAxisYLeft(axisY);  //Y�����������
	      //data.setAxisYRight(axisY);  //y���������ұ�
		  //������Ϊ���ԣ�֧�����š������Լ�ƽ��  
		    lineChart.setInteractive(true); 
		    lineChart.setZoomType(ZoomType.HORIZONTAL);  //�������ͣ�ˮƽ
		    lineChart.setMaxZoom((float) 3);//���ű���
		    lineChart.setLineChartData(data);  
		    lineChart.setVisibility(View.VISIBLE);
		    /**ע�������7��10ֻ�Ǵ���һ������ȥ��ȶ���
		     * ���������Ӻ����࣡��������http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2��;
		     * ���漸���������X�����ݵ���ʾ������x��0-7�����ݣ��������ݵ����С�ڣ�29����ʱ����С������hellochartĬ�ϵ���������ʾ�������ݵ�������ڣ�29����ʱ��
		     * ��������axisX.setMaxLabelChars(int count)��仰,����Զ�����X��������ʾ�ľ������ʵ����ݸ�����
		     * ������axisX.setMaxLabelChars(int count)��仰,
		     * 33�����ݵ���ԣ��� axisX.setMaxLabelChars(10);�����10����v.right= 7; �����7����
		                     �տ�ʼX����ʾ7�����ݣ�Ȼ�����ŵ�ʱ��X��ĸ����ᱣ֤����7С��10
		  	         ��С��v.right= 7;�е�7,�����Ҹо��������䶼����ʧЧ�˵����� - -!
		     * ����Y���Ǹ������ݵĴ�С�Զ�����Y������
		     * ����������� v.right= 7; ��仰����ͼ��տ�ʼ�ͻᾡ���ܵ���ʾ�������ݣ�������̫��
		     */
		    Viewport v = new Viewport(lineChart.getMaximumViewport()); 
			  v.left = 0; 
			  v.right= 7; 
			 
			  Log.i("aa",  String.valueOf(v.height()));
			  lineChart.setCurrentViewport(v);
	    }
	    
	    /**
	     * X �����ʾ
	     */
	    private void getAxisXLables(){
	        for (int i = 0; i < date.length; i++) {    
	        	mAxisXValues.add(new AxisValue(i).setLabel(date[i]));    
	        }    	
	    }
	    /**
	     * ͼ���ÿ�������ʾ
	     */
	    private void getAxisPoints(){
	        for (int i = 0; i < score.length; i++) {    
	        	mPointValues.add(new PointValue(i, score[i]));      
	        }    	
	    }
	
	
	
	
	

}
