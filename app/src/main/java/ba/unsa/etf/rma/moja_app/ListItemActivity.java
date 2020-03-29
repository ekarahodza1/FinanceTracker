package ba.unsa.etf.rma.moja_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ListItemActivity extends AppCompatActivity {

    ImageView image;
    EditText title;
    EditText amount;
    EditText date;
    EditText endDate;
    EditText description;
    EditText interval;
    EditText type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        image = findViewById(R.id.image1);
        title = findViewById(R.id.editTitle);
        amount = findViewById(R.id.editAmount);
        date = findViewById(R.id.editDate);
        endDate = findViewById(R.id.editEndDate);
        description = findViewById(R.id.editDescription);
        interval = findViewById(R.id.editInterval);
        type = findViewById(R.id.editType);

        String imageType = getIntent().getStringExtra("type");
        if (imageType.matches("INDIVIDUALPAYMENT")) {
            image.setImageResource(R.drawable.individual_payment);
            type.setText("INDIVIDUAL PAYMENT");
        }
        if (imageType.matches("REGULARPAYMENT")) {
            image.setImageResource(R.drawable.regular_payment);
            type.setText("REGULAR PAYMENT");

        }
        if (imageType.matches("PURCHASE")) {
            image.setImageResource(R.drawable.purchase);
            type.setText("PURCHASE");
        }
        if (imageType.matches("INDIVIDUALINCOME")) {
            image.setImageResource(R.drawable.individual_income);
            type.setText("INDIVIDUAL INCOME");
        }
        if (imageType.matches("REGULARINCOME")) {
            image.setImageResource(R.drawable.regular_income);
            type.setText("REGULAR INCOME");
        }

        title.setText(getIntent().getStringExtra("title"));
        Bundle b = getIntent().getExtras();
        String s = ""; s+=b.getDouble("amount");
        amount.setText(s);
        s = ""; s += b.getInt("interval");
        interval.setText(s);
        description.setText(getIntent().getStringExtra("description"));



    }
}
