package de.olivervier.util;

public class OS {

    private static final String WINDOWS_SPECIFIER = "WIN";
    private static final String LINUX_SPECIFIER = "NUX";

    public static OS_TYPE getOS() {

        String osName = System.getProperty("os.name");

        if(osName.toUpperCase().contains(WINDOWS_SPECIFIER.toUpperCase())) {
            return OS_TYPE.WINDOWS;
        }

        if(osName.toUpperCase().contains(LINUX_SPECIFIER.toUpperCase())) {
            return OS_TYPE.LINUX;
        }

        return OS_TYPE.UNSPECIFIED;
    }
}