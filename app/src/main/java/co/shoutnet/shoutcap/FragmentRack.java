package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.shoutnet.shoutcap.adapter.RackAdapter;

/**
 * Created by Codelabs on 9/2/2015.
 */
public class FragmentRack extends Fragment {

    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    public FragmentRack() {

    }

    public static FragmentRack newInstance() {

        Bundle args = new Bundle();

        FragmentRack fragment = new FragmentRack();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rack, container, false);

        layoutManager=new GridLayoutManager(getActivity().getApplicationContext(),3);
        RackAdapter adapter=new RackAdapter(getActivity().getApplicationContext(),Dummy.getDataRack());

        recyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerview_rack);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
