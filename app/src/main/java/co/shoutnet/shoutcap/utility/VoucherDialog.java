package co.shoutnet.shoutcap.utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import co.shoutnet.shoutcap.R;

/**
 * Created by mikqi on 11/24/15.
 */
public class VoucherDialog extends DialogFragment {

    private static String[] mcaps;
    private static DialogListener dialogListener;
    private String itemSelected;

    public static VoucherDialog newInstance(String[] caps,DialogListener listener) {
        mcaps = caps;
        dialogListener=listener;
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

                        itemSelected = o.toString();
                    }
                })
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText edtVoucher = (EditText) view.findViewById(R.id.edt_code_voucher);
                        if (itemSelected == null || itemSelected.equals("")) {
                            itemSelected = mcaps[0];
                        }
                        if (edtVoucher.getText() == null) {
                            Toast.makeText(getActivity(), "Insert Voucher Code or Skip", Toast.LENGTH_SHORT).show();
                        } else {
                            dialogListener.resultItemVoucher(itemSelected, edtVoucher.getText().toString());
                        }
                    }
                })
                .setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (itemSelected == null || itemSelected.equals("")) {
                            itemSelected = mcaps[0];
                        }
                        dialogListener.resultItemOnly(itemSelected);
                    }
                });
        return builder.create();
    }

    public interface DialogListener{
        void resultItemOnly(String item);
        void resultItemVoucher(String item,String voucherCode);
    }
}
