package com.example.locationtracker;

import android.util.Log;

import com.opencsv.CSVReader;

import java.io.FileReader;

public class CSV_reader {
    public static void readDataLineByLine(String file)
    {
        try {

            // Create an object of filereader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(file);

            // create csvReader object passing
            // file reader as a parameter
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    Log.i("asdf", cell);
                }
                //System.out.println();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
