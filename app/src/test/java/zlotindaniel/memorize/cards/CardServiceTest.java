package zlotindaniel.memorize.cards;

import org.assertj.core.util.*;
import org.junit.*;

import java.util.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.data.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CardServiceTest extends BaseTest {
	private CardService uut;
	private TestNetwork network;
	private OnSuccess<List<Card>> onReadCardsSuccess;
	private OnSuccess<String> onCreateSuccess;
	private OnSuccess<Boolean> onUpdateSuccess;
	private OnFailure onFailure;

	@SuppressWarnings("unchecked")
	@Override
	public void beforeEach() {
		super.beforeEach();
		network = new TestNetwork();
		uut = new CardService(network);
		onReadCardsSuccess = mock(OnSuccess.class);
		onCreateSuccess = mock(OnSuccess.class);
		onUpdateSuccess = mock(OnSuccess.class);
		onFailure = mock(OnFailure.class);
	}

	@Test
	public void readCardsFailure() throws Exception {
		network.nextError(new RuntimeException("error"));
		uut.readTopicCards("the id", onReadCardsSuccess, onFailure);
		verifyZeroInteractions(onReadCardsSuccess);
		verify(onFailure, times(1)).failure(any());
	}

	@Test
	public void readCards() throws Exception {
		List<Card> list = Lists.newArrayList(new Card("id", "q", "a"));
		network.nextSuccess(list);
		uut.readTopicCards("the id", onReadCardsSuccess, onFailure);
		verify(onReadCardsSuccess, times(1)).success(list);
		verifyZeroInteractions(onFailure);
		assertThat(network.reads).hasSize(1);
		assertThat(network.reads.get(0).path).isEqualTo("topics/cards/the id");
	}

	@Test
	public void createCard() throws Exception {
		Card card = new Card("", "q", "a");
		network.nextSuccess("the new id");
		uut.createCard("theTopicId", card, onCreateSuccess, onFailure);
		assertThat(network.creations.get(0).path).isEqualTo("topics/cards/theTopicId");

		verify(onCreateSuccess, times(1)).success(eq("the new id"));
		verifyZeroInteractions(onFailure);
	}

	@Test
	public void updateCard() throws Exception {
		network.nextSuccess(true);
		Card card = new Card("cardId", "q", "a");
		uut.updateCard("topicId", card, onUpdateSuccess, onFailure);
		assertThat(network.updates.get(0).path).isEqualTo("topics/cards/topicId/cardId");
		verify(onUpdateSuccess, times(1)).success(eq(true));
		verifyZeroInteractions(onFailure);
	}

	@Test
	public void deleteCard() throws Exception {
		network.nextSuccess(true);
		Card card = new Card("cardId", "q", "a");
		uut.deleteCard("theTopicId", card, onUpdateSuccess, onFailure);
		assertThat(network.deletions).hasSize(1);
		assertThat(network.deletions.get(0).path).isEqualTo("topics/cards/theTopicId/cardId");
		verify(onUpdateSuccess, times(1)).success(eq(true));
		verifyZeroInteractions(onFailure);
	}
}