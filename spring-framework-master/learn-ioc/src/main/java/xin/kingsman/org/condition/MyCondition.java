package xin.kingsman.org.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import xin.kingsman.org.bean.Fox;

public class MyCondition implements Condition {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		if(context.getBeanFactory().containsBean(Fox.class.getName()))
			return true;
		return false;
	}
}
