

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
 * Servlet implementation class SuperUser
 */
@WebServlet("/SuperUser")
public class SuperUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public SuperUser() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		PrintWriter out = response.getWriter();
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		try (
			    Connection conn = DriverManager.getConnection(
			    		"jdbc:postgresql://localhost:5990/postgres", "deepak", "");
			    Statement stmt = conn.createStatement();
			)
		{
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
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"fonts/iconic/css/material-design-iconic-font.min.css\">\n" + 
					"\n" + 
					"<!--===============================================================================================-->\n" + 
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/util.css\">\n" + 
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/main.css\">\n" + 
					"<!--===============================================================================================-->\n" + 
					"</head>\n" + 
					"<body>\n" + 
					"	\n" + 
					"	<div class=\"limiter\">\n" + 
					"		 <div class=\"container-login100\"  > \n" + 
					"			<div class=\"wrap-login100\">\n" + 
					"				<form class=\"login100-form validate-form\" action=\"SuperUser\" method=\"Post\"  >\n" + 
					"					<span class=\"login100-form-logo\">\n" + 
					"						<i class=\"zmdi zmdi-assignment-account\"></i>\n" + 
					"					</span>\n" + 
					"\n" + 
					"					<span class=\"login100-form-title p-b-34 p-t-27\">\n" + 
					"						SuperUser Log in\n" + 
					"					</span>\n" + 
					"\n" + 
					"					<div class=\"wrap-input100 validate-input\" data-validate = \"Enter username\">\n" + 
					"						<input class=\"input100\" type=\"text\" name=\"name\" placeholder=\"Username\">\n" + 
					"						<span class=\"focus-input100\" data-placeholder=\"&#xf207;\"></span>\n" + 
					"					</div>\n" + 
					"\n" + 
					"					<div class=\"wrap-input100 validate-input\" data-validate=\"Enter password\">\n" + 
					"						<input class=\"input100\" type=\"password\" name=\"password\" placeholder=\"Password\">\n" + 
					"						<span class=\"focus-input100\" data-placeholder=\"&#xf191;\"></span>\n" + 
					"					</div>\n" + 
					"\n" + 
					"					\n" + 
					"\n" + 
					"					<div class=\"container-login100-form-btn\">\n" + 
					"						<button class=\"login100-form-btn\" type=\"submit\" value=\"Submit\">\n" + 
					"							Login\n" + 
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
					"\n" + 
					"</body>\n" + 
					"</html>");
			//out.println("<html><center><h1> Log in as superuser! </h1><br>");
			//out.println("<form action=\"SuperUser \" method=\"post\">"
				//	+ " Enter your superuser id: <input type=\"text\" name = \"name\"> <br> "
					//+ "Enter your superuser password: <input type=\"password\" name = \"password\"> <br>"
					//+ " <input type=\"submit\" value = \"Submit\"> </form> </center></html>");
			
			String sup_id = request.getParameter("name");
			String sup_pwd = request.getParameter("password");
			
			int sup_id_int = Integer.parseInt(sup_id);
			int sup_pwd_int  = Integer.parseInt(sup_pwd);
			out.println("<html> <a href = \"http://localhost:8080/dbproject/Login \"> Login </a> </html> ");
			
			PreparedStatement ps = conn.prepareStatement("select password from Superuser where id = ?");
			ps.setInt(1,sup_id_int);
			ResultSet rset = ps.executeQuery();
			int sup_pwd_original = 0;
			while(rset.next()) {
				 sup_pwd_original = rset.getInt(1);
				 
			}
			if(sup_pwd.isEmpty() || sup_id.isEmpty()) {
				
				out.println("<script type=\"text/javascript\">");
			       out.println("alert('ID or password can not be blank');");
			       out.println("</script>");
			}
			
			else {
			if(sup_pwd_int == sup_pwd_original) {
				out.println("Congrats, You are logged in!");
				HttpSession session =request.getSession();
				response.sendRedirect("superuser_home");
				
			}
			else {
				out.println("<script type=\"text/javascript\">");
			       out.println("alert('Username of Password Incorrect');");
			       out.println("</script>");
			}
			
			}
		}
		catch (Exception sqle)
		{
		System.out.println("Exception : " + sqle);
		}
	
	
	
	}

}
