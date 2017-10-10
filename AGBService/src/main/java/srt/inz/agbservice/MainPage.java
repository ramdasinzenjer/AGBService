package srt.inz.agbservice;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainPage extends Activity {
	
	EditText eid,eps; String sid,sps,result; Button bl;
	
	ApplicationPreference appPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        eid=(EditText)findViewById(R.id.etcid);
        eps=(EditText)findViewById(R.id.etcps);
        bl=(Button)findViewById(R.id.btlog);
        appPref=(ApplicationPreference) getApplication();
        if(appPref.getLoginStatus()==true)
        {
        	Intent i=new Intent(getApplicationContext(),MainHome.class);
        	i.putExtra("message", "novalue");
        	startActivity(i);
        	finish();
        }
        bl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sid=eid.getText().toString();
				sps=eps.getText().toString();
				
				new LoginApiTask().execute();
			}
		});
        
    }
    
    public class LoginApiTask extends AsyncTask<String, String, String>
    
    {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urlParameters=null;
			try {
				urlParameters= "customer_id=" + URLEncoder.encode(sid, "UTF-8") + "&&"
						+"password=" + URLEncoder.encode(sps, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			result = Connectivity.excutePost(Constants.LOGIN_URL,
                    urlParameters);
            Log.e("You are at", "" + result);
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result.contains("success"))
			{
				appPref.setLoginStatus(true);
				appPref.setCustomerId(sid);
				Toast.makeText(getApplicationContext(), "Successfully logged in.", Toast.LENGTH_SHORT).show();
				Intent i=new Intent(getApplicationContext(),MainHome.class);
				i.putExtra("message", "novalue");
				startActivity(i);
			}
			else
			{
				Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
			}
		}
    	
    }
}
