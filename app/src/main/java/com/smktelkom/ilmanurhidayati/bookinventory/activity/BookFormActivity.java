package com.smktelkom.ilmanurhidayati.bookinventory.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.smktelkom.ilmanurhidayati.bookinventory.R;
import com.smktelkom.ilmanurhidayati.bookinventory.model.Book;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookFormActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.editTitle)
    EditText editTitle;
    @BindView(R.id.editAuthor)
    EditText editAuthor;
    @BindView(R.id.editGenre)
    EditText editGenre;
    @BindView(R.id.editISBN)
    EditText editISBN;
    @BindView(R.id.editPublished)
    EditText editPublished;
    @BindView(R.id.editSynopsis)
    EditText editSynopsis;
    @BindView(R.id.btnSave)
    Button btnSave;
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_form);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        //untuk view detail dari buku yg sudah ada
        if (bundle != null) {
            book = (Book) bundle.getSerializable("bookEdit");
            editISBN.setText(book.getISBN());
            editPublished.setText(book.getPublished_year() + "");
            editAuthor.setText(book.getBook_author());
            editTitle.setText(book.getBook_title());
            editGenre.setText(book.getBook_genre());
            editSynopsis.setText(book.getBook_synopsis());
            btnSave.setEnabled(false);
            getSupportActionBar().setTitle(book.getBook_title());
        } else {
            //buku baru
            book = new Book();
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    book.setISBN(editISBN.getText().toString());
                    book.setBook_title(editTitle.getText().toString());
                    book.setBook_author(editAuthor.getText().toString());
                    book.setPublished_year(Integer.parseInt(editPublished.getText().toString()));
                    book.setBook_genre(editGenre.getText().toString());
                    book.setBook_synopsis(editSynopsis.getText().toString().equals("") ? "-" :
                            editSynopsis.getText().toString());

                    Intent i = new Intent();
                    i.putExtra("book", book);
                    setResult(RESULT_OK, i);
                    finish();
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validate() {
        boolean valid = true;

        String isbn = editISBN.getText().toString();
        String title = editTitle.getText().toString();
        String author = editAuthor.getText().toString();
        String published = editPublished.getText().toString();

        if (isbn.isEmpty()) {
            editISBN.setError("Enter ISBN");
            valid = false;
        } else {
            editISBN.setError(null);
        }

        if (title.isEmpty()) {
            editTitle.setError("Enter Book Title");
            valid = false;
        } else {
            editTitle.setError(null);
        }

        if (author.isEmpty()) {
            editAuthor.setError("Enter Book Author");
            valid = false;
        } else {
            editAuthor.setError(null);
        }

        if (published.isEmpty() || published.length() < 4) {
            editPublished.setError("Published Year empty or must in yyyy format ");
            valid = false;
        } else {
            editISBN.setError(null);
        }

        return valid;
    }
}
