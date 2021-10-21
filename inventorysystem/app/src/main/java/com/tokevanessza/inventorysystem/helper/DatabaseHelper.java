package com.tokevanessza.inventorysystem.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.tokevanessza.inventorysystem.model.BoardGame;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database name and version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "boardgame.db";

    //Table
    private static final String BOARD_GAME_TABLE = "BoardGames";

    //BoardGame table columns
    private static final String PK_ID = "id";
    private static final String BOARD_GAME_NAME = "title";
    private static final String CATEGORY_NAME = "categoryName";
    private static final String PLAYING_TIME = "playingTime";
    private static final String MAX_NUMBER_OF_PLAYERS = "maxNumOfPlayers";
    private static final String PRICE = "price";
    private static final String QUANTITY = "quantity";
    private static final String TOTAL_REVENUE_BY_GAME = "totalRevenueByGame";

    //BoardGame table create statement
    public static String CREATE_TABLE_BOARD_GAME =
            "CREATE TABLE "+BOARD_GAME_TABLE+
                    "("+PK_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    +BOARD_GAME_NAME+" TEXT NOT NULL,"
                    +CATEGORY_NAME+" TEXT NOT NULL,"
                    +PLAYING_TIME+" TEXT NOT NULL,"
                    +MAX_NUMBER_OF_PLAYERS+" INTEGER NOT NULL,"
                    +PRICE+" INTEGER NOT NULL,"
                    +QUANTITY+" INTEGER NOT NULL,"
                    +TOTAL_REVENUE_BY_GAME+" INTEGER NOT NULL"+")";


    //Constructor, create database
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BOARD_GAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+BOARD_GAME_TABLE);
        onCreate(db);
    }

    // CRUD process create method
    public boolean addGame(BoardGame boardGame){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(BOARD_GAME_NAME, boardGame.getTitle());
        contentValues.put(CATEGORY_NAME, boardGame.getCategoryName());
        contentValues.put(PLAYING_TIME, boardGame.getPlayingTime());
        contentValues.put(MAX_NUMBER_OF_PLAYERS, boardGame.getMaxNumOfPlayers());
        contentValues.put(PRICE, boardGame.getPrice());
        contentValues.put(QUANTITY, boardGame.getQuantity());
        contentValues.put(TOTAL_REVENUE_BY_GAME,boardGame.calculateTotalRevenueByGame());

        long insert = db.insert(BOARD_GAME_TABLE, null, contentValues);
        db.close();

        if (insert==-1) return false;
        else return true;
    }

    //CRUD process read method for all items
    public List<BoardGame> getAllGame(){
        List<BoardGame> returnList = new ArrayList<>();
        String SELECT_ALL_ITEM = "SELECT * FROM "+BOARD_GAME_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL_ITEM, null);

        if (cursor.moveToFirst()){
            do{
                int gameId = cursor.getInt(0);
                String gameTitle = cursor.getString(1);
                String gameCategory = cursor.getString(2);
                int gameTime = cursor.getInt(3);
                int gameMaxNum = cursor.getInt(4);
                int gamePrice = cursor.getInt(5);
                int gameQuantity = cursor.getInt(6);

                BoardGame newBoardGame = new BoardGame(gameId,gameTitle,gameCategory,gameTime,gameMaxNum,gamePrice,gameQuantity);
                returnList.add(newBoardGame);
            }while (cursor.moveToNext());
        }else

        cursor.close();
        db.close();

        return returnList;
    }

    //CRUD process read method for one item
    public BoardGame getOneGame(BoardGame boardGame) {
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_ONE_ITEM="SELECT * FROM "+BOARD_GAME_TABLE+" WHERE "+PK_ID+" = "+boardGame.getId();

        Cursor cursor = db.rawQuery(SELECT_ONE_ITEM, null);

        if (cursor!=null) cursor.moveToFirst();

        BoardGame oneGame = new BoardGame(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getInt(4),
                cursor.getInt(5),
                cursor.getInt(6));

        db.close();
        return oneGame;
    }

    //CRUD process delete method
    public boolean deleteOneGame(BoardGame boardGame){
        SQLiteDatabase db =this.getWritableDatabase();
        String DELETE_ONE_ITEM = "DELETE FROM "+BOARD_GAME_TABLE +" WHERE "+PK_ID+" = "+boardGame.getId();
        Cursor cursor = db.rawQuery(DELETE_ONE_ITEM, null);

        if (cursor.moveToFirst()) return true;
        else return false;

    }

    //CRUD process update method
    public boolean updateOneGame(BoardGame boardGame){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PK_ID, boardGame.getId());
        contentValues.put(BOARD_GAME_NAME, boardGame.getTitle());
        contentValues.put(CATEGORY_NAME, boardGame.getCategoryName());
        contentValues.put(PLAYING_TIME, boardGame.getPlayingTime());
        contentValues.put(MAX_NUMBER_OF_PLAYERS, boardGame.getMaxNumOfPlayers());
        contentValues.put(PRICE, boardGame.getPrice());
        contentValues.put(QUANTITY, boardGame.getQuantity());

        String whereClause = " = ?";
        String[] whereArgs = new String[]{String.valueOf(boardGame.getId())};

        db.update(BOARD_GAME_TABLE,contentValues,PK_ID+whereClause,whereArgs);

        db.close();
        return true;
    }
}
