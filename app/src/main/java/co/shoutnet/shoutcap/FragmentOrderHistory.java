package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Codelabs on 9/2/2015.
 */
public class FragmentOrderHistory extends Fragment {

    public FragmentOrderHistory() {

    }

    public static FragmentOrderHistory newInstance() {

        Bundle args = new Bundle();

        FragmentOrderHistory fragment = new FragmentOrderHistory();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order_history, container, false);

        return rootView;
    }
}
