package ru.otus.l022;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author sergey
 * created on 16.07.18.
 */
public class ASMdemoCreateClass {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final String className = "HelloWorld";

        //Генератор классов
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V10, Opcodes.ACC_PUBLIC, className, null, "java/lang/Object", null);

        //Конструктор класса
        MethodVisitor constructor =  cw.visitMethod(Opcodes.ACC_PUBLIC,"<init>", "()V", null,null);
        constructor.visitCode();

        //Вызываем конструктор предка (Object)
        constructor.visitVarInsn(Opcodes.ALOAD, 0);
        constructor.visitMethodInsn(Opcodes.INVOKESPECIAL,  "java/lang/Object", "<init>", "()V", false);
        constructor.visitInsn(Opcodes.RETURN);
        constructor.visitMaxs(0, 0);
        constructor.visitEnd();

        //Создаем метод printHi
        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,  "printHi", "()V", null, null);

        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System","out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("Hello, World!");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream","println", "(Ljava/lang/String;)V", false);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        //Загружаем полученный класс
        Class<?> helloWorldClass = new MyClassLoader().defineClass(className,  cw.toByteArray());

        //Выполняем метод main
        Method method = helloWorldClass.getMethod("printHi");
        method.invoke(null);


    }

    //Самодельный ClassLoader
    public static class MyClassLoader extends ClassLoader {
        Class<?> defineClass(String className, byte[] bytecode) {
            return super.defineClass(className, bytecode, 0, bytecode.length);
        }
    };


}
