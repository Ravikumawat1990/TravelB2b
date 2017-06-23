package com.app.elixir.TravelB2B.utils;

import android.text.Editable;
import android.text.Html;

import org.xml.sax.XMLReader;

/**
 * Created by NetSupport on 15-06-2017.
 */

public class UlTagHandler implements Html.TagHandler {
    @Override
    public void handleTag(boolean opening, String tag, Editable output,
                          XMLReader xmlReader) {
        if (tag.equals("ul") && !opening) output.append("\n\n\n");
        if (tag.equals("li") && opening) output.append("\n\n\t•");
    }
}
