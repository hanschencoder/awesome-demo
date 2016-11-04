package ut.android.ressync;

/**
 * Created by chenhang on 2016/11/3.
 */
public interface SocketListener {

    void onBindStart();

    void onBindSuccess();

    void onBindFail();

    void onUnbind();

    void onClientAdd(int currentNum);

    void onClientRemove(int currentNum);
}
