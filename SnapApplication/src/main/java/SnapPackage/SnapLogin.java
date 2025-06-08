package SnapPackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SnapLogin extends HttpServlet {
  public void service(HttpServletRequest request,HttpServletResponse response) throws IOException{
	  String emailId = request.getParameter("mail");
	  String passId = request.getParameter("pword");
	  
	  String url = "jdbc:mysql://localhost:3306/snap_schema";
	  String user = "root";
	  String password = "2003";
	  try {
		  Class.forName("com.mysql.cj.jdbc.Driver");
		  Connection conn = DriverManager.getConnection(url,user,password);
		  String select11 = "SELECT * FROM snapdb WHERE mail_id = ? AND pass_id = ?";
		  PreparedStatement pselect = conn.prepareStatement(select11);
		  pselect.setString(1,emailId);
		  pselect.setString(2,passId);
		  ResultSet org = pselect.executeQuery();
		  if(org.next()) {
			  PrintWriter ni = response.getWriter();
			  ni.println("Welcome to SnapChat");
		  }
		
		  pselect.close();
		  conn.close();
		  
	  }
	  catch(Exception e) {
		  e.printStackTrace();
		  response.getWriter().println("Login"+e.getMessage());
	  }
  }
}
