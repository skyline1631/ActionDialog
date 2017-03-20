/*
 * Copyright (C) 2017 Legolas Kwok.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.skyline.widget.dialog;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

/**
 * A subclass of Dialog that can display a set of action buttons, which
 * provides alternative choices to complete a task initiated by user.
 */
public class ActionDialog extends Dialog
                          implements View.OnClickListener,
                                     AdapterView.OnItemClickListener {
    // INSTANCES
    private TextView mTextTitle = null;
    private TextView mTextMessage = null;
    private TextView mTextCancel = null;
    private LinearLayout mLayoutHeader = null;
    private RelativeLayout mLayoutCancel = null;
    private ListView mListViewContent = null;
    private ListItemAdapter mListItemAdapter = null;
    private OnEventListener mEventListener = null;
    private ArrayList<ActionItem> mListActionItems = new ArrayList<>();

    // CLASSES
    public static class ActionItem {
        public boolean destructive = false;
        public String title = null;
        public Object key = 0;
    }

    private class ListItemAdapter extends ArrayAdapter<ActionItem> {
        private LayoutInflater mLayoutInflater = null;

        public ListItemAdapter(Context context, ArrayList<ActionItem> items) {
            super(context, 0, items);

            mLayoutInflater = LayoutInflater.from(getContext());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.sky_list_item_action, parent, false);
            }

            final ActionItem item = getItem(position);
            final TextView textTitle = (TextView)convertView.findViewById(R.id.sky_action_item_text_title);
            textTitle.setText(item.title);

            if (item.destructive) {
                textTitle.setTextColor(getColor(R.color.sky_action_dialog_red));
            } else {
                textTitle.setTextColor(getColor(R.color.sky_action_dialog_blue));
            }

            return convertView;
        }

        private int getColor(int colorId) {
            return getContext().getResources().getColor(colorId);
        }
    }

    // INTERFACES
    public interface OnEventListener {
        void onActionItemClick(ActionDialog dialog, ActionItem item, int position);
        void onCancelItemClick(ActionDialog dialog);
    }

    // IMPLEMENTS
    public ActionDialog(Context context) {
        super(context, R.style.SkyActionDialog);

        setupViews();
        setupWindow();
    }

    @Override
    public void onClick(View v) {
        if (v == mLayoutCancel) {
            dismiss();

            if (mEventListener != null) {
                mEventListener.onCancelItemClick(this);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mEventListener != null) {
            mEventListener.onActionItemClick(this, mListActionItems.get(position), position);
        }

        dismiss();
    }

    /**
     * Set the title text for this dialog's window.
     *
     * @param text The new text to display in the title.
     */
    @Override
    public void setTitle(CharSequence text) {
        super.setTitle(text);

        setTitle(text.toString());
    }

    /**
     * Set the title text for this dialog's window. The text is retrieved
     * from the resources with the supplied identifier.
     *
     * @param textId The title's text resource identifier
     */
    @Override
    public void setTitle(int textId) {
        super.setTitle(textId);

        setTitle(getContext().getString(textId));
    }

    /**
     * Set the title text for this dialog's window. The text is retrieved
     * from the resources with the supplied identifier.
     *
     * @param text The new text to display in the title.
     */
    public void setTitle(String text) {
        if (!TextUtils.isEmpty(text)) {
            mTextTitle.setText(text);
            mTextTitle.setVisibility(View.VISIBLE);
        } else {
            mTextTitle.setVisibility(View.GONE);
        }

        updateViews();
    }

    /**
     * Set the message text for this dialog's window. The text is retrieved
     * from the resources with the supplied identifier.
     *
     * @param textId The message's text resource identifier
     */
    public void setMessage(int textId) {
        setMessage(getContext().getString(textId));
    }

    /**
     * Set the message text for this dialog's window.
     *
     * @param text The new text to display in the message.
     */
    public void setMessage(CharSequence text) {
        setMessage(text.toString());
    }

    /**
     * Set the message text for this dialog's window.
     *
     * @param text The new text to display in the message.
     */
    public void setMessage(String text) {
        if (!TextUtils.isEmpty(text)) {
            mTextMessage.setText(text);
            mTextMessage.setVisibility(View.VISIBLE);
        } else {
            mTextMessage.setVisibility(View.GONE);
        }

        updateViews();
    }

    /**
     * Set the cancel text for this dialog's window. The text is retrieved
     * from the resources with the supplied identifier.
     *
     * @param textId The cancel's text resource identifier
     */
    public void setCancelText(int textId) {
        setCancelText(getContext().getString(textId));
    }

    /**
     * Set the cancel text for this dialog's window.
     *
     * @param text The new text to display in the cancel action.
     */
    public void setCancelText(CharSequence text) {
        setCancelText(text.toString());
    }

    /**
     * Set the cancel text for this dialog's window.
     *
     * @param text The new text to display in the cancel action.
     */
    public void setCancelText(String text) {
        mTextCancel.setText(text);
    }

    /**
     * Sets whether the cancel action item should be visible.
     *
     * @param visible Whether the cancel action item should be visible
     */
    public void setCancelVisible(boolean visible) {
        ((View)mLayoutCancel.getParent()).setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * Set the callback that will be called if an action item is clicked.
     *
     * @param listener The listener that will be called.
     */
    public void setEventListener(OnEventListener listener) {
        mEventListener = listener;
    }

    /**
     * Append the specified item to the end of the dialog.
     *
     * @param item The action item object to be appended
     */
    public void addAction(ActionItem item) {
        mListActionItems.add(item);
    }

    /**
     * Appends the specified item to the end of the dialog.
     *
     * @param textId The text resource identifier of the action item's title
     */
    public void addAction(int textId) {
        addAction(getContext().getString(textId));
    }

    /**
     * Append the specified item to the end of the dialog.
     *
     * @param title The title of the action item
     */
    public void addAction(String title) {
        addAction(title, null);
    }

    /**
     * Append the specified item to the end of the dialog.
     *
     * @param title The title of the action item
     * @param key The key of the action item
     */
    public void addAction(String title, Object key) {
        ActionItem item = new ActionItem();
        item.title = title;
        item.key = key;
        mListActionItems.add(item);
        mListItemAdapter.notifyDataSetChanged();
    }

    /**
     * Append the specified item by string to the end of the list.
     *
     * @param title The title of the action item
     * @param destructive Whether the action should be showed as destructive style
     */
    public void addAction(String title, boolean destructive) {
        ActionItem item = new ActionItem();
        item.title = title;
        item.destructive = destructive;
        mListActionItems.add(item);
        mListItemAdapter.notifyDataSetChanged();
    }

    /**
     * Set a list of action items to be displayed in the dialog.
     *
     * @param arrayId The text array resource identifier for the action titles
     */
    public void setActions(int arrayId) {
        setActions(getContext().getResources().getStringArray(arrayId));
    }

    /**
     * Set a list of action items to be displayed in the dialog.
     *
     * @param titles The text array for the action titles
     */
    public void setActions(String[] titles) {
        mListActionItems.clear();

        for (String title : titles) {
            addAction(title);
        }
    }

    /**
     * Remove all of the action items from the dialog.
     *
     */
    public void clearActions() {
        mListActionItems.clear();
        mListItemAdapter.notifyDataSetChanged();
    }

    private void setupViews() {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        final View viewContent = inflater.inflate(R.layout.sky_dialog_action, null);
        setContentView(viewContent);

        // Header
        mLayoutHeader = (LinearLayout)viewContent.findViewById(R.id.sky_action_dialog_layout_header);
        mLayoutHeader.setVisibility(View.GONE);

        // Title
        mTextTitle = (TextView)viewContent.findViewById(R.id.sky_action_dialog_text_title);
        mTextTitle.setVisibility(View.GONE);

        // Message
        mTextMessage = (TextView)viewContent.findViewById(R.id.sky_action_dialog_text_message);
        mTextMessage.setVisibility(View.GONE);

        // Adapter
        mListItemAdapter = new ListItemAdapter(getContext(), mListActionItems);

        // List
        mListViewContent = (ListView)viewContent.findViewById(R.id.sky_action_dialog_list_content);
        mListViewContent.setAdapter(mListItemAdapter);
        mListViewContent.setOnItemClickListener(this);

        // Cancel
        mLayoutCancel = (RelativeLayout)viewContent.findViewById(R.id.sky_action_dialog_layout_cancel);
        mLayoutCancel.setOnClickListener(this);

        mTextCancel = (TextView)viewContent.findViewById(R.id.sky_action_dialog_text_cancel);
    }

    private void updateViews() {
        if ((mTextTitle.getVisibility() == View.VISIBLE) ||
            (mTextMessage.getVisibility() == View.VISIBLE)) {
            mLayoutHeader.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeader.setVisibility(View.GONE);
        }
    }

    private void setupWindow() {
        final Window window = getWindow();
        final WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = getContext().getResources().getDisplayMetrics().widthPixels;
        window.setAttributes(layoutParams);
        window.setGravity(Gravity.BOTTOM);
    }
}
