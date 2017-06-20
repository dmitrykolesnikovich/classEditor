package featurea.classEditor.guihelper;

import featurea.classEditor.classfile.AccessFlags;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccessFlagEditor
        extends DefaultCellEditor {
    AccessFlags currFlags;
    Component editorComponent;

    public AccessFlagEditor(JButton paramJButton) {
        super(new JCheckBox());
        this.editorComponent = paramJButton;
        setClickCountToStart(1);
        paramJButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                AccessFlagEditor.this.fireEditingStopped();
            }
        });
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

    public Object getCellEditorValue() {
        return this.currFlags;
    }

    public Component getTableCellEditorComponent(JTable paramJTable, Object paramObject, boolean paramBoolean, int paramInt1, int paramInt2) {
        this.currFlags = ((AccessFlags) paramObject);
        ((JButton) this.editorComponent).setText(this.currFlags.toString());
        return this.editorComponent;
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/guihelper/AccessFlagEditor.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */