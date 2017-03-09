package site.hanschen.patterns.decorator.ugly;

/**
 * @author HansChen
 */
public class RedTrilateral implements Shape {

    @Override
    public void draw() {
        System.out.println("红色 三角形 无阴影");
    }
}
