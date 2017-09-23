package zlotindaniel.memorize.topics;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import zlotindaniel.memorize.cards.CardsActivity;

public class TopicsView extends LinearLayout implements AdapterView.OnItemClickListener, TopicsInteractor.Display {

	private final List<Topic> topics = new ArrayList<>();
	private ListView listView;
	private TopicsAdapter adapter;

	public TopicsView(final Activity activity) {
		super(activity);
		activity.setTitle("Select A Topic");
		setOrientation(VERTICAL);
		int pad = (int) (getResources().getDisplayMetrics().density * 8);
		setPadding(pad, pad, pad, pad);
		initList();
	}

	public void bind(List<Topic> data) {
		topics.clear();
		topics.addAll(data);
		adapter.notifyDataSetChanged();
	}

	private void initList() {
		listView = new ListView(getContext());
		adapter = new TopicsAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		addView(listView);
	}

	@Override
	public void onItemClick(final AdapterView<?> adapterView, final View view, final int i, final long l) {
		Intent intent = new Intent(getContext(), CardsActivity.class);
		intent.putExtra(CardsActivity.PARAM_TOPIC, adapter.getItem(i).title);
		getContext().startActivity(intent);
	}

	private class TopicsAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return topics.size();
		}

		@Override
		public Topic getItem(final int i) {
			return topics.get(i);
		}

		@Override
		public long getItemId(final int i) {
			return topics.get(i).title.hashCode();
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getView(final int i, final View view, final ViewGroup viewGroup) {
			TopicCellView cell;
			if (view == null)
				cell = new TopicCellView(getContext());
			else
				cell = (TopicCellView) view;
			cell.bind(getItem(i));
			return cell;
		}
	}
}
