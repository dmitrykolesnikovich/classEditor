package featurea.classEditor.gui;

import featurea.classEditor.classfile.ClassFile;
import featurea.classEditor.classfile.Utils;
import featurea.classEditor.gui.attributes.AttributesDialog;
import featurea.classEditor.guihelper.ClassFileStatus;
import featurea.classEditor.guihelper.JavaFileFilter;
import featurea.classEditor.visitors.TextSummaryVisitor;
import featurea.classEditor.visitors.XMLOutputVisitor;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

public class ClassEditor extends JFrame {

    private static int NUM_FILES_IN_HISTORY = 5;
    private GeneralPropPane TabPaneGeneralProp;
    private ConstantPoolPropPane TabPaneConstPool;
    private FieldsPropPane TabPaneFields;
    private MethodsPropPane TabPaneMethods;
    private DefaultMutableTreeNode rootTreeNode;
    private ClassFile classFile;
    private ClassFileStatus classStatus;
    private boolean bEditable;
    private JFileChooser classFileChooser;
    private ArrayList asHistoryFileNames = new ArrayList();
    private ArrayList aHistoryFiles = new ArrayList();
    private Properties Config;
    private JMenu MenuFile;
    private JMenu MenuHelp;
    private JMenuItem MenuItemAbout;
    private JMenuItem MenuItemClose;
    private JMenuItem MenuItemCompleteSummary;
    private JMenuItem MenuItemConstantPool;
    private JMenuItem MenuItemExit;
    private JMenuItem MenuItemExport;
    private JMenuItem MenuItemFields;
    private JMenuItem MenuItemGeneral;
    private JMenuItem MenuItemMethodNoCode;
    private JMenuItem MenuItemMethods;
    private JMenuItem MenuItemNew;
    private JMenuItem MenuItemOpen;
    private JMenuItem MenuItemRelatedClasses;
    private JMenuItem MenuItemSave;
    private JMenuItem MenuItemSaveAs;
    private JMenuItem MenuItemValidateComplete;
    private JMenuItem MenuItemValidateConstPool;
    private JMenuItem MenuItemValidateFields;
    private JMenuItem MenuItemValidateGeneral;
    private JMenuItem MenuItemValidateMethods;
    private JMenu MenuValidateChanges;
    private JMenu MenuView;
    private JMenu MenuViewSummary;
    private JButton btnCloseClass;
    private JToggleButton btnModifyMode;
    private JButton btnNewFile;
    private JButton btnOpenClass;
    private JButton btnRelatedClass;
    private JButton btnSaveClass;
    private JButton btnShowSummary;
    private JButton btnValidate;
    private JMenuBar jMenuBar2;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JSeparator jSeparator3;
    private JSeparator jSeparator4;
    private JSeparator jSeparator5;
    private JSeparator jSeparator6;
    private JSplitPane jSplitPane1;
    private JTabbedPane jTabbedPane1;
    private JToolBar jToolBar1;
    private JTree jTree1;
    private JLabel statusBar1;

    public ClassEditor() {
        File localFile1 = null;
        File localFile2 = null;
        File localFile3 = null;
        this.classFileChooser = new JFileChooser();
        this.classFileChooser.addChoosableFileFilter(new JavaFileFilter("class", "Class Files"));
        this.rootTreeNode = new DefaultMutableTreeNode("Class Files");
        initComponents();
        this.TabPaneGeneralProp = new GeneralPropPane();
        this.jTabbedPane1.addTab("General", new ImageIcon(getClass().getResource("/res/general.gif")), this.TabPaneGeneralProp);
        this.TabPaneConstPool = new ConstantPoolPropPane();
        this.jTabbedPane1.addTab("Constant Pool", new ImageIcon(getClass().getResource("/res/constpool.gif")), this.TabPaneConstPool);
        this.TabPaneFields = new FieldsPropPane();
        this.jTabbedPane1.addTab("Fields", new ImageIcon(getClass().getResource("/res/field.gif")), this.TabPaneFields);
        this.TabPaneMethods = new MethodsPropPane();
        this.jTabbedPane1.addTab("Methods", new ImageIcon(getClass().getResource("/res/method.gif")), this.TabPaneMethods);
        try {
            localFile1 = new File(System.getProperty("user.home") + File.separatorChar + ".ce");
            localFile1.mkdir();
        } catch (Exception localException1) {
            System.err.println("Error creating user preferences directory - " + localFile1.getAbsolutePath());
            System.exit(0);
        }
        try {
            this.Config = new Properties();
            localFile3 = new File(localFile1.getAbsolutePath() + File.separatorChar + ".ce_config");
            Object localObject;
            if (!localFile3.exists()) {
                localFile3.createNewFile();
                this.Config.put("LocalSchemaLocation", System.getProperty("user.dir") + File.separatorChar + "CEJavaClass.xsd");
                localObject = new BufferedOutputStream(new FileOutputStream(localFile3));
                this.Config.store((OutputStream) localObject, "Default properties for classeditor");
                ((BufferedOutputStream) localObject).close();
            } else {
                localObject = new BufferedInputStream(new FileInputStream(localFile3));
                this.Config.load((InputStream) localObject);
                ((BufferedInputStream) localObject).close();
            }
        } catch (Exception localException2) {
            System.err.println("Error reading configuration file - " + localFile3.getAbsolutePath());
            System.exit(0);
        }
        try {
            localFile2 = new File(localFile1.getAbsolutePath() + File.separatorChar + ".ce_hostory");
            if (!localFile2.exists()) {
                localFile2.createNewFile();
            } else {
                LineNumberReader localLineNumberReader = new LineNumberReader(new FileReader(localFile2));
                String str;
                while (null != (str = localLineNumberReader.readLine())) {
                    addToFileHistory(str, true);
                }
                localLineNumberReader.close();
            }
        } catch (Exception localException3) {
            System.err.println("Error creating file history - " + localFile2.getAbsolutePath());
            System.exit(0);
        }
        setSize(800, 600);
        setModifyFlag();
    }

    public static void main(String[] paramArrayOfString) {
        ClassEditor localClassEditor = new ClassEditor();
        localClassEditor.show();
        if (paramArrayOfString.length > 0) {
            for (int i = 0; i < paramArrayOfString.length; i++) {
                try {
                    localClassEditor.chkNLoadClass(paramArrayOfString[i]);
                } catch (Exception localException) {
                    localException.printStackTrace();
                }
            }
        }
        localClassEditor.setMenuAndButtonStatus();
    }

    private void clearTab(int paramInt) {
        this.TabPaneGeneralProp.setClassFile(this.classFile);
        this.TabPaneConstPool.setClassFile(this.classFile);
        this.TabPaneFields.setClassFile(this.classFile);
        this.TabPaneMethods.setClassFile(this.classFile);
        switch (paramInt) {
            case 0:
                this.TabPaneGeneralProp.clear();
                break;
            case 1:
                this.TabPaneConstPool.clear();
                break;
            case 2:
                this.TabPaneFields.clear();
                break;
            case 3:
                this.TabPaneMethods.clear();
                break;
        }
    }

    private void updateTab(int paramInt) {
        this.TabPaneGeneralProp.setClassFile(this.classFile);
        this.TabPaneConstPool.setClassFile(this.classFile);
        this.TabPaneFields.setClassFile(this.classFile);
        this.TabPaneMethods.setClassFile(this.classFile);
        switch (paramInt) {
            case 0:
                this.TabPaneGeneralProp.refresh();
                break;
            case 1:
                this.TabPaneConstPool.refresh();
                break;
            case 2:
                this.TabPaneFields.refresh();
                break;
            case 3:
                this.TabPaneMethods.refresh();
                break;
        }
    }

    private void clearClassData() {
        this.classFile = null;
        clearTab(0);
        clearTab(1);
        clearTab(2);
        clearTab(3);
    }

    private void switchClass(ClassFileStatus paramClassFileStatus) {
        if (this.classStatus == paramClassFileStatus) {
            return;
        }
        if (null != this.classStatus) {
            clearClassData();
            this.classStatus = null;
        }
        this.classStatus = paramClassFileStatus;
        this.classFile = this.classStatus.classFile;
        this.jTabbedPane1.setSelectedIndex(0);
        updateTab(0);
        this.jTree1.setSelectionRow(this.rootTreeNode.getIndex(paramClassFileStatus));
        setTitle("ClassEditor - " + paramClassFileStatus.getTreeDisplayString());
    }

    private void unloadClass() {
        setTitle("ClassEditor");
        if (null != this.classStatus) {
            clearClassData();
            this.rootTreeNode.remove(this.classStatus);
            this.jTree1.setModel(new DefaultTreeModel(this.rootTreeNode));
            this.classStatus = null;
        }
        if (0 != this.rootTreeNode.getChildCount()) {
            ClassFileStatus localClassFileStatus = (ClassFileStatus) this.rootTreeNode.getChildAt(0);
            switchClass(localClassFileStatus);
            return;
        }
        setMenuAndButtonStatus();
    }

    private void setMenuAndButtonStatus() {
        boolean bool = null != this.classStatus;
        this.btnSaveClass.setEnabled(bool);
        this.btnCloseClass.setEnabled(bool);
        this.btnValidate.setEnabled(bool);
        this.btnRelatedClass.setEnabled(bool);
        this.btnShowSummary.setEnabled(bool);
        this.MenuItemClose.setEnabled(bool);
        this.MenuItemSave.setEnabled(bool);
        this.MenuItemSaveAs.setEnabled(bool);
        this.MenuItemExport.setEnabled(bool);
        this.MenuItemExport.setEnabled(bool);
        this.MenuViewSummary.setEnabled(bool);
        this.MenuValidateChanges.setEnabled(bool);
        this.MenuItemRelatedClasses.setEnabled(bool);
    }

    private void writeClass(String paramString) {
        DataOutputStream localDataOutputStream;
        try {
            localDataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(paramString))));
        } catch (FileNotFoundException localFileNotFoundException) {
            localFileNotFoundException.printStackTrace();
            return;
        } catch (IOException localIOException1) {
            localIOException1.printStackTrace();
            return;
        }
        try {
            this.classFile.write(localDataOutputStream);
        } catch (IOException localIOException2) {
            localIOException2.printStackTrace();
        }
        try {
            localDataOutputStream.close();
        } catch (IOException localIOException3) {
            localIOException3.printStackTrace();
        }
    }

    void chkNLoadClass(String paramString)
            throws FileNotFoundException, IOException {
        Object localObject = null;
        ClassFileStatus localClassFileStatus1 = new ClassFileStatus(paramString, null);
        for (int i = this.rootTreeNode.getChildCount(); i > 0; i--) {
            ClassFileStatus localClassFileStatus2 = (ClassFileStatus) this.rootTreeNode.getChildAt(i - 1);
            if ((localClassFileStatus1.sClassName.equals(localClassFileStatus2.sClassName)) && (localClassFileStatus1.sPath.equals(localClassFileStatus2.sPath))) {
                localObject = localClassFileStatus2;
                this.classFile = ((ClassFileStatus) localObject).classFile;
                break;
            }
        }
        if (null == localObject) {
            this.statusBar1.setText("Reading Class " + paramString);
            try {
                readClass(paramString);
                this.statusBar1.setText("Read class: " + paramString);
            } catch (FileNotFoundException localFileNotFoundException) {
                this.statusBar1.setText("File not found: " + paramString);
                return;
            } catch (IOException localIOException) {
                this.statusBar1.setText("Error reading file: " + paramString);
                return;
            }
            localObject = new ClassFileStatus(paramString, this.classFile);
            this.rootTreeNode.add((MutableTreeNode) localObject);
            this.jTree1.setModel(new DefaultTreeModel(this.rootTreeNode));
            this.statusBar1.setText("Loaded class: " + paramString);
        }
        switchClass((ClassFileStatus) localObject);
        setMenuAndButtonStatus();
        addToFileHistory(paramString, false);
    }

    private void addToFileHistory(String paramString, boolean paramBoolean) {
        for (int i = 0; i < this.asHistoryFileNames.size(); i++) {
            if (paramString.equals(this.asHistoryFileNames.get(i))) {
                this.MenuFile.remove((JMenuItem) this.aHistoryFiles.get(i));
                this.aHistoryFiles.remove(i);
                this.asHistoryFileNames.remove(i);
                break;
            }
        }
        if (this.aHistoryFiles.size() >= NUM_FILES_IN_HISTORY) {
            int j = paramBoolean ? 0 : NUM_FILES_IN_HISTORY - 1;
            this.MenuFile.remove((JMenuItem) this.aHistoryFiles.get(j));
            this.asHistoryFileNames.remove(j);
            this.aHistoryFiles.remove(j);
        }
        JMenuItem localJMenuItem = new JMenuItem();
        localJMenuItem.setText(paramString);
        localJMenuItem.setActionCommand(paramString);
        localJMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemFileHistoryActionPerformed(paramAnonymousActionEvent);
            }
        });
        if (paramBoolean) {
            this.asHistoryFileNames.add(paramString);
            this.aHistoryFiles.add(localJMenuItem);
            this.MenuFile.add(localJMenuItem);
        } else {
            this.asHistoryFileNames.add(0, paramString);
            this.aHistoryFiles.add(0, localJMenuItem);
            this.MenuFile.add(localJMenuItem, 10);
        }
    }

    private void readClass(String paramString)
            throws FileNotFoundException, IOException {
        this.classFile = new ClassFile();
        DataInputStream localDataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(paramString))));
        this.classFile.read(localDataInputStream);
        localDataInputStream.close();
    }

    private void createNewClass() {
        int i = this.classFileChooser.showSaveDialog(this);
        if (i != 0) {
            return;
        }
        String str1 = this.classFileChooser.getSelectedFile().getAbsolutePath();
        ClassFile localClassFile = new ClassFile();
        try {
            localClassFile.createSimplestClass();
            File localFile = new File(str1);
            String str2 = localFile.getName();
            str2 = str2.substring(0, str2.indexOf('.'));
            localClassFile.classNames.setThisClassName(str2);
        } catch (IOException localIOException) {
            this.statusBar1.setText("Internal error while creating class");
            return;
        }
        ClassFileStatus localClassFileStatus = new ClassFileStatus(str1, localClassFile);
        this.rootTreeNode.add(localClassFileStatus);
        this.jTree1.setModel(new DefaultTreeModel(this.rootTreeNode));
        this.statusBar1.setText("Loaded class: " + str1);
        switchClass(localClassFileStatus);
    }

    private void openNewFile() {
        int i = this.classFileChooser.showOpenDialog(this);
        if (i != 0) {
            return;
        }
        String str = this.classFileChooser.getSelectedFile().getAbsolutePath();
        this.statusBar1.setText("Loading Class " + str);
        try {
            chkNLoadClass(str);
        } catch (FileNotFoundException localFileNotFoundException) {
            this.statusBar1.setText("File not found: " + str);
        } catch (IOException localIOException) {
            this.statusBar1.setText("Error reading file: " + str);
        }
    }

    private void closeClass() {
        if (null != this.classStatus) {
            this.statusBar1.setText("Unloading Class " + this.classStatus.sFileName);
            unloadClass();
        }
        this.statusBar1.setText("Ready");
    }

    private void saveClass() {
        if (null != this.classStatus) {
            this.statusBar1.setText("Saving to class " + this.classStatus.sFileName);
            writeClass(this.classStatus.sFileName);
            this.statusBar1.setText("Wrote class " + this.classStatus.sFileName);
        }
    }

    private void showValidation(boolean paramBoolean, Vector paramVector) {
        if (!paramBoolean) {
            StringBuffer localStringBuffer = new StringBuffer(256);
            for (int i = 0; i < paramVector.size(); i++) {
                localStringBuffer.append((String) paramVector.elementAt(i)).append(Utils.sNewLine);
            }
            SummaryDialog localSummaryDialog = new SummaryDialog(this, true);
            localSummaryDialog.setClassFileSummary(localStringBuffer.toString());
            localSummaryDialog.show();
        } else {
            JOptionPane.showMessageDialog(this, "No errors detected.", "Validation Success", 1);
        }
    }

    private void showCompleteSummary() {
        if (null != this.classFile) {
            TextSummaryVisitor localTextSummaryVisitor = new TextSummaryVisitor();
            localTextSummaryVisitor.visitClass(this.classFile);
            SummaryDialog localSummaryDialog = new SummaryDialog(this, true);
            localSummaryDialog.setClassFileSummary(localTextSummaryVisitor.getSummary().toString());
            localSummaryDialog.show();
        }
    }

    private void showMethodsSummary(boolean paramBoolean) {
        if (null != this.classFile) {
            TextSummaryVisitor localTextSummaryVisitor = new TextSummaryVisitor(!paramBoolean);
            localTextSummaryVisitor.visitMethods(this.classFile.methods);
            SummaryDialog localSummaryDialog = new SummaryDialog(this, true);
            localSummaryDialog.setClassFileSummary(localTextSummaryVisitor.getSummary().toString());
            localSummaryDialog.show();
        }
    }

    private void showRelatedClasses() {
        if (null != this.classFile) {
            RelatedClasses localRelatedClasses = new RelatedClasses(this, true);
            localRelatedClasses.showRelatedClasses(this, this.classFile.constantPool);
            localRelatedClasses.show();
        }
    }

    private void setModifyFlag() {
        this.TabPaneGeneralProp.setModifyMode(this.bEditable);
        this.TabPaneConstPool.setModifyMode(this.bEditable);
        this.TabPaneFields.setModifyMode(this.bEditable);
        this.TabPaneMethods.setModifyMode(this.bEditable);
    }

    private void initComponents() {
        this.jToolBar1 = new JToolBar();
        this.btnOpenClass = new JButton();
        this.btnNewFile = new JButton();
        this.btnSaveClass = new JButton();
        this.btnCloseClass = new JButton();
        this.btnValidate = new JButton();
        this.btnRelatedClass = new JButton();
        this.btnShowSummary = new JButton();
        this.jSeparator5 = new JSeparator();
        this.btnModifyMode = new JToggleButton();
        this.jSplitPane1 = new JSplitPane();
        this.jTree1 = new JTree(this.rootTreeNode);
        ((DefaultTreeCellRenderer) this.jTree1.getCellRenderer()).setLeafIcon(new ImageIcon(getClass().getResource("/res/class.gif")));
        this.jTabbedPane1 = new JTabbedPane();
        this.statusBar1 = new JLabel();
        this.jMenuBar2 = new JMenuBar();
        this.MenuFile = new JMenu();
        this.MenuItemNew = new JMenuItem();
        this.MenuItemOpen = new JMenuItem();
        this.MenuItemClose = new JMenuItem();
        this.jSeparator1 = new JSeparator();
        this.MenuItemSave = new JMenuItem();
        this.MenuItemSaveAs = new JMenuItem();
        this.MenuItemExport = new JMenuItem();
        this.jSeparator2 = new JSeparator();
        this.MenuItemExit = new JMenuItem();
        this.jSeparator6 = new JSeparator();
        this.MenuView = new JMenu();
        this.MenuViewSummary = new JMenu();
        this.MenuItemCompleteSummary = new JMenuItem();
        this.jSeparator3 = new JSeparator();
        this.MenuItemGeneral = new JMenuItem();
        this.MenuItemConstantPool = new JMenuItem();
        this.MenuItemFields = new JMenuItem();
        this.MenuItemMethods = new JMenuItem();
        this.MenuItemMethodNoCode = new JMenuItem();
        this.MenuValidateChanges = new JMenu();
        this.MenuItemValidateComplete = new JMenuItem();
        this.jSeparator4 = new JSeparator();
        this.MenuItemValidateGeneral = new JMenuItem();
        this.MenuItemValidateConstPool = new JMenuItem();
        this.MenuItemValidateFields = new JMenuItem();
        this.MenuItemValidateMethods = new JMenuItem();
        this.MenuItemRelatedClasses = new JMenuItem();
        this.MenuHelp = new JMenu();
        this.MenuItemAbout = new JMenuItem();
        setTitle("ClassEditor");
        setName("MainFrame");
        setIconImage(new ImageIcon(getClass().getResource("/res/classeditor.gif")).getImage());
        setForeground(Color.white);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent paramAnonymousWindowEvent) {
                ClassEditor.this.exitForm(paramAnonymousWindowEvent);
            }
        });
        this.jToolBar1.setFloatable(false);
        this.btnOpenClass.setIcon(new ImageIcon(getClass().getResource("/res/open1.gif")));
        this.btnOpenClass.setText("Open");
        this.btnOpenClass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.toolBarOpenAction(paramAnonymousActionEvent);
            }
        });
        this.jToolBar1.add(this.btnOpenClass);
        this.btnNewFile.setIcon(new ImageIcon(getClass().getResource("/res/newfile1.gif")));
        this.btnNewFile.setText("New");
        this.btnNewFile.setName("");
        this.btnNewFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.toolBarNewFileAction(paramAnonymousActionEvent);
            }
        });
        this.jToolBar1.add(this.btnNewFile);
        this.btnSaveClass.setIcon(new ImageIcon(getClass().getResource("/res/save1.gif")));
        this.btnSaveClass.setText("Save");
        this.btnSaveClass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.toolBarSaveAction(paramAnonymousActionEvent);
            }
        });
        this.jToolBar1.add(this.btnSaveClass);
        this.btnCloseClass.setIcon(new ImageIcon(getClass().getResource("/res/close1.gif")));
        this.btnCloseClass.setText("Close");
        this.btnCloseClass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.toolBarCloseAction(paramAnonymousActionEvent);
            }
        });
        this.jToolBar1.add(this.btnCloseClass);
        this.btnValidate.setIcon(new ImageIcon(getClass().getResource("/res/verify3.gif")));
        this.btnValidate.setText("Validate");
        this.btnValidate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.btnValidateActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.jToolBar1.add(this.btnValidate);
        this.btnRelatedClass.setIcon(new ImageIcon(getClass().getResource("/res/related1.gif")));
        this.btnRelatedClass.setText("Related");
        this.btnRelatedClass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.btnRelatedClassActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.jToolBar1.add(this.btnRelatedClass);
        this.btnShowSummary.setIcon(new ImageIcon(getClass().getResource("/res/report1.gif")));
        this.btnShowSummary.setText("Summary");
        this.btnShowSummary.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.btnShowSummaryActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.jToolBar1.add(this.btnShowSummary);
        this.jToolBar1.add(this.jSeparator5);
        this.btnModifyMode.setBackground(new Color(153, 255, 153));
        this.btnModifyMode.setText("Modify Mode (Off)");
        this.btnModifyMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.btnModifyModeActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.jToolBar1.add(this.btnModifyMode);
        getContentPane().add(this.jToolBar1, "North");
        this.jSplitPane1.setDividerLocation(100);
        this.jSplitPane1.setOneTouchExpandable(true);
        this.jSplitPane1.setAutoscrolls(true);
        this.jTree1.setRootVisible(false);
        this.jTree1.setMinimumSize(new Dimension(50, 400));
        this.jTree1.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent paramAnonymousTreeSelectionEvent) {
                ClassEditor.this.jTree1ValueChanged(paramAnonymousTreeSelectionEvent);
            }
        });
        this.jSplitPane1.setLeftComponent(this.jTree1);
        this.jTabbedPane1.setMinimumSize(new Dimension(400, 400));
        this.jTabbedPane1.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent paramAnonymousChangeEvent) {
                ClassEditor.this.jTabbedPane1StateChanged(paramAnonymousChangeEvent);
            }
        });
        this.jSplitPane1.setRightComponent(this.jTabbedPane1);
        getContentPane().add(this.jSplitPane1, "Center");
        this.statusBar1.setText(" ");
        this.statusBar1.setBorder(new EtchedBorder(0));
        getContentPane().add(this.statusBar1, "South");
        this.MenuFile.setMnemonic('F');
        this.MenuFile.setText("File");
        this.MenuItemNew.setMnemonic('N');
        this.MenuItemNew.setAccelerator(KeyStroke.getKeyStroke(78, 2));
        this.MenuItemNew.setText("New");
        this.MenuItemNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemNewActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuFile.add(this.MenuItemNew);
        this.MenuItemOpen.setMnemonic('O');
        this.MenuItemOpen.setAccelerator(KeyStroke.getKeyStroke(79, 2));
        this.MenuItemOpen.setText("Open");
        this.MenuItemOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemOpenActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuFile.add(this.MenuItemOpen);
        this.MenuItemClose.setText("Close");
        this.MenuItemClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemCloseActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuFile.add(this.MenuItemClose);
        this.MenuFile.add(this.jSeparator1);
        this.MenuItemSave.setAccelerator(KeyStroke.getKeyStroke(83, 2));
        this.MenuItemSave.setMnemonic('S');
        this.MenuItemSave.setText("Save");
        this.MenuItemSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemSaveActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuFile.add(this.MenuItemSave);
        this.MenuItemSaveAs.setAccelerator(KeyStroke.getKeyStroke(83, 3));
        this.MenuItemSaveAs.setMnemonic('A');
        this.MenuItemSaveAs.setText("Save As");
        this.MenuItemSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemSaveAsActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuFile.add(this.MenuItemSaveAs);
        this.MenuItemExport.setAccelerator(KeyStroke.getKeyStroke(88, 2));
        this.MenuItemExport.setMnemonic('X');
        this.MenuItemExport.setText("Export to XML");
        this.MenuItemExport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemExportActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuFile.add(this.MenuItemExport);
        this.MenuFile.add(this.jSeparator2);
        this.MenuItemExit.setMnemonic('x');
        this.MenuItemExit.setText("Exit");
        this.MenuItemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemExitActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuFile.add(this.MenuItemExit);
        this.MenuFile.add(this.jSeparator6);
        this.jMenuBar2.add(this.MenuFile);
        this.MenuView.setMnemonic('V');
        this.MenuView.setText("View");
        this.MenuViewSummary.setText("Summary");
        this.MenuItemCompleteSummary.setText("Complete");
        this.MenuItemCompleteSummary.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemCompleteSummaryActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuViewSummary.add(this.MenuItemCompleteSummary);
        this.MenuViewSummary.add(this.jSeparator3);
        this.MenuItemGeneral.setText("General");
        this.MenuItemGeneral.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemGeneralActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuViewSummary.add(this.MenuItemGeneral);
        this.MenuItemConstantPool.setText("Constant Pool");
        this.MenuItemConstantPool.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemConstantPoolActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuViewSummary.add(this.MenuItemConstantPool);
        this.MenuItemFields.setText("Fields");
        this.MenuItemFields.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemFieldsActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuViewSummary.add(this.MenuItemFields);
        this.MenuItemMethods.setText("Methods");
        this.MenuItemMethods.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemMethodsActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuViewSummary.add(this.MenuItemMethods);
        this.MenuItemMethodNoCode.setText("Methods without code");
        this.MenuItemMethodNoCode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemMethodNoCodeActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuViewSummary.add(this.MenuItemMethodNoCode);
        this.MenuView.add(this.MenuViewSummary);
        this.MenuValidateChanges.setText("Validate Changes");
        this.MenuItemValidateComplete.setAccelerator(KeyStroke.getKeyStroke(67, 3));
        this.MenuItemValidateComplete.setText("Complete");
        this.MenuItemValidateComplete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemValidateCompleteActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuValidateChanges.add(this.MenuItemValidateComplete);
        this.MenuValidateChanges.add(this.jSeparator4);
        this.MenuItemValidateGeneral.setAccelerator(KeyStroke.getKeyStroke(71, 3));
        this.MenuItemValidateGeneral.setText("General");
        this.MenuItemValidateGeneral.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemValidateGeneralActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuValidateChanges.add(this.MenuItemValidateGeneral);
        this.MenuItemValidateConstPool.setAccelerator(KeyStroke.getKeyStroke(80, 3));
        this.MenuItemValidateConstPool.setText("Constant Pool");
        this.MenuItemValidateConstPool.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemValidateConstPoolActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuValidateChanges.add(this.MenuItemValidateConstPool);
        this.MenuItemValidateFields.setAccelerator(KeyStroke.getKeyStroke(70, 3));
        this.MenuItemValidateFields.setText("Fields");
        this.MenuItemValidateFields.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemValidateFieldsActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuValidateChanges.add(this.MenuItemValidateFields);
        this.MenuItemValidateMethods.setAccelerator(KeyStroke.getKeyStroke(77, 3));
        this.MenuItemValidateMethods.setText("Methods");
        this.MenuItemValidateMethods.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemValidateMethodsActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuValidateChanges.add(this.MenuItemValidateMethods);
        this.MenuView.add(this.MenuValidateChanges);
        this.MenuItemRelatedClasses.setAccelerator(KeyStroke.getKeyStroke(82, 2));
        this.MenuItemRelatedClasses.setText("Related Classes");
        this.MenuItemRelatedClasses.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemRelatedClassesActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuView.add(this.MenuItemRelatedClasses);
        this.jMenuBar2.add(this.MenuView);
        this.MenuHelp.setMnemonic('H');
        this.MenuHelp.setText("Help");
        this.MenuItemAbout.setText("About...");
        this.MenuItemAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ClassEditor.this.MenuItemAboutActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.MenuHelp.add(this.MenuItemAbout);
        this.jMenuBar2.add(this.MenuHelp);
        setJMenuBar(this.jMenuBar2);
        pack();
    }

    private void MenuItemExportActionPerformed(ActionEvent paramActionEvent) {
        if (null != this.classStatus) {
            JFileChooser localJFileChooser = new JFileChooser();
            localJFileChooser.addChoosableFileFilter(new JavaFileFilter("xml", "XML Files"));
            int i = localJFileChooser.showSaveDialog(this);
            if (i != 0) {
                return;
            }
            String str = localJFileChooser.getSelectedFile().getAbsolutePath();
            this.statusBar1.setText("Saving to XML file " + str);
            try {
                FileOutputStream localFileOutputStream = new FileOutputStream(str, false);
                XMLOutputVisitor localXMLOutputVisitor = new XMLOutputVisitor();
                if (null != this.Config.get("LocalSchemaLocation")) {
                    localXMLOutputVisitor.setLocalSchema((String) this.Config.get("LocalSchemaLocation"));
                }
                localXMLOutputVisitor.visitClass(this.classFile);
                localXMLOutputVisitor.getAsString(localFileOutputStream);
            } catch (Exception localException) {
                localException.printStackTrace();
            }
            this.statusBar1.setText("Wrote XML file " + str);
        }
    }

    private void btnModifyModeActionPerformed(ActionEvent paramActionEvent) {
        this.bEditable = this.btnModifyMode.isSelected();
        this.btnModifyMode.setText("Modify Mode (" + (this.bEditable ? "On" : "Off") + ")");
        this.btnModifyMode.setBackground(this.bEditable ? Color.pink : new Color(153, 255, 153));
        setModifyFlag();
    }

    private void btnRelatedClassActionPerformed(ActionEvent paramActionEvent) {
        showRelatedClasses();
    }

    private void MenuItemRelatedClassesActionPerformed(ActionEvent paramActionEvent) {
        showRelatedClasses();
    }

    private void MenuItemMethodNoCodeActionPerformed(ActionEvent paramActionEvent) {
        showMethodsSummary(false);
    }

    private void MenuItemMethodsActionPerformed(ActionEvent paramActionEvent) {
        showMethodsSummary(true);
    }

    private void MenuItemFieldsActionPerformed(ActionEvent paramActionEvent) {
        if (null != this.classFile) {
            TextSummaryVisitor localTextSummaryVisitor = new TextSummaryVisitor();
            localTextSummaryVisitor.visitFields(this.classFile.fields);
            SummaryDialog localSummaryDialog = new SummaryDialog(this, true);
            localSummaryDialog.setClassFileSummary(localTextSummaryVisitor.getSummary().toString());
            localSummaryDialog.show();
        }
    }

    private void MenuItemConstantPoolActionPerformed(ActionEvent paramActionEvent) {
        if (null != this.classFile) {
            TextSummaryVisitor localTextSummaryVisitor = new TextSummaryVisitor();
            localTextSummaryVisitor.visitConstantPool(this.classFile.constantPool);
            SummaryDialog localSummaryDialog = new SummaryDialog(this, true);
            localSummaryDialog.setClassFileSummary(localTextSummaryVisitor.getSummary().toString());
            localSummaryDialog.show();
        }
    }

    private void btnShowSummaryActionPerformed(ActionEvent paramActionEvent) {
        showCompleteSummary();
    }

    private void MenuItemValidateMethodsActionPerformed(ActionEvent paramActionEvent) {
        if (null != this.classFile) {
            Vector localVector = new Vector();
            boolean bool = this.classFile.methods.verify(localVector);
            showValidation(bool, localVector);
        }
    }

    private void MenuItemValidateFieldsActionPerformed(ActionEvent paramActionEvent) {
        if (null != this.classFile) {
            Vector localVector = new Vector();
            boolean bool = this.classFile.fields.verify(localVector);
            showValidation(bool, localVector);
        }
    }

    private void MenuItemValidateConstPoolActionPerformed(ActionEvent paramActionEvent) {
        if (null != this.classFile) {
            Vector localVector = new Vector();
            boolean bool = this.classFile.constantPool.verify(localVector);
            showValidation(bool, localVector);
        }
    }

    private void MenuItemValidateGeneralActionPerformed(ActionEvent paramActionEvent) {
        if (null != this.classFile) {
            Vector localVector = new Vector();
            boolean bool = this.classFile.version.verify(localVector);
            bool = (bool) && (this.classFile.accessFlags.verify("ClassFile", localVector, true));
            bool = (bool) && (this.classFile.classNames.verify(localVector));
            bool = (bool) && (this.classFile.interfaces.verify(localVector));
            bool = (bool) && (this.classFile.attributes.verify("ClassFile", localVector));
            showValidation(bool, localVector);
            AttributesDialog localAttributesDialog = new AttributesDialog(this, false);
            localAttributesDialog.setInput(this.classFile.attributes, this.classFile.constantPool, true);
            localAttributesDialog.show();
        }
    }

    private void MenuItemFileHistoryActionPerformed(ActionEvent paramActionEvent) {
        String str = paramActionEvent.getActionCommand();
        try {
            chkNLoadClass(str);
        } catch (FileNotFoundException localFileNotFoundException) {
            this.statusBar1.setText("File not found: " + str);
        } catch (IOException localIOException) {
            this.statusBar1.setText("Error reading file: " + str);
        }
    }

    private void MenuItemValidateCompleteActionPerformed(ActionEvent paramActionEvent) {
        btnValidateActionPerformed(paramActionEvent);
    }

    private void MenuItemGeneralActionPerformed(ActionEvent paramActionEvent) {
        if (null != this.classFile) {
            TextSummaryVisitor localTextSummaryVisitor = new TextSummaryVisitor();
            localTextSummaryVisitor.visitVersion(this.classFile.version);
            localTextSummaryVisitor.visitAccessFlags(this.classFile.accessFlags);
            localTextSummaryVisitor.visitClassNames(this.classFile.classNames);
            localTextSummaryVisitor.visitAttributes(this.classFile.attributes);
            localTextSummaryVisitor.visitInterfaces(this.classFile.interfaces);
            SummaryDialog localSummaryDialog = new SummaryDialog(this, true);
            localSummaryDialog.setClassFileSummary(localTextSummaryVisitor.getSummary().toString());
            localSummaryDialog.show();
        }
    }

    private void MenuItemCompleteSummaryActionPerformed(ActionEvent paramActionEvent) {
        showCompleteSummary();
    }

    private void btnValidateActionPerformed(ActionEvent paramActionEvent) {
        if (null != this.classFile) {
            Vector localVector = new Vector();
            boolean bool = this.classFile.verify(localVector);
            showValidation(bool, localVector);
        }
    }

    private void jTabbedPane1StateChanged(ChangeEvent paramChangeEvent) {
        if (null == this.classFile) {
            return;
        }
        updateTab(this.jTabbedPane1.getSelectedIndex());
    }

    private void jTree1ValueChanged(TreeSelectionEvent paramTreeSelectionEvent) {
        DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode) this.jTree1.getLastSelectedPathComponent();
        if (localDefaultMutableTreeNode == null) {
            return;
        }
        ClassFileStatus localClassFileStatus = (ClassFileStatus) localDefaultMutableTreeNode;
        switchClass(localClassFileStatus);
    }

    private void MenuItemSaveAsActionPerformed(ActionEvent paramActionEvent) {
        if (null != this.classStatus) {
            int i = this.classFileChooser.showSaveDialog(this);
            if (i != 0) {
                return;
            }
            String str = this.classFileChooser.getSelectedFile().getAbsolutePath();
            this.statusBar1.setText("Saving to class " + str);
            writeClass(str);
            this.statusBar1.setText("Wrote class " + str);
        }
    }

    private void MenuItemSaveActionPerformed(ActionEvent paramActionEvent) {
        saveClass();
    }

    private void toolBarSaveAction(ActionEvent paramActionEvent) {
        saveClass();
    }

    private void MenuItemCloseActionPerformed(ActionEvent paramActionEvent) {
        closeClass();
    }

    private void MenuItemOpenActionPerformed(ActionEvent paramActionEvent) {
        openNewFile();
    }

    private void toolBarOpenAction(ActionEvent paramActionEvent) {
        openNewFile();
    }

    private void toolBarCloseAction(ActionEvent paramActionEvent) {
        closeClass();
    }

    private void toolBarNewFileAction(ActionEvent paramActionEvent) {
        createNewClass();
    }

    private void MenuItemNewActionPerformed(ActionEvent paramActionEvent) {
        createNewClass();
    }

    private void MenuItemExitActionPerformed(ActionEvent paramActionEvent) {
        saveHistAndExit();
    }

    private void MenuItemAboutActionPerformed(ActionEvent paramActionEvent) {
        AboutDialog localAboutDialog = new AboutDialog(this, false);
        localAboutDialog.show();
    }

    private void exitForm(WindowEvent paramWindowEvent) {
        saveHistAndExit();
    }

    private void saveHistAndExit() {
        try {
            File localFile = new File(System.getProperty("user.home") + File.separatorChar + ".ce" + File.separatorChar + ".ce_hostory");
            FileWriter localFileWriter = new FileWriter(localFile);
            String str = System.getProperty("line.separator");
            for (int i = 0; i < this.asHistoryFileNames.size(); i++) {
                localFileWriter.write(this.asHistoryFileNames.get(i) + str);
            }
            localFileWriter.close();
        } catch (Exception localException) {
            System.err.println("Could not save file history - " + localException.toString());
        }
        System.exit(0);
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/gui/ClassEditor.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */