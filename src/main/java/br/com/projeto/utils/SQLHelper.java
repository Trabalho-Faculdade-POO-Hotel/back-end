package br.com.projeto.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import br.com.projeto.core.base.DAO.FilterEntry;

public class SQLHelper {

    public static String buildWhereQuery(List<FilterEntry> filter) {
        return " WHERE " + String.join(
                " AND ",
                filter.stream().map(f -> f.buildQuery()).toList());
    }

    public static void applyValuesToStatement(List<FilterEntry> values, PreparedStatement statement)
            throws SQLException {
        int index = 1;

        for (Object value : values.stream().map(f -> f.isSingleValue() ? f.getValue() : f.getValues())
                .toList()) {
            if (value instanceof List) {
                for (Object v : (List<?>) value) {
                    statement.setObject(index, v);

                    index++;
                }

                continue;
            }

            statement.setObject(index, value);

            index++;
        }
    }

}
