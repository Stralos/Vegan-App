package vegan.paki.mapa.mif.veganapp.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;
import timber.log.Timber;
import vegan.paki.mapa.mif.veganapp.R;
import vegan.paki.mapa.mif.veganapp.RxParseManager;
import vegan.paki.mapa.mif.veganapp.core.model.dto.CategoryDTO;
import vegan.paki.mapa.mif.veganapp.ui.adapter.MenuAdapter;

/**
 * Created by Panda on 11/24/2014.
 */
public class MenuPagerFragment extends Fragment implements NavigationItem {

    private List<CategoryDTO> mCategories = new ArrayList<CategoryDTO>();
    MenuAdapter menuAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_layout, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.menu_view);

        requestPosts();
        menuAdapter = new MenuAdapter(getActivity(), mCategories);
        gridView.setAdapter(menuAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return view;
    }


    private Subscription requestPosts() {
        ParseQuery<CategoryDTO> query = ParseQuery.getQuery(CategoryDTO.class);
        return RxParseManager.getInstance().find(query).subscribe(new Action1<List<CategoryDTO>>() {
            @Override
            public void call(List<CategoryDTO> categoryDTO) {
                mCategories.clear();
                mCategories.addAll(categoryDTO);
                Timber.d(mCategories.size() + "");
                menuAdapter.notifyDataSetChanged();
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    public int getTitleResId(){ return R.string.menu_navigation_drawer;}
    public int getIconResId(){ return 0;}
}
