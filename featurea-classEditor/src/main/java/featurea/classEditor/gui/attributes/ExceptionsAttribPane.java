package featurea.classEditor.gui.attributes;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.attributes.Attribute;
import featurea.classEditor.classfile.attributes.ExceptionsAttribute;
import featurea.classEditor.guihelper.attributes.ExceptionsListTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExceptionsAttribPane
        extends JPanel
        implements AttribDisplay {
    private ExceptionsAttribute attribute;
    private ConstantPool constPool;
    private boolean bModifyMode;
    private JScrollPane jScrollPane2;
    private JTable tblExceptions;
    private JPanel jPanel1;
    private JButton btnAdd;
    private JButton btnRemove;

    public ExceptionsAttribPane(boolean paramBoolean) {
        this.bModifyMode = paramBoolean;
        initComponents();
        this.btnAdd.setEnabled(paramBoolean);
        this.btnRemove.setEnabled(paramBoolean);
    }

    private void populateExceptions() {
        ExceptionsListTableModel localExceptionsListTableModel = new ExceptionsListTableModel(this.constPool, this.attribute);
        this.tblExceptions.setModel(localExceptionsListTableModel);
        localExceptionsListTableModel.setEditable(this.bModifyMode);
        localExceptionsListTableModel.setCellEditors(this.tblExceptions);
        TableColumn localTableColumn = this.tblExceptions.getColumnModel().getColumn(0);
        localTableColumn.setPreferredWidth(30);
        localTableColumn.setMaxWidth(80);
        localTableColumn = this.tblExceptions.getColumnModel().getColumn(1);
        localTableColumn.setPreferredWidth(500);
        localTableColumn.setMaxWidth(800);
    }

    public void setInput(Attribute paramAttribute, ConstantPool paramConstantPool) {
        this.attribute = ((ExceptionsAttribute) paramAttribute);
        this.constPool = paramConstantPool;
        populateExceptions();
    }

    private void initComponents() {
        this.jScrollPane2 = new JScrollPane();
        this.tblExceptions = new JTable();
        this.tblExceptions.setSelectionMode(0);
        this.jPanel1 = new JPanel();
        this.btnAdd = new JButton();
        this.btnRemove = new JButton();
        setLayout(new GridBagLayout());
        this.tblExceptions.setModel(new ExceptionsListTableModel(null, null));
        this.jScrollPane2.setViewportView(this.tblExceptions);
        GridBagConstraints localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weightx = 1.0D;
        localGridBagConstraints.weighty = 1.0D;
        add(this.jScrollPane2, localGridBagConstraints);
        this.btnAdd.setText("Add");
        this.btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ExceptionsAttribPane.this.btnAddActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.jPanel1.add(this.btnAdd);
        this.btnRemove.setText("Delete");
        this.btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ExceptionsAttribPane.this.btnRemoveActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.jPanel1.add(this.btnRemove);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weightx = 1.0D;
        add(this.jPanel1, localGridBagConstraints);
    }

    private void btnRemoveActionPerformed(ActionEvent paramActionEvent) {
        int i = this.tblExceptions.getSelectedRow();
        if (i < 0) {
            return;
        }
        this.attribute.deleteExceptionAt(i);
        populateExceptions();
    }

    private void btnAddActionPerformed(ActionEvent paramActionEvent) {
        ExceptionsListTableModel localExceptionsListTableModel = (ExceptionsListTableModel) this.tblExceptions.getModel();
        localExceptionsListTableModel.addNewExceptionEntry();
        populateExceptions();
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/gui/attributes/ExceptionsAttribPane.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */