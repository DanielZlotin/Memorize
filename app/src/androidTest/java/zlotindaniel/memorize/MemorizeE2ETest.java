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

		clickOn("Topic Name 1");

		waitForText(CardsView.title);

		waitForText("Card Question 1");
		assertDisplayed("Card Question 1");
		clickOn("Card Question 1");
		assertDisplayed("Card Answer 1");
		clickOn("Card Answer 1");
		assertDisplayed("Card Question 2");
		clickOn("Card Question 2");
		assertDisplayed("Card Answer 2");
		clickOn("Card Answer 2");
		assertDisplayed("Card Question 3");
		clickOn("Card Question 3");
		assertDisplayed("Card Answer 3");
		clickOn("Card Answer 3");
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
		onView(withId(TopicsView.idInputCreateNewTopic)).perform(typeText("New Topic 1"));

		clickOn("Create");
		waitForText(TopicsView.title);
		waitForText("New Topic 1");
		assertDisplayed("New Topic 1");
	}

	@Test
	public void _3_renameTopic() throws Exception {
		launchApp();
		waitForText(TopicsView.title);
		waitForText("Topic Name 1");

		onView(withText("New Topic 1")).perform(longClick());

		Espresso.onIdle();
		assertDisplayed("New Topic 1");

		onView(withContentDescription(EditTopicView.menuBtnRenameTopicTitle)).perform(click());
	}

	@Test
	public void _4_topicDetailsRemoveTopic() throws Exception {
		launchApp();
		waitForText(TopicsView.title);
		waitForText("Topic Name 1");

		onView(withText("New Topic 1")).perform(longClick());

		Espresso.onIdle();
		assertDisplayed("New Topic 1");

		onView(withContentDescription("More options")).perform(click());
		clickOn("Delete Topic");
		assertDisplayed("Delete New Topic 1?");
		clickOn("Oops. NO!");
		assertDisplayed("New Topic 1");

		onView(withContentDescription("More options")).perform(click());
		clickOn("Delete Topic");
		assertDisplayed("Delete New Topic 1?");
		clickOn("Yes");
		assertDisplayed("Are you sure?");
		assertDisplayed("There's no going back! New Topic 1 will be lost forever!");
		clickOn("Yes yes go ahead!");

		Espresso.onIdle();
		waitForText(TopicsView.title);

		assertNotDisplayed("New Topic 1");
	}
}
