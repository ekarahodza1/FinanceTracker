package ba.unsa.etf.rma.moja_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Transaction> {

    private int resource;
    public TextView titleView;
    public TextView amountView;
    public ImageView imageView;

    public ListAdapter(@NonNull Context context, int _resource, ArrayList<Transaction> items) {
        super(context, _resource,items);
        resource = _resource;
    }


    public void setTransaction(ArrayList<Transaction> transactions) {
        this.clear();
        this.addAll(transactions);
    }

    public Transaction getTransaction(int position) {return this.getItem(position);}


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LinearLayout newView;
        if (convertView == null) {
            newView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li;
            li = (LayoutInflater)getContext().
                    getSystemService(inflater);
            li.inflate(resource, newView, true);
        } else {
            newView = (LinearLayout)convertView;
        }

        Transaction t = getItem(position);

        titleView = newView.findViewById(R.id.titleView);
        amountView = newView.findViewById(R.id.amountView);
        imageView = newView.findViewById(R.id.imageView);
        titleView.setText(t.getTitle());
        amountView.setText(Double.toString(t.getAmount()));

        String type = t.getTypeString();
        try {
            if (type.matches("Individual income")) imageView.setImageResource(R.drawable.individual_income);
            if (type.matches("Regular income")) imageView.setImageResource(R.drawable.regular_income);
            if (type.matches("Individual payment")) imageView.setImageResource(R.drawable.individual_payment);
            if (type.matches("Purchase")) imageView.setImageResource(R.drawable.purchase);
            if (type.matches("Regular payment")) imageView.setImageResource(R.drawable.regular_payment);
        }
        catch (Exception e) {
            imageView.setImageResource(R.drawable.picture1);
        }

        return newView;
    }
}