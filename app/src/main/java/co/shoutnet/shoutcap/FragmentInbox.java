package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import co.shoutnet.shoutcap.adapter.InboxAdapter;
import co.shoutnet.shoutcap.model.ModelAdapterInbox;

/**
 * Created by Codelabs on 8/26/2015.
 */
public class FragmentInbox extends Fragment {

    private RecyclerView recyclerView;
    private InboxAdapter adapter;
    private ArrayList<ModelAdapterInbox> inboxes;

    public FragmentInbox() {

    }

    public static FragmentInbox newInstance() {

        Bundle args = new Bundle();

        FragmentInbox fragment = new FragmentInbox();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inbox, container, false);

        initView(rootView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        inboxes = Dummy.getDataInbox();
        adapter = new InboxAdapter(getActivity(), inboxes);

        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void initView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_inbox);
    }
}
