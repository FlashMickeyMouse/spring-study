package xin.kingsman.org.service;

public class UserService {
	private Myservice myservice;

	public UserService(Myservice myservice) {
		System.out.println(myservice+"UserService#UserService()");
		System.out.println("UserService#UserService(Myservice myservice)");
		this.myservice = myservice;
	}
}
