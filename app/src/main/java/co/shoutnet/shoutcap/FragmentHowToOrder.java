package co.shoutnet.shoutcap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FragmentHowToOrder extends Fragment {

    private static String resId = "RES_ID";
    private ImageView imgHowToOrder;

    public FragmentHowToOrder() {

    }

    public static FragmentHowToOrder newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(resId, id);
        FragmentHowToOrder fragment = new FragmentHowToOrder();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_how_to_order, container, false);

        initView(rootView);

        imgHowToOrder.setImageResource(getArguments().getInt(resId));

        return rootView;
    }

    private void initView(View v) {
        imgHowToOrder = (ImageView) v.findViewById(R.id.img_how_to_order);
    }
}
