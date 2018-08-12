package com.sopan.list_search;

/**
 * Created by Sopan on 05-Apr-16.
 */
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class CustomAdapter extends BaseAdapter {

    private static ArrayList<SearchResults> searchArrayList;
    private LayoutInflater mInflater;

    public CustomAdapter(Context context, ArrayList<SearchResults> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView txtBook;
        TextView txtAuthor;
        TextView txtPublisher;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_row_view, null);
            holder = new ViewHolder();

            holder.txtBook = (TextView) convertView.findViewById(R.id.book);
            holder.txtAuthor = (TextView) convertView.findViewById(R.id.author);
            holder.txtPublisher = (TextView) convertView.findViewById(R.id.publisher);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtBook.setText(searchArrayList.get(position).getBook());
        holder.txtAuthor.setText(searchArrayList.get(position).getAuthor());
        holder.txtPublisher.setText(searchArrayList.get(position).getPublisher());

        return convertView;
    }
}
