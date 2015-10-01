package co.shoutnet.shoutcap;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import co.shoutnet.shoutcap.utility.ConfigGCM;


public class MainActivity extends AppCompatActivity {

    private static final int PLAY_SERVICE_RESOLUTION_REQUEST=9000;
    private BroadcastReceiver registrationBroadcastReceiver;
    private static final String TAG="MainActivity";

    private Toolbar toolbar;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private ImageView imgProfileAva;
    private TextView txtProfileName;
    private LinearLayout linProfile;
    private TextView txtProfileCoin;
    private TextView txtProfilePoint;

    private int exitCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exitCounter = 1;

        onNewIntent(getIntent());
        initGcm();
        initToolbar();
        initView();

        imgProfileAva.setImageResource(R.drawable.download);
        txtProfileName.setText("Nama Orang");
        txtProfileCoin.setText("1000 Coin");
        txtProfilePoint.setText("1000 Point");

        setUpNavDrawer();

        setDefaultFragment();

        navigationView.setNavigationItemSelectedListener(navItemSelect);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
    }

    private void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_main);
        navigationView = (NavigationView) findViewById(R.id.navigation_main);

        imgProfileAva = (ImageView) findViewById(R.id.img_profile_ava);
        txtProfileName = (TextView) findViewById(R.id.txt_profile_name);
        linProfile = (LinearLayout) findViewById(R.id.lin_profile);
        txtProfileCoin = (TextView) findViewById(R.id.txt_profile_coin);
        txtProfilePoint = (TextView) findViewById(R.id.txt_profile_point);
    }
    private void initGcm() {
        registrationBroadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                registerProgressBar.setVisibility(ProgressBar.GONE);
                SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                boolean sentToken=sharedPreferences.getBoolean(ConfigGCM.SENT_TOKEN_TO_SERVER,false);

                if (sentToken){
//                    information.setText(getString(R.string.gcm_send_message));
                    Toast.makeText(getApplicationContext(), "sukses", Toast.LENGTH_SHORT).show();
                }else {
//                    information.setText(getString(R.string.token_error_message));
                    Toast.makeText(getApplicationContext(),"gagal",Toast.LENGTH_SHORT).show();
                }
            }
        };

        if (checkPlayService()){
            Intent intent =new Intent(this,RegistrationService.class);
            startService(intent);
        }
    }

    private boolean checkPlayService() {
        GoogleApiAvailability apiAvailability=GoogleApiAvailability.getInstance();
        int resultCode=apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode!= ConnectionResult.SUCCESS){
            if (apiAvailability.isUserResolvableError(resultCode)){
                apiAvailability.getErrorDialog(this,resultCode,PLAY_SERVICE_RESOLUTION_REQUEST).show();
            }else {
                Log.i(TAG, "This device is not supported");
                finish();
            }
            return false;
        }
        return true;
    }

    private void setUpNavDrawer() {
        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.mipmap.ic_menu_white_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }
    }

    private void setDefaultFragment() {
        FragmentCreateShout fragmentCreateShout = new FragmentCreateShout();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_content_main, fragmentCreateShout).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);

            return true;
        } else if (id == R.id.action_cart) {
            Intent i = new Intent(MainActivity.this, CartActivity.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else if (exitCounter > 0) {
            exitCounter -= 1;
            Toast.makeText(getApplicationContext(), "Press Back Again to Exit", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    NavigationView.OnNavigationItemSelectedListener navItemSelect = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {

            menuItem.setChecked(true);

            drawerLayout.closeDrawer(GravityCompat.START);
            FragmentCreateShout fragmentCreateShout;
            FragmentRack fragmentRack;
            FragmentManager fragmentManager = getFragmentManager();

            imgProfileAva.setVisibility(View.GONE);
            txtProfileName.setVisibility(View.GONE);
            linProfile.setVisibility(View.GONE);

            switch (menuItem.getItemId()) {
                case R.id.drawer_item_news:
                    getSupportActionBar().setTitle("News");

                    FragmentNews fragmentNews = new FragmentNews();
                    fragmentManager.beginTransaction().replace(R.id.frame_content_main, fragmentNews).commit();
                    return true;

                case R.id.drawer_item_promo:
                    getSupportActionBar().setTitle("Promo");

                    FragmentPromo fragmentPromo = new FragmentPromo();
                    fragmentManager.beginTransaction().replace(R.id.frame_content_main, fragmentPromo).commit();
                    return true;

                case R.id.drawer_item_create:
                    getSupportActionBar().setTitle("Create Shout");

                    fragmentCreateShout = new FragmentCreateShout();
                    fragmentManager.beginTransaction().replace(R.id.frame_content_main, fragmentCreateShout).commit();
                    return true;

                case R.id.drawer_item_profile:
                    imgProfileAva.setVisibility(View.VISIBLE);
                    txtProfileName.setVisibility(View.VISIBLE);
                    linProfile.setVisibility(View.VISIBLE);

                    getSupportActionBar().setTitle("Profile");

                    fragmentRack = new FragmentRack();
                    fragmentManager.beginTransaction().replace(R.id.frame_content_main, fragmentRack).commit();
                    return true;

                case R.id.drawer_item_inbox:
                    getSupportActionBar().setTitle("Inbox");

                    FragmentInbox fragmentInbox = new FragmentInbox();
                    fragmentManager.beginTransaction().replace(R.id.frame_content_main, fragmentInbox).commit();
                    return true;

                case R.id.drawer_item_rack:
                    getSupportActionBar().setTitle("Rack");

                    fragmentRack = new FragmentRack();
                    fragmentManager.beginTransaction().replace(R.id.frame_content_main, fragmentRack).commit();
                    return true;

                case R.id.drawer_item_order:
                    getSupportActionBar().setTitle("Order History");

                    FragmentOrderHistory fragmentOrderHistory = FragmentOrderHistory.newInstance();
                    fragmentManager.beginTransaction().replace(R.id.frame_content_main, fragmentOrderHistory).commit();
                    return true;

                case R.id.drawer_item_reward:
                    getSupportActionBar().setTitle("Reward");

                    FragmentReward fragmentReward = FragmentReward.newInstance();
                    fragmentManager.beginTransaction().replace(R.id.frame_content_main, fragmentReward).commit();
                    return true;

                default:
                    return true;
            }
        }
    };
    @Override
    protected void onNewIntent(Intent intent) {
        FragmentManager fragmentManager=getFragmentManager();
        Bundle bundle=intent.getExtras();
        if (bundle!=null){
            String point=bundle.getString("point");
            if (point.equals("reward")){
                FragmentReward fragmentReward=new FragmentReward();
                fragmentManager.beginTransaction().replace(R.id.frame_content_main,fragmentReward).commit();
            }else if (point.equals("promo")){
                FragmentPromo fragmentPromo=new FragmentPromo();
                fragmentManager.beginTransaction().replace(R.id.frame_content_main,fragmentPromo).commit();
            }else{
                Log.i("Point MainActivity","Unknown Point");
            }
        }else {
            Log.i("Bundle MainActivity","empty");
        }
    }
}
