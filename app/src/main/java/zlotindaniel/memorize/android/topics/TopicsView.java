package zlotindaniel.memorize.android.topics;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import zlotindaniel.memorize.R;
import zlotindaniel.memorize.android.ViewUtils;
import zlotindaniel.memorize.android.cards.CardsActivity;
import zlotindaniel.memorize.android.edit.EditTopicActivity;
import zlotindaniel.memorize.topics.Topic;
import zlotindaniel.memorize.topics.TopicsDisplay;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class TopicsView extends FrameLayout implements TopicsDisplay {
	public static final String title = "Select A Topic";
	public static final String menuBtnNewTopicName = "Create New Topic";
	public static final int idInputCreateNewTopic = View.generateViewId();
	public static final int idBtnMenuCreateNewTopic = View.generateViewId();

	private ListView listview;
	private TopicsListAdapter listAdapter;
	private SwipeRefreshLayout pullToRefresh;
	private Listener listener;

	public TopicsView(final Activity context) {
		super(context);
		context.setTitle(title);
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
		MenuItem menuItem = menu.add(Menu.NONE, idBtnMenuCreateNewTopic, Menu.NONE, menuBtnNewTopicName);
		menuItem.setIcon(R.drawable.ic_add_white_24dp);
		menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}

	public void onMenuItemClicked(final MenuItem item) {
		if (item.getItemId() == idBtnMenuCreateNewTopic) {
			askNewTopic();
		}
	}

	private void askNewTopic() {
		TextInputLayout layout = new TextInputLayout(getContext());
		int p = ViewUtils.dp(16);
		layout.setPadding(p, p, p, p);

		final TextInputEditText input = new TextInputEditText(getContext());
		input.setInputType(EditorInfo.TYPE_CLASS_TEXT
				| EditorInfo.TYPE_TEXT_FLAG_AUTO_COMPLETE
				| EditorInfo.TYPE_TEXT_FLAG_AUTO_CORRECT
				| EditorInfo.TYPE_TEXT_FLAG_CAP_WORDS);
		input.setSingleLine();
		input.setHint("Name");
		input.setId(idInputCreateNewTopic);

		layout.addView(input);

		new AlertDialog.Builder(getContext()).setTitle("New Topic").setView(layout).setPositiveButton("Create", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				String name = input.getText().toString();
				listener.createTopic(name);
			}
		}).show();
	}

	@Override
	public void setListener(final Listener listener) {
		this.listener = listener;
		pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				listener.refresh();
			}
		});
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
				navigateShowTopic(listAdapter.getItem(position).getId());
			}
		});
		listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
				navigateEditTopic(listAdapter.getItem(position).getId());
				return true;
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

	private void navigateShowTopic(final String topicId) {
		Intent intent = new Intent(getContext(), CardsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra(CardsActivity.INTENT_TOPIC_ID, topicId);
		getContext().startActivity(intent);
	}

	private void navigateEditTopic(final String topicId) {
		Intent intent = new Intent(getContext(), EditTopicActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra(EditTopicActivity.INTENT_TOPIC_ID, topicId);
		getContext().startActivity(intent);
	}
}
