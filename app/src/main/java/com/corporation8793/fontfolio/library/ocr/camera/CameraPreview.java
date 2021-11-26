package com.corporation8793.fontfolio.library.ocr.camera;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.corporation8793.fontfolio.library.ocr.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 사용자 정의 카메라
 * Created by Administrator on 2016/12/8.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Camera.AutoFocusCallback {
    private static final String TAG = "CameraPreview";

    private int viewWidth = 0;
    private int viewHeight = 0;

    /**
     * 카메라 촬영 상태 인터페이스
     */
    public interface OnCameraStatusListener {
        // 카메라 촬영 종료 이벤트
        void onCameraStopped(byte[] data);
    }

    /**
     * 촬영 상태 인터페이스
     */
    private OnCameraStatusListener listener;

    private SurfaceHolder holder;
    private Camera camera;
    private FocusView mFocusView;

    // PictureCallback 객체 만들어서 onPictureTaken 메소드 구현
    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {

        // 촬영 후, 사진 데이터 처리에 필요
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // 사진 촬영 중지
            try {
                camera.stopPreview();
            } catch (Exception e) {
            }
            // 호출 종료 이벤트
            if (null != listener) {
                listener.onCameraStopped(data);
            }
        }
    };

    // Preview
    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        // SurfaceHolder 객체
        holder = getHolder();
        // SurfaceHolder 의 Callback 객체 지정
        holder.addCallback(this);
        // 객체 타입 설정
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        setOnTouchListener(onTouchListener);
    }

    // surface 를 만들 때 활성화
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e(TAG, "== surfaceCreated ==");
        if (!Utils.checkCameraHardware(getContext())) {
            Toast.makeText(getContext(), "카메라를 켜는데 실패했습니다!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "== surfaceCreated : error ==");
            return;
        }
        // Camera 객체 가져오기
        camera = getCameraInstance();
        try {
            // 사진 촬영을 보여주기 위한 SurfaceHolder 객체 설정
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
            // 카메라 객체 릴리즈
            camera.release();
            camera = null;
        }
        updateCameraParameters();
        if (camera != null) {
            camera.startPreview();
        }
        setFocus();
    }

    // surface 제거
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e(TAG, "==surfaceDestroyed==");
        // 카메라 객체 릴리즈
        camera.release();
        camera = null;
    }

    // surface 의 크기가 바뀌었을 때 활성화
    public void surfaceChanged(final SurfaceHolder holder, int format, int w,
                               int h) {
        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }
        // set preview size and make any resize, rotate or
        // reformatting changes here
        updateCameraParameters();
        // start preview with new settings
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
        setFocus();
    }

    /**
     * 초점 영역
     */
    OnTouchListener onTouchListener = new OnTouchListener() {
        @SuppressWarnings("deprecation")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                int width = mFocusView.getWidth();
                int height = mFocusView.getHeight();
                mFocusView.setX(event.getX() - (width / 2));
                mFocusView.setY(event.getY() - (height / 2));
                mFocusView.beginFocus();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                focusOnTouch(event);
            }
            return true;
        }
    };

    /**
     * 카메라 인스턴스 가져오기
     *
     * @return
     */
    private Camera getCameraInstance() {
        Camera c = null;
        try {
            int cameraCount = 0;
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            cameraCount = Camera.getNumberOfCameras(); // get cameras number

            for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
                // get camerainfo
                Camera.getCameraInfo(camIdx, cameraInfo);
                // 대표 카메라의 위치, 현재 정의값은 캠에 따라 전면, 후면 카메라 두 개
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    try {
                        // 후면 카메라 켜기
                        c = Camera.open(camIdx);
                    } catch (RuntimeException e) {
                        //Toast.makeText(getContext(), "카메라를 켜는데 실패했습니다!", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "== getCameraInfo : error ==");
                    }
                }
            }
            if (c == null) {
                c = Camera.open(0); // attempt to get a Camera instance
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "카메라를 켜는데 실패했습니다!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "== getCameraInstance : error ==");
        }
        return c;
    }

    private void updateCameraParameters() {
        if (camera != null) {
            Camera.Parameters p = camera.getParameters();

            setParameters(p);

            try {
                camera.setParameters(p);
            } catch (Exception e) {
                Camera.Size previewSize = findBestPreviewSize(p);
                p.setPreviewSize(previewSize.width, previewSize.height);
                p.setPictureSize(previewSize.width, previewSize.height);
                camera.setParameters(p);
            }
        }
    }

    /**
     * @param p
     */
    private void setParameters(Camera.Parameters p) {
        List<String> focusModes = p.getSupportedFocusModes();
        if (focusModes
                .contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            p.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }

        long time = new Date().getTime();
        p.setGpsTimestamp(time);
        // 사진 형식 설정
        p.setPictureFormat(PixelFormat.JPEG);
        Camera.Size previewSize = findPreviewSizeByScreen(p);
        p.setPreviewSize(previewSize.width, previewSize.height);
        p.setPictureSize(previewSize.width, previewSize.height);
        p.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

//        if (isTransverse) {
//            if (getContext().getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
//                camera.setDisplayOrientation(90);
//                p.setRotation(90);
//            }
//        } else {
            if (getContext().getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                camera.setDisplayOrientation(90);
            }
//        }
    }

    // 사진 촬영 후, 사진을 PictureCallback 으로 불러와서 onPictureTaken
    public void takePicture() {
        if (camera != null) {
            try {
                camera.takePicture(null, null, pictureCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 카메라 상태 리스너
    public void setOnCameraStatusListener(OnCameraStatusListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {

    }

    public void start() {
        if (camera != null) {
            camera.startPreview();
        }
    }

    public void stop() {
        if (camera != null) {
            camera.stopPreview();
        }
    }


    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        viewWidth = MeasureSpec.getSize(widthSpec);
        viewHeight = MeasureSpec.getSize(heightSpec);
        super.onMeasure(
                MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY));
    }

    /**
     * 미리 보기 크기를 화면 크기로 설정
     *
     * @param parameters
     * @return
     */
    private Camera.Size findPreviewSizeByScreen(Camera.Parameters parameters) {
        if (viewWidth != 0 && viewHeight != 0) {
            return camera.new Size(Math.max(viewWidth, viewHeight),
                    Math.min(viewWidth, viewHeight));
        } else {
            return camera.new Size(Utils.getScreenWH(getContext()).heightPixels,
                    Utils.getScreenWH(getContext()).widthPixels);
        }
    }

    /**
     * 적절한 디스플레이 해상도 찾기 (미리보기 사진의 변형 방지)
     *
     * @param parameters
     * @return
     */
    private Camera.Size findBestPreviewSize(Camera.Parameters parameters) {

        // 시스템에서 지원하는 모든 미리보기 해상도
        String previewSizeValueString = null;
        previewSizeValueString = parameters.get("preview-size-values");

        if (previewSizeValueString == null) {
            previewSizeValueString = parameters.get("preview-size-value");
        }

        if (previewSizeValueString == null) {
            // 일부 휴대전화는 미리보기 크기를 얻지 못하면 화면 크기로 되돌려줍니다
            return camera.new Size(Utils.getScreenWH(getContext()).widthPixels,
                    Utils.getScreenWH(getContext()).heightPixels);
        }
        float bestX = 0;
        float bestY = 0;

        float tmpRadio = 0;
        float viewRadio = 0;

        if (viewWidth != 0 && viewHeight != 0) {
            viewRadio = Math.min((float) viewWidth, (float) viewHeight)
                    / Math.max((float) viewWidth, (float) viewHeight);
        }

        String[] COMMA_PATTERN = previewSizeValueString.split(",");
        for (String prewsizeString : COMMA_PATTERN) {
            prewsizeString = prewsizeString.trim();

            int dimPosition = prewsizeString.indexOf('x');
            if (dimPosition == -1) {
                continue;
            }

            float newX = 0;
            float newY = 0;

            try {
                newX = Float.parseFloat(prewsizeString.substring(0, dimPosition));
                newY = Float.parseFloat(prewsizeString.substring(dimPosition + 1));
            } catch (NumberFormatException e) {
                continue;
            }

            float radio = Math.min(newX, newY) / Math.max(newX, newY);
            if (tmpRadio == 0) {
                tmpRadio = radio;
                bestX = newX;
                bestY = newY;
            } else if (tmpRadio != 0 && (Math.abs(radio - viewRadio)) < (Math.abs(tmpRadio - viewRadio))) {
                tmpRadio = radio;
                bestX = newX;
                bestY = newY;
            }
        }

        if (bestX > 0 && bestY > 0) {
            return camera.new Size((int) bestX, (int) bestY);
        }
        return null;
    }

    /**
     * 초점 및 측광 영역 설정
     *
     * @param event
     */
    public void focusOnTouch(MotionEvent event) {

        int[] location = new int[2];
        RelativeLayout relativeLayout = (RelativeLayout) getParent();
        relativeLayout.getLocationOnScreen(location);

        Rect focusRect = Utils.calculateTapArea(mFocusView.getWidth(),
                mFocusView.getHeight(), 1f, event.getRawX(), event.getRawY(),
                location[0], location[0] + relativeLayout.getWidth(), location[1],
                location[1] + relativeLayout.getHeight());
        Rect meteringRect = Utils.calculateTapArea(mFocusView.getWidth(),
                mFocusView.getHeight(), 1.5f, event.getRawX(), event.getRawY(),
                location[0], location[0] + relativeLayout.getWidth(), location[1],
                location[1] + relativeLayout.getHeight());

        Camera.Parameters parameters = camera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

        if (parameters.getMaxNumFocusAreas() > 0) {
            List<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
            focusAreas.add(new Camera.Area(focusRect, 1000));

            parameters.setFocusAreas(focusAreas);
        }

        if (parameters.getMaxNumMeteringAreas() > 0) {
            List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
            meteringAreas.add(new Camera.Area(meteringRect, 1000));

            parameters.setMeteringAreas(meteringAreas);
        }

        try {
            camera.setParameters(parameters);
        } catch (Exception e) {
        }
        camera.autoFocus(this);
    }

    /**
     * 초점을 맞출 사진 설정
     *
     * @param focusView
     */
    public void setFocusView(FocusView focusView) {
        this.mFocusView = focusView;
    }

    /**
     * 자동으로 초점을 맞추고, 초점을 맞출 링을 화면 중앙에 표시합니다
     */
    public void setFocus() {
        if (!mFocusView.isFocusing()) {
            try {
                camera.autoFocus(this);
                mFocusView.setX((Utils.getWidthInPx(getContext()) - mFocusView.getWidth()) / 2);
                mFocusView.setY((Utils.getHeightInPx(getContext()) - mFocusView.getHeight()) / 2);
                mFocusView.beginFocus();
            } catch (Exception e) {
            }
        }
    }
}
