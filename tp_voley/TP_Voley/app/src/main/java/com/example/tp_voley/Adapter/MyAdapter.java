package com.example.tp_voley.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tp_voley.R;
import com.example.tp_voley.beans.Etudiant;
import java.util.List;

public class MyAdapter extends ArrayAdapter<Etudiant> {
    private Context context;
    private List<Etudiant> etudiants;

    public MyAdapter(Context context, List<Etudiant> etudiants) {
        super(context, 0, etudiants);
        this.context = context;
        this.etudiants = etudiants;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listt, parent, false);
        }


        Etudiant etudiant = etudiants.get(position);


        TextView nomTextView = convertView.findViewById(R.id.nomTextView);
        TextView prenomTextView = convertView.findViewById(R.id.prenomTextView);
        TextView villeTextView = convertView.findViewById(R.id.villeTextView);
        TextView sexeTextView = convertView.findViewById(R.id.sexeTextView);



        nomTextView.setText(etudiant.getNom());
        prenomTextView.setText(etudiant.getPrenom());
        villeTextView.setText(etudiant.getVille());
        sexeTextView.setText(etudiant.getSexe());
        return convertView;
    }

}
