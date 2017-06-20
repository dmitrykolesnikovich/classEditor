package featurea.classEditor.guihelper;

import featurea.classEditor.classfile.AccessFlags;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AccessFlagEditorDialog
        extends JDialog {
    AccessFlags flags;
    private JButton btnClose;
    private JButton btnRefresh;
    private JButton btnSave;
    private JCheckBox chkAbstract;
    private JCheckBox chkFinal;
    private JCheckBox chkInterface;
    private JCheckBox chkNative;
    private JCheckBox chkPrivate;
    private JCheckBox chkProtected;
    private JCheckBox chkPublic;
    private JCheckBox chkStatic;
    private JCheckBox chkStrict;
    private JCheckBox chkSuper;
    private JCheckBox chkSynchronized;
    private JCheckBox chkTransient;
    private JCheckBox chkVolatile;

    public AccessFlagEditorDialog(Frame paramFrame, boolean paramBoolean) {
        super(paramFrame, paramBoolean);
        initComponents();
    }

    public void setAccessFlags(AccessFlags paramAccessFlags) {
        this.flags = paramAccessFlags;
        this.chkPublic.setSelected(this.flags.isPublic());
        this.chkPrivate.setSelected(this.flags.isPrivate());
        this.chkProtected.setSelected(this.flags.isProtected());
        this.chkStatic.setSelected(this.flags.isStatic());
        this.chkFinal.setSelected(this.flags.isFinal());
        this.chkSuper.setSelected(this.flags.isSuper());
        this.chkVolatile.setSelected(this.flags.isVolatile());
        this.chkTransient.setSelected(this.flags.isTransient());
        this.chkInterface.setSelected(this.flags.isInterface());
        this.chkAbstract.setSelected(this.flags.isAbstract());
        this.chkNative.setSelected(this.flags.isNative());
        this.chkSynchronized.setSelected(this.flags.isSynchronized());
        this.chkStrict.setSelected(this.flags.isStrict());
    }

    private void saveAccessFlags() {
        this.flags.setPublic(this.chkPublic.isSelected());
        this.flags.setPrivate(this.chkPrivate.isSelected());
        this.flags.setProtected(this.chkProtected.isSelected());
        this.flags.setStatic(this.chkStatic.isSelected());
        this.flags.setFinal(this.chkFinal.isSelected());
        this.flags.setSuper(this.chkSuper.isSelected());
        this.flags.setVolatile(this.chkVolatile.isSelected());
        this.flags.setTransient(this.chkTransient.isSelected());
        this.flags.setInterface(this.chkInterface.isSelected());
        this.flags.setAbstract(this.chkAbstract.isSelected());
        this.flags.setNative(this.chkNative.isSelected());
        this.flags.setSynchronized(this.chkSynchronized.isSelected());
        this.flags.setStrict(this.chkStrict.isSelected());
    }

    private void setAsInvalid(JCheckBox paramJCheckBox) {
        paramJCheckBox.setEnabled(false);
    }

    public void setValidAccessFlags(AccessFlags paramAccessFlags) {
        if (!paramAccessFlags.isPublic()) {
            setAsInvalid(this.chkPublic);
        }
        if (!paramAccessFlags.isPrivate()) {
            setAsInvalid(this.chkPrivate);
        }
        if (!paramAccessFlags.isProtected()) {
            setAsInvalid(this.chkProtected);
        }
        if (!paramAccessFlags.isStatic()) {
            setAsInvalid(this.chkStatic);
        }
        if (!paramAccessFlags.isFinal()) {
            setAsInvalid(this.chkFinal);
        }
        if (!paramAccessFlags.isSuper()) {
            setAsInvalid(this.chkSuper);
        }
        if (!paramAccessFlags.isVolatile()) {
            setAsInvalid(this.chkVolatile);
        }
        if (!paramAccessFlags.isTransient()) {
            setAsInvalid(this.chkTransient);
        }
        if (!paramAccessFlags.isInterface()) {
            setAsInvalid(this.chkInterface);
        }
        if (!paramAccessFlags.isAbstract()) {
            setAsInvalid(this.chkAbstract);
        }
        if (!paramAccessFlags.isNative()) {
            setAsInvalid(this.chkNative);
        }
        if (!paramAccessFlags.isSynchronized()) {
            setAsInvalid(this.chkSynchronized);
        }
        if (!paramAccessFlags.isStrict()) {
            setAsInvalid(this.chkStrict);
        }
    }

    private void initComponents() {
        this.chkPublic = new JCheckBox();
        this.chkPrivate = new JCheckBox();
        this.chkProtected = new JCheckBox();
        this.chkStatic = new JCheckBox();
        this.chkFinal = new JCheckBox();
        this.chkSuper = new JCheckBox();
        this.chkVolatile = new JCheckBox();
        this.chkTransient = new JCheckBox();
        this.chkInterface = new JCheckBox();
        this.chkAbstract = new JCheckBox();
        this.chkNative = new JCheckBox();
        this.chkSynchronized = new JCheckBox();
        this.chkStrict = new JCheckBox();
        this.btnSave = new JButton();
        this.btnClose = new JButton();
        this.btnRefresh = new JButton();
        getContentPane().setLayout(new GridBagLayout());
        setModal(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent paramAnonymousWindowEvent) {
                AccessFlagEditorDialog.this.closeDialog(paramAnonymousWindowEvent);
            }
        });
        this.chkPublic.setText("public");
        this.chkPublic.setBackground(new Color(204, 255, 204));
        GridBagConstraints localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.insets = new Insets(0, 0, 10, 0);
        getContentPane().add(this.chkPublic, localGridBagConstraints);
        this.chkPrivate.setText("private");
        this.chkPrivate.setBackground(new Color(204, 255, 204));
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.insets = new Insets(0, 0, 10, 0);
        getContentPane().add(this.chkPrivate, localGridBagConstraints);
        this.chkProtected.setText("protected");
        this.chkProtected.setBackground(new Color(204, 255, 204));
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.insets = new Insets(0, 0, 10, 0);
        getContentPane().add(this.chkProtected, localGridBagConstraints);
        this.chkStatic.setText("static");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 1;
        getContentPane().add(this.chkStatic, localGridBagConstraints);
        this.chkFinal.setText("final");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 1;
        getContentPane().add(this.chkFinal, localGridBagConstraints);
        this.chkSuper.setText("super");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        getContentPane().add(this.chkSuper, localGridBagConstraints);
        this.chkVolatile.setText("volatile");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 1;
        getContentPane().add(this.chkVolatile, localGridBagConstraints);
        this.chkTransient.setText("transient");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 1;
        getContentPane().add(this.chkTransient, localGridBagConstraints);
        this.chkInterface.setText("interface");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        getContentPane().add(this.chkInterface, localGridBagConstraints);
        this.chkAbstract.setText("abstract");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 1;
        getContentPane().add(this.chkAbstract, localGridBagConstraints);
        this.chkNative.setText("native");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 1;
        getContentPane().add(this.chkNative, localGridBagConstraints);
        this.chkSynchronized.setText("synchronized");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        getContentPane().add(this.chkSynchronized, localGridBagConstraints);
        this.chkStrict.setText("strict");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.anchor = 17;
        localGridBagConstraints.insets = new Insets(0, 0, 20, 0);
        getContentPane().add(this.chkStrict, localGridBagConstraints);
        this.btnSave.setText("Save");
        this.btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                AccessFlagEditorDialog.this.btnSaveActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 1;
        getContentPane().add(this.btnSave, localGridBagConstraints);
        this.btnClose.setText("Close");
        this.btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                AccessFlagEditorDialog.this.btnCloseActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 1;
        getContentPane().add(this.btnClose, localGridBagConstraints);
        this.btnRefresh.setText("Refresh");
        this.btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                AccessFlagEditorDialog.this.btnRefreshActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 1;
        getContentPane().add(this.btnRefresh, localGridBagConstraints);
        pack();
    }

    private void btnCloseActionPerformed(ActionEvent paramActionEvent) {
        setAccessFlags(this.flags);
        closeDialog(null);
    }

    private void btnSaveActionPerformed(ActionEvent paramActionEvent) {
        saveAccessFlags();
        closeDialog(null);
    }

    private void btnRefreshActionPerformed(ActionEvent paramActionEvent) {
        setAccessFlags(this.flags);
    }

    private void closeDialog(WindowEvent paramWindowEvent) {
        setVisible(false);
        dispose();
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/guihelper/AccessFlagEditorDialog.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */