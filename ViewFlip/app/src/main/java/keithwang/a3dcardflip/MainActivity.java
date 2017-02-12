package keithwang.a3dcardflip;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private RelativeLayout wLay_ViewOneContent, wLay_ViewTwoContent;
    private Boolean mFlipViewOne = true;
    private Boolean mViewFlipRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewInit();
        viewFlipInit();
        setViewListener();
    }

    private void viewInit() {
        mContext = MainActivity.this;
        wLay_ViewOneContent = (RelativeLayout) findViewById(R.id.layout_main_view_one_content);
        wLay_ViewTwoContent = (RelativeLayout) findViewById(R.id.layout_main_view_two_content);
    }

    private void setViewListener() {
        wLay_ViewOneContent.setOnClickListener(mOnClickListener);
        wLay_ViewTwoContent.setOnClickListener(mOnClickListener);
    }

    private void viewFlipInit() {
        float scale = mContext.getResources().getDisplayMetrics().density;
        wLay_ViewOneContent.setCameraDistance(8000 * scale);
        wLay_ViewTwoContent.setCameraDistance(8000 * scale);
    }

    private void viewFlip() {
        if (!mViewFlipRunning) {
            AnimatorSet setOut = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.circle_flip_out);
            AnimatorSet setIn = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.circle_flip_in);

            setOut.addListener(sAnimatorListener);
            if (mFlipViewOne) {
                setOut.setTarget(wLay_ViewOneContent);
                setIn.setTarget(wLay_ViewTwoContent);
                mFlipViewOne = false;
            } else {
                setOut.setTarget(wLay_ViewTwoContent);
                setIn.setTarget(wLay_ViewOneContent);
                mFlipViewOne = true;
            }
            setOut.start();
            setIn.start();
        }
    }

    Animator.AnimatorListener sAnimatorListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {
            mViewFlipRunning = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mViewFlipRunning = false;
        }

        @Override
        public void onAnimationCancel(Animator animation) {}

        @Override
        public void onAnimationRepeat(Animator animation) {}

    };

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.layout_main_view_one_content:
                case R.id.layout_main_view_two_content:
                    viewFlip();
                    break;
            }
        }
    };

}
