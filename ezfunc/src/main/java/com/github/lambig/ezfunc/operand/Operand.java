package com.github.lambig.ezfunc.operand;

import java.util.List;

import com.github.lambig.ezfunc.function.Action;
import com.github.lambig.ezfunc.function.Mapping;
import com.github.lambig.ezfunc.function.Reduction;
import com.github.lambig.ezfunc.function.predicate.Predicate;

public interface Operand<T> {
	/**
	 * returns if the Operand is empty
	 *
	 * @return true if empty
	 */
	boolean isEmpty();

	/**
	 * returns if the Operand is not empty
	 *
	 * @return true if empty
	 */
	boolean isNotEmpty();

	/**
	 * reduces element[s] to dest by reduction
	 *
	 * @param <X>  type of destination
	 * @param r    reduction
	 * @param dest destination
	 * @return reduced object
	 */
	<X> X reduce(Reduction<? super T, ? super X> r, X dest);

	/**
	 * reduces element[s] to Optional of dest by reduction
	 *
	 * @param <X>  type of destination
	 * @param r    reduction
	 * @param dest destination
	 * @return reduced object's Optional
	 */
	<X> Optional<X> reduceOptional(Reduction<? super T, ? super X> r, X dest);

	Operand<T> filter(Predicate<? super T> f);

	<S> Operand<S> map(Mapping<? super T, S> mapper);

	Operand<T> map(Mapping<? super T, T> mapper, Predicate<? super T> condition);

	void forEach(Action<? super T> e);

	List<T> asList();

	Operand<T> concat(@SuppressWarnings("unchecked") T... elements);

	Operand<T> concat(@SuppressWarnings("unchecked") List<T>... elements);

	int size();
}
