package bookmark;

public class BookmarkVO {
   private String u_id;
   private String r_id;
   private String b_name;
   private String b_img;
   private String b_content;
   
   public String getU_id() {
      return u_id;
   }
   public void setU_id(String u_id) {
      this.u_id = u_id;
   }
   public String getR_id() {
      return r_id;
   }
   public void setR_id(String r_id) {
      this.r_id = r_id;
   }
   public String getB_name() {
      return b_name;
   }
   public void setB_name(String b_name) {
      this.b_name = b_name;
   }
   public String getB_img() {
      return b_img;
   }
   public void setB_img(String b_img) {
      this.b_img = b_img;
   }
   public String getB_content() {
      return b_content;
   }
   public void setB_content(String b_content) {
      this.b_content = b_content;
   }
   @Override
   public String toString() {
      return "bookmarkVO [u_id=" + u_id + ", r_id=" + r_id + ", b_name=" + b_name + ", b_img=" + b_img
            + ", b_content=" + b_content + "]";
   }
}