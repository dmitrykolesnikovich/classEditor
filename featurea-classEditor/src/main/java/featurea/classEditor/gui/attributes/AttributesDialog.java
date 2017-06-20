package featurea.classEditor.gui.attributes;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.attributes.*;
import featurea.classEditor.guihelper.AttributeTreeNode;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AttributesDialog
        extends JDialog {
    private Attributes attributeList;
    private ConstantPool constPool;
    private boolean bModifyMode;
    private DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode("Attributes");
    private SourceFileAttribPane srcFileAttribPane;
    private ConstantValueAttribPane constValAttribPane;
    private CodeAttribPane codeAttribPane;
    private ExceptionsAttribPane exceptionsAttribPane;
    private InnerClassesAttribPane innerClassesAttribPane;
    private LineNumberAttribPane lineNumberAttribPane;
    private LocalVariableAttribPane localVariableTableAttribPane;
    private UnknownAttribPane unknownAttribPane;
    private JSplitPane jSplitPane1;
    private JPanel attribDisplayPane;
    private JTree treeAttributes;

    public AttributesDialog(Frame paramFrame, boolean paramBoolean) {
        super(paramFrame, paramBoolean);
        initComponents();
    }

    public void setInput(Attributes paramAttributes, ConstantPool paramConstantPool, boolean paramBoolean) {
        this.attributeList = paramAttributes;
        this.constPool = paramConstantPool;
        this.bModifyMode = paramBoolean;
        populateTree();
        this.treeAttributes.setSelectionRow(0);
    }

    private void populateTree() {
        if ((null == this.attributeList) || (null == this.constPool)) {
            return;
        }
        clearTree();
        AttributeTreeNode.makeTree(this.attributeList, this.rootTreeNode);
        this.treeAttributes.setModel(new DefaultTreeModel(this.rootTreeNode));
    }

    private void clearTree() {
        this.treeAttributes.setModel(new DefaultTreeModel(this.rootTreeNode));
    }

    private JPanel createAndGetPanelToAdd(Attribute paramAttribute) {
        Object localObject = null;
        if ((paramAttribute instanceof SourceFileAttribute)) {
            if (null == this.srcFileAttribPane) {
                this.srcFileAttribPane = new SourceFileAttribPane(this.bModifyMode);
            }
            localObject = this.srcFileAttribPane;
        } else if ((paramAttribute instanceof ConstantValueAttribute)) {
            if (null == this.constValAttribPane) {
                this.constValAttribPane = new ConstantValueAttribPane(this.bModifyMode);
            }
            localObject = this.constValAttribPane;
        } else if ((paramAttribute instanceof ExceptionsAttribute)) {
            if (null == this.exceptionsAttribPane) {
                this.exceptionsAttribPane = new ExceptionsAttribPane(this.bModifyMode);
            }
            localObject = this.exceptionsAttribPane;
        } else if ((paramAttribute instanceof CodeAttribute)) {
            if (null == this.codeAttribPane) {
                this.codeAttribPane = new CodeAttribPane(this.bModifyMode);
            }
            localObject = this.codeAttribPane;
        } else if ((paramAttribute instanceof LineNumberTableAttribute)) {
            if (null == this.lineNumberAttribPane) {
                this.lineNumberAttribPane = new LineNumberAttribPane(this.bModifyMode);
            }
            localObject = this.lineNumberAttribPane;
        } else if ((paramAttribute instanceof LocalVariableTableAttribute)) {
            if (null == this.localVariableTableAttribPane) {
                this.localVariableTableAttribPane = new LocalVariableAttribPane(this.bModifyMode);
            }
            localObject = this.localVariableTableAttribPane;
        } else if ((paramAttribute instanceof InnerClassesAttribute)) {
            if (null == this.innerClassesAttribPane) {
                this.innerClassesAttribPane = new InnerClassesAttribPane(this.bModifyMode);
            }
            localObject = this.innerClassesAttribPane;
        } else if ((paramAttribute instanceof UnknownAttribute)) {
            if (null == this.unknownAttribPane) {
                this.unknownAttribPane = new UnknownAttribPane(this.bModifyMode);
            }
            localObject = this.unknownAttribPane;
        }
        return (JPanel) localObject;
    }

    private void initComponents() {
        this.jSplitPane1 = new JSplitPane();
        this.attribDisplayPane = new JPanel();
        this.treeAttributes = new JTree();
        getContentPane().setLayout(new GridLayout(1, 0));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent paramAnonymousWindowEvent) {
                AttributesDialog.this.closeDialog(paramAnonymousWindowEvent);
            }
        });
        this.jSplitPane1.setDividerLocation(150);
        this.jSplitPane1.setMinimumSize(new Dimension(462, 402));
        this.jSplitPane1.setOneTouchExpandable(true);
        this.jSplitPane1.setAutoscrolls(true);
        this.attribDisplayPane.setLayout(new GridLayout(1, 0));
        this.attribDisplayPane.setPreferredSize(new Dimension(600, 400));
        this.attribDisplayPane.setMinimumSize(new Dimension(300, 400));
        this.jSplitPane1.setRightComponent(this.attribDisplayPane);
        this.treeAttributes.setShowsRootHandles(true);
        this.treeAttributes.setPreferredSize(new Dimension(100, 400));
        this.treeAttributes.setRootVisible(false);
        this.treeAttributes.setMinimumSize(new Dimension(100, 400));
        this.treeAttributes.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent paramAnonymousTreeSelectionEvent) {
                AttributesDialog.this.treeAttributesValueChanged(paramAnonymousTreeSelectionEvent);
            }
        });
        this.jSplitPane1.setLeftComponent(this.treeAttributes);
        getContentPane().add(this.jSplitPane1);
        pack();
    }

    private void treeAttributesValueChanged(TreeSelectionEvent paramTreeSelectionEvent) {
        AttributeTreeNode localAttributeTreeNode = (AttributeTreeNode) this.treeAttributes.getLastSelectedPathComponent();
        if (localAttributeTreeNode == null) {
            return;
        }
        Attribute localAttribute = localAttributeTreeNode.attr;
        this.attribDisplayPane.removeAll();
        JPanel localJPanel = createAndGetPanelToAdd(localAttribute);
        if (null != localJPanel) {
            this.jSplitPane1.setRightComponent(localJPanel);
            ((AttribDisplay) localJPanel).setInput(localAttribute, this.constPool);
        } else {
            this.jSplitPane1.setRightComponent(this.attribDisplayPane);
        }
    }

    private void closeDialog(WindowEvent paramWindowEvent) {
        setVisible(false);
        dispose();
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/gui/attributes/AttributesDialog.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */