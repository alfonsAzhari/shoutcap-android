package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentNewsDetail extends Fragment {

    private ImageView imageView;
    private TextView textTitle;
    private TextView textDate;
    private TextView textInfo;
    //ArrayList<ModelNewsDetail> list;

    public FragmentNewsDetail() {

    }

    public static FragmentNewsDetail newInstance() {

        Bundle args = new Bundle();

        FragmentNewsDetail fragment = new FragmentNewsDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_detail, container, false);

        initView(rootView);

        /*list = new Dummy().getNewsDetail();

        imageView.setImageResource(list.get(0).getImage());
        textTitle.setText(list.get(0).getTitle());
        textDate.setText(list.get(0).getDate());
        textInfo.setText(list.get(0).getInfo());*/

        return rootView;
    }

    private void initView(View v) {
        imageView = (ImageView) v.findViewById(R.id.img_news_detail);
        textTitle = (TextView) v.findViewById(R.id.txt_inbox_detail_title);
        textDate = (TextView) v.findViewById(R.id.txt_inbox_detail_date);
        textInfo = (TextView) v.findViewById(R.id.txt_news_detail_info);
    }

}
