package co.shoutnet.shoutcap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Codelabs on 9/3/2015.
 */
public class FragmentGetStarted extends Fragment {

    private static String resId = "RES_ID";

    private ImageView imgStarted;

    public FragmentGetStarted() {

    }

    public static FragmentGetStarted newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(resId, id);
        FragmentGetStarted fragment = new FragmentGetStarted();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_get_started, container, false);

        initView(rootView);

        imgStarted.setImageResource(getArguments().getInt(resId));

        return rootView;
    }

    private void initView(View v) {
        imgStarted = (ImageView) v.findViewById(R.id.img_get_started);
    }
}
