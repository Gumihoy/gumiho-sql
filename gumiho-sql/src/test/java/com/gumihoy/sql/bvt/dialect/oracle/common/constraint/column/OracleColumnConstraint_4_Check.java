package com.gumihoy.sql.bvt.dialect.oracle.common.constraint.column;

import com.gumihoy.sql.enums.DBType;
import com.gumihoy.sql.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kent on 2019-07-09.
 */
public class OracleColumnConstraint_4_Check {

    @Test
    public void test_0() {
        String sql = "CREATE TABLE divisions  \n" +
                "   (div_no    NUMBER  CONSTRAINT check_divno\n" +
                "              CHECK (div_no BETWEEN 10 AND 99) \n" +
                "              DISABLE, \n" +
                "    div_name  VARCHAR2(9)  CONSTRAINT check_divname\n" +
                "              CHECK (div_name = UPPER(div_name)) \n" +
                "              DISABLE, \n" +
                "    office    VARCHAR2(10)  CONSTRAINT check_office\n" +
                "              CHECK (office IN ('DALLAS','BOSTON',\n" +
                "              'PARIS','TOKYO')) \n" +
                "              DISABLE); \n";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE divisions  \n" +
                "   (div_no    NUMBER  CONSTRAINT check_divno\n" +
                "              CHECK (div_no BETWEEN 10 AND 99) \n" +
                "              DISABLE, \n" +
                "    div_name  VARCHAR2(9)  CONSTRAINT check_divname\n" +
                "              CHECK (div_name = UPPER(div_name)) \n" +
                "              DISABLE, \n" +
                "    office    VARCHAR2(10)  CONSTRAINT check_office\n" +
                "              CHECK (office IN ('DALLAS','BOSTON',\n" +
                "              'PARIS','TOKYO')) \n" +
                "              DISABLE); \n", format);
    }

    @Test
    public void test_1() {
        String sql = "CREATE TABLE dept_20 \n" +
                "   (employee_id     NUMBER(4) PRIMARY KEY, \n" +
                "    last_name       VARCHAR2(10), \n" +
                "    job_id          VARCHAR2(9), \n" +
                "    manager_id      NUMBER(4) CONSTRAINT fk_mgr\n" +
                "                    REFERENCES employees ON DELETE SET NULL, \n" +
                "    hire_date       DATE, \n" +
                "    salary          NUMBER(7,2), \n" +
                "    commission_pct  NUMBER(7,2), \n" +
                "    department_id   NUMBER(2)   CONSTRAINT fk_deptno \n" +
                "                    REFERENCES departments(department_id) \n" +
                "                    ON DELETE CASCADE );  ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE dept_20 \n" +
                "   (employee_id     NUMBER(4) PRIMARY KEY, \n" +
                "    last_name       VARCHAR2(10), \n" +
                "    job_id          VARCHAR2(9), \n" +
                "    manager_id      NUMBER(4) CONSTRAINT fk_mgr\n" +
                "                    REFERENCES employees ON DELETE SET NULL, \n" +
                "    hire_date       DATE, \n" +
                "    salary          NUMBER(7,2), \n" +
                "    commission_pct  NUMBER(7,2), \n" +
                "    department_id   NUMBER(2)   CONSTRAINT fk_deptno \n" +
                "                    REFERENCES departments(department_id) \n" +
                "                    ON DELETE CASCADE ); ", format);
    }

    @Test
    public void test_2() {
        String sql = "CREATE TABLE order_detail \n" +
                "  (CONSTRAINT pk_od PRIMARY KEY (order_id, part_no), \n" +
                "   order_id    NUMBER \n" +
                "      CONSTRAINT fk_oid \n" +
                "         REFERENCES oe.orders(order_id), \n" +
                "   part_no     NUMBER \n" +
                "      CONSTRAINT fk_pno \n" +
                "         REFERENCES oe.product_information(product_id), \n" +
                "   quantity    NUMBER \n" +
                "      CONSTRAINT nn_qty NOT NULL \n" +
                "      CONSTRAINT check_qty CHECK (quantity > 0), \n" +
                "   cost        NUMBER \n" +
                "      CONSTRAINT check_cost CHECK (cost > 0) ); ";
        String format = SQLUtils.format(sql, DBType.Oracle);
        System.out.println(sql);
        System.out.println("----------------");
        System.out.println(format);
        Assert.assertEquals("CREATE TABLE order_detail \n" +
                "  (CONSTRAINT pk_od PRIMARY KEY (order_id, part_no), \n" +
                "   order_id    NUMBER \n" +
                "      CONSTRAINT fk_oid \n" +
                "         REFERENCES oe.orders(order_id), \n" +
                "   part_no     NUMBER \n" +
                "      CONSTRAINT fk_pno \n" +
                "         REFERENCES oe.product_information(product_id), \n" +
                "   quantity    NUMBER \n" +
                "      CONSTRAINT nn_qty NOT NULL \n" +
                "      CONSTRAINT check_qty CHECK (quantity > 0), \n" +
                "   cost        NUMBER \n" +
                "      CONSTRAINT check_cost CHECK (cost > 0) );", format);
    }
}
