package site.hanschen.patterns.decorator.refactoring;

/**
 * @author HansChen
 */
public class Square implements Shape {

    @Override
    public void draw() {
        System.out.print("正方形");
    }
}
