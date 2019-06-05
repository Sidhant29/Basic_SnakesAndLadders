package com.example.sidhant.datavault.Fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sidhant.datavault.Activities.AddNoteActivity;
import com.example.sidhant.datavault.Activities.CenterActivity;
import com.example.sidhant.datavault.Activities.LoginActivity;
import com.example.sidhant.datavault.POJOs.Note;
import com.example.sidhant.datavault.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends Fragment {

    FirebaseDatabase fbd = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String email;
    RecyclerView rvNotes;
    public NotesFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView =inflater.inflate(R.layout.fragment_notes2, container, false);
        rvNotes = itemView.findViewById(R.id.rvNotes);
        rvNotes.setLayoutManager(new LinearLayoutManager(getActivity()));

        email = mAuth.getCurrentUser().getEmail();
        DatabaseReference dbf = fbd.getReference();
        //     if(dbf.child(email)==null){
        //       dbf.push().setValue(email);
        // }
        Log.d("email", "onCreate: " + email);
        Query query = dbf.child(email).child("Notes").limitToLast(40);

        FirebaseRecyclerOptions<Note> options  =
                new FirebaseRecyclerOptions.Builder<Note>()
                        .setQuery(query, Note.class)
                        .build();
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Note,NoteHolder>(options){

            @Override
            public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater li = getLayoutInflater();
                View itemView =li.inflate(R.layout.item_notes_firebaseui, parent);
                return new NoteHolder(itemView);
            }

            @Override
            protected void onBindViewHolder(NoteHolder holder, int position, Note model) {
                final String NoteBody = model.getNoteBody();
                holder.tvNote.setText(NoteBody);
                holder.tvNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), AddNoteActivity.class);
                        i.putExtra("Motive","Edit");
                        i.putExtra("NoteBody",NoteBody);
                        startActivity(i);
                    }
                });
            }

        };
        rvNotes.setAdapter(adapter);
        return itemView;

    }

    public class  NoteHolder extends RecyclerView.ViewHolder {
        TextView tvNote;

        public NoteHolder(View itemView) {
            super(itemView);
            tvNote = itemView.findViewById(R.id.tvNote);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
}
