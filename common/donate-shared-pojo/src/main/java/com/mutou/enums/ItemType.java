package com.mutou.enums;

/**
 * @Description: 物品类型
 */
public enum ItemType {

    TYPE_BOOK(1, "书籍"),
    TYPE_OTHER(10, "其他");

    public final Integer type;
    public final String value;

    ItemType(Integer type, String value){
        this.type = type;
        this.value = value;
    }
}
