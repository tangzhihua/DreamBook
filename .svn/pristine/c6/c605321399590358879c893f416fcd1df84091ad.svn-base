package cn.retech.activity;

import kr.co.netntv.player4ux.Player4UxView;
import kr.co.netntv.player4ux.PlayerCore;
import kr.co.netntv.player4ux.VideoViewCustom;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import cn.retech.toolutils.DebugLog;

public class SecondActivity extends Activity {
	public static final String PREFERENCE_FILE = "preference_file";
	public static final String ORIENTATION = "orientation";
	private final String TAG = "Player4UxVideoActivity";
	private VideoViewCustom video_view;
	private int screen_width, screen_height;
	private int media_width, media_height;
	private boolean landscape;
	public static final String EXTRA_ZIP_FILE = "EXTRA_ZIP_FILE";
	private String mZipFilepath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		DebugLog.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);

		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.player4ux_video);

		Intent i = getIntent();
		mZipFilepath = i.getStringExtra(EXTRA_ZIP_FILE);
		readDocument(mZipFilepath);

	}

	private Handler handler = new Handler();
	private Player4UxView mGLView;
	private String mContentsPath;

	private void readDocument(final String filePath) {

		handler.post(new Runnable() {
			@Override
			public void run() {

				Log.e(TAG, "read before = " + filePath);
				boolean bSuccess = PlayerCore.readDocument(filePath);
				mContentsPath = filePath;
				DebugLog.e(TAG, bSuccess + "");
				Log.e(TAG, "read after");
				int orientation = PlayerCore.getOrientation();
				SharedPreferences sharedPreferences = getSharedPreferences(SecondActivity.PREFERENCE_FILE, MODE_PRIVATE);
				Editor editor = sharedPreferences.edit();
				editor.putInt(ORIENTATION, orientation);
				editor.commit();

				if (orientation == 0) {
					Log.e(TAG, "CONTENTS: LANDSCAPE");
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				} else if (orientation == 1) {
					Log.e(TAG, "CONTENTS: PORTRAIT");

					// for rotation top to bottom
					Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
					if (display.getRotation() == Surface.ROTATION_0) {
						setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					} else if (display.getRotation() == Surface.ROTATION_180) {
						setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
					}
				}
				applyAspectRatio();
			}
		});
	}

	private void applyAspectRatio() {
		View main_frame = findViewById(R.id.main_frame);

		int contentsWidth = PlayerCore.getDocumentWidth();
		int contentsHeight = PlayerCore.getDocumentHeight();
		float contentsAspectRatio = (float) contentsWidth / contentsHeight;
		int w = main_frame.getWidth();
		int h = main_frame.getHeight();
		float terminalAspectRatio = (float) w / h;

		Log.e(TAG, "contents = " + contentsAspectRatio + ", w = " + contentsWidth + ", h = " + contentsHeight);
		Log.e(TAG, "terminal = " + terminalAspectRatio + ", w = " + w + ", h = " + h);
		initPlayer4UxView();
		LayoutParams params = (LayoutParams) mGLView.getLayoutParams();
		if (terminalAspectRatio < 1.0f) { // portraithttps://dreambook.retechcorp.com/dreambook
			if (terminalAspectRatio < contentsAspectRatio)
				applySize(false, contentsWidth, contentsHeight, w, h, params);
			else
				applySize(true, contentsWidth, contentsHeight, w, h, params);
		} else {
			if (terminalAspectRatio < contentsAspectRatio)
				applySize(false, contentsWidth, contentsHeight, w, h, params);
			else
				applySize(true, contentsWidth, contentsHeight, w, h, params);
		}
		Log.e(TAG, "params = " + (float) params.width / params.height + ", w = " + params.width + ", h = " + params.height);
		mGLView.requestLayout();
	}

	private void applySize(boolean bKeepHeight, int contentsWidth, int contentsHeight, int w, int h, LayoutParams params) {
		if (bKeepHeight) {
			params.gravity = Gravity.CENTER;
			params.height = h;
			float ratio = (float) h / contentsHeight;
			params.width = (int) (ratio * contentsWidth + 0.5f);
		} else {
			params.gravity = Gravity.CENTER;
			params.width = w;
			float ratio = (float) w / contentsWidth;
			params.height = (int) (ratio * contentsHeight + 0.5f);
		}
	}

	private void initPlayer4UxView() {

		FrameLayout parentView = (FrameLayout) findViewById(R.id.main_frame);

		mGLView = new Player4UxView(SecondActivity.this, getApplication(), true, 16, 8, mContentsPath, parentView, SecondActivity.class);
		parentView.addView(mGLView);
	}

	@Override
	protected void onStart() {
		DebugLog.i(TAG, "onStart");
		super.onStart();
	}

	@Override
	protected void onRestart() {
		DebugLog.i(TAG, "onRestart");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		DebugLog.i(TAG, "onResume");
		if (mGLView != null) {
			mGLView.onResume();
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		DebugLog.i(TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		DebugLog.i(TAG, "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		DebugLog.i(TAG, "onDestroy");

		super.onDestroy();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Log.e(TAG, "onWindowFocusChanged hasFocus = " + hasFocus);
		applyAspectRatio();
		super.onWindowFocusChanged(hasFocus);
	}
}
