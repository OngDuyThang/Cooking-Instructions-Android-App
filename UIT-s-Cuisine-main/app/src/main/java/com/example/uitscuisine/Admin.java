package com.example.uitscuisine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieDrawable;
import com.amrdeveloper.lottiedialog.LottieDialog;
import com.bumptech.glide.Glide;
import com.example.uitscuisine.custom_textview.PoppinsBoldTextView;
import com.example.uitscuisine.custom_textview.PoppinsMediumTextView;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

public class Admin extends AppCompatActivity {

    private ListView recipesList;
    private ArrayList<AdminListRecipe> recipesArray = new ArrayList<>();
    private AdminListRecipeAdapter recipeAdapter;
    private PoppinsMediumTextView getting;
    private CircleImageView userAvatar;
    private TextView guest;
    LottieDialog Dialog;
    Admin mAdminActivity;
    String phone_check;
    String username_check;
    String password_check;
    String email_check;
    ImageButton imageButtonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        guest          = (TextView)findViewById(R.id.guest);
        recipesList    = (ListView) findViewById(R.id.recipesList);
        getting        = (PoppinsMediumTextView)findViewById(R.id.getting);
        userAvatar     = (CircleImageView)findViewById(R.id.userAvatar);
        imageButtonBack= (ImageButton)findViewById(R.id.imageButtonBack);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this,Login.class);
                startActivity(intent);
            }
        });
        Calendar calendar = Calendar.getInstance();
        int jam = calendar.get(Calendar.HOUR_OF_DAY);
        if(jam >= 0 && jam <16){
            getting.setText("Good Morning");
        }else if(jam >=12 && jam <16){
            getting.setText("Good Afternoon");
        }else if(jam >=16 && jam <21){
            getting.setText("Good Evening");
        }else if(jam >=21 && jam <24){
            getting.setText("Good Night");
        }else {
            getting.setText("UIT's Cuisine Hello");
        }
        Dialog = new LottieDialog(Admin.this)
                .setAnimation(R.raw.pan)
                .setAnimationRepeatCount(LottieDrawable.INFINITE)
                .setAutoPlayAnimation(true)
                .setMessage("Admin Loading...")
                .setMessageTextSize(30)
                .setDialogBackground(Color.WHITE)
                .setAnimationViewHeight(400)
                .setCancelable(false);
        //Get intent
        Intent intent = getIntent();
        phone_check   = intent.getStringExtra("mobile");
        username_check= intent.getStringExtra("username");
        password_check= intent.getStringExtra("password");
        email_check   = intent.getStringExtra("email");

        showListView();
        recipesListListener();
        deleteRecipe();
    }
    private void recipesListListener() {
        recipesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Admin.this, RecipeDetails.class);
                TextView recipeId = (TextView) view.findViewById(R.id.recipeId);
                intent.putExtra("recipeId", recipeId.getText());
                intent.putExtra("mobile",phone_check);
                startActivity(intent);
            }
        });
    }
    private void deleteRecipe(){
        displayData();
        recipesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Admin.this);
                alert.setTitle("Delete Recipe");
                alert.setMessage("Do you want delete this recipe");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Posts");
                        TextView recipeId = (TextView) view.findViewById(R.id.recipeId);
                        String recipeCheck= recipeId.getText().toString();
                        database.child("Recipe ID: "+recipeCheck).removeValue();
                        Dialog.show();
                        DatabaseReference database3 = FirebaseDatabase.getInstance().getReference().child("Profile");
                        DatabaseReference database2 = FirebaseDatabase.getInstance().getReference().child("Posts");
                        Query checkUser = database3.orderByKey();
                        Query checkUser2 = database2.orderByKey();

                        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                boolean foundUser = false;
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    String phoneFromDB = dataSnapshot.child("phone").getValue().toString();
                                    if (phone_check.equals(phoneFromDB)) {
                                        String nameFromDB = dataSnapshot.child("name").getValue().toString();
                                        String imageFromDB = dataSnapshot.child("imageURl").getValue().toString();
                                        guest.setText(nameFromDB);
                                        Glide.with(Admin.this).load(imageFromDB).into(userAvatar);
                                        Toast.makeText(Admin.this, "Hello Admin", Toast.LENGTH_SHORT).show();
                                        Dialog.dismiss();
                                    }
                                }
                                if (foundUser == true) {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(Admin.this);
                                    alert.setTitle("Failed!!");
                                    alert.setMessage("You haven't registered profile information");
                                    alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(Admin.this, Login.class);
                                            startActivity(intent);
                                        }
                                    });
                                    alert.show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) { }
                        });

                    }

                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Admin.this, Login.class);
        startActivity(intent);
        super.onBackPressed();
    }

    private void createRecipesList(String strRecipeName, String strDuration, String strDifficulty, String strPosterName, String imageFromDB, String imageFromDB2, String strRecipeId) {
        recipesArray.add(new AdminListRecipe(strDuration, strDifficulty, strRecipeName, strPosterName, imageFromDB, imageFromDB2, strRecipeId));
        recipeAdapter = new AdminListRecipeAdapter(Admin.this, R.layout.item_adminlist, recipesArray);
        recipesList.setAdapter(recipeAdapter);
    }
    private void showListView(){
        DatabaseReference database2 = FirebaseDatabase.getInstance().getReference().child("Posts");
        Query checkUser2 = database2.orderByKey();
        checkUser2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String strRecipeName = dataSnapshot.child("recipeName").getValue().toString();
                        String strDuration = dataSnapshot.child("duration").getValue().toString();
                        String strDifficulty = dataSnapshot.child("difficulty").getValue().toString();
                        String imageFromDB = dataSnapshot.child("recipeImage").getValue().toString();
                        String strPosterName = dataSnapshot.child("posterName").getValue().toString();
                        String imageFromDB2 = dataSnapshot.child("posterAvatar").getValue().toString();
                        String strRecipeId = dataSnapshot.child("recipeId").getValue().toString();
                        createRecipesList(strRecipeName, strDuration, strDifficulty, strPosterName, imageFromDB, imageFromDB2, strRecipeId);
                        Dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void displayData() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            try {
                                String NameUser = object.getString("name");
                                String url = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                guest.setText(NameUser);
                                Glide.with(Admin.this).load(url).into(userAvatar);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // Application code
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,picture.type(large)");
            request.setParameters(parameters);
            request.executeAsync();
        }
        else{
            try {
                Dialog.show();
                DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Profile");
                DatabaseReference database2 = FirebaseDatabase.getInstance().getReference().child("Posts");
                Query checkUser = database.orderByKey();
                Query checkUser2 = database2.orderByKey();

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean foundUser = false;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String phoneFromDB = dataSnapshot.child("phone").getValue().toString();
                            if (phone_check.equals(phoneFromDB)) {
                                String nameFromDB = dataSnapshot.child("name").getValue().toString();
                                String imageFromDB = dataSnapshot.child("imageURl").getValue().toString();
                                guest.setText(nameFromDB);
                                Glide.with(Admin.this).load(imageFromDB).into(userAvatar);
                                Toast.makeText(Admin.this, "Hello Admin", Toast.LENGTH_SHORT).show();
                                Dialog.dismiss();
                            }
                        }
                        if (foundUser == true) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(Admin.this);
                            alert.setTitle("Failed!!");
                            alert.setMessage("You haven't registered profile information");
                            alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(Admin.this, Login.class);
                                    startActivity(intent);
                                }
                            });
                            alert.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });

            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }
}