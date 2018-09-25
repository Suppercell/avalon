package tomcat;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;

public class ApplicationBoot {

    private String PROJECT_PATH  = ApplicationBoot.class.getResource("/").getPath()
                                     .replace("target/test-classes/", "");
    private String WEB_APP_PATH  = PROJECT_PATH + "/src/main/webapp";
    private String CATALINA_HOME = PROJECT_PATH + "/src/test/java/tomcat/";
    private Tomcat tomcat        = new Tomcat();

    private int    PORT          = 8080;
    private String CONTEXT_PATH  = "/";

    public void start() throws Exception {

        tomcat.setPort(PORT);
        tomcat.setBaseDir(CATALINA_HOME);
        tomcat.getHost().setAppBase(WEB_APP_PATH);
        try {
            StandardServer server = (StandardServer) tomcat.getServer();
            AprLifecycleListener listener = new AprLifecycleListener();
            server.addLifecycleListener(listener);
            tomcat.addWebapp(CONTEXT_PATH, WEB_APP_PATH);
        } catch (ServletException e) {
            e.printStackTrace();
            throw e;
        }
        try {
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
            throw e;
        }
        System.out.println("Tomcat started.");
    }

    public void stop() throws Exception {
        try {
            tomcat.stop();
        } catch (LifecycleException ex) {
            ex.printStackTrace();
            throw ex;
        }
        System.out.println("Tomcat stoped");
    }

    public static void main(String[] args) throws Exception {
        ApplicationBoot embededTomcat = new ApplicationBoot();
        embededTomcat.start();
    }
}
