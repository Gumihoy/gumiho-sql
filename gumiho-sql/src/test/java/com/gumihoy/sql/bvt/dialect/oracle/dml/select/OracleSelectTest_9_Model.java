package com.gumihoy.sql.bvt.dialect.oracle.dml.select;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-03.
 */
public class OracleSelectTest_9_Model {

    @Test
    public void test_1() {
        String sql = "SELECT country, year, sale, csum\n" +
                "   FROM \n" +
                "   (SELECT country, year, SUM(sale) sale\n" +
                "    FROM sales_view_ref\n" +
                "    GROUP BY country, year\n" +
                "   )\n" +
                "   MODEL DIMENSION BY (country, year)\n" +
                "         MEASURES (sale, 0 csum) \n" +
                "         RULES (csum[any, any]= \n" +
                "                  SUM(sale) OVER (PARTITION BY country \n" +
                "                                  ORDER BY year \n" +
                "                                  ROWS UNBOUNDED PRECEDING) \n" +
                "                )\n" +
                "   ORDER BY country, year;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("SELECT country, year, sale, csum\n" +
                "   FROM \n" +
                "   (SELECT country, year, SUM(sale) sale\n" +
                "    FROM sales_view_ref\n" +
                "    GROUP BY country, year\n" +
                "   )\n" +
                "   MODEL DIMENSION BY (country, year)\n" +
                "         MEASURES (sale, 0 csum) \n" +
                "         RULES (csum[any, any]= \n" +
                "                  SUM(sale) OVER (PARTITION BY country \n" +
                "                                  ORDER BY year \n" +
                "                                  ROWS UNBOUNDED PRECEDING) \n" +
                "                )\n" +
                "   ORDER BY country, year;", format);
    }



}
