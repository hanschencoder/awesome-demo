package site.hanschen.patterns.decorator.ugly;

/**
 * @author HansChen
 */
public class GreenCircle implements Shape {

    @Override
    public void draw() {
        System.out.println("绿色 圆形 无阴影");
    }
}
