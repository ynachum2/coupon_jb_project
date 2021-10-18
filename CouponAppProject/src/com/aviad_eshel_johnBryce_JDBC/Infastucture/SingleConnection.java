package com.johnBryce.Infastucture;

import java.sql.Connection;

public class SingleConnection  {
    public  Connection connection;
    private  Object keyBlock;
    public Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {

        }
    });

    public SingleConnection( ) {
        this.connection = connection;
        this.keyBlock = "loooloo";
        thread.run();
    }
}
