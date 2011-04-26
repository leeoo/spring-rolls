package org.paramecium.aop.cglib;

import java.util.List;
@SuppressWarnings("unchecked")
interface CallbackGenerator {
	void generate(ClassEmitter ce, Context context, List methods)
			throws Exception;

	void generateStatic(CodeEmitter e, Context context, List methods)
			throws Exception;

	interface Context {
		ClassLoader getClassLoader();

		CodeEmitter beginMethod(ClassEmitter ce, MethodInfo method);

		int getOriginalModifiers(MethodInfo method);

		int getIndex(MethodInfo method);

		void emitCallback(CodeEmitter ce, int index);

		Signature getImplSignature(MethodInfo method);
	}
}
