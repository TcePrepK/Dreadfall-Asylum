package toolbox;

public class Logger {
    public static void out(final String message) {
        System.out.println(message);
    }

    public static void out(final Object obj) {
        System.out.println(obj.toString());
    }

    public static void warn(final String message) {
        System.err.println(message);
    }

    public static void warn(final Object obj) {
        System.err.println(obj.toString());
    }

    public static void error(final String message) {
        throw new RuntimeException(message);
    }
}
