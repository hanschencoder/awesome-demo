package ut.android.ressync;

import android.support.annotation.NonNull;

/**
 * Created by chenhang on 2016/11/3.
 */

public interface SocketManager {

    boolean isBound();

    void bind(final int port);

    void unbind();

    void registerSocketListener(@NonNull final SocketListener listener);

    void unregisterSocketListener(@NonNull final SocketListener listener);
}
