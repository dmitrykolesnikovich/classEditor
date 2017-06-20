package featurea.classEditor.classfile;

import java.util.Stack;

public final class Utils {

    public static String sNewLine = System.getProperty("line.separator");
    public static String sUnderLine = "----------------------------------------------------------------------";

    private Utils() {
        // no op
    }

    public static String convertStrToClassStr(String paramString) {
        return paramString.replace('.', '/');
    }

    public static String convertClassStrToStr(String paramString) {
        return paramString.replace('/', '.');
    }

    public static String getRawDesc(String paramString) {
        if (paramString.startsWith("byte")) {
            return "B";
        }
        if (paramString.startsWith("char")) {
            return "C";
        }
        if (paramString.startsWith("double")) {
            return "D";
        }
        if (paramString.startsWith("float")) {
            return "F";
        }
        if (paramString.startsWith("int")) {
            return "I";
        }
        if (paramString.startsWith("long")) {
            return "L";
        }
        if (paramString.startsWith("short")) {
            return "Z";
        }
        if (paramString.startsWith("boolean")) {
            return "Z";
        }
        if (paramString.startsWith("void")) {
            return "V";
        }
        if (paramString.startsWith("[]")) {
            return "[" + getRawDesc(paramString.substring(2));
        }
        return "L" + convertStrToClassStr(paramString) + ";";
    }

    public static String getReadableDesc(String paramString) {
        if (paramString.length() > 0) {
            switch (paramString.charAt(0)) {
                case 'B':
                    if (paramString.length() == 1) {
                        return "byte";
                    }
                case 'C':
                    if (paramString.length() == 1) {
                        return "char";
                    }
                case 'D':
                    if (paramString.length() == 1) {
                        return "double";
                    }
                case 'F':
                    if (paramString.length() == 1) {
                        return "float";
                    }
                case 'I':
                    if (paramString.length() == 1) {
                        return "int";
                    }
                case 'J':
                    if (paramString.length() == 1) {
                        return "long";
                    }
                case 'S':
                    if (paramString.length() == 1) {
                        return "short";
                    }
                case 'Z':
                    if (paramString.length() == 1) {
                        return "boolean";
                    }
                case 'V':
                    if (paramString.length() == 1) {
                        return "void";
                    }
                case 'L':
                    int i = paramString.indexOf(';');
                    if (paramString.length() > i + 1) {
                        return "unknown";
                    }
                    return convertClassStrToStr(paramString.substring(1, i));
                case '[':
                    String str = getReadableDesc(paramString.substring(1));
                    if (str.equals("unknown")) {
                        return "unknown";
                    }
                    return "[]" + str;
            }
        }
        return "unknown";
    }

    public static String getRawMethodDesc(String[] paramArrayOfString) {
        String str1 = getRawDesc(paramArrayOfString[0]);
        String str2 = "(";
        for (int i = 1; i < paramArrayOfString.length; i++) {
            str2 = str2 + getRawDesc(paramArrayOfString[i]);
        }
        str2 = str2 + ")";
        return str2 + str1;
    }

    public static String[] getReadableMethodDesc(String paramString) {
        Stack localStack = new Stack();
        int i = 1;
        int j = paramString.indexOf(')');
        if ((paramString.length() == 0) || (-1 == j) || (paramString.charAt(0) != '(') || (paramString.length() <= j + 1)) {
            return null;
        }
        String str1;
        if (i == j) {
            str1 = null;
        } else {
            str1 = paramString.substring(i, j);
        }
        String str2 = getReadableDesc(paramString.substring(j + 1));
        localStack.push(str2);
        if (null != str1) {
            i = 0;
            while (i < j - 1) {
                localStack.push(getReadableDesc(str1.substring(i)));
                switch (str1.charAt(i)) {
                    case 'B':
                    case 'C':
                    case 'D':
                    case 'F':
                    case 'I':
                    case 'J':
                    case 'S':
                    case 'V':
                    case 'Z':
                        i++;
                        break;
                    case 'L':
                        i = str1.indexOf(';', i) + 1;
                        break;
                    case '[':
                        while ('[' == str1.charAt(++i)) {
                        }
                        if ('L' == str1.charAt(i)) {
                            i = str1.indexOf(';', i) + 1;
                        } else {
                            i++;
                        }
                        break;
                }
            }
        }
        int k = localStack.size();
        String[] arrayOfString = new String[k];
        while (k > 0) {
            arrayOfString[(--k)] = ((String) localStack.pop());
        }
        return arrayOfString;
    }

    public static boolean isJavaIdentifier(String paramString) {
        int i = paramString.length();
        if (0 == i) {
            return false;
        }
        if (!Character.isJavaIdentifierStart(paramString.charAt(0))) {
            return false;
        }
        for (int j = 1; j < i; j++) {
            if (!Character.isJavaIdentifierPart(paramString.charAt(j))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isJavaClassString(String paramString) {
        int i = paramString.length();
        if (0 == i) {
            return false;
        }
        if (!Character.isJavaIdentifierStart(paramString.charAt(0))) {
            return false;
        }
        for (int j = 1; j < i; j++) {
            if ((!Character.isJavaIdentifierPart(paramString.charAt(j))) && (paramString.charAt(j) != '/')) {
                return false;
            }
        }
        return true;
    }
}
