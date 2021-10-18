package com.aviad_eshel_johnBryce_JDBC.Infastucture;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ConnectionPool {
    private static final int CONNECTION_AMOUNT =5;
    private static String dbName = "sql11437103";
    private static String userName = "sql11437103";
    private static String password = "NLxRdvgRp9";
    private static String connectionString = "jdbc:mysql://sql11.freemysqlhosting.net/"
            + dbName +
            "?user=" + userName +
            "&password=" + password;
    private static Set <Connection> connections = new HashSet<>(CONNECTION_AMOUNT) ;
    private static ConnectionPool ourInstance = new ConnectionPool();
   // private Connection connection;

    public static ConnectionPool getInstance() {
        return ourInstance;
    }

    private ConnectionPool()  {
        super();
       // Connection connection = DriverManager.getConnection(connectionString);
    }
  //  public Connection getConnection(SingleConnection c){
//        if (connections.isEmpty()){
//         try {
//             c.thread.wait();
//         }catch (InterruptedException e){
//             System.out.println("catch"+ e);
//         }
//
//        }else {
//            connections.remove(c);
//            return c.connection;
//        }
//        connections.remove(c);
//        return c.connection;
//    }
//    public void RestoreConnection (SingleConnection c){
//        c.thread.notify();
//        connections.add(c);
//    }

    public Connection getConnection ()throws SQLException{
        Connection con;
        con= DriverManager.getConnection(connectionString);
        Iterator<Connection> iterator = connections.iterator();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("DB connection attempt established");
            }
        });
        thread.start();
        while (iterator.hasNext()){
            con = iterator.next();

        if (connections.isEmpty()){
            try {
                thread.wait();
            }catch (InterruptedException e){
                System.out.println("thread waiting for available gate");
            }
        }
        else {
                connections.remove(con);
            //   con.close();
            return con;

                // command to dao
            }
        con.close();
        connections.add(con);

        }
        return null;
    }
    public void killConnection ()throws SQLException{
        Connection con;
        con= DriverManager.getConnection(connectionString);
        con.close();
        connections.add(con);
    }
    public void closeAllConnections(){
        for (Connection connection: connections){
           try {
               connection.close();
           }catch (SQLException e){
               System.out.println("catch " + e);
           }
        }
    }

}
