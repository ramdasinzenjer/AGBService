package srt.inz.agbservice;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SrtMsgbroadcast extends BroadcastReceiver
{ 
	
	ApplicationPreference appPref;
		String strMessage = "";
		String strMessagen; String res;


		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
		
			 
	        Bundle myBundle = intent.getExtras();
		        SmsMessage [] messages = null;
		        

		        if (myBundle != null)
		        {
		            Object [] pdus = (Object[]) myBundle.get("pdus");
		            messages = new SmsMessage[pdus.length];

		            for (int i = 0; i < messages.length; i++)
		            {
		                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
		                strMessagen = messages[i].getOriginatingAddress();
		                //strMessage += " : ";
		                strMessage += messages[i].getMessageBody();
		                strMessage += "\n";
		            
		                if(strMessage.contains("book"))
		            {
		                
		            	
		            	Toast.makeText(context, "Forwading to booking service."
		            			, Toast.LENGTH_SHORT).show();
		            	
		            	Intent intt=new Intent(context,MainHome.class);
		            	intt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		                intt.putExtra("book","message");
		                
		            	context.startActivity(intt);		            	
		            	
		            }
		            
		            }
		        }
		       
			
		}
		
		public class BookingApiTask extends AsyncTask<String, String, String>
		{

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calobj = Calendar.getInstance();
		    final String sdate=df.format(calobj.getTime());
				String urlParameters=null;
				try {
					urlParameters= "customer_id=" + URLEncoder.encode("inz001", "UTF-8") + "&&"
							+"status=" + URLEncoder.encode("booked", "UTF-8") + "&&"
							+"date=" + URLEncoder.encode(sdate, "UTF-8");
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
				//Toast.makeText(ct, "Gas booking request sent.", Toast.LENGTH_SHORT).show();
			}
   
    }

}