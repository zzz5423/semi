	package recipe;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import database.Database;
import database.SqlVO;

public class RecipeDAO {
	 //레시피 등록 중복 체크
	   public int check(String r_id) {
	      int count=1;
	      try{
	         String sql="select count(*) cnt from recipeTbl where r_id=?";
	         PreparedStatement ps=Database.CON.prepareStatement(sql);
	         ps.setString(1, r_id);
	         ResultSet rs=ps.executeQuery();
	         if(rs.next()) {
	            count=rs.getInt("cnt");
	         }
	      }catch(Exception e) {
	         System.out.println("레시피 등록 중복 체크:" + e.toString());
	      }
	      return count;
	   }

	//내 레시피 수정
	   public void update(RecipeVO vo) {
	      try {
	         String sql = "update recipeTbl set r_name=?,r_img=?,r_content=? where u_id=? and r_id=?";
	         PreparedStatement ps = Database.CON.prepareStatement(sql);
	         ps.setString(1, vo.getR_name());
	         ps.setString(2, vo.getR_img());
	         ps.setString(3, vo.getR_content());
	         ps.setString(4, vo.getU_id());
	         ps.setString(5, vo.getR_id());
	         ps.execute();
	      } catch (Exception e) {
	         System.out.println("레시피수정:" + e.toString());
	      }
	   }
	
	//내 레시피 목록 출력
	public JSONObject myrecipelist(SqlVO vo) {
		JSONObject jObject=new JSONObject();
		try {
			String sql="call myrecipe_LIST(?,?,?,?,?,?,?)";
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
				obj.put("r_name", rs.getString("r_name"));
				obj.put("r_img", rs.getString("r_img"));
				obj.put("r_content", rs.getString("r_content"));
				obj.put("category_id", rs.getString("category_id"));
				obj.put("u_id", rs.getString("u_id"));
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
			System.out.println("내 레시피 목록:" + e.toString());
		}
		return jObject;
	}
		
	//내 레시피삭제
    public int delete(String r_id) {
       int count=-1;
       try {
          String sql = "{call del_myrecipe(?, ?)}";
          CallableStatement cs=Database.CON.prepareCall(sql);          
          cs.setString(1, r_id);
          cs.registerOutParameter(2, oracle.jdbc.OracleTypes.INTEGER);
          cs.execute();
          count=cs.getInt(2);
       } catch (Exception e) {
          System.out.println("레시피삭제:" + e.toString());
       }
       return count;
    }
	
	
   // 레시피 등록
   public void insert(RecipeVO vo) {
      try {
         String sql="insert into recipeTbl(r_id,category_id,u_id,r_name,r_img,r_content) values(?,?,?,?,?,?)";
         PreparedStatement ps=Database.CON.prepareStatement(sql);
         ps.setString(1, vo.getR_id());
         ps.setString(2, vo.getCategory_id());
         ps.setString(3, vo.getU_id());
         ps.setString(4, vo.getR_name());
         ps.setString(5, vo.getR_img());
         ps.setString(6, vo.getR_content());
         ps.execute();
      }catch(Exception e) {
         System.out.println("레시피등록 : " + e.toString());
      }
   }
		
	//레시피정보
   public RecipeVO read(String r_id) {
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
               vo.setU_id(rs.getString("u_id"));
               }
         }catch(Exception e) {
            System.out.println("레시피정보:" + e.toString());
         }
         return vo;
      }
	
	//레시피 목록 출력
	public JSONObject list(SqlVO vo) {
		JSONObject jObject=new JSONObject();
		try {
			String sql="call list(?,?,?,?,?,?,?)";
			CallableStatement cs=Database.CON.prepareCall(sql);
			cs.setString(1, "recipeTbl");
			cs.setString(2, vo.getKey());
			cs.setString(3, vo.getWord());
			cs.setString(4, vo.getOrder());
			cs.setString(5, vo.getDesc());
			cs.setInt(6, vo.getPage());
			cs.setInt(7, vo.getPerPage());
			cs.execute();
			
			ResultSet rs=cs.getResultSet();
			JSONArray jArray=new JSONArray();
			while(rs.next()) {
				JSONObject obj=new JSONObject();
				obj.put("r_id", rs.getString("r_id"));
				obj.put("r_name", rs.getString("r_name"));
				obj.put("r_img", rs.getString("r_img"));
				obj.put("r_content", rs.getString("r_content"));
				obj.put("category_id", rs.getString("category_id"));
				obj.put("u_id", rs.getString("u_id"));
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
			System.out.println("레시피 목록:" + e.toString());
		}
		return jObject;
	}
}