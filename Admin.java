public class Admin extends User{
	//Variables
	private String adminId;
	private String position;

	//Constructors
	public Admin(){

	}

	public Admin(String name,String password,String icNo,String adminId,String position){
		super(name,password,icNo);

		this.adminId = adminId;
		this.position = position;
	}

	//Setters and getters
	public void setAdminId(String adminId){
		this.adminId = adminId;
	}

	public void setPosition(String position){
		this.position = position;
	}

	public String getAdminId(){
		return adminId;
	}

	public String getPosition(){
		return position;
	}

}
