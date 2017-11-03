package zlotindaniel.memorize.cards;

import org.assertj.core.util.*;
import org.junit.*;

import java.util.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.data.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CardsServiceTest extends BaseTest {
	private CardsService uut;
	private TestNetwork network;
	private OnSuccess<List<Card>> onReadCardsSuccess;
	private OnFailure onFailure;

	@SuppressWarnings("unchecked")
	@Override
	public void beforeEach() {
		super.beforeEach();
		network = new TestNetwork();
		uut = new CardsService(network);
		onReadCardsSuccess = mock(OnSuccess.class);
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

}