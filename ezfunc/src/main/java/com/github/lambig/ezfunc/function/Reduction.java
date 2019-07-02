package com.github.lambig.ezfunc.function;

public interface Reduction<F, T> {
	public T reduce(T accum, F current, int index);
}
