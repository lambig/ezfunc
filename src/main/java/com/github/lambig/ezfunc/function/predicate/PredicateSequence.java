package com.github.lambig.ezfunc.function.predicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

public class PredicateSequence<T> extends Predicate<T> {
	private List<Predicate<T>> predicates;

	private PredicateSequence(List<Predicate<T>> predicates) {
		this.predicates = Collections.unmodifiableList(predicates);
	}

	@SafeVarargs
	static <K> PredicateSequence<K> sequence(Predicate<K>... predicates) {
		return new PredicateSequence<>(Lists.newArrayList(predicates));
	}

	public PredicateSequence<T> and(Predicate<T> next) {
		List<Predicate<T>> sum = new ArrayList<>(this.predicates);
		sum.add(next);
		return new PredicateSequence<>(sum);
	}

	public PredicateSequence<T> and(PredicateSequence<T> next) {
		List<Predicate<T>> sum = new ArrayList<>(this.predicates);
		sum.add(next);
		return new PredicateSequence<>(sum);
	}

	@Override
	public boolean eval(T t, int index) {
		for (Predicate<T> predicate : this.predicates) {
			if (!predicate.eval(t, index)) {
				return false;
			}
		}
		return true;
	}
}