package ms.phecda;

import jakarta.el.*;
import org.junit.jupiter.api.Test;

public class ElTest {

    class CustomELContext extends ELContext {
        @Override
        public ELResolver getELResolver() {
            return null; // 返回自定义 EL 解析器
        }

        @Override
        public FunctionMapper getFunctionMapper() {
            return null;
        }

        @Override
        public VariableMapper getVariableMapper() {
            return null;
        }

        // 其他实现方法
    }

    @Test
    public void test() {
        ExpressionFactory factory = ExpressionFactory.newInstance();
        // 创建 EL 上下文
        ELContext context = new StandardELContext(factory); // 自定义 EL 上下文

        // 创建 EL 表达式
        ValueExpression expression = factory.createValueExpression(context, "${age > 18}", Boolean.class);

        // 评估表达式并获取布尔值结果
        boolean isAdult = (Boolean) expression.getValue(context);

        if (isAdult) {
            System.out.println("The person is an adult.");
        } else {
            System.out.println("The person is not an adult.");
        }
    }

}
