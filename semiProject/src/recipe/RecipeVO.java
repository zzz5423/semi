package recipe;

public class RecipeVO {
	private String r_id;
	private String category_id;
	private String u_id;
	private String r_name;
	private String r_img;
	private String r_content;
	
	public String getR_id() {
		return r_id;
	}
	public void setR_id(String r_id) {
		this.r_id = r_id;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getR_name() {
		return r_name;
	}
	public void setR_name(String r_name) {
		this.r_name = r_name;
	}
	public String getR_img() {
		return r_img;
	}
	public void setR_img(String r_img) {
		this.r_img = r_img;
	}
	public String getR_content() {
		return r_content;
	}
	public void setR_content(String r_content) {
		this.r_content = r_content;
	}
	@Override
	public String toString() {
		return "recipeVO [r_id=" + r_id + ", category_id=" + category_id + ", u_id=" + u_id + ", r_name=" + r_name
				+ ", r_img=" + r_img + ", r_content=" + r_content + "]";
	}
}
