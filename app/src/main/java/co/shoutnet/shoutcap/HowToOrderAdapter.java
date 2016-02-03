package co.shoutnet.shoutcap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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
                return FragmentHowToOrder.newInstance(R.drawable.bg1);

            case 1:
                return FragmentHowToOrder.newInstance(R.drawable.bg2);

            case 2:
                return FragmentHowToOrder.newInstance(R.drawable.bg3);

            case 3:
                return FragmentHowToOrder.newInstance(R.drawable.bg4);
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

}
