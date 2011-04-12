package org.cy.core.aop.cglib;

import org.cy.core.aop.asm.Type;

public class Local {
	private Type type;
	private int index;

	public Local(int index, Type type) {
		this.type = type;
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public Type getType() {
		return type;
	}
}
