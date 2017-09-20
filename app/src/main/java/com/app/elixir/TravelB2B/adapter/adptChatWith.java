package com.app.elixir.TravelB2B.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.model.pojoUserChat;
import com.app.elixir.TravelB2B.pojos.pojoCity;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 14-Sep-2017.
 */

public class adptChatWith extends ArrayAdapter<pojoUserChat> {
    Context context;
    int resource, textViewResourceId;
    List<pojoUserChat> items, tempItems, suggestions;

    public adptChatWith(Context context, int resource, int textViewResourceId, List<pojoUserChat> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<pojoUserChat>(items);
        suggestions = new ArrayList<pojoUserChat>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.conntylayout, parent, false);
        }
        pojoUserChat people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.textViewSpinner);
            if (lblName != null)
                lblName.setText(people.getUsername());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((pojoUserChat) resultValue).getUsername();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (pojoUserChat people : tempItems) {
                    if (people.getUsername().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<pojoUserChat> filterList = (ArrayList<pojoUserChat>) results.values;
            if (filterList != null) {
                if (results != null && results.count > 0) {
                    try {


                        clear();
                        for (pojoUserChat people : filterList) {
                            if (people != null) {
                                add(people);
                                notifyDataSetChanged();
                            }
                        }
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        }
    };
}
