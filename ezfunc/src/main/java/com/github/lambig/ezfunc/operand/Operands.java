package com.github.lambig.ezfunc.operand;

import java.util.Set;

import com.github.lambig.ezfunc.function.Comparison;
import com.github.lambig.ezfunc.function.Mapping;
import com.github.lambig.ezfunc.function.Reduction;
import com.github.lambig.ezfunc.function.predicate.Predicate;

public interface Operands<T> extends Operand<T> {
	T reduce(Reduction<? super T, ? super T> r);

	Optional<T> reduceOptional(Reduction<? super T, ? super T> r);

	Operands<T> sort(Comparison<? super T> c);

	<X> Operands<T> sort(Comparison<? super X> c, Mapping<? super T, X> extraction);

	Operands<T> sort(Set<T> sequence);

	<X> Operands<T> sort(Set<X> sequence, Mapping<? super T, X> extraction);

	T first();

	T first(Predicate<? super T> condition);

	T firstOr(T def);

	T firstOr(Predicate<? super T> condition, T def);
}
