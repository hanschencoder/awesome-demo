package site.hanschen.patterns.decorator.structure;

/**
 * @author HansChen
 */
public class ConcreteComponent implements Component {

    @Override
    public void operation() {
        System.out.print("do something");
    }
}
