package ba.unsa.etf.rma.moja_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
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
    private String mTitle = "";
    private double mAmount;
    private LocalDate date1;
    private LocalDate date2;
    private Type type_;
    private int mInterval = 0;
    private boolean dodavanje = false;
    private String mDescription = "";
    private String imageType = "";
    private double budget;
    private IListItemPresenter presenter = new ListItemPresenter(getActivity());
    private Transaction trans, original;
    private Account account;
    private IAccountPresenter accountPresenter = new AccountPresenter(getActivity());


    private OnItemChange onItemChange;
    public interface OnItemChange {
        public void onDeleteClicked(Transaction t);
        public void onAddClicked(Transaction transaction);
        public void onChangeClicked(Transaction t1, Transaction t2);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_detail, container, false);
        int orientation = getResources().getConfiguration().orientation;

        if (getArguments() != null && getArguments().containsKey("account")){
            account = getArguments().getParcelable("account");
        }

        if (getArguments() != null && getArguments().containsKey("transaction")) {
            trans = getArguments().getParcelable("transaction");
            original = trans;
        }

        else if (getArguments() != null && getArguments().containsKey("new")) {
            trans = getArguments().getParcelable("new");
            original = trans;
            dodavanje = true;
        }

        else if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            trans = new Transaction(null, null, 0, null, null, 0, null);
            original = trans;
            dodavanje = true;
        }

        else return view;


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





        if (trans.getType() != null) imageType = trans.getType().toString();

        type.setText(imageType);
        mTitle = trans.getTitle();
        mAmount = trans.getAmount();
        mDescription = trans.getItemDescription();
        if (trans.getDate() != null) date1 = trans.getDate();
        if (trans.getEndDate() != null) date2 = trans.getEndDate();


        title.setText(mTitle);
        String s = "";
        s += mAmount;
        amount.setText(s);
        s = "";
        s += trans.getTransactionInterval();
        interval.setText(s);
        description.setText(mDescription);
        if (date1 != null) date.setText(date1.toString());
        if (date2 != null) endDate.setText(date2.toString());
        else endDate.setText("");


        if (mTitle == null) {
            delete.setEnabled(false);
        }


        onItemChange = (OnItemChange) getActivity();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog2 = new AlertDialog.Builder(getActivity())
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete transaction?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onItemChange.onDeleteClicked(trans);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        })
                        .show();
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



            OK.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {

                    mTitle = title.getText().toString();
                    mDescription = description.getText().toString();
                    if (presenter.validateDate(date.getText().toString()))
                        date1 = LocalDate.parse(date.getText().toString());
                    if (presenter.validateDate(endDate.getText().toString()))
                        date2 = LocalDate.parse(endDate.getText().toString());
                    mAmount = Double.parseDouble(amount.getText().toString());
                    mInterval = Integer.parseInt(interval.getText().toString());
                    imageType = type.getText().toString();

                    if (presenter.validateDescription(mDescription, imageType) == false
                            || presenter.validateTitle(mTitle) == false
                            || presenter.validateInterval(mInterval, imageType) == false
                            || presenter.validateType(imageType) == false ||
                            date1 == null) {
                        AlertDialog alertDialog1 = new AlertDialog.Builder(getActivity())
                                .setTitle("Incorrect data")
                                .setMessage("Some fields have incorrect inputs")
                                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                    } else {


                        type_ = Type.valueOf(imageType);
                        Transaction t = new Transaction(date1, mTitle, mAmount, type_, mDescription, mInterval, date2);

                        if (account.getBudget() + mAmount < account.getTotalLimit()) {
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                                    .setTitle("Alert!")
                                    .setMessage("Are you sure you want to go over the limit?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            account.setBudget(account.getBudget() + mAmount);
                                            accountPresenter.set(account);
                                            if (dodavanje) onItemChange.onAddClicked(t);
                                            else onItemChange.onChangeClicked(original, t);

                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            amount.setText("0.0");
                                        }
                                    }).show();
                        } else {

                            if (dodavanje) onItemChange.onAddClicked(t);
                            else onItemChange.onChangeClicked(original, t);
                        }
                    }
                }
            });

            return view;
    }
}

