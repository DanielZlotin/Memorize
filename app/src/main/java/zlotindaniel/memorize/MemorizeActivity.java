package zlotindaniel.memorize;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MemorizeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Toast.makeText(this, ((MemorizeApplication) getApplication()).bla(), Toast.LENGTH_LONG).show();
	}
}
