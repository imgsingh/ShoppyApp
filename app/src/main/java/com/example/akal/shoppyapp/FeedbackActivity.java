package com.example.akal.shoppyapp;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;;import java.io.File;

public class FeedbackActivity extends AppCompatActivity {

    RatingBar RatingBar;
    EditText txtComment;
    Button buttonSubmit;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if(getSupportActionBar() != null){
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //}
        setContentView(R.layout.activity_feedback);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Shoppy");
        setSupportActionBar(toolbar);

        RatingBar = (RatingBar) findViewById(R.id.RatingBar);
        txtComment = (EditText) findViewById(R.id.editTextComment);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit();
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

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
                withToolbar(toolbar).
                build();


        drawer.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                if (drawerItem.getIdentifier() == 0) {
                    Intent intent = new Intent(FeedbackActivity.this, MainAppPage.class);
                    startActivity(intent);
                    drawer.closeDrawer();
                } else if (drawerItem.getIdentifier() == 1) {
                    Intent intent = new Intent(FeedbackActivity.this, AboutUs.class);
                    startActivity(intent);
                } else if (drawerItem.getIdentifier() == 2) {
                    Intent intent = new Intent(FeedbackActivity.this, FaqPage.class);
                    startActivity(intent);
                    drawer.closeDrawer();
                }
                else if (drawerItem.getIdentifier() == 3) {
                    Intent intent = new Intent(FeedbackActivity.this, ChatBot.class);
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
                    Intent intent = new Intent(FeedbackActivity.this, Feedback.class);
                    startActivity(intent);
                    drawer.closeDrawer();
                }else if (drawerItem.getIdentifier() == 6) {
                    Intent intent = new Intent(FeedbackActivity.this, CheckOutScreen.class);
                    startActivity(intent);
                    drawer.closeDrawer();
                }
                return true;
            }
        });

    }

    private boolean ValidateFeedback(){
            boolean result = true;

        if(RatingBar.getRating() == 0.0){
            Toast.makeText(getApplicationContext(),"Rating bar cannot be empty",Toast.LENGTH_SHORT).show();
            result = false;
        }
        if(TextUtils.isEmpty(txtComment.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"Comment section cannot be empty",Toast.LENGTH_SHORT).show();
            result = false;
        }
        else {
            Toast.makeText(getApplicationContext(),"Submit Successful",Toast.LENGTH_SHORT).show();

        }
        return result;

    }

        private void Submit() {

                if(ValidateFeedback()){
                    Feedback fb = new Feedback(

                            RatingBar.getRating(),
                            txtComment.getText().toString().trim()

                    );

                    mDatabase.child(getString(R.string.DB_Feedbacks))
                            .child(mAuth.getCurrentUser().getUid())
                            .setValue(fb);

                    startActivity(new Intent(FeedbackActivity.this, MainAppPage.class));
                }

        }


    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}








