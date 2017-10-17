package zlotindaniel.memorize;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import zlotindaniel.memorize.android.topics.TopicsActivity;

public class LaunchActivity extends BaseActivity {

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startActivity(new Intent(this, TopicsActivity.class));
		finish();
	}

//	private void login() {
//		FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//		Toast.makeText(this, MoreObjects.firstNonNull(currentUser, "null").toString(), Toast.LENGTH_SHORT).show();
//
//		AuthUI.IdpConfig googleProvider = new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build();
//		Intent intent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(Lists.newArrayList(googleProvider)).build();
//		startActivityForResult(intent, REQUEST_CODE);
//	}

//	@Override
//	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (requestCode != REQUEST_CODE) return;
//
//		IdpResponse idpResponse = IdpResponse.fromResultIntent(data);
//
//		if (resultCode != RESULT_OK || idpResponse == null) {
//			Toast.makeText(this, "sign in error", Toast.LENGTH_SHORT).show();
//			return;
//		}
//
//		Toast.makeText(this, "success! " + idpResponse.getProviderType(), Toast.LENGTH_SHORT).show();
//		Toast.makeText(this, idpResponse.getProviderType(), Toast.LENGTH_SHORT).show();
//		Toast.makeText(this, idpResponse.getEmail(), Toast.LENGTH_SHORT).show();
//	}
}
