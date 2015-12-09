package co.shoutnet.shoutcap.utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import co.shoutnet.shoutcap.R;

/**
 * Created by mikqi on 12/1/15.
 */
public class RackItemDialog extends DialogFragment {

    private static String[] mName;
    private static RackDialogListener mListener;
    private static Bitmap image;

    public static RackItemDialog newInstance(Bitmap bitmap, RackDialogListener listener) {
        image = bitmap;
        mListener = listener;
        RackItemDialog addressDialog = new RackItemDialog();
        return addressDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_dialog_rack, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        ImageView imageView = (ImageView) view.findViewById(R.id.img_preview_rack);
        imageView.setImageBitmap(image);

        Button button = (Button) view.findViewById(R.id.btn_addtocart_rack);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.adding();
            }
        });

        return builder.create();
    }

    public interface RackDialogListener {
        void adding();
    }
}
