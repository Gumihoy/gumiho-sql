package com.gumihoy.sql.basic.ast.expr.table.partitionset;

import com.gumihoy.sql.basic.ast.expr.table.partition.SQLTablePartitioning;

/**
 * PARTITIONSET BY
 * <p>
 * https://dev.mysql.com/doc/refman/5.7/en/create-table.html
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent onCondition 2018/4/10.
 */
public interface ISQLPartitionsetBy extends SQLTablePartitioning {

    @Override
    ISQLPartitionsetBy clone();

}
