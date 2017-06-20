package featurea.classEditor.gui.attributes;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.attributes.Attribute;
import featurea.classEditor.classfile.attributes.ConstantValueAttribute;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConstantValueAttribPane
        extends JPanel
        implements AttribDisplay {
    private ConstantValueAttribute attribute;
    private ConstantPool constPool;
    private boolean bModifyMode;
    private JButton btnModConstValue;
    private JComboBox cmbAttribConstType;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JTextField txtAttribConstVal;

    public ConstantValueAttribPane(boolean paramBoolean) {
        this.bModifyMode = paramBoolean;
        initComponents();
        this.txtAttribConstVal.setEnabled(paramBoolean);
        this.btnModConstValue.setEnabled(paramBoolean);
    }

    private void setAttribConstType(String paramString) {
        if (paramString.equals("long")) {
            this.cmbAttribConstType.setSelectedIndex(1);
        } else if (paramString.equals("float")) {
            this.cmbAttribConstType.setSelectedIndex(2);
        } else if (paramString.equals("double")) {
            this.cmbAttribConstType.setSelectedIndex(3);
        } else if (paramString.equals("int, short, char, byte, boolean")) {
            this.cmbAttribConstType.setSelectedIndex(4);
        } else if (paramString.equals("java.lang.String")) {
            this.cmbAttribConstType.setSelectedIndex(5);
        }
    }

    public void setInput(Attribute paramAttribute, ConstantPool paramConstantPool) {
        this.attribute = ((ConstantValueAttribute) paramAttribute);
        this.constPool = paramConstantPool;
        setAttribConstType(((ConstantValueAttribute) paramAttribute).sConstType);
        this.txtAttribConstVal.setText(((ConstantValueAttribute) paramAttribute).sConstValue);
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.cmbAttribConstType = new JComboBox(new String[]{"", "long", "float", "double", "int, short, char, byte, boolean", "java.lang.String"});
        this.jLabel2 = new JLabel();
        this.txtAttribConstVal = new JTextField();
        this.btnModConstValue = new JButton();
        setLayout(new GridBagLayout());
        this.jLabel1.setText("Constant Type");
        GridBagConstraints localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.anchor = 18;
        localGridBagConstraints.insets = new Insets(10, 10, 0, 0);
        add(this.jLabel1, localGridBagConstraints);
        this.cmbAttribConstType.setEnabled(false);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.anchor = 18;
        localGridBagConstraints.insets = new Insets(10, 10, 0, 10);
        add(this.cmbAttribConstType, localGridBagConstraints);
        this.jLabel2.setText("Constant Value");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.anchor = 18;
        localGridBagConstraints.weighty = 1.0D;
        localGridBagConstraints.insets = new Insets(10, 10, 0, 0);
        add(this.jLabel2, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.anchor = 18;
        localGridBagConstraints.weightx = 1.0D;
        localGridBagConstraints.weighty = 1.0D;
        localGridBagConstraints.insets = new Insets(10, 10, 0, 0);
        add(this.txtAttribConstVal, localGridBagConstraints);
        this.btnModConstValue.setText("Modify");
        this.btnModConstValue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ConstantValueAttribPane.this.btnModConstValueActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.gridheight = 0;
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.anchor = 18;
        localGridBagConstraints.weighty = 1.0D;
        localGridBagConstraints.insets = new Insets(10, 10, 0, 10);
        add(this.btnModConstValue, localGridBagConstraints);
    }

    private void btnModConstValueActionPerformed(ActionEvent paramActionEvent) {
        this.attribute.setConstantValue(this.txtAttribConstVal.getText());
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/gui/attributes/ConstantValueAttribPane.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */