package flag.com.tw.ch11_player4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import flag.com.tw.ch11_player4.R;

public class Video extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    VideoView vdv;
    int pos = 0; // 用來紀錄前次播放位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // 設定為全螢幕
        getSupportActionBar().hide(); // 隱藏標題列

        setContentView(R.layout.activity_video);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent it = getIntent();

        Uri uri = Uri.parse(it.getStringExtra("uri")); // 取得要播放影片的Uri

        if(savedInstanceState != null){ // 因為旋轉兒啟動activity
            pos = savedInstanceState.getInt("pos", 0); // 取得旋轉前儲存的播放位置
        }

        vdv = (VideoView) findViewById(R.id.videoView);

        MediaController mediaCtri = new MediaController(this); // 建立播放控制元件

        vdv.setMediaController(mediaCtri); // 設定播放控制元件
        vdv.setVideoURI(uri); // 設定播放影片的Uri
        vdv.setOnCompletionListener(this);
    }

    @Override
    protected void onResume(){ // Activity啟動或暫停時
        super.onResume();

        vdv.seekTo(pos); // 移到pos位置
        vdv.start(); // 開始播放
    }

    @Override
    protected void onPause(){ // Activity進入暫停時
        super.onPause();

        pos = vdv.getCurrentPosition(); // 儲存播放位置
        vdv.stopPlayback(); // 停止播放
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("pos", pos); // 將onPause所取得的播放位置儲存到Bundle
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        finish();
    }
}
