package com.kwave.android.memohand;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    private Paint paint= new Paint();
    RadioGroup radioGroup;
    RadioButton red, green, blue, white;
    SeekBar seekBar;
    FrameLayout frameLayout;
    Board board;
    Button btnSave, back;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        red = (RadioButton) findViewById(R.id.btnRed);
        green = (RadioButton) findViewById(R.id.btnGreen);
        blue = (RadioButton) findViewById(R.id.btnBlue);
        white = (RadioButton) findViewById(R.id.btnWhite);


        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        // 저장버튼
        btnSave = (Button) findViewById(R.id.btnSave);
        back = (Button) findViewById(R.id.back);
        // 썸네일 이미지뷰
        imageView = (ImageView) findViewById(R.id.imageView);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.btnRed:
                        setPaint(Color.RED);
                        break;
                    case R.id.btnGreen:
//                        Paint paint1 = new Paint();
                        setPaint(Color.GREEN);
                        break;
                    case R.id.btnBlue:
//                        Paint paint2 = new Paint();
                        setPaint(Color.BLUE);
                        break;
                    case R.id.btnWhite:
//                        Paint paint2 = new Paint();
                        setPaint(Color.WHITE);
                        break;
                }
            }
        });
//        radioGroup.check(R.id.btnRed);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
//        setStroke();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
//                paint.setStyle(Paint.Style.STROKE);
                    setStroke();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count =board.pathList.size();
                board.pathList.remove(count-1);
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 드로잉 캐쉬를 지워주고
                frameLayout.destroyDrawingCache();

                // 다시 만들고
                frameLayout.buildDrawingCache();

                // 레이아웃의 그려진 내용을 Bitmap 형태로 가져온다.
                Bitmap capture = frameLayout.getDrawingCache();

                //캡쳐한 이미지를 썸네일에 보여준다.
                imageView.setImageBitmap(capture);
//
//                // 캡쳐를 할 뷰의 캐쉬를 사용한다.
//                frameLayout.setDrawingCacheEnabled(true);





            }
        });


        // 1. 보드를 새로 생성한다.
        board = new Board(getBaseContext());
        // 3. 생성된 보드를 화면에 세팅한다.
        frameLayout.addView(board);
//        setPaint(Color.BLACK);
    }
        private void setPaint(int ColorType){
            // 2. 붓을 만들어서 보드에 담는다
            Paint paint = new Paint();
            paint.setColor(ColorType);

            // 선을 채우지 않고 굵기를 지정
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);

            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setDither(true);

            paint.setStrokeWidth(seekBar.getProgress());
            board.setPaint(paint);
        }
    private void setStroke() {
        // 2. 붓을 만들어서 보드에 담는다
        Paint paint = new Paint();
        paint.setColor(paint.getColor());
        // 선을 채우지 않고 굵기를 지정
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setDither(true);

        paint.setStrokeWidth(seekBar.getProgress());
        board.setPaint(paint);
    }

public void setting(){
    Paint paint = new Paint();

    paint.setColor(paint.getColor());
    // 선을 채우지 않고 굵기를 지정
    paint.setStyle(Paint.Style.STROKE);
    paint.setAntiAlias(true);

    paint.setStrokeJoin(Paint.Join.ROUND);
    paint.setStrokeCap(Paint.Cap.ROUND);
    paint.setDither(true);

    paint.setStrokeWidth(seekBar.getProgress());
    board.setPaint(paint);
}

//    @Override
//    protected void onResume() {
//        super.onResume();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainActivity.this, "어떤때 나오는 것일까?", Toast.LENGTH_SHORT).show();
//            }
//        }, 5000);
//    }


//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//        }
//    };

}

class Brush{
    Paint paint;
    Path path;
    int color;
    float stroke;
}




class Board extends View {
        Paint paint;
        Path current_path;
    FrameLayout frameLayout;
    ImageView imageView;

    public ArrayList<Brush> pathList = new ArrayList<>();

    public Board(Context context) {
        super(context);
//            path = new Path();
    }

//    public Board(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    protected void onDraw(Canvas canvas) {      // onDraw는 what에  해당   // canvas는 how에 해당
        super.onDraw(canvas);
        for(Brush brush : pathList){
            canvas.drawPath(brush.path,brush.paint);
        }
    }

//    MainActivity getPaintMemo(){
//        return (MainActivity) getContext();
//    }
//    Paint getPaint(){
//        return getPaintMemo().getPaint();
//    }
//    public void setPaint(Paint paint) {
//        this.paint = paint;
//    }

//    Pair<Path, Paint> pair;
//    Path path = null;
//    Paint paint = null;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        // 내가 터치한 좌표를 꺼낸다.
            float x = event.getX();
            float y = event.getY();

//            Log.e("LOG", "onTouchEvent=============x");
//            Log.e("LOG", "onTouchEvent=============y");

        switch(event.getAction()){
            case MotionEvent.ACTION_POINTER_DOWN :
            case MotionEvent.ACTION_DOWN :
//                    path.moveTo(x,y);       // 이전 점과 현재 점 사이를 그리지 않고 이동한다.
                Brush brush = new Brush();
               current_path = new Path();
                Log.e("LOG", "onTouchEvent=============down");
                brush.path = current_path;

                brush.paint = paint;
//                paint.setColor(getPaint().getColor());
//                paint.setStrokeWidth(getPaint().getStrokeWidth());
//                paint.setStyle(Paint.Style.STROKE);

                pathList.add(brush);
                current_path.moveTo(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_MOVE :
//                    path.lineTo(x,y);       // 바로 이전 점과 현재 점 사이에 줄을 그어준다.
//                if(brush != null){
                    current_path.lineTo(event.getX(), event.getY());
                    Log.e("LOG", "onTouchEvent=============move");
                    invalidate();
//                }
                return true;
            case MotionEvent.ACTION_UP :
                Log.e("LOG", "onTouchEvent=============up");
                current_path.lineTo(x,y);       // 바로 이전 점과 현재 점 사이에 줄을 그어준다.
                break;
            case MotionEvent.ACTION_POINTER_UP :
                break;
        }

        // Path를 그린 후 화면을 갱신해서 반영해준다.
        invalidate();       // 메인쓰레드를 갱신할때는 invalidate를 쓰고 서브 쓰레드를 갱신 할 때는 PostInvalidate를 사용한다.

        // 리턴이 false가 되면 touch 이벤트를 연속해서 발생시키지 않는다.
        // 즉, 드래그 시 onTouchEvent가 호출되지 않는다.
//        invalidate();
        return true;
    }



    public Paint getPaint() {
        return paint;
    }
}
