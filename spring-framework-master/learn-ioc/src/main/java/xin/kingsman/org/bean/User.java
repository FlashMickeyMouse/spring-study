package xin.kingsman.org.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class User {

	@Autowired
	private Fox fox;

//	public User(){
//		System.out.println("======User()======");
//	}


//	/**
//	 * Autowired注解可以用于参数上面
//	 * @param fox
//	 */
//	public User(@Autowired Fox fox){
//		System.out.println("======User(@Autowired Fox fox)======");
//	}
//
//	public Fox getFox() {
//		return fox;
//	}
//
//	@Autowired
//	public void setFox(Fox fox) {
//		System.out.println(fox+"======setFox(Fox fox)=====");
//		this.fox = fox;
//	}
}
