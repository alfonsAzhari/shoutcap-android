package co.shoutnet.shoutcap;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import co.shoutnet.shoutcap.model.ModelProfile;
import co.shoutnet.shoutcap.utility.ApiReferences;
import co.shoutnet.shoutcap.utility.ConfigGCM;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.SessionManager;

public class MainActivity extends AppCompatActivity {

    private static final int PLAY_SERVICE_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    SessionManager sessionManager;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView imgProfileAva;
    private TextView txtProfileName;
    private LinearLayout linProfile;
    private TextView txtProfileCoin;
    private TextView txtProfilePoint;
    private ImageView imgAva;
    private TextView txtShoutId;
    private TextView txtCoin;
    private TextView txtPoint;
    private ModelProfile modelProfile = null;
    private HashMap<String, String> user;
    private boolean exitCounter = false;
    private BroadcastReceiver registrationBroadcastReceiver;

    private Context mContext;
    private NavigationView.OnNavigationItemSelectedListener navItemSelect = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {

            menuItem.setChecked(true);

            drawerLayout.closeDrawer(GravityCompat.START);
            FragmentCreateShout fragmentCreateShout;
            FragmentRack fragmentRack;
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
            }

            imgProfileAva.setVisibility(View.GONE);
            txtProfileName.setVisibility(View.GONE);
            linProfile.setVisibility(View.GONE);

            switch (menuItem.getItemId()) {
//                case R.id.drawer_item_news:
//                    getSupportActionBar().setTitle("News");
//
//                    FragmentNews fragmentNews = new FragmentNews();
//                    fragmentManager.beginTransaction().replace(R.id.frame_content_main, fragmentNews).commit();
//                    return true;

//                case R.id.drawer_item_promo:
//                    getSupportActionBar().setTitle("Promo");
//
//                    FragmentPromo fragmentPromo = new FragmentPromo();
//                    fragmentManager.beginTransaction().replace(R.id.frame_content_main, fragmentPromo).commit();
//                    return true;

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

                case R.id.drawer_item_voucher:
                    getSupportActionBar().setTitle("Voucher");

                    FragmentVoucher fragmentVoucher = FragmentVoucher.newInstance();
                    fragmentManager.beginTransaction().replace(R.id.frame_content_main, fragmentVoucher).commit();
                    return true;

                default:
                    return true;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        //validate login
        sessionManager = new SessionManager(getApplicationContext());

        sessionManager.checkLogin();

        user = sessionManager.getUserDetails();

        getProfile();

        onNewIntent(getIntent());
        initGcm();
        initToolbar();
        initView();

        setUpNavDrawer();

        user = sessionManager.getUserDetails();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setUpProfile(user);
            }
        }, 5000);

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

        imgAva = (ImageView) findViewById(R.id.img_drawer_photo_user);
        txtShoutId = (TextView) findViewById(R.id.txt_drawer_shout_id);
        txtCoin = (TextView) findViewById(R.id.txt_drawer_coin);
        txtPoint = (TextView) findViewById(R.id.txt_drawer_poin);

        imgProfileAva = (ImageView) findViewById(R.id.img_profile_ava);
        txtProfileName = (TextView) findViewById(R.id.txt_profile_name);
        linProfile = (LinearLayout) findViewById(R.id.lin_profile);
        txtProfileCoin = (TextView) findViewById(R.id.txt_profile_coin);
        txtProfilePoint = (TextView) findViewById(R.id.txt_profile_point);
    }

    private void initGcm() {
        registrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                registerProgressBar.setVisibility(ProgressBar.GONE);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                boolean sentToken = sharedPreferences.getBoolean(ConfigGCM.SENT_TOKEN_TO_SERVER, false);

                if (sentToken) {
//                    information.setText(getString(R.string.gcm_send_message));
                    Toast.makeText(getApplicationContext(), "sukses", Toast.LENGTH_SHORT).show();
                } else {
//                    information.setText(getString(R.string.token_error_message));
                    Toast.makeText(getApplicationContext(), "gagal", Toast.LENGTH_SHORT).show();
                }
            }
        };

        if (checkPlayService()) {
            Intent intent = new Intent(this, RegistrationService.class);
            startService(intent);
        }
    }

    private boolean checkPlayService() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICE_RESOLUTION_REQUEST).show();
            } else {
//                Log.i(TAG, "This device is not supported");
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

    private void getProfile() {
        final StringRequest requestProfile = new StringRequest(Request.Method.POST, ApiReferences.getUrlProfile(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.i("response profile", response.toString());

                try {
                    modelProfile = Parser.getProfile(response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (modelProfile.getResult().equals("success")) {
                    sessionManager.addProfile(modelProfile.getItem().getNama(), modelProfile.getItem().getEmail(), modelProfile.getItem().getHp(),
                            modelProfile.getItem().getGender(), modelProfile.getItem().getAlamat(), modelProfile.getItem().getKecamatan(), modelProfile.getItem().getKota(), modelProfile.getItem().getProvinsi(),
                            modelProfile.getItem().getKodePos(), modelProfile.getItem().getTglLahir(), modelProfile.getItem().getMinat(), modelProfile.getItem().getStatusKerja());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e("error profile", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("shoutid", user.get(SessionManager.KEY_SHOUTID));
                params.put("sessionid", user.get(SessionManager.KEY_SESSIONID));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");

                return params;
            }
        };

        RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        requestProfile.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(mContext);

        queue.add(requestProfile);
    }

    private void setUpProfile(HashMap<String, String> user) {

        Glide.with(this).load(user.get(SessionManager.KEY_URL_AVATAR)).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgAva) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                imgAva.setImageDrawable(roundedBitmapDrawable);
            }
        });
        txtShoutId.setText(user.get(SessionManager.KEY_SHOUTID));
        txtPoint.setText(user.get(SessionManager.KEY_POINT));
        txtCoin.setText(user.get(SessionManager.KEY_COIN));

        Glide.with(this).load(user.get(SessionManager.KEY_URL_AVATAR)).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgProfileAva) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                imgProfileAva.setImageDrawable(roundedBitmapDrawable);
            }
        });
        txtProfileName.setText(user.get(SessionManager.KEY_SHOUTID));
        txtProfilePoint.setText(user.get(SessionManager.KEY_POINT));
        txtProfileCoin.setText(user.get(SessionManager.KEY_COIN));
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
        } else if (id == R.id.action_help) {
            Intent i = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(i);
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
        } else {
            if (exitCounter) {
                super.onBackPressed();
                return;
            }
            exitCounter = true;
            Toast.makeText(getApplicationContext(), "Press Back Again to Exit", Toast.LENGTH_SHORT).show();
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exitCounter = false;
                }
            }, 2500);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        user = sessionManager.getUserDetails();

        Glide.with(this).load(user.get(SessionManager.KEY_URL_AVATAR)).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgAva) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                imgAva.setImageDrawable(roundedBitmapDrawable);
            }
        });

        Glide.with(this).load(user.get(SessionManager.KEY_URL_AVATAR)).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgProfileAva) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                imgProfileAva.setImageDrawable(roundedBitmapDrawable);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        FragmentManager fragmentManager = getFragmentManager();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String point = bundle.getString("point");
            if (point.equals("reward")) {
                FragmentReward fragmentReward = new FragmentReward();
                fragmentManager.beginTransaction().replace(R.id.frame_content_main, fragmentReward).commit();
            } else if (point.equals("promo")) {
                FragmentPromo fragmentPromo = new FragmentPromo();
                fragmentManager.beginTransaction().replace(R.id.frame_content_main, fragmentPromo).commit();
            } else {
//                Log.i("Point MainActivity", "Unknown Point");
            }
        } else {
//            Log.i("Bundle MainActivity", "empty");
        }
    }
}
