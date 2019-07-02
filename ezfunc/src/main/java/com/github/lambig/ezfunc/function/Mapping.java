package com.github.lambig.ezfunc.function;

public interface Mapping<F, T> {
	T map(F current, int index);
}
