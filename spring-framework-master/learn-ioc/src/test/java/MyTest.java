import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import xin.kingsman.org.bean.Fox;
import xin.kingsman.org.bean.User;
import xin.kingsman.org.config.AppConfig;
import xin.kingsman.org.service.Myservice;
import xin.kingsman.org.service.UserService;


/**
 * 为什么要用IOC 1.依赖对象多次创建 ：单例  简单工厂   2.依赖关系复杂 ：外部传入 构造器 setter 反射
 * IOC容器设计理念：通过容器统一对象的构建方式，并自动维护对象的依赖关系
 */
public class MyTest {
	@Test
	public void test1() {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		System.out.println(context.getBean(Fox.class));
		System.out.println(context.getBean(Fox.class));
		System.out.println(context.getBean("user"));
	}

	@Test
	public void test2() {
		ApplicationContext context =
				new ClassPathXmlApplicationContext("spring.xml");
		System.out.println(context.getBean(User.class));
	}

	@Test
	public void test3() {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		System.out.println(context.getBean("user")); //xin.kingsman.org.bean.User@10e31a9a
		System.out.println(context.getBean("myFactoryBean"));//xin.kingsman.org.bean.User@131774fe
		System.out.println(context.getBean("&myFactoryBean"));//xin.kingsman.org.factoryBean.MyFactoryBean@158d2680
	}


	@Test
	public void test4() {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		/**
		 * 当AppConfig上面有Configuration注解时候
		 * System.out.println(context.getBean("myservice"));
		 * System.out.println(context.getBean(Myservice.class));
		 * xin.kingsman.org.service.Myservice@7e07db1f
		 * xin.kingsman.org.service.Myservice@7e07db1f
		 */
		//
		/**
		 * 当AppConfig上面没有Configuration注解时候
		 * xin.kingsman.org.service.Myservice@10e92f8f
		 * xin.kingsman.org.service.Myservice@10e92f8f
		 */
		System.out.println(context.getBean("myservice"));
		System.out.println(context.getBean(Myservice.class));
	}

	@Test
	public void test5() {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		/**
		 * 当AppConfig上面没有Configuration注解时候
		 * xin.kingsman.org.service.Myservice@47d90b9e@Bean myservice()
		 * xin.kingsman.org.service.Myservice@1184ab05@Bean myservice()
		 * xin.kingsman.org.service.Myservice@1184ab05@Bean userService()
		 * xin.kingsman.org.service.Myservice@1184ab05UserService#UserService()
		 * UserService#UserService(Myservice myservice)
		 * xin.kingsman.org.service.Myservice@47d90b9eget
		 * ==
		 * xin.kingsman.org.service.UserService@4c9f8c13get
		 * ************************************
		 * 当AppConfig上面有Configuration注解时候
		 * xin.kingsman.org.service.Myservice@5158b42f@Bean myservice()
		 * xin.kingsman.org.service.Myservice@5158b42f@Bean userService()
		 * xin.kingsman.org.service.Myservice@5158b42fUserService#UserService()
		 * UserService#UserService(Myservice myservice)
		 * xin.kingsman.org.service.Myservice@5158b42fget
		 * ==
		 * xin.kingsman.org.service.UserService@7f0eb4b4get
		 * -----------------------------------------------------------
		 * 不加Configuration的时候调用两次并且得到的对象是不同的，
		 * 而当加了Configuration的时候只调用了一次得到的对象都相同，
		 * 原因是当我们在别的方法中调用被@bean标记的方法时候，他会直接从容器中获取，而不是再次调用此方法
		 *
		 * 加了Configuration 的类会被增强 cglib增强类，调用其中的factory method生成bean时 会被拦截（方法前后）
		 * 具体实现在 ConfigurationClassEnhancer.class 中
		 * @see org.springframework.context.annotation.ConfigurationClassEnhancer#CALLBACKS
		 *
		 * 其实不仅仅会生成增强类，还将appConfig的{@link BeanDefinition}中的属性设置为了full（不配置是lite）
		 */


		System.out.println(context.getBean(Myservice.class)+"get");
		System.out.println("==");
		System.out.println(context.getBean(UserService.class)+"get");
	}

	@Test
	public void test6() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		System.out.println(context.getBean(Fox.class));
		/**
		 * org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'fox' available
		 * @see xin.kingsman.org.importSelector.MyImportSelector#selectImports(AnnotationMetadata)
		 */
		System.out.println(context.getBean("fox"));
	}
	@Test
	public void test7() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		/**
		 * @see xin.kingsman.org.ImportBeanDefinitionRegistrar.MyImportBeanDefinitionRegistrar#registerBeanDefinitions(AnnotationMetadata, BeanDefinitionRegistry)
		 */
		System.out.println(context.getBean(Fox.class));
		System.out.println(context.getBean("fox"));
	}

	@Test
	public void test8() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		System.out.println(context.getBean(Fox.class));
//		/**
//		 * org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'fox' available
//		 * @Import(Fox.class)
//		 */
//		System.out.println(context.getBean("fox"));
	}

	@Test
	public void test9() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		/**
		 * @see xin.kingsman.org.condition.MyCondition#matches(ConditionContext, AnnotatedTypeMetadata)
		 */
		System.out.println(context.getBean("user"));
		System.out.println(context.getBean("fox"));
	}

	@Test
	public void test10(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		System.out.println(context.getBean("user1"));
	}
}
