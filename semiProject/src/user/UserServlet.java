package user;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import database.SqlVO;
import recipe.RecipeVO;

@WebServlet(value = { "/user/login", "/user/userdelete", "/user/delete", "/user/myreciperead", "/user/myrecipelist",
		"/user/logout", "/user/signUp","/user/read","/user/update"})
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		SqlVO sqlVO = new SqlVO();
		UserDAO dao = new UserDAO();
		PrintWriter out = response.getWriter();
		JSONObject jObject = new JSONObject();

		String key = request.getParameter("key") == null ? "r_id" : request.getParameter("key");
		String word = request.getParameter("word") == null ? "" : request.getParameter("word");
		String order = request.getParameter("order") == null ? "r_id" : request.getParameter("order");
		String desc = request.getParameter("desc") == null ? "" : request.getParameter("desc");
		String strPage = request.getParameter("page") == null ? "1" : request.getParameter("page");
		String strPerPage = request.getParameter("perPage") == null ? "2" : request.getParameter("perPage");
		String u_id = request.getParameter("u_id");
		int page = Integer.parseInt(strPage);
		int perPage = Integer.parseInt(strPerPage);
		sqlVO.setKey(key);
		sqlVO.setWord(word);
		sqlVO.setOrder(order);
		sqlVO.setDesc(desc);
		sqlVO.setPage(page);
		sqlVO.setPerPage(perPage);
		sqlVO.setU_id(u_id);

		switch (request.getServletPath()) {
		case "/user/read":
	         request.setAttribute("vo", dao.UserRead(request.getParameter("u_id")));
	         RequestDispatcher dis=request.getRequestDispatcher("userUpdate.jsp");
	         dis.forward(request, response);
	         break;
		
		case "/user/userdelete":
			dao.userdelete(u_id);
			break;

		case "/user/delete":
			int count = dao.delete(request.getParameter("r_id"), u_id);
			jObject.put("count", count);
			out.println(jObject);
			break;

		case "/user/myreciperead":
			request.setAttribute("vo", dao.read(request.getParameter("r_id")));
			dis = request.getRequestDispatcher("/user/read.jsp");
			dis.forward(request, response);
			break;

		case "/user/logout":
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("/semiProject/user/login.jsp");
			break;

		case "/user/myrecipelist":
			out.println(dao.list(sqlVO));
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		UserVO userVO = new UserVO();
		UserDAO userDAO = new UserDAO();
		JSONObject jObject = new JSONObject();
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		switch (request.getServletPath()) {
		case "/user/update":
			 String name=request.getParameter("name");
			 String strAge=request.getParameter("age");
			 int age=Integer.parseInt(strAge);
	         String email=request.getParameter("email");
	         String phone=request.getParameter("phone");
	         String address=request.getParameter("address");
	         
	         userVO.setU_id(id);
	         userVO.setU_name(name);
	         userVO.setU_age(age);
	         userVO.setU_pass(pass);
	         userVO.setU_email(email);
	         userVO.setU_phone(phone);
	         userVO.setU_address(address);
	         
	         userDAO.update(userVO);
			 session.setAttribute("name", userVO.getU_name());
	         response.sendRedirect("/semiProject/index.jsp");
	         break;
		
		case "/user/login":
			userVO = userDAO.login(id);
			int check = 0;
			if (userVO.getU_id() != null) {
				if (userVO.getU_pass().equals(pass)) {
						if (userVO.getDel_user() == 0) {
							check = 2; /* pass가 일치하는 경우 */
							session = request.getSession();/* 모든곳에서 사용가능 */
							session.setAttribute("id", userVO.getU_id());
							session.setAttribute("name", userVO.getU_name());
						}
						userVO = userDAO.userRead(userVO);
						if (userVO.getDel_user() == 1) {
							check = 3;/* 탈퇴할 회원일 경우 */
						}
					} else {
						check = 1;
					}
			}
			jObject.put("check", check);
			out.println(jObject);
			break;

		case "/user/signUp":
			UserVO vo = new UserVO();
			vo.setU_id(request.getParameter("id"));
			vo.setU_pass(request.getParameter("pass"));
			vo.setU_name(request.getParameter("name"));
			vo.setU_age(Integer.parseInt(request.getParameter("age")));
			vo.setU_email(request.getParameter("email"));
			vo.setU_phone(request.getParameter("phone"));
			vo.setU_birth(request.getParameter("birth"));
			vo.setU_address(request.getParameter("address"));

			jObject = userDAO.insert(vo);
			out.print(jObject);
			break;
		}
	}
}
