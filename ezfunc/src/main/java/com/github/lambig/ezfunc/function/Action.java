package com.github.lambig.ezfunc.function;

public abstract class Action<E> {
	public abstract void exec(E current, int index);

	public final void exec(E current) {
		this.exec(current);
	}

}
