package xin.kingsman.org.config;

import org.springframework.context.annotation.*;
import org.springframework.core.annotation.AliasFor;
import xin.kingsman.org.ImportBeanDefinitionRegistrar.MyImportBeanDefinitionRegistrar;
import xin.kingsman.org.bean.Fox;
import xin.kingsman.org.bean.User;
import xin.kingsman.org.condition.MyCondition;
import xin.kingsman.org.factoryBean.MyFactoryBean;
import xin.kingsman.org.importSelector.MyImportSelector;
import xin.kingsman.org.service.Myservice;
import xin.kingsman.org.service.UserService;

@ComponentScan("xin.kingsman.org.bean")
@Configuration
//@Import(MyImportSelector.class)
//@Import(MyImportBeanDefinitionRegistrar.class)
//@Import(Fox.class)
public class AppConfig {
//	@Bean  //method bean
//	public Myservice myservice() {
//		Myservice myservice = new Myservice();
//		System.out.println(myservice+"@Bean myservice()");
//		return myservice;
//	}
//
//	@Bean
//	public UserService userService() {
//		Myservice myservice = myservice();
//		System.out.println(myservice+"@Bean userService()");
//		return new UserService(myservice);
//	}

//	@Bean
//	public Fox fox(){
//		return  new Fox();
//	}
//
//	@Bean
//	public MyFactoryBean myFactoryBean(){
//		return new MyFactoryBean();
//	}
//
//	@Bean
//	@Conditional(MyCondition.class)
//	public User user(){
//		return new User();
//	}

}
