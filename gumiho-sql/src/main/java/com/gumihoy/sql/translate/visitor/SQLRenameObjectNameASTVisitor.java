package com.gumihoy.sql.translate.visitor;

import com.gumihoy.sql.basic.ast.expr.table.element.constraint.column.*;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLCheckTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLForeignKeyTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLPrimaryKeyTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLRefWithRowIdTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLScopeForTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLUniqueTableConstraint;
import com.gumihoy.sql.basic.ast.statement.ddl.table.SQLCreateTableStatement;
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;

/**
 * rename object name
 * 修改对象名称（表名、视图名等）
 *
 * @author kent on 2018/5/18.
 */
public class SQLRenameObjectNameASTVisitor extends SQLASTTransformVisitor {


    public SQLRenameObjectNameASTVisitor(SQLTransformConfig config) {
        super(config);
    }


    @Override
    public boolean visit(SQLCreateTableStatement x) {
        SQLTransformConfig.TableMapping tableMapping = config.findTableMapping(x.getName());
        if (tableMapping == null
                || tableMapping.targetName == null
                || tableMapping.targetName.length() == 0) {
            return false;
        }

        x.setTableName(tableMapping.targetName);

        return false;
    }



    // ------------------------- column constraint Start ----------------------------------------
    @Override
    public boolean visit(SQLCheckColumnConstraint x) {
        
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLNotNullColumnConstraint x) {
        
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLNullColumnConstraint x) {
        
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLPrimaryKeyColumnConstraint x) {
        
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLReferencesColumnConstraint x) {
        
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLUniqueColumnConstraint x) {
        
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLScopeIsColumnConstraint x) {
        
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLWithRowIdColumnConstraint x) {
        
        return super.visit(x);
    }
    // ------------------------- column constraint End ----------------------------------------


    // ------------------------- table constraint Start ----------------------------------------
    @Override
    public boolean visit(SQLCheckTableConstraint x) {
        
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLForeignKeyTableConstraint x) {
        
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLPrimaryKeyTableConstraint x) {
        
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLUniqueTableConstraint x) {
        
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLScopeForTableConstraint x) {
        
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLRefWithRowIdTableConstraint x) {
        
        return super.visit(x);
    }
    // ------------------------- table constraint End ----------------------------------------
}
