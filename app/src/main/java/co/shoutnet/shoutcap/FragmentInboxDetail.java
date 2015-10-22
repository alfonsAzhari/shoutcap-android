package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Adam MB on 9/10/2015.
 */
public class FragmentInboxDetail extends Fragment {

    TextView title, date, content;
    //ArrayList<ModelInboxDetail> list;

    public FragmentInboxDetail() {

    }

    public static FragmentInboxDetail newInstance() {

        Bundle args = new Bundle();

        FragmentInboxDetail fragment = new FragmentInboxDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inbox_detail, container, false);

        initView(rootView);

        /*list = new Dummy().getInboxDetail();

        title.setText(list.get(0).getTitle());
        date.setText(list.get(0).getDate());
        content.setText(list.get(0).getContent());*/

        return rootView;
    }

    private void initView(View v) {
        title = (TextView) v.findViewById(R.id.txt_inbox_detail_title);
        date = (TextView) v.findViewById(R.id.txt_inbox_detail_date);
        content = (TextView) v.findViewById(R.id.txt_inbox_detail_content);
    }

}
