package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner query_runner = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }

    @SneakyThrows
    public static String getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            return query_runner.query(conn, codeSQL, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static void cleanDataBase() {
        try (var conn = getConn()) {
            query_runner.execute(conn,"DELETE FROM auth_codes");
            query_runner.execute(conn,"DELETE FROM card_transactions");
            query_runner.execute(conn,"DELETE FROM cards");
            query_runner.execute(conn,"DELETE FROM users");
        }
    }

    @SneakyThrows
    public static void cleanAuthCodes() {
        try (var conn = getConn()) {
            query_runner.execute(conn,"DELETE FROM auth_codes");
        }
    }
}