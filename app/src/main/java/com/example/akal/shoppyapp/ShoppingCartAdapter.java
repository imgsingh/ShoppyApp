package com.example.akal.shoppyapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Akal on 10-11-2017.
 */

public class ShoppingCartAdapter extends ArrayAdapter {
    Context context;
    ArrayList<ShoppingItem> items;
    Locale indiaLocale = new Locale("en","IN");


    public ShoppingCartAdapter(Context context, List<ShoppingItem> items){
        super(context, 0, items);
        this.context = context;
        this.items = (ArrayList<ShoppingItem>) items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.cart_item, parent, false
            );
        }

        ShoppingItem currentItem = (ShoppingItem) getItem(position);

        ImageView img = (ImageView) listItemView.findViewById(R.id.cartItemIcon);
        Picasso.with(getContext())
                .load(context.getApplicationContext().getString(R.string.ip)
                        + String.valueOf(currentItem.getProductID())
                        + ".jpg")
                .fit()
                .into(img);

        ((TextView) listItemView.findViewById(R.id.cartItemName))
                .setText(currentItem.getTitle());

        String x = "x " + String.valueOf(currentItem.getQuantity());
        ((TextView) listItemView.findViewById(R.id.cartItemQuantity))
                .setText(x);

        int itemPrice=0;
        try{
            itemPrice = Integer.valueOf(NumberFormat.getCurrencyInstance(indiaLocale)
                    .parse(String.valueOf(currentItem.getPrice()))
                    .toString());
        } catch (ParseException e){
            e.printStackTrace();
        }
        ((TextView) listItemView.findViewById(R.id.cartItemPrice))
                .setText(NumberFormat.getCurrencyInstance(indiaLocale).format(itemPrice));

        ((TextView) listItemView.findViewById(R.id.cartItemTotal))
                .setText(NumberFormat.getCurrencyInstance(indiaLocale).format(itemPrice * currentItem.getQuantity()));
        return listItemView;
    }
}
