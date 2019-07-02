package com.github.lambig.ezfunc.function;

public abstract class Reduction<F, T> {
	public abstract T reduce(T accum, F current, int index);

	public final T reduce(T accum, F current) {
		return this.reduce(accum, current);
	}
}
