package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kent on 2019-07-26.
 */
public interface ISQLPragma extends ISQLExpr {
    @Override
    ISQLPragma clone();

    List<ISQLExpr> getArguments();

    void addArgument(ISQLExpr argument);


    abstract class AbstractSQLPragma extends AbstractSQLExpr implements ISQLPragma {

        protected final List<ISQLExpr> arguments = new ArrayList<>();

        public AbstractSQLPragma() {
            setAfterSemi(true);
        }

        @Override
        public AbstractSQLPragma clone() {
            throw new UnsupportedOperationException();
        }


        public void cloneTo(AbstractSQLPragma x) {
            super.cloneTo(x);

            for (ISQLExpr argument : arguments) {
                ISQLExpr argumentClone = argument.clone();
                x.addArgument(argumentClone);
            }
        }

        public List<ISQLExpr> getArguments() {
            return arguments;
        }

        public void addArgument(ISQLExpr argument) {
            if (argument == null) {
                return;
            }
            setChildParent(argument);
            this.arguments.add(argument);
        }

    }

    /**
     * PRAGMA AUTONOMOUS_TRANSACTION ;
     */
    class SQLAutonomousTransactionPragma extends AbstractSQLPragma {

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }
    }


    /**
     * PRAGMA COVERAGE ( coverage_pragma_argument);
     */
    class SQLCoveragePragma extends AbstractSQLPragma {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, arguments);
            }
        }
    }


    /**
     * PRAGMA DEPRECATE ( “pls_identifier” [, “character_literal”] )
     */
    class SQLDeprecatePragma extends AbstractSQLPragma {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, arguments);
            }
        }
    }

    /**
     * PRAGMA EXCEPTION_INIT ( “pls_identifier” [, “character_literal”] )
     */
    class SQLExceptionInitPragma extends AbstractSQLPragma {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, arguments);
            }
        }
    }


    /**
     * PRAGMA INLINE ( subprogram , { 'YES' | 'NO' } ) ;
     */
    class SQLInlinePragma extends AbstractSQLPragma {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, arguments);
            }
        }
    }

    /**
     * PRAGMA RESTRICT_REFERENCES ( subprogram , { 'YES' | 'NO' } ) ;
     */
    class SQLRestrictReferencesPragma extends AbstractSQLPragma {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, arguments);
            }
        }
    }

    /**
     * PRAGMA SERIALLY_REUSABLE ;
     */
    class SQLSeriallyReusablePragma extends AbstractSQLPragma {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }
    }


    /**
     * PRAGMA UTF ;
     */
    class SQLUDFPragma extends AbstractSQLPragma {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            visitor.visit(this);
        }
    }
}
