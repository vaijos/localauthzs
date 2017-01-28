
package com.membership.authzs;


import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import com.membership.authzs.controller.AuthzController;
import com.membership.authzs.service.TServimpl;
import com.membership.authzs.util.SrvLog;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.threadPool;

public class App
{
    private static Properties m_prop = null;

    public static String getProperty(String item){
        return m_prop.getProperty(item);
    }

    public static boolean init(){
        m_prop = new Properties();
        String propFileName = "/config.properties";
        try {
            InputStream inputStream = App.class.getResourceAsStream(propFileName); 
            		//getClass().getClassLoader().getResourceAsStream(propFileName);
            m_prop.load(inputStream);
            return true;
        }
        catch ( Exception e)
        {
            SrvLog.logger.error( e.getMessage());
        }
        return false;
    }

    public void start()
    {
        if ( init() == false ) {
            SrvLog.logger.error( "initialization failed, exiting...");
            return;
        }
        try {
            // configure default thread pool
            int maxThreads      = Integer.parseInt(getProperty("maxThreads") );
            int minThreads      = Integer.parseInt(getProperty("minThreads") );
            int timeOutMillis   = Integer.parseInt(getProperty("timeOutMillis") );
            threadPool(maxThreads, minThreads, timeOutMillis);

            // Quite unsafe!
            before(new CorsFilter());
            //new OptionsController();
            new AuthzController(new TServimpl());

            String swaggerJson = new String(Files.readAllBytes(Paths.get("swagger.json")));
            get("/swagger", (req, res) -> {
                return swaggerJson;
            });
        } catch (Exception e) {
            SrvLog.logger.error( e.getMessage() );
            e.printStackTrace();
        }
        SrvLog.logger.debug("spark service is up");

        /////////////// FIXME: for testing
        //Transaction tran  = new GroupManager();
        //tran.test();
        //Transaction tran  = new Authn();
        //tran.test();

        //Transaction tran = new JWTtoken();
        //tran.test();

        //tran = new AuthCodeToken();
        //tran.test();

    }

    public static void main(String[] args) {
        new App().start();
    }
}