package ba.unsa.etf.rma.moja_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

public class ListItemActivity extends AppCompatActivity implements IListItemView{

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
    private String mTitle;
    private double mAmount;
    private LocalDate date1;
    private LocalDate date2;
    private Type type_;
    private int mInterval;
    private String mDescription;
    private String imageType;
    private IListItemPresenter presenter = new ListItemPresenter(this, this);
    public IListItemPresenter getPresenter() {
        if (presenter == null) {
            presenter = new ListItemPresenter(this, this);
        }
        return presenter;
    }





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


        imageType = getIntent().getStringExtra("type");

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

        mTitle = getIntent().getStringExtra("title");
        Bundle b = getIntent().getExtras();
        mAmount = b.getDouble("amount");
        mDescription = getIntent().getStringExtra("description");
        if (!getIntent().getStringExtra("date").matches("")) date1 = LocalDate.parse(getIntent().getStringExtra("date"));
        if (!getIntent().getStringExtra("eDate").matches("")) date2 = LocalDate.parse(getIntent().getStringExtra("eDate"));


        title.setText(getIntent().getStringExtra("title"));
        String s = ""; s += mAmount;
        amount.setText(s);
        s = ""; s += b.getInt("interval");
        interval.setText(s);
        description.setText(mDescription);
        date.setText(getIntent().getStringExtra("date"));
        endDate.setText(getIntent().getStringExtra("eDate"));

        if (mTitle.matches("")){
            delete.setEnabled(false);
        }



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(2, getIntent());
                finish();
            }

        });

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                title.setBackgroundColor(Color.GREEN);
            }
        });

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                description.setBackgroundColor(Color.GREEN);
            }
        });

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                amount.setBackgroundColor(Color.GREEN);
            }
        });

        interval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                interval.setBackgroundColor(Color.GREEN);
            }
        });

        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                date.setBackgroundColor(Color.GREEN);
            }
        });

        endDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                endDate.setBackgroundColor(Color.GREEN);
            }
        });

        type.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                type.setBackgroundColor(Color.GREEN);
            }
        });


       // if (!mTitle.matches(title.getText().toString())) title.setBackgroundColor(Color.GREEN);

        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTitle = title.getText().toString();
//                if (!presenter.validateTitle(mTitle))  title.setBackgroundColor(Color.RED);
//                if (!presenter.validateDescription(mDescription, imageType)) description.setBackgroundColor(Color.RED);

            }
        });


    }
}
