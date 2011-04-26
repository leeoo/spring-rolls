package org.paramecium.aop.cglib;

import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
class NoOpGenerator implements CallbackGenerator {
	public static final NoOpGenerator INSTANCE = new NoOpGenerator();

	public void generate(ClassEmitter ce, Context context, List methods) {
		for (Iterator it = methods.iterator(); it.hasNext();) {
			MethodInfo method = (MethodInfo) it.next();
			if (TypeUtils.isProtected(context.getOriginalModifiers(method))
					&& TypeUtils.isPublic(method.getModifiers())) {
				CodeEmitter e = EmitUtils.begin_method(ce, method);
				e.load_this();
				e.load_args();
				e.super_invoke();
				e.return_value();
				e.end_method();
			}
		}
	}

	public void generateStatic(CodeEmitter e, Context context, List methods) {
	}
}
