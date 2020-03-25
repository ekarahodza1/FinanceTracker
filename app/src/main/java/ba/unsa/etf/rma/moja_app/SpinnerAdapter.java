package ba.unsa.etf.rma.moja_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<Transaction> {

    public SpinnerAdapter(@NonNull Context context, ArrayList<Transaction> list) {
        super(context, 0,  list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView (int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_row, parent, false
            );
        }
        ImageView icon = convertView.findViewById(R.id.image_view);
        TextView typeTransaction = convertView.findViewById(R.id.text_view_name);

        Transaction currentItem = (Transaction) getItem(position);

        if (currentItem != null) {
            icon.setImageResource(currentItem.getImage());
            typeTransaction.setText(currentItem.getTypeString());
        }
        return convertView;
    }
}
