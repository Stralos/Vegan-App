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
import vegan.paki.mapa.mif.veganapp.R;
import vegan.paki.mapa.mif.veganapp.RxParseManager;
import vegan.paki.mapa.mif.veganapp.core.model.dto.FoodDTO;
import vegan.paki.mapa.mif.veganapp.util.RxImageLoader;


public class DishFragment extends Fragment {

    private String objectId;
    private TextView mDishName;
    private TextView mDishDescription;
    private ImageView mImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            objectId = getArguments().getString("objectId");
        }else{
            objectId = savedInstanceState.getString("objectId");
        }
    }

    private View initialiseView(View view){
        requestFood();

        mDishName = (TextView) view.findViewById(R.id.dish_name);
        mDishDescription = (TextView) view.findViewById(R.id.how_to_dish);
        mImageView = (ImageView) view.findViewById(R.id.dish_image_view);

        return view;
    }

    private void loadView(FoodDTO foodDTO) {
        mDishName.setText(foodDTO.getName());
        mDishDescription.setText(foodDTO.getRecipe());
        ParseFile image = foodDTO.getImage();
        if (image != null) {
            RxImageLoader.displayImage(image.getUrl(), mImageView).cache().subscribe();
        } else {
            RxImageLoader.displayImage("drawable://" + R.drawable.vegan_placeholder, mImageView).cache().subscribe();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dish_view, container, false);

        return initialiseView(view);
    }

    private Subscription requestFood() {
        ParseQuery<FoodDTO> query = ParseQuery.getQuery(FoodDTO.class);
        query.whereEqualTo("objectId", objectId);
        return RxParseManager.getInstance().getFirst(query).subscribe(new Action1<FoodDTO>() {
            @Override
            public void call(FoodDTO foodDTO) {
                loadView(foodDTO);
            }
        });
    }


}
