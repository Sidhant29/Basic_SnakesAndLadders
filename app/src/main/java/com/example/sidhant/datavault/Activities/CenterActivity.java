package com.example.sidhant.datavault.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.sidhant.datavault.Fragments.ImageFragment;
import com.example.sidhant.datavault.Fragments.NotesFragment;
import com.example.sidhant.datavault.Fragments.TodoFragment;
import com.example.sidhant.datavault.R;

public class CenterActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    FloatingActionButton btnAdd;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent();

  //      email = i.getStringExtra("Email");
        setContentView(R.layout.activity_center);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(CenterActivity.this,btnAdd);
                popupMenu.inflate(R.menu.menu_add_btn);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.addNote){
                            Intent i = new Intent(CenterActivity.this, AddNoteActivity.class);
                            i.putExtra("Motive","New");
                            startActivity(i);
                        };
                        return true;
                    }
                });
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_center, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString("email",email);
            switch (position) {
                case 0:
    //                Log.d("centeractivity", "getItem: "+ email);
                  //  NotesFragment noteFrag = new NotesFragment();
      //              noteFrag.setArguments(bundle);
                    return new NotesFragment();

                case 1:
                    return  new TodoFragment();

                case 2:
                    return new ImageFragment();
                default:
                    Toast.makeText(CenterActivity.this, "ERROR", Toast.LENGTH_SHORT).show();

            }
            Toast.makeText(CenterActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
