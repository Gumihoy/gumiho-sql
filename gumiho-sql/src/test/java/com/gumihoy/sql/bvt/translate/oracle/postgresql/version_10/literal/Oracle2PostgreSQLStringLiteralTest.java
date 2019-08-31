package com.gumihoy.sql.bvt.translate.oracle.postgresql.version_10.literal;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.enums.DBVersion;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.translate.result.SQLTransformResult;
import com.gumihoy.sql.util.SQLTransformUtils;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-06-28.
 */
public class Oracle2PostgreSQLStringLiteralTest {

    @Test
    public void test_0() {
        String sql = "select 'a string'";
        SQLTransformConfig config = new SQLTransformConfig();
        config.targetDBVersion = DBVersion.PostgreSQL.VERSION_9_6;
        SQLTransformResult result = SQLTransformUtils.oracleToPostgreSQL(sql, config);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(result.targetSql);
        Assert.assertEquals("SELECT 'a string'", result.targetSql);
    }

    @Test
    public void test_1() {
        String sql = "SELECT 'another string'";
        SQLTransformConfig config = new SQLTransformConfig();
        config.targetDBVersion = DBVersion.PostgreSQL.VERSION_9_6;
        SQLTransformResult result = SQLTransformUtils.oracleToPostgreSQL(sql, config);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(result.targetSql);
        Assert.assertEquals("SELECT 'another string'", result.targetSql);
    }

    @Test
    public void test_2() {
        String sql = "select 'a' ' ' 'string'";
        SQLTransformConfig config = new SQLTransformConfig();
        config.targetDBVersion = DBVersion.PostgreSQL.VERSION_9_6;
        SQLTransformResult result = SQLTransformUtils.oracleToPostgreSQL(sql, config);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(result.targetSql);
        Assert.assertEquals("SELECT 'a string'", result.targetSql);
    }

    @Test
    public void test_3() {
        String sql = "SELECT 'hello', '\"hello\"', '\"\"hello\"\"', 'hel''lo', '\\'hello';";
        SQLTransformConfig config = new SQLTransformConfig();
        config.targetDBVersion = DBVersion.PostgreSQL.VERSION_9_6;
        SQLTransformResult result = SQLTransformUtils.oracleToPostgreSQL(sql, config);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(result.targetSql);
        Assert.assertEquals("SELECT 'hello', '\"hello\"', '\"\"hello\"\"', 'hel\\'lo', '\\'hello';", result.targetSql);
    }


    @Test
    public void test_4() {
        String sql = "SELECT 'This\\nIs\\nFour\\nLines';";
        SQLTransformConfig config = new SQLTransformConfig();
        config.targetDBVersion = DBVersion.PostgreSQL.VERSION_9_6;
        SQLTransformResult result = SQLTransformUtils.oracleToPostgreSQL(sql, config);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(result.targetSql);
        Assert.assertEquals("SELECT 'This\\nIs\\nFour\\nLines';", result.targetSql);
    }

    @Test
    public void test_5() {
        String sql = "SELECT 'disappearing\\ backslash';";
        SQLTransformConfig config = new SQLTransformConfig();
        config.targetDBVersion = DBVersion.PostgreSQL.VERSION_9_6;
        SQLTransformResult result = SQLTransformUtils.oracleToPostgreSQL(sql, config);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(result.targetSql);
        Assert.assertEquals("SELECT 'disappearing\\ backslash';", result.targetSql);
    }

}
