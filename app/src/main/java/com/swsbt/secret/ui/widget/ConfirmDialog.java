package com.swsbt.secret.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.swsbt.secret.R;

public class ConfirmDialog extends Dialog {

    private TextView mDone;
    private ConfirmDialogImpl mConfirmDialogImpl;

    public ConfirmDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        setContentView(R.layout.dialog_confirm);
        findViewById(R.id.text_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mDone = findViewById(R.id.text_done);
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConfirmDialogImpl.done();
                dismiss();
            }
        });
    }

    public ConfirmDialog setConfirmDialogImpl(ConfirmDialogImpl confirmDialogImpl) {
        mConfirmDialogImpl = confirmDialogImpl;
        return this;
    }

    /**
     * 设置确定按钮的文字
     * @param text  文字
     * @return  对象
     */
    public ConfirmDialog setDoneText(String text) {
        mDone.setText(text);
        return this;
    }

    /**
     * 设置提示框的文字
     * @param text  文字
     * @return  对象
     */
    public ConfirmDialog setNoticeText(String text) {
        TextView notice = findViewById(R.id.tv_notice_text);
        notice.setText(text);
        return this;
    }

    public interface ConfirmDialogImpl {
        void done();
    }

    public ConfirmDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ConfirmDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
