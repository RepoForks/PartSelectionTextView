package com.thepacific.partselectiontextview;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {
    private boolean isAllowSelect = false;
    private Button button;
    private SelectionTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.btn_state);
        textView = (SelectionTextView) findViewById(R.id.text_view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllowSelect = !isAllowSelect;
                textView.setAllowSelectText(isAllowSelect);
                if(isAllowSelect){
                    button.setText("disable selection !");
                }else{
                    button.setText("enable selection !");
                }
            }
        });
    }
}
