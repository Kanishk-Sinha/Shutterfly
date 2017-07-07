package com.kanishk.code.shutterfly.model;

/**
 * Created by kanishk on 7/5/17.
 */

public class UnsplashImage {

    private ImageUrl urls;
    private User user;

    public ImageUrl getUrls() {
        return urls;
    }

    public void setUrls(ImageUrl urls) {
        this.urls = urls;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public class ImageUrl {

        private String raw;

        private String regular;

        private String full;

        private String thumb;

        private String small;

        public String getRaw ()
        {
            return raw;
        }

        public void setRaw (String raw)
        {
            this.raw = raw;
        }

        public String getRegular ()
        {
            return regular;
        }

        public void setRegular (String regular)
        {
            this.regular = regular;
        }

        public String getFull ()
        {
            return full;
        }

        public void setFull (String full)
        {
            this.full = full;
        }

        public String getThumb ()
        {
            return thumb;
        }

        public void setThumb (String thumb)
        {
            this.thumb = thumb;
        }

        public String getSmall ()
        {
            return small;
        }

        public void setSmall (String small)
        {
            this.small = small;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [raw = "+raw+", regular = "+regular+", full = "+full+", thumb = "+thumb+", small = "+small+"]";
        }

    }

    public class User
    {
        private String id;

        private String username;

        private String updated_at;

        private String bio;

        private String location;

        private String portfolio_url;

        private String name;

        private String total_collections;

        private String total_photos;

        private String total_likes;

        private String prof_image;

        private String prof_link;

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getUsername ()
        {
            return username;
        }

        public void setUsername (String username)
        {
            this.username = username;
        }

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public String getBio ()
        {
            return bio;
        }

        public void setBio (String bio)
        {
            this.bio = bio;
        }

        public String getLocation ()
        {
            return location;
        }

        public void setLocation (String location)
        {
            this.location = location;
        }

        public String getPortfolio_url ()
        {
            return portfolio_url;
        }

        public void setPortfolio_url (String portfolio_url)
        {
            this.portfolio_url = portfolio_url;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public String getTotal_collections ()
        {
            return total_collections;
        }

        public void setTotal_collections (String total_collections)
        {
            this.total_collections = total_collections;
        }

        public String getTotal_photos ()
        {
            return total_photos;
        }

        public void setTotal_photos (String total_photos)
        {
            this.total_photos = total_photos;
        }

        public String getTotal_likes ()
        {
            return total_likes;
        }

        public void setTotal_likes (String total_likes)
        {
            this.total_likes = total_likes;
        }

        public String getProf_image() {
            return prof_image;
        }

        public void setProf_image(String prof_image) {
            this.prof_image = prof_image;
        }

        public String getProf_link() {
            return prof_link;
        }

        public void setProf_link(String prof_link) {
            this.prof_link = prof_link;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [id = "+id+", username = "+username+", updated_at = "+updated_at+", bio = "+bio+", location = "+location+", portfolio_url = "+portfolio_url+", name = "+name+", total_collections = "+total_collections+", total_photos = "+total_photos+", total_likes = "+total_likes+"]";
        }
    }

}
