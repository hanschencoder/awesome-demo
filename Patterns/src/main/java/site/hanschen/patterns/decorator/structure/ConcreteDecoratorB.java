package site.hanschen.patterns.decorator.structure;

/**
 * @author HansChen
 */
public class ConcreteDecoratorB extends Decorator {

    public ConcreteDecoratorB(Component component) {
        super(component);
    }

    @Override
    public void operation() {
        super.operation();
        System.out.println("do something");
    }
}
