package org.cy.core.test;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

import org.cy.core.aop.asm.ClassWriter;
import org.cy.core.aop.asm.MethodVisitor;
import org.cy.core.aop.asm.Opcodes;

public class Main {

	public static void main(String[] args) throws Exception {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cw.visit(Opcodes.V1_6, Opcodes.ACC_PUBLIC, "org/cy/core/test/AA", null,"java/lang/Object", null);// 类访问开始：必须
		MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "", "()V", null,null);// 至少提供一个构造函数
		mv.visitCode();// 代码开始：必须
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "","()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);// 计算栈和局部变量最大空间：必须
		mv.visitEnd();// 代码结束：必须

		mv = cw.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "main","([Ljava/lang/String;)V", null, null);
		mv.visitCode();
		mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out","Ljava/io/PrintStream;");
		mv.visitLdcInsn("测试一下，Hello World!");
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream","println", "(Ljava/lang/String;)V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		cw.visitEnd();// 类结束：必须

		final byte[] bs = cw.toByteArray();
	/*	Class clazz = new ClassLoader() {
			protected Class findClass(String name)
					throws ClassNotFoundException {
				return defineClass(name, bs, 0, bs.length);
			}
		}.loadClass("org.cy.core.test.CT");
		Method method = clazz.getMethod("main", new Class[] { String[].class });
		// 数组参数的方法，反射调用方式看起来比较古怪
		method.invoke(null, (Object) new String[0]);*/

		//for (int i = 0; i < bs.length; i++)
			//System.out.printf("%d:\t%02X\t%c\n", i, bs[i], (char) bs[i]);

		String classPath = ClassLoader.getSystemResource("").getFile()+"org/cy/core/test/AA.class";
		OutputStream out = new FileOutputStream(classPath);
		out.write(bs);
		out.close();
		
		Class<?> clazz = new ClassLoader() {
			protected Class<?> findClass(String name)
					throws ClassNotFoundException {
				return defineClass(name, bs, 0, bs.length);
			}
		}.loadClass("org.cy.core.test.AA");
		Method method = clazz.getMethod("main", new Class[] { String[].class });
		// 数组参数的方法，反射调用方式看起来比较古怪
		method.invoke(null, (Object) new String[0]);
	}

}
