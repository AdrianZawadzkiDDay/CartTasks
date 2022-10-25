package com.nphase.common;

public class ProductsCategoryInfo {
    private Category category;
    private boolean isCategoryDiscount;

    public ProductsCategoryInfo(Category category, boolean isCategoryDiscount) {
        this.category = category;
        this.isCategoryDiscount = isCategoryDiscount;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isCategoryDiscount() {
        return isCategoryDiscount;
    }
}
