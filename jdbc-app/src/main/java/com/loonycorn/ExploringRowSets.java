package com.loonycorn;

import javax.sql.RowSet; // interface RowSet
import javax.sql.rowset.FilteredRowSet; // interface FilteredRowSet
import javax.sql.rowset.RowSetProvider; // class RowSetProvider
import java.sql.Connection; // interface Connection
import java.sql.SQLException; // class SQLException

public class ExploringRowSets {

    public static void displayRow(String label, RowSet rowSet) throws SQLException {

        String fName = rowSet.getString("first_name");
        String lName = rowSet.getString("last_name");
        double hourlyRate = rowSet.getDouble("hourly_rate");
        boolean isFT = rowSet.getBoolean("is_fulltime");

        String stdData = "\n%s: %s | %s | %.2f | %s \n";
        System.out.format(stdData, label, fName, lName, hourlyRate, isFT);
    }

    public static void main(String[] args) {

        try (Connection conn = DBUtils.getMysqlConnection("DeliveryService")) {

            FilteredRowSet partnersRS = RowSetProvider.newFactory().createFilteredRowSet();

            partnersRS.setCommand("select first_name, last_name, hourly_rate, is_fulltime "
                    + "from delpartners");
            partnersRS.execute(conn);

            DeliveryPartnerFilter zeroToTwentyFilter
                    = new DeliveryPartnerFilter(0, 20, 3);

            partnersRS.setFilter(zeroToTwentyFilter);

            int rowNum = 1;

            while (partnersRS.next()) {

                displayRow("Row #" + rowNum, partnersRS);
                //Row #1: Adam | Bell | 18.50 | true
                //
                //Row #2: Pam | Cruz | 19.00 | true
                //
                //Row #3: Marie | Woods | 19.00 | true
                //
                //Row #4: Pablo | Hernandez | 20.00 | false
                rowNum++;
            }

            partnersRS.close();

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

//        try (Connection conn = DBUtils.getMysqlConnection("DeliveryService")) {
//
//            FilteredRowSet partnersRS = RowSetProvider.newFactory().createFilteredRowSet();
//
//            partnersRS.setCommand("select first_name, last_name, hourly_rate, is_fulltime "
//                    + "from delpartners");
//            partnersRS.execute(conn);
//
//            int rowNum = 1;
//
//            while (partnersRS.next()) {
//
//                displayRow("Row #" + rowNum, partnersRS);
//                //Row #1: Adam | Bell | 18.50 | true
//                //
//                //Row #2: Eric | Jones | 22.75 | false
//                //
//                //Row #3: Pam | Cruz | 19.00 | true
//                //
//                //Row #4: Stacey | Shields | 21.00 | false
//                //
//                //Row #5: Marie | Woods | 19.00 | true
//                //
//                //Row #6: Pablo | Hernandez | 20.00 | false
//                //
//                //Row #7: Kylie | Kass | 22.00 | false
//                //
//                //Row #8: Brian | Walters | 22.00 | false
//                rowNum++;
//            }
//
//            partnersRS.close();
//
//        }
//        catch (SQLException ex) {
//            ex.printStackTrace();
//        }

    }
}