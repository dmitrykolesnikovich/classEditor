package featurea.classEditor.gui.attributes;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.attributes.Attribute;
import featurea.classEditor.classfile.attributes.LocalVariableTableAttribute;
import featurea.classEditor.classfile.attributes.LocalVariableTableEntry;
import featurea.classEditor.guihelper.attributes.LocalVariableTableAttribTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LocalVariableAttribPane
        extends JPanel
        implements AttribDisplay {
    private LocalVariableTableAttribute attribute;
    private ConstantPool constPool;
    private boolean bModifyMode;
    private JScrollPane jScrollPane2;
    private JTable tblLocalVariables;
    private JPanel jPanel1;
    private JButton btnAdd;
    private JButton btnDelete;

    public LocalVariableAttribPane(boolean paramBoolean) {
        this.bModifyMode = paramBoolean;
        initComponents();
        this.btnAdd.setEnabled(paramBoolean);
        this.btnDelete.setEnabled(paramBoolean);
    }

    public void setInput(Attribute paramAttribute, ConstantPool paramConstantPool) {
        this.attribute = ((LocalVariableTableAttribute) paramAttribute);
        this.constPool = paramConstantPool;
        setTableModel();
    }

    private void checkForObfuscation() {
        if (this.bModifyMode) {
            int i = this.attribute.vectLocalVariableTable.size();
            for (int j = 0; j < i; j++) {
                LocalVariableTableEntry localLocalVariableTableEntry = (LocalVariableTableEntry) this.attribute.vectLocalVariableTable.elementAt(j);
                if ((null == localLocalVariableTableEntry.cpDescriptor) || (null == localLocalVariableTableEntry.cpName)) {
                    this.bModifyMode = false;
                    JOptionPane.showMessageDialog(this, "Local variable table is invalid (probably obfuscated).\nHence it can not be edited.", "Class format invalid", 1);
                    break;
                }
            }
        }
    }

    private void setTableModel() {
        LocalVariableTableAttribTableModel localLocalVariableTableAttribTableModel = new LocalVariableTableAttribTableModel(this.attribute, this.constPool);
        checkForObfuscation();
        localLocalVariableTableAttribTableModel.setEditable(this.bModifyMode);
        this.tblLocalVariables.setModel(localLocalVariableTableAttribTableModel);
        localLocalVariableTableAttribTableModel.setCellEditors(this.tblLocalVariables);
        TableColumn localTableColumn = this.tblLocalVariables.getColumnModel().getColumn(0);
        localTableColumn.setPreferredWidth(30);
        localTableColumn.setMaxWidth(80);
        localTableColumn = this.tblLocalVariables.getColumnModel().getColumn(1);
        localTableColumn.setPreferredWidth(200);
        localTableColumn.setMaxWidth(400);
        localTableColumn = this.tblLocalVariables.getColumnModel().getColumn(2);
        localTableColumn.setPreferredWidth(200);
        localTableColumn.setMaxWidth(400);
        localTableColumn = this.tblLocalVariables.getColumnModel().getColumn(3);
        localTableColumn.setPreferredWidth(50);
        localTableColumn.setMaxWidth(200);
        localTableColumn = this.tblLocalVariables.getColumnModel().getColumn(4);
        localTableColumn.setPreferredWidth(50);
        localTableColumn.setMaxWidth(200);
        this.tblLocalVariables.changeSelection(0, 1, false, false);
    }

    private void initComponents() {
        this.jScrollPane2 = new JScrollPane();
        this.tblLocalVariables = new JTable();
        this.tblLocalVariables.setSelectionMode(0);
        this.jPanel1 = new JPanel();
        this.btnAdd = new JButton();
        this.btnDelete = new JButton();
        setLayout(new GridBagLayout());
        this.tblLocalVariables.setModel(new LocalVariableTableAttribTableModel(null, null));
        this.jScrollPane2.setViewportView(this.tblLocalVariables);
        GridBagConstraints localGridBagConstraints1 = new GridBagConstraints();
        localGridBagConstraints1.gridwidth = 0;
        localGridBagConstraints1.fill = 1;
        localGridBagConstraints1.insets = new Insets(10, 10, 10, 10);
        localGridBagConstraints1.weightx = 1.0D;
        localGridBagConstraints1.weighty = 1.0D;
        add(this.jScrollPane2, localGridBagConstraints1);
        this.jPanel1.setLayout(new GridBagLayout());
        this.btnAdd.setText("Add");
        this.btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                LocalVariableAttribPane.this.btnAddActionPerformed(paramAnonymousActionEvent);
            }
        });
        GridBagConstraints localGridBagConstraints2 = new GridBagConstraints();
        localGridBagConstraints2.anchor = 13;
        this.jPanel1.add(this.btnAdd, localGridBagConstraints2);
        this.btnDelete.setText("Delete");
        this.btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                LocalVariableAttribPane.this.btnDeleteActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints2 = new GridBagConstraints();
        localGridBagConstraints2.anchor = 17;
        this.jPanel1.add(this.btnDelete, localGridBagConstraints2);
        localGridBagConstraints1 = new GridBagConstraints();
        localGridBagConstraints1.gridwidth = 0;
        localGridBagConstraints1.fill = 1;
        add(this.jPanel1, localGridBagConstraints1);
    }

    private void btnDeleteActionPerformed(ActionEvent paramActionEvent) {
        int i = this.tblLocalVariables.getSelectedRow();
        if (0 > i) {
            return;
        }
        this.attribute.deleteEntryAt(i);
        setTableModel();
    }

    private void btnAddActionPerformed(ActionEvent paramActionEvent) {
        ((LocalVariableTableAttribTableModel) this.tblLocalVariables.getModel()).addNewEntry();
        setTableModel();
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/gui/attributes/LocalVariableAttribPane.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */