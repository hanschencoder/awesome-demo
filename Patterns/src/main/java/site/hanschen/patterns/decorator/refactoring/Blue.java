package site.hanschen.patterns.decorator.refactoring;

/**
 * @author HansChen
 */
public class Blue extends ShapeDecorator {

    public Blue(Shape shape) {
        super(shape);
    }

    @Override
    public void draw() {
        super.draw();
        System.out.print(" 蓝色");
    }
}
