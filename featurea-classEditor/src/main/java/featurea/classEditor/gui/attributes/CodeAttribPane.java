package featurea.classEditor.gui.attributes;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.attributes.Attribute;
import featurea.classEditor.classfile.attributes.CodeAttribute;
import featurea.classEditor.guihelper.attributes.InstructionsTableModel;
import featurea.classEditor.guihelper.attributes.CodeExceptionsListTableModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CodeAttribPane
        extends JPanel
        implements AttribDisplay {
    private CodeAttribute attribute;
    private ConstantPool constPool;
    private boolean bModifyMode;
    private JPanel jPanel1;
    private JLabel jLabel2;
    private JTextField txtMaxStack;
    private JLabel jLabel4;
    private JTextField txtMaxLocals;
    private JLabel jLabel6;
    private JTextField txtNumAttribs;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private JTable tblInstr;
    private JPanel jPanel5;
    private JButton btnAddInstr;
    private JButton btnRemoveInstr;
    private JPanel jPanel3;
    private JScrollPane jScrollPane3;
    private JTable tblExceptions;
    private JPanel jPanel4;
    private JButton btnAdd;
    private JButton btnRemove;

    public CodeAttribPane(boolean paramBoolean) {
        this.bModifyMode = paramBoolean;
        initComponents();
        this.txtMaxStack.setEnabled(paramBoolean);
        this.txtMaxLocals.setEnabled(paramBoolean);
        this.txtNumAttribs.setEnabled(false);
        this.btnAdd.setEnabled(paramBoolean);
        this.btnRemove.setEnabled(paramBoolean);
        this.btnAddInstr.setEnabled(paramBoolean);
        this.btnRemoveInstr.setEnabled(paramBoolean);
    }

    private void populateCode() {
        this.txtMaxStack.setText(Integer.toString(this.attribute.iMaxStack));
        this.txtMaxLocals.setText(Integer.toString(this.attribute.iMaxLocals));
        this.txtNumAttribs.setText(Integer.toString(this.attribute.codeAttributes.getAttribCount()));
        setInstructionsTableModel();
        setExceptionTableModel();
    }

    private void setInstructionsTableModel() {
        InstructionsTableModel localInstructionsTableModel = new InstructionsTableModel(this.constPool, this.attribute.code);
        this.tblInstr.setModel(localInstructionsTableModel);
        localInstructionsTableModel.setEditable(this.bModifyMode);
        localInstructionsTableModel.setCellEditors(this.tblExceptions);
        TableColumn localTableColumn = this.tblInstr.getColumnModel().getColumn(0);
        localTableColumn.setPreferredWidth(60);
        localTableColumn.setMaxWidth(150);
        localTableColumn = this.tblInstr.getColumnModel().getColumn(1);
        localTableColumn.setPreferredWidth(60);
        localTableColumn.setMaxWidth(150);
        localTableColumn = this.tblInstr.getColumnModel().getColumn(2);
        localTableColumn.setPreferredWidth(500);
        localTableColumn.setMaxWidth(800);
    }

    private void setExceptionTableModel() {
        CodeExceptionsListTableModel localCodeExceptionsListTableModel = new CodeExceptionsListTableModel(this.constPool, this.attribute);
        this.tblExceptions.setModel(localCodeExceptionsListTableModel);
        localCodeExceptionsListTableModel.setEditable(this.bModifyMode);
        localCodeExceptionsListTableModel.setCellEditors(this.tblExceptions);
        TableColumn localTableColumn = this.tblExceptions.getColumnModel().getColumn(0);
        localTableColumn.setPreferredWidth(30);
        localTableColumn.setMaxWidth(80);
        localTableColumn = this.tblExceptions.getColumnModel().getColumn(1);
        localTableColumn.setPreferredWidth(300);
        localTableColumn.setMaxWidth(500);
        localTableColumn = this.tblExceptions.getColumnModel().getColumn(2);
        localTableColumn.setPreferredWidth(100);
        localTableColumn.setMaxWidth(150);
        localTableColumn = this.tblExceptions.getColumnModel().getColumn(3);
        localTableColumn.setPreferredWidth(100);
        localTableColumn.setMaxWidth(150);
        localTableColumn = this.tblExceptions.getColumnModel().getColumn(4);
        localTableColumn.setPreferredWidth(100);
        localTableColumn.setMaxWidth(150);
    }

    public void setInput(Attribute paramAttribute, ConstantPool paramConstantPool) {
        this.attribute = ((CodeAttribute) paramAttribute);
        this.constPool = paramConstantPool;
        populateCode();
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jLabel2 = new JLabel();
        this.txtMaxStack = new JTextField();
        this.jLabel4 = new JLabel();
        this.txtMaxLocals = new JTextField();
        this.jLabel6 = new JLabel();
        this.txtNumAttribs = new JTextField();
        this.jPanel2 = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        this.tblInstr = new JTable();
        this.tblInstr.setSelectionMode(0);
        this.jPanel5 = new JPanel();
        this.btnAddInstr = new JButton();
        this.btnRemoveInstr = new JButton();
        this.jPanel3 = new JPanel();
        this.jScrollPane3 = new JScrollPane();
        this.tblExceptions = new JTable();
        this.tblExceptions.setSelectionMode(0);
        this.jPanel4 = new JPanel();
        this.btnAdd = new JButton();
        this.btnRemove = new JButton();
        setLayout(new GridBagLayout());
        this.jPanel1.setLayout(new GridBagLayout());
        this.jLabel2.setText("Max Stack");
        GridBagConstraints localGridBagConstraints2 = new GridBagConstraints();
        localGridBagConstraints2.anchor = 17;
        this.jPanel1.add(this.jLabel2, localGridBagConstraints2);
        this.txtMaxStack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                CodeAttribPane.this.txtMaxStackActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints2 = new GridBagConstraints();
        localGridBagConstraints2.fill = 1;
        localGridBagConstraints2.weightx = 0.1D;
        this.jPanel1.add(this.txtMaxStack, localGridBagConstraints2);
        this.jLabel4.setText("Max Locals");
        localGridBagConstraints2 = new GridBagConstraints();
        localGridBagConstraints2.insets = new Insets(0, 50, 0, 0);
        this.jPanel1.add(this.jLabel4, localGridBagConstraints2);
        this.txtMaxLocals.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                CodeAttribPane.this.txtMaxLocalsActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints2 = new GridBagConstraints();
        localGridBagConstraints2.fill = 1;
        localGridBagConstraints2.weightx = 0.1D;
        this.jPanel1.add(this.txtMaxLocals, localGridBagConstraints2);
        this.jLabel6.setText("Attribute Count");
        localGridBagConstraints2 = new GridBagConstraints();
        localGridBagConstraints2.insets = new Insets(0, 50, 0, 0);
        this.jPanel1.add(this.jLabel6, localGridBagConstraints2);
        localGridBagConstraints2 = new GridBagConstraints();
        localGridBagConstraints2.fill = 1;
        localGridBagConstraints2.weightx = 0.1D;
        this.jPanel1.add(this.txtNumAttribs, localGridBagConstraints2);
        GridBagConstraints localGridBagConstraints1 = new GridBagConstraints();
        localGridBagConstraints1.gridwidth = 0;
        localGridBagConstraints1.fill = 1;
        localGridBagConstraints1.insets = new Insets(10, 10, 10, 10);
        localGridBagConstraints1.weightx = 1.0D;
        add(this.jPanel1, localGridBagConstraints1);
        this.jPanel2.setLayout(new GridBagLayout());
        this.jPanel2.setBorder(new TitledBorder("Code"));
        this.tblInstr.setModel(new InstructionsTableModel(null, null));
        this.jScrollPane1.setViewportView(this.tblInstr);
        GridBagConstraints localGridBagConstraints3 = new GridBagConstraints();
        localGridBagConstraints3.gridwidth = 0;
        localGridBagConstraints3.fill = 1;
        localGridBagConstraints3.weightx = 1.0D;
        localGridBagConstraints3.weighty = 1.0D;
        this.jPanel2.add(this.jScrollPane1, localGridBagConstraints3);
        this.btnAddInstr.setText("Add");
        this.jPanel5.add(this.btnAddInstr);
        this.btnRemoveInstr.setText("Delete");
        this.jPanel5.add(this.btnRemoveInstr);
        localGridBagConstraints3 = new GridBagConstraints();
        this.jPanel2.add(this.jPanel5, localGridBagConstraints3);
        localGridBagConstraints1 = new GridBagConstraints();
        localGridBagConstraints1.fill = 1;
        localGridBagConstraints1.insets = new Insets(0, 10, 10, 0);
        localGridBagConstraints1.weightx = 1.0D;
        localGridBagConstraints1.weighty = 1.0D;
        add(this.jPanel2, localGridBagConstraints1);
        this.jPanel3.setLayout(new GridBagLayout());
        this.jPanel3.setBorder(new TitledBorder("Exceptions"));
        this.tblExceptions.setModel(new CodeExceptionsListTableModel(null, null));
        this.jScrollPane3.setViewportView(this.tblExceptions);
        GridBagConstraints localGridBagConstraints4 = new GridBagConstraints();
        localGridBagConstraints4.gridwidth = 0;
        localGridBagConstraints4.gridheight = -1;
        localGridBagConstraints4.fill = 1;
        localGridBagConstraints4.weightx = 1.0D;
        localGridBagConstraints4.weighty = 1.0D;
        this.jPanel3.add(this.jScrollPane3, localGridBagConstraints4);
        this.btnAdd.setText("Add");
        this.btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                CodeAttribPane.this.btnAddActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.jPanel4.add(this.btnAdd);
        this.btnRemove.setText("Delete");
        this.btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                CodeAttribPane.this.btnRemoveActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.jPanel4.add(this.btnRemove);
        localGridBagConstraints4 = new GridBagConstraints();
        this.jPanel3.add(this.jPanel4, localGridBagConstraints4);
        localGridBagConstraints1 = new GridBagConstraints();
        localGridBagConstraints1.fill = 1;
        localGridBagConstraints1.insets = new Insets(0, 0, 10, 10);
        localGridBagConstraints1.weightx = 1.0D;
        localGridBagConstraints1.weighty = 1.0D;
        add(this.jPanel3, localGridBagConstraints1);
    }

    private void btnRemoveActionPerformed(ActionEvent paramActionEvent) {
        int i = this.tblExceptions.getSelectedRow();
        if (i < 0) {
            return;
        }
        this.attribute.deleteExceptionTableEntryAt(i);
        setExceptionTableModel();
    }

    private void btnAddActionPerformed(ActionEvent paramActionEvent) {
        this.attribute.addNewExceptionTableEntry();
        setExceptionTableModel();
    }

    private void txtMaxLocalsActionPerformed(ActionEvent paramActionEvent) {
        String str = this.txtMaxLocals.getText().trim();
        try {
            this.attribute.iMaxLocals = Integer.parseInt(str);
        } catch (NumberFormatException localNumberFormatException) {
            str = Integer.toString(this.attribute.iMaxLocals);
        }
        this.txtMaxLocals.setText(str);
    }

    private void txtMaxStackActionPerformed(ActionEvent paramActionEvent) {
        String str = this.txtMaxStack.getText().trim();
        try {
            this.attribute.iMaxStack = Integer.parseInt(str);
        } catch (NumberFormatException localNumberFormatException) {
            str = Integer.toString(this.attribute.iMaxStack);
        }
        this.txtMaxStack.setText(str);
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/gui/attributes/CodeAttribPane.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */