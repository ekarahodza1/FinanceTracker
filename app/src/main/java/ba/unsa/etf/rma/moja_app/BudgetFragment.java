package ba.unsa.etf.rma.moja_app;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;



public class BudgetFragment extends Fragment implements GestureDetector.OnGestureListener{

    private Button ok;
    private EditText totalLimit;
    private EditText monthLimit;
    private TextView budget;
    private Account account;
    private GestureDetector gestureDetector;
    private OnBudgetChange onBudgetChange;


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent down, MotionEvent move, float X, float Y) {
        boolean value = false;
        float diffY = move.getY() - down.getY();
        float diffX = move.getX() - down.getX();
        if (Math.abs(diffX) > 100 && Math.abs(X) > 100){
            if (diffX > 0){
                onSwipeRight();
            } else {
                onSwipeLeft();
            }
            value = true;
        }
        return value;
    }

    private void onSwipeLeft() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) onBudgetChange.onRightClicked2();
    }

    private void onSwipeRight() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) onBudgetChange.onLeftClicked2();
    }


    public interface OnBudgetChange {
        public void onRightClicked2();
        public void onLeftClicked2();
        public void onChanged(Account account);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.budget_fragment, container, false);
        onBudgetChange = (OnBudgetChange) getActivity();
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

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account.setMonthLimit(Double.valueOf(String.valueOf(monthLimit.getText())));
                account.setTotalLimit(Double.valueOf(String.valueOf(totalLimit.getText())));
                onBudgetChange.onChanged(account);
            }
        });

        gestureDetector = new GestureDetector(this);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });




        return view;
    }

}
