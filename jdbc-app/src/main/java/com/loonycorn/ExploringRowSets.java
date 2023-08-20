package com.loonycorn;

import javax.sql.rowset.CachedRowSet; // interface CachedRowSet
import javax.sql.rowset.JoinRowSet; // interface JoinRowSet
import javax.sql.rowset.RowSetProvider; // class RowSetProvider
import java.sql.Connection; // interface Connection
import java.sql.SQLException; // class SQLException

public class ExploringRowSets {

    public static void main(String[] args) {

        try (Connection conn = DBUtils.getMysqlConnection("DeliveryService")) {

            CachedRowSet partnersRS = DBUtils.getCachedRowSet("");
            partnersRS.setCommand("select * from delpartners");
            partnersRS.execute(conn);

            CachedRowSet deliveriesRS = DBUtils.getCachedRowSet("");
            deliveriesRS.setCommand("select * from deliveries");
            deliveriesRS.execute(conn);

            JoinRowSet joinRS = RowSetProvider.newFactory().createJoinRowSet();

            joinRS.addRowSet(partnersRS, "pid");
            joinRS.addRowSet(deliveriesRS, "pid");

            int rowNum = 1;

            while (joinRS.next()) {

                String fName = joinRS.getString("first_name");
                boolean isFT = joinRS.getBoolean("is_fulltime");
                String destination = joinRS.getString("destination");

                String stdData = "\nRow #%d: %s | %s | %s";
                System.out.format(stdData, rowNum, fName, isFT, destination);
                //Row #1: Kylie | false | Financial District
                //Row #2: Pam | true | Jersey City
                //Row #3: Eric | false | Astoria
                //Row #4: Eric | false | Harlem
                //Row #5: Adam | true | South Slope
                rowNum++;
            }

            joinRS.close();
            partnersRS.close();
            deliveriesRS.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}