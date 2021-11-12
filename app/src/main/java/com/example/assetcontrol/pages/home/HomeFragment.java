package com.example.assetcontrol.pages.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assetcontrol.R;
import com.example.assetcontrol.adapter.RecycleHomeAdapter;
import com.example.assetcontrol.helper.SessionManager;
import com.example.assetcontrol.pages.BaseViewActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView imgSetting, imgProfile;
    private TextView txtGreet, txtUsername, timeGone, sunday, monday, thuesday, wednesday, thursday, friday, saturday;
    Dialog dialog;
    String time = "HH";
    String days = "EEEE";
    SimpleDateFormat sdfTime = new SimpleDateFormat(time);
    SimpleDateFormat sdfDays = new SimpleDateFormat(days);
    CardView close;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    String getNama, daysWeek;

    private static final String TAG = "HomeFragment";
    private ArrayList<String> mDesc = new ArrayList<>();
    private ArrayList<String> mImage = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.view_fragment_home, container, false);

        sessionManager = new SessionManager(getActivity());
        sharedPreferences = this.getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getNama = user.get(SessionManager.EMAIL);

        txtGreet = view.findViewById(R.id.txt_greeting);
        txtUsername = view.findViewById(R.id.txt_username);
        timeGone = view.findViewById(R.id.txt_time);
        imgSetting = view.findViewById(R.id.img_setting);
        imgProfile = view.findViewById(R.id.img_profile);
        sunday = view.findViewById(R.id.txt1);
        monday = view.findViewById(R.id.txt2);
        thuesday = view.findViewById(R.id.txt3);
        wednesday = view.findViewById(R.id.txt4);
        thursday = view.findViewById(R.id.txt5);
        friday = view.findViewById(R.id.txt6);
        saturday = view.findViewById(R.id.txt7);

        System.out.println("Ambil" +getNama);
        txtUsername.setText(getNama);

        dialog = new Dialog(getActivity());
        dialog.setContentView(R.xml.alert_connection);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        close = dialog.findViewById(R.id.close_dialog);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        close = dialog.findViewById(R.id.close_dialog);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //Set Tanggal
        Calendar c1 = Calendar.getInstance();
        String str1 = sdfTime.format(c1.getTime());
        String str2 = sdfDays.format(c1.getTime());
        timeGone.setText(str1);
        daysWeek = str2;
        greeting();
        setDays();
        getImages();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(layoutManager);
        RecycleHomeAdapter adapter = new RecycleHomeAdapter(getActivity(), mDesc, mImage);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void greeting(){
        final String sjam = timeGone.getText().toString().trim();
        //cek jam
        int jam = Integer.parseInt(sjam);
        if(jam >= 10 && jam < 14){
            txtGreet.setText("Selamat Siang");
        } else if(jam >= 15 && jam < 19){
            txtGreet.setText("Selamat Sore");
        } else if(jam >= 19 && jam < 24){
            txtGreet.setText("Selamat Malam");
        } else {
            txtGreet.setText("Selamat Pagi");
        }
    }

    private void setDays(){
        final String days = daysWeek;
//        final String days = "Senin";
        if (days.equals("Minggu")){
            sunday.setTextColor(getResources().getColor(R.color.attribut));
        }
        else if (days.equals("Senin")){
            monday.setTextColor(getResources().getColor(R.color.attribut));
        }
        else if (days.equals("Selasa")){
            thuesday.setTextColor(getResources().getColor(R.color.attribut));
        }
        else if (days.equals("Rabu")){
            wednesday.setTextColor(getResources().getColor(R.color.attribut));
        }
        else if (days.equals("Kamis")){
            thursday.setTextColor(getResources().getColor(R.color.attribut));
        }
        else if (days.equals("Jumat")){
            friday.setTextColor(getResources().getColor(R.color.attribut));
        }
        else if (days.equals("Sabtu")){
            saturday.setTextColor(getResources().getColor(R.color.attribut));
        }
    }

    private void getImages(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
        mImage.add("https://tandaseru.id/wp-content/uploads/2018/11/ikon-823x1024.jpg");
        mDesc.add("Liberty, Amerika");

        mImage.add("https://media-cdn.tripadvisor.com/media/photo-s/0f/c1/75/68/photo0jpg.jpg");
        mDesc.add("Monas, Indonesia");

        mImage.add("https://ik.imagekit.io/tvlk/blog/2020/04/Menara-Eiffel-Wikipedia.jpg?tr=dpr-1,w-675");
        mDesc.add("Eifel, Francis");

        mImage.add("https://indonesiatraveler.id/wp-content/uploads/2020/09/Singapore-Merlion-Park-Photo-by-TravelingYuk-e1600716345262.jpg");
        mDesc.add("Merlion, Singapure");

        mImage.add("https://www.korinatour.co.id/wp-content/uploads/2018/08/australia.jpg");
        mDesc.add("Opera House, Sydney");
    }

}