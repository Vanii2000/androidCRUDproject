package com.tokevanessza.inventorysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.tokevanessza.inventorysystem.helper.DatabaseHelper;
import com.tokevanessza.inventorysystem.model.BoardGame;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    private Button crt_btn;
    private ListView listViewOfGames;
    private TextView calculatedRevenue;

    ArrayAdapter gameArrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper=new DatabaseHelper(this);

        crt_btn=findViewById(R.id.createProduct);
        listViewOfGames=findViewById(R.id.listViewOfGames);
        calculatedRevenue=findViewById(R.id.revenueNum);

        calculatedRevenue.setText(String.valueOf(calculateExceptedRevenueAllGame()));

        gameArrayAdapter = new ArrayAdapter<BoardGame>(MainActivity.this, android.R.layout.simple_list_item_1,databaseHelper.getAllGame());
        listViewOfGames.setAdapter(gameArrayAdapter);


        //Action for create button
        crt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateProductPage();
            }
        });

        //Action for list view click
        listViewOfGames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BoardGame clickedGame = (BoardGame) parent.getItemAtPosition(position);
                BoardGame oneGame = databaseHelper.getOneGame(clickedGame);
                openEditDeleteProductPage(oneGame);
            }
        });
    }

    //Open the Edit and Create page of the BoardGame Inventory System app
    public void openCreateProductPage(){
        Intent intent=new Intent(this, CreateProduct.class);
        startActivity(intent);
    }

    public void openEditDeleteProductPage(BoardGame oneGame){
        BoardGame openGame = oneGame;

        Intent intent=new Intent(this, EditDeleteProduct.class);
        intent.putExtra("ONEBOARDGAME",oneGame);
        startActivity(intent);
    }


    // The total Revenue (for all game) are calculated here.
    private int calculateExceptedRevenueAllGame(){
        List<BoardGame> allGame = databaseHelper.getAllGame();
        int sumExpectedRevenue=0;

        for (int i=0; i<allGame.size(); i++) {
            sumExpectedRevenue=sumExpectedRevenue+allGame.get(i).getTotalRevenueByGame();
        }

        return sumExpectedRevenue;
    }
}