package com.example.akal.shoppyapp;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
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
import java.util.ArrayList;

public class MainAppPage extends AppCompatActivity {

    public final String TAG = MainAppPage.class.getSimpleName();
    ListView shoppingItemView;
    ShoppingListAdapter adapter;
    ProgressBar progressBar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("items");
    private Boolean exit = false;
    private ArrayList<ShoppingItem> shoppingItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Shoppy");
        setSupportActionBar(toolbar);

        FloatingActionButton shoppingCart = (FloatingActionButton) findViewById(R.id.cartMainPage);
        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShoppingCartWindow.class));
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.mainPageProgressBar);
        shoppingItemView = (ListView) findViewById(R.id.shoppingList);

        myRef.addValueEventListener(new ValueEventListener() {
            // This listener is only for database with reference of key "items"
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                // Now the Shopping List gets updated whenever the data changes in the server
                shoppingItems = getAllItems(dataSnapshot);
                adapter = new ShoppingListAdapter(getApplicationContext(), shoppingItems);
                progressBar.setVisibility(View.GONE);
                shoppingItemView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        shoppingItemView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent productIntent = new Intent(MainAppPage.this, IndividualProduct.class);
                productIntent.putExtra("product", shoppingItems.get(i));
                startActivity(productIntent);
            }
        });

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
                    Intent intent = new Intent(MainAppPage.this, MainAppPage.class);
                    startActivity(intent);
                    drawer.closeDrawer();
                } else if (drawerItem.getIdentifier() == 1) {
                    Intent intent = new Intent(MainAppPage.this, AboutUs.class);
                    startActivity(intent);
                } else if (drawerItem.getIdentifier() == 2) {
                    Intent intent = new Intent(MainAppPage.this, FaqPage.class);
                    startActivity(intent);
                    drawer.closeDrawer();
                }
                else if (drawerItem.getIdentifier() == 3) {
                    Intent intent = new Intent(MainAppPage.this, ChatBot.class);
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
                    Intent intent = new Intent(MainAppPage.this, FeedbackActivity.class);
                    startActivity(intent);
                    drawer.closeDrawer();
                }else if (drawerItem.getIdentifier() == 6) {
                    Intent intent = new Intent(MainAppPage.this, CheckOutScreen.class);
                    startActivity(intent);
                    drawer.closeDrawer();
                }
                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_app_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logoutItem) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), OpenScreen.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    // For exiting the application
    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
        } else {
            Snackbar.make(findViewById(R.id.main_content), "Press back again to exit", Snackbar.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);
        }
    }

    public static ArrayList<ShoppingItem> getAllItems(DataSnapshot dataSnapshot){

        ArrayList<ShoppingItem> items  = new ArrayList<ShoppingItem>();

        for (DataSnapshot item : dataSnapshot.getChildren()) {

            items.add(new ShoppingItem(
                    Integer.valueOf(item.child("productID").getValue().toString()),
                    item.child("name").getValue().toString(),
                    item.child("type").getValue().toString(),
                    item.child("description").getValue().toString(),
                    Integer.valueOf(item.child("price").getValue().toString()),
                    Integer.valueOf(item.child("quantity").getValue().toString())
            ));

        }

        return items;
    }

}