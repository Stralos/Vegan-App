package vegan.paki.mapa.mif.veganapp.core.model.dto;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by Mantas on 11/10/2014.
 */
@ParseClassName("Food")
public class FoodDTO extends ParseObject {

    public String getName() {
        return getString("name");
    }
    public ParseFile getImage() {
        return getParseFile("image");
    }

    public String getCategory() {
        return getString("category");
    }

}
