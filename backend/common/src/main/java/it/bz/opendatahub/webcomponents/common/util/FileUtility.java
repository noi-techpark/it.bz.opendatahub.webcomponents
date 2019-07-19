package it.bz.opendatahub.webcomponents.common.util;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileUtility {
    private FileUtility() {}

    public static String byteToString(byte[] byteData) {
        return new String(byteData, UTF_8);
    }
}
