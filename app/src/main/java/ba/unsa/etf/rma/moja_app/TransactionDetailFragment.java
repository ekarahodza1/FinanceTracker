package ba.unsa.etf.rma.moja_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;

import static android.app.Activity.RESULT_CANCELED;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TransactionDetailFragment extends Fragment {

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
    private boolean dodavanje = false;
    private String mDescription;
    private String imageType;
    private double budget;
    private IListItemPresenter presenter = new ListItemPresenter(getActivity());
    private Transaction trans;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_detail, container, false);

        if (getArguments() != null && getArguments().containsKey("transaction")) {
            trans = getArguments().getParcelable("transaction");


            image = view.findViewById(R.id.image1);
            title = view.findViewById(R.id.editTitle);
            amount = view.findViewById(R.id.editAmount);
            date = view.findViewById(R.id.editDate);
            endDate = view.findViewById(R.id.editEndDate);
            description = view.findViewById(R.id.editDescription);
            interval = view.findViewById(R.id.editInterval);
            type = view.findViewById(R.id.editType);
            delete = (Button) view.findViewById(R.id.buttonDelete);
            OK = (Button) view.findViewById(R.id.buttonOK);


//        imageType = trans.getType().toString();
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

            mTitle = trans.getTitle();
            if (mTitle.matches("")) dodavanje = true;

            mAmount = trans.getAmount();
            budget = 600;                    //skontat kako slat budzet
            mDescription = trans.getItemDescription();
            if (!trans.getDate().toString().matches("")) date1 = trans.getDate();
            if (!trans.getEndDate().toString().matches("")) date2 = trans.getEndDate();


            type.setText("REGULAR INCOME");

            title.setText(mTitle);
            String s = "";
            s += mAmount;
            amount.setText(s);
            s = "";
            s += trans.getTransactionInterval();
            interval.setText(s);
            description.setText(mDescription);
            date.setText(date1.toString());
            endDate.setText(date2.toString());

            if (mTitle.matches("")) {
                delete.setEnabled(false);
            }


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                AlertDialog alertDialog2 = new AlertDialog.Builder(getActivity())
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

                }

            });

            title.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    title.setBackgroundColor(Color.GREEN);
                }
            });

            description.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    description.setBackgroundColor(Color.GREEN);
                }
            });

            amount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    amount.setBackgroundColor(Color.GREEN);
                }
            });

            interval.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    interval.setBackgroundColor(Color.GREEN);
                }
            });

            date.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {

                    date.setBackgroundColor(Color.GREEN);


                }
            });

            endDate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {

                    endDate.setBackgroundColor(Color.GREEN);

                }
            });

            type.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    type.setBackgroundColor(Color.GREEN);
                }
            });


            // if (!mTitle.matches(title.getText().toString())) title.setBackgroundColor(Color.GREEN);

            OK.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {

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
//                    AlertDialog alertDialog1 = new AlertDialog.Builder(getActivity())
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
//                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
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


                }
            });


        }

            return view;

    }

}

