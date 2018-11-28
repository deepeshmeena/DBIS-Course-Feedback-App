

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

@WebServlet("/superuser_home")
public class superuser_home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public superuser_home() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try (
			    Connection conn = DriverManager.getConnection(
			    		"jdbc:postgresql://localhost:5990/postgres", "deepak", "");
			    Statement stmt = conn.createStatement();
			){
			
			PrintWriter out = response.getWriter();
		//	out.println("<html><center> <h1> <u>Set the default values</u></h1><br>");
			//out.println("<form action=\"superuser_home \" method=\"post\"> "
				//	+ "Enter current section: <input type=\"text\" name = \"sec_inp\"> <br>"
					//+ " Enter current semester: <input type=\"text\" name = \"sem_inp\"> <br> "
					//+ "Enter current year: <input type=\"text\" name = \"year_inp\"> <br>"
					//+ " <input type=\"submit\" value = \"Submit\"> </form> </center></html>");
			
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
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"fonts/iconic/css/material-design-iconic-font.min.css\">\n" + 
					"\n" + 
					"<!--===============================================================================================-->\n" + 
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/util.css\">\n" + 
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/main.css\">\n" + 
					"<!--===============================================================================================-->\n" + 
					"</head>\n" + 
					"<body>\n" + 
					"	\n" + 
					"	\n" + 
					"	<div id=\"home\"class=\"limiter\">\n" + 
					"		 <div class=\"container-login100\"  > \n" + 
					"			<div class=\"wrap-login100\">\n" + 
					"				<form action=\"superuser_home\" method=\"Post\" class=\"login100-form validate-form\">\n" + 
					"					\n" + 
					"\n" + 
					"					<span class=\"login100-form-title p-b-34 p-t-27\">\n" + 
					"						Change Default Values\n" + 
					"					</span>\n" + 
					"\n" + 
					"					<div class=\"wrap-input100 validate-input\" data-validate = \"Enter username\">\n" + 
					"						<input class=\"input100\" type=\"text\" name=\"sec_inp\" placeholder=\"Section\">\n" + 
					"						\n" + 
					"					</div>\n" + 
					"\n" + 
					"					<div class=\"wrap-input100 validate-input\" data-validate = \"Enter username\">\n" + 
					"						<input class=\"input100\" type=\"text\" name=\"sem_inp\" placeholder=\"Semester\">\n" + 
					"						\n" + 
					"					</div>\n" + 
					"\n" + 
					"					<div class=\"wrap-input100 validate-input\" data-validate = \"Enter username\">\n" + 
					"						<input class=\"input100\" type=\"text\" name=\"year_inp\" placeholder=\"Year\">\n" + 
					"						\n" + 
					"					</div>\n" + 
					"\n" + 
					"					\n" + 
					"\n" + 
					"					<div class=\"container-login100-form-btn\">\n" + 
					"						<button type=\"submit\" value=\"Submit\" class=\"login100-form-btn\">\n" + 
					"							Submit\n" + 
					"						</button>\n" + 
					"					</div>\n" + 
					"\n" + 
					"					</div>\n" + 
					"				</form>\n" + 
					"			</div>\n" + 
					"		</div>\n" + 
					"	</div>\n" + 
					"	\n" + 
					"\n" + 
					"	<div id=\"dropDownSelect1\"></div>\n" + 
					"	\n" + 
					"\n");
			String sec_inp  =  request.getParameter("sec_inp");
			String sem_inp = request.getParameter("sem_inp");
			String year_inp = request.getParameter("year_inp");
			
			PreparedStatement ps2 = conn.prepareStatement("select * from app_feedback");
			ResultSet rs=ps2.executeQuery();
			
			out.println("<!DOCTYPE html>\n" + 
					"<html lang=\"en\">\n" + 
					"<head>\n" + 
					"	<title>Feedback App</title>\n" + 
					"	<meta charset=\"UTF-8\">\n" + 
					"	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" + 
					"	<link rel=\"icon\" type=\"image/png\" href=\"images/icons/favicon1.ico\"/>\n" + 
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"fonts/font-awesome-4.7.0/css/font-awesome.min.css\">\n" + 
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"fonts/iconic/css/material-design-iconic-font.min.css\">\n" + 
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/util.css\">\n" + 
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/main.css\">\n" + 
					"\n" + 
					"</head>\n" + 
					"<body>\n" + 
					"	<center>\n" + 
					"	<table id=\"customers\">\n" + 
					"		\n" + 
					"		<tr>\n" + 
					"			<th>App Feedback by Our Users</th>\n" + 
					"		</tr>\n" );
			
			
			while(rs.next())
			{
				out.println(
						"<tr><td>"+rs.getString(1)+"</td></tr>");
			}
			out.println("</table><br><br><br><br><br></body></html>");
			
		//	out.println("<html> <a href = \"http://localhost:8080/dbproject/Logout \"> Logout </a> </html> ");
			out.println("<div class=\"navbar\" id=\"myNavbar\">\n" + 
					"    <a href=\"#home\" >Back To Top</a>\n" + 
					"    \n" + 
					"    <a href=\"http://localhost:8080/dbproject/Logout \" >Logout</a>\n" + 
					"\n" + 
					"</div></body></html>");
		
		}
		
		catch(Exception sqle) {
			System.out.println("Exception : " + sqle);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try (
			    Connection conn = DriverManager.getConnection(
			    		"jdbc:postgresql://localhost:5990/postgres", "deepak", "");
			    Statement stmt = conn.createStatement();
			)
		{
			PrintWriter out = response.getWriter();
			String sec_inp=request.getParameter("sec_inp");
			String sem_inp=request.getParameter("sem_inp");
			String year_inp = request.getParameter("year_inp");
			
			int year_inp_int = Integer.parseInt(year_inp);
			
			PreparedStatement ps = conn.prepareStatement("delete from current");
			ps.execute();
			
			PreparedStatement ps2 = conn.prepareStatement("insert into current values (? , ?, ?)");
			ps2.setString(1,  sec_inp);
			ps2.setString(2, sem_inp);
			ps2.setInt(3, year_inp_int);
			
			ps2.execute();
			
			
			response.sendRedirect("superuser_home");
			
			
			
		}
		catch (Exception sqle)
		{
		System.out.println("Exception : " + sqle);
		}
		
		
	}

}
