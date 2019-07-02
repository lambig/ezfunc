package com.github.lambig.ezfunc.operand;

import java.util.ArrayList;
import java.util.List;

import com.github.lambig.ezfunc.function.Action;
import com.github.lambig.ezfunc.function.Mapping;
import com.github.lambig.ezfunc.function.Reduction;
import com.github.lambig.ezfunc.function.Supplier;
import com.github.lambig.ezfunc.function.predicate.Predicate;
import com.github.lambig.ezfunc.operand.impl.list.ListOperands;
import com.google.common.collect.Lists;

public class Optional<E> implements Operand<E>, Supplier<E> {

	private final E value;

	public static <O> Optional<O> of(O value) {
		return wrap(value);
	}

	private static <O> Optional<O> wrap(O value) {
		return new Optional<>(value);
	}

	private Optional(E value) {
		this.value = value;
	}

	@Override
	public boolean isEmpty() {
		return this.value == null;
	}

	@Override
	public boolean isNotEmpty() {
		return !this.isEmpty();
	}

	public E get() {
		return this.getOr(null);
	}

	public E getOr(E def) {
		return this.isEmpty() ? this.value : def;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> X reduce(Reduction<? super E, ? super X> r, X dest) {
		return (X) r.reduce(dest, this.value, 0);
	}

	@Override
	public <X> Optional<X> reduceOptional(Reduction<? super E, ? super X> r, X dest) {
		return wrap(this.reduce(r, dest));
	}

	@Override
	public Optional<E> filter(Predicate<? super E> f) {
		return f.eval(this.value, 0) ? this : new Optional<>(null);
	}

	@Override
	public <S> Optional<S> map(Mapping<? super E, S> mapper) {
		return wrap(this.isNotEmpty() ? mapper.map(this.value, 0) : null);
	}

	@Override
	public Optional<E> map(Mapping<? super E, E> mapper, Predicate<? super E> condition) {
		return condition.eval(this.value, 0) ? wrap(mapper.map(this.value, 0)) : this;
	}

	@Override
	public void forEach(Action<? super E> e) {
		if (this.isNotEmpty()) {
			e.exec(this.value, 0);
		}
	}

	@Override
	public List<E> asList() {
		return this.isNotEmpty() ? Lists.newArrayList(this.value) : new ArrayList<>();
	}

	@SafeVarargs
	@Override
	public final ListOperands<E> concat(E... elements) {
		return ListOperands.of(this.value).concat(elements);
	}

	@SafeVarargs
	@Override
	public final ListOperands<E> concat(List<E>... elements) {
		return ListOperands.of(this.value).concat(elements);
	}

	@Override
	public int size() {
		return 0;
	}

}
