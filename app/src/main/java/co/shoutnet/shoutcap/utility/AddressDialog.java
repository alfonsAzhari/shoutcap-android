package co.shoutnet.shoutcap.utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by mikqi on 12/1/15.
 */
public class AddressDialog extends DialogFragment {

    private static String[] mName;
    private static AddrDialogListener mListener;
    private String itemSelected;

    public static AddressDialog newInstance(String[] name, AddrDialogListener listener) {
        mName = name;
        mListener = listener;
        AddressDialog addressDialog = new AddressDialog();
        return addressDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(mName, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ListView listView = ((AlertDialog) dialogInterface).getListView();
                Object o = listView.getAdapter().getItem(listView.getCheckedItemPosition());

                itemSelected = o.toString();

                if (itemSelected.isEmpty()) {
                    itemSelected = mName[0];
                }
                mListener.result(itemSelected);
            }
        });
        return builder.create();
    }

    public interface AddrDialogListener {
        void result(String value);
    }
}
