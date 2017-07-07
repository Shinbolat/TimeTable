/*
* To get Data from site - URLS
*
*/

package com.example.shinbolat.tabapp.setting;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shinbolat.tabapp.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Shinbolat on 15.01.2016.
 */
class SitesAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {

    Context context;

    ProgressBar progressBar;
    Spinner spinner;

    ArrayList<String> listOfUrl;
    ArrayList<String> listOfMenu;

    View view;
    String SELECT;
    ArrayAdapter<String> arrayAdapter;

    public SitesAsyncTask(Context context, Spinner spinner, String sel){

        SELECT = sel;
        this.context = context;
        listOfUrl = new ArrayList<String>();
        listOfMenu = new ArrayList<String>();
        this.spinner = spinner;

    }

    protected void onPreExecute(){
        super.onPreExecute();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.setting_layout, null);
        progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

    }


     protected ArrayList<String> doInBackground(String... params) {

         Document doc = null;

         try {
             doc = Jsoup.connect(params[0]).get();
         } catch (IOException e) {
             return null;
             //e.printStackTrace();
         }

         Elements menu = null;

         if(SELECT.equals("MENU") ) {
             menu = doc.getElementsByAttributeValue("title", "universities");
         }
         if(SELECT.equals("FACULTY")){
             menu = doc.getElementsByAttributeValue("title", "faculty");
         }
         if(SELECT.equals("GROUPS")){
             menu = doc.getElementsByAttributeValue("title", "groups");
         }

         listOfUrl.clear();
         listOfMenu.clear();
         if(menu.size()!=0){

             for(int i = 0; i < menu.size(); i ++){

                 Element element = menu.select("a[href]").get(i);

                 // get link to the menu
                 listOfUrl.add(element.attr("href").toString());
                 //Log.i("LIST OF URL",listOfUrl.get(i));

                 // get text of link
                 listOfMenu.add(element.text().toString());
                 //Log.i("LIST OF " + SELECT,listOfMenu.get(i));

             }

         }

         else{
             //Log.i("MENU ", "No menu");
         }

        return listOfUrl;
    }

    protected void onPostExecute(ArrayList listOfUrl){
        super.onPostExecute(listOfUrl);
        if(listOfMenu.size() != 0) {
            arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, listOfMenu);
            //Log.i("SET DROP","DOWN ITEM ");
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            progressBar.setVisibility(View.INVISIBLE);
            spinner.setAdapter(arrayAdapter);
        }
        else Toast.makeText(context,context.getResources().getString(R.string.no_connection),Toast.LENGTH_LONG).show();
    }

}
