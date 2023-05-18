package in.co.extra;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import in.co.umcsl.DeshBoard;
import in.co.umcsl.LoginPage;

/**
 * Created by Narendra on 6/8/2017.
 */

public class SessionManager {

    SharedPreferences sharedprefernce;
    SharedPreferences.Editor editor;

    Context context;
    int PRIVATE_MODE=0;
    private static final String PREF_NAME="sharedcheckLogin";
    private static final String User_Id="userid";
    private static final String IS_LOGIN="islogin";
    private static final String userPassword="password";
    private static final String USERNAME="username";
    private static final String USERPHONENUMBER="userPhoneNumber";
    private static final String USEREMAIL="userEmail";


    public SessionManager(Context context){

        this.context =  context;
        sharedprefernce = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor=sharedprefernce.edit();

    }

    public Boolean isLogin(){
        return sharedprefernce.getBoolean(IS_LOGIN, false);

    }
    public void setLogin(){

        editor.putBoolean(IS_LOGIN, true);
        editor.commit();

    }

    public void setUserID(String id ){

     editor.putString(User_Id,id);
     editor.commit();


    }

    public String getUserID(){

        return  sharedprefernce.getString(User_Id,"DEFAULT");
    }

    public void setUserName(String name){
        editor.putString(USERNAME,name);
        editor.commit();

    }
    public String getUserName(){
        return sharedprefernce.getString(USERNAME,"DEFAULT");
    }

    public void setUserPhonenumber(String uphone){
        editor.putString(USERPHONENUMBER,uphone);
        editor.commit();
    }
    public String getUserPhonenumber(){
        return sharedprefernce.getString(USERPHONENUMBER,"DEFAULT");
    }

    public void setUserEmail(String name)
    {
        editor.putString(USEREMAIL,name);
        editor.commit();
    }
    public String getUserEmail(){
        return sharedprefernce.getString(USEREMAIL,"DEFAULT");
    }

    public void setUserPassword(String userPass ){

        editor.putString(userPassword,userPass);
        editor.commit();

    }

    public String getUserPassword() {
        return sharedprefernce.getString(userPassword,"DEFAULT");
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginPage.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);


    }

}


