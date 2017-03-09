package site.hanschen.patterns.decorator.ugly;

/**
 * @author HansChen
 */
public class GreenTrilateralShadow implements Shape {

    @Override
    public void draw() {
        System.out.println("绿色 三角形 有阴影");
    }
}
