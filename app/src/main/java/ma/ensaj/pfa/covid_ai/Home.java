package ma.ensaj.pfa.covid_ai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {
    private CardView card_self_screening,card_sponsors_partners,card_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        card_self_screening = findViewById(R.id.card_self_screening);
        card_sponsors_partners = findViewById(R.id.card_sponsors_partners);
        card_about = findViewById(R.id.card_about);

        card_self_screening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("position",2);
                startActivity(intent);
                finish();
            }
        });

        card_sponsors_partners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("position",3);
                startActivity(intent);
                finish();
            }
        });

        card_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("position",4);
                startActivity(intent);
                finish();
            }
        });
    }
}