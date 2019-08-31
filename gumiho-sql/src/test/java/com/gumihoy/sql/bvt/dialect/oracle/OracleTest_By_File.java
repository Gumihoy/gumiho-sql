package com.gumihoy.sql.bvt.dialect.oracle;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author kent on 2019-07-09.
 */
public class OracleTest_By_File {

    @Test
    public void test_0() throws Exception {
        InputStream inputStream = this.getClass().getResourceAsStream("/package/create/create_package_0.sql");
        String sql = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        System.out.println(sql);
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals(sql, format);
    }


}
