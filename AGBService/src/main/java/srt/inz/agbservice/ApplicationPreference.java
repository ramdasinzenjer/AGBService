package srt.inz.agbservice;

import android.app.Application;
import android.content.SharedPreferences;


 public class ApplicationPreference extends Application {
    private static SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    
    Boolean LoginStatus,SmsStatus; String Snumber,Customer_id;

    public Boolean getLoginStatus() {
        LoginStatus= appSharedPrefs.getBoolean(Constants.LOGINSTATUS, false);     
        return LoginStatus;
    }

    public void setLoginStatus(Boolean loginStatus) {
        prefsEditor.putBoolean(Constants.LOGINSTATUS,loginStatus);
        prefsEditor.commit();
    }
    
    public void setBookingNumber(String callstatus) {
        prefsEditor.putString(Constants.BOOKINGNUMBER,callstatus);
        prefsEditor.commit();
    }
    public String getBookingNumber() {
    	Snumber= appSharedPrefs.getString(Constants.BOOKINGNUMBER, "");     
        return Snumber;
    }
    
    public void setTelSmsStatus(Boolean smsstatus) {
        prefsEditor.putBoolean(Constants.SMSSERVICE,smsstatus);
        prefsEditor.commit();
    }
    public Boolean getTelSmsStatus() {
    	SmsStatus= appSharedPrefs.getBoolean(Constants.SMSSERVICE, false);     
        return SmsStatus;
    }
    
    public void setCustomerId(String customerid) {
        prefsEditor.putString(Constants.ID,customerid);
        prefsEditor.commit();
    }
    public String getCustomerId() {
    	Customer_id= appSharedPrefs.getString(Constants.ID, "");     
        return Customer_id;
    }

    @SuppressWarnings("static-access")
	@Override
    public void onCreate() {
        super.onCreate();
        this.appSharedPrefs = getApplicationContext().getSharedPreferences(
                Constants.PREFERENCE_PARENT, MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
        
    }

}
