package com.xqh.jvm.processor;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner6;
import javax.tools.Diagnostic;

/**
 *
 *  =--------------发现书上代码 跟 jdk1.8 的貌似不一样
 * 程序命名规范的编译器插件
 * 如果程序名称不符合规范,将会输出一个编译器的WARNING信息
 */
public class NameChecker {

    private final Messager messager;

    NameCheckScanner nameCheckScanner = new NameCheckScanner();

    NameChecker(ProcessingEnvironment processingEvn){
        this.messager=processingEvn.getMessager();
    }

//    public void checkNames(Element)


    /**
     * 名称检查器实现类,集成了JDK 1.6新提供的ElementScanner6
     * 将会以Visitor模式访问抽象语法树中的元素
     */
    private class NameCheckScanner extends ElementScanner6<Void,Void>{
        @Override
        public Void visitType(TypeElement e, Void p) {
            scan(e.getTypeParameters(), p);
//            checkCamelCase(e, true);
            return super.visitType(e, p);
        }

        @Override
        public Void visitExecutable(ExecutableElement e, Void p) {
            if(e.getKind() == ElementKind.METHOD){
                Name name = e.getSimpleName();
                if(name.contentEquals(e.getEnclosingElement().getSimpleName())){
                    messager.printMessage(Diagnostic.Kind.WARNING, "一个普通方法"+name+"不应当与类名重复,避免与构造函数产生混淆", e);
                }
            }
            return super.visitExecutable(e, p);
        }

        @Override
        public Void visitVariable(VariableElement e, Void p) {
            if(e.getKind() ==ElementKind.ENUM_CONSTANT || e.getConstantValue() != null)
             super.visitVariable(e, p);
            return null;
        }
    }

}
