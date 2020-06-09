package xin.kingsman.org.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import xin.kingsman.org.bean.User;

public class Myservice {

	public Myservice() {
		System.out.println("======Myservice()======");
	}


	private User user;

	public Myservice( User user) {
		System.out.println("======Myservice(User user)======");
		System.out.println(user);
		this.user = user;
	}

	public Myservice( User user,User user1,User user2) {
		System.out.println("======Myservice( User user,User user1,User user2) 1======");
		System.out.println(user);
		this.user = user;
	}

	public Myservice( User user,User user1,User user2,String strBean) {
		System.out.println("======Myservice( User user,User user1,User user2,String name)======");
		System.out.println(user);
		System.out.println(strBean);
		this.user = user;
	}

	private String dao;


	public void query(){
		System.out.println("Myservice#query");
	}

	public String getDao() {
		return dao;
	}

	public void setDao(String dao) {
		this.dao = dao;
	}
}
