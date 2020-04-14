package ba.unsa.etf.rma.moja_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;



public class BudgetFragment extends Fragment {

    private Button right;

    private OnBudgetChange onBudgetChange;
    public interface OnBudgetChange {
        public void onRightClicked2();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.budget_fragment, container, false);
        onBudgetChange = (OnBudgetChange) getActivity();
        right = (Button)view.findViewById(R.id.right);

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBudgetChange.onRightClicked2();
            }
        });
        return view;
    }

}
