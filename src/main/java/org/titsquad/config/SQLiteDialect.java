package org.titsquad.config;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;
import org.hibernate.dialect.pagination.LimitHandler;
import org.hibernate.dialect.pagination.LimitOffsetLimitHandler;
import java.sql.Types;

public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        // Конструктор по умолчанию
    }

    @Override
    protected String columnType(int sqlTypeCode) {
        // Кастомные маппинги типов для SQLite
        switch (sqlTypeCode) {
            case Types.BIT:
            case Types.BOOLEAN:
                return "integer"; // SQLite хранит boolean как integer (0/1)
            case Types.TINYINT:
                return "tinyint";
            case Types.SMALLINT:
                return "smallint";
            case Types.INTEGER:
                return "integer";
            case Types.BIGINT:
                return "bigint";
            case Types.FLOAT:
                return "float";
            case Types.REAL:
                return "real";
            case Types.DOUBLE:
                return "double";
            case Types.NUMERIC:
            case Types.DECIMAL:
                return "decimal";
            case Types.CHAR:
                return "char";
            case Types.VARCHAR:
                return "varchar";
            case Types.LONGVARCHAR:
                return "text";
            case Types.DATE:
                return "date";
            case Types.TIME:
                return "time";
            case Types.TIMESTAMP:
                return "timestamp";
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
            case Types.BLOB:
                return "blob";
            case Types.CLOB:
                return "clob";
            default:
                return super.columnType(sqlTypeCode);
        }
    }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new IdentityColumnSupportImpl() {
            @Override
            public boolean supportsIdentityColumns() {
                return true;
            }

            @Override
            public String getIdentityColumnString(int type) {
                return "integer primary key autoincrement";
            }
        };
    }

    @Override
    public LimitHandler getLimitHandler() {
        return LimitOffsetLimitHandler.INSTANCE;
    }
}