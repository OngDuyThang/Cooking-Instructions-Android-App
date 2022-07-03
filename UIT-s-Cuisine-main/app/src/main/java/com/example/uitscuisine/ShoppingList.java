package com.example.uitscuisine;

public class ShoppingList {
    private String username;
    private String idRecipe;
    private String nameRecipe;
    private String ingredients;
    private boolean status;

    public ShoppingList(String username, String idRecipe,String nameRecipe, String ingredients, boolean status) {
        this.username = username;
        this.idRecipe = idRecipe;
        this.nameRecipe = nameRecipe;
        this.ingredients = ingredients;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(String idRecipe) {
        this.idRecipe = idRecipe;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNameRecipe() {
        return nameRecipe;
    }

    public void setNameRecipe(String nameRecipe) {
        this.nameRecipe = nameRecipe;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "username='" + username + '\'' +
                ", idRecipe='" + idRecipe + '\'' +
                ", nameRecipe='" + nameRecipe + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
