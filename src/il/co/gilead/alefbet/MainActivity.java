package il.co.gilead.alefbet;

import il.co.gilead.alefbet.LocalService.LocalBinder;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class MainActivity extends SherlockFragmentActivity {
	private static final String TAG = "AlefBet";
	public static ViewPager mViewPager;
	public static TabsAdapter mTabsAdapter;
	public static int intNumOfPages;
	List<String> pages;
    public static LocalService mService;
    public static boolean mBound = false;
    public static Typeface typeFace;
    private int pos;
    public static int font;
	private InterstitialAd mInterstitialAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null){
			font = savedInstanceState.getInt("font");
		}
		// mobile core init
//		mActivity = this;
//		MobileCore.init(mActivity, "2JQPSK2CXD4E9B3E5UQWR3CU9SNT4", LOG_TYPE.PRODUCTION, AD_UNITS.OFFERWALL);

//		setContentView(R.layout.activity_main);
//		MobileCore.getSlider().setContentViewWithSlider(this, R.layout.activity_main, R.raw.slider_1);

		pages = initListOfLetters();
        intNumOfPages = pages.size();
        mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.pager);
		setContentView(mViewPager);
        addTabs(21);

        switch (font) {
        case 0:
            typeFace=Typeface.createFromAsset(getAssets(), "hayim.ttf");
            break;
        case 1:
            typeFace=Typeface.createFromAsset(getAssets(), "KtavYadCLM-BoldItalic.otf");
            break;
        }
//        typeFace=Typeface.createFromAsset(getAssets(), "hayim.ttf");
		setVolumeControlStream(AudioManager.STREAM_MUSIC);	
	}

	private void addTabs(int pos) {
        mTabsAdapter = new TabsAdapter(this, mViewPager);
        mTabsAdapter.notifyDataSetChanged();
		final ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.removeAllTabs();
		for (int i=0; i<intNumOfPages; i++)
		{
	        Bundle args = new Bundle();
	        args.putString("letter", pages.get(i));
	        args.putInt("index", i);
	        mTabsAdapter.addTab(bar.newTab().setText(pages.get(i)), LetterFragment.class, args);
		}
		bar.setSelectedNavigationItem(pos);
		mViewPager.setCurrentItem(pos);
	}

	@Override
    protected void onStart() {
    	super.onStart();
//    	Bind to LocalService
    	Intent intent = new Intent(this, LocalService.class);
    	startService(intent);
    	bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    	loadInterstitial();
    }

	@Override
	protected void onStop() {
		super.onStop();
//		Unbind from the service
		if (mBound) {
			mService.timer.start();
			unbindService(mConnection);
			mBound = false;
		}
	}
    
    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
        	LocalBinder binder = (LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    private List<String> initListOfLetters(){
		List<String> pages = new ArrayList<String>();
		pages.add("ú");
		pages.add("ù");
		pages.add("ø");
		pages.add("÷");
		pages.add("ö");
		pages.add("ô");
		pages.add("ò");
		pages.add("ñ");
		pages.add("ð");
		pages.add("î");
		pages.add("ì");
		pages.add("ë");
		pages.add("é");
		pages.add("è");
		pages.add("ç");
		pages.add("æ");
		pages.add("å");
		pages.add("ä");
		pages.add("ã");
		pages.add("â");
		pages.add("á");
		pages.add("à");
		return pages;
	}

    @Override
    public void onBackPressed() {
    	showInterstitial();
		super.onBackPressed();
//	MobileCore.showOfferWall(mActivity, new CallbackResponse() {
//	    @Override
//	    public void onConfirmation(TYPE arg0) {
//	    	mActivity.finish();
//	    }
//	});
    }
    
	private void loadInterstitial() {
        // Create the InterstitialAd and set the adUnitId.
        mInterstitialAd = new InterstitialAd(this);
        // Defined in values/setup.xml
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_unit_id));
        // Load the ad.
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
	}

	private void showInterstitial() {
        // Show the ad if it's ready. 
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d(TAG, "Interstitial Ad did not load");
        }
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, R.id.print, 100, getString(R.string.print));
		menu.add(1, R.id.handwriting, 100, getString(R.string.handwriting));
		menu.add(1, R.id.license, 900, getString(R.string.license));
//		menu.add(1, R.id.action_settings, 100, getString(R.string.action_settings));
		menu.add(1, R.id.exit, 999, getString(R.string.exit));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
    	case R.id.print:
    		typeFace=Typeface.createFromAsset(getAssets(), "hayim.ttf");
    		font = 0;
    		pos = mViewPager.getCurrentItem();
    		addTabs(pos);
    		return true;
    	case R.id.handwriting:
    		typeFace=Typeface.createFromAsset(getAssets(), "KtavYadCLM-BoldItalic.otf");
    		font = 1;
    		pos = mViewPager.getCurrentItem();
    		addTabs(pos);
    		return true;
    	case R.id.license:
    		startActivity(new Intent(getApplicationContext(), License.class));
    		return true;
//    	case R.id.action_settings:
//    		startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
//    		return true;
    	case R.id.exit:
    		onBackPressed();
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }

    // Some lifecycle callbacks so that the image can survive orientation change
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("font", font);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		font = savedInstanceState.getInt("font");
		super.onRestoreInstanceState(savedInstanceState);
	}
}
