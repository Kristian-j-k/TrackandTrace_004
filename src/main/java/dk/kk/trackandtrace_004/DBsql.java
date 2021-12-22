package dk.kk.trackandtrace_004;

import java.sql.*;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class DBsql {
    private Connection connection;
    private Statement stmt;
    private String databaseName = "";

    //Database connection
    public DBsql() {
        connection = null;
        stmt = null;
        try{
            String url = "";
            connection = DriverManager.getConnection(url);
            System.out.println("Forbundet til Database");
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

//UPDATESTATEMENT

    //prepared SQL-exequteUpdate
    public void DbsqlUpdate(String SQLUpdate){
        try{
            Statement sta = connection.createStatement();
            sta.executeUpdate(SQLUpdate);
            sta.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //create adress/costumer
    public void updateCostumer(String name,String adress,String postnr,String city,String mobile,String email){
        DbsqlUpdate("INSERT INTO costumer (name,adress,postnr,city,mobile,email)" +
                "VALUES ('"+name+"','"+adress+"','"+postnr+"','"+city+"','"+mobile+"','"+email+"')");
        System.out.println("an adress is saved");
    }

    //save new package to db
    public void createPackage(String sender,String recipient,String deliverytype){
        DbsqlUpdate("INSERT INTO package (sender,recipient,deleveryType,recieved)" +
                "VALUES ('"+sender+"','"+recipient+"','"+deliverytype+"', '0' )");
        trackAndTraceUpdate(lastPackageID(),"Afsender: "+sender+", Modtager: "+recipient+", Leveringstype: "+deliverytype);
    }

    //update TrackAndTrace statues
    public  void trackAndTraceUpdate(Integer packageID, String status){
        DbsqlUpdate("INSERT INTO trackandtrace(PackageID,status)" +
                "VALUES ('"+packageID+"','"+status+"')");
    }
    //update TrackAndTrace statues with string
    public  void trackAndTraceUpdateS(String packageID, String status){
        DbsqlUpdate("INSERT INTO trackandtrace(PackageID,status)" +
                "VALUES ('"+packageID+"','"+status+"')");
    }

    //update Package dilivered
    public void delivered(Integer packageiD,String deliveredText){
        DbsqlUpdate("UPDATE package SET recieved = '1'"+
                "WHERE packageID = "+packageiD);
        trackAndTraceUpdate(packageiD,deliveredText);
    }

//QUERY-STATEMENTS

    //search last packageID
    public Integer lastPackageID(){
        int ret = -1;
    try{
        Statement sta = connection.createStatement();
        ResultSet res = sta.executeQuery("SELECT MAX(packageID) FROM package");
        ret= res.getInt(1);
    }catch (SQLException e){        System.out.println("huston we hava a problem "+e); }
       return ret;
    }

    //search costumers
    public String[] costumerList(){
        String allCostumers = "SELECT name FROM costumer";
        String[] retAll = new String[15];

        try{
            Statement sta = connection.createStatement();
            ResultSet res = sta.executeQuery(allCostumers);
            int i = 0;
            while (res.next()){
                retAll[i] =res.getString(1);
                i++;
            }
        }catch (SQLException e){
            System.out.println("your search for costumers failed "+e);
        }
        return retAll;
    }

    //search track and trace
    public String[] costumerSearchPackages(String name){

        String allCostumers = "SELECT packageID, recieved FROM package WHERE recipient = '"+name+"'";

        String[] retAll = new String[15];

        try{
            Statement sta = connection.createStatement();
            ResultSet res = sta.executeQuery(allCostumers);
            int i = 0;
            while (res.next()){
                if (res.getInt(2)!= 1){
                retAll[i] =res.getString(1);
                i++;
                }
            }
        }catch (SQLException e){
            System.out.println("your search for costumer packages failed "+e);
        }
        return retAll;
    }

    //search track and trace
    public String[] trackAndTraceSearch(String packageID){
        String allCostumers = "SELECT * FROM trackandtrace WHERE packageID = "+packageID;
        String[] retAll = new String[10];

        int i = 0;
        try{
            Statement sta = connection.createStatement();
            ResultSet res = sta.executeQuery(allCostumers);

            while (res.next()){
                retAll[i] =res.getInt(1)+" "+res.getString(2);
                i++;
            }
        }catch (SQLException e){
            System.out.println("your search for track and trace failed "+e);
        }

        String[]      x = new String[i];
        for (int j = 0; j < i; j++) {
            x[j] = retAll[j];
        }

        return x;
    }

    //search for packages which are not delivered
    public String[] packages(){
        String packagesSearchNotDelivered = "SELECT * FROM package";

        String[] retAll = new String[10];
        int i = 0;
        try{
            Statement sta = connection.createStatement();
            ResultSet res = sta.executeQuery(packagesSearchNotDelivered);

            while (res.next()){
                String x = "Afventer levering";
                if (res.getInt(5) == 1) {x= "LEVERET"; }

                retAll[i] ="Pake nr. "+res.getInt(1)+
                        " Leveres af "+res.getString(2)+
                        " Fra "+res.getString(3)+
                        " Til "+res.getString(4)+
                        " "+x;
                i++;

            }
        }catch (SQLException e){
            System.out.println("your search for packages failed "+e);
        }
        String[] x = new String[i];
        for (int j = 0; j < i ; j++) {
            x[j] = retAll[j];
        }

        return x;
    }


//DATABASE AND TABLES UPDATE AT EVERY START

    //Install database
    public void InstallDatabase(){
        DBsql d =new DBsql();
        if (d.connection == null) {
            DbsqlUpdate("CREATE DATABASE "+databaseName);
            System.out.println("Database opretet");
        }
    }

    //create tables
    public void CreateTebles(){
      //  DBsql d = new DBsql();

        DbsqlUpdate("CREATE TABLE IF NOT EXISTS costumer(" +
                "costumerID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name STRING, " +
                "adress STRING, " +
                "postnr STRING, " +
                "city STRING, " +
                "mobile STRING, " +
                "email STRING" +
                ")");

        DbsqlUpdate("CREATE TABLE IF NOT EXISTS package(" +
                "packageID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "deleveryType STRING NOT NULL, " +
                "sender INTEGER, "+
                "recipient INTEGER, "+
                "recieved BOOLEAN, " +
                "FOREIGN KEY (sender) REFERENCES costumer(costumerID)" +
                "FOREIGN KEY (recipient) REFERENCES costumer(costumerID)" +
                ")");

        DbsqlUpdate("CREATE TABLE IF NOT EXISTS trackandtrace(" +
                "PackageID INTEGER, " +
                "status STRING, " +
                "FOREIGN KEY (packageID) REFERENCES package" +
                ")");
    }


}
