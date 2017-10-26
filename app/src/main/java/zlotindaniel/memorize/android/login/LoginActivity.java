package zlotindaniel.memorize.android.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import zlotindaniel.memorize.BaseActivity;
import zlotindaniel.memorize.android.topics.TopicsActivity;

public class LoginActivity extends BaseActivity {

	private static final int LOGIN_REQUEST_CODE = 123456;
	private boolean waitingForLogin = false;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (isLoggedIn()) {
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
		AuthUI.IdpConfig idpConfig = new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build();
		Intent intent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(Lists.newArrayList(idpConfig)).build();
		startActivityForResult(intent, LOGIN_REQUEST_CODE);
		waitingForLogin = true;
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		waitingForLogin = false;

		if (requestCode != LOGIN_REQUEST_CODE
				|| resultCode != Activity.RESULT_OK
				|| !isLoggedIn()) {
			loginFailed();
		} else {
			loginSuccess();
		}
	}

	private boolean isLoggedIn() {
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		return user != null
				&& user.getEmail() != null
				&& user.isEmailVerified()
				&& user.getEmail().equals("zlotindaniel@gmail.com");
	}

	private void loginSuccess() {
		Toast.makeText(this, "Welcome " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), Toast.LENGTH_LONG).show();
		startActivity(new Intent(this, TopicsActivity.class));
		finish();
	}

	private void loginFailed() {
		Toast.makeText(this, "Hey! You're not me! Go Away! (not yet supported)", Toast.LENGTH_LONG).show();
		FirebaseAuth.getInstance().signOut();
		finish();
	}
}
