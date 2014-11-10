package vegan.paki.mapa.mif.veganapp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.Subscriptions;
import vegan.paki.mapa.mif.veganapp.R;
import vegan.paki.mapa.mif.veganapp.RxParseManager;
import vegan.paki.mapa.mif.veganapp.core.model.dto.PostDTO;
import vegan.paki.mapa.mif.veganapp.ui.activity.MainActivity;
import vegan.paki.mapa.mif.veganapp.ui.adapter.PostAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlogFragment extends Fragment implements NavigationFragment {

    private RecyclerView mRecyclerView;
    private PostAdapter mPostAdapter;
    private List<PostDTO> mPosts = new ArrayList<PostDTO>();
    private Subscription mPostSubscription = Subscriptions.empty();
    private boolean mFromLocal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mFromLocal = savedInstanceState.getBoolean("local");
        } else if (getArguments() != null) {
            mFromLocal = getArguments().getBoolean("local");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("local", mFromLocal);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blog, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.post_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPostAdapter = new PostAdapter(mPosts, new PostAdapter.ClickListener() {
            @Override
            public void onClick(int index) {
                if (getActivity() instanceof MainActivity) {
                    Bundle bundle = new Bundle();
                    bundle.putString("objectId", mPosts.get(index).getObjectId());
                    if (mFromLocal) {
                        bundle.putBoolean("local", true);
                    }
                    PostFragment postFragment = new PostFragment();
                    postFragment.setArguments(bundle);
                    ((MainActivity) getActivity()).switchFragment(postFragment, false);
                }
            }

            @Override
            public boolean onLongClick(int index) {
                return false;
            }
        });
        mRecyclerView.setAdapter(mPostAdapter);

        mPostSubscription = requestPosts();

        return view;
    }

    @Override
    public void onDestroyView() {
        mPostSubscription.unsubscribe();
        super.onDestroyView();
    }

    private Subscription requestPosts() {
        ParseQuery<PostDTO> query = ParseQuery.getQuery(PostDTO.class);
        if (mFromLocal) {
            query.fromLocalDatastore();
        }
        return RxParseManager.getInstance().find(query).subscribe(new Action1<List<PostDTO>>() {
            @Override
            public void call(List<PostDTO> postDTOs) {
                mPosts = postDTOs;
                if (mPostAdapter != null) {
                    mPostAdapter.set(mPosts);
                }
            }
        });
    }

    @Override
    public int getTitleResId() {
        if (mFromLocal) {
            return R.string.blog_local_title;
        }
        return R.string.blog_title;
    }

    @Override
    public int getIconResId() {
        return 0;
    }
}
