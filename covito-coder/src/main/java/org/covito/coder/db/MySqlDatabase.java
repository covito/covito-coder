package org.covito.coder.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.covito.coder.config.TypeMapping;
import org.covito.coder.db.model.Table;

public class MySqlDatabase extends DefaultDatabase {

    private static final String TABLE_COMMENTS_SQL  = "show table status where NAME=?";

    public MySqlDatabase(Connection connection, TypeMapping typeMapping){
        super(connection, typeMapping);
    }

    @Override
    public Table getTable(String catalog, String schema, String tableName) {
        Table table = super.getTable(catalog, schema, tableName);
        if (table != null) {
            introspectTableComments(table);
        }
        return table;
    }

    public void introspectTableComments(Table table) {
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            psmt = connection.prepareStatement(TABLE_COMMENTS_SQL);
            psmt.setString(1, table.getTableName());
            rs = psmt.executeQuery();
            if (rs.next()) {
                table.setRemarks(rs.getString("COMMENT"));
            }
        } catch (SQLException e) {

        } finally {
            close(rs);
            close(psmt);
        }
    }
}
