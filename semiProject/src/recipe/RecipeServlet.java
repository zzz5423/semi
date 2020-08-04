package recipe;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import database.SqlVO;

@WebServlet(value= {"/recipe/list", "/recipe/update", "/recipe/check", "/recipe/myrecipelist", "/recipe/delete", "/recipe/read", "/recipe/insert"})
public class RecipeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		SqlVO sqlVO=new SqlVO();
		RecipeDAO rdao=new RecipeDAO();
		PrintWriter out=response.getWriter();
		JSONObject jObject=new JSONObject();
		
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
		case "/recipe/check":
	         int count=rdao.check(request.getParameter("r_id"));
	            jObject.put("count", count);
	            out.println(jObject);
	         break;
	         
		case "/recipe/myrecipelist":
			out.println(rdao.myrecipelist(sqlVO));
			break;
			
		case "/recipe/list":
			out.println(rdao.list(sqlVO));
			break;
			
		case "/recipe/read":
			request.setAttribute("vo", rdao.read(request.getParameter("r_id")));
			RequestDispatcher dis=request.getRequestDispatcher("/recipe/read.jsp");
			dis.forward(request, response);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		
		RecipeVO vo=new RecipeVO();
	    RecipeDAO dao=new RecipeDAO();
	    JSONObject jObject=new JSONObject();
	    
	    
		switch(request.getServletPath()) {
		case "/recipe/insert":
			// 이미지파일 업로드
		    String uploadPath="c:" + File.separator + "semiProject" + File.separator + "recipe" + File.separator;
		    File mdPath=new File(uploadPath);
		    if(!mdPath.exists()) mdPath.mkdir();
		    MultipartRequest multi = new MultipartRequest(request, uploadPath, 1024*1024*10, "UTF-8", new DefaultFileRenamePolicy());
			
			String image=multi.getFilesystemName("r_img");
		    String r_id=multi.getParameter("r_id");
		    
		     vo.setR_id(r_id);
		     vo.setCategory_id(multi.getParameter("category_id"));
		     vo.setU_id(multi.getParameter("u_id"));
		     vo.setR_name(multi.getParameter("r_name"));
		     vo.setR_content(multi.getParameter("r_content"));
	         vo.setR_img(image);
	         dao.insert(vo);
	         response.sendRedirect("/semiProject/user/mypage.jsp");
	         break;
	         
		case "/recipe/update":
			// 이미지파일 업로드
			uploadPath="c:" + File.separator + "semiProject" + File.separator + "recipe" + File.separator;
		    mdPath=new File(uploadPath);
		    if(!mdPath.exists()) mdPath.mkdir();
		    multi = new MultipartRequest(request, uploadPath, 1024*1024*10, "UTF-8", new DefaultFileRenamePolicy());
			
			image=multi.getFilesystemName("r_img");
			r_id=multi.getParameter("r_id");
			
			vo.setR_id(r_id);
		    vo.setCategory_id(multi.getParameter("category_id"));
		    vo.setU_id(multi.getParameter("u_id"));
		    vo.setR_name(multi.getParameter("r_name"));
	     	vo.setR_content(multi.getParameter("r_content"));
			
	         RecipeVO oldVO=dao.read(r_id);
	         if(image!=null) {   //업로드한 이미지가 있으면
	            if(oldVO.getR_img()!=null) {
	               System.gc(); //기존에 열려있던 것들을 청소해주겠다. 시스템이 이미지를 잡고있어서 삭제가 안됐다.
	               File oldImage=new File(uploadPath + oldVO.getR_img());
	               oldImage.delete();   //예전 이미지를 지워주는 방법
	            }
	            vo.setR_img(image);
	         }else {   //업로드한 이미지가 없으면
	            vo.setR_img(oldVO.getR_img());   //옛날 이미지를 넣어준다.
	         }
	         dao.update(vo);
	         response.sendRedirect("/semiProject/user/mypage.jsp");
	         break;
		
		case "/recipe/delete":
	         vo=dao.read(request.getParameter("r_id"));
	         int count=dao.delete(request.getParameter("r_id"));
	         jObject.put("count", count);
	         out.println(jObject);
	         break;

	      }
	}
}
