package com.example.sqlite_bai2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.sqlite_bai2.control.BookListViewAdapter;
import com.example.sqlite_bai2.database.DatabaseHelper;
import com.example.sqlite_bai2.model.Book;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ListView lvShow;
    Dialog dialog;
    DatabaseHelper databaseHelper;
    BookListViewAdapter bookListViewAdapter;
    ArrayList<Book> arrBook;
    SearchView svBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setID();
        databaseHelper = new DatabaseHelper(this);
        arrBook = new ArrayList<>();
        arrBook.addAll(databaseHelper.getAllBook());

        bookListViewAdapter = new BookListViewAdapter(arrBook);
        lvShow.setAdapter(bookListViewAdapter);
        //Tìm kiếm
        svBook.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    bookListViewAdapter.filter("");
                    lvShow.clearTextFilter();
                } else {
                    bookListViewAdapter.filter(newText);
                }

                return true;
            }
        });
        //set sự kiện cho item
        lvShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = (Book) bookListViewAdapter.getItem(position);
                HopThoaiSelect(book, position);
            }
        });
    }

    protected void setID() {
        lvShow = findViewById(R.id.lvShow);
        svBook = findViewById(R.id.svBook);
        dialog = new Dialog(HomeActivity.this);
    }
    //set sự kiện cho menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itAdd:
                HopThoai();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //Thêm sách vào cơ sở dữ liệu
    public void HopThoai() {
        dialog.setContentView(R.layout.dialogthem);
        dialog.setCanceledOnTouchOutside(false);
        final EditText etRegisterBookName = dialog.findViewById(R.id.etRegisterBookName);
        final EditText etRegisterBookType = dialog.findViewById(R.id.etRegisterBookType);
        Button btHuy = dialog.findViewById(R.id.btHuy);
        Button btThem = dialog.findViewById(R.id.btThem);
        btThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book();
                book.setName(etRegisterBookName.getText().toString());
                book.setType(etRegisterBookType.getText().toString());
                databaseHelper.addBook(book);
                arrBook.add(book);
                bookListViewAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    //lựa chọn xóa item
    public void HopThoaiXoa(final int id, final int position) {
        dialog.setContentView(R.layout.dialogxoa);
        dialog.setCanceledOnTouchOutside(false);
        Button btCancel = dialog.findViewById(R.id.btCancel);
        Button btXacNhan = dialog.findViewById(R.id.btConfirm);
        btXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteBook(id);
                arrBook.remove(position);
                bookListViewAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    //Lựa chọn sửa hoặc xóa item
    public void HopThoaiSelect(final Book book, final int position) {
        dialog.setContentView(R.layout.dialogselect);
        dialog.setCanceledOnTouchOutside(false);
        Button btCancel = dialog.findViewById(R.id.btCancel1);
        Button btXoa = dialog.findViewById(R.id.btDelete);
        Button btUpdate = dialog.findViewById(R.id.btUpdate);
        btXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HopThoaiXoa(book.getId(), position);
            }
        });
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HopThoaiUpdate(book, position);
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    //Lựa chọn chỉnh sửa item
    public void HopThoaiUpdate(final Book book, final int position) {
        dialog.setContentView(R.layout.dialogthem);
        dialog.setCanceledOnTouchOutside(false);
        final EditText etUpdateBookName = dialog.findViewById(R.id.etRegisterBookName);
        final EditText etUpdateBookType = dialog.findViewById(R.id.etRegisterBookType);
        Button btHuy = dialog.findViewById(R.id.btHuy);
        Button btThem = dialog.findViewById(R.id.btThem);
        etUpdateBookName.setText(book.getName());
        etUpdateBookType.setText(book.getType());
        btThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book();
                book.setId(book.getId());
                book.setName(etUpdateBookName.getText().toString());
                book.setType(etUpdateBookType.getText().toString());
                databaseHelper.updateBook(book);
                arrBook.get(position).setName(book.getName());
                arrBook.get(position).setType(book.getType());
                bookListViewAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}