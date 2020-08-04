package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.Database;
import recipe.RecipeVO;

public class MainDAO {
	//한식정보
		public RecipeVO read(String r_id) {
			RecipeVO vo=new RecipeVO();
			try {
				String sql="select * from recipeTbl";
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
				System.out.println("한식정보:" + e.toString());
			}
			return vo;
		}
}
