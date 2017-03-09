package site.hanschen.patterns.decorator;

import site.hanschen.patterns.decorator.refactoring.Blue;
import site.hanschen.patterns.decorator.refactoring.Circle;
import site.hanschen.patterns.decorator.refactoring.Green;
import site.hanschen.patterns.decorator.refactoring.Red;
import site.hanschen.patterns.decorator.refactoring.Shadow;
import site.hanschen.patterns.decorator.refactoring.Shape;
import site.hanschen.patterns.decorator.refactoring.Square;
import site.hanschen.patterns.decorator.refactoring.Trilateral;

/**
 * @author HansChen
 */
public class Test {

    public static void main(String[] args) {
        //正方形 红色 有阴影
        Shape shape = new Square();
        shape = new Red(shape);
        shape = new Shadow(shape);
        shape.draw();

        //圆形 绿色
        shape = new Circle();
        shape = new Green(shape);
        shape.draw();

        //三角形 蓝色 有阴影
        shape = new Trilateral();
        shape = new Blue(shape);
        shape = new Shadow(shape);
        shape.draw();
    }
}
