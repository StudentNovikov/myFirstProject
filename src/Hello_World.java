import com.sun.org.apache.xpath.internal.SourceTree;

import java.sql.*;
import java.util.Scanner;
import java.util.Properties;


public class Hello_World
{
    public static void main(String args[]) throws SQLException {

        //0. Connecting to DataBase
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;
        }
             Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "ALEX","123qwe456asd");
                    } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

      //1. Output start menu
        String exit = "no";
        while (exit.equals("no")){

        System.out.println("-------------------------");
        System.out.println("Choose one of this variants:");
        System.out.println("1. Show all guitars");
        System.out.println("2. Show guitar detail by id");
        System.out.println("3. Show guitars with specific condition");
        System.out.println("5. Exit");
        System.out.println("Enter your number:");

        Scanner userInput = new Scanner(System.in);
        int input = userInput.nextInt();

        //2. Input analyzing
        if (input == 1){

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from guitar");

            while (resultSet.next()){
                System.out.println(resultSet.getString("id")+" | "+resultSet.getString("name")+" | "+resultSet.getString("price")+"$");
            }
            statement.close();
        }

        if (input == 2 ){

            System.out.println("Input Guitar id you want to see");
            int id = userInput.nextInt();
            Statement statement = connection.createStatement();
            String myQuery ="SELECT Guitar.id, Guitar.name,  Guitar.description, Guitar.price, Guitar.color, Guitar.stringType,Guitar.photo,GuitarBrand.name as Brand, GuitarType.name as Type\n" +
                    "FROM GUITAR\n" +
                    "INNER JOIN GUITARBRAND\n" +
                    "ON GUITAR.GUITARBRANDID = GUITARBRAND.ID\n" +
                    "INNER JOIN GUITARTYPE\n" +
                    "ON GUITAR.GUITARTYPEID = GUITARTYPE.ID\n" +
                    "WHERE Guitar.id = "+id;
            ResultSet resultSet = statement.executeQuery(myQuery);

            while (resultSet.next()){
                System.out.println(resultSet.getString("id")+" | "+resultSet.getString("name")+" | "+resultSet.getString("description")+" | "+resultSet.getString("price")+"$ | "+ resultSet.getString("color")+" | "+resultSet.getString("stringtype")+" | "+resultSet.getString("photo")+" | "+resultSet.getString("brand")+" | "+resultSet.getString("type")+" | ");
            }
            statement.close();
        }

        if (input == 3){

            System.out.println("Press 1 to choose string type");
            System.out.println("Press 2 to choose the price");
            System.out.println("Press 3 to choose the brand ");
            int id = userInput.nextInt();

            if (id == 1 ){
                System.out.println("Press 1 to choose metal strings");
                System.out.println("Press 2 to choose nylon strings");
                int stringType = userInput.nextInt();
                Statement statement = connection.createStatement();

                if(stringType==1) {
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM GUITAR\n" +
                            "WHERE STRINGTYPE LIKE 'metal%'");
                    while (resultSet.next()) {
                        System.out.println(resultSet.getString("id") + " | " + resultSet.getString("name") + " | " + resultSet.getString("price") + "$ | " + resultSet.getString("stringtype"));
                    }
                    statement.close();
                }

                if(stringType==2) {
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM GUITAR\n" +
                            "WHERE STRINGTYPE LIKE 'nylon%'");
                    while (resultSet.next()) {
                        System.out.println(resultSet.getString("id") + " | " + resultSet.getString("name") + " | " + resultSet.getString("price") + "$ | " + resultSet.getString("stringtype"));
                    }
                    statement.close();
                }
            }

            if (id == 2){

                System.out.println("Choose the lowest price");
                double bottomPrice = userInput.nextDouble();

                System.out.println("Choose the highest price");
                double topPrice = userInput.nextDouble();

                Statement statement = connection.createStatement();
                String myQuery = "SELECT * FROM GUITAR\n" +
                        "WHERE PRICE>="+bottomPrice+" AND PRICE<="+topPrice+" ORDER BY PRICE";
                ResultSet resultSet = statement.executeQuery(myQuery);

                while (resultSet.next()){
                    System.out.println(resultSet.getString("id")+" | "+resultSet.getString("name")+" | "+resultSet.getString("price")+"$ | "+resultSet.getString("color"));
                }
                statement.close();
            }

            if(id == 3){
                System.out.println("Press 1 - FENDER, 2 - GIBSON, 3 - IBANEZ, 4- MATON, 5- LAKEWOOD");
                int brandId = userInput.nextInt();

                String myQuery = "SELECT Guitar.id, Guitar.name, Guitar.price, Guitar.color, GuitarBrand.name as Brand\n" +
                        "FROM GUITAR\n" +
                        "INNER JOIN GUITARBRAND\n" +
                        "ON GUITAR.GUITARBRANDID = GUITARBRAND.ID\n" +
                        "WHERE GUITAR.GUITARBRANDID="+brandId;
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(myQuery);

                while (resultSet.next()){
                    System.out.println(resultSet.getString("id")+" | "+resultSet.getString("name")+" | "+resultSet.getString("price")+"$ | "+ resultSet.getString("color")+" | "+resultSet.getString("brand"));
                }
                statement.close();
            }
        }

        if (input == 5){

            System.out.println("Come back soon");
            exit = "yes";
        }
        }
    }
}



