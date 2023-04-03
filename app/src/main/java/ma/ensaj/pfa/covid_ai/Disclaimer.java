package ma.ensaj.pfa.covid_ai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class Disclaimer extends AppCompatActivity {
    private CheckBox checkBox_privacy_policy;
    private Button start_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);

        checkBox_privacy_policy = findViewById(R.id.checkBox_privacy_policy);
        start_app = findViewById(R.id.start_app);
        start_app.setEnabled(false);

        checkBox_privacy_policy.setOnClickListener(v -> {
            if (((CheckBox) v).isChecked()) {
                start_app.setEnabled(true);
            }else{
                start_app.setEnabled(false);
            }
        });

        start_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Home.class);
                startActivity(intent);
                finish();
            }
        });
    }
}