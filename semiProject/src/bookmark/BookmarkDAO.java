package bookmark;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import database.Database;
import database.SqlVO;
import recipe.RecipeVO;

public class BookmarkDAO {
	//즐겨찾기 삭제
	public void delete(String r_id, String u_id) {
		try {
			String sql="delete from bookmarkTbl where r_id=? and u_id=?";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, r_id);
			ps.setString(2, u_id);
			ps.execute();
		}catch(Exception e) {
			System.out.println("즐겨찾기 삭제:" + e.toString());
		}
	}
		
	//내 레시피 정보
	public RecipeVO reciperead(String r_id) {
		RecipeVO vo=new RecipeVO();
		try {
			String sql="select * from recipeTbl where r_id=?";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, r_id);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				vo.setR_id(rs.getString("r_id"));
				vo.setR_name(rs.getString("r_name"));
				vo.setR_img(rs.getString("r_img"));
				vo.setR_content(rs.getString("r_content"));
				}
		}catch(Exception e) {
			System.out.println("즐겨찾기정보:" + e.toString());
		}
		return vo;
	}
		
	//즐겨찾기정보
	public RecipeVO read(String r_id, String u_id) {
		RecipeVO vo=new RecipeVO();
		try {
			String sql="select * from bookmarkTbl where r_id=? and u_id=?";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, r_id);
			ps.setString(2, u_id);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				vo.setR_id(rs.getString("r_id"));
				vo.setR_name(rs.getString("b_name"));
				vo.setR_img(rs.getString("b_img"));
				vo.setR_content(rs.getString("b_content"));
				}
		}catch(Exception e) {
			System.out.println("즐겨찾기정보:" + e.toString());
		}
		return vo;
	}
			
	//즐겨찾기목록출력
	public JSONObject list(SqlVO vo) {
		JSONObject jObject=new JSONObject();
		try {
			String sql="call bookmark_list(?,?,?,?,?,?,?)";
			CallableStatement cs=Database.CON.prepareCall(sql);
			cs.setString(1, vo.getKey());
			cs.setString(2, vo.getWord());
			cs.setString(3, vo.getOrder());
			cs.setString(4, vo.getDesc());
			cs.setInt(5, vo.getPage());
			cs.setInt(6, vo.getPerPage());
			cs.setString(7, vo.getU_id());
			cs.execute();
			
			ResultSet rs=cs.getResultSet();
			JSONArray jArray=new JSONArray();
			while(rs.next()) {
				JSONObject obj=new JSONObject();
				obj.put("r_id", rs.getString("r_id"));
				obj.put("u_id", rs.getString("u_id"));
				obj.put("b_name", rs.getString("b_name"));
				obj.put("b_img", rs.getString("b_img"));
				obj.put("b_content", rs.getString("b_content"));
				jArray.add(obj);
			}
			jObject.put("list", jArray);
			
			cs.getMoreResults();
			rs=cs.getResultSet();
			int count=0;
			if(rs.next()) {
				count=rs.getInt("total");;
			}
			int perPage=vo.getPerPage();
			int totPage=count%perPage==0?count/perPage:(count/perPage)+1;
			jObject.put("count", count);
			jObject.put("totPage", totPage);
			jObject.put("perPage", perPage);
			jObject.put("page", vo.getPage());
		}catch(Exception e) {
			System.out.println("즐겨찾기목록:" + e.toString());
		}
		return jObject;
	}
			
	//즐겨찾기 등록
	 public int insert(RecipeVO vo) {
		 int count=1;
	      try {
	         String sql = "call add_bookmark(?,?,?,?,?,?)";
	         CallableStatement cs = Database.CON.prepareCall(sql);
	         cs.setString(1, vo.getU_id());
	         cs.setString(2, vo.getR_id());
	         cs.setString(3, vo.getR_name());
	         cs.setString(4, vo.getR_img());
	         cs.setString(5, vo.getR_content());
	         cs.registerOutParameter(6, java.sql.Types.INTEGER);
	         cs.execute();
	         count=cs.getInt(6);
	      } catch (Exception e) {
	         System.out.println("즐겨찾기등록:" + e.toString());
	      }
	      return count;
	   }
}
