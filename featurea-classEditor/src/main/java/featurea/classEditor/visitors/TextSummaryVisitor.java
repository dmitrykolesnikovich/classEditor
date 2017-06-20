package featurea.classEditor.visitors;

import featurea.classEditor.classfile.*;
import featurea.classEditor.classfile.attributes.*;

public class TextSummaryVisitor
        extends NavigatingClassVisitor {
    StringBuffer summary = new StringBuffer(4096);
    String sPrepend = "";
    int iConstantPoolIndex;
    int iFieldIndex;
    int iMethodIndex;
    boolean bNoCode;

    public TextSummaryVisitor() {
    }

    public TextSummaryVisitor(boolean paramBoolean) {
        this.bNoCode = paramBoolean;
    }

    public StringBuffer getSummary() {
        return this.summary;
    }

    public void visitAccessFlags(AccessFlags paramAccessFlags) {
        this.summary.append(this.sPrepend).append(Utils.sUnderLine).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("Access flags").append(Utils.sNewLine).append(this.sPrepend).append(Utils.sUnderLine).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append(paramAccessFlags.toString()).append(Utils.sNewLine);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitAccessFlags(paramAccessFlags);
        this.sPrepend = str;
    }

    public void visitAttributes(Attributes paramAttributes) {
        this.summary.append(this.sPrepend).append(Utils.sUnderLine).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("Attributes");
        this.summary.append(this.sPrepend).append(Utils.sNewLine).append(this.sPrepend).append(Utils.sUnderLine).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("Attributes count: ").append(paramAttributes.iAttributesCount).append(Utils.sNewLine);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitAttributes(paramAttributes);
        this.sPrepend = str;
    }

    public void visitClass(ClassFile paramClassFile) {
        this.summary.append(this.sPrepend).append("Summary for class ").append(paramClassFile.classNames.getThisClassName()).append(Utils.sNewLine);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitClass(paramClassFile);
        this.sPrepend = str;
    }

    public void visitClassNames(ClassNames paramClassNames) {
        this.summary.append(this.sPrepend).append(Utils.sUnderLine).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("Class Names").append(Utils.sNewLine).append(this.sPrepend).append(Utils.sUnderLine).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("This class: ").append(paramClassNames.getThisClassName()).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("Super class: ").append(paramClassNames.getSuperClassName()).append(Utils.sNewLine);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitClassNames(paramClassNames);
        this.sPrepend = str;
    }

    public void visitCode(Code paramCode) {
        if (this.bNoCode) {
            return;
        }
        int j;
        int i = j = 0;
        this.summary.append(this.sPrepend).append("------Begin Bytecode-------").append(Utils.sNewLine);
        String str1;
        while (null != (str1 = paramCode.getNextInstruction())) {
            this.summary.append(this.sPrepend).append(str1).append(Utils.sNewLine);
        }
        this.summary.append(this.sPrepend).append("------End Bytecode-------").append(Utils.sNewLine);
        String str2 = this.sPrepend;
        this.sPrepend += "\t";
        super.visitCode(paramCode);
        this.sPrepend = str2;
    }

    public void visitCodeAttribute(CodeAttribute paramCodeAttribute) {
        this.summary.append(this.sPrepend).append(paramCodeAttribute.toString()).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("Max stack: ").append(paramCodeAttribute.iMaxStack);
        this.summary.append(this.sPrepend).append("Max locals: ").append(paramCodeAttribute.iMaxLocals).append(Utils.sNewLine);
        if (0 < paramCodeAttribute.vectExceptionTableEntries.size()) {
            this.summary.append(this.sPrepend).append("Exceptions Table").append(Utils.sNewLine);
            this.summary.append(this.sPrepend).append("\tCatch type : Start PC : End PC : Handler PC").append(Utils.sNewLine);
        }
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitCodeAttribute(paramCodeAttribute);
        this.sPrepend = str;
    }

    public void visitConstantPool(ConstantPool paramConstantPool) {
        int i = paramConstantPool.getPoolInfoCount();
        this.summary.append(this.sPrepend).append(Utils.sUnderLine).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("Constant Pool").append(Utils.sNewLine).append(this.sPrepend).append(Utils.sUnderLine).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("Constant pool count: ").append(i + 1).append(Utils.sNewLine);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        this.iConstantPoolIndex = 0;
        super.visitConstantPool(paramConstantPool);
        this.sPrepend = str;
    }

    public void visitConstantPoolInfo(ConstantPoolInfo paramConstantPoolInfo) {
        this.summary.append(this.sPrepend).append(this.iConstantPoolIndex + 1).append(": ");
        this.summary.append(paramConstantPoolInfo.toString()).append(Utils.sNewLine);
        this.iConstantPoolIndex += 1;
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitConstantPoolInfo(paramConstantPoolInfo);
        this.sPrepend = str;
    }

    public void visitConstantValueAttribute(ConstantValueAttribute paramConstantValueAttribute) {
        this.summary.append(this.sPrepend).append("Attribute ").append(paramConstantValueAttribute.sName).append(". Type=").append(paramConstantValueAttribute.sConstType).append(". Value=").append(paramConstantValueAttribute.sConstValue).append(Utils.sNewLine);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitConstantValueAttribute(paramConstantValueAttribute);
        this.sPrepend = str;
    }

    public void visitDeprecatedAttribute(DeprecatedAttribute paramDeprecatedAttribute) {
        this.summary.append(this.sPrepend).append("Attribute ").append("Deprecated");
        this.summary.append(Utils.sNewLine);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitDeprecatedAttribute(paramDeprecatedAttribute);
        this.sPrepend = str;
    }

    public void visitExceptionTableEntry(ExceptionTableEntry paramExceptionTableEntry) {
        this.summary.append(this.sPrepend).append(null == paramExceptionTableEntry.cpCatchType ? "all" : Utils.convertClassStrToStr(paramExceptionTableEntry.cpCatchType.refUTF8.sUTFStr)).append(" : ");
        this.summary.append(Integer.toString(paramExceptionTableEntry.iStartPC)).append(" : ");
        this.summary.append(Integer.toString(paramExceptionTableEntry.iEndPC)).append(" : ");
        this.summary.append(Integer.toString(paramExceptionTableEntry.iHandlerPC)).append(Utils.sNewLine);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitExceptionTableEntry(paramExceptionTableEntry);
        this.sPrepend = str;
    }

    public void visitExceptionsAttribute(ExceptionsAttribute paramExceptionsAttribute) {
        this.summary.append(this.sPrepend).append("Attribute ").append(paramExceptionsAttribute.sName).append(". Number=").append(paramExceptionsAttribute.iNumExceptions);
        for (int i = 0; i < paramExceptionsAttribute.iNumExceptions; i++) {
            ConstantPoolInfo localConstantPoolInfo = (ConstantPoolInfo) paramExceptionsAttribute.vectExceptionTypes.elementAt(i);
            this.summary.append(", ").append(Utils.convertClassStrToStr(localConstantPoolInfo.refUTF8.sUTFStr));
        }
        this.summary.append(Utils.sNewLine);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitExceptionsAttribute(paramExceptionsAttribute);
        this.sPrepend = str;
    }

    public void visitFieldInfo(FieldInfo paramFieldInfo) {
        this.iFieldIndex += 1;
        this.summary.append(this.sPrepend).append(this.iFieldIndex).append(": ");
        this.summary.append(paramFieldInfo.accessFlags.toString()).append(" ");
        this.summary.append(paramFieldInfo.getFieldDescriptor()).append(" ").append(paramFieldInfo.getFieldName()).append(Utils.sNewLine);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitFieldInfo(paramFieldInfo);
        this.sPrepend = str;
    }

    public void visitFields(Fields paramFields) {
        this.summary.append(this.sPrepend).append(Utils.sUnderLine).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("Fields").append(Utils.sNewLine).append(this.sPrepend).append(Utils.sUnderLine).append(Utils.sNewLine);
        int i = paramFields.getFieldsCount();
        this.summary.append(this.sPrepend).append("Number of fields: ").append(i).append(Utils.sNewLine);
        this.iFieldIndex = 0;
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitFields(paramFields);
        this.sPrepend = str;
    }

    public void visitInnerClassInfo(InnerClassInfo paramInnerClassInfo) {
        if (null != paramInnerClassInfo.cpInnerClass) {
            this.summary.append(Utils.sNewLine);
            this.summary.append(this.sPrepend).append("InnerClass: ").append(Utils.convertClassStrToStr(paramInnerClassInfo.cpInnerClass.refUTF8.sUTFStr));
        }
        if (null != paramInnerClassInfo.cpOuterClass) {
            this.summary.append(Utils.sNewLine);
            this.summary.append(this.sPrepend).append("OuterClass: ").append(Utils.convertClassStrToStr(paramInnerClassInfo.cpOuterClass.refUTF8.sUTFStr));
        }
        if (null != paramInnerClassInfo.cpInnerName) {
            this.summary.append(Utils.sNewLine);
            this.summary.append(this.sPrepend).append("Name: ").append(paramInnerClassInfo.cpInnerName.sUTFStr);
        }
        this.summary.append(Utils.sNewLine);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitInnerClassInfo(paramInnerClassInfo);
        this.sPrepend = str;
    }

    public void visitInnerClassesAttribute(InnerClassesAttribute paramInnerClassesAttribute) {
        this.summary.append(this.sPrepend).append("Attribute ").append("InnerClasses ").append(". Number=").append(paramInnerClassesAttribute.getNumClasses());
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitInnerClassesAttribute(paramInnerClassesAttribute);
        this.sPrepend = str;
        this.summary.append(Utils.sNewLine);
    }

    public void visitInstruction(Instruction paramInstruction) {
    }

    public void visitInstructions(Instructions paramInstructions) {
    }

    public void visitInterfaces(Interfaces paramInterfaces) {
        int i = paramInterfaces.getInterfacesCount();
        this.summary.append(this.sPrepend).append(Utils.sUnderLine).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("Interfaces").append(Utils.sNewLine).append(this.sPrepend).append(Utils.sUnderLine).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("Interfaces count: ").append(i).append(Utils.sNewLine);
        for (int j = 0; j < i; j++) {
            this.summary.append(this.sPrepend).append(j + 1).append(": ").append(paramInterfaces.getInterfaceName(j)).append(Utils.sNewLine);
        }
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitInterfaces(paramInterfaces);
        this.sPrepend = str;
    }

    public void visitLineNumberTableAttribute(LineNumberTableAttribute paramLineNumberTableAttribute) {
        int i = paramLineNumberTableAttribute.vectEntries.size();
        this.summary.append(this.sPrepend).append("Attribute ").append(paramLineNumberTableAttribute.sName);
        this.summary.append(". TableLength=").append(i).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("\tLine : Start Program Counter").append(Utils.sNewLine);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitLineNumberTableAttribute(paramLineNumberTableAttribute);
        this.sPrepend = str;
    }

    public void visitLineNumberTableEntry(LineNumberTableEntry paramLineNumberTableEntry) {
        this.summary.append(this.sPrepend).append(Integer.toString(paramLineNumberTableEntry.iLineNum)).append(" : ").append(Integer.toString(paramLineNumberTableEntry.iStartPC)).append(Utils.sNewLine);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitLineNumberTableEntry(paramLineNumberTableEntry);
        this.sPrepend = str;
    }

    public void visitLocalVariableTableAttribute(LocalVariableTableAttribute paramLocalVariableTableAttribute) {
        this.summary.append(this.sPrepend).append("Attribute ").append(paramLocalVariableTableAttribute.sName);
        this.summary.append(". TableLength=").append(paramLocalVariableTableAttribute.vectLocalVariableTable.size()).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("\tPosition : Start PC : Length : Descriptor : Name").append(Utils.sNewLine);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitLocalVariableTableAttribute(paramLocalVariableTableAttribute);
        this.sPrepend = str;
    }

    public void visitLocalVariableTableEntry(LocalVariableTableEntry paramLocalVariableTableEntry) {
        this.summary.append(this.sPrepend);
        this.summary.append(Integer.toString(paramLocalVariableTableEntry.iIndex)).append(" : ");
        this.summary.append(Integer.toString(paramLocalVariableTableEntry.iStartPC)).append(" : ");
        this.summary.append(Integer.toString(paramLocalVariableTableEntry.iLength)).append(" : ");
        if (null != paramLocalVariableTableEntry.cpDescriptor) {
            this.summary.append(Utils.getReadableDesc(paramLocalVariableTableEntry.cpDescriptor.sUTFStr)).append(" : ");
        } else {
            this.summary.append("null (possibly obfuscated) : ");
        }
        if (null != paramLocalVariableTableEntry.cpName) {
            this.summary.append(paramLocalVariableTableEntry.cpName.sUTFStr);
        } else {
            this.summary.append("null (possibly obfuscated)");
        }
        this.summary.append(Utils.sNewLine);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitLocalVariableTableEntry(paramLocalVariableTableEntry);
        this.sPrepend = str;
    }

    public void visitMethodInfo(MethodInfo paramMethodInfo) {
        this.iMethodIndex += 1;
        this.summary.append(this.sPrepend).append(this.iMethodIndex).append(": ");
        this.summary.append(paramMethodInfo.accessFlags).append(" ");
        String[] arrayOfString = Utils.getReadableMethodDesc(paramMethodInfo.cpDescriptor.sUTFStr);
        if (arrayOfString.length > 0) {
            this.summary.append(arrayOfString[0]).append(" ");
        }
        this.summary.append(paramMethodInfo.cpName.sUTFStr).append("(");
        for (int i = 1; i < arrayOfString.length; i++) {
            this.summary.append(arrayOfString[i]);
            if (i + 1 < arrayOfString.length) {
                this.summary.append(", ");
            }
        }
        this.summary.append(")");
        this.summary.append(Utils.sNewLine).append(this.sPrepend).append("{").append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append(Utils.sUnderLine).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("Attributes");
        this.summary.append(this.sPrepend).append(Utils.sNewLine).append(this.sPrepend).append(Utils.sUnderLine).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("Attributes count: ").append(paramMethodInfo.attributes.iAttributesCount).append(Utils.sNewLine);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitMethodInfo(paramMethodInfo);
        this.sPrepend = str;
        this.summary.append(this.sPrepend).append("}").append(Utils.sNewLine);
        this.summary.append(Utils.sNewLine);
    }

    public void visitMethods(Methods paramMethods) {
        this.summary.append(this.sPrepend).append(Utils.sUnderLine).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("Methods").append(Utils.sNewLine).append(this.sPrepend).append(Utils.sUnderLine).append(Utils.sNewLine);
        int i = paramMethods.getMethodsCount();
        this.summary.append(this.sPrepend).append("Methods count: ").append(i).append(Utils.sNewLine);
        this.iMethodIndex = 0;
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitMethods(paramMethods);
        this.sPrepend = str;
    }

    public void visitSourceFileAttribute(SourceFileAttribute paramSourceFileAttribute) {
        this.summary.append(this.sPrepend).append("Attribute ").append(paramSourceFileAttribute.sName).append(". Source=").append(paramSourceFileAttribute.cpSourceFile.sUTFStr).append(Utils.sNewLine);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitSourceFileAttribute(paramSourceFileAttribute);
        this.sPrepend = str;
    }

    public void visitSyntheticAttribute(SyntheticAttribute paramSyntheticAttribute) {
        this.summary.append(this.sPrepend).append("Attribute ").append("Synthetic");
        this.summary.append(Utils.sNewLine);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitSyntheticAttribute(paramSyntheticAttribute);
        this.sPrepend = str;
    }

    public void visitUnknownAttribute(UnknownAttribute paramUnknownAttribute) {
        this.summary.append(this.sPrepend).append("Attribute ").append(paramUnknownAttribute.sName);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitUnknownAttribute(paramUnknownAttribute);
        this.sPrepend = str;
    }

    public void visitVersion(Version paramVersion) {
        this.summary.append(this.sPrepend).append(Utils.sUnderLine).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("Java Version Information").append(Utils.sNewLine).append(this.sPrepend).append(Utils.sUnderLine).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("Magic number: ").append(paramVersion.getMagicNumberString()).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("Minor version: ").append(paramVersion.getMinorVersionString()).append(Utils.sNewLine);
        this.summary.append(this.sPrepend).append("Major version: ").append(paramVersion.getMajorVersionString()).append(Utils.sNewLine);
        String str = this.sPrepend;
        this.sPrepend += "\t";
        super.visitVersion(paramVersion);
        this.sPrepend = str;
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/visitors/TextSummaryVisitor.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */