package ba.unsa.etf.rma.moja_app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;

import static android.app.Activity.RESULT_CANCELED;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TransactionDetailFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private ImageView image;
    private EditText title;
    private EditText amount;
    private TextView date;
    private TextView endDate;
    private EditText description;
    private EditText interval;
    private Spinner type;
    private Button delete;
    private Button OK;
    private String mTitle = "";
    private double mAmount;
    private LocalDate date1;
    private LocalDate date2;
    private LocalDate current;
    private Type type_;
    private Integer mInterval = 0;
    private boolean dodavanje = false;
    private String mDescription = "";
    private String imageType = "";
    private double budget;
    private boolean boja = false;
    private IListItemPresenter presenter = new ListItemPresenter(getActivity());
    private Transaction trans, original;
    private Account account;
    private IAccountPresenter accountPresenter = new AccountPresenter(getActivity());
    private DatePickerDialog.OnDateSetListener  mOnDateSetListener1;
    private DatePickerDialog.OnDateSetListener  mOnDateSetListener2;


    private OnItemChange onItemChange;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        if (pos == 0) type_ = Type.INDIVIDUALPAYMENT;
        else if (pos == 1) type_ = Type.REGULARPAYMENT;
        else if (pos == 2) type_ = Type.PURCHASE;
        else if (pos == 3) type_ = Type.INDIVIDUALINCOME;
        else if (pos == 4) type_ = Type.REGULARINCOME;
        if (boja) {
            type.setBackgroundColor(Color.GREEN);
        }
        boja = true;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

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


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.type, android.R.layout.simple_spinner_item);

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


        type.setAdapter(adapter);
        type.setOnItemSelectedListener(this);

            current = LocalDate.now();

        if (trans.getType() != null) {
            imageType = trans.getType().toString();
            if (trans.getType() == Type.INDIVIDUALPAYMENT) {
                type.setSelection(0);

            }
            else if (trans.getType() == Type.REGULARPAYMENT) {
                type.setSelection(1);

            }
            else if (trans.getType() == Type.PURCHASE) {
                type.setSelection(2);
            }
            else if (trans.getType() == Type.INDIVIDUALINCOME) {
                type.setSelection(3);

            }
            else if (trans.getType() == Type.REGULARINCOME) {
                type.setSelection(4);

            }
            type_ = trans.getType();
        }




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
        else date.setText("No date, click to enter");
        if (date2 != null) endDate.setText(date2.toString());
        else endDate.setText("No end date, click to enter");



        if (mTitle == null) {
            delete.setEnabled(false);
        }

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date1 != null) {
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            mOnDateSetListener1, date1.getYear(), date1.getMonthValue() - 1, date1.getDayOfMonth());
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
                else {
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            mOnDateSetListener1, current.getYear(), current.getMonthValue() - 1, current.getDayOfMonth());
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            }
        });

        mOnDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                LocalDate d = LocalDate.of(year, month+1, dayOfMonth);
                date1 = d;
                date.setText(date1.toString());
            }
        };

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date2 != null) {
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            mOnDateSetListener2, date2.getYear(), date2.getMonthValue()-1, date2.getDayOfMonth());
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
                else {
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            mOnDateSetListener2, current.getYear(), current.getMonthValue() - 1, current.getDayOfMonth());
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }

            }
        });

        mOnDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                LocalDate d = LocalDate.of(year, month+1, dayOfMonth);
                date2 = d;
                endDate.setText(date2.toString());
            }
        };




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


            OK.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {

                    mTitle = title.getText().toString();
                    mDescription = description.getText().toString();
                    mAmount = Double.parseDouble(amount.getText().toString());
                    mInterval = Integer.parseInt(interval.getText().toString());
                    if (type_ != null) imageType = type_.toString();
                    else imageType = null;

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

