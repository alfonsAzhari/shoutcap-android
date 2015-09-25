package co.shoutnet.shoutcap;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.shoutnet.shoutcap.adapter.NewsAdapter;

/**
 * Created by Codelabs on 8/24/2015.
 */
public class FragmentNews extends android.app.Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    public FragmentNews() {
    }

    public static FragmentNews newInstance() {

        Bundle args = new Bundle();

        FragmentNews fragment = new FragmentNews();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_news);
        recyclerView.setHasFixedSize(true);
//
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        NewsAdapter adapter = new NewsAdapter(getActivity(), Dummy.getDataNews());
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}
