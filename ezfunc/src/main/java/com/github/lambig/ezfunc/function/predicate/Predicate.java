package com.github.lambig.ezfunc.function.predicate;

import com.github.lambig.ezfunc.function.Mapping;
import com.google.common.base.Objects;

public abstract class Predicate<E> extends Mapping<E, Boolean> {
	public static final Predicate<Object> IS_NULL = new Predicate<Object>() {
		@Override
		public boolean eval(Object current, int index) {
			return current == null;
		}
	};

	public static final Predicate<Object> IS_FIRST = new Predicate<Object>() {
		@Override
		public boolean eval(Object current, int index) {
			return index == 0;
		}
	};

	public static final <E> Predicate<E> equalTo(E target) {
		return new Predicate<E>() {
			@Override
			public boolean eval(E current, int index) {
				return Objects.equal(current, target);
			}
		};
	}

	public static final <E> Predicate<E> always() {
		return new Predicate<E>() {
			@Override
			public boolean eval(E current, int index) {
				return true;
			}
		};
	}

	public static final <E> Predicate<E> never() {
		return new Predicate<E>() {
			@Override
			public boolean eval(E current, int index) {
				return false;
			}
		};
	}

	public static final <E> Predicate<E> before(int threshold) {
		return new Predicate<E>() {
			@Override
			public boolean eval(E current, int index) {
				return index < threshold;
			}
		};
	}

	public static final <E> Predicate<E> after(int threshold) {
		return new Predicate<E>() {
			@Override
			public boolean eval(E current, int index) {
				return index < threshold;
			}
		};
	}

	public static final <E> Predicate<E> between(int from, int to) {
		return and(
				after(from),
				before(to));
	}

	public abstract boolean eval(E current, int index);

	public final boolean eval(E current) {
		return this.eval(current, 0);
	}

	@Override
	public final Boolean map(E current, int index) {
		return this.eval(current, index);
	}

	public Predicate<E> invert() {
		return new Predicate<E>() {
			@Override
			public boolean eval(E current, int index) {
				return !Predicate.this.eval(current, index);
			}
		};
	}

	public Predicate<E> and(Predicate<E> next) {
		return PredicateSequence.sequence(this, next);
	}

	@SafeVarargs
	public static <O> Predicate<O> and(Predicate<O>... elements) {
		return Predicates.and(elements);
	}

	@SafeVarargs
	public static <O> Predicate<O> or(Predicate<O>... elements) {
		return Predicates.or(elements);
	}

	public static <O> Predicate<O> not(Predicate<O> element) {
		return element.invert();
	}
}
