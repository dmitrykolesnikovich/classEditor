package featurea.classEditor.gui.attributes;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.attributes.Attribute;
import featurea.classEditor.classfile.attributes.InnerClassInfo;
import featurea.classEditor.classfile.attributes.InnerClassesAttribute;

import javax.swing.*;
import java.awt.*;

public class InnerClassesAttribPane
        extends JPanel
        implements AttribDisplay {
    private InnerClassesAttribute attribute;
    private ConstantPool constPool;
    private boolean bModifyMode;
    private JLabel jLabel1;
    private JLabel lblNumInnerClasses;
    private JScrollPane jScrollPane1;
    private JList lstInnerClasses;

    public InnerClassesAttribPane(boolean paramBoolean) {
        this.bModifyMode = paramBoolean;
        initComponents();
    }

    public void setInput(Attribute paramAttribute, ConstantPool paramConstantPool) {
        this.attribute = ((InnerClassesAttribute) paramAttribute);
        this.constPool = paramConstantPool;
        int i = this.attribute.getNumClasses();
        this.lblNumInnerClasses.setText(Integer.toString(i));
        DefaultListModel localDefaultListModel = new DefaultListModel();
        for (int j = 0; j < i; j++) {
            InnerClassInfo localInnerClassInfo = this.attribute.getInnerClassInfo(j);
            localDefaultListModel.addElement(localInnerClassInfo);
        }
        this.lstInnerClasses.setModel(localDefaultListModel);
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.lblNumInnerClasses = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.lstInnerClasses = new JList();
        setLayout(new GridBagLayout());
        this.jLabel1.setText("Number of Inner Classes");
        GridBagConstraints localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.insets = new Insets(10, 10, 0, 0);
        localGridBagConstraints.anchor = 18;
        add(this.jLabel1, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.insets = new Insets(10, 10, 0, 10);
        localGridBagConstraints.anchor = 17;
        localGridBagConstraints.weightx = 1.0D;
        add(this.lblNumInnerClasses, localGridBagConstraints);
        this.jScrollPane1.setViewportView(this.lstInnerClasses);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.insets = new Insets(10, 10, 10, 10);
        localGridBagConstraints.weightx = 1.0D;
        localGridBagConstraints.weighty = 1.0D;
        add(this.jScrollPane1, localGridBagConstraints);
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/gui/attributes/InnerClassesAttribPane.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */