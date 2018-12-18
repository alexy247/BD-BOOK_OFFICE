import java.sql.*;
import java.util.Scanner;


public class Program
{
    public static void main (String ... args) {
        Program program = new Program();
        Scanner input = new Scanner(System.in);
        System.out.print("Enter action: \n1. Select tables \n2. Add new book \n3. Sum profit from author \n4. Exit\n");
        int choice = input.nextInt();
        switch (choice) {
            case 1: {
                program.open();
                program.select();
                program.selectBook();
                program.close();
                break;
                     }
             case 2: {
                program.open();
                program.insert();
                program.insertBook();
                program.insertOffice();
                program.close();
                break;
            }
            case 3: {
                 program.open();
                 program.sumProfit();
                 program.close();
                     }
            case 4: {
               program.close();
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
            co = DriverManager.getConnection ("jdbc:sqlite:books_officeTest.db");
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
               System.out.println(author_id);
               Statement statement3 = co.createStatement();
               String query3 = "INSERT INTO Book_Author(author_id, book_id)" +
                       "VALUES (  '" + author_id + "', '" + link + "' )";
               statement3.executeUpdate(query3);
               System.out.println("Author added");
               res.close();
           }
           else {
               String query1 =
                       "INSERT INTO Author (name)" +
                               "VALUES ('" + name + "')";
               Statement statement1 = co.createStatement();
               statement1.executeUpdate(query1);

               Statement statement2 = co.createStatement();
               String query2 = "SELECT author_id FROM Author WHERE author_id =last_insert_rowid()";
               statement2.executeUpdate(query2);
               ResultSet rs = statement2.executeQuery(query2);
               while (rs.next()) {
                   int author_id = rs.getInt("author_id");
                   System.out.println(author_id);
                   Statement statement3 = co.createStatement();
                   String query3 = "INSERT INTO Book_Author(author_id, book_id)" +
                           "VALUES (  '" + author_id + "', '" + link + "' )";
                   statement3.executeUpdate(query3);
                   System.out.println("Author added");
               }
               rs.close();
           }
       }

        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

   void insertBook () {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter book title: ");
            String title = scanner.nextLine();
            System.out.print("Enter book release date: ");
            String release_date = scanner.nextLine();
            System.out.print("Enter book price: ");
            String price = scanner.nextLine();
            System.out.print("Enter book circulation: ");
            String circulation = scanner.nextLine();
            System.out.print("Enter quantity of sold books: ");
            String quantity_sold = scanner.nextLine();
            String query1 =
                    "INSERT INTO Book (title, release_date, price, circulation, quantity_sold, office_id )" +
                            "VALUES ('" + title + "', '" + release_date + "' , '" + price + "', '" + circulation + "', '" + quantity_sold + "', '" + link2 + "' )";

            Statement statement1 = co.createStatement();
            statement1.executeUpdate(query1);

            Statement statement2 = co.createStatement();
            String query2 = "SELECT book_id FROM Book WHERE book_id =last_insert_rowid()";
            statement2.executeUpdate(query2);
            ResultSet rs = statement2.executeQuery (query2);
            while (rs.next()) {
                int book_id = rs.getInt("book_id");
                System.out.println(book_id);
                Statement statement3 = co.createStatement();
                String query3 = "UPDATE Book_Author set book_id = '" + book_id + "' WHERE book_id = '" + link + "'";
                statement3.executeUpdate(query3);
                System.out.println("Book added");
            }
            rs.close();
        }


        catch(Exception e){
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
               System.out.println("Author added");
               res.close();
            } else {
                String query1 =
                        "INSERT INTO Publishing_Office (office_name)" +
                                "VALUES ('" + office_name + "' )";

                Statement statement1 = co.createStatement();
                statement1.executeUpdate(query1);

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
                System.out.println("Authors table");
                Statement statement = co.createStatement();
                String query = " SELECT * " +
                        " FROM Author ";
                ResultSet rs = statement.executeQuery (query);
                while (rs.next()) {
                    int author_id = rs.getInt("author_id");
                    String name = rs.getString ("name");
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
                String price = rs1.getString ("price");
                String circulation = rs1.getString ("circulation");
                String quantity_sold = rs1.getString ("quantity_sold");
                String office_id = rs1.getString ("office_id");
                Statement statement2 = co.createStatement();
                String query2 = "SELECT office_name FROM Publishing_Office WHERE office_id = '" + office_id + "'";
                statement2.executeUpdate(query2);
                ResultSet rs2 = statement2.executeQuery(query2);
                String office_name = rs2.getString("office_name");
                rs2.close();
                System.out.println(book_id + "\t|"+ title + "\t|"+ release_date + "\t|"+ price + "\t|"+ circulation + "\t|"+ quantity_sold + "\t|"+ office_name);
            }
            rs1.close();
            statement1.close();
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
            int author_id = res00.getInt("author_id");

            String query0 = "SELECT book_id FROM Book_Author WHERE author_id = '" + author_id + "'";
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

            String query1 = "SELECT book_id FROM Book_Author WHERE author_id = '" + author_id + "'";
            Statement statement1 = co.createStatement();
            statement1.executeUpdate(query1);
            ResultSet res1 = statement1.executeQuery(query1);
            while ((res1.next())) {
                float book_id = res1.getFloat("book_id");
                sum[k][0]= book_id;

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


            System.out.println("\nStatistics of: " + name );
            for (int p=0;p<i;p++) {
                String query3 = "SELECT title FROM Book WHERE book_id = '" + sum[p][0] + "'";
                Statement statement3 = co.createStatement();
                statement3.executeUpdate(query3);
                ResultSet res3 = statement1.executeQuery(query3);
                String title = res3.getString("title");

                float percent =  sum[p][3]/sum[p][2] * 100;
                System.out.println("\nPersent of sold books (named " + title + ") about: " + Math.round(percent)+ "%");
                float profit = sum[p][3] * sum[p][1];
                System.out.println("Profit: " + profit + " rub\n");
            }
        }

        catch  (Exception e) {
            System.out.println (e.getMessage());

    }
}
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