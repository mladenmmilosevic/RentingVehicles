package helpentities;

public class ChangePassword {
	
	private String newpassword = null;
	private String oldpassword = null;
	  
	public ChangePassword() {
		super();
	}

	public ChangePassword(String newpassword, String oldpassword) {
		super();
		this.newpassword = newpassword;
		this.oldpassword = oldpassword;
	}
	
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getOldpassword() {
		return oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	  
	  
	  
}
