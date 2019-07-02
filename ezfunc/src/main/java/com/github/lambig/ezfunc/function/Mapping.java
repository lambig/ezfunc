package com.github.lambig.ezfunc.function;

public abstract class Mapping<F, T> {
	public abstract T map(F current, int index);

	public final T map(F current) {
		return this.map(current);
	}
}
