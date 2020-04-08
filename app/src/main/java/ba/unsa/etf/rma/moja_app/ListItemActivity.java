package ba.unsa.etf.rma.moja_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class ListItemActivity extends AppCompatActivity{


    // implements IListItemView{

//    private ImageView image;
//    private EditText title;
//    private EditText amount;
//    private EditText date;
//    private EditText endDate;
//    private EditText description;
//    private EditText interval;
//    private EditText type;
//    private Button delete;
//    private Button OK;
//    private String mTitle;
//    private double mAmount;
//    private LocalDate date1;
//    private LocalDate date2;
//    private Type type_;
//    private int mInterval;
//    private boolean dodavanje = false;
//    private String mDescription;
//    private String imageType;
//    private double budget;
//    private IListItemPresenter presenter = new ListItemPresenter(this, this);


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_item);
//
//        image = findViewById(R.id.image1);
//        title = findViewById(R.id.editTitle);
//        amount = findViewById(R.id.editAmount);
//        date = findViewById(R.id.editDate);
//        endDate = findViewById(R.id.editEndDate);
//        description = findViewById(R.id.editDescription);
//        interval = findViewById(R.id.editInterval);
//        type = findViewById(R.id.editType);
//        delete = (Button)findViewById(R.id.buttonDelete);
//        OK = (Button)findViewById(R.id.buttonOK);
//
//
//        imageType = getIntent().getStringExtra("type");
//
//        if (imageType.matches("INDIVIDUALPAYMENT")) {
//            image.setImageResource(R.drawable.individual_payment);
//            type.setText("INDIVIDUAL PAYMENT");
//            type_ = Type.INDIVIDUALPAYMENT;
//        }
//        if (imageType.matches("REGULARPAYMENT")) {
//            image.setImageResource(R.drawable.regular_payment);
//            type.setText("REGULAR PAYMENT");
//            type_ = Type.REGULARPAYMENT;
//
//        }
//        if (imageType.matches("PURCHASE")) {
//            image.setImageResource(R.drawable.purchase);
//            type.setText("PURCHASE");
//            type_ = Type.PURCHASE;
//        }
//        if (imageType.matches("INDIVIDUALINCOME")) {
//            image.setImageResource(R.drawable.individual_income);
//            type.setText("INDIVIDUAL INCOME");
//            type_ = Type.INDIVIDUALINCOME;
//        }
//        if (imageType.matches("REGULARINCOME")) {
//            image.setImageResource(R.drawable.regular_income);
//            type.setText("REGULAR INCOME");
//            type_ = Type.REGULARINCOME;
//        }
//
//        mTitle = getIntent().getStringExtra("title");
//        if (mTitle.matches("")) dodavanje = true;
//        Bundle b = getIntent().getExtras();
//        mAmount = b.getDouble("amount");
//        budget = getIntent().getDoubleExtra("budget", 0);
//        mDescription = getIntent().getStringExtra("description");
//        if (!getIntent().getStringExtra("date").matches("")) date1 = LocalDate.parse(getIntent().getStringExtra("date"));
//        if (!getIntent().getStringExtra("eDate").matches("")) date2 = LocalDate.parse(getIntent().getStringExtra("eDate"));
//
//
//        title.setText(getIntent().getStringExtra("title"));
//        String s = ""; s += mAmount;
//        amount.setText(s);
//        s = ""; s += b.getInt("interval");
//        interval.setText(s);
//        description.setText(mDescription);
//        date.setText(getIntent().getStringExtra("date"));
//        endDate.setText(getIntent().getStringExtra("eDate"));
//
//        if (mTitle.matches("")){
//            delete.setEnabled(false);
//        }
//
//
//
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog alertDialog2 = new AlertDialog.Builder(ListItemActivity.this)
//                        .setTitle("Delete")
//                        .setMessage("Are you sure you want to delete transaction?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                setResult(2, getIntent());
//                                finish();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                              //  alertDialog2.cancel();
//
//                            }
//                        })
//                        .show();
////                setResult(2, getIntent());
////                finish();
//            }
//
//        });
//
//        title.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//            @Override
//            public void afterTextChanged(Editable s) {
//                title.setBackgroundColor(Color.GREEN);
//            }
//        });
//
//        description.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//            @Override
//            public void afterTextChanged(Editable s) {
//                description.setBackgroundColor(Color.GREEN);
//            }
//        });
//
//        amount.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//            @Override
//            public void afterTextChanged(Editable s) {
//                amount.setBackgroundColor(Color.GREEN);
//            }
//        });
//
//        interval.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//            @Override
//            public void afterTextChanged(Editable s) {
//                interval.setBackgroundColor(Color.GREEN);
//            }
//        });
//
//        date.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                    date.setBackgroundColor(Color.GREEN);
//
//
//            }
//        });
//
//        endDate.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                    endDate.setBackgroundColor(Color.GREEN);
//
//            }
//        });
//
//        type.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//            @Override
//            public void afterTextChanged(Editable s) {
//                type.setBackgroundColor(Color.GREEN);
//            }
//        });
//
//
//       // if (!mTitle.matches(title.getText().toString())) title.setBackgroundColor(Color.GREEN);
//
//        OK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mTitle = title.getText().toString();
//                mDescription = description.getText().toString();
//                if (presenter.validateDate(date.getText().toString())) date1 = LocalDate.parse(date.getText().toString());
//                if (presenter.validateDate(endDate.getText().toString()))  date2 = LocalDate.parse(endDate.getText().toString());
//                mAmount = Double.parseDouble(amount.getText().toString());
//                mInterval = Integer.parseInt(interval.getText().toString());
//                imageType = type.getText().toString();
//                Intent result = new Intent();
//
//                if (presenter.validateDescription(mDescription, imageType) == false || presenter.validateTitle(mTitle) == false
//                || presenter.validateInterval(mInterval, imageType) == false || presenter.validateType(imageType) == false || date1 == null) {
//                    AlertDialog alertDialog1 = new AlertDialog.Builder(ListItemActivity.this)
//                            .setTitle("Incorrect data")
//                            .setMessage("Some fields have incorrect inputs")
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    setResult(RESULT_CANCELED, result);
//                                    finish();
//                                }
//                            })
//                          .show();
//
//                    setResult(RESULT_CANCELED, result);
//                    finish();
//                }
//
//
//
//                result.putExtra("title", mTitle);
//                Bundle b = new Bundle();
//                b.putDouble("amount", mAmount);
//                result.putExtras(b);
//                result.putExtra("date", date.getText().toString());
//                result.putExtra("eDate", endDate.getText().toString());
//                b.putInt("interval", mInterval);
//                result.putExtra("type", imageType);
//                result.putExtra("description", mDescription);
//
//
//
//                if (budget + mAmount < -1000) {
//                    AlertDialog alertDialog = new AlertDialog.Builder(ListItemActivity.this)
//                            .setTitle("Alert!")
//                            .setMessage("Are you sure you want to go over the limit?")
//                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    if (dodavanje) setResult(3, result);
//                                    else setResult(4, result);
//                                    finish();
//                                }
//                            })
//                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    amount.setText("0.0");
//                                }
//                            }).show();
//                }
//                else {
//
//                    if (dodavanje) setResult(3, result);
//                    else setResult(4, result);
//                    finish();
//                }
//
//
//            }
//        });
//
//
    }
}
