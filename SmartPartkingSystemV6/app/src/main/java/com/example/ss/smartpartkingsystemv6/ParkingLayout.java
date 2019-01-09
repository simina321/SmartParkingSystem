package com.example.ss.smartpartkingsystemv6;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class ParkingLayout extends Dialog {

    public final ThreadLocal<Activity> c = new ThreadLocal<Activity>();
    public Dialog d;

    public Button A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7;
    public Button reserve, cancel, btn;
    public static int id = -1;
    static int[] index ={ -1,-1,-1};
    static boolean []ok = {true,true,true};
    public static ArrayList<Integer> unavailableParkingSpots = new ArrayList<>();
    TextView txtJson;

    public ParkingLayout(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c.set(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.parking_layout);
        new Json().execute("https://parkingdb-a7779.firebaseio.com/.json");
        final ArrayList<Button> spots = new ArrayList<>();

        txtJson = (TextView) findViewById(R.id.txtView);
        A1 = (Button) findViewById(R.id.A1);
        A2 = (Button) findViewById(R.id.A2);
        A3 = (Button) findViewById(R.id.A3);
        A4 = (Button) findViewById(R.id.A4);
        A5 = (Button) findViewById(R.id.A5);
        A6 = (Button) findViewById(R.id.A6);
        A7 = (Button) findViewById(R.id.A7);
        B1 = (Button) findViewById(R.id.B1);
        B2 = (Button) findViewById(R.id.B2);
        B3 = (Button) findViewById(R.id.B3);
        B4 = (Button) findViewById(R.id.B4);
        B5 = (Button) findViewById(R.id.B5);
        B6 = (Button) findViewById(R.id.B6);
        B7 = (Button) findViewById(R.id.B7);
        cancel = (Button) findViewById(R.id.cancel);
        reserve = (Button) findViewById(R.id.reserve);

        //add buttons
        spots.add(A1);
        spots.add(A2);
        spots.add(A3);
        spots.add(A4);
        spots.add(A5);
        spots.add(A6);
        spots.add(A7);
        spots.add(B1);
        spots.add(B2);
        spots.add(B3);
        spots.add(B4);
        spots.add(B5);
        spots.add(B6);
        spots.add(B7);



            for (int i=0;i<3;i++)
                if (index[i]!=-1)
                    unavailableParkingSpots.remove(index);

        for (int i : unavailableParkingSpots) {

            for (Button b : spots)
                if (b.getId() == i) {
                    b.setBackgroundColor(Color.rgb(112, 128, 144));//grey
                    b.setClickable(false);
                    reserve.setClickable(false);

                }

        }
        for (Button b : spots)
            b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    for (Button a : spots)
                        if (a.getId() == id & id != -1 & !unavailableParkingSpots.contains(a)) {
                            a.setBackgroundColor(Color.rgb(255, 255, 255));

                        }
                    if (!unavailableParkingSpots.contains(v.getId())) {
                        reserve.setClickable(true);
                        reserve.setBackgroundColor(Color.rgb(0, 130, 117));
                        v.setBackgroundColor(Color.rgb(0, 52, 47));
                        id = v.getId();
                    } else {
                        reserve.setBackgroundColor(Color.rgb(112, 128, 144));
                        reserve.setClickable(false);
                    }

                }
            });

        reserve.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (reserve.isClickable())
                    openDialog(v);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                id = -1;

                //index=-1;
                //    unavailableParkingSpots.remove(index);
                //}
                //txtJson.setText(String.valueOf(index));
                dismiss();//daca nu se da cancel id-ul nu mai devine -1!!!!!!!

            }
        });

    }

    public void openDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Booking Details:");
        builder.setCancelable(true);
        final EditText rn = new EditText(view.getContext());
        final EditText name = new EditText(view.getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        rn.setLayoutParams(lp);
        name.setLayoutParams(lp);

        builder.setPositiveButton(
                "Book",
                new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        unavailableParkingSpots.add(ParkingLayout.id);
                        new CallAPI().execute("https://parkingdb-a7779.firebaseio.com/Parcare1/.json",String.valueOf(ParkingLayout.id-2131165183),"true");

                    }


                });
        builder.setNegativeButton(
                "Cancel",
                new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog a = builder.create();
        a.setView(rn);
        a.setView(name);
        a.show();
    }
    public class CallAPI extends AsyncTask<String, String, String> {

        public CallAPI(){
            //set context variables if required
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0]; // URL to call
            String data1 = params[1]; //data to post
            String data2 = params[1];
            OutputStream out = null;

            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                out = new BufferedOutputStream(urlConnection.getOutputStream());

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(data1);
                writer.flush();
                writer.write(data2);
                writer.close();
                out.close();

                urlConnection.connect();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return null;
        }
    }
    public class sendData extends AsyncTask<String, Void, String> {
        InputStream IS = null;
        String JB = "";
        String json = "";
        private String res;


        @Override
        protected void onPreExecute() {
            json = "";
        }

        @Override
        protected String doInBackground(String... params) {

            PutUtility put = new PutUtility();

            if (params[0] != null & params[1] != null) {
                put.setParam("Id", params[0].toString());
                put.setParam("Status", params[1].toString());
            }
            try {
                res = put.putData("http://localhost:62796/api/PD");
                Log.v("res", res);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.print(res);
            return res;
        }


        @Override
        protected void onPostExecute(String res) {


        }

    }
}