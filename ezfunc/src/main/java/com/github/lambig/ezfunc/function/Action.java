package com.github.lambig.ezfunc.function;

public interface Action<E> {
	void exec(E current, int index);
}
