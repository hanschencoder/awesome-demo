package site.hanschen.patterns.decorator.ugly;

/**
 * @author HansChen
 */
public class BlueSquare implements Shape {

    @Override
    public void draw() {
        System.out.println("蓝色 正方形 无阴影");
    }
}
