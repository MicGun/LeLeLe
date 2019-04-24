package com.hugh.lelele.post;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hugh.lelele.R;
import com.hugh.lelele.component.ProfileAvatarOutlineProvider;
import com.hugh.lelele.data.Article;
import com.hugh.lelele.util.Constants;
import com.hugh.lelele.util.ImageManager;
import com.hugh.lelele.util.UserManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.google.common.base.Preconditions.checkNotNull;

public class PostFragment extends Fragment implements PostContract.View {

    private PostContract.Presenter mPresenter;

    private ImageView mAuthorPicture;
    private TextView mAuthorName;
    private EditText mContentEditor;
    private EditText mTitleEditor;
    private String mContent;
    private String mTitle;


    private Article mArticle;
    private FloatingActionButton mFloatingArticleEditDoneButton;

    public PostFragment() {
        mArticle = new Article();
        mContent = "";
        mTitle = "";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_posting, container, false);
        mPresenter.hideBottomNavigation();

        mAuthorPicture = root.findViewById(R.id.image_author_picture_posting);
        mAuthorPicture.setOutlineProvider(new ProfileAvatarOutlineProvider(getResources().
                getDimensionPixelOffset(R.dimen.radius_author_avatar)));

        mAuthorName = root.findViewById(R.id.text_view_author_name_posting);

        mContentEditor = root.findViewById(R.id.text_view_content_posting);
        mContentEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mContent = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTitleEditor = root.findViewById(R.id.text_view_title_posting);
        mTitleEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitle = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mFloatingArticleEditDoneButton = root.findViewById(R.id.button_article_edit_done_posting);
        mFloatingArticleEditDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mContent.equals("") && !mTitle.equals("")) {
                    postingArticle();
                } else {
                    Toast.makeText(getContext(), getString(R.string.content_empty), Toast.LENGTH_SHORT).show();
                }
            }
        });

        setAuthorInfo();

        return root;
    }

    private void postingArticle() {

        mArticle.setTitle(mTitle);
        mArticle.setContent(mContent);
        mArticle.setAuthor(UserManager.getInstance().getUserData().getName());
        mArticle.setAuthorEmail(UserManager.getInstance().getUserData().getEmail());
        mArticle.setAuthorPicture(UserManager.getInstance().getUserData().getPictureUrl());
        mArticle.setType(Constants.GENERAL);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        mArticle.setTime(String.valueOf(formatter.format(Calendar.getInstance().getTime())));

        mPresenter.releaseGroupArticle(mArticle);
    }

    private void setAuthorInfo() {

        ImageManager.getInstance().setImageByUrl(mAuthorPicture,
                UserManager.getInstance().getUserData().getPictureUrl());

        mAuthorName.setText(UserManager.getInstance().getUserData().getName());

    }

    public static PostFragment newInstance() {
        return new PostFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.showBottomNavigation();
        mPresenter.updateToolbar(getString(R.string.home));
    }

    @Override
    public void setPresenter(PostContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void toBackStack() {
        mPresenter.showLastFragment();
    }
}
