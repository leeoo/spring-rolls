package org.cy.core.test;

import org.cy.core.aop.asm.ClassAdapter;
import org.cy.core.aop.asm.ClassVisitor;
import org.cy.core.aop.asm.MethodAdapter;
import org.cy.core.aop.asm.MethodVisitor;
import org.cy.core.aop.asm.Opcodes;

public class SecurityProxy extends ClassAdapter {

	public SecurityProxy(ClassVisitor cv) {
		super(cv);
	}

	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature,
				exceptions);

		MethodVisitor wrappedMv = mv;
		if (mv != null) {
			// 对于 "operation" 方法
			if (name.equals("test")) {
				// 使用自定义 MethodVisitor，实际改写方法内容
				wrappedMv = new SecurityCheckMethodAdapter(mv);
			}
		}
		return wrappedMv;

	}

	class SecurityCheckMethodAdapter extends MethodAdapter implements MethodVisitor {

		public SecurityCheckMethodAdapter(MethodVisitor mv) {
			super(mv);
		}

		public void visitCode() {
			visitMethodInsn(Opcodes.INVOKESTATIC, "org.cy.core.test.Security","check", "()V");
		}

	}

}
