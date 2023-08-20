package com.loonycorn;

import javax.sql.rowset.CachedRowSet; // interface CachedRowSet
import javax.sql.rowset.JoinRowSet; // interface JoinRowSet
import javax.sql.rowset.RowSetProvider; // class RowSetProvider
import java.sql.Connection; // interface Connection
import java.sql.SQLException; // class SQLException

public class ExploringRowSets {

    public static void main(String[] args) {


        try (Connection conn = DBUtils.getMysqlConnection("DeliveryService")) {

            CachedRowSet vehicleRS = DBUtils.getCachedRowSet("");
            vehicleRS.setCommand("select * from delvehicles");
            vehicleRS.execute(conn);

            CachedRowSet deliveriesRS = DBUtils.getCachedRowSet("");
            deliveriesRS.setCommand("select * from deliveries");
            deliveriesRS.execute(conn);

            JoinRowSet joinRS = RowSetProvider.newFactory().createJoinRowSet();

            // left outer join
            joinRS.setJoinType(JoinRowSet.LEFT_OUTER_JOIN);
            //java.sql.SQLException: This type of join is not supported
            //	at java.sql.rowset/com.sun.rowset.JoinRowSetImpl.setJoinType(JoinRowSetImpl.java:554)
            //	at com.loonycorn.ExploringRowSets.main(ExploringRowSets.java:25)

            joinRS.addRowSet(vehicleRS, "vid");
            joinRS.addRowSet(deliveriesRS, "vid");

            int rowNum = 1;

            while (joinRS.next()) {

                String color = joinRS.getString("color");
                String vType = joinRS.getString("vehicle_type");
                String destination = joinRS.getString("destination");

                String stdData = "\nRow #%d: %s | %s | %s";
                System.out.format(stdData, rowNum, color, vType, destination);
                rowNum++;
            }

            joinRS.close();
            vehicleRS.close();
            deliveriesRS.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

//        try (Connection conn = DBUtils.getMysqlConnection("DeliveryService")) {
//
//            CachedRowSet vehicleRS = DBUtils.getCachedRowSet("");
//            vehicleRS.setCommand("select * from delvehicles");
//            vehicleRS.execute(conn);
//
//            CachedRowSet deliveriesRS = DBUtils.getCachedRowSet("");
//            deliveriesRS.setCommand("select * from deliveries");
//            deliveriesRS.execute(conn);
//
//            JoinRowSet joinRS = RowSetProvider.newFactory().createJoinRowSet();
//
//            // inner join by default
//            joinRS.addRowSet(vehicleRS, "vid");
//            joinRS.addRowSet(deliveriesRS, "vid");
//
//            int rowNum = 1;
//
//            while (joinRS.next()) {
//
//                String color = joinRS.getString("color");
//                String vType = joinRS.getString("vehicle_type");
//                String destination = joinRS.getString("destination");
//
//                String stdData = "\nRow #%d: %s | %s | %s";
//                System.out.format(stdData, rowNum, color, vType, destination);
//                //Row #1: White | Truck | Harlem
//                //Row #2: Grey | Truck | Financial District
//                //Row #3: Blue | Van | Jersey City
//                //Row #4: Red | Pickup | Astoria
//                //Row #5: Red | Pickup | South Slope
//                rowNum++;
//            }
//
//            joinRS.close();
//            vehicleRS.close();
//            deliveriesRS.close();
//        }
//        catch (SQLException ex) {
//            ex.printStackTrace();
//        }

    }
}