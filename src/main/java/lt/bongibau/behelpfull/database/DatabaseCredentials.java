package lt.bongibau.behelpfull.database;

public class DatabaseCredentials {

    public static final class Production {
        public static final String HOST = "srv-bdens.insa-toulouse.fr";

        public static final int PORT = 3306;

        public static final String DATABASE = "projet_gei_027";

        public static final String USERNAME = "projet_gei_027";

        public static final String PASSWORD = "tahFah2U";
    }

    public static final class Test {
        public static final String HOST = "localhost";

        public static final String DATABASE = "test";

        public static final String FILE = "test.db";
    }

}
