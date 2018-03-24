package com.example.devsmar.dailynews.Model;

/**
 * Created by apple on 01/04/16.
 */
public class NewsFeed {

    private String title;
    private String sectioName;
    private String webUrl;
    private String publicationDate;
    private String imgUrl;

    public NewsFeed(String title, String sectionName, String webUrl, String publicationDate, String imgUrl) {

        this.setTitle(title);
        this.setSectioName(sectionName);
        this.setWebUrl(webUrl);
        this.setPublicationDate(publicationDate);
        this.setImgUrl(imgUrl);
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getSectioName() {
        return sectioName;
    }

    private void setSectioName(String sectioName) {
        this.sectioName = sectioName;
    }

    public String getWebUrl() {
        return webUrl;
    }

    private void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    private void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }
}