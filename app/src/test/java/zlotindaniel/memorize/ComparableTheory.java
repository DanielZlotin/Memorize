package zlotindaniel.memorize;


import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public abstract class ComparableTheory<T extends Object & Comparable<T>> {
	/**
	 * Executes the test and stores the value, be it an exception or the
	 * calculated value.
	 */
	private static final class TestAtom<T extends Object & Comparable<T>> {
		private int value;
		private Exception e;

		@SuppressWarnings("unchecked")
		private TestAtom(final Object uutX, final Object uutY) {
			try {
				this.value = ((Comparable<T>) uutX).compareTo((T) uutY);
			} catch (final Exception e) {
				this.e = e;
			}
		}
	}

	private static boolean noExceptionThrown(final TestAtom<?>... atoms) {
		for (final TestAtom<?> e : atoms)
			if (e.e != null)
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
		assumeThat(uutX, is(notNullValue()));
		final TestAtom<T> xToY = new TestAtom<T>(uutX, uutY);
		assumeThat(xToY.e, is(nullValue()));
		assumeThat(xToY.value, is(0));
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
		assumeThat(uutX, is(notNullValue()));
		assumeThat(uutY, is(notNullValue()));
		final TestAtom<T> xToY = new TestAtom<T>(uutX, uutY);
		final TestAtom<T> yToX = new TestAtom<T>(uutY, uutX);
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
		assumeThat(uutX, is(notNullValue()));
		assumeThat(uutY, is(notNullValue()));
		assumeThat(uutZ, is(notNullValue()));
		final TestAtom<T> xToY = new TestAtom<T>(uutX, uutY);
		final TestAtom<T> yToZ = new TestAtom<T>(uutY, uutZ);
		final TestAtom<T> xToZ = new TestAtom<T>(uutX, uutZ);
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

	protected boolean shouldCheckConsistancyWithEquals() {
		return false;
	}
}
