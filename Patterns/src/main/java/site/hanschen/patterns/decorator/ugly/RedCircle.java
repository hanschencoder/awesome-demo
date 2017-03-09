package site.hanschen.patterns.decorator.ugly;

/**
 * @author HansChen
 */
public class RedCircle implements Shape {

    @Override
    public void draw() {
        System.out.println("红色 圆形 无阴影");
    }
}
