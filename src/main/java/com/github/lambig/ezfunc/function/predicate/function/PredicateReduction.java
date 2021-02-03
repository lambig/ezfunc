package com.github.lambig.ezfunc.function.predicate.function;

import com.github.lambig.ezfunc.function.Reduction;
import com.github.lambig.ezfunc.function.predicate.Predicate;

public abstract class PredicateReduction {
	public static <E> Reduction<Predicate<E>, Predicate<E>> conjunct() {
		return new Reduction<Predicate<E>, Predicate<E>>() {
			@Override
			public Predicate<E> reduce(Predicate<E> accum, Predicate<E> current, int index) {
				return accum.and(current);
			}
		};
	}
}
