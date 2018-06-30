package zlotindaniel.memorize;

import android.os.*;
import android.support.annotation.*;
import android.support.v7.app.*;

import com.google.common.base.*;
import com.google.firebase.auth.*;
import com.google.firebase.database.FirebaseDatabase;

import zlotindaniel.memorize.data.*;

public abstract class BaseActivity extends AppCompatActivity {

	public Config config;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		config = ((MemorizeApplication) getApplication()).getConfig();
	}

	public boolean isSignedIn() {
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

		return user != null
				&& user.getEmail() != null
				&& user.isEmailVerified();
	}

	public UserDetails getUserDetails() {
		Preconditions.checkArgument(isSignedIn(), "not signed in");

		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

		return new UserDetails(
				user.getUid(),
				user.getEmail(),
				user.getDisplayName(),
				MoreObjects.firstNonNull(user.getPhotoUrl(), "").toString()
		);
	}
}
