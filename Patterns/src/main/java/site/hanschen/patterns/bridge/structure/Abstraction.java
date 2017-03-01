package site.hanschen.patterns.bridge.structure;

/**
 * @author HansChen
 */
public abstract class Abstraction {

    /**
     * 持有一个实现部分的对象
     */
    Implementor impl;

    /**
     * 构造方法，传入实现部分的对象
     *
     * @param impl 实现部分的对象
     */
    Abstraction(Implementor impl) {
        this.impl = impl;
    }

    public void operation() {
        impl.operationImpl();
    }
}
