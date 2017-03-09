package site.hanschen.patterns.decorator.ugly;

/**
 * @author HansChen
 */
public class GreenTrilateral implements Shape {

    @Override
    public void draw() {
        System.out.println("绿色 三角形 无阴影");
    }
}
