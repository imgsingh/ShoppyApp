package com.example.akal.shoppyapp;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.File;
import java.text.NumberFormat;

import static android.R.attr.data;
import static android.R.attr.password;

public class CheckOutScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


private EditText mStreet;
private EditText mCity;
private EditText mPostal;
    private TextView mEmail;
    private TextView mUserName;
private Button mSendData;

    private Firebase ref;

    String[] country = {"India", "USA", "China", "Japan", "Other",};


        private final String TAG = CheckOutScreen.class.getSimpleName();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        Boolean isCartEmpty = true;
        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener mAuthListener;
        private FirebaseUser user;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_screen);
        Firebase.setAndroidContext(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                        user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                                // User is signed in
                                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                                myRef = database.getReference("users/" + user.getUid()+"/delivery/");

                                myRef.addValueEventListener(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        mStreet = (EditText) findViewById(R.id.street);
                                        mCity = (EditText) findViewById(R.id.city);
                                        mPostal = (EditText) findViewById(R.id.postal);
                                        mSendData = (Button) findViewById(R.id.senddata);
                                        mEmail = (TextView) findViewById(R.id.userrr1);
                                        mUserName = (TextView) findViewById(R.id.userrr);

                                        final AutoCompleteTextView textView1 = (AutoCompleteTextView) findViewById(R.id.state);
                                        String[] state = getResources().getStringArray(R.array.india_states);
                                        final ArrayAdapter<String> adapter =
                                                new ArrayAdapter<String>(CheckOutScreen.this, android.R.layout.simple_list_item_1, state);
                                        textView1.setAdapter(adapter);

                                        final AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.country);
                                        String[] countries = getResources().getStringArray(R.array.countries_array);
                                        final ArrayAdapter<String> adapter1 =
                                                new ArrayAdapter<String>(CheckOutScreen.this, android.R.layout.simple_list_item_1, countries);
                                        textView.setAdapter(adapter1);


                                        mSendData.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                String fetchData = user.getEmail();
                                                mEmail.setText(fetchData);
                                                String fetchData1 = user.getDisplayName();
                                                mUserName.setText(fetchData1);
                                                String username = mUserName.getText().toString().trim();
                                                String email = mEmail.getText().toString().trim();
                                                String street = mStreet.getText().toString().trim();
                                                String city = mCity.getText().toString().trim();
                                                String state = textView1.getText().toString().trim();
                                                String postal = mPostal.getText().toString().trim();
                                                String country = textView.getText().toString().trim();

                                                DeliveryData dData = new DeliveryData(username, email, street, city, state, postal, country);


                                                myRef.setValue(dData);


                                                Snackbar.make(
                                                        findViewById(R.id.senddata),
                                                        "Delivery Address Added ",
                                                        Snackbar.LENGTH_SHORT).show();

                                                startActivity(new Intent(getApplicationContext(), MainAppPage.class));


                                            }
                                    });
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(CheckOutScreen.this, "Data Not Saved", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        } else {
                                Log.d(TAG, "onAuthStateChanged:signed_out");
                        }
                }
        };

    //drawer items
    PrimaryDrawerItem shop = new PrimaryDrawerItem().withIdentifier(0).withName("Shop").withIcon(R.mipmap.ic_launcher);
    final PrimaryDrawerItem about_us = new PrimaryDrawerItem().withIdentifier(1).withName("About Us");
    PrimaryDrawerItem faq_page = new PrimaryDrawerItem().withIdentifier(2).withName("FAQ's");
    PrimaryDrawerItem chat_bot  = new PrimaryDrawerItem().withIdentifier(3).withName("Chatbot");
    PrimaryDrawerItem share  = new PrimaryDrawerItem().withIdentifier(4).withName("Share App");
    PrimaryDrawerItem feedback  = new PrimaryDrawerItem().withIdentifier(5).withName("Feedback");
    PrimaryDrawerItem delivery  = new PrimaryDrawerItem().withIdentifier(6).withName("Delivery Details");


    final Drawer drawer = new DrawerBuilder().
            withActivity(this).
            addDrawerItems(shop, about_us, faq_page, chat_bot, share, feedback, delivery).
            withDrawerWidthDp(250).
            withActionBarDrawerToggle(true).
            build();


    drawer.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
        @Override
        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            if (drawerItem.getIdentifier() == 0) {
                Intent intent = new Intent(CheckOutScreen.this, MainAppPage.class);
                startActivity(intent);
                drawer.closeDrawer();
            } else if (drawerItem.getIdentifier() == 1) {
                Intent intent = new Intent(CheckOutScreen.this, AboutUs.class);
                startActivity(intent);
            } else if (drawerItem.getIdentifier() == 2) {
                Intent intent = new Intent(CheckOutScreen.this, FaqPage.class);
                startActivity(intent);
                drawer.closeDrawer();
            }
            else if (drawerItem.getIdentifier() == 3) {
                Intent intent = new Intent(CheckOutScreen.this, ChatBot.class);
                startActivity(intent);
                drawer.closeDrawer();
            }
            else if (drawerItem.getIdentifier() == 4) {
                ApplicationInfo applicationInfo = getApplicationContext().getApplicationInfo();
                String apkPath = applicationInfo.sourceDir;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("application/vnd.android.package-archieve");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkPath)));
                startActivity(Intent.createChooser(intent, "Share App Using"));
            }else if (drawerItem.getIdentifier() == 5) {
                Intent intent = new Intent(CheckOutScreen.this, Feedback.class);
                startActivity(intent);
                drawer.closeDrawer();
            }else if (drawerItem.getIdentifier() == 6) {
                Intent intent = new Intent(CheckOutScreen.this, CheckOutScreen.class);
                startActivity(intent);
                drawer.closeDrawer();
            }
            return true;
        }
    });
        }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

@Override
public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getContext();
        parent.getItemAtPosition(position).toString();

        }

    @Override
public void onNothingSelected(AdapterView<?> parent) {

        }

}
