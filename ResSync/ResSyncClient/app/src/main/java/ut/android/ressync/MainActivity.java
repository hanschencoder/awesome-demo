package ut.android.ressync;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import ut.android.ressync.application.ClientApplication;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context        mContext;
    private TextView       mStatus;
    private EditText       mPort;
    private Button         mConfirmBtn;
    private Button         mMessageTest;
    private MaterialDialog mWaitingDialog;

    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        initViews();

        //延时一秒等待ProtocolManager绑定成功
        mMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getSocketManager().registerSocketListener(mSocketListener);
            }
        }, TimeUnit.SECONDS.toMillis(1));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSocketManager().unregisterSocketListener(mSocketListener);
    }

    private void initViews() {
        mStatus = (TextView) findViewById(R.id.status);
        mPort = (EditText) findViewById(R.id.input_port);
        mConfirmBtn = (Button) findViewById(R.id.confirm_btn);
        mMessageTest = (Button) findViewById(R.id.message_test);
        mConfirmBtn.setOnClickListener(MainActivity.this);
        mMessageTest.setOnClickListener(MainActivity.this);

        if (isConnected()) {
            mStatus.setText("已监听本地端口.");
            changeBtnState(true);
        }
    }

    private void changeBtnState(boolean syncing) {
        if (syncing) {
            mConfirmBtn.setText("停止同步");
            mMessageTest.setVisibility(View.VISIBLE);
        } else {
            mConfirmBtn.setText("开始同步");
            mMessageTest.setVisibility(View.GONE);
        }
    }

    private boolean isConnected() {
        return getSocketManager() != null && getSocketManager().isBound();
    }

    private SocketManager getSocketManager() {
        return ClientApplication.getInstance().getServerManager();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_btn:
                if (isConnected()) {
                    getSocketManager().unbind();
                } else {
                    if (TextUtils.isEmpty(mPort.getText())) {
                        Toast.makeText(MainActivity.this, "请先输入监听端口", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    getSocketManager().bind(Integer.parseInt(mPort.getText().toString()));
                }
                break;
            case R.id.message_test:
                ClientApplication.getInstance()
                                 .getServerManager()
                                 .reportMobileInfo(Build.MODEL,
                                                   Build.DEVICE,
                                                   Build.PRODUCT,
                                                   Build.MANUFACTURER,
                                                   Build.VERSION.RELEASE,
                                                   Build.VERSION.SDK_INT);
                break;
            default:
                break;
        }
    }

    private SocketListener mSocketListener = new SocketListener() {
        @Override
        public void onBindStart() {
            mStatus.setText("开始绑定端口...");
            showWaitingDialog("请等待", "开始绑定端口...");
        }

        @Override
        public void onBindSuccess() {
            mStatus.setText("已监听本地端口");
            changeBtnState(true);
            dismissWaitingDialog();
        }

        @Override
        public void onBindFail() {
            mStatus.setText("绑定端口失败");
            changeBtnState(false);
            dismissWaitingDialog();
        }

        @Override
        public void onUnbind() {
            mStatus.setText("未开始监听");
            changeBtnState(false);
        }

        @Override
        public void onClientAdd(int currentNum) {
            mStatus.setText(String.format(Locale.getDefault(), "正在监听，已有 %d 台设备连接到手机", currentNum));
        }

        @Override
        public void onClientRemove(int currentNum) {
            if (getSocketManager().isBound()) {
                mStatus.setText(String.format(Locale.getDefault(), "正在监听，已有 %d 台设备连接到手机", currentNum));
            }
        }
    };


    private boolean isWaitingDialogShowing() {
        return mWaitingDialog != null && mWaitingDialog.isShowing();
    }

    public void showWaitingDialog(String title, String content) {
        if (!isWaitingDialogShowing()) {
            mWaitingDialog = new MaterialDialog.Builder(mContext).title(title)
                                                                 .content(content)
                                                                 .progress(true, 0)
                                                                 .progressIndeterminateStyle(true)
                                                                 .build();
            mWaitingDialog.setCancelable(false);
            mWaitingDialog.show();
        }
    }

    public void dismissWaitingDialog() {
        if (isWaitingDialogShowing()) {
            mWaitingDialog.dismiss();
        }
    }
}
