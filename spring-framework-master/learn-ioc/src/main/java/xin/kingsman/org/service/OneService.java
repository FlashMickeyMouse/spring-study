package xin.kingsman.org.service;


import xin.kingsman.org.bean.User;

public class OneService {

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser1(User user) {
		System.out.println("======setUser1(User user)======");
		this.user = user;
	}

	@Override
	public String toString() {
		return "OneService{" +
				"user=" + user +
				'}';
	}
}
