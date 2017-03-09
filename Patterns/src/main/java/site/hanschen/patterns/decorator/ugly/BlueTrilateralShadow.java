package site.hanschen.patterns.decorator.ugly;

/**
 * @author HansChen
 */
public class BlueTrilateralShadow implements Shape {

    @Override
    public void draw() {
        System.out.println("蓝色 三角形 有阴影");
    }
}
