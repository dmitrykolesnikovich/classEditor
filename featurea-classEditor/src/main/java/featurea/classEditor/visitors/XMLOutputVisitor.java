/*
 * XMLOutputVisitor.java
 *
 * Created on November 22, 2003, 7:27 PM
 * 1.00   22nd Nov 2003   Tanmay   Original version.
 * 1.10   21st Mar 2004   Tanmay   After lots of changes, it has taken a reasonably good shape.
 * 1.11   21st Apr 2004   Tanmay   Setting schema to builderfactory in setLocalSchema method. Otherwise doc was not getting validated.
 * 1.12   22nd Apr 2004   Tanmay   Changes as per change in schema regarding constant pool references (not yet complete).
 * 1.13   26th Apr 2004   Tanmay   doTransform method changed to transform into HTML
 */

package featurea.classEditor.visitors;

import featurea.classEditor.classfile.*;
import featurea.classEditor.classfile.attributes.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

/**
 * A visitor class that generates a XML representation for a class file.
 *
 * @author Tanmay K. Mohapatra
 * @version 1.13, 26th Apr, 2004
 */
public class XMLOutputVisitor extends NavigatingClassVisitor {
    static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
    static final String schemaSource = "http://classeditor.sourceforge.net/CEJavaClass.xsd";
    static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
    static String localSchemaSource = "";
    static String localHTMLXSL = "";
    static String localTextXSL = "";
    DocumentBuilderFactory builderFactory;
    DocumentBuilder builder;
    Document doc;
    Element currElem;
    int iConstantPoolIndex;
    ConstantPool pool;

    /**
     * Creates a new instance of XMLOutputVisitor
     */
    public XMLOutputVisitor() throws javax.xml.parsers.ParserConfigurationException {
        builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setValidating(true);
        builderFactory.setNamespaceAware(true);

        try {
            builderFactory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
            builderFactory.setAttribute(JAXP_SCHEMA_SOURCE, localSchemaSource);
        } catch (IllegalArgumentException iae) {
            //iae.printStackTrace();
        }

        builder = builderFactory.newDocumentBuilder();
        doc = builder.newDocument();
    }

    public void setLocalSchema(String sSchemaLocation) {
        localSchemaSource = sSchemaLocation;
        try {
            builderFactory.setAttribute(JAXP_SCHEMA_SOURCE, new File(localSchemaSource));
        } catch (IllegalArgumentException iae) {
            //iae.printStackTrace();
        }
    }

    public void setLocalHTMLXSL(String sXSLLocation) {
        localHTMLXSL = sXSLLocation;
    }

    public void setLocalTextXSL(String sXSLLocation) {
        localTextXSL = sXSLLocation;
    }

    public void getAsText(OutputStream os) throws javax.xml.transform.TransformerConfigurationException, javax.xml.transform.TransformerException {
        doTransform(localTextXSL, os);
    }

    public void getAsHTML(OutputStream os) throws javax.xml.transform.TransformerConfigurationException, javax.xml.transform.TransformerException {
        doTransform(localHTMLXSL, os);
    }

    public void getAsString(OutputStream os) throws javax.xml.transform.TransformerConfigurationException, javax.xml.transform.TransformerException {
        doTransform(null, os);
    }

    private void doTransform(String sXSL, OutputStream os) throws javax.xml.transform.TransformerConfigurationException, javax.xml.transform.TransformerException {
        // create the transformerfactory & transformer instance
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();

        if (null == sXSL) {
            t.transform(new DOMSource(doc), new StreamResult(os));
            return;
        }

        try {
            Transformer finalTransformer = tf.newTransformer(new StreamSource(sXSL));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(baos);
            t.transform(new DOMSource(doc), new StreamResult(bos));
            bos.flush();
            StreamSource xmlSource = new StreamSource(new BufferedInputStream(new ByteArrayInputStream(baos.toByteArray())));
            finalTransformer.transform(xmlSource, new StreamResult(os));
        } catch (IOException ioe) {
            throw new javax.xml.transform.TransformerException(ioe);
        }
    }

    public void visitClass(ClassFile classFile) {
        Element prevElem = currElem;
        // add our root node
        currElem = (Element) doc.createElement("ce:JavaClass");
        doc.appendChild(currElem);

        currElem.setAttribute("xmlns:ce", schemaSource);
        currElem.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        currElem.setAttribute("xsi:schemaLocation", schemaSource + " " + localSchemaSource);
        super.visitClass(classFile);
        currElem = prevElem;
    }

    public void visitVersion(Version ver) {
        currElem.setAttribute("magic", Integer.toString(ver.getMagicNumberInteger()));
        currElem.setAttribute("major", ver.getMajorVersionString());
        currElem.setAttribute("minor", ver.getMinorVersionString());
        super.visitVersion(ver);
    }

    public void visitConstantPool(ConstantPool pool) {
        this.pool = pool;
        int iNumPoolInfos = pool.getPoolInfoCount();
        Element prevElem = currElem;
        currElem = doc.createElement("ConstantPool");
        prevElem.appendChild(currElem);
        iConstantPoolIndex = 0;
        super.visitConstantPool(pool);
        currElem = prevElem;
    }

    public void visitConstantPoolInfo(ConstantPoolInfo poolInfo) {
        Element prevElem = currElem;
        currElem = doc.createElement("ConstantPoolElement");
        currElem.setAttribute("cpindex", Integer.toString(iConstantPoolIndex + 1));
        prevElem.appendChild(currElem);
        Element subElem;
        // TODO: Add more details
        switch (poolInfo.iTag) {
            case ConstantPoolInfo.CONSTANT_Class: {
                currElem.setAttribute("type", "class");
                subElem = doc.createElement("cpClass");
                subElem.setAttribute("name_cpindex", createPoolIndexRef(poolInfo.refUTF8));
                currElem.appendChild(subElem);
            }
            break;
            case ConstantPoolInfo.CONSTANT_String: {
                currElem.setAttribute("type", "string");
                subElem = doc.createElement("cpString");
                subElem.setAttribute("utf_cpindex", createPoolIndexRef(poolInfo.refUTF8));
                currElem.appendChild(subElem);
            }
            break;
            case ConstantPoolInfo.CONSTANT_Fieldref:
            case ConstantPoolInfo.CONSTANT_Methodref:
            case ConstantPoolInfo.CONSTANT_InterfaceMethodref: {
                if (ConstantPoolInfo.CONSTANT_Fieldref == poolInfo.iTag) currElem.setAttribute("type", "fieldref");
                if (ConstantPoolInfo.CONSTANT_Methodref == poolInfo.iTag) currElem.setAttribute("type", "methodref");
                if (ConstantPoolInfo.CONSTANT_InterfaceMethodref == poolInfo.iTag)
                    currElem.setAttribute("type", "interfacemethodref");
                subElem = doc.createElement("cpRef");
                subElem.setAttribute("class_cpindex", createPoolIndexRef(poolInfo.refClass));
                subElem.setAttribute("nameAndType_cpindex", createPoolIndexRef(poolInfo.refNameAndType));
                currElem.appendChild(subElem);
            }
            break;
            case ConstantPoolInfo.CONSTANT_Integer:
                currElem.setAttribute("type", "integer");
                subElem = doc.createElement("cpInteger");
                subElem.appendChild(doc.createTextNode(Integer.toString(poolInfo.iIntValue)));
                currElem.appendChild(subElem);
                break;
            case ConstantPoolInfo.CONSTANT_Float:
                currElem.setAttribute("type", "float");
                subElem = doc.createElement("cpFloat");
                subElem.appendChild(doc.createTextNode(Float.toString(poolInfo.fFloatVal)));
                currElem.appendChild(subElem);
                break;
            case ConstantPoolInfo.CONSTANT_NameAndType:
                currElem.setAttribute("type", "nametype");
                subElem = doc.createElement("cpNameAndType");
                subElem.setAttribute("name_cpindex", createPoolIndexRef(poolInfo.refUTF8));
                subElem.setAttribute("descriptor_cpindex", createPoolIndexRef(poolInfo.refExtraUTF8));
                currElem.appendChild(subElem);
                break;
            case ConstantPoolInfo.CONSTANT_Long:
                currElem.setAttribute("type", "long");
                subElem = doc.createElement("cpLong");
                subElem.appendChild(doc.createTextNode(Long.toString(poolInfo.lLongVal)));
                currElem.appendChild(subElem);
                break;
            case ConstantPoolInfo.CONSTANT_Double:
                currElem.setAttribute("type", "double");
                subElem = doc.createElement("cpDouble");
                subElem.appendChild(doc.createTextNode(Double.toString(poolInfo.dDoubleVal)));
                currElem.appendChild(subElem);
                break;
            case ConstantPoolInfo.CONSTANT_Utf8:
                currElem.setAttribute("type", "utf8");
                subElem = doc.createElement("cpUtf8");
                subElem.appendChild(doc.createTextNode(poolInfo.sUTFStr));
                currElem.appendChild(subElem);
                break;
        }
        super.visitConstantPoolInfo(poolInfo);
        iConstantPoolIndex++;
        currElem = prevElem;
    }

    public void visitAccessFlags(AccessFlags flags) {
        String sNodeName = "";
        if (currElem.getNodeName().startsWith("ce:JavaClass")) {
            sNodeName = "ClassAccessFlags";
        } else if (currElem.getNodeName().startsWith("Field")) {
            sNodeName = "FieldAccessFlags";
        } else if (currElem.getNodeName().startsWith("Method")) {
            sNodeName = "MethodAccessFlags";
        } else if (currElem.getNodeName().startsWith("InnerClassInfo")) {
            sNodeName = "InnerClassAccessFlags";
        }

        Element prevElem = currElem;
        currElem = doc.createElement(sNodeName);
        prevElem.appendChild(currElem);

        if (!sNodeName.equals("ClassAccessFlags")) {
            if (flags.isSynchronized()) currElem.setAttribute("synchronized", Boolean.toString(flags.isSynchronized()));
        }
        if (flags.isNative()) currElem.setAttribute("native", Boolean.toString(flags.isNative()));
        if (flags.isAbstract()) currElem.setAttribute("abstract", Boolean.toString(flags.isAbstract()));
        if (flags.isInterface()) currElem.setAttribute("interface", Boolean.toString(flags.isInterface()));
        if (flags.isTransient()) currElem.setAttribute("transient", Boolean.toString(flags.isTransient()));
        if (flags.isVolatile()) currElem.setAttribute("volatile", Boolean.toString(flags.isVolatile()));
        if (flags.isSuper()) currElem.setAttribute("super", Boolean.toString(flags.isSuper()));
        if (flags.isFinal()) currElem.setAttribute("final", Boolean.toString(flags.isFinal()));
        if (flags.isStatic()) currElem.setAttribute("static", Boolean.toString(flags.isStatic()));
        if (flags.isProtected()) currElem.setAttribute("protected", Boolean.toString(flags.isProtected()));
        if (flags.isPrivate()) currElem.setAttribute("private", Boolean.toString(flags.isPrivate()));
        if (flags.isPublic()) currElem.setAttribute("public", Boolean.toString(flags.isPublic()));

        super.visitAccessFlags(flags);

        // if no attributes were set, remove the node
        if ((currElem.getAttributes().getLength() == 0) && (currElem.getChildNodes().getLength() == 0)) {
            prevElem.removeChild(currElem);
        }
        currElem = prevElem;
    }

    public void visitClassNames(ClassNames names) {
        Element prevElem = currElem;

        currElem = doc.createElement("ThisClass");
        currElem.setAttribute("cpindex", createPoolIndexRef(names.cpThisClass));
        prevElem.appendChild(currElem);

        currElem = doc.createElement("SuperClass");
        currElem.setAttribute("cpindex", createPoolIndexRef(names.cpSuperClass));
        prevElem.appendChild(currElem);
        super.visitClassNames(names);
        currElem = prevElem;
    }


    private String createPoolIndexRef(ConstantPoolInfo cpInfo) {
        return Integer.toString(pool.getIndexOf(cpInfo));
    }

    private String createPoolRef(ConstantPoolInfo cpInfo) {
        return ("/JavaClass/ConstantPool/ConstantPoolElement[@cpindex=" + pool.getIndexOf(cpInfo) + "]");
    }

    public void visitFieldInfo(FieldInfo fldInfo) {
        Element prevElem = currElem;

        currElem = doc.createElement("Field");
        prevElem.appendChild(currElem);
        visitAccessFlags(fldInfo.accessFlags);
        currElem.setAttribute("name_cpindex", createPoolIndexRef(fldInfo.cpName));
        currElem.setAttribute("descriptor_cpindex", createPoolIndexRef(fldInfo.cpDescriptor));

        visitAttributes(fldInfo.attributes);
        super.visitFieldInfo(fldInfo);
        currElem = prevElem;
    }

    public void visitFields(Fields flds) {
        Element prevElem = currElem;

        currElem = doc.createElement("Fields");
        if (flds.getFieldsCount() > 0) prevElem.appendChild(currElem);
        currElem.setAttribute("count", Integer.toString(flds.getFieldsCount()));
        super.visitFields(flds);
        currElem = prevElem;
    }

    public void visitMethodInfo(MethodInfo methodInfo) {
        Element prevElem = currElem;

        currElem = doc.createElement("Method");
        prevElem.appendChild(currElem);
        visitAccessFlags(methodInfo.accessFlags);
        currElem.setAttribute("name_cpindex", createPoolIndexRef(methodInfo.cpName));
        currElem.setAttribute("descriptor_cpindex", createPoolIndexRef(methodInfo.cpDescriptor));

        super.visitMethodInfo(methodInfo);
        currElem = prevElem;
    }

    public void visitMethods(Methods methods) {
        Element prevElem = currElem;

        currElem = doc.createElement("Methods");
        prevElem.appendChild(currElem);
        currElem.setAttribute("count", Integer.toString(methods.getMethodsCount()));
        super.visitMethods(methods);
        currElem = prevElem;
    }

    public void visitInterface(Interfaces interfaces, int iIndex) {
        Element prevElem = currElem;

        currElem = doc.createElement("Interface");
        prevElem.appendChild(currElem);
        currElem.setAttribute("cpindex", createPoolIndexRef(interfaces.getInterface(iIndex)));
        super.visitInterface(interfaces, iIndex);
        currElem = prevElem;
    }

    public void visitInterfaces(Interfaces interfaces) {
        Element prevElem = currElem;

        currElem = doc.createElement("Interfaces");
        prevElem.appendChild(currElem);
        currElem.setAttribute("count", Integer.toString(interfaces.getInterfacesCount()));
        super.visitInterfaces(interfaces);
        currElem = prevElem;
    }

    public void visitAttribute(Attribute attr) {
        Element prevElem = currElem;
        currElem = doc.createElement("Attribute");
        currElem.setAttribute("name_cpindex", createPoolIndexRef(attr.cpAttribName));
        prevElem.appendChild(currElem);
        super.visitAttribute(attr);
        currElem = prevElem;
    }

    public void visitAttributes(Attributes attrs) {
        if (attrs.iAttributesCount <= 0) {
            super.visitAttributes(attrs);
            return;
        }
        String sNodeName = "";
        String sParentNodeName = currElem.getNodeName();
        if (sParentNodeName.startsWith("Field")) {
            sNodeName = "FieldAttributes";
        } else if (sParentNodeName.startsWith("Method")) {
            sNodeName = "MethodAttributes";
        } else if (sParentNodeName.startsWith("AttributeCode")) {
            sNodeName = "CodeAttributes";
        } else if (sParentNodeName.startsWith("ce:JavaClass")) {
            sNodeName = "ClassAttributes";
        }

        Element prevElem = currElem;
        currElem = doc.createElement(sNodeName);
        prevElem.appendChild(currElem);
        currElem.setAttribute("count", Integer.toString(attrs.iAttributesCount));
        super.visitAttributes(attrs);
        currElem = prevElem;
    }

    public void visitSourceFileAttribute(SourceFileAttribute src) {
        Element prevElem = currElem;
        currElem = doc.createElement("AttributeSourceFile");
        currElem.setAttribute("filename_cpindex", createPoolIndexRef(src.cpSourceFile));
        prevElem.appendChild(currElem);
        super.visitSourceFileAttribute(src);
        currElem = prevElem;
    }

    public void visitSyntheticAttribute(SyntheticAttribute synth) {
        Element prevElem = currElem;
        currElem = doc.createElement("AttributeSynthetic");
        prevElem.appendChild(currElem);
        super.visitSyntheticAttribute(synth);
        currElem = prevElem;
    }

    public void visitDeprecatedAttribute(DeprecatedAttribute depr) {
        Element prevElem = currElem;
        currElem = doc.createElement("Deprecated");
        prevElem.appendChild(currElem);
        super.visitDeprecatedAttribute(depr);
        currElem = prevElem;
    }

    public void visitLineNumberTableAttribute(LineNumberTableAttribute linenumtab) {
        Element prevElem = currElem;
        currElem = doc.createElement("AttributeLineNumberTable");
        currElem.setAttribute("count", Integer.toString(linenumtab.vectEntries.size()));
        prevElem.appendChild(currElem);
        super.visitLineNumberTableAttribute(linenumtab);
        currElem = prevElem;
    }

    public void visitLineNumberTableEntry(LineNumberTableEntry linenumtabentry) {
        Element prevElem = currElem;
        currElem = doc.createElement("LineNumber");
        currElem.setAttribute("pc", Integer.toString(linenumtabentry.iStartPC));
        currElem.setAttribute("line", Integer.toString(linenumtabentry.iLineNum));
        prevElem.appendChild(currElem);
        super.visitLineNumberTableEntry(linenumtabentry);
        currElem = prevElem;
    }

    public void visitLocalVariableTableAttribute(LocalVariableTableAttribute lvtab) {
        Element prevElem = currElem;
        currElem = doc.createElement("AttributeLocalVariableTable");
        currElem.setAttribute("count", Integer.toString(lvtab.vectLocalVariableTable.size()));
        prevElem.appendChild(currElem);
        super.visitLocalVariableTableAttribute(lvtab);
        currElem = prevElem;
    }

    public void visitLocalVariableTableEntry(LocalVariableTableEntry lvtabentry) {
        Element prevElem = currElem;
        currElem = doc.createElement("LocalVariable");
        currElem.setAttribute("pc", Integer.toString(lvtabentry.iStartPC));
        currElem.setAttribute("length", Integer.toString(lvtabentry.iLength));
        // null check added as a guard against obfuscated classes
        if (null != lvtabentry.cpName) currElem.setAttribute("name_cpindex", createPoolIndexRef(lvtabentry.cpName));
        if (null != lvtabentry.cpDescriptor)
            currElem.setAttribute("descriptor_cpindex", createPoolIndexRef(lvtabentry.cpDescriptor));
        currElem.setAttribute("index", Integer.toString(lvtabentry.iIndex));
        prevElem.appendChild(currElem);
        super.visitLocalVariableTableEntry(lvtabentry);
        currElem = prevElem;
    }

    public void visitInnerClassesAttribute(InnerClassesAttribute classattr) {
        Element prevElem = currElem;
        currElem = doc.createElement("AttributeInnerClass");
        currElem.setAttribute("count", Integer.toString(classattr.getNumClasses()));
        prevElem.appendChild(currElem);
        super.visitInnerClassesAttribute(classattr);
        currElem = prevElem;
    }

    public void visitInnerClassInfo(InnerClassInfo innerclass) {
        Element prevElem = currElem;
        currElem = doc.createElement("InnerClassInfo");
        if (null != innerclass.cpInnerClass)
            currElem.setAttribute("innerclass_cpindex", createPoolIndexRef(innerclass.cpInnerClass));
        if (null != innerclass.cpOuterClass)
            currElem.setAttribute("outerclass_cpindex", createPoolIndexRef(innerclass.cpOuterClass));
        if (null != innerclass.cpInnerName)
            currElem.setAttribute("innername_cpindex", createPoolIndexRef(innerclass.cpInnerName));
        prevElem.appendChild(currElem);
        super.visitInnerClassInfo(innerclass);
        currElem = prevElem;
    }

    public void visitConstantValueAttribute(ConstantValueAttribute constval) {
        Element prevElem = currElem;
        currElem = doc.createElement("AttributeConstantValue");
        currElem.setAttribute("value_cpindex", createPoolIndexRef(constval.cpConstant));
        prevElem.appendChild(currElem);
        super.visitConstantValueAttribute(constval);
        currElem = prevElem;
    }

    public void visitExceptionsAttribute(ExceptionsAttribute ex) {
        Element prevElem = currElem;
        currElem = doc.createElement("AttributeExceptions");
        currElem.setAttribute("count", Integer.toString(ex.iNumExceptions));
        prevElem.appendChild(currElem);
        for (int iIndex = 0; iIndex < ex.iNumExceptions; iIndex++) {
            Element except = doc.createElement("Exception");
            except.setAttribute("class_cpindex", createPoolIndexRef((ConstantPoolInfo) ex.vectExceptionTypes.elementAt(iIndex)));
            currElem.appendChild(except);
        }

        super.visitExceptionsAttribute(ex);
        currElem = prevElem;
    }

    public void visitCodeAttribute(CodeAttribute codeattr) {
        Element prevElem = currElem;
        currElem = doc.createElement("AttributeCode");
        prevElem.appendChild(currElem);
        currElem.setAttribute("maxstack", Integer.toString(codeattr.iMaxStack));
        currElem.setAttribute("maxlocals", Integer.toString(codeattr.iMaxLocals));
        int iExceptTableLength = codeattr.vectExceptionTableEntries.size();
        if (0 < iExceptTableLength) {
            Element prevElem1 = currElem;
            currElem = doc.createElement("ExceptionTable");
            prevElem1.appendChild(currElem);
            for (int iIndex = 0; iIndex < iExceptTableLength; iIndex++) {
                Element prevElem2 = currElem;
                currElem = doc.createElement("ExceptionTableEntry");
                prevElem2.appendChild(currElem);
                ExceptionTableEntry entry = (ExceptionTableEntry) codeattr.vectExceptionTableEntries.elementAt(iIndex);
                currElem.setAttribute("startpc", Integer.toString(entry.iStartPC));
                currElem.setAttribute("endpc", Integer.toString(entry.iEndPC));
                currElem.setAttribute("handlerpc", Integer.toString(entry.iHandlerPC));
                if ((0 != entry.iCatchType) && (null != entry.cpCatchType))
                    currElem.setAttribute("catchtype", createPoolRef((ConstantPoolInfo) entry.cpCatchType));
                currElem = prevElem2;
            }
            currElem = prevElem1;
        }
        super.visitCodeAttribute(codeattr);
        currElem = prevElem;
    }

    public void visitCode(Code code) {
        Element prevElem = currElem;
        currElem = doc.createElement("Instructions");
        prevElem.appendChild(currElem);
        currElem.setAttribute("length", Integer.toString(code.iCodeLength));

        for (int iIndex = 0; iIndex < code.vectCode.size(); iIndex++) {
            Instruction thisInstr = (Instruction) code.vectCode.elementAt(iIndex);
            Element instrElem = doc.createElement("Instruction");
            currElem.appendChild(instrElem);
            Element instr = doc.createElement(Instructions.sInstrCodes[thisInstr.iInstruction]);
            instrElem.appendChild(instr);
            if (0xab == thisInstr.iInstruction) { // matchoffsetpair
                instr.setAttribute("defaultbyte", Integer.toString(thisInstr.iDefaultByte));
                instr.setAttribute("numpairs", Integer.toString(thisInstr.iNPairs));
                for (int jIndex = 0; jIndex < thisInstr.iNPairs; jIndex++) {
                    Element pair = doc.createElement("matchoffsetpair");
                    instr.appendChild(pair);
                    instr.setAttribute("match", Integer.toString(thisInstr.aiMatchPairs[jIndex]));
                    instr.setAttribute("offset", Integer.toString(thisInstr.aiOffsetPairs[jIndex]));
                }
            } else if (0xaa == thisInstr.iInstruction) { // tableswitch
                instr.setAttribute("defaultbyte", Integer.toString(thisInstr.iDefaultByte));
                instr.setAttribute("lowint", Integer.toString(thisInstr.iLowInt));
                instr.setAttribute("highint", Integer.toString(thisInstr.iHighInt));
                for (int jIndex = 0; jIndex < thisInstr.iNPairs; jIndex++) {
                    Element pair = doc.createElement("offsetpair");
                    instr.appendChild(pair);
                    instr.setAttribute("value", Integer.toString(thisInstr.aiOffsetPairs[jIndex]));
                }
            } else {
                for (int jIndex = 0; jIndex < thisInstr.iNumData; jIndex++) {
                    Element instrData = doc.createElement("InstructionData");
                    instr.appendChild(instrData);
                    instrData.setAttribute("length", Integer.toString(thisInstr.aiLen[jIndex]));
                    instrData.setAttribute("data", Integer.toString(thisInstr.aiData[jIndex]));
                }
            }

        }
        super.visitCode(code);
        currElem = prevElem;
    }

    public void visitUnknownAttribute(UnknownAttribute unknown) {
        Element prevElem = currElem;
        currElem = doc.createElement("AttributeUnknown");
        currElem.appendChild(doc.createTextNode(byteToHex(unknown.abUnknAttr)));
        prevElem.appendChild(currElem);
        super.visitUnknownAttribute(unknown);
        currElem = prevElem;
    }

    private String byteToHex(byte[] barr) {
        StringBuffer sbuff = new StringBuffer();
        for (int iIndex = 0; iIndex < barr.length; iIndex++) {
            sbuff.append(Integer.toHexString(0x000F & barr[iIndex]));
        }
        return sbuff.toString();
    }
}