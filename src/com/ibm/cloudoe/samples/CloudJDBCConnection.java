package com.ibm.cloudoe.samples;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CloudJDBCConnection
 */
//@WebServlet("/CloudJDBCConnection")
public class CloudJDBCConnection extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CloudJDBCConnection() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();        
        String name=request.getParameter("uname");                          
        try{
        	out.println("Before Class.forName");
               Class.forName("com.ibm.db2.jcc.DB2Driver");
               out.println("After Class.forName");
               Connection con=DriverManager.getConnection("jdbc:db2://cap-sg-prd-5.integration.ibmcloud.com:15482/WAS","db2admin","miracle");  
               out.println(con.toString()+"Before getConnection");
               
              /* PreparedStatement ps = con.prepareStatement("INSERT INTO employee (name) values(?)");
               ps.setString(1,name);
               int i = ps.executeUpdate();
               out.println("<h1><center>Welcome</center></h1>");
               out.println(i);
               if(i!=0)
               {
               out.println("Your Data has been successfully stored in database");
               out.println("Hello <b>"+name+"</b><br>");
               out.println("Your password is "+name);
               }
               else
               {
               out.println("sorry failed to store ! try again......");
               }*/


               
               
               PreparedStatement ps=con.prepareStatement("select * from employee where name=?");
               ps.setString(1,name);                   
               out.print("<table width=25% border=1>");
               out.print("<center><h1>Result:</h1></center>");
               ResultSet rs=ps.executeQuery();                
               //Printing column names
               ResultSetMetaData rsmd=rs.getMetaData();
               out.println(rs+"resultset");

               while(rs.next())
                  {
               out.print("<tr>");
               out.print("<td>"+rsmd.getColumnName(1)+"</td>");
                  out.print("<td>"+rs.getString(1)+"</td></tr>");
                  out.print("<tr><td>"+rsmd.getColumnName(2)+"</td>");
                  out.print("<td>"+rs.getString(2)+"</td></tr>");    
               }
               out.print("</table>");

        }catch (Exception e2)
          {
              e2.printStackTrace();
          }

        finally{out.close();
          }
 }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
