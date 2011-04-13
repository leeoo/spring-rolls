package org.cy.core.ioc;

public class ClassInfo {

	public static enum CLASS_LOAD_TYPE {
		SERVICE, CONTROLLER;
	}

	private CLASS_LOAD_TYPE loadType;
	private Class<?> clazz;
	private String className;

	public CLASS_LOAD_TYPE getLoadType() {
		return loadType;
	}

	public void setLoadType(CLASS_LOAD_TYPE loadType) {
		this.loadType = loadType;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
}
