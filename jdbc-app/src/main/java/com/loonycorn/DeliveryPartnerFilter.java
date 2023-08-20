package com.loonycorn;

import java.sql.SQLException; // class SQLException
import javax.sql.RowSet; // interface RowSet
import javax.sql.rowset.FilteredRowSet; // interface FilteredRowSet
import javax.sql.rowset.Predicate; // interface Predicate

public class DeliveryPartnerFilter implements Predicate {

    private int lowRate;
    private int highRate;
    private String colName = null;
    private int colNumber = -1;

    public DeliveryPartnerFilter(int lo, int hi, int colNumber) {
        this.lowRate = lo;
        this.highRate = hi;
        this.colNumber = colNumber;
    }

    public DeliveryPartnerFilter(int lo, int hi, String colName) {
        this.lowRate = lo;
        this.highRate = hi;
        this.colName = colName;
    }

    @Override
    public boolean evaluate(RowSet rs) {

        if (rs == null) {

            return false;
        }

        FilteredRowSet frs = (FilteredRowSet) rs;
        boolean evaluation = false;

        try {

            double columnValue = frs.getDouble(this.colNumber);

            if ((columnValue >= this.lowRate) && (columnValue <= this.highRate)) {

                evaluation = true;

            }
        } catch (SQLException e) {

            return false;
        }

        return evaluation;
    }

    @Override
    public boolean evaluate(Object value, int columnNumber) throws SQLException {

        boolean evalution = true;

        if (colNumber == columnNumber) {

            double columnValue = ((Double) value).doubleValue();

            if ((columnValue >= this.lowRate) && (columnValue <= this.highRate)) {

                evalution = true;

            } else {

                evalution = false;

            }
        }

        return evalution;
    }

    @Override
    public boolean evaluate(Object value, String columnName) throws SQLException {

        boolean evaluation = true;

        if (columnName.equalsIgnoreCase(this.colName)) {

            double columnValue = ((Double) value).doubleValue();

            if ((columnValue >= this.lowRate) && (columnValue <= this.highRate)) {

                evaluation = true;

            } else {

                evaluation = false;

            }
        }

        return evaluation;
    }
}
