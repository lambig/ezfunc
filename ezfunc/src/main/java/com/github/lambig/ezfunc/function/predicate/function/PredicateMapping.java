package com.github.lambig.ezfunc.function.predicate.function;

import java.util.List;
import java.util.Objects;

import com.github.lambig.ezfunc.function.Mapping;
import com.github.lambig.ezfunc.function.predicate.Predicate;
import com.github.lambig.ezfunc.operand.impl.list.ListOperands;

public abstract class PredicateMapping {
	public static final <E> Mapping<Predicate<E>, Predicate<E>> invert() {
		return new Mapping<Predicate<E>, Predicate<E>>() {
			@Override
			public Predicate<E> map(Predicate<E> current, int index) {
				return current.invert();
			}
		};
	}

	public static <T> Mapping<T, Predicate<T>> equalTo() {
		return new Mapping<T, Predicate<T>>() {
			@Override
			public Predicate<T> map(T current, int index) {
				return Predicate.equalTo(current);
			}
		};
	}

	public static <O, P> Mapping<P, Predicate<O>> having(Mapping<O, P> extraction) {
		return new Mapping<P, Predicate<O>>() {
			@Override
			public Predicate<O> map(P currentCriteria, int index) {
				return new Predicate<O>() {
					@Override
					public boolean eval(O current, int index) {
						return Objects.equals(currentCriteria, extraction.map(current));
					}
				};
			}
		};
	}

	public static <T> Mapping<Predicate<? super T>, T> firstSatisfierIn(List<T> list) {
		return new Mapping<Predicate<? super T>, T>() {
			@Override
			public T map(Predicate<? super T> current, int index) {
				return ListOperands.of(list).first(current);
			}
		};
	}
}
