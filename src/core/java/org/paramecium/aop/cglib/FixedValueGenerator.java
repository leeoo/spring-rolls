package org.paramecium.aop.cglib;

import java.util.Iterator;
import java.util.List;

import org.paramecium.aop.asm.Type;

@SuppressWarnings("unchecked")
class FixedValueGenerator implements CallbackGenerator {
	public static final FixedValueGenerator INSTANCE = new FixedValueGenerator();
	private static final Type FIXED_VALUE = TypeUtils
			.parseType("net.sf.cglib.proxy.FixedValue");
	private static final Signature LOAD_OBJECT = TypeUtils
			.parseSignature("Object loadObject()");

	public void generate(ClassEmitter ce, Context context, List methods) {
		for (Iterator it = methods.iterator(); it.hasNext();) {
			MethodInfo method = (MethodInfo) it.next();
			CodeEmitter e = context.beginMethod(ce, method);
			context.emitCallback(e, context.getIndex(method));
			e.invoke_interface(FIXED_VALUE, LOAD_OBJECT);
			e.unbox_or_zero(e.getReturnType());
			e.return_value();
			e.end_method();
		}
	}

	public void generateStatic(CodeEmitter e, Context context, List methods) {
	}
}
