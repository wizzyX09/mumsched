package edu.mum.mumsched.util;

import edu.mum.mumsched.model.BlockMonths;

import java.util.ArrayList;
import java.util.List;

public class MonthUtil {
    public static List<BlockMonths> months;

    public static List<BlockMonths> getMonths(){
        months = new ArrayList<>();
        months.add(BlockMonths.JANUARY);
        months.add(BlockMonths.FEBRUARY);
        months.add(BlockMonths.MARCH);
        months.add(BlockMonths.APRIL);
        months.add(BlockMonths.MAY);
        months.add(BlockMonths.JUNE);
        months.add(BlockMonths.JULY);
        months.add(BlockMonths.AUGUST);
        months.add(BlockMonths.SEPTEMBER);
        months.add(BlockMonths.OCTOBER);
        months.add(BlockMonths.NOVEMBER);
        months.add(BlockMonths.DECEMBER);
        return months;
    }

    public static void setMonths(List<BlockMonths> months) {
        MonthUtil.months = months;
    }
}
