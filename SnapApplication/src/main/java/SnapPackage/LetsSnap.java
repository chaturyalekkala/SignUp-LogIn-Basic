package SnapPackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LetsSnap extends HttpServlet {
 public void service(HttpServletRequest request,HttpServletResponse response) throws IOException{
	 String firstname = request.getParameter("fname");
	 String lastname = request.getParameter("lname");
	 String enterphone = request.getParameter("pnum");
	 if (enterphone == null || !enterphone.matches("\\d{10}")) {
		 PrintWriter out = response.getWriter();
		    out.println("Phone number must be exactly 10 digits");
		}
	 String emailId = request.getParameter("mail");
	 if (emailId == null || !emailId.contains("@")) {
		 PrintWriter in = response.getWriter();
		    in.println("Email must contain '@'.");
		}
	 String passId = request.getParameter("pword");
	 String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).+$";
	 if (passId == null || !passId.matches(passwordPattern)) {
		 PrintWriter no = response.getWriter();
	     no.println("Password must have at least 1 uppercase, 1 lowercase, and 1 special character");
	 }
	 
	 String url = "jdbc:mysql://localhost:3306/snap_schema";
	 String user = "root";
	 String password ="2003";
	 
	 try {
		 Class.forName("com.mysql.cj.jdbc.Driver");
		 Connection conn = DriverManager.getConnection(url,user,password);
		 String create = "CREATE TABLE IF NOT EXISTS snapdb("+
		                 "first_name  VARCHAR(500),"+
				         "last_name   VARCHAR(500),"+
		                 "ph_no VARCHAR(10) PRIMARY KEY,"+
				         "mail_id   VARCHAR(500),"+
		                 "pass_id    VARCHAR(100))";
		 PreparedStatement pcreate = conn.prepareStatement(create);
		 pcreate.executeUpdate();
		
	

		 
		 String insert = "INSERT INTO snapdb(first_name,last_name,ph_no,mail_id,pass_id) VALUES(?,?,?,?,?)";
		 PreparedStatement pinsert = conn.prepareStatement(insert);
		 pinsert.setString(1,firstname);
		 pinsert.setString(2,lastname);
		 pinsert.setString(3,enterphone);
		 pinsert.setString(4,emailId);
		 pinsert.setString(5,passId);
		 pinsert.executeUpdate();
		 response.getWriter().println("Account created");
		 
		 
//		   RequestDispatcher dispatcher=request.getRequestDispatcher("login.html");
//		    dispatcher.forward(request, response);
		 RequestDispatcher dispatcher=request.getRequestDispatcher("SignUpSuccessful.html");
		    dispatcher.forward(request, response);
		
		
		 pcreate.close();
		pinsert.close();
		conn.close();

		
		 
	 }
	 catch(Exception e) {
		 e.printStackTrace();
		 response.getWriter().println("Table"+e.getMessage());
	 }
	 

 }

}
