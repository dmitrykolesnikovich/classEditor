package featurea.classEditor.gui;

import featurea.classEditor.classfile.ClassFile;
import featurea.classEditor.classfile.ConstantPoolInfo;
import featurea.classEditor.gui.attributes.AttributesDialog;
import featurea.classEditor.guihelper.AttributeTreeNode;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeneralPropPane
        extends JPanel {
    private ClassFile currClassFile;
    private DefaultMutableTreeNode interfacesRootNode;
    private boolean bEditable;
    private JButton btnAcceptChanges;
    private JButton btnAddNewInterface;
    private JButton btnDeleteInterface;
    private JButton btnDiscardChanges;
    private JButton btnModifyInterface;
    private JButton btnShowEditAttribs;
    private JCheckBox chkAbstract;
    private JCheckBox chkFinal;
    private JCheckBox chkInterface;
    private JCheckBox chkPublic;
    private JCheckBox chkSuper;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JScrollPane jScrollPane1;
    private JLabel lblClassName;
    private JLabel lblConstPoolIndex;
    private JLabel lblMagicNumber;
    private JLabel lblMajorVersion;
    private JLabel lblMinorVersion;
    private JLabel lblModifiedName;
    private JLabel lblSuperClassName;
    private JTree treeInterfaces;
    private JTextField txtClassName;
    private JTextField txtConstPoolIndex;
    private JTextArea txtHelpText;
    private JTextField txtMagicNumber;
    private JTextField txtMajorVersion;
    private JTextField txtMinorVersion;
    private JTextField txtNewInterfaceName;
    private JTextField txtSuperClassName;

    public GeneralPropPane() {
        initComponents();
        this.treeInterfaces.getSelectionModel().setSelectionMode(1);
        ((DefaultTreeCellRenderer) this.treeInterfaces.getCellRenderer()).setLeafIcon(new ImageIcon(getClass().getResource("/res/interface.gif")));
    }

    public void setModifyMode(boolean paramBoolean) {
        this.bEditable = paramBoolean;
        this.txtClassName.setEnabled(this.bEditable);
        this.txtSuperClassName.setEnabled(this.bEditable);
        this.txtMagicNumber.setEnabled(this.bEditable);
        this.txtMajorVersion.setEnabled(this.bEditable);
        this.txtMinorVersion.setEnabled(this.bEditable);
        this.chkFinal.setEnabled(this.bEditable);
        this.chkPublic.setEnabled(this.bEditable);
        this.chkSuper.setEnabled(this.bEditable);
        this.chkInterface.setEnabled(this.bEditable);
        this.chkAbstract.setEnabled(this.bEditable);
        this.txtNewInterfaceName.setEnabled(this.bEditable);
        this.txtConstPoolIndex.setEnabled(this.bEditable);
        this.btnDiscardChanges.setEnabled(this.bEditable);
        this.btnAcceptChanges.setEnabled(this.bEditable);
        this.btnDeleteInterface.setEnabled(this.bEditable);
        this.btnModifyInterface.setEnabled(this.bEditable);
        this.btnAddNewInterface.setEnabled(this.bEditable);
    }

    private void modifyInterfaceName(int paramInt) {
        this.currClassFile.interfaces.setInterfaceName(paramInt, this.txtNewInterfaceName.getText());
        refreshInterfaceList();
    }

    private void deleteInterface(int paramInt) {
        this.currClassFile.interfaces.removeInterface(paramInt);
        refreshInterfaceList();
    }

    void clear() {
        this.txtClassName.setText("");
        this.txtSuperClassName.setText("");
        this.txtMagicNumber.setText("");
        this.txtMajorVersion.setText("");
        this.txtMinorVersion.setText("");
        this.chkFinal.setSelected(false);
        this.chkPublic.setSelected(false);
        this.chkSuper.setSelected(false);
        this.chkInterface.setSelected(false);
        this.chkAbstract.setSelected(false);
        this.interfacesRootNode.removeAllChildren();
        this.treeInterfaces.setModel(new DefaultTreeModel(this.interfacesRootNode));
        this.txtNewInterfaceName.setText("");
        this.txtConstPoolIndex.setText("");
    }

    void setClassFile(ClassFile paramClassFile) {
        this.currClassFile = paramClassFile;
    }

    void refresh() {
        if (null == this.currClassFile) {
            return;
        }
        this.txtClassName.setText(this.currClassFile.classNames.getThisClassName());
        this.txtSuperClassName.setText(this.currClassFile.classNames.getSuperClassName());
        this.txtMagicNumber.setText(this.currClassFile.version.getMagicNumberString());
        this.txtMajorVersion.setText(this.currClassFile.version.getMajorVersionString());
        this.txtMinorVersion.setText(this.currClassFile.version.getMinorVersionString());
        this.chkFinal.setSelected(this.currClassFile.accessFlags.isFinal());
        this.chkPublic.setSelected(this.currClassFile.accessFlags.isPublic());
        this.chkSuper.setSelected(this.currClassFile.accessFlags.isSuper());
        this.chkInterface.setSelected(this.currClassFile.accessFlags.isInterface());
        this.chkAbstract.setSelected(this.currClassFile.accessFlags.isAbstract());
        refreshInterfaceList();
    }

    private void refreshInterfaceList() {
        this.interfacesRootNode.removeAllChildren();
        int i = this.currClassFile.interfaces.getInterfacesCount();
        for (int j = 0; j < i; j++) {
            String str = this.currClassFile.interfaces.getInterfaceName(j);
            DefaultMutableTreeNode localDefaultMutableTreeNode = new DefaultMutableTreeNode(str);
            localDefaultMutableTreeNode.setUserObject(str);
            this.interfacesRootNode.add(localDefaultMutableTreeNode);
        }
        this.treeInterfaces.setModel(new DefaultTreeModel(this.interfacesRootNode));
    }

    private void saveChanges() {
        if (null == this.currClassFile) {
            return;
        }
        this.currClassFile.version.setMagicNumberString(this.txtMagicNumber.getText());
        this.currClassFile.version.setMajorVersionString(this.txtMajorVersion.getText());
        this.currClassFile.version.setMinorVersionString(this.txtMinorVersion.getText());
        this.currClassFile.classNames.setThisClassName(this.txtClassName.getText());
        this.currClassFile.classNames.setSuperClassName(this.txtSuperClassName.getText());
        this.currClassFile.accessFlags.setFinal(this.chkFinal.isSelected());
        this.currClassFile.accessFlags.setPublic(this.chkPublic.isSelected());
        this.currClassFile.accessFlags.setSuper(this.chkSuper.isSelected());
        this.currClassFile.accessFlags.setInterface(this.chkInterface.isSelected());
        this.currClassFile.accessFlags.setAbstract(this.chkAbstract.isSelected());
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jPanel2 = new JPanel();
        this.lblClassName = new JLabel();
        this.txtClassName = new JTextField();
        this.lblSuperClassName = new JLabel();
        this.txtSuperClassName = new JTextField();
        this.jPanel3 = new JPanel();
        this.lblMagicNumber = new JLabel();
        this.txtMagicNumber = new JTextField();
        this.lblMajorVersion = new JLabel();
        this.txtMajorVersion = new JTextField();
        this.lblMinorVersion = new JLabel();
        this.txtMinorVersion = new JTextField();
        this.jPanel4 = new JPanel();
        this.chkFinal = new JCheckBox();
        this.chkPublic = new JCheckBox();
        this.chkInterface = new JCheckBox();
        this.chkSuper = new JCheckBox();
        this.chkAbstract = new JCheckBox();
        this.jPanel5 = new JPanel();
        this.btnShowEditAttribs = new JButton();
        this.jPanel6 = new JPanel();
        this.btnDiscardChanges = new JButton();
        this.btnAcceptChanges = new JButton();
        this.jPanel7 = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        this.treeInterfaces = new JTree(this.interfacesRootNode = new DefaultMutableTreeNode(""));
        this.btnDeleteInterface = new JButton();
        this.lblModifiedName = new JLabel();
        this.txtNewInterfaceName = new JTextField();
        this.btnModifyInterface = new JButton();
        this.lblConstPoolIndex = new JLabel();
        this.txtConstPoolIndex = new JTextField();
        this.btnAddNewInterface = new JButton();
        this.txtHelpText = new JTextArea();
        setLayout(new GridBagLayout());
        this.jPanel1.setLayout(new GridBagLayout());
        this.jPanel1.setBorder(new TitledBorder(null, "Class Properties", 1, 0));
        this.jPanel2.setLayout(new GridBagLayout());
        this.jPanel2.setBorder(new TitledBorder("Names"));
        this.lblClassName.setText("Class Name");
        GridBagConstraints localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.anchor = 17;
        this.jPanel2.add(this.lblClassName, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.weightx = 1.0D;
        localGridBagConstraints.insets = new Insets(0, 4, 0, 0);
        this.jPanel2.add(this.txtClassName, localGridBagConstraints);
        this.lblSuperClassName.setText("Super Class");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.anchor = 17;
        this.jPanel2.add(this.lblSuperClassName, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.weightx = 1.0D;
        localGridBagConstraints.insets = new Insets(0, 4, 0, 0);
        this.jPanel2.add(this.txtSuperClassName, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weighty = 1.0D;
        this.jPanel1.add(this.jPanel2, localGridBagConstraints);
        this.jPanel3.setLayout(new GridBagLayout());
        this.jPanel3.setBorder(new TitledBorder("Version"));
        this.lblMagicNumber.setText("Magic Number");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.anchor = 17;
        this.jPanel3.add(this.lblMagicNumber, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.weightx = 1.0D;
        localGridBagConstraints.insets = new Insets(0, 4, 0, 0);
        this.jPanel3.add(this.txtMagicNumber, localGridBagConstraints);
        this.lblMajorVersion.setText("Major Version");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.anchor = 17;
        this.jPanel3.add(this.lblMajorVersion, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.weightx = 1.0D;
        localGridBagConstraints.insets = new Insets(0, 4, 0, 0);
        this.jPanel3.add(this.txtMajorVersion, localGridBagConstraints);
        this.lblMinorVersion.setText("Minor Version");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.anchor = 17;
        this.jPanel3.add(this.lblMinorVersion, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.weightx = 1.0D;
        localGridBagConstraints.insets = new Insets(0, 4, 0, 0);
        this.jPanel3.add(this.txtMinorVersion, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weighty = 1.0D;
        this.jPanel1.add(this.jPanel3, localGridBagConstraints);
        this.jPanel4.setLayout(new GridLayout(2, 3));
        this.jPanel4.setBorder(new TitledBorder("Access"));
        this.chkFinal.setText("final");
        this.jPanel4.add(this.chkFinal);
        this.chkPublic.setText("public");
        this.jPanel4.add(this.chkPublic);
        this.chkInterface.setText("interface");
        this.jPanel4.add(this.chkInterface);
        this.chkSuper.setText("super");
        this.jPanel4.add(this.chkSuper);
        this.chkAbstract.setText("abstract");
        this.jPanel4.add(this.chkAbstract);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weighty = 1.0D;
        this.jPanel1.add(this.jPanel4, localGridBagConstraints);
        this.jPanel5.setBorder(new TitledBorder("Attributes"));
        this.btnShowEditAttribs.setText("Show/Edit Attributes");
        this.btnShowEditAttribs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                GeneralPropPane.this.btnShowEditAttribsActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.jPanel5.add(this.btnShowEditAttribs);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weighty = 1.0D;
        this.jPanel1.add(this.jPanel5, localGridBagConstraints);
        this.jPanel6.setBorder(new TitledBorder("Property Changes"));
        this.btnDiscardChanges.setText("Discard");
        this.btnDiscardChanges.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                GeneralPropPane.this.btnDiscardChangesActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.jPanel6.add(this.btnDiscardChanges);
        this.btnAcceptChanges.setText("Accept");
        this.btnAcceptChanges.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                GeneralPropPane.this.btnAcceptChangesActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.jPanel6.add(this.btnAcceptChanges);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weighty = 1.0D;
        this.jPanel1.add(this.jPanel6, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weighty = 1.0D;
        add(this.jPanel1, localGridBagConstraints);
        this.jPanel7.setLayout(new GridBagLayout());
        this.jPanel7.setBorder(new TitledBorder("Interfaces"));
        this.treeInterfaces.setToolTipText("");
        this.treeInterfaces.setEditable(true);
        this.treeInterfaces.setRootVisible(false);
        this.treeInterfaces.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent paramAnonymousTreeSelectionEvent) {
                GeneralPropPane.this.treeInterfacesValueChanged(paramAnonymousTreeSelectionEvent);
            }
        });
        this.jScrollPane1.setViewportView(this.treeInterfaces);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weightx = 1.0D;
        localGridBagConstraints.weighty = 1.0D;
        this.jPanel7.add(this.jScrollPane1, localGridBagConstraints);
        this.btnDeleteInterface.setIcon(new ImageIcon(getClass().getResource("/res/close1.gif")));
        this.btnDeleteInterface.setText("Delete");
        this.btnDeleteInterface.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                GeneralPropPane.this.btnDeleteInterfaceActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.anchor = 13;
        this.jPanel7.add(this.btnDeleteInterface, localGridBagConstraints);
        this.lblModifiedName.setText("Modified Name");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.anchor = 17;
        this.jPanel7.add(this.lblModifiedName, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.weightx = 1.0D;
        this.jPanel7.add(this.txtNewInterfaceName, localGridBagConstraints);
        this.btnModifyInterface.setText("Modify");
        this.btnModifyInterface.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                GeneralPropPane.this.btnModifyInterfaceActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.anchor = 13;
        this.jPanel7.add(this.btnModifyInterface, localGridBagConstraints);
        this.lblConstPoolIndex.setText("Constant Pool Index");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.anchor = 17;
        localGridBagConstraints.insets = new Insets(0, 0, 0, 2);
        this.jPanel7.add(this.lblConstPoolIndex, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.weightx = 1.0D;
        this.jPanel7.add(this.txtConstPoolIndex, localGridBagConstraints);
        this.btnAddNewInterface.setText("Add New");
        this.btnAddNewInterface.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                GeneralPropPane.this.btnAddNewInterfaceActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.anchor = 13;
        this.jPanel7.add(this.btnAddNewInterface, localGridBagConstraints);
        this.txtHelpText.setWrapStyleWord(true);
        this.txtHelpText.setLineWrap(true);
        this.txtHelpText.setEditable(false);
        this.txtHelpText.setText("To remove an interface, select interface name from list and push the \"Delete\" button.\nTo modify an interface name, select interface name from list, modify it in the text field below and push the \"Modify\" button.\nTo add a new interface, enter a constant pool index of type ClassInfo in the text field and push the \"Add New\" botton.");
        this.txtHelpText.setBackground(new Color(204, 204, 255));
        this.txtHelpText.setMargin(new Insets(4, 4, 0, 0));
        this.txtHelpText.setRequestFocusEnabled(false);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weighty = 0.25D;
        this.jPanel7.add(this.txtHelpText, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.gridheight = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weightx = 1.0D;
        localGridBagConstraints.weighty = 1.0D;
        add(this.jPanel7, localGridBagConstraints);
    }

    private void btnShowEditAttribsActionPerformed(ActionEvent paramActionEvent) {
        if ((null == this.currClassFile) || (null == this.currClassFile.attributes)) {
            return;
        }
        AttributesDialog localAttributesDialog = new AttributesDialog(AttributeTreeNode.getFrameFrom(this), true);
        localAttributesDialog.setTitle("Class Attributes");
        localAttributesDialog.setInput(this.currClassFile.attributes, this.currClassFile.constantPool, this.bEditable);
        localAttributesDialog.show();
    }

    private void btnDeleteInterfaceActionPerformed(ActionEvent paramActionEvent) {
        DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode) this.treeInterfaces.getLastSelectedPathComponent();
        if (localDefaultMutableTreeNode == null) {
            return;
        }
        DefaultTreeModel localDefaultTreeModel = (DefaultTreeModel) this.treeInterfaces.getModel();
        int i = localDefaultTreeModel.getIndexOfChild(localDefaultTreeModel.getRoot(), localDefaultMutableTreeNode);
        if ((null != this.currClassFile) && (i >= 0)) {
            deleteInterface(i);
        }
    }

    private void btnAddNewInterfaceActionPerformed(ActionEvent paramActionEvent) {
        if (null == this.currClassFile) {
            return;
        }
        String str = this.txtConstPoolIndex.getText();
        str = str.trim();
        if (str.length() <= 0) {
            return;
        }
        int i;
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException localNumberFormatException) {
            JOptionPane.showMessageDialog(null, "Constant pool index must be an integer greater than 0.", "Error", 0);
            return;
        }
        if (i <= 0) {
            return;
        }
        ConstantPoolInfo localConstantPoolInfo = this.currClassFile.constantPool.getPoolInfo(i);
        if (7 != localConstantPoolInfo.iTag) {
            JOptionPane.showMessageDialog(null, "Constant pool entry " + i + " is not of type Class.", "Error", 0);
            return;
        }
        this.currClassFile.interfaces.addInterface(localConstantPoolInfo);
        refreshInterfaceList();
    }

    private void btnModifyInterfaceActionPerformed(ActionEvent paramActionEvent) {
        DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode) this.treeInterfaces.getLastSelectedPathComponent();
        if (localDefaultMutableTreeNode == null) {
            return;
        }
        DefaultTreeModel localDefaultTreeModel = (DefaultTreeModel) this.treeInterfaces.getModel();
        int i = localDefaultTreeModel.getIndexOfChild(localDefaultTreeModel.getRoot(), localDefaultMutableTreeNode);
        if ((null != this.currClassFile) && (i >= 0)) {
            modifyInterfaceName(i);
        }
    }

    private void treeInterfacesValueChanged(TreeSelectionEvent paramTreeSelectionEvent) {
        DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode) this.treeInterfaces.getLastSelectedPathComponent();
        if (localDefaultMutableTreeNode == null) {
            return;
        }
        this.txtNewInterfaceName.setText((String) localDefaultMutableTreeNode.getUserObject());
    }

    private void btnAcceptChangesActionPerformed(ActionEvent paramActionEvent) {
        saveChanges();
    }

    private void btnDiscardChangesActionPerformed(ActionEvent paramActionEvent) {
        refresh();
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/gui/GeneralPropPane.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */