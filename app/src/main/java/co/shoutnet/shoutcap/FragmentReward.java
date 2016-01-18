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

import co.shoutnet.shoutcap.adapter.RewardAdapter;
import co.shoutnet.shoutcap.model.ModelAdapterReward;

/**
 * Created by Codelabs on 9/2/2015.
 */
public class FragmentReward extends Fragment {

    private RecyclerView recyclerView;
    private RewardAdapter adapter;
    private ArrayList<ModelAdapterReward> rewards;

    public FragmentReward() {

    }

    public static FragmentReward newInstance() {

        Bundle args = new Bundle();

        FragmentReward fragment = new FragmentReward();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reward, container, false);

        initView(rootView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        rewards = Dummy.getDataReward();
        adapter = new RewardAdapter(getActivity(), rewards);

        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void initView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_reward);
    }
}
