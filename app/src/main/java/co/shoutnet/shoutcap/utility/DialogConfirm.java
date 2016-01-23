package co.shoutnet.shoutcap.utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import co.shoutnet.shoutcap.R;

/**
 * Created by codelabs on 1/22/16.
 */
public class DialogConfirm extends DialogFragment {

    private static ConfirmDialogListener mListener;

    public static DialogConfirm newInstance(ConfirmDialogListener listener) {
        mListener = listener;
        DialogConfirm fragmentConfirm = new DialogConfirm();
        return fragmentConfirm;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_confirm, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        Button btnYes = (Button) view.findViewById(R.id.btn_yes_confirm);
        Button btnNo = (Button) view.findViewById(R.id.btn_no_confirm);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onYesAction();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onNoAction();
            }
        });
        return builder.create();
    }

    public interface ConfirmDialogListener {
        void onYesAction();

        void onNoAction();
    }
}
