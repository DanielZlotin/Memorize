package zlotindaniel.memorize;

import android.os.*;
import android.support.annotation.*;
import android.support.v7.app.*;

import com.google.common.base.*;
import com.google.firebase.auth.*;

import zlotindaniel.memorize.data.*;

public class BaseActivity extends AppCompatActivity {

	public Config config;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		config = ((MemorizeApplication) getApplication()).getConfig();
	}

	public String getUserId() {
		return isSignedIn() ? FirebaseAuth.getInstance().getCurrentUser().getUid() : "";
	}

	public boolean isSignedIn() {
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		return user != null
				&& user.getEmail() != null
				&& user.isEmailVerified();
	}

	public UserDetails getUserDetails() {
		if (!isSignedIn()) throw new IllegalArgumentException("notSignedIn");
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		return new UserDetails(
				getUserId(),
				user.getEmail(),
				user.getDisplayName(),
				MoreObjects.firstNonNull(user.getPhotoUrl(), "").toString()
		);
	}
}
