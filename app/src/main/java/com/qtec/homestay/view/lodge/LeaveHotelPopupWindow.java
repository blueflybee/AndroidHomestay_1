package com.qtec.homestay.view.lodge;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qtec.homestay.R;

/**
 * <pre>
 *      author: xiehao
 *      e-mail: xieh@qtec.cn
 *      time: 2017/06/07
 *      desc: 离店 弹窗
 *      version: 1.0
 * </pre>
 */

public class LeaveHotelPopupWindow extends PopupWindow implements View.OnClickListener {
    private View mView;
    private TextView mDialogTitle,mDialogContent;
    private Button mDialogPos,mDialogNeg;
    private RelativeLayout mRelayoutClose;
    private int mIndex = 0;

    OnPositiveClickListener mOnPositiveClickListener;

    public void setOnPositiveClickListener(OnPositiveClickListener onPositiveClickListener) {
        mOnPositiveClickListener = onPositiveClickListener;
    }

    public LeaveHotelPopupWindow(final Context context,String roomNo,String name,String time,String pwd) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.dialog_leave_hotel, null);

        mRelayoutClose = (RelativeLayout) mView.findViewById(R.id.rl_close);
        mDialogPos = (Button) mView.findViewById(R.id.dialog_pos);
        mDialogNeg = (Button) mView.findViewById(R.id.dialog_neg);
        mDialogTitle = (TextView) mView.findViewById(R.id.dialog_title);
        mDialogContent = (TextView) mView.findViewById(R.id.dialog_content);

        mDialogTitle.setText("离店");
        mDialogPos.setText("确认");
        mDialogNeg.setText("取消");
        mDialogContent.setText("房间号："+roomNo+"\n"+"客户："+name+"\n"+"入住时间："+time+"\n"+"当前密码："+pwd);

        mDialogNeg.setOnClickListener(this);
        mDialogPos.setOnClickListener(this);
        mRelayoutClose.setOnClickListener(this);

        this.setContentView(mView);
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        this.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        //this.setAnimationStyle(R.style.PopScaleAnimation);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);

        // mView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mView.findViewById(R.id.exit_outer_layout).getTop();
                int bottom = mView.findViewById(R.id.exit_outer_layout).getBottom();
                int left = mView.findViewById(R.id.exit_outer_layout).getLeft();
                int right = mView.findViewById(R.id.exit_outer_layout).getRight();
                int y = (int) event.getY();
                int x = (int) event.getX();
                // ACTION_UP 离开触屏
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height || x < left || x > right || y > bottom) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.rl_close:
//                dismiss();
//                break;

            case R.id.dialog_pos:
                if (mOnPositiveClickListener != null) {
                    mOnPositiveClickListener.onPositiveClick(mIndex);
                    dismiss();
                }
                break;

            case R.id.dialog_neg:
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface OnPositiveClickListener {
        void onPositiveClick(int index);
    }


    public LinearLayout getOuterLayout() {
        return (LinearLayout) mView.findViewById(R.id.exit_outer_layout);
    }

}
