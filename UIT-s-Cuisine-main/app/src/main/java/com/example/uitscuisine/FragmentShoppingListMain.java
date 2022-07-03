package com.example.uitscuisine;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class FragmentShoppingListMain extends Fragment {
    private List<ShoppingList_ChildItem> ingredientsList;
    private RecyclerView ParentRecyclerViewItem;
    private LinearLayoutManager layoutManager;
    private List<ShoppingList_ParentItem> itemList = new ArrayList<>();
    private TextView sumItems;
    private Context mContext;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list_main, container, false);
        ParentRecyclerViewItem
                = view.findViewById(R.id.parent_recyclerview);

        // Initialise the Linear layout manager
        /*LinearLayoutManager
                layoutManager
                = new LinearLayoutManager(
                view.this);*/
        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        // Pass the arguments
        // to the parentItemAdapter.
        // These arguments are passed
        // using a method ParentItemList()
//        ShoppingList_ParentItemAdapter
//                parentItemAdapter
//                = new ShoppingList_ParentItemAdapter(
//                ParentItemList());
//
//        // Set the layout manager
//        // and adapter for items
//        // of the parent recyclerview
//        ParentRecyclerViewItem
//                .setAdapter(parentItemAdapter);
        displayDataFromDB();
//        ParentRecyclerViewItem
//                .setLayoutManager(layoutManager);

        deleteShoppingList();
        return view;
    }

    private List<ShoppingList_ParentItem> ParentItemList() {
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

    private ShoppingList_ParentItem titleIngredients(String title, String ingr) {
        ShoppingList_ParentItem item
                = new ShoppingList_ParentItem(title,
                ingredientsList(ingr));
        return item;
    }

    // Method to pass the arguments
    // for the elements
    // of child RecyclerView
    private List<ShoppingList_ChildItem> ChildItemList() {
        List<ShoppingList_ChildItem> ChildItemList
                = new ArrayList<>();

        ChildItemList.add(new ShoppingList_ChildItem("Card 1"));
        ChildItemList.add(new ShoppingList_ChildItem("Card 2"));
        ChildItemList.add(new ShoppingList_ChildItem("Card 3"));
        ChildItemList.add(new ShoppingList_ChildItem("Card 4"));

        return ChildItemList;
    }

    private List<ShoppingList_ChildItem> ingredientsList(String strIngredients) {
        ingredientsList = new ArrayList<>();

        String concatStr = "";
        for (int i = 0; i < strIngredients.length(); i++) {
            if (i == strIngredients.length() - 1) {
                ingredientsList.add(new ShoppingList_ChildItem(concatStr));
            } else {
                if (strIngredients.charAt(i) == '\n') {
                    ingredientsList.add(new ShoppingList_ChildItem(concatStr));
                    concatStr = "";
                } else {
                    concatStr += strIngredients.charAt(i);
                }
            }
        }
        return ingredientsList;
    }

    private void displayDataFromDB() {
        try {
            DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("ShoppingList");
            Query checkUser = database.orderByKey();
            String username = "BanhBao";
            String status = "true";
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String usernameFromDB = dataSnapshot.child("username").getValue().toString();
                        String status = dataSnapshot.child("status").getValue().toString();
                        if (username.equals(usernameFromDB) && status.equals(status)) {
                            Log.e("LOG BUG", "Username ok");
                            String strRecipeName = dataSnapshot.child("nameRecipe").getValue().toString();
                            String strIngredients = dataSnapshot.child("ingredients").getValue().toString();

                            itemList.add(titleIngredients(strRecipeName, strIngredients));
                        }
                    }
                    ShoppingList_ParentItemAdapter
                            parentItemAdapter
                            = new ShoppingList_ParentItemAdapter(itemList);
                    ParentRecyclerViewItem
                            .setAdapter(parentItemAdapter);
                    ParentRecyclerViewItem
                            .setLayoutManager(layoutManager);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void deleteShoppingList()
    {
        ParentRecyclerViewItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final CharSequence[] items = {"Supprimer", "etc", "etc1"};

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                builder.setTitle("Select The Action");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                    }
                });
                builder.show();
                return true;
            }
        });
    }

}