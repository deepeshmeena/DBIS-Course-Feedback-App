import java.io.PrintWriter;
import java.sql.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public Home() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		
		HttpSession session = request.getSession();
		String name_logg = (String) session.getAttribute("uid");
		if(session.isNew())
		{
			response.sendRedirect("Login");
		}
		out.println("<h1>Hey " + name_logg + " you are logged in </h1>");
		
		try (
			    Connection conn = DriverManager.getConnection(
			    		"jdbc:postgresql://localhost:5990/postgres", "deepak", "");
			    Statement stmt = conn.createStatement();
			)
		{
			PreparedStatement pstmt = conn.prepareStatement("select * from student where ID = ?");
			pstmt.setString(1,name_logg);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next() == false) {
				out.println("you are a teacher");
				PreparedStatement pst = conn.prepareStatement("select dept_name from instructor where ID = ?");
				pst.setString(1,name_logg);
				
				ResultSet rse = pst.executeQuery();
				while(rse.next()) {
					out.println("Your department is: " + rse.getString(1));
				}
			}
			else {
				out.println("<html> <a href = \"http://localhost:8080/dbproject/DisplayGrades \"> See Grades </a> <br></html> ");
			
		}
			
			out.println("<html> <a href = \"http://localhost:8080/dbproject/Logout \"> Logout </a> </html> ");
		}
		
		catch (Exception sqle)
		{
		System.out.println("Exception : " + sqle);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}