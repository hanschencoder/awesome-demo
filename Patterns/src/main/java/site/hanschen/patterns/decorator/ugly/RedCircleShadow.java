package site.hanschen.patterns.decorator.ugly;

/**
 * @author HansChen
 */
public class RedCircleShadow implements Shape {

    @Override
    public void draw() {
        System.out.println("红色 圆形 有阴影");
    }
}
