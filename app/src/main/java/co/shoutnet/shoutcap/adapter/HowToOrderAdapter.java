package co.shoutnet.shoutcap.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import co.shoutnet.shoutcap.FragmentHowToOrder;
import co.shoutnet.shoutcap.R;

/**
 * Created by Codelabs on 9/3/2015.
 */
public class HowToOrderAdapter extends FragmentStatePagerAdapter {

    public HowToOrderAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FragmentHowToOrder.newInstance(R.drawable.imgcreat);

            case 1:
                return FragmentHowToOrder.newInstance(R.drawable.imgpreview);

            case 2:
                return FragmentHowToOrder.newInstance(R.drawable.imgcart);

            case 3:
                return FragmentHowToOrder.newInstance(R.drawable.imgrecipient);

            case 4:
                return FragmentHowToOrder.newInstance(R.drawable.imgcheckout);
        }

        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }

}
