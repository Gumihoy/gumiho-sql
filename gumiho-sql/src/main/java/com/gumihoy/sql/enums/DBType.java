package com.gumihoy.sql.enums;

/**
 * @author kent on 2019-06-13.
 */
public enum DBType {

    SQL {
    },

    /**
     *
     */
    Oracle {
    },

    /**
     *
     */
    MySQL {

    },


    /**
     *
     */
    MariaDB {

    },

    /**
     *
     */
    EDB {
    },

    /**
     *
     */
    PostgreSQL {
    },

    /**
     * https://gpdb.docs.pivotal.io/43330/ref_guide/sql_commands/sql_ref.html
     */
    GPDB {
    },

    ;
    public DBVersion version;

    DBType() {
    }

    public void version(DBVersion version) {
        this.version = version;
    }

    public static void main(String[] args) {
        DBType type = DBType.Oracle;
    }

}
