package com.github.alexgreench.pattern_builder;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    DataDevice dataDevice;
    DataDevice.Builder builder;
    int currentColor;
    private LinearLayout ll_stage1, ll_stage2, ll_stage3, result;
    private Button btn;
    private ImageView img;
    private Spinner spinner;
    private FrameLayout fr_color;
    private TextView name, result_name;
    private int stage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        builder = new DataDevice.Builder();

        ll_stage1 = findViewById(R.id.stage1);
        ll_stage2 = findViewById(R.id.stage2);
        ll_stage3 = findViewById(R.id.stage3);
        result = findViewById(R.id.result);
        btn = findViewById(R.id.btn);
        img = findViewById(R.id.result_img);
        spinner = findViewById(R.id.type);
        fr_color = findViewById(R.id.color);
        name = findViewById(R.id.input_text);
        result_name = findViewById(R.id.result_name);

        btn.setOnClickListener(v -> complete());
        fr_color.setOnClickListener(v -> show_dialog());
    }

    private void complete() {
        switch (stage) {
            case 1:
                ll_stage1.setVisibility(View.GONE);
                ll_stage2.setVisibility(View.VISIBLE);
                stage = 2;
                currentColor = ContextCompat.getColor(this, android.R.color.black);

                builder.setType(spinner.getSelectedItemPosition());
                break;
            case 2:
                ll_stage2.setVisibility(View.GONE);
                ll_stage3.setVisibility(View.VISIBLE);
                name.setText("");
                stage = 3;
                builder.setColor(currentColor);

                btn.setText("Генерируем устройство");
                break;
            case 3:
                ll_stage3.setVisibility(View.GONE);
                result.setVisibility(View.VISIBLE);
                stage = 4;

                builder.setName(name.getText().toString());

                dataDevice = builder.create();
                switch (dataDevice.getType()) {
                    case 0:
                        img.setImageResource(R.drawable.ic_smartphone);
                        break;
                    case 1:
                        img.setImageResource(R.drawable.ic_tablet);
                        break;
                    case 2:
                        img.setImageResource(R.drawable.ic_tv);
                        break;
                    case 3:
                        img.setImageResource(R.drawable.ic_smartwatch);
                        break;
                }
                DrawableCompat.setTint(img.getDrawable(), dataDevice.getColor());

                result_name.setText(dataDevice.getName());

                btn.setText("Начать заново");
                break;
            case 4:
                builder = new DataDevice.Builder();

                result.setVisibility(View.GONE);
                ll_stage1.setVisibility(View.VISIBLE);
                stage = 1;
                btn.setText("Далее");
                break;
        }
    }


    void show_dialog() {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, currentColor, false,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        currentColor = color;
                        fr_color.setBackgroundColor(color);
                    }

                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        // cancel was selected by the user
                    }
                });
        dialog.show();
    }
}
