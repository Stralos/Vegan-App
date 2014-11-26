package vegan.paki.mapa.mif.veganapp.core.model.dto;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Mantas on 11/10/2014.
 */
@ParseClassName("Category")
public class CategoryDTO extends ParseObject {

    public String getName() {
        return getString("name");
    }

}
