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
package com.gumihoy.sql.basic.ast.expr.table.element.constraint;

import com.gumihoy.sql.basic.ast.expr.AbstractSQLExpr;
import com.gumihoy.sql.basic.ast.expr.ISQLExpr;
import com.gumihoy.sql.basic.ast.expr.identifier.ISQLName;
import com.gumihoy.sql.basic.ast.expr.table.element.constraint.option.ISQLConstraintState;

import java.util.ArrayList;
import java.util.List;

/**
 * [ CONSTRAINT constraint_name ]
 * <p>
 * https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/constraint.html#GUID-1055EA97-BA6F-4764-A15F-1024FD5B6DFE
 *
 * @author kent onCondition 2018/3/13.
 */
public abstract class AbstractSQLConstraint extends AbstractSQLExpr implements ISQLConstraint {

    protected boolean constraint;
    protected ISQLName name;

    protected final List<ISQLExpr> options = new ArrayList<>();

    public AbstractSQLConstraint() {
    }

    public AbstractSQLConstraint(ISQLName name) {
        setName(name);
    }


    @Override
    public AbstractSQLConstraint clone() {
        throw new UnsupportedOperationException(this.getClass().getName());
    }

    public void cloneTo(AbstractSQLConstraint x) {
        super.cloneTo(x);

        if (this.name != null) {
            ISQLName nameClone = this.name.clone();
            x.setName(nameClone);
        }

        for (ISQLExpr option : options) {
            ISQLExpr optionClone = option.clone();
            x.addOption(optionClone);
        }
    }

    @Override
    public boolean replace(ISQLExpr source, ISQLExpr target) {

        if (target == null) {
            if (source == name) {
                setName(null);
                return true;
            }

            boolean replace = replaceInList(options, source, null, this);
            if (replace) {
                return true;
            }

            return false;
        }

        if (source == name
                && target instanceof ISQLName) {
            setName((ISQLName) target);
            return true;
        }

        if (target instanceof ISQLConstraintState) {
            boolean replace = replaceInList(options, source, (ISQLConstraintState) target, this);
            if (replace) {
                return true;
            }
        }

        return false;
    }

    public boolean isConstraint() {
        return constraint;
    }

    public void setConstraint(boolean constraint) {
        this.constraint = constraint;
    }

    public ISQLName getName() {
        return name;
    }

    public String getConstraintName() {
        if (name != null) {
            return name.getSimpleName();
        }
        return null;
    }

    public void setName(ISQLName name) {
        setChildParent(name);
        this.name = name;
        this.constraint = true;
    }

    public List<ISQLExpr> getOptions() {
        return options;
    }

    public void addOption(ISQLExpr option) {
        if (option == null) {
            return;
        }
        setChildParent(option);
        this.options.add(option);
    }

    public void addAllOption(List<ISQLConstraintState> options) {
        if (options == null
                || options.size() == 0) {
            return;
        }
        for (ISQLConstraintState option : options) {
            setChildParent(option);
        }
        this.options.addAll(options);
    }
}
