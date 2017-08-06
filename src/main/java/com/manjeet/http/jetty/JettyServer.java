package com.manjeet.http.jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manjeet.common.ConfigReader;
import com.manjeet.http.rest.handler.HttpApiHandler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class JettyServer {
    public static final Logger logger = LoggerFactory.getLogger(JettyServer.class);

    public static void main(String[] args) throws Exception {
        String ctxPath = ConfigReader.getStringProperty(ConfigReader.getProperties(), "jetty.server.ctxPath", "/");
        int port = ConfigReader.getIntProperty(ConfigReader.getProperties(), "jetty.server.port", 8888);
        String requestLogPath = ConfigReader.getStringProperty(ConfigReader.getProperties(), "jetty.server.requestLogPath", "/var/log/test/request.log");
        
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setContextPath(ctxPath);

        servletContextHandler.addEventListener(new ServletContextListener() {
            public void contextInitialized(ServletContextEvent servletContextEvent) {
                logger.info("Listener Initializing");
            }

            public void contextDestroyed(ServletContextEvent servletContextEvent) {
                logger.info("Listenener Destroyed");
            }
        });
        ServletHolder sendNowJerseyServlet = servletContextHandler.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        sendNowJerseyServlet.setInitOrder(0);
        sendNowJerseyServlet.setInitParameter("jersey.config.server.provider.classnames", 
                                             HttpApiHandler.class.getCanonicalName());

        Server jettyServer = new Server(port);
        // Add the ResourceHandler to the server.
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { servletContextHandler, new DefaultHandler() });

        // Create request log handler (access log)
        NCSARequestLog requestLog = new NCSARequestLog();
        requestLog.setFilename(requestLogPath);
        requestLog.setFilenameDateFormat("yyyy_MM_dd");
        requestLog.setRetainDays(10);
        requestLog.setAppend(true);
        requestLog.setExtended(true);
        requestLog.setLogLatency(true);
        RequestLogHandler requestLogHandler = new RequestLogHandler();
        requestLogHandler.setRequestLog(requestLog);
        requestLog.setAppend(true);

        // Make main handler list be tracked by request logging
        requestLogHandler.setHandler(handlers);

        // Setup top level handler list, what the server uses
        HandlerList topLevelHandlers = new HandlerList();
        topLevelHandlers.addHandler(requestLogHandler);

        jettyServer.setHandler(topLevelHandlers);

        try {
            jettyServer.start();
            System.out.println("Started Jetty Server at port=" + port );
            jettyServer.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jettyServer.destroy();
        }
    }
}
