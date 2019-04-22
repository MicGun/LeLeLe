package com.hugh.lelele.post;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hugh.lelele.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class PostFragment extends Fragment implements PostContract.View {

    private PostContract.Presenter mPresenter;

    private ImageView mAuthorPicture;
    private TextView mAuthorName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_posting, container, false);
        mPresenter.hideBottomNavigation();

        mAuthorPicture = root.findViewById(R.id.image_author_picture_posting);
        mAuthorName = root.findViewById(R.id.text_view_author_name_posting);

        return root;
    }

    public static PostFragment newInstance() {
        return new PostFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.showBottomNavigation();
    }

    @Override
    public void setPresenter(PostContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
