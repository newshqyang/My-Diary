package com.swsbt.secret.ui.widget;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.swsbt.secret.R;

public class ItemDiaryPopupMenu extends PopupMenu implements PopupMenu.OnMenuItemClickListener {

    private ItemDiaryPopupMenuImpl mImpl;

    public ItemDiaryPopupMenu(Context context, View anchor) {
        super(context, anchor);
        inflate(R.menu.item_diary_menu);
        setOnMenuItemClickListener(this);
    }

    public ItemDiaryPopupMenu(Context context, View anchor, int gravity) {
        super(context, anchor, gravity);
    }

    public ItemDiaryPopupMenu(Context context, View anchor, int gravity, int popupStyleAttr, int popupStyleRes) {
        super(context, anchor, gravity, popupStyleAttr, popupStyleRes);
    }

    public void setImpl(ItemDiaryPopupMenuImpl impl) {
        mImpl = impl;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_diary_edit:
                mImpl.edit();
                break;
            case R.id.item_diary_delete:
                mImpl.delete();
                break;
        }
        return false;
    }

    public interface ItemDiaryPopupMenuImpl {
        void edit();
        void delete();
    }
}
