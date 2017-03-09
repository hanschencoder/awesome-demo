package site.hanschen.patterns.decorator.refactoring;

/**
 * @author HansChen
 */
public class Shadow extends ShapeDecorator {

    public Shadow(Shape shape) {
        super(shape);
    }

    @Override
    public void draw() {
        super.draw();
        System.out.print(" 有阴影");
    }
}
