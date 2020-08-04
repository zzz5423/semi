package database;

public class SqlVO{
	private String key;
	private String word;
	private String desc;
	private String order;
	private int page;
	private int perPage;
	private String u_id;
	
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPerPage() {
		return perPage;
	}
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	@Override
	public String toString() {
		return "SqlVO [key=" + key + ", word=" + word + ", desc=" + desc + ", order=" + order + ", page=" + page
				+ ", perPage=" + perPage + ", u_id=" + u_id + "]";
	}
}
