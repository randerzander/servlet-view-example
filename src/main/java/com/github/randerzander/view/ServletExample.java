package com.github.randerzander.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
@RequestMapping("/hive")
public class ServletExample extends HttpServlet {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataSource dataSource;

    @RequestMapping(value = "/tables", method = RequestMethod.GET)
    public void showTables() {
        queryTables();
    }

    private void queryTables() {
        final String sql = "show tables";

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()
        ) {
            log.debug("Running: " + sql);

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                log.debug(rs.getString(1));
            }

        } catch (SQLException e) {
            log.error("error", e);
        }
    }

}
