package co.shoutnet.shoutcap.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import co.shoutnet.shoutcap.FragmentGetStarted;
import co.shoutnet.shoutcap.R;

/**
 * Created by Codelabs on 9/3/2015.
 */
public class GetStartedAdapter extends FragmentStatePagerAdapter {

    public GetStartedAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return FragmentGetStarted.newInstance(R.drawable.bg1);

            case 1:
                return FragmentGetStarted.newInstance(R.drawable.bg2);

            case 2:
                return FragmentGetStarted.newInstance(R.drawable.bg3);

            case 3:
                return FragmentGetStarted.newInstance(R.drawable.bg4);
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
