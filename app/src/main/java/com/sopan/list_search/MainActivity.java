package com.sopan.list_search;

/**
 * Created by Sopan on 05-Apr-16.
 */

import java.util.ArrayList;
import java.lang.String;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
    ArrayList<SearchResults> originalResults, searchResults;
    EditText editText;
    ListView lv;
    String books[];
    String authors[];
    String publishers[];
    CustomAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.search);
        lv = (ListView) findViewById(R.id.custView);
        initializeList();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String searchString = s.toString();
                searchResults.clear();
                int size = originalResults.size();
                int counter[] = new int[size];
                for (int i = 0; i < size; i++)
                    counter[i] = 0;

                for (int i = 0; i < size; i++) {
                    String bookName = originalResults.get(i).getBook().toLowerCase();
                    if (bookName.contains(searchString)) {
                        counter[i] = 1;
                        searchResults.add(originalResults.get(i));
                    }
                }
                for (int i = 0; i < size; i++) {
                    String authorName = originalResults.get(i).getAuthor().toLowerCase();
                    if (authorName.contains(searchString) && counter[i] != 1) {
                        counter[i] = 1;
                        searchResults.add(originalResults.get(i));
                    }
                }
                for (int i = 0; i < size; i++) {
                    String publisherName = originalResults.get(i).getPublisher().toLowerCase();
                    if (publisherName.contains(searchString) && counter[i] != 1) {
                        counter[i] = 1;
                        searchResults.add(originalResults.get(i));
                    }
                }
                int countItems = 0;
                for (int i = 0; i < size; i++)
                    if (counter[i] != 0)
                        countItems++;
                if (editText.getText().toString().equals("") || searchString.equals("") || searchString.isEmpty() || editText.getText().toString().isEmpty())
                    setTitle("Book Library");
                else if (!searchString.isEmpty() && countItems > 1)
                    setTitle(countItems + " results returned");
                else if (countItems <= 1)
                    setTitle(countItems + " result returned");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                searchResults = new ArrayList<SearchResults>(originalResults);
                adapter = new CustomAdapter(this, searchResults);
                lv.setAdapter(adapter);
                setTitle("Book Library");
                editText.setText("");
                editText.setSelection(0);
                return true;

            case R.id.about:

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("About");
                builder.setMessage("Sopan Ahmed");
                AlertDialog dialog = builder.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void setTextWithSpan(TextView textView, String text, String spanText, StyleSpan style) {
        SpannableStringBuilder sb = new SpannableStringBuilder(text);
        int start = text.indexOf(spanText);
        int end = start + spanText.length();
        sb.setSpan(style, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(sb);
    }

    private void initializeList() {
        originalResults = new ArrayList<SearchResults>();

        books = new String[]{"David Copperfield", "Pride and Prejudice", "I Too Had A Love Story", "Moby Dick",
                "Wuthering Heights", "War and Peace", "The Song of Ice and Fire : Winds of Winter", "The Casuarina Tree"};

        authors = new String[]{"Charles Dickens", "Jane Austen", "Ravinder Singh", "Herman Melville",
                "Emily Bronte", "Leo Tolstoy", "George R.R. Martin", "William Somerset Maugham"};

        publishers = new String[] {"Oxford University Press", "Cambridge Press", "Pelican Publishers", "Penguin Random House",
                "Heinemann Publishers", "Mir Publishers", "Redbook", "Heinemann Publishers"};

        for(int i = 0; i < books.length; i ++) {
            SearchResults sr = new SearchResults();
            sr.setBook(books[i]);
            sr.setAuthor(authors[i]);
            sr.setPublisher(publishers[i]);
            originalResults.add(sr);
        }
        searchResults = new ArrayList<SearchResults>(originalResults);
        adapter = new CustomAdapter(this, searchResults);
        lv.setAdapter(adapter);

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState != 0) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        });
    }
}
