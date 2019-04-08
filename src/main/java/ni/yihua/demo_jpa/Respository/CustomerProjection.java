package ni.yihua.demo_jpa.Respository;

import org.springframework.beans.factory.annotation.Value;


public interface CustomerProjection {
    /**
     * 这里声明的方式是可以直接通过get+属性名，这是普通的，另外也可以通过@Value注解来实现指定字段，
     * 除了指定字段也可以做聚合展示，比如有些地方需要展示客户的全名，这
     * 里定义的getFullName()方法及注解@Value即完成这一操作。
     * 需要注意这里的@Value中的target表达式写法及拼接方法。
     * @return
     */
    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();

    String getFirstName();

    String getLastName();
}