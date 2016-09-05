package com.example.lanouhn.zhibo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lanouhn.zhibo.R;
import com.example.lanouhn.zhibo.widget.InputPanel;


public class PlayBottomFragment extends Fragment {

    private static final String TAG = "PlayBottomFragment";

    private InputPanel inputPanel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_bottom, container);
        inputPanel = (InputPanel) view.findViewById(R.id.input_panel);

        return view;
    }

    public void setInputPanelListener(InputPanel.InputPanelListener l) {
        inputPanel.setPanelListener(l);
    }
}
