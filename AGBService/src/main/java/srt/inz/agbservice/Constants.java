package srt.inz.agbservice;


public interface Constants {

    //Progress Message
    String LOGIN_MESSAGE="Logging in...";
    String REGISTER_MESSAGE="Register in...";

    //Urls 10.0.2.2   : 192.168.43.28
   String BASE_URL="https://agasbooking.000webhostapp.com/";
    String LOGIN_URL=BASE_URL+"mLoginUser.php?";  
   	String BOOKING_URL=BASE_URL+"mBooking.php?";
   	String BOOKINGDATA_URL=BASE_URL+"mBookingdata.php?";
    
    //Details

    String ID="id";
    String NAME="Name";
    String PASSWORD="Password";
    String EMAIL="Email";
    String LOGINSTATUS="LoginStatus";
    String SMSSERVICE="SmsService";
    String CALLSERVICE="CallService";
    String BOOKINGNUMBER="BookingNumber";
    

    //SharedPreference

    String PREFERENCE_PARENT="Parent_Pref";

   
}
