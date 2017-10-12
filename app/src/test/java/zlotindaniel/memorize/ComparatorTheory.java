package zlotindaniel.memorize;

import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.Comparator;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public abstract class ComparatorTheory<T extends Object, C extends Object & Comparator<T>> {
	/**
	 * Executes the test and stores the value, be it an exception or the
	 * calculated value.
	 */
	private static final class TestAtom<T extends Object, C extends Object & Comparator<T>> {
		private int value;
		private Exception e;

		@SuppressWarnings("unchecked")
		private TestAtom(final C comparator, final Object uutX, final Object uutY) {
			try {
				this.value = comparator.compare((T) uutX, (T) uutY);
			} catch (final Exception e) {
				this.e = e;
			}
		}

		private boolean isExceptionThrown() {
			return e != null;
		}
	}

	private static boolean noExceptionThrown(final TestAtom<?, ?>... atoms) {
		for (final TestAtom<?, ?> e : atoms)
			if (e.isExceptionThrown())
				return false;
		return true;
	}

	/**
	 * It is strongly recommended, but <i>not</i> strictly required that
	 * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>. Generally speaking, any
	 * class that implements the <tt>Comparable</tt> interface and violates this
	 * condition should clearly indicate this fact. The recommended language is
	 * "Note: this class has a natural ordering that is inconsistent with equals."
	 */
	@Theory
	public final void compareToIsConsistentToEquals(final Object uutX, final Object uutY) {
		if (!shouldCheckConsistancyWithEquals())
			return;
		assumeNotNull(uutX);
		final TestAtom<T, C> xToY = new TestAtom<>(comparatorUnderTest(), uutX, uutY);
		assumeFalse(xToY.isExceptionThrown());
		assumeTrue(xToY.value == 0);
		assertThat(uutX).isEqualTo(uutY);
	}

	/**
	 * Checks the symmetric property of the {@link Comparable#compareTo(Object)}
	 * method.
	 * <p>
	 * The implementor must ensure <tt>sgn(x.compareTo(y)) ==
	 * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>. (This
	 * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
	 * <tt>y.compareTo(x)</tt> throws an exception.)
	 */
	@Theory
	public void compareToIsSymmetric(final Object uutX, final Object uutY) {
		assumeNotNull(uutX);
		assumeNotNull(uutY);
		final TestAtom<T, C> xToY = new TestAtom<>(comparatorUnderTest(), uutX, uutY);
		final TestAtom<T, C> yToX = new TestAtom<>(comparatorUnderTest(), uutY, uutX);
		if (noExceptionThrown(xToY, yToX))
			assertThat(xToY.value).isEqualTo(-yToX.value);
		else {
			assertThat(xToY.e).isNotNull();
			assertThat(yToX.e).isNotNull();
		}
	}

	/**
	 * Checks the transitive property of the
	 * {@link Comparable#compareTo(Object)} method.
	 * <p>
	 * The implementor must also ensure that the relation is transitive:
	 * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
	 * <tt>x.compareTo(z)&gt;0</tt>.
	 * <p>
	 * Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
	 * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for all
	 * <tt>z</tt>.
	 */
	@Theory
	public final void compareToIsTransitive(final Object uutX, final Object uutY, final Object uutZ) {
		assumeNotNull(uutX);
		assumeNotNull(uutY);
		assumeNotNull(uutZ);
		final TestAtom<T, C> xToY = new TestAtom<>(comparatorUnderTest(), uutX, uutY);
		final TestAtom<T, C> yToZ = new TestAtom<>(comparatorUnderTest(), uutY, uutZ);
		final TestAtom<T, C> xToZ = new TestAtom<>(comparatorUnderTest(), uutX, uutZ);
		if (noExceptionThrown(xToY, yToZ, xToZ)) {
			if (xToY.value > 0 && yToZ.value > 0)
				assertThat(xToZ.value).isPositive();
			if (xToY.value == 0)
				assertThat(Integer.signum(xToZ.value)).isEqualTo(Integer.signum(yToZ.value));
		} else {
			assertThat(xToY.e).isNotNull();
			assertThat(yToZ.e).isNotNull();
			assertThat(xToZ.e).isNotNull();
		}
	}

	protected abstract C comparatorUnderTest();

	protected boolean shouldCheckConsistancyWithEquals() {
		return false;
	}
}
