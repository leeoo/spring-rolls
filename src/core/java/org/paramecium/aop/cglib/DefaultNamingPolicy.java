package org.paramecium.aop.cglib;

public class DefaultNamingPolicy implements NamingPolicy {
	public static final DefaultNamingPolicy INSTANCE = new DefaultNamingPolicy();

	public String getClassName(String prefix, String source, Object key,
			Predicate names) {
		if (prefix == null) {
			prefix = "net.sf.cglib.empty.Object";
		} else if (prefix.startsWith("java")) {
			prefix = "$" + prefix;
		}
		String base = prefix + "$$"
				+ source.substring(source.lastIndexOf('.') + 1) + getTag()
				+ "$$" + Integer.toHexString(key.hashCode());
		String attempt = base;
		int index = 2;
		while (names.evaluate(attempt))
			attempt = base + "_" + index++;
		return attempt;
	}

	protected String getTag() {
		return "ByCGLIB";
	}
}
