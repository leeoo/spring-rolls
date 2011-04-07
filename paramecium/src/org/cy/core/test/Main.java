package org.cy.core.test;

import java.io.File;
import java.io.FileOutputStream;

import org.cy.core.aop.asm.ClassAdapter;
import org.cy.core.aop.asm.ClassReader;
import org.cy.core.aop.asm.ClassWriter;


public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println("............................");
		ClassReader cr = new ClassReader("org.cy.core.test.Account");
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		ClassAdapter classAdapter = new SecurityProxy(cw);
		cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
		byte[] data = cw.toByteArray();
		File file = new File("D://workspace//paramecium//WebRoot//WEB-INF//classes//org//cy//core//test//Account.class");
		FileOutputStream fout = new FileOutputStream(file);
		fout.write(data);
		fout.close();
		System.out.println("222222222222222222222222222");
		Account account1 = new Account();
		account1.test();
	}

}
