package com.hugh.lelele.message;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.hugh.lelele.R;
import com.hugh.lelele.data.Message;
import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class MessageFragment extends Fragment implements MessageContract.View {

    private MessageContract.Presenter mPresenter;
    private MessageAdapter mMessageAdapter;

    private Tenant mTenant;

    private EditText mContentEditText;
    private ImageView mSendButton;

    private String mContent;

    public MessageFragment() {
        mContent = "";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMessageAdapter = new MessageAdapter(mPresenter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.setupMessageListener(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_message, container, false);

        mPresenter.hideBottomNavigation();

        RecyclerView recyclerView = root.findViewById(R.id.recycler_message);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mMessageAdapter);

        mContentEditText = root.findViewById(R.id.edit_text_content_message);
        mContentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mContent = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSendButton = root.findViewById(R.id.image_view_send_message_button_message);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mContent.equals("")) {
                    mPresenter.sendMessage(mContent);
                    mContentEditText.setText("");
                }
            }
        });

        return root;
    }

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (UserManager.getInstance().getUserType() == R.string.tenant) {
            mPresenter.updateToolbar(getString(R.string.application));
            mPresenter.showBottomNavigation();
        } else if (UserManager.getInstance().getUserType() == R.string.landlord) {
            mPresenter.updateToolbar(getString(R.string.messaging_list));
        }

        mPresenter.setupMessageListener(false);
    }

    @Override
    public void setPresenter(MessageContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setTenantView(Tenant tenant) {
        mTenant = checkNotNull(tenant);
    }

    @Override
    public void setMessagesView(ArrayList<Message> messages) {
        if (mMessageAdapter == null) {
            mMessageAdapter = new MessageAdapter(mPresenter);
            mMessageAdapter.updateData(messages);
        } else {
            mMessageAdapter.updateData(messages);
        }
    }
}
