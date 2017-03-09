package site.hanschen.patterns.decorator.refactoring;

/**
 * @author HansChen
 */
public class Circle implements Shape {

    @Override
    public void draw() {
        System.out.print("圆形");
    }
}
