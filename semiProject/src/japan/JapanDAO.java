package japan;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import database.Database;
import database.SqlVO;
import recipe.RecipeVO;

public class JapanDAO {	
	//�Ͻ�����
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
				}
		}catch(Exception e) {
			System.out.println("�Ͻ�����:" + e.toString());
		}
		return vo;
	}
			
	//�Ͻĸ�����
	public JSONObject list(SqlVO vo) {
		JSONObject jObject=new JSONObject();
		try {
			String sql="call list(?,?,?,?,?,?,?)";
			CallableStatement cs=Database.CON.prepareCall(sql);
			cs.setString(1, "(select * from recipeTbl where category_id='j')tbl");
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
				obj.put("u_id", rs.getString("u_id"));
				jArray.add(obj);
			}
			jObject.put("list", jArray);
			
			cs.getMoreResults();
			rs=cs.getResultSet();
			int count=0;
			if(rs.next()) {
				count=rs.getInt("total");
			}
			int perPage=vo.getPerPage();
			int totPage=count%perPage==0?count/perPage:(count/perPage)+1;
			jObject.put("count", count);
			jObject.put("totPage", totPage);
			jObject.put("perPage", perPage);
			jObject.put("page", vo.getPage());
		}catch(Exception e) {
			System.out.println("�Ͻĸ��:" + e.toString());
		}
		return jObject;
	}
}
