// to save preferences
package com.example.shinbolat.tabapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.Set;

/**
 * Created by Shinbolat on 12.01.2016.
 */
public class Prefs {

    private static final String FIRST_INSTALLED = "first_installed";
    private static final String path = "path";
    private static final String timeBeforeLesson = "timer";
    private static final String CheckBoxState = "state";
    private static SharedPreferences preferences;

    private static final String FirstLesson="first_lesson";
    private static final String SecondLesson="second_lesson";
    private static final String ThirdLesson="third_lesson";
    private static final String FourthLesson="fourth_lesson";
    private static final String FifthLesson="fifth_lesson";
    private static final String SixLesson="six_lesson";

    private static final String UpdateCheckBoxState="false";
    private static final String Faculty="faculty";
    private static final String Group="224-12";
    private static final String DownloadDate="date";

    public static void init(Context context) {
        preferences = context.getSharedPreferences("prefs", context.MODE_PRIVATE);
    }

    public static void saveInstall(boolean isFirstInstalled) {
        preferences.edit().putBoolean(FIRST_INSTALLED, isFirstInstalled)
                .commit();
    }

    public static boolean getInstalled() {
        return preferences.getBoolean(FIRST_INSTALLED, false);
    }

    public static void setPath(String pathToFile){
        preferences.edit().putString(path, pathToFile).commit();
    }
    public static String getPath(){
        return preferences.getString(path, "");
    }

    public static void setTimeBeforeLesson(int timer){
        preferences.edit().putInt(timeBeforeLesson, timer).commit();
    }

    public static int getTimer(){
        return preferences.getInt(timeBeforeLesson, 5);
    }

    public static void setCheckBoxState(boolean state){
        preferences.edit().putBoolean(CheckBoxState, state).commit();
    }

    public static Boolean getCheckBoxState() {
        return preferences.getBoolean(CheckBoxState, false);
    }

    public static void setFirstLesson(String time){
        preferences.edit().putString(FirstLesson, time).commit();
    }
    public static String getFirstLesson(){
        return preferences.getString(FirstLesson, "8:30");
    }

    public static void setSecondLesson(String time){
        preferences.edit().putString(SecondLesson, time).commit();
    }
    public static String getSecondLesson(){
        return preferences.getString(SecondLesson, "10:00");
    }
    public static void setThirdLesson(String time){
        preferences.edit().putString(ThirdLesson,time).commit();
    }
    public static String getThirdLesson(){
        return preferences.getString(ThirdLesson,"11:30");
    }
    public static void setFourthLesson(String time){
        preferences.edit().putString(FourthLesson,time).commit();
    }
    public static String getFourthLesson(){
        return preferences.getString(FourthLesson,"13:30");
    }
    public static void setFifthLesson(String time){
        preferences.edit().putString(FifthLesson,time).commit();
    }
    public static String getFifthLesson(){
        return preferences.getString(FifthLesson,"15:00");
    }
    public static void setSixLesson(String time){
        preferences.edit().putString(SixLesson, time).commit();
    }
    public static String getSixLesson(){
        return preferences.getString(SixLesson,"16:30");
    }

    public static void setUpdateCheckBoxState(Boolean state){
        preferences.edit().putBoolean(UpdateCheckBoxState, state).commit();
    }
    public static Boolean getUpdateCheckBoxState(){
        return preferences.getBoolean(UpdateCheckBoxState, false);
    }

    public static void setSelectedGroup(String temp){
        preferences.edit().putString(Group, temp).commit();
    }
    public static String getSelectedGroup(){
        return preferences.getString(Group, "");
    }

    public static void setDownloadDate(String temp){
        preferences.edit().putString(DownloadDate,temp).commit();
    }
    public static String getDownloadDate(){
        return preferences.getString(DownloadDate,"1/1/2010");
    }

    public static void setFaculty(String temp){
        preferences.edit().putString(Faculty,temp).commit();
    }
    public static String getFaculty(){
        return preferences.getString(Faculty,"");
    }

}
