package user;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import database.Database;
import database.SqlVO;
import recipe.RecipeVO;

public class UserDAO {
	
	//회원정보 수정
	   public void update(UserVO vo) {
	      try {
	         String sql = "update userTbl set u_pass=?,u_name=?,u_age=?,u_email=?,u_phone=?,u_address=? where u_id=?";
	         PreparedStatement ps = Database.CON.prepareStatement(sql);
	         ps.setString(1, vo.getU_pass());
	         ps.setString(2, vo.getU_name());
	         ps.setInt(3, vo.getU_age());
	         ps.setString(4, vo.getU_email());
	         ps.setString(5, vo.getU_phone());
	         ps.setString(6, vo.getU_address());
	         ps.setString(7, vo.getU_id());
	         ps.execute();
	      } catch (Exception e) {
	         System.out.println("회원정보수정:" + e.toString());
	      }
	   }
	   
	   //회원정보 읽기
	    public UserVO UserRead(String u_id) {
	        UserVO vo = new UserVO();
	        try {
	           String sql = "select * from userTbl where u_id=?";
	           PreparedStatement ps = Database.CON.prepareStatement(sql);
	           ps.setString(1, u_id);
	           ResultSet rs = ps.executeQuery();
	           if (rs.next()) {
	              vo.setU_id(rs.getString("u_id"));
	              vo.setU_pass(rs.getString("u_pass"));
	              vo.setU_name(rs.getString("u_name"));
	              vo.setU_age(rs.getInt("u_age"));
	              vo.setU_email(rs.getString("u_email"));
	              vo.setU_phone(rs.getString("u_phone"));
	              vo.setU_birth(rs.getString("u_birth"));
	              vo.setU_address(rs.getString("u_address"));
	           }
	        } catch (Exception e) {
	           System.out.println("회원정보읽기:" + e.toString());
	        }
	        return vo;
	     }
	
	//유저읽기
    public UserVO userRead(UserVO vo) {
        try {
           String sql = "select * from userTbl where u_id=?";
           PreparedStatement ps = Database.CON.prepareStatement(sql);
           ps.setString(1, vo.getU_id());
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
              vo.setDel_user(rs.getInt("del_user"));
           }
        } catch (Exception e) {
           System.out.println("회원정보:" + e.toString());
        }
        return vo;
     }
    
    //회원 탈퇴
    public void userdelete(String u_id) {
       try {
          String sql="update userTbl set del_user=1 where u_id=?"; 
          PreparedStatement cs = Database.CON.prepareStatement(sql);
          cs.setString(1, u_id);
          cs.execute();
       }catch(Exception e) {
          System.out.println("회원탈퇴 :" +e.toString());
       }
    }

	//내 레시피삭제
    public int delete(String r_id, String u_id) {
    	int count=-1;
       try {
          String sql = "call del_myrecipe(?,?,?)";
          CallableStatement cs = Database.CON.prepareCall(sql);
          cs.setString(1, r_id);
          cs.setString(2, u_id);
          cs.registerOutParameter(3, java.sql.Types.INTEGER);
          cs.execute();
          count=cs.getInt(3);
       } catch (Exception e) {
          System.out.println("레시피삭제:" + e.toString());
       }
       return count;
    }
    
	//내 레시피 읽기
    public RecipeVO read(String r_id) {
        RecipeVO vo = new RecipeVO();
        try {
           String sql = "select * from recipeTbl where r_id=?";
           PreparedStatement ps = Database.CON.prepareStatement(sql);
           ps.setString(1, r_id);
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
              vo.setR_id(rs.getString("r_id"));
              vo.setR_name(rs.getString("r_name"));
              vo.setR_img(rs.getString("r_img"));
              vo.setR_content(rs.getString("r_content"));
           }
        } catch (Exception e) {
           System.out.println("레시피정보:" + e.toString());
        }
        return vo;
     }
    
	//내 레시피 목록출력
	public JSONObject list(SqlVO vo) {
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
				obj.put("category_id", rs.getString("category_id"));
				obj.put("u_id", rs.getString("u_id"));
				obj.put("r_name", rs.getString("r_name"));
				obj.put("r_img", rs.getString("r_img"));
				obj.put("r_content", rs.getString("r_content"));
				jArray.add(obj);
			}
			jObject.put("myrecipelist", jArray);
			
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
		
	//로그인
	public UserVO login(String id) {
		UserVO userVO=new UserVO();
		try {
			String sql="select * from userTbl where u_id=?";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				userVO.setU_id(rs.getString("u_id"));
				userVO.setU_pass(rs.getString("u_pass"));
				userVO.setU_name(rs.getString("u_name"));
				userVO.setU_age(rs.getInt("u_age"));
				userVO.setU_email(rs.getString("u_email"));
				userVO.setU_phone(rs.getString("u_phone"));
				userVO.setU_birth(rs.getString("u_birth"));
				userVO.setU_address(rs.getString("u_address"));
			}
		}catch(Exception e) {
			System.out.println("로그인 오류:" + e.toString());
		}
		return userVO;
	}
	
	//회원가입
    public JSONObject insert(UserVO vo) {
       JSONObject jObject =new JSONObject();
       try {
          String sql="call add_user(?,?,?,?,?,?,?,?,?)";
          CallableStatement cs=Database.CON.prepareCall(sql);
          cs.setString(1, vo.getU_id());
          cs.setString(2, vo.getU_pass());
          cs.setString(3, vo.getU_name());
          cs.setInt(4, vo.getU_age());
          cs.setString(5, vo.getU_email());
          cs.setString(6, vo.getU_phone());
          cs.setString(7, vo.getU_birth());
          cs.setString(8, vo.getU_address());
          cs.registerOutParameter(9, java.sql.Types.INTEGER);
          cs.execute();
          jObject.put("count", cs.getInt(9));   
       }catch(Exception e) {
          System.out.println("회원가입 오류:"+e.toString());
       }
       return jObject;
    }

}
