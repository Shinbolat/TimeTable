/*
 * Read data from file
 */
package com.example.shinbolat.tabapp.editor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

//import com.example.shinbolat.tabapp.Alarm;
import com.example.shinbolat.tabapp.findfile.TimeTable;
import com.example.shinbolat.tabapp.utils.Prefs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shinbolat
 */
public class ReadTable {

    private String pathToFile;

    private ArrayList<String> listOfTablesLine;

    final String dayOfWeek[] = new String[]{"MON","TUE","WED","THU","FRI","SAT"};

    Context context;

    public ReadTable(Context context){

        this.context=context;
        pathToFile = Prefs.getPath();

        //Log.i("CONSTRUCTOR READTABLE", "SET PATH");

    }

    public List<TimeTable> getDataFromTable(){

        listOfTablesLine = new ArrayList<String>();

        List<TimeTable> timeTablesList = new ArrayList<TimeTable>();

        File tableFile = new File(Prefs.getPath());

        BufferedReader table_in = null;
        try {
            table_in = new BufferedReader(new FileReader(tableFile));
        } catch (FileNotFoundException e) {
            Toast.makeText(context,"Error reading from file",Toast.LENGTH_LONG).show();
        }
        String line=null;


        try {
            while ((line = table_in.readLine()) != null) {

                String nameOfWeek = new String();
                String numOfSubject = new String();
                String nameOfSubject = new String();
                String numOfRoom = new String();

                int len = line.length();
                int position = 0, count = 0;

                for (int t = 0; t < len; t++) {

                    if (line.charAt(t) == '|') {

                        switch (count) {

                            case 0:
                                nameOfWeek = line.substring(position, t);
                                break;
                            case 1:
                                numOfSubject = line.substring(position, t);
                                break;
                            case 2:
                                nameOfSubject = line.substring(position, t);
                                count++;
                                numOfRoom = line.substring(t + 1);
                                break;
                        }

                        count++;

                        position = t + 1;
                    }

                }
                timeTablesList.add(new TimeTable(nameOfWeek, numOfSubject, nameOfSubject, numOfRoom));
            }
        } catch (IOException e) {
            Toast.makeText(context,"Error reading from file",Toast.LENGTH_LONG).show();
        }

        return timeTablesList;
    }
    //
    public String [][][]getData(){

        Log.i("METHOD GETDATA()", "IS EXECUTING");

        String [][][] textData = new String[6][2][6];

        File tableFile = new File(pathToFile);

        BufferedReader table_in = null;
        try {
            table_in = new BufferedReader(new FileReader(tableFile));
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            Toast.makeText(context,"Error reading from file",Toast.LENGTH_LONG).show();
        }
        String line;
        String temp = "";

        try {
            while ((line = table_in.readLine()) != null) {

                String nameOfWeek = new String();
                String numOfSubject = new String();
                String nameOfSubject = new String();
                String numOfRoom = new String();

                int len = line.length();
                int position = 0, count = 0;

                for (int t = 0; t < len; t++) {

                    if (line.charAt(t) == '|') {

                        switch (count) {

                            case 0:
                                nameOfWeek = line.substring(position, t);
                                break;
                            case 1:
                                numOfSubject = line.substring(position, t);
                                break;
                            case 2:
                                nameOfSubject = line.substring(position, t);
                                count++;
                                numOfRoom = line.substring(t + 1);
                                break;
                        }

                        count++;

                        position = t + 1;
                    }

                }

                for(int i = 0; i < 6; i ++) {
                    if (dayOfWeek[i].equals(nameOfWeek.toUpperCase())) {

                        textData[i][0][Integer.parseInt(numOfSubject)-1] = nameOfSubject;
                        textData[i][1][Integer.parseInt(numOfSubject)-1] = numOfRoom;

                    }
                }

                //timeTablesList.add(new TimeTable(nameOfWeek, numOfSubject, nameOfSubject, numOfRoom));
            }
        } catch (IOException e) {
            Toast.makeText(context,"Error reading from file",Toast.LENGTH_LONG).show();
        }

        return textData;
    }

    public ArrayList<String> getDataForWidget(int dayOfWeeksIndex){

        listOfTablesLine = new ArrayList<String>();

        File tableFile = new File(pathToFile);

        BufferedReader table_in = null;
        try {
            table_in = new BufferedReader(new FileReader(tableFile));
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            Toast.makeText(context,"Error reading from file",Toast.LENGTH_LONG).show();
        }
        String line;

        try {
            while ((line = table_in.readLine()) != null) {

                String nameOfWeek = new String();
                String numOfSubject = new String();
                String nameOfSubject = new String();
                String numOfRoom = new String();

                int len = line.length();
                int position = 0, count = 0;

                for (int t = 0; t < len; t++) {

                    if (line.charAt(t) == '|') {

                        switch (count) {

                            case 0:
                                nameOfWeek = line.substring(position, t);
                                break;
                            case 1:
                                numOfSubject = line.substring(position, t);
                                break;
                            case 2:
                                nameOfSubject = line.substring(position, t);
                                count++;
                                numOfRoom = line.substring(t + 1);
                                break;
                        }

                        count++;

                        position = t + 1;
                    }

                }

                if(dayOfWeek[dayOfWeeksIndex].toUpperCase().equals((String)nameOfWeek.toUpperCase())){

                    listOfTablesLine.add(numOfSubject + " " + nameOfSubject + " " + numOfRoom);

                }

            }
        } catch (IOException e) {
            Toast.makeText(context,"Error reading from file",Toast.LENGTH_LONG).show();
        }


        return listOfTablesLine;
    }

    public String getTimesOfLessons(int DayOfWeek){

        //Log.i(dayOfWeek[DayOfWeek],"");

        listOfTablesLine = new ArrayList<String>();

        File tableFile = new File(pathToFile);

        BufferedReader table_in = null;
        try {
            table_in = new BufferedReader(new FileReader(tableFile));
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            Toast.makeText(context,"Error reading from file",Toast.LENGTH_LONG).show();
        }
        String line;


        try {
            while ((line = table_in.readLine()) != null) {

                String nameOfWeek = new String();
                String numOfSubject = new String();
                String nameOfSubject = new String();
                String numOfRoom = new String();

                int len = line.length();
                int position = 0, count = 0;

                for (int t = 0; t < len; t++) {

                    if (line.charAt(t) == '|') {

                        switch (count) {

                            case 0:
                                nameOfWeek = line.substring(position, t);
                                break;
                            case 1:
                                numOfSubject = line.substring(position, t);
                                break;
                            case 2:
                                nameOfSubject = line.substring(position, t);
                                count++;
                                numOfRoom = line.substring(t + 1);
                                break;
                        }

                        count++;

                        position = t + 1;
                    }

                }

                if(dayOfWeek[DayOfWeek].toUpperCase().equals((String)nameOfWeek.toUpperCase())){

                    //listOfTablesLine.add(numOfSubject + " " + nameOfSubject.substring(0,nameOfSubject.length()>18? 17:nameOfSubject.length()) + " " + numOfRoom.substring(0,numOfRoom.length()>9?8:numOfRoom.length()));

                    return numOfSubject;
                }


            }
        } catch (IOException e) {
            ///e.printStackTrace();
            Toast.makeText(context,"Error reading from file",Toast.LENGTH_LONG).show();
        }

        return null;
    }

    public String getNameOfLesson(int DayOfWeek){

        //Log.i(dayOfWeek[DayOfWeek],"");

        File tableFile = new File(pathToFile);

        BufferedReader table_in = null;
        try {
            table_in = new BufferedReader(new FileReader(tableFile));
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            Toast.makeText(context,"Error reading from file",Toast.LENGTH_LONG).show();
        }
        String line;


        try {
            while ((line = table_in.readLine()) != null) {

                String nameOfWeek = new String();
                String numOfSubject = new String();
                String nameOfSubject = new String();
                String numOfRoom = new String();

                int len = line.length();
                int position = 0, count = 0;

                for (int t = 0; t < len; t++) {

                    if (line.charAt(t) == '|') {

                        switch (count) {

                            case 0:
                                nameOfWeek = line.substring(position, t);
                                break;
                            case 1:
                                numOfSubject = line.substring(position, t);
                                break;
                            case 2:
                                nameOfSubject = line.substring(position, t);
                                count++;
                                numOfRoom = line.substring(t + 1);
                                break;
                        }

                        count++;

                        position = t + 1;
                    }

                }

                if(dayOfWeek[DayOfWeek].toUpperCase().equals((String)nameOfWeek.toUpperCase())){

                    return nameOfSubject;
                }

            }
        } catch (IOException e) {
            //e.printStackTrace();
            Toast.makeText(context,"Error reading from file",Toast.LENGTH_LONG).show();
        }

        return null;
    }

}
