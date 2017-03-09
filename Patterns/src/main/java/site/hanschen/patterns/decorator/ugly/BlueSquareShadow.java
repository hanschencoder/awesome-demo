package site.hanschen.patterns.decorator.ugly;

/**
 * @author HansChen
 */
public class BlueSquareShadow implements Shape {

    @Override
    public void draw() {
        System.out.println("蓝色 正方形 有阴影");
    }
}
