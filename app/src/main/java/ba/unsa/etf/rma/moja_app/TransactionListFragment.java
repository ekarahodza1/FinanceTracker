package ba.unsa.etf.rma.moja_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class TransactionListFragment extends Fragment {


        @Override
        public View onCreateView(
                LayoutInflater inflater,
                ViewGroup container,
                Bundle savedInstanceState) {
//Ovdje se dodjeljuje layout fragmentu,
//tj. ˇsta ´ce se nalaziti unutar fragmenta
//Ovu liniju ´cemo poslije promijeniti
            return inflater.inflate(R.layout.fragment_list, container, false);
        }
    }

