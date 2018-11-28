import java.io.PrintWriter;
import java.sql.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Assignment5a
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public Login() {
        super();       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String role = (String) session.getAttribute("role");
		
		//we have a attribute "role" in session .Value of role will be set to
		//"i" if instructor logged in or "s" if a student logged in.. role will be null if session is expired 
		//so below check if session is exist or not . 
		if(role == "i")
		{
			response.sendRedirect("InstructorView");
		}
		else if(role == "s"){
			response.sendRedirect("DisplayGrades");
		}

		try (
			    Connection conn = DriverManager.getConnection(
			    		"jdbc:postgresql://localhost:5990/postgres", "deepak", "");
			    Statement stmt = conn.createStatement();
			){
			
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
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/util.css\"/>" + 
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/main.css\"/>" + 
					"<!--===============================================================================================-->\n" + 
					"</head>\n" + 
					"<body>\n" + 
					"	\n" + 
					"	<div class=\"limiter\">\n" + 
					"		 <div class=\"container-login100\"  > \n" + 
					"			<div class=\"wrap-login100\">\n" 
							);
			
			//below is login form 
			out.println("<form class=\"login100-form validate-form\"     action=\"Login \" method=\"post\" >\n" + 
					"					<span class=\"login100-form-logo\">\n" + 
					"						<i class=\"zmdi zmdi-assignment-account\"></i>\n" + 
					"					</span>\n" + 
					"\n" + 
					"					<span class=\"login100-form-title p-b-34 p-t-27\">\n" + 
					"						Log in\n" + 
					"					</span>\n" + 
					"\n" + 
					"					<div class=\"wrap-input100 validate-input\" data-validate = \"Enter username\">\n" + 
					"						<input class=\"input100\" type=\"text\" name = \"name\" placeholder=\"Username\">\n" + 
					"						<span class=\"focus-input100\" data-placeholder=\"&#xf207;\"></span>\n" + 
					"					</div>\n" + 
					"\n" + 
					"					<div class=\"wrap-input100 validate-input\" data-validate=\"Enter password\">\n" + 
					"						<input class=\"input100\" type=\"password\" name = \"password\" placeholder=\"Password\">\n" + 
					"						<span class=\"focus-input100\" data-placeholder=\"&#xf191;\"></span>\n" + 
					"					</div>\n" + 
					"\n" + 
					"					\n" + 
					"\n" + 
					"					<div class=\"container-login100-form-btn\">\n" + 
					"						<button class=\"login100-form-btn\">\n" + 
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
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			
			
			//checking the validity of inputs in login form
			PreparedStatement ps = conn.prepareStatement("select password from password where id = ?");
			ps.setString(1,name);
			ResultSet rset = ps.executeQuery();
			String pass_original = "";
			while(rset.next()) {
				 pass_original = rset.getString(1);
				
			}
			if(password.isEmpty() || name.isEmpty()) {
				out.println("<script type=\"text/javascript\">");
			       out.println("alert('ID or password can not be blank');");
			       out.println("</script>");
				
			}
			else {
				if(password.equals(pass_original)) {
					
					
					session.setAttribute("uid", name);
					session.setAttribute("password", password);
					
					PreparedStatement ps2 = conn.prepareStatement("select id from instructor where id = ?");
					ps2.setString(1, name);
					ResultSet rset2= ps2.executeQuery();
					
					if(rset2.next() == true)
					{
						session.setAttribute("role", "i");//setting the value of role to "i" as id is found to be of instructor
						response.sendRedirect("InstructorView");
					}
					else
					{
						session.setAttribute("role", "s");
						response.sendRedirect("DisplayGrades");
					}
				}
				else {
					out.println("<script type=\"text/javascript\">");
				       out.println("alert('User or password incorrect');");
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