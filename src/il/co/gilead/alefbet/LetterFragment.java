package il.co.gilead.alefbet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class LetterFragment extends SherlockFragment {
	private String letter;
	private int index;
	private List<Integer> colors;
	private AdView mAdView;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null){
        	letter = args.getString("letter");
        	index = args.getInt("index");
        }else
        	letter = "א";
        colors = initListOfColors();
    }

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_letter, container, false);

//	    Letter
    	Collections.shuffle(colors);
//    	TextFitTextView txtLetter = (TextFitTextView) v.findViewById(R.id.txtLetter);
//    	txtLetter.setFitTextToBox(true);
//    	AutoResizeTextView txtLetter = (AutoResizeTextView) v.findViewById(R.id.txtLetter);
    	TextView txtLetter = (TextView) v.findViewById(R.id.txtLetter);
    	txtLetter.setTextColor(colors.get(1));
//      txtLetter.setGravity(Gravity.CENTER);
    	txtLetter.setTypeface(MainActivity.typeFace);
    	txtLetter.setText(letter);
    	if (MainActivity.font == 1){
    		DisplayMetrics metrics = new DisplayMetrics();
    		getSherlockActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
    		Float currentTextSize = getResources().getDimension(R.dimen.textSize);
    		Float desiredTextSize = currentTextSize / metrics.density * 0.6f;
    		txtLetter.setTextSize(TypedValue.COMPLEX_UNIT_DIP, desiredTextSize);
    	}

//	    Animal illustration
    	ImageView iv = (ImageView) v.findViewById(R.id.imgAnimal);
    	Glide.with(this).load(getAnimal(index)).into(iv);
	    
//	    Animal name
	    Collections.shuffle(colors);
	    TextView txtAnimal = (TextView) v.findViewById(R.id.txtAnimal);
	    txtAnimal.setTextColor(colors.get(1));
	    txtAnimal.setTypeface(MainActivity.typeFace);
	    txtAnimal.setText(" " + getAnimalCaption(index));

	    RelativeLayout rlLetterFrag = (RelativeLayout) v.findViewById(R.id.rlLetterFrag);
	    rlLetterFrag.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (MainActivity.mBound)
					if (MainActivity.mService.finishedLoading)
						MainActivity.mService.audioPlay(index);
			}
		});
	    if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.FROYO){
	    	mAdView = (AdView) v.findViewById(R.id.ad);
	    	mAdView.loadAd(new AdRequest.Builder().build());
	    }
	    return v;
	}

	@Override
	public void onPause() {
		if (mAdView != null)
			mAdView.pause();
		super.onPause();
	}

	@Override
	public void onResume() {
        super.onResume();
		if (mAdView != null)
			mAdView.resume();
    }

	@Override
	public void onDestroy() {
		if (mAdView != null)
			mAdView.destroy();
		super.onDestroy();
	}

	private List<Integer> initListOfColors(){
		List<Integer> colors = new ArrayList<Integer>();
		colors.add(Color.BLUE);
		colors.add(Color.CYAN);
		colors.add(Color.GREEN);
		colors.add(Color.RED);
		colors.add(Color.MAGENTA);
//		colors.add(Color.YELLOW);
		colors.add(Color.rgb(235, 220, 12));
		colors.add(Color.rgb(250, 119, 5));
		colors.add(Color.rgb(185, 27, 224));
		colors.add(Color.rgb(27, 224, 30));
		colors.add(Color.rgb(224, 129, 27));
		colors.add(Color.rgb(27, 224, 224));
		colors.add(Color.rgb(36, 128, 18));
		colors.add(Color.rgb(224, 27, 93));
		return colors;
	}

	private Integer getAnimal(int index) {
		switch (index) {
		case 0:  return R.drawable.i21_crocodile;
		case 1:  return R.drawable.i20_chimpanzee;
		case 2:  return R.drawable.i19_oryx;
		case 3:  return R.drawable.i18_rhino;
		case 4:  return R.drawable.i17_turtle;
		case 5:  return R.drawable.i16_elephant;
		case 6:  return R.drawable.i15_goat;
		case 7:  return R.drawable.i14_horse;
		case 8:  return R.drawable.i13_leopard;
		case 9:  return R.drawable.i12_jellyfish;
		case 10: return R.drawable.i11_lizard;
		case 11: return R.drawable.i10_sheep;
		case 12: return R.drawable.i09_ostrich;
		case 13: return R.drawable.i08_peacock;
		case 14: return R.drawable.i07_cat;
		case 15: return R.drawable.i06_zebra;
		case 16: return R.drawable.i05_wallaby;
		case 17: return R.drawable.i04_hippo;
		case 18: return R.drawable.i03_bear;
		case 19: return R.drawable.i02_camel;
		case 20: return R.drawable.i01_duck;
		case 21: return R.drawable.i00_lion;
		default: return null;
		}
	}

	private String getAnimalCaption(int index) {
		switch (index) {
		case 0:  return "תנין";
		case 1:  return "שימפנזה";
		case 2:  return "ראם";
		case 3:  return "קרנף";
		case 4:  return "צב";
		case 5:  return "פיל";
		case 6:  return "עז";
		case 7:  return "סוס";
		case 8:  return "נמר";
		case 9:  return "מדוזה";
		case 10: return "לטאה";
		case 11: return "כבשה";
		case 12: return "יען";
		case 13: return "טווס";
		case 14: return "חתול";
		case 15: return "זברה";
		case 16: return "וואלאבי";
		case 17: return "היפופוטם";
		case 18: return "דוב";
		case 19: return "גמל";
		case 20: return "ברווז";
		case 21: return "אריה";
		default: return null;
		}
	}

}
