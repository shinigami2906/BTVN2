package com.example.sqlite_bai2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlite_bai2.model.Account;
import com.example.sqlite_bai2.model.Book;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String dbName = "QLTV";
    public static final int version = 1;
    public static final String Table_Account = "account";
    public static final String Table_Book = "book";

    public DatabaseHelper(Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String scrip = "CREATE TABLE IF NOT EXISTS " + Table_Account
                + "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Ten TEXT," +
                "Email TEXT," +
                "Password TEXT)";
        String scrip1 = "CREATE TABLE IF NOT EXISTS " + Table_Book
                + "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Ten_Sach TEXT," +
                "The_Loai TEXT)";
        db.execSQL(scrip);
        db.execSQL(scrip1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addAccount(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Ten",account.getName());
        values.put("Email",account.getEmail());
        values.put("Password",account.getPassword());
        db.insert(Table_Account,null,values);
        db.close();
    }
    public String checkAccount(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String scrip = "SELECT * FROM "+Table_Account+" WHERE Email = ? AND Password = ?";
        Cursor cursor = db.rawQuery(scrip,new String[]{email+"",password+""});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            String name = cursor.getString(1);
            cursor.close();
            return name;
        }
        cursor.close();
        return "";
    }
    public ArrayList<Book> getAllBook(){
        ArrayList<Book> arrBook = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String scrip = "SELECT * FROM "+Table_Book;
        Cursor cursor = db.rawQuery(scrip,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String type = cursor.getString(2);
            Book book = new Book(id,name,type);
            arrBook.add(book);
            cursor.moveToNext();
        }
        cursor.close();
        return arrBook;
    }
    public void addBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Ten_Sach",book.getName());
        values.put("The_Loai",book.getType());
        db.insert(Table_Book,null,values);
        db.close();
    }
    public void deleteBook(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+Table_Book+" where id = ?", new String[]{String.valueOf(id)});
    }
    public void updateBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        String scrip = "UPDATE "+Table_Book+" SET Ten_Sach = ?, The_Loai = ? WHERE Id = ?";
        db.execSQL(scrip,new String[]{book.getName(),book.getType(),String.valueOf(book.getId())});
    }
}
