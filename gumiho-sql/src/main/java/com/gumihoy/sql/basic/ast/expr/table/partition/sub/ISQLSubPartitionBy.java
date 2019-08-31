package com.gumihoy.sql.basic.ast.expr.table.partition.sub;

import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.table.partition.SQLTablePartitioning;

import java.util.List;

/**
 * Partition By
 * <p>
 * https://dev.mysql.com/doc/refman/5.7/en/create-table.html
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/sqlrf/CREATE-TABLE.html
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/CREATE-TABLE.html#GUID-F9CE0CC3-13AE-4744-A43C-EAC7A71AAAB6
 *
 * @author kent onCondition 2018/4/10.
 */
public interface ISQLSubPartitionBy extends SQLTablePartitioning {

    @Override
    ISQLSubPartitionBy clone();


    List<ISQLExpr> getColumns();

    void addColumn(ISQLExpr column);

    SQLSubpartitionTemplate getSubpartitionTemplate();

    void setSubpartitionTemplate(SQLSubpartitionTemplate subpartitionTemplate);

}
