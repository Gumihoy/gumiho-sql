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
package com.gumihoy.sql.basic.ast.expr.common;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLASTEnum;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.datatype.ISQLDataType;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.visitor.ISQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * https://docs.oracle.com/en/database/oracle/oracle-database/12.2/lnpls/call-specification.html#GUID-C5F117AE-E9A2-499B-BA6A-35D072575BAD
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/lnpls/call-specification.html#GUID-C5F117AE-E9A2-499B-BA6A-35D072575BAD
 *
 * @author kent onCondition 2018/3/22.
 */
public interface ISQLCallSpec extends ISQLExpr {

    @Override
    ISQLCallSpec clone();

    class SQLJavaDeclaration extends AbstractSQLExpr implements ISQLCallSpec {

        private ISQLName name;

        public SQLJavaDeclaration(ISQLName name) {
            setName(name);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
//            if (visitor.visit(this)) {
//                this.acceptChild(visitor, name);
//            }
        }

        @Override
        public SQLJavaDeclaration clone() {

            ISQLName nameClone = this.name.clone();

            SQLJavaDeclaration x = new SQLJavaDeclaration(nameClone);

            return x;
        }

        public ISQLName getName() {
            return name;
        }

        public void setName(ISQLName name) {
            setChildParent(name);
            this.name = name;
        }
    }



    abstract class AbstractSQLCDeclaration extends AbstractSQLExpr implements ISQLCallSpec {

        protected final List<ISQLExpr> names = new ArrayList<>();

        protected final List<ISQLExpr> agentInArguments = new ArrayList<>();

        protected boolean withContext;

        protected final List<SQLExternalParameter> parameters = new ArrayList<>();

        @Override
        public AbstractSQLCDeclaration clone() {
            throw new UnsupportedOperationException();
        }


        public void cloneTo(AbstractSQLCDeclaration x) {

            for (ISQLExpr name : this.names) {
                ISQLExpr nameClone = name.clone();
                x.addName(nameClone);
            }

            for (ISQLExpr agentInArgument : this.agentInArguments) {
                ISQLExpr agentInArgumentClone = agentInArgument.clone();
                x.addAgentInArgument(agentInArgumentClone);
            }

            x.withContext = this.withContext;

            for (SQLExternalParameter parameter : this.parameters) {
                SQLExternalParameter parameterClone = parameter.clone();
                x.addParameter(parameterClone);
            }
        }


        public List<ISQLExpr> getNames() {
            return names;
        }

        public void addName(ISQLExpr name) {
            if (name == null) {
                return;
            }
            setChildParent(name);
            this.names.add(name);
        }

        public List<ISQLExpr> getAgentInArguments() {
            return agentInArguments;
        }

        public void addAgentInArgument(ISQLExpr agentInArgument) {
            if (agentInArgument == null) {
                return;
            }
            setChildParent(agentInArgument);
            this.agentInArguments.add(agentInArgument);
        }

        public boolean isWithContext() {
            return withContext;
        }

        public void setWithContext(boolean withContext) {
            this.withContext = withContext;
        }

        public List<SQLExternalParameter> getParameters() {
            return parameters;
        }

        public void addParameter(SQLExternalParameter parameter) {
            if (parameter == null) {
                return;
            }
            setChildParent(parameter);
            this.parameters.add(parameter);
        }

    }

    class SQLCDeclarationLanguageC extends AbstractSQLCDeclaration implements ISQLCallSpec {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, names);
                this.acceptChild(visitor, agentInArguments);
                this.acceptChild(visitor, parameters);
            }
        }

    }

    class SQLCDeclarationExternal extends AbstractSQLCDeclaration implements ISQLCallSpec {
        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, names);
                this.acceptChild(visitor, agentInArguments);
                this.acceptChild(visitor, parameters);
            }
        }

    }


    /**
     * NAME name
     */
    class SQLNameExpr extends AbstractSQLExpr {
        protected ISQLExpr name;

        public SQLNameExpr(ISQLExpr name) {
            setName(name);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
            }
        }

        public ISQLExpr getName() {
            return name;
        }

        public void setName(ISQLExpr name) {
            setChildParent(name);
            this.name = name;
        }
    }

    /**
     * LIBRARY lib_name
     */
    class SQLLibraryExpr extends AbstractSQLExpr {
        protected ISQLExpr name;

        public SQLLibraryExpr(ISQLExpr name) {
            setName(name);
        }

        @Override
        public void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
            }
        }


        public ISQLExpr getName() {
            return name;
        }

        public void setName(ISQLExpr name) {
            setChildParent(name);
            this.name = name;
        }
    }

    class SQLExternalParameter extends AbstractSQLExpr {

        protected ISQLName name;
        protected SQLExternalParameterProperty property;
        protected boolean byReference;
        protected ISQLDataType dataType;

        @Override
        protected void accept0(ISQLASTVisitor visitor) {
            if (visitor.visit(this)) {
                this.acceptChild(visitor, name);
                this.acceptChild(visitor, dataType);
            }
        }

        @Override
        public SQLExternalParameter clone() {
            SQLExternalParameter x= new SQLExternalParameter();

            x.setProperty(this.property);
            x.setByReference(this.byReference);

            if (this.dataType != null) {
                ISQLDataType dataTypeClone = this.dataType.clone();
                x.setDataType(dataTypeClone);
            }

            return x;
        }

        public ISQLName getName() {
            return name;
        }

        public void setName(ISQLName name) {
            setChildParent(name);
            this.name = name;
        }

        public SQLExternalParameterProperty getProperty() {
            return property;
        }

        public void setProperty(SQLExternalParameterProperty property) {
            this.property = property;
        }

        public boolean isByReference() {
            return byReference;
        }

        public void setByReference(boolean byReference) {
            this.byReference = byReference;
        }

        public ISQLDataType getDataType() {
            return dataType;
        }

        public void setDataType(ISQLDataType dataType) {
            setChildParent(dataType);
            this.dataType = dataType;
        }

    }



     enum SQLExternalParameterProperty implements ISQLASTEnum {

        TDO("tdo", "TDO"),
        INDICATOR("indicator", "INDICATOR"),
        INDICATOR_STRUCT("indicator struct", "INDICATOR STRUCT"),
        INDICATOR_TDO("indicator tdo", "INDICATOR TDO"),
        LENGTH("length", "LENGTH"),
        DURATION("duration", "DURATION"),
        MAXLEN("maxlen", "MAXLEN"),
        CHARSETID("charsetid", "CHARSETID"),
        CHARSETFORM("charsetform", "CHARSETFORM"),;

         public final String lower;
         public final String upper;


         SQLExternalParameterProperty(String lower, String upper) {
             this.lower = lower;
             this.upper = upper;
         }

         @Override
         public String toString() {
             return upper;
         }

         @Override
         public String lower() {
             return lower;
         }

         @Override
         public String upper() {
             return upper;
         }
    }


}
