package site.hanschen.patterns.decorator.ugly;

/**
 * @author HansChen
 */
public class RedSquareShadow implements Shape {

    @Override
    public void draw() {
        System.out.println("红色 正方形 有阴影");
    }
}
