import java.io.PrintWriter;
import java.sql.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/DisplayGrades")
public class DisplayGrades extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DisplayGrades() {
        super();        
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		if(session.isNew())
		{
			response.sendRedirect("Login");
		}
		
		String id = (String) session.getAttribute("uid");
		String role = (String) session.getAttribute("role");
		
		if(role == null) {
			response.sendRedirect("Login");
			return;
		}
		if(role.equals("i"))
		{
			response.sendRedirect("InstructorView");
			return;
		}

		try (
			    Connection conn = DriverManager.getConnection(
			    		"jdbc:postgresql://localhost:5990/postgres", "deepak", "");
			    Statement stmt = conn.createStatement();
			)
		{
			
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
					"  <button class=\"tablinks\" onclick=\"location.href='http://localhost:8080/dbproject/DisplayGrades'\">Give Feedback</button>\n" + 
					"  <button class=\"tablinks\" onclick=\"location.href='http://localhost:8080/dbproject/searchAllCourses'\">View Feedback</button>  \n" + 
					"</div>\n" + 
					"\n" + 
					"\n" + 
					"<br><br><br><br>"+
					"<center>"+
					"<table id=\"customers\" >\n" + 
					"  <tr> \n" + 
					"    <th> ID </th> <th> Course ID </th> <th> Section ID </th> <th> Semester </th> <th> Year </th>"
					+ " <th> Grade </th><th> Status </th><th> View Feedback </th> \n" + 
					"  </tr>\n" );
			
			
			
			PreparedStatement ps_getval = conn.prepareStatement("select * from current");
			ResultSet values = ps_getval.executeQuery();
			
			String get_sec = "";
			String get_sem = "";
			int get_year = 0;
			
			
			if(values.next()) {
				get_sec = values.getString(1);
				get_sem = values.getString(2);
				get_year = values.getInt(3);
			}
			
			//out.println(get_sec + get_sem +get_year);
			
			PreparedStatement pstm = conn.prepareStatement("select * from takes where ID = ? and  sec_id=? and semester=? and year=? ");
			pstm.setString(1,id);
			pstm.setString(2, get_sec);
			pstm.setString(3, get_sem);
			pstm.setInt(4,get_year);
			
			ResultSet rset = pstm.executeQuery();
			//out.println("<table <tr> <th> ID </th> <th> course_id </th> <th> sec_id </th> <th> semester </th> <th> year </th> <th> grade </th> </tr>");
				while(rset.next()) {
			
				out.println("<tr> <td>" + rset.getString(1) + "</td>  <td>" + rset.getString(2) + " </td> <td>" +
				rset.getString(3)+ "  </td> <td>" + rset.getString(4) +
				" </td> <td> " + rset.getInt(5)+ "  </td> <td>" + rset.getString(6)+ "</td> <td> ");
				
				if(rset.getInt(7) == 0) {
					out.print("<a href = \"http://localhost:8080/dbproject/GiveFeedback?course_name="+ rset.getString(2) +
//							rset.getString(1) +
//							"&sec_id=" + rset.getString(3) + "&semester=" + rset.getString(4) + "&year=" + rset.getInt(5) +
							" \"> "+ "Give Feedback" + "</a> </td>");
				}
				else {
					out.print("Feedback already given </td>");
				}
				
				out.print("<td><a href = \"http://localhost:8080/dbproject/ViewFeedback?course_name="+ rset.getString(2) +
//						rset.getString(1) +
						"&sec_id=" + rset.getString(3) + "&semester=" + rset.getString(4) + "&year=" + rset.getInt(5) +
				" \"> "+ "View Feedback" + "</a> </td>");

				
			
				out.print( "</tr> ");
				
			}
			out.println("</table></center> <br><br><br><br><br> ");
			
			
			
//			out.println("<html><h2> Hello " + id + " </h2> " + "<form action=\"GiveFeedback \" method=\"get\"> Choose the subject u want to give feedback for : <input type=\"text\" name = \"course_name\" value = \""+
//			request.getParameter("temp")+"\"> <br>  <br> <input type=\"submit\" value = \"Submit\"> </form> </html>");
//			String selected_course = request.getParameter("temp");
//			out.println("You have choosen "+ selected_course);
//			session.setAttribute("selected_course", selected_course);
			
			
		
	
			//out.println("<html> <a href = \"http://localhost:8080/dbproject/searchAllCourses \"> Seach all courses </a> <br> ");
			//out.println("<html> <a href = \"http://localhost:8080/dbproject/Logout \"> Logout </a> </html> ");
			out.println("<div class=\"navbar\" id=\"myNavbar\">\n" + 
					"    <a href=\"#home\" >Back To Top</a>\n" + 
					"    \n" + 
					"    <a href=\"http://localhost:8080/dbproject/Logout \" >Logout</a>\n" + 
					"\n" + 
					"</div></body></html>");
		}
		catch (Exception sqle)
		{
		System.out.println("Exception : " + sqle);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request,response);
	}
}