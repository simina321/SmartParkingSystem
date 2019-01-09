package com.example.ss.smartpartkingsystemv6;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.ss.smartpartkingsystemv6.ParkingLayout.index;
import static com.example.ss.smartpartkingsystemv6.ParkingLayout.unavailableParkingSpots;
import static com.example.ss.smartpartkingsystemv6.ParkingLayout.ok;
public class Json extends AsyncTask<String, Void, String> {
    InputStream IS = null;
    String JB = "";
    String json = "";
    private String res;

    public Json() {

    }

    @Override
    protected void onPreExecute() {
        json = "";
    }

    @Override
    protected String doInBackground(String... params) {

        PutUtility put = new PutUtility();

        //if(params[0]!=null & params[1]!=null){
        //put.setParam("Id", params[0].toString());
        //.setParam("Status", params[1].toString());
        //put.putData("https://parkingdb-a7779.firebaseio.com/Parcare1/.json");}
        try {
            res = put.getData("https://parkingdb-a7779.firebaseio.com/.json");
            Log.v("res", res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print(res);
        return res;
    }


    @Override
    protected void onPostExecute(String res) {
        //StringBuilder sb = new StringBuilder();
        boolean found = false;
        int str = 2131165183;

            String [] parts={"","",""} ;
            String part1 = res.substring(0, 34); // 004
            String part2 = res.substring(35, 69);
            String part3 = res.substring(70, res.length());
            parts[0]=part1;
            parts[1]=part2;
            parts[2]=part3;

            for (int j = 0; j < 3; j++) {
                if (parts[j].contains("tr")) {
                    for (char d : parts[j].toCharArray()) {
                        if (Character.isDigit(d)) {

                            if (!unavailableParkingSpots.contains(Integer.parseInt(String.valueOf(d)) + str)) {
                                unavailableParkingSpots.add(Integer.parseInt(String.valueOf(d)) + str);
                                index[j] = unavailableParkingSpots.indexOf(Integer.parseInt(String.valueOf(d)) + str);
                                ok[j] = true;

                            }
                        }
                    }
                }
                        //ok este un flag folosit pt a nu sterge direct din json un alement al listei
                        else {
                    for (char d : parts[j].toCharArray()) {
                        if (Character.isDigit(d)) {
                            if (res.contains("fl")) {
                                ok [j]= false;
                                index[j] = unavailableParkingSpots.indexOf(Integer.parseInt(String.valueOf(d)) + str);
                                //unavailableParkingSpots.remove(index[j]);//?
                            }
                        }
                    }
                }

                }
            }
        }




