package site.hanschen.patterns.decorator.ugly2;

/**
 * @author HansChen
 */
public class ShapeImpl implements Shape {

    enum Type {
        Circle,
        Square,
        Trilatera
    }

    enum Color {
        Red,
        Green,
        Blue
    }

    private Type    type;
    private Color   color;
    private boolean shadow;

    public ShapeImpl() {
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isShadow() {
        return shadow;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    @Override
    public void draw() {
        // TODO: 2017/3/9 根据属性情况画图...
        switch (type) {
            case Circle:
                break;
            case Trilatera:
                break;
            case Square:
                break;
        }
    }
}
