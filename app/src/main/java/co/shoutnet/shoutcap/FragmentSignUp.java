package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Codelabs on 9/2/2015.
 */
public class FragmentSignUp extends Fragment {

    public FragmentSignUp() {

    }

    public static FragmentSignUp newInstance() {

        Bundle args = new Bundle();

        FragmentSignUp fragment = new FragmentSignUp();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        return rootView;
    }
}
