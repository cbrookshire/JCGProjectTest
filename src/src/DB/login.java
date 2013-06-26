package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Login {
    
    static final String DATABASE_URL = "jdbc:mysql://localhost/JCGroup";
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    Employee emp;
    int code;
    String CurrentUsername;
    String CurrentPassword;
    
    public Login() {
    
    }
    
    public int Login(String username, String password)
    {
        
        try
        {
            connection = DriverManager.getConnection(DATABASE_URL, username, password);
            setUsername(username);
            setPassword(password);
            code = getLoginStatus(username);
            if(code == 0)
                return 55;
            code = getEmpType(username);
            return code;
        }
        catch(SQLException sqlException)
        {
            return sqlException.getErrorCode();            
        }
        
    }
    
    private void setPassword(String password)
    {
        CurrentPassword = password;
        
    }
    
    String getPassword()
    {
        return CurrentPassword;
    }
    
    private void setUsername(String username) {
        CurrentUsername = username;
        
    }
    
    String getUsername() {
        
       
        return CurrentUsername;
    }
    
    int getLoginStatus(String username)
    {
        try {
             
            
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "select * from Employee where Username = '" + username + "'");
            while(resultSet.next())
            {
                code = resultSet.getInt("FirstLog");
            }
            return code;
        }
        catch(SQLException sqlE) {
            return sqlE.getErrorCode();
        }
    }
    
    int getEmpType(String username) {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT EmpType from Employee"
                + " where Username = '" + username + "'");
            while(resultSet.next())
            {
                code = resultSet.getInt("EmpType");
            }
            return code;
        }
        catch(SQLException sqlE) {
            return sqlE.getErrorCode();
        }
    }
    
    int updatePassword(String username, String password)
    {
        try {
            statement.execute("SET PASSWORD FOR '" + username + "'@'localhost' = PASSWORD('" + password + "')");
            code = updateLogStatus(username);
            return code;
        }
        catch(SQLException sqlE)
        {
            return sqlE.getErrorCode();
        }
    }
    
    private int updateLogStatus(String username) {
        try {
            statement.execute("UPDATE Employee SET FIRSTLOG = 1 "
                    + "where Username = '" + username + "'");
            return 1;
        }
        catch(SQLException sqlE)
        {
            return sqlE.getErrorCode();
        }
    }
    
    public void logOff()
    {
        try {
                CurrentUsername = null;
                CurrentPassword = null;
                connection.close();
            }catch(Exception exception) {
            }
    }
    
}
