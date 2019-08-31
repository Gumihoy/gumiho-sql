package com.gumihoy.sql.bvt.dialect.oracle.dml.delete;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-05.
 */
public class OracleDeleteTest_3_Partition {

    @Test
    public void test_0() {
        String sql = "DELETE FROM employees\n" +
                "   WHERE job_id = 'SA_REP' \n" +
                "   AND hire_date + TO_YMINTERVAL('01-00') < SYSDATE \n" +
                "   RETURNING salary INTO :bnd1;";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("DELETE FROM employees\n" +
                "   WHERE job_id = 'SA_REP' \n" +
                "   AND hire_date + TO_YMINTERVAL('01-00') < SYSDATE \n" +
                "   RETURNING salary INTO :bnd1;", format);
    }


}
