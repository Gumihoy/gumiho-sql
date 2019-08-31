///*
// * Copyright (C) 2017-2018 kent(kent.bohai@gmail.com).
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.gumihoy.sql.translate.visitor.oracle2.mariadb;
//
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.SQLDataType;
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.SQLDataTypeImpl;
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.datetime.SQLDateDataType;
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.datetime.SQLTimestampDataType;
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.interval.SQLIntervalDataType;
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.numeric.*;
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.string.*;
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.xml.SQLUriTypeDataType;
//import com.aliyun.gumiho.sql.basic.ast.element.datatype.xml.SQLXmlTypeDataType;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.ISQLSubqueryRestrictionClause;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.SQLCaseExpr;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.SQLExpr;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.operator.SQLBinaryOperatorExpr;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.operator.SQLUnaryOperatorExpr;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.select.SQLForUpdateClause;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.select.order.SQLOrderByClause;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.select.order.SQLOrderByItem;
//import com.aliyun.gumiho.sql.basic.ast.element.expr.table.element.constraint.table.*;
//import com.aliyun.gumiho.sql.basic.ast.element.function.SQLCharFunction;
//import com.aliyun.gumiho.sql.basic.ast.element.function.SQLExtractFunction;
//import com.aliyun.gumiho.sql.basic.ast.element.function.SQLMethodInvocation;
//import com.aliyun.gumiho.sql.basic.ast.element.function.SQLTrimFunction;
//import com.aliyun.gumiho.sql.basic.ast.element.identifier.SQLAllColumnExpr;
//import com.aliyun.gumiho.sql.basic.ast.element.identifier.SQLName;
//import com.aliyun.gumiho.sql.basic.ast.element.literal.numeric.AbstractSQLNumericLiteral;
//import com.aliyun.gumiho.sql.basic.ast.element.literal.numeric.SQLIntegerLiteral;
//import com.aliyun.gumiho.sql.basic.ast.element.literal.numeric.SQLNumberLiteral;
//import com.aliyun.gumiho.sql.basic.ast.element.literal.text.SQLCharLiteral;
//import com.aliyun.gumiho.sql.basic.ast.element.pseudocolumn.SQLSequenceExpr;
//import com.aliyun.gumiho.sql.basic.ast.enums.SQLBinaryOperator;
//import com.aliyun.gumiho.sql.basic.ast.enums.SQLIndexCategory;
//import com.aliyun.gumiho.sql.basic.ast.enums.SQLReserved;
//import com.aliyun.gumiho.sql.basic.ast.enums.SQLUnaryOperator;
//import com.aliyun.gumiho.sql.basic.ast.statement.ddl.index.SQLAlterIndexStatement;
//import com.aliyun.gumiho.sql.basic.ast.statement.ddl.index.SQLCreateIndexStatement;
//import com.aliyun.gumiho.sql.basic.ast.statement.ddl.index.SQLDropIndexStatement;
//import com.aliyun.gumiho.sql.basic.ast.statement.ddl.table.SQLCreateTableStatement;
//import com.aliyun.gumiho.sql.basic.ast.statement.ddl.view.SQLAlterViewStatement;
//import com.aliyun.gumiho.sql.basic.ast.statement.ddl.view.SQLCreateViewStatement;
//import com.aliyun.gumiho.sql.basic.ast.statement.ddl.view.SQLDropViewStatement;
//import com.aliyun.gumiho.sql.translate.SQLASTTransformVisitor;
//import com.aliyun.gumiho.sql.translate.SQLTransformConfig;
//import com.aliyun.gumiho.sql.util.SQLUtils;
//
//import java.util.List;
//
///**
// * @author kent onCondition 2018/1/16.
// */
//public class Oracle2MariaDBASTTransformVisitor extends SQLASTTransformVisitor {
//
//    public Oracle2MariaDBASTTransformVisitor(SQLTransformConfig config) {
//        super(config);
//    }
//
//
//    @Override
//    public boolean visit(SQLCreateIndexStatement x) {
//
//        // 为支持 drop index, 缓存 on table
//        String name = x.getName().getName();
//        String tableName = x.getTableName();
//        config.setIndexTable(name, tableName);
//
//        // bitmap not support, remove
//        if (x.getCategory() == SQLIndexCategory.BITMAP) {
//            x.setCategory(null);
//        }
//
//        // index properties / attributes 不支持，去掉
//        x.getProperties().clear();
//
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLAlterIndexStatement x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDropIndexStatement x) {
//        SQLExpr name = x.getNames().get(0);
//
//        // 设置  on table
//        String tableName = null;
//        if (name instanceof SQLName) {
//            tableName = config.getIndexTable(((SQLName) name).getName());
//        }
//        if (tableName != null) {
//            x.setTable(tableName);
//        }
//
//        // online、force、invalidation 不支持, remove
//        x.setOnline(false);
//        x.setForce(false);
//        x.setInvalidation(null);
//
//        return super.visit(x);
//    }
//
//
//    @Override
//    public boolean visit(SQLCreateViewStatement x) {
//        if (x.getSubqueryRestriction() instanceof ISQLSubqueryRestrictionClause.SQLWithReadOnly) {
//            x.setSubqueryRestriction(null);
//
//        } else if (x.getSubqueryRestriction() instanceof ISQLSubqueryRestrictionClause.SQLWithCheckOption
//                && x.getSubqueryRestriction().getConstraint() != null) {
//            ((ISQLSubqueryRestrictionClause.SQLWithCheckOption) x.getSubqueryRestriction()).setConstraint(null);
//        }
//
//        return true;
//    }
//
//    @Override
//    public boolean visit(SQLAlterViewStatement x) {
//        return true;
//    }
//
//    @Override
//    public boolean visit(SQLDropViewStatement x) {
//        return true;
//    }
//
//    @Override
//    public boolean visit(SQLCreateTableStatement x) {
//        return true;
//    }
//
//
//
//
//
//    // ------------------------- Data Types Start ----------------------------------------
//    @Override
//    public boolean visit(SQLCharDataType x) {
//        // [0, 255] => CHAR
//        // (255, 2000] => VARCHAR
//        List<SQLExpr> arguments = x.getArguments();
//
//        SQLDataType target = null;
//        if (arguments != null
//                && arguments.size() == 1
//                && arguments.get(0) instanceof SQLIntegerLiteral
//                && ((SQLIntegerLiteral) arguments.get(0)).getLongValue() > 255) {
//
//            target = new SQLVarcharDataType();
//            target.addArgument(x.getArguments().get(0));
//
//
//        }
//
//        if (target != null) {
//            SQLUtils.replaceInParent(x, target);
//            target.accept(this);
//            return false;
//
//        } else if (x.getCharSizeUnit() != null) {
//            x.setCharSizeUnit(null);
//        }
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLVarchar2DataType x) {
//        // [0, 2000 ] => VARCHAR
//        // (2000 , 4000] => TEXT
//        SQLDataType target;
//        if (x.getArguments() != null
//                && x.getArguments().size() == 1
//                && x.getArguments().get(0) instanceof SQLIntegerLiteral
//                && ((SQLIntegerLiteral) x.getArguments().get(0)).getLongValue() <= 2000) {
//
//            target = new SQLVarcharDataType();
//            target.addArgument(x.getArguments().get(0));
//        } else {
//            target = new SQLTextDataType();
//        }
//
//        boolean replace = SQLUtils.replaceInParent(x, target);
//        if (replace) {
//            target.accept(this);
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLNCharDataType x) {
//        // [0, 255] => NCHAR
//        // (255 , 2000] => NVARCHAR
//        SQLDataType target = null;
//        if (x.getArguments() != null
//                && x.getArguments().size() == 1
//                && x.getArguments().get(0) instanceof SQLIntegerLiteral
//                && ((SQLIntegerLiteral) x.getArguments().get(0)).getLongValue() > 255) {
//            target = new SQLNVarcharDataType();
//            target.addArgument(x.getArguments().get(0));
//        }
//
//        if (target != null) {
//            boolean replace = SQLUtils.replaceInParent(x, target);
//            target.accept(this);
//            return false;
//        }
//
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNVarchar2DataType x) {
//        // [0, 4000] => NVARCHAR
//        SQLNVarcharDataType target = new SQLNVarcharDataType();
//        target.addArgument(x.getArguments().get(0));
//
//        boolean replace = SQLUtils.replaceInParent(x, target);
//        if (replace) {
//            target.accept(this);
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLNumberDataType x) {
//        List<SQLExpr> arguments = x.getArguments();
//        int size = arguments.size();
//
//        SQLDataType target = x;
//
//        // NUMBER|NUMBER(*) => DECIMAL(40, 30)
//        // NUMBER(*,0)	    => DECIMAL(40, 0)
//        // NUMBER(p)        => DECIMAL(p)
//        // NUMBER(p, 0)     => DECIMAL(p, 0)
//        // NUMBER(p, s)  (p > s > 0)   => DECIMAL(p, s)
//        // NUMBER(p, s)  (p > 0 > s)   => DECIMAL(p + |s|, 0)
//        // NUMBER(p, s)  (s > p > 0)   => DECIMAL(p, p)
//
//        if (size == 0) {
//            target = new SQLDecimalDataType(40, 30);
//
//        } else if (size == 1) {
//            SQLExpr arg0 = arguments.get(0);
//            if (arg0 instanceof SQLAllColumnExpr) {
//                target = new SQLDecimalDataType(40, 30);
//            } else {
//                target = new SQLDecimalDataType();
//                target.addArgument(arguments.get(0));
//            }
//
//        } else if (size == 2) {
//            SQLExpr arg0 = arguments.get(0);
//            SQLExpr arg1 = arguments.get(1);
//
//            if (arg0 instanceof SQLAllColumnExpr
//                    && arg1 instanceof SQLIntegerLiteral
//                    && ((SQLIntegerLiteral) arg1).getLongValue() == 0) {
//                target = new SQLDecimalDataType(40, 0);
//
//            } else {
//
//                target = new SQLDecimalDataType();
//                target.addArgument(arguments.get(0).clone());
//                target.addArgument(arguments.get(1).clone());
//
//                if (arguments.get(0) instanceof SQLIntegerLiteral
//                        && arguments.get(1) instanceof SQLIntegerLiteral
//                        && ((SQLIntegerLiteral) arguments.get(0)).getLongValue() < ((SQLIntegerLiteral) arguments.get(1)).getLongValue()) {
//                    ((SQLIntegerLiteral) target.getArguments().get(1)).setValue(((SQLIntegerLiteral) arguments.get(0)).getValue());
//                }
//
//            }
//
//        }
//
//        if (target == x) {
//            return true;
//        }
//
//        boolean replace = SQLUtils.replaceInParent(x, target);
//        if (replace) {
//            target.accept(this);
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLFloatDataType x) {
//        return super.visit(x);
//    }
//
//
//    @Override
//    public boolean visit(SQLBinaryFloatDataType x) {
//        SQLFloatDataType target = new SQLFloatDataType();
//        SQLUtils.replaceInParent(x, target);
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLBinaryDoubleDataType x) {
//        SQLDoubleDataType target = new SQLDoubleDataType();
//        SQLUtils.replaceInParent(x, target);
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLLongDataType x) {
//        SQLLongTextDataType target = new SQLLongTextDataType();
//        SQLUtils.replaceInParent(x, target);
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLLongRawDataType x) {
//        SQLLongBlobDataType target = new SQLLongBlobDataType();
//        SQLUtils.replaceInParent(x, target);
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLRawDataType x) {
//        List<SQLExpr> arguments = x.getArguments();
//        int size = arguments.size();
//
//        SQLDataType target = x;
//
//        if (size == 1
//                && arguments.get(0) instanceof SQLIntegerLiteral) {
//            long arg0Val = ((SQLIntegerLiteral) arguments.get(0)).getLongValue();
//            SQLIntegerLiteral targetArg0 = new SQLIntegerLiteral(arg0Val);
//            if (arg0Val <= 255) {
//                target = new SQLBinaryDataType();
//                target.addArgument(targetArg0);
//            } else if (arg0Val <= 2000) {
//                target = new SQLVarBinaryDataType();
//                target.addArgument(targetArg0);
//            } else {
//                target = new SQLTextDataType();
//            }
//        }
//
//
//        SQLUtils.replaceInParent(x, target);
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLDateDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLTimestampDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLIntervalDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLBlobDataType x) {
//        SQLLongBlobDataType target = new SQLLongBlobDataType();
//        SQLUtils.replaceInParent(x, target);
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLClobDataType x) {
//        SQLLongTextDataType target = new SQLLongTextDataType();
//        SQLUtils.replaceInParent(x, target);
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLNClobDataType x) {
//        SQLNVarcharDataType target = new SQLNVarcharDataType();
//        SQLUtils.replaceInParent(x, target);
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLBFileDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLRowIdDataType x) {
//        SQLCharDataType target = new SQLCharDataType(10);
//        SQLUtils.replaceInParent(x, target);
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLURowIdDataType x) {
//        List<SQLExpr> arguments = x.getArguments();
//        int size = arguments.size();
//
//        SQLVarcharDataType target = new SQLVarcharDataType();
//        SQLExpr arg0 = null;
//        if (size == 0) {
//            arg0 = new SQLIntegerLiteral(4000);
//        } else {
//            arg0 = arguments.get(0).clone();
//        }
//        target.addArgument(arg0);
//
//        SQLUtils.replaceInParent(x, target);
//
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLCharacterDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLCharacterVaryingDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLCharVaryingDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNCharVaryingDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLVarcharDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNationalCharacterDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNationalCharacterVaryingDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNationalCharDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNationalCharVaryingDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLNumericDataType x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLDecimalDataType x) {
//        return super.visit(x);
//    }
//
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
//
//    // ------------------------- Data Types End ----------------------------------------
//
//
//    // ------------------------- Functions Start ----------------------------------------
//    @Override
//    public boolean visit(SQLExtractFunction x) {
//        return true;
//    }
//
//    @Override
//    public boolean visit(SQLMethodInvocation x) {
//        long nameLowerHash = x.lowerHash();
//        List<SQLExpr> arguments = x.getArguments();
//        int size = arguments.size();
//
//        SQLExpr target = x;
//        if (nameLowerHash == SQLReserved.BITAND.lowerHashCode64) {
//            SQLExpr arg0 = arguments.get(0);
//            SQLExpr arg1 = arguments.get(1);
//            target = new SQLBinaryOperatorExpr(true, arg0, SQLBinaryOperator.AMPERSAND, arg1);
//
//        } else if (nameLowerHash == SQLReserved.COSH.lowerHashCode64) {
//            SQLExpr arg0 = arguments.get(0);
//
//            SQLMethodInvocation leftExprMethod = new SQLMethodInvocation(SQLReserved.EXP.ofExpr());
//            SQLMethodInvocation rightExprMethod = new SQLMethodInvocation(SQLReserved.EXP.ofExpr());
//
//            // cosh(0) => expr(0)
//            if (arg0 instanceof AbstractSQLNumericLiteral
//                    && ((AbstractSQLNumericLiteral) arg0).getValue().doubleValue() == 0) {
//                leftExprMethod.addArgument(arg0.clone());
//                target = leftExprMethod;
//
//            } else {
//
//                // cosh(x) => (exp(x) + exp(-x))/2
//                if (arg0 instanceof SQLIntegerLiteral) {
//                    long value = ((SQLIntegerLiteral) arg0).getLongValue();
//
//                    leftExprMethod.addArgument(SQLIntegerLiteral.of(value));
//                    rightExprMethod.addArgument(SQLIntegerLiteral.of(-value));
//
//                } else if (arg0 instanceof SQLNumberLiteral) {
//                    double value = ((SQLNumberLiteral) arg0).getValue().doubleValue();
//                    leftExprMethod.addArgument(SQLNumberLiteral.of(value));
//                    rightExprMethod.addArgument(SQLNumberLiteral.of(-value));
//                } else {
//                    leftExprMethod.addArgument(arg0.clone());
//                    rightExprMethod.addArgument(SQLUnaryOperatorExpr.of(SQLUnaryOperator.NEGATIVE, arg0.clone()));
//                }
//
//                SQLBinaryOperatorExpr left = SQLBinaryOperatorExpr.of(true, leftExprMethod, SQLBinaryOperator.Add, rightExprMethod);
//                target = new SQLBinaryOperatorExpr(left, SQLBinaryOperator.Divide, SQLIntegerLiteral.of(2));
//            }
//
//        } else if (nameLowerHash == SQLReserved.REMAINDER.lowerHashCode64) {
//            // (n1 - n2*ROUND(n1/n2))
//
//            SQLExpr arg0 = arguments.get(0);
//            SQLExpr arg1 = arguments.get(1);
//
//            SQLMethodInvocation roundMethod = new SQLMethodInvocation(SQLReserved.ROUND.upper);
//            roundMethod.addArgument(SQLBinaryOperatorExpr.of(arg0.clone(), SQLBinaryOperator.Divide, arg1.clone()));
//
//            SQLBinaryOperatorExpr right = SQLBinaryOperatorExpr.of(arg1, SQLBinaryOperator.Multiply, roundMethod);
//
//            target = new SQLBinaryOperatorExpr(true, arg0, SQLBinaryOperator.Subtract, right);
//
//        } else if (nameLowerHash == SQLReserved.SINH.lowerHashCode64) {
//            // (EXP(num) - EXP(-num)) / 2
//
//            SQLExpr arg0 = arguments.get(0);
//
//            SQLMethodInvocation leftExprMethod = new SQLMethodInvocation(SQLReserved.EXP.ofExpr());
//            SQLMethodInvocation rightExprMethod = new SQLMethodInvocation(SQLReserved.EXP.ofExpr());
//
//            // SINH(0) => 0
//            if (arg0 instanceof AbstractSQLNumericLiteral
//                    && ((AbstractSQLNumericLiteral) arg0).getValue().doubleValue() == 0) {
//
//                target = SQLIntegerLiteral.of(0);
//
//            } else {
//
//                // SINH(x) => (exp(x) - exp(-x))/2
//                if (arg0 instanceof SQLIntegerLiteral) {
//                    long value = ((SQLIntegerLiteral) arg0).getLongValue();
//
//                    leftExprMethod.addArgument(SQLIntegerLiteral.of(value));
//                    rightExprMethod.addArgument(SQLIntegerLiteral.of(-value));
//
//                } else if (arg0 instanceof SQLNumberLiteral) {
//                    double value = ((SQLNumberLiteral) arg0).getValue().doubleValue();
//                    leftExprMethod.addArgument(SQLNumberLiteral.of(value));
//                    rightExprMethod.addArgument(SQLNumberLiteral.of(-value));
//                } else {
//                    leftExprMethod.addArgument(arg0.clone());
//                    rightExprMethod.addArgument(SQLUnaryOperatorExpr.of(SQLUnaryOperator.NEGATIVE, arg0.clone()));
//                }
//
//                SQLBinaryOperatorExpr left = SQLBinaryOperatorExpr.of(true, leftExprMethod, SQLBinaryOperator.Subtract, rightExprMethod);
//                target = new SQLBinaryOperatorExpr(left, SQLBinaryOperator.Divide, SQLIntegerLiteral.of(2));
//            }
//
//        } else if (nameLowerHash == SQLReserved.TANH.lowerHashCode64) {
//            // (EXP(2*num) - 1)/(EXP(2*num) + 1)
//            SQLExpr arg0 = arguments.get(0);
//
//            SQLExpr newArg0 = null;
//            if (arg0 instanceof SQLIntegerLiteral) {
//                long newVal = 2 * ((SQLIntegerLiteral) arg0).getLongValue();
//                newArg0 = SQLIntegerLiteral.of(newVal);
//            } else if (arg0 instanceof SQLNumberLiteral) {
//                double newVal = 2 * ((AbstractSQLNumericLiteral) arg0).getValue().doubleValue();
//                newArg0 = SQLNumberLiteral.of(newVal);
//            } else {
//                newArg0 = SQLBinaryOperatorExpr.of(SQLIntegerLiteral.of(2), SQLBinaryOperator.Multiply, arg0);
//            }
//
//            SQLBinaryOperatorExpr left = SQLBinaryOperatorExpr.of(true, SQLMethodInvocation.of(SQLReserved.EXP.upper, newArg0.clone()), SQLBinaryOperator.Subtract, SQLIntegerLiteral.of(1));
//            SQLBinaryOperatorExpr right = SQLBinaryOperatorExpr.of(true, SQLMethodInvocation.of(SQLReserved.EXP.upper, newArg0.clone()), SQLBinaryOperator.Add, SQLIntegerLiteral.of(1));
//
//            target = SQLBinaryOperatorExpr.of(left, SQLBinaryOperator.Divide, right);
//
//        } else if (nameLowerHash == SQLReserved.CHR.lowerHashCode64) {
//            SQLCharFunction charFunction = new SQLCharFunction();
//            charFunction.addArgument(arguments.get(0));
//            target = charFunction;
//
//        } else if (nameLowerHash == SQLReserved.LPAD.lowerHashCode64) {
//            if (size == 2) {
//                x.addArgument(SQLCharLiteral.empty());
//            }
//
//        } else if (nameLowerHash == SQLReserved.LTRIM.lowerHashCode64) {
//            if (size == 2) {
//                // LTRIM(string, set)  => TRIM(LEADING set FROM string)
//
//                SQLExpr arg0 = arguments.get(0);
//                SQLExpr arg1 = arguments.get(1);
//
//                target = new SQLTrimFunction(SQLTrimFunction.SQLTrimSpecification.LEADING, arg1, arg0);
//            }
//
//        } else if (nameLowerHash == SQLReserved.NCHAR.lowerHashCode64) {
//
//        } else if (nameLowerHash == SQLReserved.REPLACE.lowerHashCode64) {
//            if (size == 2) {
//                x.addArgument(SQLCharLiteral.empty());
//            }
//        } else if (nameLowerHash == SQLReserved.RPAD.lowerHashCode64) {
//            if (size == 2) {
//                x.addArgument(SQLCharLiteral.empty());
//            }
//        } else if (nameLowerHash == SQLReserved.RTRIM.lowerHashCode64) {
//            if (size == 2) {
//                // RTRIM(string, set)  => TRIM(TRAILING set FROM string)
//
//                SQLExpr arg0 = arguments.get(0);
//                SQLExpr arg1 = arguments.get(1);
//
//                target = new SQLTrimFunction(SQLTrimFunction.SQLTrimSpecification.TRAILING, arg1, arg0);
//            }
//
//        } else if (nameLowerHash == SQLReserved.INSTR.lowerHashCode64) {
//            if (size == 3) {
//                target = SQLMethodInvocation.of(SQLReserved.LOCAL.upper, arguments);
//            }
//
//        } else if (nameLowerHash == SQLReserved.LENGTH.lowerHashCode64) {
//            // LENGTH(string) =>  CHAR_LENGTH(string)
//            target = SQLMethodInvocation.of(SQLReserved.CHAR_LENGTH.upper, arguments);
//        } else if (nameLowerHash == SQLReserved.LENGTHB.lowerHashCode64) {
//            // LENGTHB(string) =>  LENGTH(string)
//            target = SQLMethodInvocation.of(SQLReserved.LENGTH.upper, arguments);
//
//        } else if (nameLowerHash == SQLReserved.ADD_MONTHS.lowerHashCode64) {
//            SQLMethodInvocation timeStampAdd = new SQLMethodInvocation(SQLReserved.TIMESTAMPADD.upper);
//
//            timeStampAdd.addArgument(SQLReserved.MONTH.ofExpr());
//            timeStampAdd.addArgument(arguments.get(1));
//            timeStampAdd.addArgument(arguments.get(0));
//
//            target = timeStampAdd;
//
//        } else if (nameLowerHash == SQLReserved.SYSDATE.lowerHashCode64
//                && !x.isParen()) {
//            x.setParen(true);
//
//        } else if (nameLowerHash == SQLReserved.SYSTIMESTAMP.lowerHashCode64) {
//            target = SQLMethodInvocation.ofNoneParen(SQLReserved.CURRENT_TIMESTAMP.upper);
//        } else if (nameLowerHash == SQLReserved.NVL.lowerHashCode64) {
//            target = SQLMethodInvocation.of(SQLReserved.IFNULL.upper, arguments);
//        } else if (nameLowerHash == SQLReserved.NVL2.lowerHashCode64) {
//            target = new SQLCaseExpr();
//        } else if (nameLowerHash == SQLReserved.SYS_GUID.lowerHashCode64) {
//            // SYS_GUID()	=>  REPLACE(UUID(), '-', '')
//            target = SQLMethodInvocation.of(SQLReserved.REPLACE.upper, SQLMethodInvocation.of(SQLReserved.UUID.upper), SQLCharLiteral.of("-"), SQLCharLiteral.empty());
//        } else if (nameLowerHash == SQLReserved.USER.lowerHashCode64
//                && !x.isParen()) {
//            // USER	=>  USER()
//            x.setParen(true);
//        }
//
//
//
//
//        if (x != target) {
//            SQLUtils.replaceInParent(x, target);
//            target.accept(this);
//            return false;
//        }
//
//        return super.visit(x);
//    }
//
//
//    // ------------------------- Functions End ----------------------------------------
//
//    // ------------------------- Expressions Start ----------------------------------------
//
//    @Override
//    public boolean visit(SQLSequenceExpr x) {
//        if (SQLSequenceExpr.SQLSequenceFunction.NEXTVAL == x.getName()) {
//            SQLMethodInvocation nextValMethod = SQLMethodInvocation.of(SQLReserved.NEXTVAL.ofExpr());
//            nextValMethod.addArgument(x.getSequence().clone());
//            boolean replace = SQLUtils.replaceInParent(x, nextValMethod);
//            if (replace) {
//                nextValMethod.accept(this);
//            }
//            return false;
//        }
//
//        return super.visit(x);
//    }
//
//    // ------------------------- Expressions End ----------------------------------------
//
//
//    // ------------------------- column constraint Start ----------------------------------------
//
//    // ------------------------- column constraint End ----------------------------------------
//
//
//    // ------------------------- table constraint Start ----------------------------------------
//    @Override
//    public boolean visit(SQLCheckTableConstraint x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLForeignKeyTableConstraint x) {
//        x.getReferencingColumns();
//        x.getReferencedColumns();
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLPrimaryKeyTableConstraint x) {
//        x.getColumns();
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLUniqueTableConstraint x) {
//        x.getColumns();
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLScopeForTableConstraint x) {
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLRefWithRowIdTableConstraint x) {
//        return super.visit(x);
//    }
//
//
//    // ------------------------- table constraint End ----------------------------------------
//
//
//    // ------------------ Select Details Start ----------------------
//    @Override
//    public boolean visit(SQLOrderByClause x) {
//
//        if (x.isSiblings()) {
//
//        }
//
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLOrderByItem x) {
//        //
//        if (x.getNullOrdering() != null) {
//
//        }
//        return super.visit(x);
//    }
//
//    @Override
//    public boolean visit(SQLForUpdateClause x) {
//        if (x.getColumns() != null
//                && x.getColumns().size() > 0) {
//            x.getColumns().clear();
//        }
//        return true;
//    }
//
//    @Override
//    public boolean visit(SQLForUpdateClause.SQLForSkipLockedOption x) {
//        boolean replace = SQLUtils.replaceInParent(x, null);
//        if (replace) {
//
//        }
//        return false;
//    }
//
//    @Override
//    public boolean visit(SQLForUpdateClause.SQLForNoWaitOption x) {
//        boolean replace = SQLUtils.replaceInParent(x, null);
//        if (replace) {
//
//        }
//        return false;
//    }
//
//    // ------------------ Select Details End ----------------------
//
//}
