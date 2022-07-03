package com.example.uitscuisine;

import java.util.List;

public class ShoppingList_ParentItem {

    // Declaration of the variables
    private String ParentItemTitle;
    private List<ShoppingList_ChildItem> ChildItemList;

    // Constructor of the class
    // to initialize the variables
    public ShoppingList_ParentItem(
            String ParentItemTitle,
            List<ShoppingList_ChildItem> ChildItemList)
    {

        this.ParentItemTitle = ParentItemTitle;
        this.ChildItemList = ChildItemList;
    }

    // Getter and Setter methods
    // for each parameter
    public String getParentItemTitle()
    {
        return ParentItemTitle;
    }

    public void setParentItemTitle(
            String parentItemTitle)
    {
        ParentItemTitle = parentItemTitle;
    }

    public List<ShoppingList_ChildItem> getChildItemList()
    {
        return ChildItemList;
    }

    public void setChildItemList(
            List<ShoppingList_ChildItem> childItemList)
    {
        ChildItemList = childItemList;
    }
}
