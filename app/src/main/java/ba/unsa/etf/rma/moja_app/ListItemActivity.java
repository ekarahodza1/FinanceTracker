package ba.unsa.etf.rma.moja_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

public class ListItemActivity extends AppCompatActivity {

    private ImageView image;
    private EditText title;
    private EditText amount;
    private EditText date;
    private EditText endDate;
    private EditText description;
    private EditText interval;
    private EditText type;
    private Button delete;
    private Button OK;
    private Type type_;
    private LocalDate date1;
    private LocalDate date2;




    @RequiresApi(api = Build.VERSION_CODES.O)
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
        delete = (Button)findViewById(R.id.buttonDelete);
        OK = (Button)findViewById(R.id.buttonOK);


        String imageType = getIntent().getStringExtra("type");

        if (imageType.matches("INDIVIDUALPAYMENT")) {
            image.setImageResource(R.drawable.individual_payment);
            type.setText("INDIVIDUAL PAYMENT");
            type_ = Type.INDIVIDUALPAYMENT;
        }
        if (imageType.matches("REGULARPAYMENT")) {
            image.setImageResource(R.drawable.regular_payment);
            type.setText("REGULAR PAYMENT");
            type_ = Type.REGULARPAYMENT;

        }
        if (imageType.matches("PURCHASE")) {
            image.setImageResource(R.drawable.purchase);
            type.setText("PURCHASE");
            type_ = Type.PURCHASE;
        }
        if (imageType.matches("INDIVIDUALINCOME")) {
            image.setImageResource(R.drawable.individual_income);
            type.setText("INDIVIDUAL INCOME");
            type_ = Type.INDIVIDUALINCOME;
        }
        if (imageType.matches("REGULARINCOME")) {
            image.setImageResource(R.drawable.regular_income);
            type.setText("REGULAR INCOME");
            type_ = Type.REGULARINCOME;
        }

        title.setText(getIntent().getStringExtra("title"));
        Bundle b = getIntent().getExtras();
        String s = ""; s+=b.getDouble("amount");
        amount.setText(s);
        s = ""; s += b.getInt("interval");
        interval.setText(s);
        description.setText(getIntent().getStringExtra("description"));
        date.setText(getIntent().getStringExtra("date"));
        endDate.setText(getIntent().getStringExtra("eDate"));



        if (!getIntent().getStringExtra("date").matches("")) date1 = LocalDate.parse(getIntent().getStringExtra("date"));
        if (!getIntent().getStringExtra("eDate").matches("")) date2 = LocalDate.parse(getIntent().getStringExtra("eDate"));

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transaction t = null;
                t = new Transaction(date1, getIntent().getStringExtra("title"), b.getDouble("amount"),
                        type_, getIntent().getStringExtra("description"), b.getInt("interval"), date2);

                setResult(2, getIntent());


                finish();
            }

        });


    }
}
