package featurea.classEditor.guihelper;

import featurea.classEditor.classfile.attributes.Attribute;
import featurea.classEditor.classfile.attributes.Attributes;
import featurea.classEditor.classfile.attributes.CodeAttribute;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class AttributeTreeNode
        extends DefaultMutableTreeNode {
    public Attribute attr;

    public AttributeTreeNode(Attribute paramAttribute) {
        this.attr = paramAttribute;
        setUserObject(this.attr.sName);
    }

    public static JFrame getFrameFrom(Component paramComponent) {
        int i = 0;
        Object localObject = paramComponent;
        for (i = 0; (i < 1000) && (!(localObject instanceof JFrame)); i++) {
            localObject = ((Component) localObject).getParent();
        }
        if ((localObject instanceof JFrame)) {
            return (JFrame) localObject;
        }
        return null;
    }

    private static void addTreeNode(Attribute paramAttribute, DefaultMutableTreeNode paramDefaultMutableTreeNode) {
        AttributeTreeNode localAttributeTreeNode = new AttributeTreeNode(paramAttribute);
        if ((paramAttribute instanceof CodeAttribute)) {
            Attributes localAttributes = ((CodeAttribute) paramAttribute).codeAttributes;
            makeTree(localAttributes, localAttributeTreeNode);
        }
        paramDefaultMutableTreeNode.add(localAttributeTreeNode);
    }

    public static void makeTree(Attributes paramAttributes, DefaultMutableTreeNode paramDefaultMutableTreeNode) {
        for (int i = paramAttributes.getAttribCount(); i > 0; i--) {
            Attribute localAttribute = paramAttributes.getAttribute(i - 1);
            addTreeNode(localAttribute, paramDefaultMutableTreeNode);
        }
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/guihelper/AttributeTreeNode.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */