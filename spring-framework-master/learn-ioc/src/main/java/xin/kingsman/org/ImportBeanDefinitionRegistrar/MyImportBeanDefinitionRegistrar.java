package xin.kingsman.org.ImportBeanDefinitionRegistrar;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import xin.kingsman.org.bean.Fox;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		//创建BeanDefinition
		RootBeanDefinition beanDefinition = new RootBeanDefinition(Fox.class);
		//注册到容器
		registry.registerBeanDefinition("fox",beanDefinition);
	}
}
