/*
* To work with files data
*/
package com.example.shinbolat.tabapp.findfile;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Shinbolat
 */
public class TimeTable {
    
    private String nameOfWeek;
    private String numOfSubject;
    private String nameOfSubject;
    private String numOfRoom;

    public TimeTable(String nameOfWeek, String numOfSubject, String nameOfSubject, String numOfRoom){
        
        this.nameOfWeek = nameOfWeek;
        this.numOfSubject = numOfSubject;
        this.nameOfSubject = nameOfSubject;
        this.numOfRoom = numOfRoom;
                
    }
    
    public String getNameOfWeek(){
        
        return nameOfWeek;
    }
    
    public String getNumOfSubject(){
        
        return numOfSubject;
    }
    
    public String getNameOfSubject(){
        
        return nameOfSubject;
    }
    
    public String getNumOfRoom(){
        
        return numOfRoom;
    }

}
