package com.example.tp_voley;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tp_voley.beans.Etudiant;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;


import org.json.JSONException;
import org.json.JSONObject;

public class AddEtudiant extends AppCompatActivity implements View.OnClickListener {
    private EditText nom;
    private EditText prenom;
    private Spinner ville;
    private RadioButton m;
    private RadioButton f;
    private Button retour;

    private Button add;
    RequestQueue requestQueue;
    private Button backButton;
    String insertUrl = "http://10.0.2.2/projet/ws/createEtudiant.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_etudiant);
        nom = (EditText) findViewById(R.id.nom);
        prenom = (EditText) findViewById(R.id.prenom);
        ville = (Spinner) findViewById(R.id.ville);
        add = (Button) findViewById(R.id.add);
        retour = (Button) findViewById(R.id.retour);
        m = (RadioButton) findViewById(R.id.m);
        f = (RadioButton) findViewById(R.id.f);

        add.setOnClickListener(this);

        // Gestionnaire de clic pour le bouton "retour"
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirigez l'utilisateur vers l'activité "MainActivity"
                Intent intent = new Intent(AddEtudiant.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onClick(View v) {
        Log.d("ok", "ok");


        if (v == add) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest request = new StringRequest(Request.Method.POST,
                    insertUrl, new Response.Listener<String>() {


                @Override

                public void onResponse(String response) {
                    Log.d(TAG, response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.has("error")) {
                            String error = jsonObject.getString("error");
                            Toast.makeText(AddEtudiant.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                        } else {
                            // Parse the student data from the JSON object
                            Etudiant etudiant = new Gson().fromJson(jsonObject.toString(), Etudiant.class);
                            Log.d(TAG, etudiant.toString());
                            Toast.makeText(AddEtudiant.this, "Etudiant added successfully", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error here
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    String sexe = "";
                    if (m.isChecked())
                        sexe = "homme";
                    else
                        sexe = "femme";
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("nom", nom.getText().toString());
                    params.put("prenom", prenom.getText().toString());
                    params.put("ville", ville.getSelectedItem().toString());
                    params.put("sexe", sexe);
                    return params;
                }
            };
            requestQueue.add(request);
        }
    }

}