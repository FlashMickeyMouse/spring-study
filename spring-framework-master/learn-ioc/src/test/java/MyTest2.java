import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xin.kingsman.org.bean.User;
import xin.kingsman.org.service.Myservice;
import xin.kingsman.org.service.OneService;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * bean的注入离不开BeanDefinition，而BeanDefinition 需要从不同的配置中读取，读取后传给注册器
 * 关键性的两步就是1.读取 2.注册
 */
public class MyTest2 {
	@Test
	public void test(){
		//创建一个简单的bean定义注册器
		BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		//创建一个bean定义读取器
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
		reader.loadBeanDefinitions("spring.xml");
		System.out.println(Arrays.toString(registry.getBeanDefinitionNames()));
		System.out.println(Arrays.toString(registry.getBeanDefinition("user1").getDependsOn()));
		//从这个注册器中仅仅能拿到BeanDefinition而不能拿到实例化之后的bean
	}

	@Test
	public void test2(){
		/**
		 * @see DefaultListableBeanFactory
		 * 既是一个注册器 又是一个beanFactory
		 * 既可以注册BeanDefinition 又可以拿bean
		 */
		BeanDefinitionRegistry registry = new DefaultListableBeanFactory();
		//创建一个bean定义读取器
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
		reader.loadBeanDefinitions("spring.xml");
		System.out.println(Arrays.toString(registry.getBeanDefinitionNames()));
		ConfigurableListableBeanFactory beanFactory = (ConfigurableListableBeanFactory) registry;
		System.out.println(beanFactory.getBean("user1"));
	}

	/**
	 * 我们可以用它这个特点手动注入配置中没有的bean
	 */
	@Test
	public void test3(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		//拿到工厂其实也是一个注册器
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		//org.springframework.beans.factory.support.DefaultListableBeanFactory@27c86f2d: defining beans [xin.kingsman.org.bean.User#0,user2,user1]; root of factory hierarchy
		System.out.println(beanFactory);
		//因为beanFactory的实际类型是DefaultListableBeanFactory
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;

		//创建一个BeanDefinition
		RootBeanDefinition beanDefinition = new RootBeanDefinition(Myservice.class);

		//注册
		defaultListableBeanFactory.registerBeanDefinition("myservice",beanDefinition);

		//填充属性  注意属性需要设置 setter
		beanDefinition.getPropertyValues().add("dao","xxxxDao");

		//设置构造器贪婪模式 选择参数最多的构造方法  ，注意如果调用参数最多的构造方法构造失败则会调用剩余中参数最小的构造器
		/**
		 * 我这边的例子是 容器中有多个user类型的bean，
		 *
		 * <bean class="xin.kingsman.org.bean.User"></bean>
		 * <bean id="user1" class="xin.kingsman.org.bean.User"></bean>
		 *
		 * 而调用
		 * public Myservice(User user) {
		 * 		System.out.println("======Myservice(User user)======");
		 * 		System.out.println(user);
		 * 		this.user = user;
		 *        }
		 *这个的时候会按照类型传入user，由于有多个合格的类型所以，spring不知道选择哪一个导致构造失败
		 * 然后会自动调用无参构造
		 * 然而当修改xml文件为
		 * <bean id="user1" class="xin.kingsman.org.bean.User" primary="true"></bean>
		 * 时，则会正常注入 因为指定user1为主要的bean
		 *
		 * 为什么不能用@Qualifier("user1")呢？
		 * 因为myservice是我们手动通过注册器注入的并且手动填充对象的 容器感知不到此注解，所以此场景下并没有什么用
		 */
		beanDefinition.setAutowireMode(3);


		Myservice myservice = (Myservice)beanFactory.getBean("myservice");
		System.out.println(myservice);
		System.out.println(myservice.getDao());
	}

	@Test
	public void test4(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		AutowireCapableBeanFactory beanFactory = context.getAutowireCapableBeanFactory();
		/**
		 * @see DefaultListableBeanFactory
		 */
		System.out.println(beanFactory);
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
		RootBeanDefinition beanDefinition = new RootBeanDefinition(OneService.class);
		registry.registerBeanDefinition("oneService",beanDefinition);


		//注意参数上无需添加 @Autowired 注解，还是那句话，我们此时时手动添加的beanDefinition容器根本不会感知到类似@Autowired的注解
//		beanDefinition.setAutowireMode(1);//按照名称装配 基于规范的setter
//		beanDefinition.setAutowireMode(2); //set开头即可 基于set方法的参数类型即可
		System.out.println(beanFactory.getBean("oneService"));
	}

	@Test
	public void test5(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring1.xml");
		System.out.println(context.getBean(User.class));
		System.out.println(context.getBean(OneService.class));
		/**
		 * 输出如下 可以看到xml注入的方式并不会扫描到注解
		 * ======User()======
		 * xin.kingsman.org.bean.User@4c1d9d4b
		 * OneService{user=null}
		 */
	}

	@Test
	public void test6() throws NoSuchAlgorithmException {
		// 生成一个MD5加密计算摘要
		MessageDigest md = MessageDigest.getInstance("MD5");
		// 计算md5函数
		md.update("songhao".getBytes());
		// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
		// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
		System.out.println(new BigInteger(1, md.digest()).toString(16));
	}


}
