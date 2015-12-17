package co.shoutnet.shoutcap.utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import co.shoutnet.shoutcap.R;
import co.shoutnet.shoutcap.model.Model;

/**
 * Created by mikqi on 11/24/15.
 */
public class VoucherDialog extends DialogFragment {

    private static String[] mcaps;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<Model> mModelVoucher;

    public static VoucherDialog newInstance(String[] caps) {
        mcaps = caps;
        VoucherDialog voucherDialog = new VoucherDialog();
        return voucherDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.item_voucher, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setSingleChoiceItems(mcaps, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ListView listView = ((AlertDialog) dialogInterface).getListView();
                        Object o = listView.getAdapter().getItem(listView.getCheckedItemPosition());

                        Log.i("info", String.valueOf(o));
                    }
                })
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        EditText editText = (EditText) view.findViewById(R.id.edt_code_voucher);
                        Log.i("info kode", editText.getText().toString());
                    }
                })
                .setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }
}
