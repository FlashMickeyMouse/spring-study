package xin.kingsman.org.factoryBean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;
import xin.kingsman.org.bean.User;

@Component
public class MyFactoryBean implements FactoryBean<User> {
	@Override
	public User getObject() {
		return new User();
	}

	@Override
	public Class<User> getObjectType() {
		return User.class;
	}
}
