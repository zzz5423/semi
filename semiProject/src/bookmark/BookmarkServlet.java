package bookmark;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import database.SqlVO;
import recipe.RecipeVO;

@WebServlet(value= {"/bookmark/list", "/bookmark/delete", "/bookmark/reciperead", "/bookmark/read", "/bookmark/insert"})
public class BookmarkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		SqlVO sqlVO=new SqlVO();
		BookmarkDAO dao=new BookmarkDAO();
		PrintWriter out=response.getWriter();
		
		String key=request.getParameter("key")==null?"r_id":request.getParameter("key");
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
		case "/bookmark/reciperead":
			request.setAttribute("v", dao.reciperead(request.getParameter("r_id")));
			RequestDispatcher dis=request.getRequestDispatcher("/user/myrecipe.jsp");
			dis.forward(request, response);
			break;
			
		case "/bookmark/list":
			out.println(dao.list(sqlVO));
			break;
			
		case "/bookmark/read":
			String r_id=request.getParameter("r_id");
			request.setAttribute("vo", dao.read(r_id, u_id));
			dis=request.getRequestDispatcher("read.jsp");
			dis.forward(request, response);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      RecipeVO vo=new RecipeVO();
	      vo.setR_id(request.getParameter("r_id"));
	      vo.setR_name(request.getParameter("r_name"));
	      vo.setR_content(request.getParameter("r_content"));
	      vo.setR_img(request.getParameter("r_img"));
	      vo.setU_id(request.getParameter("u_id"));
	      BookmarkDAO dao=new BookmarkDAO();
	      PrintWriter out=response.getWriter();
	      
	      switch(request.getServletPath()) {
	      case "/bookmark/delete":
		    	 String r_id=request.getParameter("r_id");
		    	 String u_id=request.getParameter("u_id");
		    	 dao.delete(r_id, u_id);
		         break;
		         
	      case "/bookmark/insert":
	    	 JSONObject jObject=new JSONObject();
	    	 int count=dao.insert(vo);
		     jObject.put("count", count);
			 out.println(jObject);
	         break;
	      }
	}
}
