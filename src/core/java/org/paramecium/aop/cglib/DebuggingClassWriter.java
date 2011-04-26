package org.paramecium.aop.cglib;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.paramecium.aop.asm.ClassWriter;

@SuppressWarnings("unchecked")
public class DebuggingClassWriter extends ClassWriter {
    
    public static final String DEBUG_LOCATION_PROPERTY = "cglib.debugLocation";
    
    private static String debugLocation;
    
    private String className;
    private String superName;
    
    static {
        debugLocation = System.getProperty(DEBUG_LOCATION_PROPERTY);
        if (debugLocation != null) {
            System.err.println("CGLIB debugging enabled, writing to '" + debugLocation + "'");
            try {
                Class.forName("org.objectweb.asm.util.TraceClassVisitor");
            } catch (Throwable ignore) {
            }
        }
    }
    
    public DebuggingClassWriter(int flags) {
        super(flags);
    }

    public void visit(int version,
                      int access,
                      String name,
                      String signature,
                      String superName,
                      String[] interfaces) {
        className = name.replace('/', '.');
        this.superName = superName.replace('/', '.');
        super.visit(version, access, name, signature, superName, interfaces);
    }
    
    public String getClassName() {
        return className;
    }
    
    public String getSuperName() {
        return superName;
    }
    
    public byte[] toByteArray() {
        
      return (byte[]) java.security.AccessController.doPrivileged(
        new java.security.PrivilegedAction() {
            public Object run() {
                
                
                byte[] b = DebuggingClassWriter.super.toByteArray();
                if (debugLocation != null) {
                    String dirs = className.replace('.', File.separatorChar);
                    try {
                        new File(debugLocation + File.separatorChar + dirs).getParentFile().mkdirs();
                        
                        File file = new File(new File(debugLocation), dirs + ".class");
                        OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
                        try {
                            out.write(b);
                        } finally {
                            out.close();
                        }
                    } catch (IOException e) {
                    	e.printStackTrace();
                    }
                }
                return b;
             }  
            });
            
        }
    }
