package com.github.lambig.ezfunc.function;

public abstract class Mapping<F, T> {
	public static final Mapping<Object, String> TO_STRING = new Mapping<Object, String>() {
		@Override
		public String map(Object current, int index) {
			return String.valueOf(current);
		}
	};

	public abstract T map(F current, int index);

	public final T map(F current) {
		return this.map(current, 0);
	}

	public final <E> Mapping<F, E> then(final Mapping<T, E> next) {
		return new Mapping<F, E>() {
			@Override
			public E map(F current, int index) {
				return next.map(Mapping.this.map(current, index), index);
			}
		};
	}
}
