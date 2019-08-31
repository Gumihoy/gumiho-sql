/*
 * Copyright (C) 2017-2018 kent(kent.bohai@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gumihoy.sql.translate.visitor.oracle2.mysql;

import com.gumihoy.sql.basic.ast.enums.SQLBinaryOperator;
import com.gumihoy.sql.basic.ast.enums.SQLUnaryOperator;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.common.ISQLSubQueryRestrictionClause;
import com.gumihoy.sql.basic.ast.expr.common.SQLCaseExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.datetime.SQLDateDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.datetime.SQLDateTimeDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.datetime.SQLTimestampDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.interval.SQLIntervalDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLBinaryDoubleDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLBinaryFloatDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLDecimalDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLDoubleDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLFloatDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLNumberDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.numeric.SQLNumericDataType;
import com.gumihoy.sql.basic.ast.expr.datatype.string.*;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.identifier.SQLAllColumnExpr;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.AbstractSQLNumericLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLDecimalLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLIntegerLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.numeric.SQLNumericLiteral;
import com.gumihoy.sql.basic.ast.expr.literal.string.SQLStringLiteral;
import com.gumihoy.sql.basic.ast.expr.method.SQLCharFunction;
import com.gumihoy.sql.basic.ast.expr.method.SQLMethodInvocation;
import com.gumihoy.sql.basic.ast.expr.method.SQLTrimFunction;
import com.gumihoy.sql.basic.ast.expr.operator.SQLBinaryOperatorExpr;
import com.gumihoy.sql.basic.ast.expr.operator.SQLUnaryOperatorExpr;
import com.gumihoy.sql.basic.ast.expr.pseudocolumns.SQLSequenceExpr;
import com.gumihoy.sql.basic.ast.expr.select.SQLForUpdateClause;
import com.gumihoy.sql.basic.ast.expr.select.order.SQLOrderByClause;
import com.gumihoy.sql.basic.ast.expr.select.order.SQLOrderByItem;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLCheckTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLForeignKeyTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLPrimaryKeyTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLRefWithRowIdTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLScopeForTableConstraint;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.table.SQLUniqueTableConstraint;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLAlterIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLCreateIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.index.SQLDropIndexStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLAlterViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLCreateViewStatement;
import com.gumihoy.sql.basic.ast.statement.ddl.view.SQLDropViewStatement;
import com.gumihoy.sql.basic.parser.SQLKeyWord;
import com.gumihoy.sql.dialect.oracle.ast.expr.select.OracleSelectQuery;
import com.gumihoy.sql.translate.SQLASTTransformVisitor;
import com.gumihoy.sql.translate.SQLTransformConfig;
import com.gumihoy.sql.util.SQLUtils;

import java.util.List;

/**
 * @author kent onCondition 2018/1/16.
 */
public class Oracle2MySQLVersion8_0ASTTransformVisitor extends SQLASTTransformVisitor {

    public Oracle2MySQLVersion8_0ASTTransformVisitor(SQLTransformConfig config) {
        super(config);
    }


    @Override
    public boolean visit(SQLCreateIndexStatement x) {

        // 为支持 drop index, 缓存 on table
        String name = x.getName().getSimpleName();
        String tableName = x.getTableName();
        config.setIndexTable(name, tableName);

        // bitmap not support, remove
        if (x.getCategory() == SQLCreateIndexStatement.SQLCategory.BITMAP) {
            x.setCategory(null);
        }

        // index properties / attributes 不支持，去掉
        x.getProperties().clear();

        // 字段是 整数 不支持, 去掉
        x.getColumns().removeIf(column -> column.getName() instanceof SQLIntegerLiteral);

        return super.visit(x);
    }

    @Override
    public boolean visit(SQLAlterIndexStatement x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLDropIndexStatement x) {
        ISQLExpr name = x.getNames().get(0);

        // 设置  on table
        String tableName = null;
        if (name instanceof ISQLName) {
            tableName = config.getIndexTable(((ISQLName) name).getSimpleName());
        }
        if (tableName != null) {
            x.setTable(tableName);
        }

        // online、force、invalidation 不支持, remove
        x.setOnline(false);
//        x.setForce(false);
//        x.setInvalidation(null);

        return super.visit(x);
    }


    @Override
    public boolean visit(SQLCreateViewStatement x) {
        if (x.getSubQueryRestriction() instanceof ISQLSubQueryRestrictionClause.SQLWithReadOnly) {
            x.setSubQueryRestriction(null);

        } else if (x.getSubQueryRestriction() instanceof ISQLSubQueryRestrictionClause.SQLWithCheckOption
                && x.getSubQueryRestriction().getConstraint() != null) {
            ((ISQLSubQueryRestrictionClause.SQLWithCheckOption) x.getSubQueryRestriction()).setConstraint(null);
        }

        return super.visit(x);
    }

    @Override
    public boolean visit(SQLAlterViewStatement x) {
        return true;
    }

    @Override
    public boolean visit(SQLDropViewStatement x) {
        return true;
    }


    // ------------------------- Data Types Start ----------------------------------------
    @Override
    public boolean visit(SQLCharDataType x) {
        // [0, 255] => CHAR
        // (255, 2000] => VARCHAR
        List<ISQLExpr> arguments = x.getArguments();

        ISQLDataType target = null;
        if (arguments != null
                && arguments.size() == 1
                && arguments.get(0) instanceof SQLIntegerLiteral
                && ((SQLIntegerLiteral) arguments.get(0)).getLongValue() > 255) {

            target = new SQLVarcharDataType();
            target.addArgument(x.getArguments().get(0));


        }

        if (target != null) {
            SQLUtils.replaceInParent(x, target);
            target.accept(this);
            return false;

        } else if (x.getCharSizeUnit() != null) {
            x.setCharSizeUnit(null);
        }
        return false;
    }

    @Override
    public boolean visit(SQLVarchar2DataType x) {
        // [0, 2000 ] => VARCHAR
        // (2000 , 4000] => TEXT
        ISQLDataType target;
        if (x.getArguments() != null
                && x.getArguments().size() == 1
                && x.getArguments().get(0) instanceof SQLIntegerLiteral
                && ((SQLIntegerLiteral) x.getArguments().get(0)).getLongValue() <= 2000) {

            target = new SQLVarcharDataType();
            target.addArgument(x.getArguments().get(0));
        } else {
            target = new SQLTextDataType();
        }

        boolean replace = SQLUtils.replaceInParent(x, target);
        if (replace) {
            target.accept(this);
        }

        return false;
    }

    @Override
    public boolean visit(SQLNCharDataType x) {
        // [0, 255] => NCHAR
        // (255 , 2000] => NVARCHAR
        ISQLDataType target = null;
        if (x.getArguments() != null
                && x.getArguments().size() == 1
                && x.getArguments().get(0) instanceof SQLIntegerLiteral
                && ((SQLIntegerLiteral) x.getArguments().get(0)).getLongValue() > 255) {
            target = new SQLNVarcharDataType();
            target.addArgument(x.getArguments().get(0));
        }

        if (target != null) {
            boolean replace = SQLUtils.replaceInParent(x, target);
            target.accept(this);
            return false;
        }
        return false;
    }

    @Override
    public boolean visit(SQLNVarchar2DataType x) {
        // [0, 4000] => NVARCHAR
        SQLNVarcharDataType target = new SQLNVarcharDataType();
        target.addArgument(x.getArguments().get(0));

        boolean replace = SQLUtils.replaceInParent(x, target);
        if (replace) {
            target.accept(this);
        }

        return false;
    }

    @Override
    public boolean visit(SQLNumberDataType x) {
        List<ISQLExpr> arguments = x.getArguments();
        int size = arguments.size();

        ISQLDataType target = x;

        // NUMBER|NUMBER(*) => DECIMAL(40, 30)
        // NUMBER(*,0)	    => DECIMAL(40, 0)
        // NUMBER(p)        => DECIMAL(p)
        // NUMBER(p, 0)     => DECIMAL(p, 0)
        // NUMBER(p, s)  (p > s > 0)   => DECIMAL(p, s)
        // NUMBER(p, s)  (p > 0 > s)   => DECIMAL(p + |s|, 0)
        // NUMBER(p, s)  (s > p > 0)   => DECIMAL(p, p)

        if (size == 0) {
            target = new SQLDecimalDataType(40, 30);

        } else if (size == 1) {
            ISQLExpr arg0 = arguments.get(0);
            if (arg0 instanceof SQLAllColumnExpr) {
                target = new SQLDecimalDataType(40, 30);
            } else {
                target = new SQLDecimalDataType();
                target.addArgument(arguments.get(0));
            }

        } else if (size == 2) {
            ISQLExpr arg0 = arguments.get(0);
            ISQLExpr arg1 = arguments.get(1);

            if (arg0 instanceof SQLAllColumnExpr
                    && arg1 instanceof SQLIntegerLiteral
                    && ((SQLIntegerLiteral) arg1).getLongValue() == 0) {
                target = new SQLDecimalDataType(40, 0);

            } else {

                target = new SQLDecimalDataType();
                target.addArgument(arguments.get(0).clone());
                target.addArgument(arguments.get(1).clone());

                if (arguments.get(0) instanceof SQLIntegerLiteral
                        && arguments.get(1) instanceof SQLIntegerLiteral
                        && ((SQLIntegerLiteral) arguments.get(0)).getLongValue() < ((SQLIntegerLiteral) arguments.get(1)).getLongValue()) {
                    ((SQLIntegerLiteral) target.getArguments().get(1)).setValue(((SQLIntegerLiteral) arguments.get(0)).getValue());
                }

            }

        }

        if (target == x) {
            return true;
        }

        boolean replace = SQLUtils.replaceInParent(x, target);
        if (replace) {
            target.accept(this);
        }
        return false;
    }

    @Override
    public boolean visit(SQLFloatDataType x) {
        return super.visit(x);
    }


    @Override
    public boolean visit(SQLBinaryFloatDataType x) {
        SQLFloatDataType target = new SQLFloatDataType();
        SQLUtils.replaceInParent(x, target);
        return false;
    }

    @Override
    public boolean visit(SQLBinaryDoubleDataType x) {
        SQLDoubleDataType target = new SQLDoubleDataType();
        SQLUtils.replaceInParent(x, target);
        return false;
    }

    @Override
    public boolean visit(SQLLongDataType x) {
        SQLLongTextDataType target = new SQLLongTextDataType();
        SQLUtils.replaceInParent(x, target);
        return false;
    }

    @Override
    public boolean visit(SQLLongRawDataType x) {
        SQLLongBlobDataType target = new SQLLongBlobDataType();
        SQLUtils.replaceInParent(x, target);
        return false;
    }

    @Override
    public boolean visit(SQLRawDataType x) {
        List<ISQLExpr> arguments = x.getArguments();
        int size = arguments.size();

        ISQLDataType target = x;

        if (size == 1) {
            ISQLExpr targetArg0 = arguments.get(0).clone();
            target = new SQLVarBinaryDataType();
            target.addArgument(targetArg0);
        }

        SQLUtils.replaceInParent(x, target);

        return false;
    }

    @Override
    public boolean visit(SQLDateDataType x) {
        // date => datetime
        SQLDateTimeDataType target = new SQLDateTimeDataType();
        SQLUtils.replaceInParent(x, target);
        target.accept(this);
        return false;
    }

    @Override
    public boolean visit(SQLTimestampDataType x) {
        // TIMESTAMP [WITH TIME ZONE|WITH LOCAL TIME ZONE] => DATETIME(6)
        // TIMESTAMP(p) [WITH TIME ZONE|WITH LOCAL TIME ZONE] => DATETIME(p)
        ISQLExpr lenExpr = SQLIntegerLiteral.of(6);

        if (x.getArguments() != null
                && x.getArguments().size() > 0) {
            lenExpr = x.getArguments().get(0);
        }

        SQLDateTimeDataType target = new SQLDateTimeDataType(lenExpr);

        SQLUtils.replaceInParent(x, target);
        target.accept(this);

        return false;
    }

    @Override
    public boolean visit(SQLIntervalDataType x) {
        // 不支持
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLBlobDataType x) {
        SQLLongBlobDataType target = new SQLLongBlobDataType();
        SQLUtils.replaceInParent(x, target);
        return false;
    }

    @Override
    public boolean visit(SQLClobDataType x) {
        SQLLongTextDataType target = new SQLLongTextDataType();
        SQLUtils.replaceInParent(x, target);
        return false;
    }

    @Override
    public boolean visit(SQLNClobDataType x) {
        SQLLongTextDataType target = new SQLLongTextDataType();
        SQLUtils.replaceInParent(x, target);
        return false;
    }

    @Override
    public boolean visit(SQLBFileDataType x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLRowIdDataType x) {
        SQLCharDataType target = new SQLCharDataType(18);
        SQLUtils.replaceInParent(x, target);
        return false;
    }

    @Override
    public boolean visit(SQLURowIdDataType x) {
        List<ISQLExpr> arguments = x.getArguments();
        int size = arguments.size();

        SQLVarcharDataType target = new SQLVarcharDataType();
        ISQLExpr arg0 = null;
        if (size == 0) {
            arg0 = new SQLIntegerLiteral(4000);
        } else {
            arg0 = arguments.get(0).clone();
        }
        target.addArgument(arg0);

        SQLUtils.replaceInParent(x, target);
        return false;
    }

    @Override
    public boolean visit(SQLCharacterDataType x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCharacterVaryingDataType x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLCharVaryingDataType x) {
        return super.visit(x);
    }

//    @Override
//    public boolean visit(SQLNCharVaryingDataType x) {
//        return super.visit(x);
//    }

    @Override
    public boolean visit(SQLVarcharDataType x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLNationalCharacterDataType x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLNationalCharacterVaryingDataType x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLNationalCharDataType x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLNationalCharVaryingDataType x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLNumericDataType x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLDecimalDataType x) {
        return super.visit(x);
    }

//    @Override
//    public boolean visit(SQLDecDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDoublePrecisionDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLRealDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLXmlTypeDataType x) {
//        SQLLongTextDataType target = new SQLLongTextDataType();
//        SQLUtils.replaceInParent(x, target);
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLUriTypeDataType x) {
//        return super.visit(x);
//    }
//
//
//    @Override
//    public boolean visit(SQLDataTypeImpl x) {
//        return super.visit(x);
//    }

    // ------------------------- Data Types End ----------------------------------------


    // ------------------------- Operators Start ----------------------------------------

    @Override
    public boolean visit(SQLBinaryOperatorExpr x) {
        SQLBinaryOperator operator = x.getOperator();
        if (operator == SQLBinaryOperator.LOGIC_OR) {
            SQLMethodInvocation target = new SQLMethodInvocation(SQLKeyWord.CONCAT.upper);
            target.addArgument(x.getLeft());
            target.addArgument(x.getRight());
            boolean replace = SQLUtils.replaceInParent(x, target);
            if (replace) {
                target.accept(this);
                return false;
            }
        }
        return true;
    }

    // ------------------------- Operators End ----------------------------------------


    // ------------------------- Functions Start ----------------------------------------
    @Override
    public boolean visit(SQLMethodInvocation x) {
        long nameLowerHash = x.lowerHash();
        List<ISQLExpr> arguments = x.getArguments();
        int size = arguments.size();

        ISQLExpr target = x;
        if (nameLowerHash == SQLKeyWord.BITAND.lowerHash) {
            ISQLExpr arg0 = arguments.get(0);
            ISQLExpr arg1 = arguments.get(1);
            target = new SQLBinaryOperatorExpr(true, arg0, SQLBinaryOperator.BIT_AND, arg1);

        } else if (nameLowerHash == SQLKeyWord.COSH.lowerHash) {
            ISQLExpr arg0 = arguments.get(0);

            SQLMethodInvocation leftExprMethod = new SQLMethodInvocation(SQLKeyWord.EXP.upper);
            SQLMethodInvocation rightExprMethod = new SQLMethodInvocation(SQLKeyWord.EXP.upper);

            // cosh(0) => expr(0)
            if (arg0 instanceof AbstractSQLNumericLiteral
                    && ((AbstractSQLNumericLiteral) arg0).getValue().doubleValue() == 0) {
                leftExprMethod.addArgument(arg0.clone());
                target = leftExprMethod;

            } else {

                // cosh(x) => (exp(x) + exp(-x))/2
                if (arg0 instanceof SQLIntegerLiteral) {
                    long value = ((SQLIntegerLiteral) arg0).getLongValue();

                    leftExprMethod.addArgument(SQLIntegerLiteral.of(value));
                    rightExprMethod.addArgument(SQLIntegerLiteral.of(-value));

                } else if (arg0 instanceof SQLNumericLiteral) {
                    double value = ((SQLNumericLiteral) arg0).getValue().doubleValue();
                    leftExprMethod.addArgument(SQLNumericLiteral.of(value));
                    rightExprMethod.addArgument(SQLNumericLiteral.of(-value));
                } else {
                    leftExprMethod.addArgument(arg0.clone());
                    rightExprMethod.addArgument(SQLUnaryOperatorExpr.of(SQLUnaryOperator.NEGATIVE, arg0.clone()));
                }

                SQLBinaryOperatorExpr left = SQLBinaryOperatorExpr.of(true, leftExprMethod, SQLBinaryOperator.Add, rightExprMethod);
                target = new SQLBinaryOperatorExpr(left, SQLBinaryOperator.Divide, SQLIntegerLiteral.of(2));
            }

        } else if (nameLowerHash == SQLKeyWord.REMAINDER.lowerHash) {
            // (n1 - n2*ROUND(n1/n2))

            ISQLExpr arg0 = arguments.get(0);
            ISQLExpr arg1 = arguments.get(1);

            SQLMethodInvocation roundMethod = new SQLMethodInvocation(SQLKeyWord.ROUND.upper);
            roundMethod.addArgument(SQLBinaryOperatorExpr.of(arg0.clone(), SQLBinaryOperator.Divide, arg1.clone()));

            SQLBinaryOperatorExpr right = SQLBinaryOperatorExpr.of(arg1, SQLBinaryOperator.Multiply, roundMethod);

            target = new SQLBinaryOperatorExpr(true, arg0, SQLBinaryOperator.Sub, right);

        } else if (nameLowerHash == SQLKeyWord.SINH.lowerHash) {
            // (EXP(num) - EXP(-num)) / 2

            ISQLExpr arg0 = arguments.get(0);

            SQLMethodInvocation leftExprMethod = new SQLMethodInvocation(SQLKeyWord.EXP.upper);
            SQLMethodInvocation rightExprMethod = new SQLMethodInvocation(SQLKeyWord.EXP.upper);

            // SINH(0) => 0
            if (arg0 instanceof AbstractSQLNumericLiteral
                    && ((AbstractSQLNumericLiteral) arg0).getValue().doubleValue() == 0) {

                target = SQLIntegerLiteral.of(0);

            } else {

                // SINH(x) => (exp(x) - exp(-x))/2
                if (arg0 instanceof SQLIntegerLiteral) {
                    long value = ((SQLIntegerLiteral) arg0).getLongValue();

                    leftExprMethod.addArgument(SQLIntegerLiteral.of(value));
                    rightExprMethod.addArgument(SQLIntegerLiteral.of(-value));

                } else if (arg0 instanceof SQLNumericLiteral) {
                    double value = ((SQLNumericLiteral) arg0).getValue().doubleValue();
                    leftExprMethod.addArgument(SQLNumericLiteral.of(value));
                    rightExprMethod.addArgument(SQLNumericLiteral.of(-value));
                } else {
                    leftExprMethod.addArgument(arg0.clone());
                    rightExprMethod.addArgument(SQLUnaryOperatorExpr.of(SQLUnaryOperator.NEGATIVE, arg0.clone()));
                }

                SQLBinaryOperatorExpr left = SQLBinaryOperatorExpr.of(true, leftExprMethod, SQLBinaryOperator.Sub, rightExprMethod);
                target = new SQLBinaryOperatorExpr(left, SQLBinaryOperator.Divide, SQLIntegerLiteral.of(2));
            }

        } else if (nameLowerHash == SQLKeyWord.TANH.lowerHash) {
            // (EXP(2*num) - 1)/(EXP(2*num) + 1)
            ISQLExpr arg0 = arguments.get(0);

            ISQLExpr newArg0 = null;
//            if (arg0 instanceof SQLIntegerLiteral) {
//                long newVal = 2 * ((SQLIntegerLiteral) arg0).getLongValue();
//                newArg0 = SQLIntegerLiteral.of(newVal);
//            } else if (arg0 instanceof SQLDecimalLiteral) {
//                double newVal = 2 * ((AbstractSQLNumericLiteral) arg0).getValue().doubleValue();
//                newArg0 = SQLDecimalLiteral.of(newVal);
//            } else {
//                newArg0 = SQLBinaryOperatorExpr.of(SQLIntegerLiteral.of(2), SQLBinaryOperator.Multiply, arg0);
//            }

            SQLBinaryOperatorExpr left = SQLBinaryOperatorExpr.of(true, SQLMethodInvocation.of(SQLKeyWord.EXP.upper, newArg0.clone()), SQLBinaryOperator.Sub, SQLIntegerLiteral.of(1));
            SQLBinaryOperatorExpr right = SQLBinaryOperatorExpr.of(true, SQLMethodInvocation.of(SQLKeyWord.EXP.upper, newArg0.clone()), SQLBinaryOperator.Add, SQLIntegerLiteral.of(1));

            target = SQLBinaryOperatorExpr.of(left, SQLBinaryOperator.Divide, right);

        } else if (nameLowerHash == SQLKeyWord.CHR.lowerHash) {
            SQLCharFunction charFunction = new SQLCharFunction();
            charFunction.addArgument(arguments.get(0));
            target = charFunction;

        } else if (nameLowerHash == SQLKeyWord.LPAD.lowerHash) {
            if (size == 2) {
                x.addArgument(SQLStringLiteral.empty());
            }

        } else if (nameLowerHash == SQLKeyWord.LTRIM.lowerHash) {
            if (size == 2) {
                // LTRIM(string, set)  => TRIM(LEADING set FROM string)

                ISQLExpr arg0 = arguments.get(0);
                ISQLExpr arg1 = arguments.get(1);

                target = new SQLTrimFunction(SQLTrimFunction.SQLTrimSpecification.LEADING, arg1, arg0);
            }

        } else if (nameLowerHash == SQLKeyWord.NCHAR.lowerHash) {

        } else if (nameLowerHash == SQLKeyWord.REPLACE.lowerHash) {
            if (size == 2) {
                x.addArgument(SQLStringLiteral.empty());
            }
        } else if (nameLowerHash == SQLKeyWord.RPAD.lowerHash) {
            if (size == 2) {
                x.addArgument(SQLStringLiteral.empty());
            }
        } else if (nameLowerHash == SQLKeyWord.RTRIM.lowerHash) {
            if (size == 2) {
                // RTRIM(string, set)  => TRIM(TRAILING set FROM string)

                ISQLExpr arg0 = arguments.get(0);
                ISQLExpr arg1 = arguments.get(1);

                target = new SQLTrimFunction(SQLTrimFunction.SQLTrimSpecification.TRAILING, arg1, arg0);
            }

        } else if (nameLowerHash == SQLKeyWord.INSTR.lowerHash) {
            if (size == 3) {
                target = SQLMethodInvocation.of(SQLKeyWord.LOCAL.upper, arguments);
            }

        } else if (nameLowerHash == SQLKeyWord.LENGTH.lowerHash) {
            // LENGTH(string) =>  CHAR_LENGTH(string)
            target = SQLMethodInvocation.of(SQLKeyWord.CHAR_LENGTH.upper, arguments);
        } else if (nameLowerHash == SQLKeyWord.LENGTHB.lowerHash) {
            // LENGTHB(string) =>  LENGTH(string)
            target = SQLMethodInvocation.of(SQLKeyWord.LENGTH.upper, arguments);

        } else if (nameLowerHash == SQLKeyWord.ADD_MONTHS.lowerHash) {
            SQLMethodInvocation timeStampAdd = new SQLMethodInvocation(SQLKeyWord.TIMESTAMPADD.upper);

//            timeStampAdd.addArgument(SQLKeyWord.MONTH.upper);
            timeStampAdd.addArgument(arguments.get(1));
            timeStampAdd.addArgument(arguments.get(0));

            target = timeStampAdd;

        } else if (nameLowerHash == SQLKeyWord.SYSDATE.lowerHash
                && !x.isParen()) {
            x.setParen(true);

        } else if (nameLowerHash == SQLKeyWord.SYSTIMESTAMP.lowerHash) {
            target = SQLMethodInvocation.ofNoneParen(SQLKeyWord.CURRENT_TIMESTAMP.upper);
        } else if (nameLowerHash == SQLKeyWord.NVL.lowerHash) {
            target = SQLMethodInvocation.of(SQLKeyWord.IFNULL.upper, arguments);
        } else if (nameLowerHash == SQLKeyWord.NVL2.lowerHash) {
            target = new SQLCaseExpr();
        } else if (nameLowerHash == SQLKeyWord.SYS_GUID.lowerHash) {
            // SYS_GUID()	=>  REPLACE(UUID(), '-', '')
            target = SQLMethodInvocation.of(SQLKeyWord.REPLACE.upper, SQLMethodInvocation.of(SQLKeyWord.UUID.upper), SQLStringLiteral.of("-"), SQLStringLiteral.empty());
        } else if (nameLowerHash == SQLKeyWord.USER.lowerHash
                && !x.isParen()) {
            // USER	=>  USER()
            x.setParen(true);
        }


        if (x != target) {
            SQLUtils.replaceInParent(x, target);
            target.accept(this);
            return false;
        }

        return super.visit(x);
    }

    // ------------------------- Functions End ----------------------------------------


    // ------------------------- Expressions Start ----------------------------------------

    @Override
    public boolean visit(SQLSequenceExpr x) {
        if (SQLSequenceExpr.SQLSequenceFunction.NEXTVAL == x.getName()) {
            SQLMethodInvocation nextValMethod = SQLMethodInvocation.of(SQLKeyWord.NEXTVAL.upper);
            nextValMethod.addArgument(x.getSequence().clone());
            boolean replace = SQLUtils.replaceInParent(x, nextValMethod);
            if (replace) {
                nextValMethod.accept(this);
            }
            return false;
        }

        return super.visit(x);
    }

    // ------------------------- Expressions End ----------------------------------------


    // ------------------------- column constraint Start ----------------------------------------

    // ------------------------- column constraint End ----------------------------------------


    // ------------------------- table constraint Start ----------------------------------------
    @Override
    public boolean visit(SQLCheckTableConstraint x) {
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLForeignKeyTableConstraint x) {
        x.getReferencingColumns();
        x.getReferencedColumns();
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLPrimaryKeyTableConstraint x) {
        x.getColumns();
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLUniqueTableConstraint x) {
        x.getColumns();
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


    // ------------------ Select Details Start ----------------------

    @Override
    public boolean visit(OracleSelectQuery x) {
        return true;
    }

    @Override
    public boolean visit(SQLOrderByClause x) {
        if (x.isSiblings()) {

        }
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLOrderByItem x) {
        //
        if (x.getNullOrdering() != null) {

        }
        return super.visit(x);
    }

    @Override
    public boolean visit(SQLForUpdateClause.SQLForWaitOption x) {
        boolean replace = SQLUtils.replaceInParent(x, null);
        if (replace) {

        }
        return false;
    }

    // ------------------ Select Details End ----------------------

}
