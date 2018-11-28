


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/ViewFeedbackInstructor")
public class ViewFeedbackInstructor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ViewFeedbackInstructor() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
	   
	    HttpSession session = request.getSession();
		if(session.isNew())
		{
			response.sendRedirect("Login");
		}
		
		PrintWriter out = response.getWriter();
		String id = (String) session.getAttribute("uid");
		String selected_course = request.getParameter("course_name");
		String role = (String) session.getAttribute("role");
		
		if(role == null) {
			response.sendRedirect("Login");
			return;
		}
		if(role.equals("s")) {
			response.sendRedirect("ViewFeedback");
			return;
		}
		try (
			    Connection conn = DriverManager.getConnection(
			    		"jdbc:postgresql://localhost:5990/postgres", "deepak", "");
			    Statement stmt = conn.createStatement();
			)
		{
			String get_sec = (String) request.getParameter("sec_id");
			String get_sem = (String) request.getParameter("semester");
			String get_Year = (String) request.getParameter("year");
			
			int get_year = Integer.parseInt(get_Year);
			
			PreparedStatement ps1 = conn.prepareStatement("select f.que_id10, f.view_permission, f.feedback_id from \n" + 
					"feedback  natural join feedback_value as f   where feedback.course_id=? and sec_id=? and semester=? and year=?");
			ps1.setString(1, selected_course);
			ps1.setString(2,get_sec);
			ps1.setString(3,  get_sem);
			ps1.setInt(4, get_year);
			ResultSet rs1 = ps1.executeQuery();
			
			out.println("<!DOCTYPE html>\n" + 
					"<html>\n" + 
					"<head>\n" + 
					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" + 
					"<link rel=\"icon\" type=\"image/png\" href=\"images/icons/favicon1.ico\"/>\n" + 
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"css/main.css\"/>\n" + 
					"\n" + 
					
			
					"</head>\n" + 
					"<body>\n" + 
					"\n" + 
					"<div id=\"home\">\n" + 
					"  <button class=\"tablinks\" onclick=\"location.href='http://localhost:8080/dbproject/InstructorView'\">Home</button>\n" + 
					"  <button class=\"tablinks\" onclick=\"location.href='http://localhost:8080/dbproject/SearchAllCourseInstructor'\">"
					+ "View Feedback</button>  \n" + 
					"</div>\n");
			//out.println("<html><center> <h1><u> Feedbacks </u></h1>");
			
			
			PreparedStatement ps2 = conn.prepareStatement("select avg(f.que_id1),avg(f.que_id2),avg(f.que_id3),"
					+ "avg(f.que_id4),avg(f.que_id5) from feedback  natural join feedback_value as f "
					+ "where feedback.course_id=? ;");
			
			ps2.setString(1, selected_course);
			ResultSet rs2 = ps2.executeQuery();
			
			String s1 = "", s2 = "", s3 = "", s4 = "", s5 = "";
			
			if(rs2.next())
			{
					s1 = rs2.getString(1);
					s2 = rs2.getString(2);
					s3 = rs2.getString(3);
					s4 = rs2.getString(4);
					s5 = rs2.getString(5);
					if(s1 != null) {
						s1 = s1.substring(0, Math.min(s1.length(), 4));
						s2 = s2.substring(0, Math.min(s2.length(), 4));
						s3 = s3.substring(0, Math.min(s3.length(), 4));
						s4 = s4.substring(0, Math.min(s4.length(), 4));
						s5 = s5.substring(0, Math.min(s5.length(), 4));					
					}
			
					else {
						s1 = "-";
						s2 = "-";
						s3 = "-";
						s4 = "-";
						s5 = "-";
					}
			}
				
			out.println("<!DOCTYPE html>\n" + 
					"<html>\n" + 
					"<head>\n" + 
					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" + 
					"<link rel=\"icon\" type=\"image/png\" href=\"images/icons/favicon1.ico\"/>\n" + 
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"css/main.css\"/>\n" + 
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"css/util.css\"/>\n" + 
					"</head>\n" + 
					"<center>\n" + 
					"<table id=\"customers\" >\n" + 
					"  	<tr> \n" + 
					"    	<th> Quesion</q></th> <th> Average Score </th>\n" + 
					"  	</tr>   \n" + 
					"  	<tr>\n" + 
					"    	<td>How was the Instructors' Teaching(involes punctuality "
					+ ",course completion,class involvment)</td><td> "+ s1 + " </td>\n" + 
					"	</tr>\n" + 
					"	<tr>\n" + 
					"    	<td>Course difficulty level</td><td>"+ s2 +"</td>\n" + 
					"	</tr>\n" + 
					"	<tr>\n" + 
					"    	<td>Examination difficulty level</td><td> " + s3+ "</td>\n" + 
					"	</tr>\n" + 
					"	<tr>\n" + 
					"    	<td>Assignment difficulty level:</td><td>"+ s4 + "</td>\n" + 
					"	</tr>\n" + 
					"	<tr>\n" + 
					"    	<td>How was the course in overall?</td><td> "+ s5+ "</td>\n" + 
					"	</tr>\n" + 
					"</table>\n" + 
					"</center>\n" + 
					"\n"
				); 
			out.println(
					"<br><br><br>\n" + 
					"<table id=\"customers\" >\n" + 
					"  	<tr> \n" + 
					"    	<th> ID</q></th> <th> Feedback Text </th>\n" + 
					"  	</tr>   \n" );	
				
			System.out.println(get_sec + " anshu " + get_sem + " " + get_year);
			while(rs1.next())
			{
				
				int permission = rs1.getInt(2);
				int get_feedback_id = rs1.getInt(3);
				
				
				PreparedStatement search = conn.prepareStatement("select id from feedback where feedback_id = ?");
				
				search.setInt(1,get_feedback_id);
				
				ResultSet value_id = search.executeQuery();
				
				String get_id = "";
				while(value_id.next()) {
					get_id = value_id.getString(1);
				}
				
				if(permission == 1) {
					out.println("<tr> <td>" +get_id+ "</td> <td>"+   rs1.getString(1) + "</td></tr>" );
				}
				else {
					out.println("<tr> <td>" +"Posted anonymously"+ "</td> <td>"+   rs1.getString(1) + "</td></tr>" );
					
				}
			}
			out.println("</table> <br><br><br><br><br>");
			
			//out.println("<html> <a href = \"http://localhost:8080/dbproject/Logout \"> Logout </a> </center></html> ");
			out.println("<div class=\"navbar\" id=\"myNavbar\">\n" + 
					"    <a href=\"#home\" >Back To Top</a>\n" + 
					"    \n" + 
					"    <a href=\"http://localhost:8080/dbproject/Logout \" >Logout</a>\n" + 
					"\n" + 
					"</div></body></html>");
		} catch (Exception sqle) {
			System.out.println("Exception : " + sqle);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

