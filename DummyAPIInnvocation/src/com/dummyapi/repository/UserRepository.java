package com.dummyapi.repository;

import com.dummyapi.model.User;
import com.dummyapi.model.UserDetailsDTO;
import com.dummyapi.sqlproperties.DBQueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserRepository {

   private static volatile UserRepository userRepository = null;
   private static final Object lock = new Object();
   private Connection conn = null;
   private final String pattern = "yyyy-MM-dd";
   private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

   public static UserRepository getInstance() {

     if(userRepository == null ) {

        synchronized (lock) {
           if(userRepository == null ) {
              userRepository = new UserRepository();
           }
        }
     }
     return userRepository;

  }

   public void insertUser(User user) {
      Statement stmt = null;
      try {

         conn = getDBConnection();
         stmt = conn.createStatement();

         for(UserDetailsDTO userDetails : user.data) {

            String sql = DBQueries.insertQuery.replace("%USERID%", userDetails.id)
                 .replace("%FIRSTNAME%", userDetails.firstName)
                 .replace("%LASTNAME%", userDetails.lastName)
                 .replace("%TITLE%", userDetails.title)
                 .replace("%PICTURE%", userDetails.picture);
            stmt.executeUpdate(sql);
         }
      } catch (Exception e) {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
      }
      finally {
         if(stmt != null) {
            try {
               stmt.close();
            } catch (SQLException e) {
               throw new RuntimeException(e);
            }
         }
         try {
            conn.commit();
            conn.close();
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
      }
      System.out.println("Records created successfully");
   }

   public void updateUser(User user) {
      Statement stmt = null;
      try {

         conn = getDBConnection();
         stmt = conn.createStatement();

         String modifiedDate = simpleDateFormat.format(new Date());

         for(UserDetailsDTO userDetails : user.data) {

            String sql = DBQueries.updateQuery.replace("%USERID%", userDetails.id)
                    .replace("%FIRSTNAME%", userDetails.firstName)
                    .replace("%LASTNAME%", userDetails.lastName)
                    .replace("%TITLE%", userDetails.title)
                    .replace("%PICTURE%", userDetails.picture)
                    .replace("%NEWDATE%",modifiedDate);
            stmt.executeUpdate(sql);
         }
      } catch (Exception e) {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
      }
      finally {
         if(stmt != null) {
            try {
               stmt.close();
            } catch (SQLException e) {
               throw new RuntimeException(e);
            }
         }
         try {
            conn.commit();
            conn.close();
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
      }
      System.out.println("Records updated successfully");
   }

   private Connection getDBConnection() {
      Connection connection = null;
      try {
         Class.forName("org.postgresql.Driver");
         connection = DriverManager
                 .getConnection("jdbc:postgresql://localhost:5432/userdb",
                         "postgres", "admin");
         connection.setAutoCommit(false);
         System.out.println("Opened database successfully");
      }
      catch (Exception e) {
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         System.exit(0);
      }
      return connection;
   }
}