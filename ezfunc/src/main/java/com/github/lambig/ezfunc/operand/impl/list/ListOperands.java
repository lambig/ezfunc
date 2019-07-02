package com.github.lambig.ezfunc.operand.impl.list;

import static com.github.lambig.ezfunc.function.predicate.Predicate.not;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.github.lambig.ezfunc.function.Action;
import com.github.lambig.ezfunc.function.Comparison;
import com.github.lambig.ezfunc.function.Mapping;
import com.github.lambig.ezfunc.function.Reduction;
import com.github.lambig.ezfunc.function.comparison.ComparisonMapping;
import com.github.lambig.ezfunc.function.predicate.Predicate;
import com.github.lambig.ezfunc.function.predicate.function.PredicateMapping;
import com.github.lambig.ezfunc.operand.Operands;
import com.github.lambig.ezfunc.operand.Optional;
import com.google.common.collect.Lists;

public class ListOperands<E> implements Operands<E> {
	private final List<E> list;

	public static <O> ListOperands<O> of(Collection<O> list) {
		return wrap(list);
	}

	@SafeVarargs
	public static <O> ListOperands<O> of(O... elements) {
		return new ListOperands<>(elements);
	}

	private static <O> ListOperands<O> wrap(Collection<O> list) {
		return new ListOperands<>(list);
	}

	protected ListOperands(Collection<E> list) {
		this.list = new ArrayList<>(list);
	}

	public ListOperands(E[] elements) {
		this.list = elements == null ? new ArrayList<>() : Lists.newArrayList(elements);
	}

	@Override
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	@Override
	public <X> X reduce(Reduction<? super E, ? super X> r, X dest) {
		return Handler.reduce(this.asList(), r, dest);
	}

	@Override
	public E reduce(Reduction<? super E, ? super E> r) {
		return ListOperands.of(this.asList().subList(1, this.size())).reduce(r, this.first());
	}

	@Override
	public <X> Optional<X> reduceOptional(Reduction<? super E, ? super X> r, X dest) {
		return Optional.of(this.reduce(r, dest));
	}

	@Override
	public Optional<E> reduceOptional(Reduction<? super E, ? super E> r) {
		return Optional.of(this.reduce(r, this.first()));
	}

	@Override
	public ListOperands<E> filter(Predicate<? super E> f) {
		return wrap(Handler.filter(this.list, f));
	}

	@Override
	public <S> ListOperands<S> map(Mapping<? super E, S> mapping) {
		return wrap(Handler.map(this.list, mapping));
	}

	@Override
	public ListOperands<E> map(Mapping<? super E, E> mapper, Predicate<? super E> condition) {
		return null;
	}

	@Override
	public void forEach(Action<? super E> a) {
		Handler.forEach(this.list, a);
	}

	@Override
	public List<E> asList() {
		return new ArrayList<>(this.list);
	}

	@SafeVarargs
	@Override
	public final ListOperands<E> concat(E... elements) {
		return wrap(Handler.concat(this.list, elements));
	}

	@SafeVarargs
	@Override
	public final ListOperands<E> concat(List<E>... elements) {
		return wrap(Handler.concat(this.list, elements));
	}

	@Override
	public int size() {
		return this.list.size();
	}

	@Override
	public E first() {
		return this.firstOr(null);
	}

	@Override
	public E first(Predicate<? super E> condition) {
		return this.filter(condition).first();
	}

	@Override
	public E firstOr(E def) {
		return this.isNotEmpty() ? this.list.get(0) : def;
	}

	@Override
	public E firstOr(Predicate<? super E> condition, E def) {
		return this.filter(condition).firstOr(def);
	}

	@Override
	public boolean isNotEmpty() {
		return !this.isEmpty();
	}

	@Override
	public ListOperands<E> sort(Comparison<? super E> c) {
		List<E> sorted = new ArrayList<>(this.list);
		Collections.sort(sorted, c.toComparator());
		return wrap(sorted);
	}

	@Override
	public <X> ListOperands<E> sort(Comparison<? super X> c, Mapping<? super E, X> extraction) {
		Comparison<? super E> ownerComparison = Optional.of(c).map(ComparisonMapping.escalate(extraction)).get();
		return this.sort(ownerComparison);
	}

	@Override
	public ListOperands<E> sort(Set<E> sequence) {
		return ListOperands.of(new ArrayList<>(sequence)).map(PredicateMapping.equalTo())
				.map(PredicateMapping.firstSatisfierIn(this.list)).filter(not(Predicate.IS_NULL));
	}

	@Override
	public <X> ListOperands<E> sort(Set<X> sequence, Mapping<? super E, X> extraction) {
		return ListOperands.of(new ArrayList<>(sequence)).map(PredicateMapping.having(extraction))
				.map(PredicateMapping.firstSatisfierIn(this.list)).filter(not(Predicate.IS_NULL));
	}

}
