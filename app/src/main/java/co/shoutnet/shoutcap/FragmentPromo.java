package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.shoutnet.shoutcap.adapter.RackAdapter;

/**
 * Created by Codelabs on 9/14/2015.
 */
public class FragmentPromo extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    public FragmentPromo() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_promo,container,false);

        layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
        RackAdapter adapter=new RackAdapter(getActivity().getApplicationContext(),Dummy.getDataRack());

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview_promo);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
