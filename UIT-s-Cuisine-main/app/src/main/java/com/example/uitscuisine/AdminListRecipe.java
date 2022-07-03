package com.example.uitscuisine;

public class AdminListRecipe {
    private String neededTime, level, titleText, posterName;
    private String titleImage, posterAvatar, recipeId;

    public AdminListRecipe(String neededTime, String level, String titleText, String posterName, String titleImage, String posterAvatar, String recipeId) {
        this.neededTime = neededTime;
        this.level = level;
        this.titleText = titleText;
        this.posterName = posterName;
        this.titleImage = titleImage;
        this.posterAvatar = posterAvatar;
        this.recipeId = recipeId;
    }

    @Override
    public String toString() {
        return "AdminListRecipe{" +
                "neededTime='" + neededTime + '\'' +
                ", level='" + level + '\'' +
                ", titleText='" + titleText + '\'' +
                ", posterName='" + posterName + '\'' +
                ", titleImage='" + titleImage + '\'' +
                ", posterAvatar='" + posterAvatar + '\'' +
                ", recipeId='" + recipeId + '\'' +
                '}';
    }

    public String getNeededTime() {
        return neededTime;
    }

    public void setNeededTime(String neededTime) {
        this.neededTime = neededTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public String getPosterName() {
        return posterName;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getPosterAvatar() {
        return posterAvatar;
    }

    public void setPosterAvatar(String posterAvatar) {
        this.posterAvatar = posterAvatar;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }
}
