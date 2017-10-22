package zlotindaniel.memorize.android.topics;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import zlotindaniel.memorize.android.cards.CardsActivity;
import zlotindaniel.memorize.android.edit.EditTopicsActivity;
import zlotindaniel.memorize.topics.Topic;
import zlotindaniel.memorize.topics.TopicsDisplay;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class TopicsView extends FrameLayout implements TopicsDisplay {
	public static final String TITLE = "Select A Topic";
	public static final String ADMIN_MODE_TITLE = "Admin Mode";

	private static final int LOGIN_REQUEST_CODE = 123456;

	private ListView listview;
	private TopicsListAdapter listAdapter;
	private SwipeRefreshLayout pullToRefresh;

	public TopicsView(final Activity context) {
		super(context);
		context.setTitle(TITLE);
		initList();
	}

	private void initList() {
		pullToRefresh = new SwipeRefreshLayout(getContext());
		pullToRefresh.setRefreshing(true);

		listview = new ListView(getContext());
		listview.setId(generateViewId());
		listview.setVisibility(GONE);
		LayoutParams params = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
		pullToRefresh.addView(listview);
		addView(pullToRefresh, params);

		listAdapter = new TopicsListAdapter();
		listview.setAdapter(listAdapter);
	}

	public void onCreateMenu(final Menu menu) {
		menu.add(ADMIN_MODE_TITLE);
	}

	public void onMenuItemClicked(final MenuItem item) {
		if (item.getTitle().equals(ADMIN_MODE_TITLE)) {
			onClickAdmin();
		}
	}

	@Override
	public void setListener(final Listener listener) {
		pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				listener.onRefresh();
			}
		});
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
				listener.onTopicClicked(listAdapter.getItem(position));
			}
		});
		listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
				boolean isAdmin = isAdmin();
				if (isAdmin) listener.onTopicEditClicked(listAdapter.getItem(position));
				return isAdmin;
			}
		});
	}

	@Override
	public void bind(final List<Topic> topics) {
		pullToRefresh.setRefreshing(false);
		listview.setVisibility(VISIBLE);
		listAdapter.bind(topics);
	}

	@Override
	public void bind(final String error) {
		pullToRefresh.setRefreshing(false);
		Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
	}

	@Override
	public void navigateShowTopic(final String topicId) {
		Intent intent = new Intent(getContext(), CardsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra(CardsActivity.INTENT_TOPIC_ID, topicId);
		getContext().startActivity(intent);
	}

	@Override
	public void navigateEditTopic(final String topicId) {
		Intent intent = new Intent(getContext(), EditTopicsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra(EditTopicsActivity.INTENT_TOPIC_ID, topicId);
		getContext().startActivity(intent);
	}

	private void onClickAdmin() {
		if (isAdmin()) {
			Toast.makeText(getContext(), "Already Admin", Toast.LENGTH_SHORT).show();
			return;
		}

		new AlertDialog.Builder(getContext())
				.setMessage("Nothing to see here, move along.")
				.setPositiveButton("It's me", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog, final int which) {
						login();
					}
				})
				.show();
	}

	private void login() {
		AuthUI.IdpConfig idpConfig = new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build();
		Intent intent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(Lists.newArrayList(idpConfig)).build();
		((Activity) getContext()).startActivityForResult(intent, LOGIN_REQUEST_CODE);
	}

	public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		IdpResponse idpResponse = IdpResponse.fromResultIntent(data);
		if (requestCode != LOGIN_REQUEST_CODE
				|| resultCode != Activity.RESULT_OK
				|| idpResponse == null
				|| !Strings.nullToEmpty(idpResponse.getEmail()).equals("zlotindaniel@gmail.com")) {
			Toast.makeText(getContext(), "Hey! You're not me! Go Away! (not yet supported)", Toast.LENGTH_LONG).show();
			FirebaseAuth.getInstance().signOut();
		}
	}

	private boolean isAdmin() {
		return FirebaseAuth.getInstance().getCurrentUser() != null;
	}
}
