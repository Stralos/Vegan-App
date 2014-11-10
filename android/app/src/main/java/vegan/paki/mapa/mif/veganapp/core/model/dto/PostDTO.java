package vegan.paki.mapa.mif.veganapp.core.model.dto;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by Mantas on 11/10/2014.
 */
@ParseClassName("Post")
public class PostDTO extends ParseObject {

    public String getContent() {
        return getString("content");
    }

    public String getAuthor() {
        return getString("author");
    }

    public String getTitle() {
        return getString("title");
    }

    public ParseFile getImage() {
        return getParseFile("image");
    }

}
