public class User {
	//Variables
    private String name;
    private String password;
    private String icNo;

	//Constructors
	public User(){

	}

	public User(String name,String password,String icNo){
		this.name = name;
		this.password = password;
		this.icNo = icNo;
	}

	//Setters & Getters
	public void setName(String name){
		this.name = name;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public void setIcNo(String icNo){
		this.icNo = icNo;
	}

	public String getName(){
		return name;
	}

	public String getPassword(){
		return password;
	}

	public String getIcNo(){
		return icNo;
	}

}