

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


@WebServlet("/SearchAllCourseInstructor")
public class SearchAllCourseInstructor extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SearchAllCourseInstructor() {
        super();      
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try (
			    Connection conn = DriverManager.getConnection(
			    		"jdbc:postgresql://localhost:5990/postgres", "deepak", "");
			    Statement stmt = conn.createStatement();
			){
			HttpSession session = request.getSession();
			String role = (String) session.getAttribute("role");
			
			if(role == null) {
				response.sendRedirect("Login");
				return;
			}
			if(role.equals("s"))
			{
				response.sendRedirect("SearchAllCourses");
				return;
			}
			PrintWriter out = response.getWriter();
		
			out.println("<!DOCTYPE html>\n" + 
					"<html lang=\"en\">\n" + 
					"<head>\n" + 
					"	<title>Feedback App</title>\n" + 
					"	<meta charset=\"UTF-8\">\n" + 
					"	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" + 
					"<!--===============================================================================================-->	\n" + 
					"	<link rel=\"icon\" type=\"image/png\" href=\"images/icons/favicon1.ico\"/>\n" + 
					"<!--===============================================================================================-->\n" + 
					"\n" + 
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"fonts/font-awesome-4.7.0/css/font-awesome.min.css\">\n" + 
					"<!--===============================================================================================-->\n" +  
					"\n" + 
					"<!--===============================================================================================-->\n" + 
				
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/main.css\">\n" + 
					"<!--===============================================================================================-->\n" + 
					"</head>\n" + 
					"<body>\n" + 
					"<div id=\"home\">\n" + 
					"  <button class=\"tablinks\" onclick=\"location.href='http://localhost:8080/dbproject/InstructorView'\">Home</button>\n" + 
					"  <button class=\"tablinks\" onclick=\"location.href='http://localhost:8080/dbproject/SearchAllCourseInstructor'\">View Feedback</button>  \n" + 
					"</div>\n"+ 
					"	\n" + 
					"	<div class=\"limiter\">\n" + 
					"		 <div class=\"container-login100\"  > \n" + 
					"			<div class=\"wrap-login100\">\n" + 
					"				<form class=\"login100-form validate-form\" action=\"SearchAllCourseInstructor \" method=\"post\">\n" + 
					"					\n" + 
					"\n" + 
					"					<span class=\"login100-form-title p-b-34 p-t-27\">\n" + 
					"						Search Feedback \n" + 
					"					</span>\n" + 
					"\n" + 
					"					<div class=\"wrap-input100 validate-input\" data-validate = \"Enter username\"  >\n" + 
					"						<input class=\"input100\" type=\"text\" name = \"section_input\" placeholder=\"Section\">\n" + 
					"						\n" + 
					"					</div>\n" + 
					"\n" + 
					"					<div class=\"wrap-input100 validate-input\" data-validate = \"Enter username\" > \n" + 
					"						<input class=\"input100\" type=\"text\" name = \"semester_input\" placeholder=\"Semester\">\n" + 
					"						\n" + 
					"					</div>\n" + 
					"\n" + 
					"					<div class=\"wrap-input100 validate-input\" data-validate = \"Enter username\" > \n" + 
					"						<input class=\"input100\" type=\"text\" name = \"year_input\"placeholder=\"Year\">\n" + 
					"						\n" + 
					"					</div>\n" + 
					"\n" + 
					"					\n" + 
					"\n" + 
					 
					"\n" + 
					"<input class=\"login100-form-btn\"type=\"submit\" value = \"Submit\">				</form>\n" + 
					"			</div>\n" + 
					"		</div>\n" + 
					"	</div>\n" + 
					"	\n" + 
					"\n" +"<br><br><br><br><div class=\"navbar\" id=\"myNavbar\">\n" + 
					"    <a href=\"#home\" >Back To Top</a>\n" + 
					"    \n" + 
					"    <a href=\"http://localhost:8080/dbproject/Logout \" >Logout</a>\n" + 
					"\n" + 
					"</div></body></html>");
		}
		catch(Exception sqle) {
			System.out.println("Error is: " + sqle);
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try (
			    Connection conn = DriverManager.getConnection(
			    		"jdbc:postgresql://localhost:5990/postgres", "deepak", "");
			    Statement stmt = conn.createStatement();
			){
			
			PrintWriter out = response.getWriter();
			String section_input = "%" + request.getParameter("section_input") + "%";
			String semester_input = "%" + request.getParameter("semester_input") + "%";
			String year_input = "%" + request.getParameter("year_input") + "%";
			String course_input = request.getParameter("course_input");
			
//			int year_input_int = Integer.parseInt(year_input);
//			System.out.println("anshu" + section_input + "rtyui" + semester_input + "rtyu" + year_input);
			PreparedStatement pstmt = conn.prepareStatement("select * from section where sec_id like ? and semester like ?"
					+ " and cast(year as VARCHAR(9)) like ?  ");
			pstmt.setString(1, section_input);
			pstmt.setString(2, semester_input);
			pstmt.setString(3, year_input);
			
			
			ResultSet rset = pstmt.executeQuery();
			
			out.println("<!DOCTYPE html>\n" + 
					"<html>\n" + 
					"<head>\n" + 
					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" + 
					"<link rel=\"icon\" type=\"image/png\" href=\"images/icons/favicon1.ico\"/>\n" + 
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"css/main.css\"/>\n" + 
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"css/util.css\"/>\n" + 
					"</head>\n" + 
					"\n" + 
					"<div id=\"home\">\n" + 
					"  <button class=\"tablinks\" onclick=\"location.href='http://localhost:8080/dbproject/InstructorView'\">Give Feedback</button>\n" + 
					"  <button class=\"tablinks\" onclick=\"location.href='http://localhost:8080/dbproject/SearchAllCourseInstructor'\">View Feedback</button>  \n" + 
					"</div>\n"
					+
					"<br><br><br><br><center><table id=\"customers\" \n" + 
					"  <tr> \n" + 
					"    <th> Course ID </th> <th> Section ID </th> <th> Semester </th> <th> Year </th>  <th>View Feedback </th>\n" + 
					"  </tr>");
			
			while(rset.next()) {
				
				out.println("<tr> <td>" +  rset.getString(1) + " </td> <td>" +
						rset.getString(2)+ "  </td> <td>" + rset.getString(3) +
						" </td> <td> " + rset.getInt(4)+ "  </td> <td>");
						
				out.print("<a href = \"http://localhost:8080/dbproject/ViewFeedbackInstructor?"
						+ "course_name="+ 
				rset.getString(1) +
				"&sec_id=" + rset.getString(2) + "&semester=" + rset.getString(3) + "&year=" + rset.getInt(4) +
				" \"> "+ "View Feedback" + "</a></td>");
						out.print( "</tr> ");
					}
					out.println("</table></center> <br><br><br><br><br><br> </html>");
					
					out.println("<div class=\"navbar\" id=\"myNavbar\">\n" + 
							"    <a href=\"#home\" >Back To Top</a>\n" + 
							"    \n" + 
							"    <a href=\"http://localhost:8080/dbproject/Logout \" >Logout</a>\n" + 
							"\n" + 
							"</div></body></html>");
			
		}
		
		catch(Exception sqle) {
			System.out.println("Error is: " + sqle);
		}
	}

}
