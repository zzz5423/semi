package japan;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import database.SqlVO;
import recipe.RecipeVO;

@WebServlet(value= {"/japan/list", "/japan/read"})
public class JapanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		SqlVO sqlVO=new SqlVO();
		JapanDAO dao=new JapanDAO();
		PrintWriter out=response.getWriter();
		
		String key=request.getParameter("key")==null?"category_id":request.getParameter("key");
		String word=request.getParameter("word")==null?"":request.getParameter("word");
		String order=request.getParameter("order")==null?"r_id":request.getParameter("order");
		String desc=request.getParameter("desc")==null?"":request.getParameter("desc");
		String strPage=request.getParameter("page")==null?"1":request.getParameter("page");
		String strPerPage=request.getParameter("perPage")==null?"2":request.getParameter("perPage");
		String u_id=request.getParameter("u_id");
		int page=Integer.parseInt(strPage);
		int perPage=Integer.parseInt(strPerPage);
		sqlVO.setKey(key);
		sqlVO.setWord(word);
		sqlVO.setOrder(order);
		sqlVO.setDesc(desc);
		sqlVO.setPage(page);
		sqlVO.setPerPage(perPage);
		sqlVO.setU_id(u_id);
		
		switch(request.getServletPath()) {
		case "/japan/list":
			out.println(dao.list(sqlVO));
			break;
			
		case "/japan/read": //일반유저가 레시피 읽을 때
			request.setAttribute("vo", dao.read(request.getParameter("r_id")));
			RequestDispatcher dis=request.getRequestDispatcher("read.jsp");
			dis.forward(request, response);
			break;
		}	
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
}
