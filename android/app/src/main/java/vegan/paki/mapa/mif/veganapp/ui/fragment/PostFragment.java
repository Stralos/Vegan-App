package vegan.paki.mapa.mif.veganapp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseQuery;

import rx.Subscription;
import rx.functions.Action1;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPostId = savedInstanceState.getString("objectId");
        } else if (getArguments() != null) {
            mPostId = getArguments().getString("objectId");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("objectId", mPostId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        mImageView = (ImageView) view.findViewById(R.id.image);
        mTitleView = (TextView) view.findViewById(R.id.title);
        mAuthorView = (TextView) view.findViewById(R.id.author);
        mContentView = (TextView) view.findViewById(R.id.content);

        mPostSubscription = requestPost(mPostId);

        return view;
    }

    @Override
    public void onDestroyView() {
        mPostSubscription.unsubscribe();
        super.onDestroyView();
    }

    private Subscription requestPost(String postId) {
        ParseQuery<PostDTO> query = ParseQuery.getQuery(PostDTO.class);
        query.whereEqualTo("objectId", postId);
        return RxParseManager.getInstance().getFirst(query).subscribe(new Action1<PostDTO>() {
            @Override
            public void call(PostDTO postDTO) {
                loadView(postDTO);
            }
        });
    }

    private void loadView(PostDTO postDTO) {
        ParseFile image = postDTO.getImage();
        if (image != null) {
            RxImageLoader.displayImage(image.getUrl(), mImageView).cache().subscribe();
        } else {
            RxImageLoader.displayImage("drawable://" + R.drawable.vegan_placeholder, mImageView).cache().subscribe();
        }

        mTitleView.setText(postDTO.getTitle());
        mAuthorView.setText(postDTO.getAuthor());
        mContentView.setText(postDTO.getContent());
    }

}
