package com.tokevanessza.inventorysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.tokevanessza.inventorysystem.helper.DatabaseHelper;
import com.tokevanessza.inventorysystem.model.BoardGame;

import java.util.List;

public class CreateProduct extends AppCompatActivity {

    //Declarations
    private Button save_btn, cancel_btn;
    private TextView edit_title, edit_category, edit_playTime, edit_numPlay, edit_price, edit_quantity;
    private Switch edit_isSold;

    ArrayAdapter gameArrayAdapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        //List of get elements by their id
        save_btn=findViewById(R.id.saveButton);
        cancel_btn=findViewById(R.id.cancelButton);
        edit_title=findViewById(R.id.editGameTitle);
        edit_category=findViewById(R.id.editGameCategory);
        edit_playTime=findViewById(R.id.editGameTime);
        edit_numPlay=findViewById(R.id.editGameNumPlayers);
        edit_price=findViewById(R.id.editGamePrice);
        edit_quantity=findViewById(R.id.editGameQuantity);

        databaseHelper =new DatabaseHelper(CreateProduct.this);

        //Save/Create button
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoardGame boardGame;

                try {
                    boardGame = new BoardGame(-1,
                            edit_title.getText().toString(),
                            edit_category.getText().toString(),
                            Integer.parseInt(edit_playTime.getText().toString()),
                            Integer.parseInt(edit_numPlay.getText().toString()),
                            Integer.parseInt(edit_price.getText().toString()),
                            Integer.parseInt(edit_quantity.getText().toString()));

                    databaseHelper.addGame(boardGame);
                    Toast.makeText(CreateProduct.this, "Board game "+boardGame.getTitle()+" was added!", Toast.LENGTH_SHORT).show();
                    goBackToMain();
                  }
                  catch (Exception e){
                      Toast.makeText(CreateProduct.this, "No new game was added! Try again!", Toast.LENGTH_SHORT).show();
                   }
            }
        });

        //Cancel button
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain();
            }
        });
    }

    public void goBackToMain(){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}