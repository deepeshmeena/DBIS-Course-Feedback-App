

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class InstructorView
 */
@WebServlet("/InstructorView")
public class InstructorView extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InstructorView() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		if(session.isNew()){
			response.sendRedirect("Login");
		}
		
		String id = (String) session.getAttribute("uid");
		String role = (String) session.getAttribute("role");
		
		if(role == null) {
			response.sendRedirect("Login");
			return;
		}	
		if(role.equals("s"))
		{
			response.sendRedirect("DisplayGrades");
			return;
		}
		
		try (
			    Connection conn = DriverManager.getConnection(
			    		"jdbc:postgresql://localhost:5990/postgres", "deepak", "");
			    Statement stmt = conn.createStatement();
			)
		{

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
			
			
			PreparedStatement pstm = conn.prepareStatement("select * from teaches where ID = ? and semester=? and year=?  ");
			pstm.setString(1,id);
			pstm.setString(2, get_sem);
			pstm.setInt(3,get_year);
			
			ResultSet rset = pstm.executeQuery();
			
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
					"  <button class=\"tablinks\" onclick=\"location.href='http://localhost:8080/dbproject/SearchAllCourseInstructor'\">View Feedback</button>  \n" + 
					"</div>\n" + 
					"\n" + 
					"\n" + 
					"<br><br><br><br>"+
					"<center>"+
					"<table id=\"customers\" >\n" + 
					"  <tr> \n" + 
					"    <th> ID </th> <th> Course ID </th> <th> Section ID </th> <th> Semester </th> <th> Year </th>"
					+ "<th> View Feedback </th> \n" + 
					"  </tr>\n" );
			//out.println("<table <tr> <th> ID </th> <th> course_id </th> <th> sec_id </th> <th> semester </th> <th> year </th><th> Feedback status </th>  </tr>");
			while(rset.next()) {
				out.println("<tr> <td>" + rset.getString(1) + "</td>  <td>" + rset.getString(2) + " </td> <td>" +
				rset.getString(3)+ "  </td> <td>" + rset.getString(4) +
				" </td> <td> " + rset.getInt(5)+ "  </td> " + 
				"<td><a href = \"http://localhost:8080/dbproject/ViewFeedbackInstructor?course_name=" +
				rset.getString(2) + "&sec_id=" + rset.getString(3) + "&semester=" + rset.getString(4) +
				"&year=" + rset.getInt(5) +
				" \"> "+ "View Feedback" + "</a></td>"
						);
				
				out.print("</tr> ");
				

				
				//				if(rset.getInt(7) == 0) {
//					out.print("<a href = \"http://localhost:8080/dbproject/GiveFeedback?course_name="+ rset.getString(2) +
//							" \"> "+ "Give Feedback" + "</a>");
//				}
//				else {
//					out.print("Feedback already given");
				
//				(rset.getString(7).toString() == "0") != null?
//				 : 
			
				
			}
			out.println("</table> <br><br>");
			
			
//			out.println("<html><h2> Hello " + id + " </h2> " + "<form action=\"GiveFeedback \" method=\"get\"> Choose the subject u want to give feedback for : <input type=\"text\" name = \"course_name\" value = \""+
//			request.getParameter("temp")+"\"> <br>  <br> <input type=\"submit\" value = \"Submit\"> </form> </html>");
//			String selected_course = request.getParameter("temp");
//			out.println("You have choosen "+ selected_course);
//			session.setAttribute("selected_course", selected_course);
			
			
		
	
		
			
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