package com.example.shinbolat.tabapp.message;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by shinbolat on 2/6/16.
 */
public class Message {

    Context context;
    String msgText;

    public Message(Context context, String msgText){
        this.context=context;
        this.msgText=msgText;
    }
    public void ShowMessage(){
        Toast.makeText(context,msgText,Toast.LENGTH_LONG).show();
    }
}
