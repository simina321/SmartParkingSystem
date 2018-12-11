package com.example.ss.smartparkingv4;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;

public class ParkingLayout extends Dialog {

    public final ThreadLocal<Activity> c = new ThreadLocal<Activity>();
    public Dialog d;
    public Button A1, A2, A3, A4, A5, A6, A7, B1, B2, B3, B4, B5, B6, B7;
    public Button reserve, cancel, btn;
    public  static int id = -1;
    public static ArrayList<Integer> unavailableParkingSpots = new ArrayList<>();

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
        final ArrayList<Button> spots = new ArrayList<>();
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
        for (int i : unavailableParkingSpots) {
            for (Button b : spots)
                if (b.getId() == i) {
                    b.setBackgroundColor(Color.rgb(112, 128, 144));
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
                    if(!unavailableParkingSpots.contains(v.getId())) {
                        reserve.setClickable(true);
                        reserve.setBackgroundColor(Color.rgb(0, 130, 117));
                        v.setBackgroundColor(Color.rgb(0, 52, 47));
                        id = v.getId();
                    }
                    else{
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
                id=-1;
                dismiss();
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
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        unavailableParkingSpots.add(ParkingLayout.id);
                        dialog.cancel();

                    }
                });
        builder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog a = builder.create();
        a.setView(rn);
        a.setView(name);
        a.show();
    }
}


