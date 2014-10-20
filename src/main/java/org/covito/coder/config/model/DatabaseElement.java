package org.covito.coder.config.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import org.covito.coder.util.MessagesUtil;
import org.covito.coder.util.ObjectFactory;

public class DatabaseElement implements Serializable, Comparable<DatabaseElement> {

    private static final long serialVersionUID = -4793412674735445680L;
    private String            name;
    private String            driverClass;
    private String            connectionUrl;
    private String            username;
    private String            password;
    private String            schema;

    public DatabaseElement(String name, String driverClass, String connectionUrl, String username, String password,
                           String schema){
        setName(name);
        setDriverClass(driverClass);
        setConnectionUrl(connectionUrl);
        setUsername(username);
        setPassword(password);
        setSchema(schema);
    }

    public String getUsername() {
        return username == null ? "" : username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password == null ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name == null ? "" : name;
    }

    public void setName(String name) {
        if (name == null || name.length() == 0) throw new NullPointerException("You must specify a name!");
        this.name = name;
    }

    public String getDriverClass() {
        return this.driverClass == null ? "" : driverClass;
    }

    public void setDriverClass(String driverClass) {
        if (driverClass == null) throw new NullPointerException("You must specify a driver!");
        this.driverClass = driverClass;
    }

    public String getConnectionUrl() {
        return this.connectionUrl == null ? "" : connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        if (connectionUrl == null) throw new NullPointerException("You must specify a connection URL!");
        this.connectionUrl = connectionUrl;
    }

    /**
     * Connects to the database. Attempts to load the driver and connect to this instance's url.
     */
    public Connection connect() throws SQLException, ClassNotFoundException {
        Driver driver = getDriver();

        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);

        Connection conn = driver.connect(connectionUrl, props);

        if (conn == null) {
            throw new SQLException(MessagesUtil.getString("RuntimeError.2")); //$NON-NLS-1$
        }

        return conn;
    }

    private Driver getDriver() {
        Driver driver;

        try {
            Class<?> clazz = ObjectFactory.externalClassForName(driverClass);
            driver = (Driver) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(MessagesUtil.getString("RuntimeError.3"), e);
        }
        return driver;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DatabaseElement)){
            return false;
        }
        if (((DatabaseElement) obj).getName().equals(name)){
            return true;
        }
        return false;
    }

    public int hashCode() {
        return name.hashCode();
    }

    public int compareTo(DatabaseElement other) {
        return name.toLowerCase().compareTo(other.getName().toLowerCase());
    }

    public String toString() {
        return this.name;
    }

    public String getSchema() {
        return schema == null ? "" : schema.toUpperCase();
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
