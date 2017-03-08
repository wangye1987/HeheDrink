package com.heheys.ec.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.heheys.ec.R;
import com.heheys.ec.camera.CameraManager;
import com.heheys.ec.decoding.CaptureActivityHandler;
import com.heheys.ec.decoding.InactivityTimer;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.dataBean.ScanResultBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.thirdPartyModule.payModule.JDPayActivity;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.view.ViewfinderView;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.apache.http.Header;

import java.io.IOException;
import java.util.Vector;

import static com.heheys.ec.netWorkHelper.UrlsUtil.H5URL_ORDER;

/**
 * Created by wangkui on 2016/12/8.
 * <p>
 * 扫描界面
 */

public class MipcaActivityCapture extends Activity implements SurfaceHolder.Callback {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private Button btn_cancel_scan;
    private ScanResultBean resultBean;
    private MessageHandler scanHandler;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        //ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        btn_cancel_scan = (Button) findViewById(R.id.btn_cancel_scan);
        btn_cancel_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        scanHandler = new MessageHandler(this);
    }
    public static class MessageHandler extends
            WeakHandler<MipcaActivityCapture> {

        public MessageHandler(MipcaActivityCapture reference) {
            super(reference);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantsUtil.HTTP_SUCCESS:
                    //获取扫描结果
                    ScanResultBean.ResultScan result = (ScanResultBean.ResultScan) msg.obj;
                    if(result != null){
                        if(result.getUrl().contains(H5URL_ORDER)) {
                            String oid = result.getUrl().substring(result.getUrl().indexOf("oid=")+4,result.getUrl().length());
                            Intent intent  = new Intent(getReference(), MyOrderDetailActivity.class);
                            intent.putExtra("oid", oid);
                            //传递1 是需要调取货到付款接口
                            intent.putExtra("OrderFrom", "1");
                            StartActivityUtil.startActivity(getReference(), intent);
                        }else{
                                getReference().GoH5(result.getUrl(), "确认收货");
                            }
                    }
                    break;
                case ConstantsUtil.HTTP_FAILE:
                    //获取扫描结果错误
                    ScanResultBean.ErrorScan error = (ScanResultBean.ErrorScan) msg.obj;
                    if(error != null){
                        ToastUtil.showToast(getReference(),error.getInfo());
                    }
                    break;
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }


    private void GoH5(String url,String title){
        Intent intent = new Intent(MipcaActivityCapture.this,JDPayActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("flag", 5);
        StartActivityUtil.startActivity(MipcaActivityCapture.this, intent);
    }
    /**
     * 处理扫描结果
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (resultString.equals("")) {
            Toast.makeText(MipcaActivityCapture.this, "扫描失败!", Toast.LENGTH_SHORT).show();
        } else {
            ApiHttpCilent.getInstance(getApplicationContext()).ScanZxing(this, resultString, new MyScanBaseJsonHttpResponseHandler());
//            Intent resultIntent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putString("result", resultString);
//            bundle.putParcelable("bitmap", barcode);
//            resultIntent.putExtras(bundle);
//            this.setResult(RESULT_OK, resultIntent);
//            ToastUtil.showToast(this,resultString);
        }
//        MipcaActivityCapture.this.finish();
    }

    /**
     * 获取扫描结果
     * */

    public class MyScanBaseJsonHttpResponseHandler extends BaseJsonHttpResponseHandler<ScanResultBean> {


        @Override
        public void onSuccess(int i, Header[] headers, String result, ScanResultBean scanResultBean) {
            Dimess();
        }

        @Override
        public void onFailure(int i, Header[] headers, Throwable throwable, String s, ScanResultBean scanResultBean) {
            Dimess();
            Message msg = new Message();
            msg.obj = new ScanResultBean.ErrorScan();
            scanHandler.obtainMessage(ConstantsUtil.HTTP_FAILE,msg);
        }

        @Override
        protected ScanResultBean parseResponse(String result, boolean b) throws Throwable {
            Dimess();
            Gson gson = new Gson();
            resultBean = gson.fromJson(result,ScanResultBean.class);
            if(resultBean !=null){
                String status = resultBean.getStatus();
                Message msg = new Message();
                if(status.equals("1")){
                    msg.obj = resultBean.getResult();
                    msg.what = ConstantsUtil.HTTP_SUCCESS;
                }else{
                    msg.obj = resultBean.getError();
                    msg.what = ConstantsUtil.HTTP_FAILE;
                }
                scanHandler.sendMessage(msg);
            }
            return resultBean;
        }

    }
    private void Dimess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(ApiHttpCilent.loading != null && ApiHttpCilent.loading.isShowing())
                ApiHttpCilent.loading.dismiss();
            }
        });


    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

}
