package srt.inz.agbservice;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainHome extends Activity{
	
	String res,sdate; ApplicationPreference appPref;
	public ProgressDialog loading;
	
	ListView nlist;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	ListAdapter adapter;
	
	String resdb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_home);
		nlist=(ListView)findViewById(R.id.mlistdet);
		appPref=(ApplicationPreference)getApplication();
			
		 savedInstanceState = getIntent().getExtras();
		String message = savedInstanceState.getString("message");

		
		
			if(message==null)
			{
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calobj = Calendar.getInstance();
		     sdate=df.format(calobj.getTime());
		     Toast.makeText(getApplicationContext(), ""+sdate, Toast.LENGTH_LONG).show();
				
		     Intent phoneIntent = new Intent(Intent.ACTION_CALL); 
    			phoneIntent.setData(Uri.parse("tel:"+"123"));
    		      try{
    		    	  
    		    	  new BookingApiTask().execute();
    		         startActivity(phoneIntent);
    		      }
    		      
    		      catch (android.content.ActivityNotFoundException ex){
    		         Toast.makeText(getApplicationContext(),"Error in call facility",Toast.LENGTH_SHORT).show();
    		      }
			}
			
			new BookingDataApiTask().execute();
	}
	
	public class BookingApiTask extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String urlParameters=null;
			try {
				urlParameters= "customer_id=" + URLEncoder.encode(appPref.getCustomerId(), "UTF-8") + "&&"
						+"status=" + URLEncoder.encode("booked", "UTF-8") + "&&"
						+"booking_date=" + URLEncoder.encode(sdate, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			res = Connectivity.excutePost(Constants.BOOKING_URL,
                    urlParameters);
			Log.e("MyReciever", res);
			return res;
			
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(getApplicationContext(), "Gas booking request sent."+res, Toast.LENGTH_SHORT).show();
		}

}

	
	public class BookingDataApiTask extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String urlParameters=null;
			try {
				urlParameters= "customer_id=" + URLEncoder.encode(appPref.getCustomerId(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			resdb = Connectivity.excutePost(Constants.BOOKINGDATA_URL,
                    urlParameters);
			Log.e("MainHome", resdb);
			return resdb;
			
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			keyparser(resdb);
			Toast.makeText(getApplicationContext(), ""+resdb, Toast.LENGTH_SHORT).show();
		}

}
	

	public void keyparser(String result)
	{
		try
		{
			JSONObject  jObject = new JSONObject(result);
			JSONObject  jObject1 = jObject.getJSONObject("Event");
			JSONArray ja = jObject1.getJSONArray("Details"); 
			int length=ja.length();
			for(int i=0;i<length;i++)
			{
				JSONObject data1= ja.getJSONObject(i);
				String bdate=data1.getString("booking_date");
				String bstat=data1.getString("status");

				
				
				// Adding value HashMap key => value
	            HashMap<String, String> map = new HashMap<String, String>();
	            map.put("booking_date", bdate);
	            map.put("status", bstat);
	           	            
	            map.put("notification", "Requested on date : "+bdate+"."+
	            "/n Status :"+bstat+".");
	        	            
	            //Toast.makeText(getApplicationContext(), ""+sname,Toast.LENGTH_SHORT).show();
	            oslist.add(map);
	            
	            adapter = new SimpleAdapter(getApplicationContext(), oslist,
	                R.layout.layout_single,
	                new String[] {"notification"}, new int[] {R.id.mtext_single});
	            nlist.setAdapter(adapter);
	            
	            nlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	               
				@Override
	               public void onItemClick(AdapterView<?> parent, View view,
	                                            int position, long id) {               
	               Toast.makeText(getApplicationContext(), 
	            		   " "+oslist.get(+position).get("status"), Toast.LENGTH_SHORT).show();	               
	               }
	                });
			}
		}
			catch (Exception e)         
		{
				System.out.println("Error:"+e);
		}
	}
	
	
}
