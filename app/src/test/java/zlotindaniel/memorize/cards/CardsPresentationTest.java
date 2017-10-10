package zlotindaniel.memorize.cards;

import org.junit.Test;

import zlotindaniel.memorize.BaseTest;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CardsPresentationTest extends BaseTest {

	@Test
	public void isEnum() throws Exception {
		assertThat(CardsPresentation.valueOf("Loading")).isEqualTo(CardsPresentation.Loading);
		assertThat(CardsPresentation.values()).hasSize(4);
	}

	@Test
	public void progressVisibility() throws Exception {
		assertThat(CardsPresentation.Loading.isProgressVisible()).isTrue();
		assertThat(CardsPresentation.Phrase.isProgressVisible()).isFalse();
		assertThat(CardsPresentation.Error.isProgressVisible()).isFalse();
	}

	@Test
	public void titleVisibility() throws Exception {
		assertThat(CardsPresentation.Phrase.isTitleVisbile()).isTrue();
		assertThat(CardsPresentation.Definition.isTitleVisbile()).isFalse();
		assertThat(CardsPresentation.Error.isTitleVisbile()).isFalse();
	}

	@Test
	public void textVisibility() throws Exception {
		assertThat(CardsPresentation.Phrase.isTextVisible()).isTrue();
		assertThat(CardsPresentation.Definition.isTextVisible()).isTrue();
		assertThat(CardsPresentation.Error.isTextVisible()).isTrue();
		assertThat(CardsPresentation.Loading.isTextVisible()).isFalse();
	}
}
