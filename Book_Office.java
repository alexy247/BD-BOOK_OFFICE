import java.sql.*;
import java.io.*;
import java.util.*;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

//import tablebuild.*;


public class Book_Office
{
    public static void main (String ... args) {
        Book_Office program = new Book_Office();
        Scanner input = new Scanner(System.in);

        int choice1 = 0;
        while (choice1!=6)
        {
        System.out.print("Enter action: \n1. Select tables \n2. Add new book \n3. Sum profit from author \n4. Popular in 2018\n5. Update sold\n6. Exit\n");
        choice1 = input.nextInt();
        switch (choice1) {
            case 1: {
                System.out.print("What you would like to look through: \n1. Authors table  \n2. Books table \n3. Publishing office \n4. Exit\n");
                int choice2 = input.nextInt();
                switch (choice2) {
                        case 1:
                        {
                            program.open();
                            program.select();
                           break;
                        }
                        case 2:
                        {
                            program.open();
                            program.selectBook();
                            break;
                        }
                        case 3:
                        {
                            program.open();
                            program.selectOffice();
                            break;
                        }
                        case 4:
                        {
                            break;
                        }
                                 }
                    break;
            }
             case 2: {
                program.open();
                program.insert();
                program.insertOffice();
                break;
            }
            case 3: {
                 program.open();
                 program.sumProfit();
                 break;
                     }
            case 4: {
                program.open();
                program.yearPopular();
                break;
            }
            case 5: {
                program.open();
                program.updateSold();
                break;
            }
            case 6: {

               program.close();
            }
        }
    }
    }
    Connection co;
    int link = 0;
    int link2 = 100;
    void open()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            co = DriverManager.getConnection ("jdbc:sqlite:book_office.db");
            System.out.println ("Connected");
        }
        catch (Exception e)
        {	System.out.println (e.getMessage());
        }
    }
	
   void insert() {
       try {
           Scanner scanner = new Scanner(System.in);
           System.out.print("Enter author name: ");
           String name = scanner.nextLine();

           String query0 = "SELECT author_id FROM Author WHERE name = '" + name + "'";
           Statement statement0 = co.createStatement();
           statement0.executeUpdate(query0);
           ResultSet res = statement0.executeQuery(query0);
           if (res.next()) {
               int author_id = res.getInt("author_id");
               System.out.println("Author was found");

               System.out.print("Enter book title: ");
               String title = scanner.nextLine();
               System.out.print("Enter book release date: ");
               String release_date = scanner.nextLine();
               System.out.print("Enter book price: ");
               float price = scanner.nextFloat();
               System.out.print("Enter book circulation: ");
               int circulation = scanner.nextInt();
               System.out.print("Enter quantity of sold books: ");
               int quantity_sold = scanner.nextInt();

               Statement statement3 = co.createStatement();
               String query3 = "INSERT INTO Book(title, release_date, price, circulation, quantity_sold, author_id, office_id)" +
                       "VALUES ( '" + title + "', '" + release_date + "' , '" + price + "', '" + circulation + "', '" + quantity_sold + "','" + author_id + "', '" + link2 +  "' )";
               statement3.executeUpdate(query3);
               res.close();
           }
           else {
               String query1 =
                       "INSERT INTO Author (name)" +
                               "VALUES ('" + name + "')";
               Statement statement1 = co.createStatement();
               statement1.executeUpdate(query1);
               System.out.println("Author added");

               Statement statement2 = co.createStatement();
               String query2 = "SELECT author_id FROM Author WHERE author_id =last_insert_rowid()";
               statement2.executeUpdate(query2);
               ResultSet rs = statement2.executeQuery(query2);
               while (rs.next()) {
                   int author_id = rs.getInt("author_id");
                   System.out.println(author_id);

                   System.out.print("Enter book title: ");
                   String title = scanner.nextLine();
                   System.out.print("Enter book release date: ");
                   String release_date = scanner.nextLine();
                   System.out.print("Enter book price: ");
                   float price = scanner.nextFloat();
                   System.out.print("Enter book circulation: ");
                   int circulation = scanner.nextInt();
                   System.out.print("Enter quantity of sold books: ");
                   int quantity_sold = scanner.nextInt();

                   String query3 =
                           "INSERT INTO Book (title, release_date, price, circulation, quantity_sold, author_id, office_id )" +
                                   "VALUES ('" + title + "', '" + release_date + "' , '" + price + "', '" + circulation + "', '" + quantity_sold + "','" + author_id + "', '" + link2 + "' )";
                   Statement statement3 = co.createStatement();
                   statement3.executeUpdate(query3);

               }
               rs.close();
           }
       }

        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


void insertOffice () {

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter publishing office name: ");
            String office_name = scanner.nextLine();

            String query0 = "SELECT office_id FROM Publishing_Office WHERE office_name = '" + office_name + "'";
            Statement statement0 = co.createStatement();
            statement0.executeUpdate(query0);
            ResultSet res = statement0.executeQuery(query0);
            if (res.next()) {
               int office_id = res.getInt("office_id");
               System.out.println(office_id);
               Statement statement3 = co.createStatement();
               String query3 = "UPDATE Book set office_id = '" + office_id + "' WHERE office_id = '" + link2 + "'";
               statement3.executeUpdate(query3);
               System.out.println("Office was found");
               res.close();
            } else {
                String query1 =
                        "INSERT INTO Publishing_Office (office_name)" +
                                "VALUES ('" + office_name + "' )";
                Statement statement1 = co.createStatement();
                statement1.executeUpdate(query1);
                System.out.println("Office add");

                Statement statement2 = co.createStatement();
                String query2 = "SELECT office_id FROM Publishing_Office WHERE office_id =last_insert_rowid()";
                statement2.executeUpdate(query2);
                ResultSet rs = statement2.executeQuery(query2);
                while (rs.next()) {
                    int office_id = rs.getInt("office_id");
                    System.out.println(office_id);
                    Statement statement3 = co.createStatement();
                    String query3 = "UPDATE Book set office_id = '" + office_id + "' WHERE office_id = '" + link2 + "'";
                    statement3.executeUpdate(query3);
                    System.out.println("Office added");
                }
                rs.close();

            }

        }

        catch(Exception e){
            System.out.println(e.getMessage());

        }
}


	void select() {
            try{
               // TableBuilder tb = new TableBuilder();
                System.out.println("Authors table");
               // tb.addRow("id","name");
                //tb.addRow("---","---------------------------------");
                Statement statement = co.createStatement();
                String query = " SELECT * " +
                        " FROM Author ";
                ResultSet rs = statement.executeQuery (query);
                while (rs.next()) {
                    int author_id = rs.getInt("author_id");
                    String name = rs.getString ("name");
                   // tb.addRow(author_id,name);
                   // System.out.println(tb.toString());
                    System.out.println(author_id + "\t|"+ name);
                }
                rs.close();
                statement.close();
            }
	        catch (Exception e){
    System.out.println (e.getMessage());
    }

}

  void selectBook() {
        try{
            System.out.println("Books table");
            Statement statement1 = co.createStatement();
            String query1 = " SELECT * " +
                    " FROM Book ";
            ResultSet rs1 = statement1.executeQuery (query1);

            while (rs1.next()) {
                int book_id = rs1.getInt("book_id");
                String title = rs1.getString("title");
                String release_date = rs1.getString ("release_date");
                float price = rs1.getFloat ("price");
                int circulation = rs1.getInt ("circulation");
                int quantity_sold = rs1.getInt ("quantity_sold");
                int author_id = rs1.getInt ("author_id");
                int office_id = rs1.getInt ("office_id");


                Statement statement2 = co.createStatement();
                String query2 = "SELECT office_name FROM Publishing_Office WHERE office_id = '" + office_id + "'";
                statement2.executeUpdate(query2);
                ResultSet rs2 = statement2.executeQuery(query2);
                String office_name = rs2.getString("office_name");

                Statement statement3 = co.createStatement();
                String query3 = "SELECT name FROM Author WHERE author_id = '" + author_id + "'";
                statement3.executeUpdate(query3);
                ResultSet rs3 = statement3.executeQuery(query3);
                String name = rs3.getString("name");

                System.out.println(book_id + "\t|"+ title + "\t|"+ release_date + "\t|"+ price + "\t|"+ circulation + "\t|"+ quantity_sold + "\t|"+ name + "\t|"+ office_name);
            }
            rs1.close();
            statement1.close();
        }
        catch (Exception e){
            System.out.println (e.getMessage());
        }

    }

    void selectOffice()
    {
        try
        {
            System.out.println("Publishing office table");
            Statement statement = co.createStatement();
            String query = " SELECT * " +
                    " FROM Publishing_Office";
            ResultSet rs = statement.executeQuery (query);
            while (rs.next()) {
                int office_id = rs.getInt("office_id");
                String office_name = rs.getString ("office_name");
                System.out.println(office_id + "\t|"+ office_name);
            }
            rs.close();
            statement.close();
        }
        catch (Exception e){
            System.out.println (e.getMessage());
        }
    }

    void sumProfit() {
        try {
            int i = 0;
            int j = 4;
            int k = 0;

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter author name: ");
            String name = scanner.nextLine();

            String query00 = "SELECT author_id FROM Author WHERE name = '" + name + "' ";
            Statement statement00 = co.createStatement();
            statement00.executeUpdate(query00);
            ResultSet res00 = statement00.executeQuery(query00);
            if (res00.next()) {
                int author_id = res00.getInt("author_id");
                String query0 = "SELECT book_id FROM Book WHERE author_id = '" + author_id + "'";
                Statement statement0 = co.createStatement();
                statement0.executeUpdate(query0);
                ResultSet res0 = statement0.executeQuery(query0);
                while (res0.next()) {
                    float book_id = res0.getFloat("book_id");
                    i++;
                }
                res0.close();
                statement0.close();

                float[][] sum = new float[i][j];
               // String[] fun = new String[i];
                String query1 = "SELECT book_id FROM Book WHERE author_id = '" + author_id + "'";
                Statement statement1 = co.createStatement();
                statement1.executeUpdate(query1);
                ResultSet res1 = statement1.executeQuery(query1);
                while ((res1.next())) {
                    float book_id = res1.getFloat("book_id");
                    sum[k][0] = book_id;

                    Statement statement2 = co.createStatement();
                    String query2 = "SELECT price,circulation,quantity_sold FROM Book WHERE book_id = '" + book_id + "'";
                    statement2.executeUpdate(query2);
                    ResultSet res2 = statement2.executeQuery(query2);

                    float price = res2.getFloat("price");
                    sum[k][1] = price;

                    float circulation = res2.getFloat("circulation");
                    sum[k][2] = circulation;

                    float quantity_sold = res2.getFloat("quantity_sold");
                    sum[k][3] = quantity_sold;
                    k++;


                }
                res1.close();
                statement1.close();

                float per1 = 0;
                float per2 = 0;
                float percent = 0;
                System.out.println("\nStatistics of: " + name);
                for (int p = 0; p < i; p++) {
                    /*String query3 = "SELECT title FROM Book WHERE book_id = '" + sum[p][0] + "'";
                    Statement statement3 = co.createStatement();
                    statement3.executeUpdate(query3);
                    ResultSet res3 = statement1.executeQuery(query3);
                    String title = res3.getString("title");*/

                    per1 = per1 + sum[p][2];//circulation
                    per2 = per2 + sum[p][3];//sold
                    percent = per2/per1*100;


                    //float percent =  sum[p][3]/sum[p][2] * 100;

                    // float profit = sum[p][3] * sum[p][1];

                }
                percent = per2/per1*100;
                FileWriter nFile = new FileWriter("file1.txt");
                String text = "{\"name\" : \"" + name + "\", \"percent\":" + percent + "}";
                nFile.write(text);
                nFile.flush();//("{\"name\" : \"" + name + "\", \"percent\" :" + percent + "}");
                //System.out.println( "{\"name\" : \"" + name + "\", \"percent\" :" + percent + "}");
                System.out.println("\nPersent of sold books of " + name + " about: " + Math.round(percent) + "%");
                //System.out.println("Profit: " + profit + " rub\n");
            }
            else System.out.println("Author does not exist");
            res00.close();
            statement00.close();
        }

        catch  (Exception e) {
            System.out.println (e.getMessage());

    }
}

void yearPopular(){
        try {
            int i = 0;
            int k = 0;
            System.out.print("\nPopular authors in 2018 \n\n ");
            class TablePopular implements Serializable {

                private String title;

                private String name;

                private double profit;

                private int percent;

                public TablePopular(String t, String n, double pr, int pe) {
                    title = t;
                    name = n;
                    profit = pr;
                    percent = pe;
                }

                public String getTitle() {
                    return title;
                }

                public String getName() {
                    return name;
                }

                public double getProfit() {
                    return profit;
                }

                public int getPercent() {
                    return percent;
                }

                @Override
                public String toString() {
                    return name + "\t|  " + title + "\t|  " + profit + "k rub \t|  " + percent + "% \t|";
                }
                public String toJson() {
                    return "\"name\":\"" + name + "\", \"title\": \"" + title + "\", \"profit\": " + profit + ", \"percent\":" + percent + ",";
                }

            }
            class SortedByPercent implements Comparator<TablePopular>
            {
                public int compare(TablePopular obj1, TablePopular obj2)
                {
                    float percent1 = obj1.getPercent();
                    float percent2 = obj2.getPercent();

                    if (percent1 < percent2) {
                        return 1;
                    } else if (percent1 > percent2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }





            String query000 = "SELECT title, price, circulation, quantity_sold, author_id FROM Book WHERE release_date >= '2018-01-01';";
            Statement statement000 = co.createStatement();
            statement000.executeUpdate(query000);
            ResultSet res000 = statement000.executeQuery(query000);
            while (res000.next()) {
                k++;
            }
            res000.close();
            statement000.close();

            TablePopular[] item = new TablePopular[k];//добавить счетчик

            String query00 = "SELECT title, price, circulation, quantity_sold, author_id FROM Book WHERE release_date >= '2018-01-01';";
            Statement statement00 = co.createStatement();
            statement00.executeUpdate(query00);
            ResultSet res00 = statement00.executeQuery(query00);
            while (res00.next()) {
                String title = res00.getString("title");
                double price = res00.getDouble("price");
                double circulation = res00.getDouble("circulation");
                double quantity_sold = res00.getDouble("quantity_sold");
                int author_id = res00.getInt("author_id");

                String query0 = "SELECT name FROM Author WHERE author_id = '" + author_id + "'";
                Statement statement0 = co.createStatement();
                statement0.executeUpdate(query0);
                ResultSet res0 = statement0.executeQuery(query0);
                String name = res0.getString("name");

                double profit = price*quantity_sold/1000;
                int percent =(int)Math.floor(quantity_sold*100/circulation);

                item[i] = new TablePopular(title, name, profit, percent);
                i++;
                }
            res00.close();
            statement00.close();

            Arrays.sort(item, new SortedByPercent());

            for(TablePopular products : item)
                System.out.println(products.toString());


           /* void object toJson (String name, String title, double profit, int percent) {
                try {
                    String text = "{\"name\":\"" + name + "\", \"title\": \"" + title + "\", \"profit\": " + profit + ", \"percent\":" + percent + "}";
                    nFile.write(text);
                    nFile.flush();
                }
                catch (Exception e) {
                    System.out.println (e.getMessage());
                }
                return "\"name\":\"" + name + "\", \"title\": \"" + title + "\", \"profit\": " + profit + ", \"percent\":" + percent + "";}
*/


            FileWriter nFile = new FileWriter("file1.txt");
            nFile.write("{");
            for(TablePopular products : item) {
                String text = products.toJson() ;
                nFile.write(text + "\t\n");
               // writer.append('\n');
                //String text2 = "\n\t";
                //nFile.write(text2);
               // nFile.flush();
            }
            nFile.write("}");
            nFile.close();


            System.out.println("{");
            for(TablePopular products : item)
            { System.out.println(products.toJson());
            }
            System.out.println("}");
            //output.printf("%-20s", products.toString());


        }

        catch (Exception e){
            System.out.println (e.getMessage());
        }

}
void updateSold() {
    try {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter book title to update sold books");
        String title = scanner.nextLine();
        System.out.println("Enter quantity sold");


        String query00 = "SELECT quantity_sold FROM Book WHERE title = '" + title + "'";
        Statement statement00 = co.createStatement();
        ResultSet res00 = statement00.executeQuery(query00);
        int quantity = res00.getInt("quantity_sold");
        res00.close();
        statement00.close();


        int quantity_sold = scanner.nextInt();
        if (quantity_sold>quantity){


        String query0 = "UPDATE Book set quantity_sold = '" + quantity_sold + "' WHERE title = '" + title + "'";

        Statement statement0 = co.createStatement();
        // ResultSet res0 = statement0.executeQuery(query0);
        //if (res0.next()){
        statement0.executeUpdate(query0);
        System.out.println("UPDATED");

    //else {System.out.println("Not such book");}

    // ResultSet res0 = statement0.executeQuery(query0);
        statement0.close();}
        else  System.out.println("Err");

    //res0.close();
}
catch (Exception e)
{
    System.out.println (e.getMessage());
}
}







/*public static void CheckInt (String ai)
    {
        try
        {
            Scanner scannere = new Scanner(System.in);
            int name = scannere.nextInt();
            if (name>0) {ai = name;}

        }
        catch (Exception e)
        {
            System.out.println ("Enter integer");
        }
        return ai;
    }*/

    void close()
    {
    try
    {
    co.close();
    }
    catch (Exception e){
    System.out.println (e.getMessage());
    }
}
}

