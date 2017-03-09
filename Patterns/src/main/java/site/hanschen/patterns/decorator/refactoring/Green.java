package site.hanschen.patterns.decorator.refactoring;

/**
 * @author HansChen
 */
public class Green extends ShapeDecorator {

    public Green(Shape shape) {
        super(shape);
    }

    @Override
    public void draw() {
        super.draw();
        System.out.print(" 绿色");
    }
}
