package com.kanishk.code.shutterfly.model;

/**
 * Created by kanishk on 7/5/17.
 */

public class UnsplashCollections
{
    private String id;

    private String title;

    private String updated_at;

    private String description;

    private String curated;

    private String share_key;

    private UnsplashImage.ImageUrl imageUrl;

    private UnsplashImage.User user;

    private String total_photos;

    private String published_at;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getCurated ()
    {
        return curated;
    }

    public void setCurated (String curated)
    {
        this.curated = curated;
    }

    public String getShare_key ()
    {
        return share_key;
    }

    public void setShare_key (String share_key)
    {
        this.share_key = share_key;
    }

    public UnsplashImage.User getUser ()
    {
        return user;
    }

    public void setUser (UnsplashImage.User user)
    {
        this.user = user;
    }

    public String getTotal_photos ()
    {
        return total_photos;
    }

    public void setTotal_photos (String total_photos)
    {
        this.total_photos = total_photos;
    }

    public String getPublished_at ()
    {
        return published_at;
    }

    public void setPublished_at (String published_at)
    {
        this.published_at = published_at;
    }

    public UnsplashImage.ImageUrl getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(UnsplashImage.ImageUrl imageUrl) {
        this.imageUrl = imageUrl;
    }
}
