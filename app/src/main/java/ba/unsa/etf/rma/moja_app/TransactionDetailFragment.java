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
import java.util.ArrayList;

import static android.app.Activity.RESULT_CANCELED;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TransactionDetailFragment extends Fragment implements AdapterView.OnItemSelectedListener, IFinanceView {

    private ImageView image;
    private EditText title;
    private EditText amount;
    private TextView date;
    private TextView endDate;
    private TextView connection;
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
    private Integer mInterval = 0;
    private boolean dodavanje = false;
    private String mDescription = "";
    private String imageType = "";
    private double budget;
    private boolean boja = false;
    private IListItemPresenter presenter = new ListItemPresenter(getActivity());
    private Transaction trans, original = null;
    private Account account;
    private IAccountPresenter accountPresenter = new AccountPresenter(this, getActivity());
    private DatePickerDialog.OnDateSetListener  mOnDateSetListener1;
    private DatePickerDialog.OnDateSetListener  mOnDateSetListener2;


    private OnItemChange onItemChange;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        if (pos == 0) imageType = "Individual payment";
        else if (pos == 1) imageType = "Regular payment";
        else if (pos == 2) imageType = "Purchase";
        else if (pos == 3) imageType = "Individual income";
        else if (pos == 4) imageType = "Regular income";
        if (boja) {
            type.setBackgroundColor(Color.GREEN);
        }
        boja = true;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void setTransactions(ArrayList<Transaction> transactions) {

    }

    @Override
    public void notifyTransactionsListDataSetChanged() {

    }

    @Override
    public void setAccount(Account result) {

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
            trans = new Transaction(-1, null, null, 0, -1, null, 0, null);
            original = trans;
            dodavanje = true;
        }

        else return view;


        connection = view.findViewById(R.id.connection);
        if (dodavanje && presenter.connected()) connection.setText("Online dodavanje");
        else if (dodavanje && !presenter.connected()) connection.setText("Offline dodavanje");
        else if (!dodavanje && presenter.connected()) connection.setText("Online izmjena");
        else if (!dodavanje && !presenter.connected()) connection.setText("Offline izmjena");


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

        if (trans.getTypeString() != null) {
            imageType = trans.getTypeString();
            if (imageType.matches("Individual payment")) {
                type.setSelection(0);

            }
            else if (imageType.matches("Regular payment")) {
                type.setSelection(1);

            }
            else if (imageType.matches("Purchase")) {
                type.setSelection(2);
            }
            else if (imageType.matches("Individual income")) {
                type.setSelection(3);

            }
            else if (imageType.matches("Regular income")) {
                type.setSelection(4);

            }
        }

        if (original == null) original.setAmount(0);



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
        if (!presenter.connected()) delete.setText("Offline delete");
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog2 = new AlertDialog.Builder(getActivity())
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete transaction?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (trans.getId() == 1 || trans.getId() == 3 || trans.getId() == 5) {
                                    account.setBudget(account.getBudget() + trans.getAmount());
                                }
                                else {
                                    account.setBudget(account.getBudget() - trans.getAmount());
                                }
                                accountPresenter.setAccount(account);
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
                    imageType = type.getSelectedItem().toString();
                    int id = original.getTypeId(imageType);


                    if (presenter.validateDescription(mDescription, imageType) == false
                            || presenter.validateTitle(mTitle) == false
                            || presenter.validateInterval(mInterval, imageType) == false
                            || presenter.validateDate(date1, date2, id)) {
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



                        Transaction t = new Transaction(original.getId(), date1, mTitle, mAmount, id, mDescription, mInterval, date2);

                        double newAmount;
                        if (original.getTId() != 0 && Math.abs(original.getTId() - t.getTId()) % 2 == 1) newAmount = -mAmount;

                        else newAmount = original.getAmount() - mAmount;

                        if ((account.getBudget() + newAmount < account.getTotalLimit()) && (id == 1 || id == 3 || id == 5)) {
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                                    .setTitle("Alert!")
                                    .setMessage("Are you sure you want to go over the limit?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            account.setBudget(account.getBudget() + newAmount);
                                            accountPresenter.setAccount(account);
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

                            if (id == 1 || id == 3 || id == 5) {
                                account.setBudget(account.getBudget() + newAmount);
                            }
                            else {
                                account.setBudget(account.getBudget() - newAmount);
                            }
                            accountPresenter.setAccount(account);
                            if (dodavanje) onItemChange.onAddClicked(t);
                            else onItemChange.onChangeClicked(original, t);
                        }
                    }
                }
            });

            return view;
    }
}

