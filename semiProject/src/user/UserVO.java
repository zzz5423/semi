package user;

public class UserVO {
	private String u_id;
	private String u_pass;
	private String u_name;
	private int u_age;
	private String u_email;
	private String u_phone;
	private String u_birth;
	private String u_address;
	private int del_user;
	
	public int getDel_user() {
		return del_user;
	}
	public void setDel_user(int del_user) {
		this.del_user = del_user;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getU_pass() {
		return u_pass;
	}
	public void setU_pass(String u_pass) {
		this.u_pass = u_pass;
	}
	public String getU_name() {
		return u_name;
	}
	public void setU_name(String u_name) {
		this.u_name = u_name;
	}
	public int getU_age() {
		return u_age;
	}
	public void setU_age(int u_age) {
		this.u_age = u_age;
	}
	public String getU_email() {
		return u_email;
	}
	public void setU_email(String u_email) {
		this.u_email = u_email;
	}
	public String getU_phone() {
		return u_phone;
	}
	public void setU_phone(String u_phone) {
		this.u_phone = u_phone;
	}
	public String getU_birth() {
		return u_birth;
	}
	public void setU_birth(String u_birth) {
		this.u_birth = u_birth;
	}
	public String getU_address() {
		return u_address;
	}
	public void setU_address(String u_address) {
		this.u_address = u_address;
	}
	@Override
	public String toString() {
		return "UserVO [u_id=" + u_id + ", u_pass=" + u_pass + ", u_name=" + u_name + ", u_age=" + u_age + ", u_email="
				+ u_email + ", u_phone=" + u_phone + ", u_birth=" + u_birth + ", u_address=" + u_address + ", del_user="
				+ del_user + "]";
	}
}
