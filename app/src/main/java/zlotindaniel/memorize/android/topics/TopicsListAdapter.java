package zlotindaniel.memorize.android.topics;

import android.view.*;
import android.widget.*;

import com.google.common.collect.*;

import java.util.*;

import zlotindaniel.memorize.topics.*;

public class TopicsListAdapter extends BaseAdapter {
	private List<Topic> data = Lists.newArrayList();

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Topic getItem(final int i) {
		return data.get(i);
	}

	@Override
	public long getItemId(final int i) {
		return i;
	}

	@Override
	public View getView(final int i, final View view, final ViewGroup viewGroup) {
		TopicCellView cell = view != null ? (TopicCellView) view : new TopicCellView(viewGroup.getContext());
		final Topic topic = getItem(i);
		cell.bind(topic);
		return cell;
	}

	public void bind(final List<Topic> topics) {
		data = topics;
		notifyDataSetChanged();
	}
}
