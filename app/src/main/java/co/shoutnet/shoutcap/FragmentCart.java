package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.shoutnet.shoutcap.adapter.CartAdapter;

/**
 * Created by Codelabs on 9/14/2015.
 */
public class FragmentCart extends Fragment {

    private RecyclerView recyclerView;

    public FragmentCart() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        CartAdapter adapter = new CartAdapter(getActivity().getApplicationContext(), Dummy.getDataCart());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_cart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
