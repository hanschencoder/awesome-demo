package site.hanschen.patterns.bridge.ugly;

/**
 * @author HansChen
 */
public interface SpecialUrgencyMessage extends Message {

    void hurry(String messageId);
}
