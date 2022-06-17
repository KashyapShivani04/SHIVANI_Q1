package com.dummyapi.sqlproperties;

public class DBQueries {

    public static String insertQuery = "INSERT INTO User (userid,firstname,lastname,title,picture) "
            + "VALUES (%USERID%,%FIRSTNAME%,%LASTNAME%,%TITLE%,%PICTURE%);";

    public static String updateQuery = "update User set  firstname = %FIRSTNAME% , lastname = %LASTNAME% , " +
            "title=%TITLE% , picture=%PICTURE% , modifiedon = %NEWDATE% where userid =%USERID%;";
}
