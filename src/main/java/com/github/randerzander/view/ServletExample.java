package com.github.randerzander.view;

import org.apache.ambari.view.ViewContext;
import org.apache.hive.jdbc.HiveDriver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import java.lang.String;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;

public class ServletExample extends HttpServlet {

  private ViewContext viewContext;
  Connection con;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    ServletContext context = config.getServletContext();
    viewContext = (ViewContext) context.getAttribute(ViewContext.CONTEXT_ATTRIBUTE);

    try {
       Class.forName("org.apache.hive.jdbc.HiveDriver");
    }
    catch(ClassNotFoundException ex) {
       System.out.println("Error: unable to load driver class!");
       System.exit(1);
    }
    String host = "n0.dev:10000";
    try {
      con = DriverManager.getConnection("jdbc:hive2://"+host+"/default", "", "");
    }catch(SQLException e){ e.printStackTrace(); }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    PrintWriter writer = response.getWriter();
    String uri = request.getRequestURI();
    if (!uri.contains("/services") && !uri.contains("/ui/")){
      //Forward requests for UI files to the default servlet mapping
      try {
        request.getRequestDispatcher("ui").forward(request, response);
      }catch(ServletException e){ e.printStackTrace(); }
    } else {
      try{
        Statement stmt = con.createStatement();
        String sql = "show tables";
        writer.println("Running: " + sql);
        ResultSet res = stmt.executeQuery(sql);
        while (res.next()){
          writer.println(res.getString(1));
        }
      }catch(SQLException e){ e.printStackTrace(); }
      writer.println(uri + " was a services req");
    }
  }
}
