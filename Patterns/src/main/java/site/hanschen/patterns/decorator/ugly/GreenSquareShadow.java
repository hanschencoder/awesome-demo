package site.hanschen.patterns.decorator.ugly;

/**
 * @author HansChen
 */
public class GreenSquareShadow implements Shape {

    @Override
    public void draw() {
        System.out.println("绿色 正方形 有阴影");
    }
}
