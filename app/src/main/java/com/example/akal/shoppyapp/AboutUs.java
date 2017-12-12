package com.example.akal.shoppyapp;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.File;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Shoppy");
        setSupportActionBar(toolbar);

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
                    Intent intent = new Intent(AboutUs.this, MainAppPage.class);
                    startActivity(intent);
                    drawer.closeDrawer();
                } else if (drawerItem.getIdentifier() == 1) {
                    Intent intent = new Intent(AboutUs.this, AboutUs.class);
                    startActivity(intent);
                } else if (drawerItem.getIdentifier() == 2) {
                    Intent intent = new Intent(AboutUs.this, FaqPage.class);
                    startActivity(intent);
                    drawer.closeDrawer();
                }
                else if (drawerItem.getIdentifier() == 3) {
                    Intent intent = new Intent(AboutUs.this, ChatBot.class);
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
                    Intent intent = new Intent(AboutUs.this, Feedback.class);
                    startActivity(intent);
                    drawer.closeDrawer();
                }else if (drawerItem.getIdentifier() == 6) {
                    Intent intent = new Intent(AboutUs.this, CheckOutScreen.class);
                    startActivity(intent);
                    drawer.closeDrawer();
                }
                return true;
            }
        });
    }
}