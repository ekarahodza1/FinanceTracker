package ba.unsa.etf.rma.moja_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;



public class BudgetFragment extends Fragment {

    private Button right;
    private Button ok;
    private EditText totalLimit;
    private EditText monthLimit;
    private TextView budget;
    private Account account;

    private OnBudgetChange onBudgetChange;
    public interface OnBudgetChange {
        public void onRightClicked2();
        public void onChanged(Account account);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.budget_fragment, container, false);
        onBudgetChange = (OnBudgetChange) getActivity();
        right = (Button)view.findViewById(R.id.right);
        ok = (Button)view.findViewById(R.id.ok);
        totalLimit = view.findViewById(R.id.totalLimit);
        monthLimit = view.findViewById(R.id.monthLimit);
        budget = view.findViewById(R.id.budgetView);

        if (getArguments() != null && getArguments().containsKey("budget")) {
            account = getArguments().getParcelable("budget");
        }
        else return view;

        String result = String.valueOf(account.getBudget());
        budget.setText(result);

        result = String.valueOf(account.getMonthLimit());
        monthLimit.setText(result);

        result = String.valueOf(account.getTotalLimit());
        totalLimit.setText(result);




        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBudgetChange.onRightClicked2();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account.setMonthLimit(Double.valueOf(String.valueOf(monthLimit.getText())));
                account.setTotalLimit(Double.valueOf(String.valueOf(totalLimit.getText())));
                onBudgetChange.onChanged(account);
            }
        });




        return view;
    }

}
