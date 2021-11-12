package com.example.assetcontrol.pages.auth;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.assetcontrol.R;
import com.example.assetcontrol.helper.Server;
import com.example.assetcontrol.helper.SessionManager;
import com.example.assetcontrol.pages.BaseViewActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    MaterialEditText etEmail, etPass;
    CheckBox checkBox;
    CardView btLogin, close;
    Dialog dialog;
    private SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    private String LoginAPI = Server.URL_API + "login.php";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.auth_fragment_login, container, false);


        etEmail = view.findViewById(R.id.email_login);
        etPass = view.findViewById(R.id.pass_login);
        btLogin = view.findViewById(R.id.cvLogin);
        checkBox = view.findViewById(R.id.state);

        sessionManager = new SessionManager(getActivity());
        sharedPreferences = this.getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        String loginstatus = sharedPreferences.getString(getResources().getString(R.string.prefLoginState),"");
        if (loginstatus.equals("LoggedIn")){
            startActivity(new Intent(getActivity(), BaseViewActivity.class));
        }

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

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtemail = etEmail.getText().toString().trim();
                String txtpass = etPass.getText().toString().trim();

                if (TextUtils.isEmpty(txtemail) || TextUtils.isEmpty(txtpass)){
                    Toast.makeText(getActivity(), "Data Belum Lengkap!", Toast.LENGTH_SHORT).show();
                }
                else {
                    LoginProses(txtemail, txtpass);
                }
            }
        });
        return view;
    }

    private void LoginProses(final String email, final String password) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        final Handler handler = new Handler();
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Tunggu Sebentar...");
        progressDialog.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, LoginAPI,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("success");
                                    JSONArray jsonArray = jsonObject.getJSONArray("login");
                                    if (success.equals("1")) {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject object = jsonArray.getJSONObject(i);
                                            String id = object.getString("id").trim();
                                            String email = object.getString("email").trim();
                                            String nama = object.getString("nama").trim();
                                            String level = object.getString("level").trim();

                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            if (checkBox.isChecked()){
                                                editor.putString(getResources().getString(R.string.prefLoginState),"LoggedIn");
                                            }
                                            else {
                                                editor.putString(getResources().getString(R.string.prefLoginState),"LoggedOut");
                                            }

                                            if (success.equals("1")) {
                                                sessionManager.createSession(id, email, nama, level);
                                                editor.apply();
                                                final Intent inte = new Intent(getActivity(), BaseViewActivity.class);
                                                inte.putExtra("email", email);
                                                inte.putExtra("nama", nama);
                                                startActivity(inte);
                                            }
                                            else{
                                                editor.putString(getResources().getString(R.string.prefLoginState),"LoggedOut");
                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    progressDialog.dismiss();
                                    dialog.show();
//                                    Toast.makeText(getActivity(), "Error, Email Or Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                dialog.show();
//                                Toast.makeText(getActivity(), "Error, Check Connection" +error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email);
                        params.put("password", password);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                requestQueue.add(stringRequest);
            }
        }, 2000);

    }
}