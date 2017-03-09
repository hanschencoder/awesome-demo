package site.hanschen.patterns.decorator.ugly;

/**
 * @author HansChen
 */
public class GreenCircleShadow implements Shape {

    @Override
    public void draw() {
        System.out.println("绿色 圆形 有阴影");
    }
}
