package org.squiril.hilcoe.schedule.dialogs;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.squiril.hilcoe.schedule.R;

public class ConfirmDialog extends BottomSheetDialog {

    private TextView queryTextView;
    private Button noButton;
    private Button yesButton;

    private ConfirmDialog(Context context, final Builder builder) {
        super(context);
        setDialogView();

        this.queryTextView.setText(builder.query);
        this.yesButton.setText(builder.yesButton);
        this.yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                builder.yesListener.onClick(view);
            }
        });
        this.noButton.setText(builder.noButton);
        this.noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                builder.noListener.onClick(view);
            }
        });
    }

    private void setDialogView(){
        View bottomSheetView = getLayoutInflater().inflate(R.layout.dialog_confirm_view, null);
        setContentView(bottomSheetView);

        queryTextView = findViewById(R.id.dialog_view_query_text_view);
        yesButton = findViewById(R.id.dialog_view_yes_button);
        noButton = findViewById(R.id.dialog_view_no_button);
    }

    public static class Builder{
        private Context context;
        String query;
        String noButton;
        String yesButton;
        View.OnClickListener noListener;
        View.OnClickListener yesListener;

        public Builder(Context context){
            this.context = context;
        }

        public Builder setTitle(String title){
            this.query = title;
            return this;
        }

        public Builder setPositiveButton(String text, View.OnClickListener listener){
            this.yesButton = text;
            this.yesListener = listener;
            return this;
        }

        public Builder setNegativeButton(String text, View.OnClickListener listener){
            this.noButton = text;
            this.noListener = listener;
            return this;
        }

        public ConfirmDialog build(){
            return new ConfirmDialog(context, this);
        }
    }

}
