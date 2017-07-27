package com.app.elixir.TravelB2B.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.model.Person;
import com.app.elixir.TravelB2B.tokenautocomplete.TokenCompleteTextView;


/**
 * Created by elixirtechnologies on 30/05/17.
 */

public class AutoCompletionView extends TokenCompleteTextView<Person> {

    Context context;

    public AutoCompletionView(Context context, AttributeSet attrs) {

        super(context, attrs);
        this.context = context;
    }

    @Override
    protected View getViewForObject(Person person) {

        LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        TextView view = (TextView) l.inflate(R.layout.autocomplet, (ViewGroup) getParent(), false);
        Typeface face = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.fontface_DroidSerif_Bold));
        view.setTypeface(face);
        view.setText(person.getEmail());

        return view;
    }

    @Override
    protected Person defaultObject(String completionText) {
        //Stupid simple example of guessing if we have an email or not
        int index = completionText.indexOf('@');
        if (index == -1) {
            return new Person(completionText, completionText.replace(" ", ""));
        } else {
            return new Person(completionText.substring(0, index), completionText);
        }
    }
}
