package com.example.shinbolat.tabapp.editor;

import com.example.shinbolat.tabapp.findfile.TimeTable;
import com.example.shinbolat.tabapp.utils.Prefs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by shinbolat on 2/21/16.
 */
public class WriteTable {

    private String table;
    Boolean flag;

    public WriteTable(){

        flag = true;
        table = new String();

    }

    public Boolean Writer(List<TimeTable> temp){

        for (int i = 0; i < temp.size(); i ++){

            table += temp.get(i).getNameOfWeek() + "|" + temp.get(i).getNumOfSubject() + "|" + temp.get(i).getNameOfSubject() + "|" + temp.get(i).getNumOfRoom() +"\n";

        }

        File tableFile = new File(Prefs.getPath().isEmpty()?"":Prefs.getPath());

        BufferedWriter table_in = null;

        try {
            table_in = new BufferedWriter(new FileWriter(tableFile));
        } catch (IOException e) {
            flag = false;
            //e.printStackTrace();
        }

        try {
            table_in.write(table);
        } catch (IOException e) {
            flag = false;
            //e.printStackTrace();
        }

        try {
            table_in.close();
        } catch (IOException e) {
            flag=false;
            //e.printStackTrace();
        }

        return flag;

    }

}
