package site.hanschen.patterns.decorator.ugly;

/**
 * @author HansChen
 */
public class BlueCircle implements Shape {

    @Override
    public void draw() {
        System.out.println("蓝色 圆形 无阴影");
    }
}
