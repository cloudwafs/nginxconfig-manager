package com.yeahmobi.loadbalance_manager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * @author Ryan Sun
 */

public class WebStart {

    private Server server;

    public void startServer() throws Exception {
        this.server = new Server(8080);
        this.server.setStopAtShutdown(true);
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/");
        webAppContext.setResourceBase("webapp");

        webAppContext.setClassLoader(getClass().getClassLoader());
        this.server.setHandler(webAppContext);
        this.server.start();

        waitForAnyKey();

    }

    protected void waitForAnyKey() throws IOException {
        String timestamp = new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date());

        System.out.println(String.format("[%s] [INFO] Press any key to stop server ... ", timestamp));
        System.in.read();
    }

    public void shutdownServer() throws Exception {
        this.server.stop();
    }

    public static void main(String[] args) throws Exception {
        WebStart server = new WebStart();
        server.startServer();
        server.shutdownServer();
    }

}
