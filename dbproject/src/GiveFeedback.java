

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

/**
 * Servlet implementation class GiveFeedback
 */
@WebServlet("/GiveFeedback")
public class GiveFeedback extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public GiveFeedback() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		//getting values from session and checking if already logged in
		
		String selected_course = request.getParameter("course_name");
		String id = (String) session.getAttribute("uid");
		String role = (String) session.getAttribute("role");
		
		if(role == null) {
			response.sendRedirect("Login");
			return;
		}
		out.println("<!DOCTYPE html>\n" + 
				"<html>\n" + 
				"<head>\n" + 
				"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" + 
				"<link rel=\"icon\" type=\"image/png\" href=\"images/icons/favicon1.ico\"/>\n" + 
				"<link rel=\"stylesheet\" type=\"text/css\" href=\"css/main.css\"/>\n" + 
				"\n" + 
				"<script type=\"text/javascript\">\n" + 
				"		function setbg(color)\n" + 
				"	{\n" + 
				"	document.getElementById(\"styled\").style.background=color\n" + 
				"	}\n" + 
				"</script>\n" + 
				"\n" + 
				"<style type=\"text/css\">\n" + 
				"	\n" + 
				"#styled {\n" + 
				"  width: 600px;\n" + 
				"  height: 120px;\n" + 
				"  border: 4px inset #9152f8;\n" + 
				"  padding: 5px;\n" + 
				"  font-family: Tahoma, sans-serif;\n" + 
				"  background-image: url(bg.gif);\n" + 
				"\n" + 
				"  background-position: bottom right;\n" + 
				"  background-repeat: no-repeat;\n" + 
				"}\n" + 
				"\n" + 
				"</style>"+
				"</head>\n" + 
				"<body>\n" + 
				"\n" + 
				"<div id=\"home\">\n" + 
				"  <button class=\"tablinks\" onclick=\"location.href='http://localhost:8080/dbproject/DisplayGrades'\">Give Feedback</button>\n" + 
				"  <button class=\"tablinks\" onclick=\"location.href='http://localhost:8080/dbproject/searchAllCourses'\">View Feedback</button>  \n" + 
				"</div><br><br><br>\n");
		
		out.println("<html> <body><br><br><center><h2> You are giving feedback for: " + selected_course+"</h2></center><br><br></body></html>");
		
		out.println("<html>\n" + 
				"<body>\n" + 
				"<form id=\"nameform\" action=\"GiveFeedback\" method=\"post\" >\n" + 
				
					"<center><h3>Would you like to show your identity? </h3>\n" + 
					"<select name=\"question6\">\n" + 
					"  <option value=\"0\">no</option>\n" + 
					"  <option value=\"1\">yes</option>\n" + 
				
					"  \n" + 
					"</select></center>\n" + 
				
				"\n" + 
				"<center>\n" + 
				"<h3>Que1: How was the Instructors' Teaching(involes punctuality ,course completion,class involvment )</h3>\n" + 
					"  <input type=\"radio\" name=\"question1\"  value=\"1\" > Very Bad\n" + 
					"  <input type=\"radio\" name=\"question1\" value=\"2\" > Bad\n" + 
					"  <input type=\"radio\" name=\"question1\" value=\"3\"  > Average\n" + 
					"  <input type=\"radio\" name=\"question1\" value=\"4\" > Good \n" + 
					"  <input type=\"radio\" name=\"question1\" value=\"5\" > Very Good  \n" + 
				"</center><br<br>"+
				
								
				"<center>\n" + 
				"<h3>Que2: Course Difficulty Level</h3>\n" + 
				"  <input type=\"radio\" name=\"question2\"  value=\"1\" > Very Bad\n" + 
				"  <input type=\"radio\" name=\"question2\" value=\"2\" > Bad\n" + 
				"  <input type=\"radio\" name=\"question2\" value=\"3\"  > Average\n" + 
				"  <input type=\"radio\" name=\"question2\" value=\"4\" > Good \n" + 
				"  <input type=\"radio\" name=\"question2\" value=\"5\" > Very Good  \n" +
				"</center><br<br>"+
									
					"<center>\n" + 
					"<h3>Que3:Examination Difficulty Level</h3>\n" + 
					"  <input type=\"radio\" name=\"question3\"  value=\"1\" > Very Bad\n" + 
					"  <input type=\"radio\" name=\"question3\" value=\"2\" > Bad\n" + 
					"  <input type=\"radio\" name=\"question3\" value=\"3\"  > Average\n" + 
					"  <input type=\"radio\" name=\"question3\" value=\"4\" > Good \n" + 
					"  <input type=\"radio\" name=\"question3\" value=\"5\" > Very Good  \n" +
					"</center><br<br>"+
					
					"<center>\n" + 
					"<h3>Que4:Assignment Difficulty Level </h3>\n" + 
					"  <input type=\"radio\" name=\"question4\"  value=\"1\" > Very Bad\n" + 
					"  <input type=\"radio\" name=\"question4\" value=\"2\" > Bad\n" + 
					"  <input type=\"radio\" name=\"question4\" value=\"3\"  > Average\n" + 
					"  <input type=\"radio\" name=\"question4\" value=\"4\" > Good \n" + 
					"  <input type=\"radio\" name=\"question4\" value=\"5\" > Very Good  \n" +
					"</center><br<br>" + 
					"<center>\n" + 
					"<h3>Que5:How was the course in overall?</h3>\n" + 
					"  <input type=\"radio\" name=\"question5\"  value=\"1\" > Very Bad\n" + 
					"  <input type=\"radio\" name=\"question5\" value=\"2\" > Bad\n" + 
					"  <input type=\"radio\" name=\"question5\" value=\"3\"  > Average\n" + 
					"  <input type=\"radio\" name=\"question5\" value=\"4\" > Good \n" + 
					"  <input type=\"radio\" name=\"question5\" value=\"5\" > Very Good  \n" +
					"</center><br<br>" + 
				"<br><br>\n" +
					
				"<center><h3>Any comments:</h3>\n" + 
						"<textarea name=\"review\" id=\"styled\" placeholder=\"Enter your Comment here...\""
						+ " onfocus=\"this.value=''; "
						+ "setbg('#efccff');\" onblur=\"setbg('white')\"></textarea>\n" + 
						 
				 "<input class=\"login100-form-btn\"type=\"submit\" value = \"Submit\">"	
				+"</form>\n"+ 
				"</center></body>\n" + 
				"</html><br<br><br<br><br<br>");
		
		//out.println("<html> <a href = \"http://localhost:8080/dbproject/Logout \"> Logout </a> </html> ");
		out.println("<br><br><br><br><br><div class=\"navbar\" id=\"myNavbar\">\n" + 
				"    <a href=\"#home\" >Back To Top</a>\n" + 
				"    \n" + 
				"    <a href=\"http://localhost:8080/dbproject/Logout \" >Logout</a>\n" + 
				"\n" + 
				"</div></body></html>");
		
	    session.setAttribute("selected_course", selected_course);
	
	}
	
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();	
		try (
			    Connection conn = DriverManager.getConnection(
			    		"jdbc:postgresql://localhost:5990/postgres", "deepak", "");
			    Statement stmt = conn.createStatement();
			)

		{
			//getting values for each questions
			String que1=request.getParameter("question1");
			String que2=request.getParameter("question2");
			String que3=request.getParameter("question3");
			String que4=request.getParameter("question4");
			String que5=request.getParameter("question5");
			
			String review=request.getParameter("review");
			String permi = request.getParameter("question6");
			
			int permi_int = Integer.parseInt(permi);
			
			int que1int = Integer.parseInt(que1);	
			int que2int = Integer.parseInt(que2);
			int que3int = Integer.parseInt(que3);
			int que4int = Integer.parseInt(que4);
			int que5int = Integer.parseInt(que5);
			//int reviewint =Integer.parseInt(review);
			
			
			out.println(que1);
			out.println(que2);
			out.println(que3);
			out.println(que4);
			out.println(que5);
			out.println(review);
			
			//get current values of sem, sec, year from current table
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
			
			
			HttpSession session = request.getSession();
			String id = (String) session.getAttribute("uid");
			String selected_course =(String) session.getAttribute("selected_course");
			
			out.println(id);
			out.println(selected_course);
			
			//insert all the values of question with the specific parameters
			
			PreparedStatement ps1 = conn.prepareStatement("INSERT INTO feedback(course_id,id,sec_id,semester,year) "
					+ " values(?,?,?,?,?) ;\n" + 
					"");
			ps1.setString(1,selected_course);
			ps1.setString(2,id);
			ps1.setString(3,get_sec);
			ps1.setString(4,  get_sem);
			ps1.setInt(5, get_year);
			ps1.execute();
			
			out.println("succesful submission1");
			
			PreparedStatement ps2 = conn.prepareStatement("select feedback_id from feedback where course_id=? and id=? and "
					+  "sec_id=? and semester=? and year=? ");
	
			ps2.setString(1,selected_course );
			ps2.setString(2,id );
			ps2.setString(3,get_sec );
			ps2.setString(4,  get_sem);
			ps2.setInt(5, get_year);
			ResultSet rset2 = ps2.executeQuery();
			
			
            int feedback_id=0;            
			while(rset2.next()) {
				 feedback_id = rset2.getInt(1);
			}
			out.println(feedback_id);
			
			PreparedStatement ps3 = conn.prepareStatement("insert into feedback_value(feedback_id, que_id1, que_id2, que_id3, que_id4, que_id5, view_permission, que_id10) values (?,?,?,?,?,?,?,?)");
			ps3.setInt(1,feedback_id);
			ps3.setInt(2, que1int);
			ps3.setInt(3, que2int);
			ps3.setInt(4, que3int);
			ps3.setInt(5, que4int);
			ps3.setInt(6, que5int);
			ps3.setInt(7, permi_int);
			ps3.setString(8, review);
			ps3.execute();
			
			PreparedStatement ps4 = conn.prepareStatement("update takes set feedbackstatus ='1' where id =? and course_id=?  "
					+ "and sec_id= ? and semester = ? and year= ?;");  
			ps4.setString(1,id );
			ps4.setString(2,selected_course );
			ps4.setString(3,  get_sec);
			ps4.setString(4,  get_sem);
			ps4.setInt(5, get_year);
			ps4.execute();
			
			out.println("succesful submission");
			
			response.sendRedirect("DisplayGrades");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception : " + e);
		}
	}

}
