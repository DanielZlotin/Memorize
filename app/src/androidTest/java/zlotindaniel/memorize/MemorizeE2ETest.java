package zlotindaniel.memorize;

import android.support.test.espresso.*;

import org.junit.*;
import org.junit.runners.*;

import zlotindaniel.memorize.android.cards.*;
import zlotindaniel.memorize.android.edit.*;
import zlotindaniel.memorize.android.topics.*;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MemorizeE2ETest extends BaseE2ETest {

	@Test
	public void _1_topicsCardsHappyPath() throws Exception {
		launchApp();
		waitForText(TopicsView.title);

		waitForText("Topic Name 1");
		assertDisplayed("Topic Name 1");
		assertDisplayed("Topic Name 2");
		assertDisplayed("Topic Name 3");

		onView(withText("Topic Name 1")).perform(click());

		waitForText(CardsView.title);

		waitForText("Card Question 1");
		assertDisplayed("Card Question 1");
		onView(withText("Card Question 1")).perform(click());
		assertDisplayed("Card Answer 1");
		onView(withText("Card Answer 1")).perform(click());
		assertDisplayed("Card Question 2");
		onView(withText("Card Question 2")).perform(click());
		assertDisplayed("Card Answer 2");
		onView(withText("Card Answer 2")).perform(click());
		assertDisplayed("Card Question 3");
		onView(withText("Card Question 3")).perform(click());
		assertDisplayed("Card Answer 3");
		onView(withText("Card Answer 3")).perform(click());
		assertDisplayed("Card Question 1");
	}

	@Test
	public void _2_addNewTopic() throws Exception {
		launchApp();
		waitForText(TopicsView.title);
		waitForText("Topic Name 1");

		onView(withContentDescription(TopicsView.menuBtnNewTopicName)).perform(click());

		assertDisplayed("New Topic");
		onView(withHint("Name")).check(matches(isDisplayed()));
		onView(withId(TopicsView.idInputCreateNewTopic)).perform(typeText("New Topic"));

		onView(withText("Create")).perform(click());
		waitForText(TopicsView.title);
		waitForText("New Topic");
		assertDisplayed("New Topic");
	}

	@Test
	public void _3_renameTopic() throws Exception {
		launchApp();
		waitForText(TopicsView.title);
		waitForText("Topic Name 1");

		onView(withText("New Topic")).perform(longClick());

		Espresso.onIdle();
		assertDisplayed("New Topic");

		onView(withContentDescription(EditTopicView.menuBtnRenameTopicTitle)).perform(click());
		onView(withId(EditTopicView.idInputRenameTopic)).perform(clearText());
		onView(withId(EditTopicView.idInputRenameTopic)).perform(typeText("renamed topic"));

		onView(withText("Save")).perform(click());
		Espresso.onIdle();
		waitForText("Renamed Topic");
		assertDisplayed("Renamed Topic");

		device().pressBack();
		assertDisplayed("Renamed Topic");
		assertNotDisplayed("New Topic");
	}

	@Test
	public void _4_topicDetailsRemoveTopic() throws Exception {
		launchApp();
		waitForText(TopicsView.title);
		waitForText("Topic Name 1");

		onView(withText("Renamed Topic")).perform(longClick());

		Espresso.onIdle();
		assertDisplayed("Renamed Topic");

		onView(withContentDescription("More options")).perform(click());
		onView(withText("Delete Topic")).perform(click());
		assertDisplayed("Delete Renamed Topic?");
		onView(withText("Oops. NO!")).perform(click());
		assertDisplayed("Renamed Topic");

		onView(withContentDescription("More options")).perform(click());
		onView(withText("Delete Topic")).perform(click());
		assertDisplayed("Delete Renamed Topic?");
		onView(withText("Yes")).perform(click());
		assertDisplayed("Are you sure?");
		assertDisplayed("There's no going back! Renamed Topic will be lost forever!");
		onView(withText("Yes yes go ahead!")).perform(click());

		Espresso.onIdle();
		waitForText(TopicsView.title);

		assertNotDisplayed("Renamed Topic");
	}

	@Test
	public void _5_topicDetailsShowCards() throws Exception {
		launchApp();
		waitForText(TopicsView.title);
		waitForText("Topic Name 1");

		onView(withText("Topic Name 3")).perform(longClick());
		waitForText("Card Question 1A");
		assertDisplayed("Card Question 1A");
		assertDisplayed("Card Answer 1A");
	}
}
