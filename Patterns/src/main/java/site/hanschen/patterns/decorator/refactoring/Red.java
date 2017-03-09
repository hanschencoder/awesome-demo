package site.hanschen.patterns.decorator.refactoring;

/**
 * @author HansChen
 */
public class Red extends ShapeDecorator {

    public Red(Shape shape) {
        super(shape);
    }

    @Override
    public void draw() {
        super.draw();
        System.out.print(" 红色");
    }
}
