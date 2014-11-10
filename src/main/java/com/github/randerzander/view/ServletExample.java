package com.github.randerzander.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@RequestMapping("/hive")
public class ServletExample extends HttpServlet {

    private final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    private DataSource dataSource;

    @RequestMapping(value = "/tables", method = RequestMethod.GET)
    public void showTables() {
        Connection connection = null;
        Statement stmt = null;
        try {
            final String sql = "show tables";
            connection = dataSource.getConnection();
            stmt = connection.createStatement();

            log.debug("Running: " + sql);

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                log.debug(res.getString(1));
            }


        } catch (SQLException e) {
            log.error("error", e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
