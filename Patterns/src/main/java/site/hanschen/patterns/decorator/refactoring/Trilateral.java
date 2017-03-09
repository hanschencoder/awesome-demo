package site.hanschen.patterns.decorator.refactoring;

/**
 * @author HansChen
 */
public class Trilateral implements Shape {

    @Override
    public void draw() {
        System.out.print("三角形");
    }
}
