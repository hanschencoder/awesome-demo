package site.hanschen.patterns.decorator.structure;

/**
 * @author HansChen
 */
public class ConcreteDecoratorA extends Decorator {

    public ConcreteDecoratorA(Component component) {
        super(component);
    }

    @Override
    public void operation() {
        super.operation();
        System.out.println("do something");
    }
}
