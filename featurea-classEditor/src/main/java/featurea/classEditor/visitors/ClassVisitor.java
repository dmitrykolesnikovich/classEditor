package featurea.classEditor.visitors;

import featurea.classEditor.classfile.*;
import featurea.classEditor.classfile.attributes.*;

public abstract interface ClassVisitor {
    public abstract void visitClass(ClassFile paramClassFile);

    public abstract void visitAccessFlags(AccessFlags paramAccessFlags);

    public abstract void visitClassNames(ClassNames paramClassNames);

    public abstract void visitConstantPool(ConstantPool paramConstantPool);

    public abstract void visitConstantPoolInfo(ConstantPoolInfo paramConstantPoolInfo);

    public abstract void visitFields(Fields paramFields);

    public abstract void visitFieldInfo(FieldInfo paramFieldInfo);

    public abstract void visitInterfaces(Interfaces paramInterfaces);

    public abstract void visitMethods(Methods paramMethods);

    public abstract void visitMethodInfo(MethodInfo paramMethodInfo);

    public abstract void visitVersion(Version paramVersion);

    public abstract void visitAttribute(Attribute paramAttribute);

    public abstract void visitAttributes(Attributes paramAttributes);

    public abstract void visitCodeAttribute(CodeAttribute paramCodeAttribute);

    public abstract void visitCode(Code paramCode);

    public abstract void visitConstantValueAttribute(ConstantValueAttribute paramConstantValueAttribute);

    public abstract void visitDeprecatedAttribute(DeprecatedAttribute paramDeprecatedAttribute);

    public abstract void visitExceptionsAttribute(ExceptionsAttribute paramExceptionsAttribute);

    public abstract void visitExceptionTableEntry(ExceptionTableEntry paramExceptionTableEntry);

    public abstract void visitInnerClassesAttribute(InnerClassesAttribute paramInnerClassesAttribute);

    public abstract void visitInnerClassInfo(InnerClassInfo paramInnerClassInfo);

    public abstract void visitInstruction(Instruction paramInstruction);

    public abstract void visitInstructions(Instructions paramInstructions);

    public abstract void visitLineNumberTableAttribute(LineNumberTableAttribute paramLineNumberTableAttribute);

    public abstract void visitLineNumberTableEntry(LineNumberTableEntry paramLineNumberTableEntry);

    public abstract void visitLocalVariableTableAttribute(LocalVariableTableAttribute paramLocalVariableTableAttribute);

    public abstract void visitLocalVariableTableEntry(LocalVariableTableEntry paramLocalVariableTableEntry);

    public abstract void visitSourceFileAttribute(SourceFileAttribute paramSourceFileAttribute);

    public abstract void visitSyntheticAttribute(SyntheticAttribute paramSyntheticAttribute);

    public abstract void visitUnknownAttribute(UnknownAttribute paramUnknownAttribute);
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/visitors/ClassVisitor.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */