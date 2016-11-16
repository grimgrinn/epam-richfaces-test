package connectionPool;


//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
import settings.BigSettings;

import java.sql.*;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
 * Пул соеденений
 */
public final class ConnectionPool {
  //  public static final Logger MEGALOG = LogManager.getLogger(ConnectionPool.class);
    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> givenAwayConQueue;

    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;
    public static ConnectionPool INSTANCE;

    public static ConnectionPool getInstance() {
        if (INSTANCE == null) {
            synchronized (ConnectionPool.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ConnectionPool();
                }
            }
        }
        return INSTANCE;
    }

    private ConnectionPool() {
        BigSettings dbResourceManager = BigSettings.getInstance();
        this.driverName = dbResourceManager.getValue(DatabaseParams.DB_DRIVER);
        this.url = dbResourceManager.getValue(DatabaseParams.DB_URL);
        this.user = dbResourceManager.getValue(DatabaseParams.DB_USER);
        this.password = dbResourceManager.getValue(DatabaseParams.DB_PASSWORD);
        try {
            this.poolSize = Integer.parseInt(dbResourceManager.getValue(DatabaseParams.DB_POOL_SIZE));
        } catch (NumberFormatException e) {
    //        MEGALOG.error("invalid poolsize format", e);
            this.poolSize = 5;
        }
        initPoolData();
    }

    private void initPoolData() {
        Locale.setDefault(Locale.ENGLISH);
        System.out.println("TRING TO INTI!");
        try {
            Class.forName(driverName);
            connectionQueue = new ArrayBlockingQueue<>(poolSize);
            givenAwayConQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);

                PooledConnection polledConnection = new PooledConnection(connection, connectionQueue, givenAwayConQueue);
                connectionQueue.add(polledConnection);
            }

        } catch (ClassNotFoundException e) {
    //        MEGALOG.fatal("error driver not found", e);
            System.out.println(e + "<- e!");
        } catch (SQLException e) {
    //        MEGALOG.fatal("error get connection", e);
            System.out.println(e + "<- e!");
        }

    }

    public void dispoce() {
        clearConnectionQueue();
    }

    private void clearConnectionQueue() {
        try {
            closeConnectionsQueue(givenAwayConQueue);
            closeConnectionsQueue(connectionQueue);
        } catch (SQLException e) {
     //       MEGALOG.error("error close connections", e);
        }
    }

    public Connection takeConnection() {
        System.out.println("try to tae connection");
        Connection connection = null;
        try {
            connection = connectionQueue.take();
            givenAwayConQueue.add(connection);
        } catch (InterruptedException e) {
    //        MEGALOG.error("error take connection", e);
            System.out.println("error take connection -> "+e);
        }
        return connection;
    }

    public void closeConnections(Connection con, Statement st, ResultSet rs) {
        try {
            con.close();
        } catch (SQLException e) {
    //        MEGALOG.warn("Connection isn't in pool");
        }

        try {
            rs.close();
        } catch (SQLException e) {
     //       MEGALOG.warn("Result set isnt closed");
        }

        try {
            st.close();
        } catch (SQLException e) {
    //        MEGALOG.warn("Statement isnt close");
        }
    }


    public void closeConnection(Connection con, Statement st) {
        try {
            con.close();
        } catch (SQLException e) {
     //       MEGALOG.warn("Connection isn't in pool");
        }

        try {
            st.close();
        } catch (SQLException e) {
     //       MEGALOG.warn("Statement isnt close");
        }
    }

    private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            ((PooledConnection) connection).reallyClose();
        }
    }



}
