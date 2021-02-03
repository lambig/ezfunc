package com.github.lambig.ezfunc.function.predicate;

import java.util.List;

import com.github.lambig.ezfunc.function.predicate.function.PredicateMapping;
import com.github.lambig.ezfunc.function.predicate.function.PredicateReduction;
import com.github.lambig.ezfunc.operand.impl.list.ListOperands;

public class Predicates<T> extends ListOperands<Predicate<T>> {
	@SafeVarargs
	public static <E> Predicates<E> group(Predicate<E>... elements) {
		return new Predicates<>(elements);
	}

	public static <E> Predicates<E> group(List<Predicate<E>> elements) {
		return new Predicates<>(elements);
	}

	@SafeVarargs
	static <E> Predicate<E> and(Predicate<E>... elements) {
		return Predicates.group(elements).and();
	}

	@SafeVarargs
	static <E> Predicate<E> or(Predicate<E>... elements) {
		return Predicates.group(elements).or();
	}

	protected Predicates(List<Predicate<T>> elements) {
		super(elements);
	}

	@SafeVarargs
	protected Predicates(Predicate<T>... elements) {
		super(elements);
	}

	public Predicate<T> or() {
		return this
				.map(PredicateMapping.<T>invert())
				.reduceOptional(PredicateReduction.<T>conjunct())
				.getOr(Predicate.<T>never())
				.invert();
	}

	public Predicate<T> and() {
		return this
				.reduceOptional(PredicateReduction.<T>conjunct())
				.getOr(Predicate.<T>always());
	}

}
