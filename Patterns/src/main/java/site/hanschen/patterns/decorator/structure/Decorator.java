package site.hanschen.patterns.decorator.structure;

/**
 * @author HansChen
 */
public class Decorator implements Component {

    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void operation() {
        component.operation();
    }
}
