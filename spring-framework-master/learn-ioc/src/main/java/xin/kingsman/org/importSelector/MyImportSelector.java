package xin.kingsman.org.importSelector;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import xin.kingsman.org.bean.Fox;


public class MyImportSelector implements ImportSelector {
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		// 仅仅支持byType 注意此时 Fox 类上并没有任何注解
		return new String[]{Fox.class.getName()};
	}
}
