package com.tx.practice.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tx.practice.R;

/**
 * Created by Taxi on 2017/1/20.
 */

public class GameDialog extends Dialog implements View.OnClickListener {
    public GameDialog(Context context) {
        this(context, R.style.GameDialog);
    }

    public GameDialog(Context context, int themeResId) {
        super(context, themeResId);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_game);

        Button btnExit = (Button) findViewById(R.id.btn_finish);
        Button btnRestart = (Button) findViewById(R.id.btn_restart);

        btnExit.setOnClickListener(this);
        btnRestart.setOnClickListener(this);

        //TODO tvTile set score

    }

    @Override
    public void onClick(View v) {
        if (listener == null) {
            dismiss();
            return;
        }

        //TODO 延迟一秒
        switch (v.getId()) {
            case R.id.btn_finish:
                listener.onExitClick();
                break;

            case R.id.btn_restart:
                listener.onRestartClick();
                break;
        }
        dismiss();
    }


    public interface onButtonClickListener {
        void onExitClick();

        void onRestartClick();
    }

    private onButtonClickListener listener;

    public void setOnButtonClickListener(onButtonClickListener listener) {
        this.listener = listener;
    }

}
