package com.tokevanessza.inventorysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tokevanessza.inventorysystem.helper.DatabaseHelper;
import com.tokevanessza.inventorysystem.model.BoardGame;

public class EditDeleteProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_product);


        DatabaseHelper databaseHelper=new DatabaseHelper(EditDeleteProduct.this);
        BoardGame chosenBoardGame = (BoardGame) getIntent().getSerializableExtra("ONEBOARDGAME");

        EditText detailTitle, detailCategory, detailPlayTime, detailNumOfPlayer, detailPrice, detailQuantity;
        Button delete_btn, backToMain_btn, update_btn;

        //List of get elements by their id
        detailTitle=findViewById(R.id.editGameTitle);
        detailCategory=findViewById(R.id.editGameCategory);
        detailPlayTime=findViewById(R.id.editGameTime);
        detailNumOfPlayer=findViewById(R.id.editGameNumPlayers);
        detailPrice=findViewById(R.id.editGamePrice);
        detailQuantity=findViewById(R.id.editGameQuantity);

        delete_btn=findViewById(R.id.deleteButton);
        backToMain_btn = findViewById(R.id.backToMainButton);
        update_btn=findViewById(R.id.updateButton);

        //Set the TextBoxes "Text" attribute to the boardgames attribute.
        detailTitle.setText(chosenBoardGame.getTitle());
        detailCategory.setText(chosenBoardGame.getCategoryName());
        detailPlayTime.setText(String.valueOf(chosenBoardGame.getPlayingTime()));
        detailNumOfPlayer.setText(String.valueOf(chosenBoardGame.getMaxNumOfPlayers()));
        detailPrice.setText(String.valueOf(chosenBoardGame.getPrice()));
        detailQuantity.setText(String.valueOf(chosenBoardGame.getQuantity()));

        //Delete button
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDeleted = databaseHelper.deleteOneGame(chosenBoardGame);

                openMainActivityPage();
            }
        });

        //Cancel button (Back to Main Menu)
        backToMain_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivityPage();
            }
        });

        //Update button
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    chosenBoardGame.setTitle(detailTitle.getText().toString());
                    chosenBoardGame.setCategoryName(detailCategory.getText().toString());
                    chosenBoardGame.setPlayingTime(Integer.parseInt(detailPlayTime.getText().toString()));
                    chosenBoardGame.setMaxNumOfPlayers(Integer.parseInt(detailNumOfPlayer.getText().toString()));
                    chosenBoardGame.setPrice(Integer.parseInt(detailPrice.getText().toString()));
                    chosenBoardGame.setQuantity(Integer.parseInt(detailQuantity.getText().toString()));

                    databaseHelper.updateOneGame(chosenBoardGame);

                    Toast.makeText(EditDeleteProduct.this, "Update was successful!", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(EditDeleteProduct.this, "Update was unsuccessful!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void openMainActivityPage(){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}