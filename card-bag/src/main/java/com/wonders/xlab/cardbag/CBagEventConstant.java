package com.wonders.xlab.cardbag;

/**
 * Created by hua on 16/9/21.
 */

public class CBagEventConstant {
    public static final String EVENT_BROADCAST_SUFFIX = ".cb.event";
    /**
     * 事件key
     */
    public static final String EXTRA_KEY_EVENT = "event";
    public static final String EXTRA_KEY_NAME = "name";
    public static final String EXTRA_KEY_TIME_IN_MILL = "timeInMill";

    /**
     * 所有广播事件的类型
     */
    public static final String EVENT_PAGE_CREATE_HOME = "home_create";
    public static final String EVENT_PAGE_DESTROY_HOME = "home_destroy";
    public static final String EVENT_PAGE_CREATE_CARD_EDIT = "card_edit_create";
    public static final String EVENT_PAGE_DESTROY_CARD_EDIT = "card_edit_destroy";
    public static final String EVENT_PAGE_CREATE_CARD_MY = "card_my_create";
    public static final String EVENT_PAGE_DESTROY_CARD_MY = "card_my_destroy";
    public static final String EVENT_PAGE_CREATE_CARD_SEARCH = "card_search_create";
    public static final String EVENT_PAGE_DESTROY_CARD_SEARCH = "card_search_destroy";
    public static final String EVENT_PAGE_CREATE_CARD_SHOW = "card_show_create";
    public static final String EVENT_PAGE_DESTROY_CARD_SHOW = "card_show_destroy";
    public static final String EVENT_CLICK_SCAN_BAR_CODE = "scan_bar_code";
    public static final String EVENT_CLICK_TAKE_FRONT_PICTURE = "take_front_picture";
    public static final String EVENT_CLICK_TAKE_BACK_PICTURE = "take_back_picture";
    public static final String EVENT_CLICK_SAVE_CARD = "save_card_success";
}
