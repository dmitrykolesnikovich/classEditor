package featurea.classEditor.util;

import java.io.DataInputStream;

public final class Util {

    private Util() {
        // no op
    }

    public static DataInputStream getInputStream(String file) {
        return new DataInputStream(Util.class.getClassLoader().getResourceAsStream(file));
    }

}
