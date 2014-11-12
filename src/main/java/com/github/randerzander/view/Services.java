package com.github.randerzander.view;

import org.apache.ambari.view.ViewContext;

import java.io.BufferedReader;
import org.json.JSONObject;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class Services extends HttpServlet {

    private ViewContext viewContext;
    private Connection connection;

    @Override
    public void init(ServletConfig config) throws ServletException {
      super.init(config);

      ServletContext context = config.getServletContext();
      viewContext = (ViewContext) context.getAttribute(ViewContext.CONTEXT_ATTRIBUTE);

      try {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
      } catch (ClassNotFoundException ex) {
        System.out.println("Error: unable to load driver class!");
        System.exit(1);
      }
      String host = "n0.dev:10000";
      try {
        connection = DriverManager.getConnection("jdbc:hive2://" + host + "/default", "", "");
      } catch (SQLException e) { e.printStackTrace(); }
    }

    private String concatWS(String separator, String[] tokens){
      StringBuilder ret = new StringBuilder();
      for (String token : tokens){
        if (ret.equals("")) ret.append(token);
        else {
          ret.append(separator);
          ret.append(token);
        }
      }
      return ret.toString();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      //Read request body and build Hive query
      BufferedReader reader = request.getReader();
      StringBuilder builder = new StringBuilder();
      String line;
      while( (line = reader.readLine()) != null){ builder.append(line); }
      String query = builder.toString();

      JSONObject json = new JSONObject();
      json.put("query", query);

      if (query.length() > 0 && query.charAt(query.length()-1)==';') {
        query = query.substring(0, query.length()-1);
      }

      PrintWriter writer = response.getWriter();
      try {
        ResultSet res = connection.createStatement().executeQuery(query); //Run query
        
        //Write out column headers
        ResultSetMetaData rms = res.getMetaData();
        String[] cols = new String[rms.getColumnCount()];
        for (int i=0; i < cols.length; i++){ cols[i] = rms.getColumnName(i+1);}
        json.put("columns", cols);

        //Write out each row in the result set
        ArrayList<String[]> rows = new ArrayList<String[]>();
        String[] row = new String[cols.length];
        while (res.next()) {
          for (int i=0; i < cols.length; i++){ row[i] = res.getString(i+1); }
          rows.add(row);
        }
        json.put("result", rows);

        writer.println(json.toString());
      } catch (SQLException e) {
        json.put("error", e.toString());
        writer.println(json.toString());
      }
    }
}
