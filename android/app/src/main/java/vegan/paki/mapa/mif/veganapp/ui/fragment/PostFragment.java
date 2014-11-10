package vegan.paki.mapa.mif.veganapp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;
import vegan.paki.mapa.mif.veganapp.R;
import vegan.paki.mapa.mif.veganapp.RxParseManager;
import vegan.paki.mapa.mif.veganapp.core.model.dto.PostDTO;
import vegan.paki.mapa.mif.veganapp.util.RxImageLoader;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class PostFragment extends Fragment {

    private Subscription mPostSubscription = Subscriptions.empty();
    private String mPostId;
    private ImageView mImageView;
    private TextView mContentView;
    private TextView mTitleView;
    private TextView mAuthorView;
    private FloatingActionButton mFavouriteButton;
    private PostDTO mPostDTO;
    private boolean mFromLocal = false;
    private boolean mFavourited;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPostId = savedInstanceState.getString("objectId");
            mFromLocal = savedInstanceState.getBoolean("local");
        } else if (getArguments() != null) {
            mPostId = getArguments().getString("objectId");
            mFromLocal = getArguments().getBoolean("local");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("objectId", mPostId);
        outState.putBoolean("local", mFromLocal);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        mImageView = (ImageView) view.findViewById(R.id.image);
        mTitleView = (TextView) view.findViewById(R.id.title);
        mAuthorView = (TextView) view.findViewById(R.id.author);
        mContentView = (TextView) view.findViewById(R.id.content);
        mFavouriteButton = (FloatingActionButton) view.findViewById(R.id.favourite);
        mPostSubscription = requestPost(mPostId);
        return view;
    }

    @Override
    public void onDestroyView() {
        mPostSubscription.unsubscribe();
        super.onDestroyView();
    }

    private Subscription requestPost(final String postId) {
        ParseQuery<PostDTO> query = ParseQuery.getQuery(PostDTO.class);
        query.fromLocalDatastore();
        query.whereEqualTo("objectId", postId);
        mFromLocal = true;
        return RxParseManager.getInstance().getFirst(query).onErrorResumeNext(new Func1<Throwable, Observable<? extends PostDTO>>() {
            @Override
            public Observable<? extends PostDTO> call(Throwable throwable) {
                mFromLocal = false;
                ParseQuery<PostDTO> query = ParseQuery.getQuery(PostDTO.class);
                query.whereEqualTo("objectId", postId);
                return RxParseManager.getInstance().getFirst(query);
            }
        }).subscribe(new Action1<PostDTO>() {
            @Override
            public void call(PostDTO postDTO) {
                loadView(postDTO);
            }
        });
    }

    private void loadView(PostDTO postDTO) {
        mPostDTO = postDTO;
        ParseFile image = postDTO.getImage();
        if (image != null) {
            RxImageLoader.displayImage(image.getUrl(), mImageView).cache().subscribe();
        } else {
            RxImageLoader.displayImage("drawable://" + R.drawable.vegan_placeholder, mImageView).cache().subscribe();
        }

        mTitleView.setText(postDTO.getTitle());
        mAuthorView.setText(postDTO.getAuthor());
        mContentView.setText(postDTO.getContent());
        if (mFromLocal) {
            mFavouriteButton.setImageResource(R.drawable.ic_favorite);
        } else {
            mFavouriteButton.setImageResource(R.drawable.ic_action_content_new);
        }
        mFavourited = mFromLocal;
        mFavouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFavourited) {
                    mFavouriteButton.setImageResource(R.drawable.ic_action_content_new);
                    mPostDTO.unpinInBackground(mPostId);
                } else {
                    mFavouriteButton.setImageResource(R.drawable.ic_favorite);
                    mPostDTO.pinInBackground(mPostId);
                }
                mFavourited = !mFavourited;
            }
        });
    }

}
