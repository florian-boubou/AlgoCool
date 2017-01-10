package algopars.util.color;

/**
 * Created by yoann on 10/01/17.
 */
public enum OS {

    WINDOWS("win"),
    MAC("mac"),
    LINUX("nix", "nux", "aix"),
    SOLARIS("sunos")
    ;

    private String[] patterns;

    OS(String... patterns) {
        this.patterns = patterns;
    }

    private boolean match(String os) {
        for (String p : patterns) {
            if (os.contains(p))
                return true;
        }
        return false;
    }

    private static       OS     OS;
    private static final String P_OS = System.getProperty("os.name").toLowerCase();

    public static OS getOS() {
        if (OS == null) {
            for (OS os : values()) {
                if (os.match(P_OS)) {
                    OS = os;
                    break;
                }
            }
        }
        return OS;
    }
}
