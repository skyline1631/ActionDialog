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


package com.skyline.demo.action;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;

import com.skyline.widget.dialog.ActionDialog;

import butterknife.OnClick;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
                          implements View.OnClickListener,
                                     ActionDialog.OnEventListener {
    @BindView(R.id.main_text_action)
    TextView mTextAction = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    @OnClick({
        R.id.main_button_style_i,
        R.id.main_button_style_ii,
        R.id.main_button_style_iii,
        R.id.main_button_style_iv,
    })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_button_style_i: {
                ActionDialog dialog = new ActionDialog(this);
                dialog.setTitle("Title");
                dialog.setMessage("Message");
                dialog.addAction("Default");
                dialog.addAction("Destructive", true);
                dialog.setEventListener(this);
                dialog.show();
                break;
            }

            case R.id.main_button_style_ii: {
                ActionDialog dialog = new ActionDialog(this);
                dialog.addAction("Copy");
                dialog.addAction("Share");
                dialog.addAction("Favorite");
                dialog.addAction("Delete", true);
                dialog.setCancelVisible(false);
                dialog.setEventListener(this);
                dialog.show();
                break;
            }

            case R.id.main_button_style_iii: {
                ActionDialog dialog = new ActionDialog(this);
                dialog.setActions(R.array.mobile_os_entries);
                dialog.setEventListener(this);
                dialog.show();
                break;
            }

            case R.id.main_button_style_iv: {
                ActionDialog dialog = new ActionDialog(this);
                dialog.setTitle(R.string.dialog_title_android);
                dialog.setMessage(R.string.dialog_message_android);
                dialog.setActions(R.array.android_version_entries);
                dialog.setEventListener(this);
                dialog.show();
                break;
            }

            default:
                break;
        }
    }

    @Override
    public void onActionItemClick(ActionDialog dialog, ActionDialog.ActionItem item, int position) {
        mTextAction.setText(item.title);
    }

    @Override
    public void onCancelItemClick(ActionDialog dialog) {
        // Nothing to do
    }
}
