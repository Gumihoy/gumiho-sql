package com.gumihoy.sql.translate.visitor.oracle2;


import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLAlterTableStatement;
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;

/**
 * PARTITIONS 表，如果有创建唯一索引、主键索引 ，需要添加 分区 唯一索引、主键索引
 *
 * @author kent on 2018/5/18.
 */
public class OracleAddCreateTablePartitionConstraintASTVisitor extends SQLASTTransformVisitor {

    public OracleAddCreateTablePartitionConstraintASTVisitor() {
    }

    public OracleAddCreateTablePartitionConstraintASTVisitor(SQLTransformConfig config) {
        super(config);
    }

    @Override
    public boolean visit(SQLAlterTableStatement x) {

        return true;
    }


}
