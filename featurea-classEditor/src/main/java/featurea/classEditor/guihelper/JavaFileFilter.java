package featurea.classEditor.guihelper;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

public class JavaFileFilter
        extends FileFilter {
    private Hashtable filters = null;
    private String description = null;
    private String fullDescription = null;
    private boolean useExtensionsInDescription = true;

    public JavaFileFilter() {
    }

    public JavaFileFilter(String paramString) {
        this(paramString, null);
    }

    public JavaFileFilter(String paramString1, String paramString2) {
        this();
        if (paramString1 != null) {
            addExtension(paramString1);
        }
        if (paramString2 != null) {
            setDescription(paramString2);
        }
    }

    public JavaFileFilter(String[] paramArrayOfString) {
        this(paramArrayOfString, null);
    }

    public JavaFileFilter(String[] paramArrayOfString, String paramString) {
        this();
        for (int i = 0; i < paramArrayOfString.length; i++) {
            addExtension(paramArrayOfString[i]);
        }
        if (paramString != null) {
            setDescription(paramString);
        }
    }

    public boolean accept(File paramFile) {
        if (paramFile != null) {
            if (paramFile.isDirectory()) {
                return true;
            }
            String str = getExtension(paramFile);
            if ((str != null) && (this.filters.get(getExtension(paramFile)) != null)) {
                return true;
            }
        }
        return false;
    }

    public String getExtension(File paramFile) {
        if (paramFile != null) {
            String str = paramFile.getName();
            int i = str.lastIndexOf('.');
            if ((i > 0) && (i < str.length() - 1)) {
                return str.substring(i + 1).toLowerCase();
            }
        }
        return null;
    }

    public void addExtension(String paramString) {
        if (this.filters == null) {
            this.filters = new Hashtable(5);
        }
        this.filters.put(paramString.toLowerCase(), this);
        this.fullDescription = null;
    }

    public String getDescription() {
        if (this.fullDescription == null) {
            if ((this.description == null) || (isExtensionListInDescription())) {
                this.fullDescription = (this.description + " (");
                Enumeration localEnumeration = this.filters.keys();
                if (localEnumeration != null) {
                    for (this.fullDescription = (this.fullDescription + "." + (String) localEnumeration.nextElement()); localEnumeration.hasMoreElements(); this.fullDescription = (this.fullDescription + ", " + (String) localEnumeration.nextElement())) {
                    }
                }
                this.fullDescription += ")";
            } else {
                this.fullDescription = this.description;
            }
        }
        return this.fullDescription;
    }

    public void setDescription(String paramString) {
        this.description = paramString;
        this.fullDescription = null;
    }

    public boolean isExtensionListInDescription() {
        return this.useExtensionsInDescription;
    }

    public void setExtensionListInDescription(boolean paramBoolean) {
        this.useExtensionsInDescription = paramBoolean;
        this.fullDescription = null;
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/guihelper/JavaFileFilter.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */