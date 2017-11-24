package zlotindaniel.memorize.data;

import org.assertj.core.util.*;
import org.junit.*;

import java.util.*;

import zlotindaniel.memorize.*;

import static org.assertj.core.api.Assertions.*;

public class DatabaseServiceTest extends BaseTest {

	private DatabaseService uut;
	private MockDatabase database;
	private MockOnSuccess<String> onSuccessString;
	private MockRunnable onSuccessRunnable;
	private MockOnFailure onFailure;

	@SuppressWarnings("unchecked")
	@Override
	public void beforeEach() {
		super.beforeEach();
		database = new MockDatabase();
		uut = new DatabaseService(true, "theUserId", database);
		onSuccessString = new MockOnSuccess<>();
		onSuccessRunnable = new MockRunnable();
		onFailure = new MockOnFailure();
	}

	@Test
	public void fullPath() throws Exception {
		assertThat(uut.fullpath("a", "b", "c")).isEqualTo("v1/test/users/theUserId/a/b/c");
		assertThat(new DatabaseService(false, "theUserId2", database).fullpath("a", "b", "c"))
				.isEqualTo("v1/production/users/theUserId2/a/b/c");
	}

	@Test
	public void topicExistsExceptionMsg() throws Exception {
		assertThat(new DatabaseService.TopicExistsException()).hasMessage("Topic already exists");
	}

	@Test
	public void createTopicCheckForDuplicateByName() throws Exception {
		database.nextSuccess(Lists.newArrayList(new Topic("", "  Some Name\n\n ")));

		uut.createTopic(new Topic("", "some name"), onSuccessString, onFailure);

		assertThat(database.reads).hasSize(1);
		assertThat(database.creations).hasSize(0);
		assertThat(onFailure.calls).hasSize(1);
		assertThat(onFailure.calls.get(0)).isInstanceOf(DatabaseService.TopicExistsException.class);
		assertThat(onSuccessString.calls).isEmpty();
	}

	@Test
	public void createTopic() throws Exception {
		database.nextSuccess(Lists.newArrayList(new Topic("", "other")));
		database.nextSuccess("theNewId");

		uut.createTopic(new Topic("", "new name"), onSuccessString, onFailure);

		assertThat(database.creations).hasSize(1);
		assertThat(onFailure.calls).isEmpty();
		assertThat(onSuccessString.calls).hasSize(1).containsOnly("theNewId");
	}

	@Test
	public void createTopicUrl() throws Exception {
		database.nextSuccess(Lists.newArrayList());
		uut.createTopic(new Topic("", "some name"), onSuccessString, onFailure);
		assertThat(database.creations).hasSize(1);
		assertThat(database.creations.get(0).toArray()).contains("v1/test/users/theUserId/topics/index");
	}

	@Test
	public void createError() throws Exception {
		RuntimeException error = new RuntimeException("error");
		database.nextFailure(error);

		uut.createTopic(new Topic("", "some name"), onSuccessString, onFailure);

		assertThat(onFailure.calls).containsExactly(error);
		assertThat(onSuccessString.calls).isEmpty();
	}

	@Test
	public void updateTopicChecksDuplicateName() throws Exception {
		database.nextSuccess(Lists.newArrayList(new Topic("", "  Some Name\n\n ")));

		uut.updateTopic(new Topic("theId", "some name"), onSuccessRunnable, onFailure);

		assertThat(database.reads).hasSize(1);
		assertThat(database.updates).hasSize(0);

		assertThat(onFailure.calls).hasSize(1);
		assertThat(onFailure.calls.get(0)).isInstanceOf(DatabaseService.TopicExistsException.class);
		assertThat(onSuccessRunnable.calls).isZero();
	}

	@Test
	public void updateTopic() throws Exception {
		database.nextSuccess(Lists.newArrayList(new Topic("", "othe")));
		database.nextSuccess(true);

		uut.updateTopic(new Topic("theId", "some name"), onSuccessRunnable, onFailure);

		assertThat(database.reads).hasSize(1);
		assertThat(database.updates).hasSize(1);
		assertThat(database.updates.get(0).toArray()).contains("v1/test/users/theUserId/topics/index/theId");
		assertThat(onFailure.calls).isEmpty();
		assertThat(onSuccessRunnable.calls).isOne();
	}

	@Test
	public void readAllTopics() throws Exception {
		ArrayList<Topic> list = Lists.newArrayList(new Topic("theId", "theName"));
		database.nextSuccess(list);
		MockOnSuccess<List<Topic>> onSuccess = new MockOnSuccess<>();

		uut.readAllTopics(onSuccess, onFailure);

		assertThat(onFailure.calls).isEmpty();
		assertThat(onSuccess.calls).hasSize(1).containsExactly(list);
		assertThat(database.reads.get(0).toArray()).contains("v1/test/users/theUserId/topics/index");
	}

	@Test
	public void deleteTopicThenDeleteAllCardsOfSaidTopic() throws Exception {
		database.nextSuccess(true);
		database.nextSuccess(true);
		uut.deleteTopic(new Topic("theTopicId", "name"), onSuccessRunnable, onFailure);
		assertThat(onFailure.calls).isEmpty();
		assertThat(onSuccessRunnable.calls).isOne();

		assertThat(database.deletions).hasSize(2);
		assertThat(database.deletions.get(0).toArray()).contains("v1/test/users/theUserId/topics/index/theTopicId");
		assertThat(database.deletions.get(1).toArray()).contains("v1/test/users/theUserId/topics/cards/theTopicId");
	}

	@Test
	public void readCardsFailure() throws Exception {
		RuntimeException error = new RuntimeException("error");
		database.nextFailure(error);
		MockOnSuccess<List<Card>> onSuccess = new MockOnSuccess<>();

		uut.readTopicCards("the id", onSuccess, onFailure);

		assertThat(onSuccess.calls).isEmpty();
		assertThat(onFailure.calls).hasSize(1).containsExactly(error);
	}

	@Test
	public void readCards() throws Exception {
		List<Card> list = Lists.newArrayList(new Card("id", "q", "a"));
		database.nextSuccess(list);
		MockOnSuccess<List<Card>> onSuccess = new MockOnSuccess<>();

		uut.readTopicCards("theId", onSuccess, onFailure);

		assertThat(onSuccess.calls).hasSize(1).containsExactly(list);
		assertThat(onFailure.calls).isEmpty();

		assertThat(database.reads).hasSize(1);
		assertThat(database.reads.get(0).toArray()).contains("v1/test/users/theUserId/topics/cards/theId");
	}

	@Test
	public void createCard() throws Exception {
		Card card = new Card("", "q", "a");
		database.nextSuccess("the new id");

		uut.createCard("theTopicId", card, onSuccessString, onFailure);

		assertThat(database.creations.get(0).toArray()).contains("v1/test/users/theUserId/topics/cards/theTopicId");
		assertThat(onSuccessString.calls).hasSize(1).containsExactly("the new id");
		assertThat(onFailure.calls).isEmpty();
	}

	@Test
	public void updateCard() throws Exception {
		database.nextSuccess(true);
		Card card = new Card("cardId", "q", "a");
		MockRunnable onSuccess = new MockRunnable();

		uut.updateCard("topicId", card, onSuccess, onFailure);

		assertThat(database.updates.get(0).toArray()).contains("v1/test/users/theUserId/topics/cards/topicId/cardId");
		assertThat(onSuccess.calls).isOne();
		assertThat(onFailure.calls).isEmpty();
	}

	@Test
	public void deleteCard() throws Exception {
		database.nextSuccess(true);
		Card card = new Card("cardId", "q", "a");
		MockRunnable onSuccess = new MockRunnable();

		uut.deleteCard("theTopicId", card, onSuccess, onFailure);

		assertThat(database.deletions).hasSize(1);
		assertThat(database.deletions.get(0).toArray()).contains("v1/test/users/theUserId/topics/cards/theTopicId/cardId");
		assertThat(onSuccess.calls).isOne();
		assertThat(onFailure.calls).isEmpty();
	}

	@Test
	public void updateUserDetails() throws Exception {
		database.nextSuccess(true);

		uut.updateUserDetails(new UserDetails("theId", "theEmail", "theName", "thePhoto"), onSuccessRunnable, onFailure);

		assertThat(onFailure.calls).isEmpty();
		assertThat(onSuccessRunnable.calls).isOne();
		assertThat(database.updates).hasSize(1);
		assertThat(database.updates.get(0).toArray()[0]).isEqualTo("v1/test/users/theUserId/details");
	}
}
