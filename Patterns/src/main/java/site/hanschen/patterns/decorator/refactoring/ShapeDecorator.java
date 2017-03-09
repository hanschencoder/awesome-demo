package site.hanschen.patterns.decorator.refactoring;

/**
 * @author HansChen
 */
public class ShapeDecorator implements Shape {

    private Shape shape;

    public ShapeDecorator(Shape shape) {
        this.shape = shape;
    }

    @Override
    public void draw() {
        shape.draw();
    }
}
