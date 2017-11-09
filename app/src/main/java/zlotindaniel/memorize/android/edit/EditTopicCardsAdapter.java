package zlotindaniel.memorize.android.edit;

import android.view.*;
import android.widget.*;

import com.google.common.collect.*;

import java.util.*;

import zlotindaniel.memorize.cards.*;

public class EditTopicCardsAdapter extends BaseAdapter {
	private List<Card> data = Lists.newArrayList();

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Card getItem(final int i) {
		return data.get(i);
	}

	@Override
	public long getItemId(final int i) {
		return i;
	}

	@Override
	public View getView(final int i, final View view, final ViewGroup viewGroup) {
		TopicCardCellView cell = view != null ? (TopicCardCellView) view : new TopicCardCellView(viewGroup.getContext());
		Card item = getItem(i);
		cell.bind(item);
		return cell;
	}

	public void bind(List<Card> data) {
		this.data = data;
		notifyDataSetChanged();
	}
}
