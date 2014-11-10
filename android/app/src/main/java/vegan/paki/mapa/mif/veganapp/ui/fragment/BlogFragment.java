package vegan.paki.mapa.mif.veganapp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import vegan.paki.mapa.mif.veganapp.R;
import vegan.paki.mapa.mif.veganapp.core.model.Post;
import vegan.paki.mapa.mif.veganapp.ui.adapter.PostAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlogFragment extends Fragment implements NavigationFragment {

    private List<Post> postList;

    public BlogFragment() {
        // Required empty public constructor
    }

    private void fillArray(){
        postList = new ArrayList<Post>();
        postList.add(new Post("SOMETHING","Potato", R.drawable.ic_launcher ));
        postList.add(new Post("SOMETHING","Potato2", R.drawable.ic_launcher));
        postList.add(new Post("SOMETHING","Potato3", R.drawable.ic_launcher));
        postList.add(new Post("SOMETHING","Potato4", R.drawable.ic_launcher));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fillArray();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blog, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView ls = (ListView) view.findViewById(R.id.post_list_view);
        PostAdapter postAdapter = new PostAdapter(this.getActivity(), postList);
        ls.setAdapter(postAdapter);
    }


    @Override
    public int getTitleResId() {
        return R.string.blog_title;
    }

    @Override
    public int getIconResId() {
        return 0;
    }
}
