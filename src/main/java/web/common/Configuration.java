package web.common;

import web.configutils.EnvironmentUrlConfigReader;

public class Configuration {

    public static String baseUrl = null;
    public static boolean headless = true;
    public static boolean devTools = false;
    public static double defaultTimeout = 10000.0;
    public static double poolingInterval = 200.0;
    public static double browserToStartTimeout = 60000.0;
    public static double navigateToURLTimeout = 90000.0;
    public static double locatorTimeout = 30000.0;
    public static double apiRequestTimeout = 60000.0;
    public static String tracesPath = System.getProperty("user.dir") + "/build/pw";
    public static boolean saveTraces = true;
}
