package vegan.paki.mapa.mif.veganapp.core.model;

/**
 * Created by Panda on 11/6/2014.
 */
public class Post {
    private String _content;
    private int _pictureId;
    private String _name;

    public Post(String content, String name, int pictureId){
        this._content = content;
        this._name = name;
        this._pictureId = pictureId;
    }
    public String getName(){
        return this._name;
    }
    public int getPictureId(){
        return this._pictureId;
    }
    public String getContent(){
        return this._content;
    }
}
