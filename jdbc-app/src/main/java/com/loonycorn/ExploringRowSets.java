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

            DeliveryPartnerFilter customFilter
                    = new DeliveryPartnerFilter(19, 21, false, 3, 4);

            partnersRS.setFilter(customFilter);

            int rowNum = 1;

            while (partnersRS.next()) {

                displayRow("Row #" + rowNum, partnersRS);
                //Row #1: Stacey | Shields | 21.00 | false
                //
                //Row #2: Pablo | Hernandez | 20.00 | false
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
//            DeliveryPartnerFilter customFilter
//                    = new DeliveryPartnerFilter(19, 21, true, 3, 4);
//
//            partnersRS.setFilter(customFilter);
//
//            int rowNum = 1;
//
//            while (partnersRS.next()) {
//
//                displayRow("Row #" + rowNum, partnersRS);
//                //Row #1: Pam | Cruz | 19.00 | true
//                //
//                //Row #2: Marie | Woods | 19.00 | true
//                rowNum++;
//            }
//
//            partnersRS.close();
//
//        }
//        catch (SQLException ex) {
//            ex.printStackTrace();
//        }

//        try (Connection conn = DBUtils.getMysqlConnection("DeliveryService")) {
//
//            FilteredRowSet partnersRS = RowSetProvider.newFactory().createFilteredRowSet();
//
//            partnersRS.setCommand("select first_name, last_name, hourly_rate, is_fulltime "
//                    + "from delpartners");
//            partnersRS.execute(conn);
//
//            DeliveryPartnerFilter rateRangeFilter
//                    = new DeliveryPartnerFilter(19, 21, 3);
//
//            partnersRS.setFilter(rateRangeFilter);
//
//            int rowNum = 1;
//
//            while (partnersRS.next()) {
//
//                displayRow("Row #" + rowNum, partnersRS);
//                //Row #1: Pam | Cruz | 19.00 | true
//                //
//                //Row #2: Stacey | Shields | 21.00 | false
//                //
//                //Row #3: Marie | Woods | 19.00 | true
//                //
//                //Row #4: Pablo | Hernandez | 20.00 | false
//                rowNum++;
//            }
//
//            partnersRS.close();
//
//        }
//        catch (SQLException ex) {
//            ex.printStackTrace();
//        }

//        try (Connection conn = DBUtils.getMysqlConnection("DeliveryService")) {
//
//            FilteredRowSet partnersRS = RowSetProvider.newFactory().createFilteredRowSet();
//
//            partnersRS.setCommand("select first_name, last_name, hourly_rate, is_fulltime "
//                    + "from delpartners");
//            partnersRS.execute(conn);
//
//            DeliveryPartnerFilter moreThanTwentyFilter
//                    = new DeliveryPartnerFilter(20, 500, 3);
//
//            partnersRS.setFilter(moreThanTwentyFilter);
//
//            int rowNum = 1;
//
//            while (partnersRS.next()) {
//
//                displayRow("Row #" + rowNum, partnersRS);
//                //Row #1: Eric | Jones | 22.75 | false
//                //
//                //Row #2: Stacey | Shields | 21.00 | false
//                //
//                //Row #3: Pablo | Hernandez | 20.00 | false
//                //
//                //Row #4: Kylie | Kass | 22.00 | false
//                //
//                //Row #5: Brian | Walters | 22.00 | false
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