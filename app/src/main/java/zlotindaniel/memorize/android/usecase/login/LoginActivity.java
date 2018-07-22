package zlotindaniel.memorize.android.usecase.login;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.widget.*;

import com.firebase.ui.auth.*;
import com.google.common.collect.*;
import com.google.firebase.auth.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.android.usecase.topics.*;

public class LoginActivity extends BaseActivity {

	private static final int LOGIN_REQUEST_CODE = 100;
	private boolean waitingForLogin = false;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (isSignedIn()) {
			loginSuccess();
		} else {
			login();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!waitingForLogin && !isFinishing()) {
			finish();
		}
	}

	private void login() {
		waitingForLogin = true;
		AuthUI.IdpConfig idpConfig = new AuthUI.IdpConfig.GoogleBuilder().build();
		Intent intent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(Lists.newArrayList(idpConfig)).build();
		startActivityForResult(intent, LOGIN_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		waitingForLogin = false;

		if (requestCode != LOGIN_REQUEST_CODE
				|| resultCode != Activity.RESULT_OK
				|| !isSignedIn()) {
			loginFailed();
		} else {
			loginSuccess();
		}
	}

	private void loginSuccess() {
		overridePendingTransition(0, 0);
		startActivity(new Intent(this, TopicsActivity.class));
		finish();
	}

	private void loginFailed() {
		Toast.makeText(this, "Nothing to see here... move along citizen", Toast.LENGTH_LONG).show();
		FirebaseAuth.getInstance().signOut();
		finish();
	}

}
