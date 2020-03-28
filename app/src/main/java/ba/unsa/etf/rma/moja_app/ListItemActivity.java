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
//        if (imageType.matches("Individual Payment")) image.setImageResource(R.drawable.individual_payment);
//        if (imageType.matches("Regular Payment")) image.setImageResource(R.drawable.regular_payment);
//        if (imageType.matches("Purchase")) image.setImageResource(R.drawable.purchase);
//        if (imageType.matches("Individual Income")) image.setImageResource(R.drawable.individual_income);
       // if (imageType.matches("Regular Income"))
            image.setImageResource(R.drawable.regular_income);

        title.setText(getIntent().getStringExtra("title"));
        amount.setText(getIntent().getStringExtra("type"));
   //     amount.setText((int) getIntent().getDoubleExtra("amount", 0));
       // date.setText(getIntent().getStringExtra("date"));


    }
}
