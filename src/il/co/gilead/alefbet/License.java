package il.co.gilead.alefbet;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import android.app.Activity;

public class License extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_license);
		TextView tv = (TextView) findViewById(R.id.textViewLicense);
		tv.setMovementMethod(LinkMovementMethod.getInstance());
	}

}
