package com.example.android.homechat;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android.homechat.ServerCommunication.Database;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Post messages in the group
 */
public class PostMessageFragment extends Fragment {

    private static final String TAG = "PostMessageFragment";

    public static final int DEFAULT_MSG_LENGTH_LIMIT = 100;

    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;
    private Button mSendButton;

    /**
     * public constructor is always needed...
     */
    public PostMessageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the fragment layout
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);

        // get all the UI elements
        mPhotoPickerButton = (ImageButton) rootView.findViewById(R.id.photoPickerButton);
        mMessageEditText = (EditText) rootView.findViewById(R.id.messageEditText);
        mSendButton = (Button) rootView.findViewById(R.id.sendButton);

        // Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // Add a Filter which makes sure that the message doesn't exceed the length limit
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String writtenMsg = mMessageEditText.getText().toString();

                // Clear input box
                mMessageEditText.setText("");

                // TODO: Send messages on click
                Database.saveMsgToDatabase(new Message(writtenMsg));
            }
        });

        // Return the rootView
        return rootView;
    }
}
