package com.example.uitscuisine;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListMain extends AppCompatActivity {
    private String recipeID;
    private Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.shopping_list_main);

        RecyclerView
                ParentRecyclerViewItem
                = findViewById(
                R.id.parent_recyclerview);

        // Initialise the Linear layout manager
        LinearLayoutManager
                layoutManager
                = new LinearLayoutManager(
                ShoppingListMain.this);

        // Pass the arguments
        // to the parentItemAdapter.
        // These arguments are passed
        // using a method ParentItemList()
        ShoppingList_ParentItemAdapter
                parentItemAdapter
                = new ShoppingList_ParentItemAdapter(
                ParentItemList());

        // Set the layout manager
        // and adapter for items
        // of the parent recyclerview
        ParentRecyclerViewItem
                .setAdapter(parentItemAdapter);
        ParentRecyclerViewItem
                .setLayoutManager(layoutManager);

    }

    private List<ShoppingList_ParentItem> ParentItemList()
    {
        List<ShoppingList_ParentItem> itemList
                = new ArrayList<>();

        ShoppingList_ParentItem item
                = new ShoppingList_ParentItem(
                "Title 1",
                ChildItemList());
        itemList.add(item);
        ShoppingList_ParentItem item1
                = new ShoppingList_ParentItem(
                "Title 2",
                ChildItemList());
        itemList.add(item1);
        ShoppingList_ParentItem item2
                = new ShoppingList_ParentItem(
                "Title 3",
                ChildItemList());
        itemList.add(item2);
        ShoppingList_ParentItem item3
                = new ShoppingList_ParentItem(
                "Title 4",
                ChildItemList());
        itemList.add(item3);

        return itemList;
    }

    // Method to pass the arguments
    // for the elements
    // of child RecyclerView
    private List<ShoppingList_ChildItem> ChildItemList()
    {
        List<ShoppingList_ChildItem> ChildItemList
                = new ArrayList<>();

        ChildItemList.add(new ShoppingList_ChildItem("Card 1"));
        ChildItemList.add(new ShoppingList_ChildItem("Card 2"));
        ChildItemList.add(new ShoppingList_ChildItem("Card 3"));
        ChildItemList.add(new ShoppingList_ChildItem("Card 4"));

        return ChildItemList;
    }

}
