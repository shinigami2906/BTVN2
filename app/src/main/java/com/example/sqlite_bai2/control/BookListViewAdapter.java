package com.example.sqlite_bai2.control;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sqlite_bai2.R;
import com.example.sqlite_bai2.model.Book;

import java.util.ArrayList;
import java.util.Locale;

public class BookListViewAdapter extends BaseAdapter {
    final ArrayList<Book> bookList ;
    final ArrayList<Book> bookArrayList;

    public BookListViewAdapter(ArrayList<Book> bookList) {
        this.bookList = bookList;
        this.bookArrayList = new ArrayList<>();
        bookArrayList.addAll(bookList);
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return bookList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null){
            view = View.inflate(parent.getContext(), R.layout.listviewitem, null);
        }else view = convertView;

        Book book = (Book) getItem(position);
        ((TextView) view.findViewById(R.id.tvNameBook)).setText(book.getName());
        ((TextView) view.findViewById(R.id.tvTypeBook)).setText(book.getType());
        return view;
    }
    //lọc tìm kiếm
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        bookList.clear();
        if (charText.length()==0){
            bookList.addAll(bookArrayList);
        }
        else {
            for (Book book: bookArrayList){
                if (book.getName().toLowerCase(Locale.getDefault()).contains(charText)||book.getType().toLowerCase(Locale.getDefault()).contains(charText)){
                    bookList.add(book);
                }
            }
        }
        notifyDataSetChanged();
    }
}
