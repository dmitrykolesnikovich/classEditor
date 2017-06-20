package featurea.classEditor.gui.attributes;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.attributes.Attribute;
import featurea.classEditor.classfile.attributes.UnknownAttribute;

import javax.swing.*;
import java.awt.*;

public class UnknownAttribPane
        extends JPanel
        implements AttribDisplay {
    private UnknownAttribute attribute;
    private ConstantPool constPool;
    private boolean bModifyMode;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JList lstUnknownAttrib;

    public UnknownAttribPane(boolean paramBoolean) {
        this.bModifyMode = paramBoolean;
        initComponents();
    }

    public void setInput(Attribute paramAttribute, ConstantPool paramConstantPool) {
        this.attribute = ((UnknownAttribute) paramAttribute);
        this.constPool = paramConstantPool;
        DefaultListModel localDefaultListModel = new DefaultListModel();
        localDefaultListModel.addElement(paramAttribute.cpAttribName.sUTFStr);
        this.lstUnknownAttrib.setModel(localDefaultListModel);
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.lstUnknownAttrib = new JList();
        setLayout(new GridBagLayout());
        this.jLabel1.setText("Names");
        GridBagConstraints localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.insets = new Insets(10, 10, 10, 10);
        localGridBagConstraints.anchor = 18;
        add(this.jLabel1, localGridBagConstraints);
        this.jScrollPane1.setViewportView(this.lstUnknownAttrib);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.gridheight = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.insets = new Insets(10, 0, 10, 10);
        localGridBagConstraints.weightx = 1.0D;
        localGridBagConstraints.weighty = 1.0D;
        add(this.jScrollPane1, localGridBagConstraints);
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/gui/attributes/UnknownAttribPane.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */