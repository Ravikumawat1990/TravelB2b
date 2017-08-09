package com.app.elixir.TravelB2B.volly;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;


public class MtplProgressDialog extends Dialog {

    public MtplProgressDialog(Context context, String Message,
                              boolean isCancelable) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mtpl_progress_dialog);
        setCancelable(isCancelable);
        /*ImageView pBar = (ImageView) findViewById(R.id.dialogProgressBar);

        Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.scale);
        a.setDuration(1000);
        pBar.startAnimation(a);

        a.setInterpolator(new Interpolator() {
            private final int frameCount = 8;

            @Override
            public float getInterpolation(float input) {
                return (float) Math.floor(input * frameCount) / frameCount;
            }
        });
        TextView txtMsg = (TextView) findViewById(R.id.mtpl_customdialog_txtMessage);

        txtMsg.setText(Message);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);*/
        ProgressBar pBar = (ProgressBar) findViewById(R.id.dialogProgressBar);

        TextView txtMsg = (TextView) findViewById(R.id.mtpl_customdialog_txtMessage);

        txtMsg.setText(Message);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

}
