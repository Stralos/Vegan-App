package vegan.paki.mapa.mif.veganapp.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;
import timber.log.Timber;
import vegan.paki.mapa.mif.veganapp.R;
import vegan.paki.mapa.mif.veganapp.RxParseManager;
import vegan.paki.mapa.mif.veganapp.core.model.dto.CategoryDTO;
import vegan.paki.mapa.mif.veganapp.core.model.dto.FoodDTO;
import vegan.paki.mapa.mif.veganapp.ui.activity.MainActivity;
import vegan.paki.mapa.mif.veganapp.ui.adapter.FoodAdapter;

/**
 * Created by Panda on 11/26/2014.
 */
public class FoodFragment extends android.support.v4.app.Fragment {
    private String objectId;
    private FoodAdapter foodAdapter;
    private List<FoodDTO> mFoodList = new ArrayList<FoodDTO>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            objectId = getArguments().getString("objectId");
        }else{
            objectId = savedInstanceState.getString("objectId");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("objectId", objectId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_list, container, false);
        ListView ls = (ListView) view.findViewById(R.id.food_list_view);

        foodAdapter = new FoodAdapter(getActivity(), mFoodList);
        requestFoods();
        ls.setAdapter(foodAdapter);

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DishFragment dishFragment = new DishFragment();
                Bundle bundle = new Bundle();

                bundle.putString("objectId", mFoodList.get(position).getObjectId());
                dishFragment.setArguments(bundle);
                ((MainActivity) getActivity()).switchFragment(dishFragment, false);
            }
        });

        return view;
    }

    private Subscription requestFoods() {
        ParseQuery<FoodDTO> query = ParseQuery.getQuery(FoodDTO.class);
        query.whereEqualTo("category", objectId);
        return RxParseManager.getInstance().find(query).subscribe(new Action1<List<FoodDTO>>() {
            @Override
            public void call(List<FoodDTO> foodDTOs) {
                mFoodList.clear();
                mFoodList.addAll(foodDTOs);
                foodAdapter.notifyDataSetChanged();
            }
        });
    }

}
