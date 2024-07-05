package com.poojithairosha.vristopos.util;

public class UriProperties {

    public static final String URI_BASE = "api";

    // Common
    public static final String URI_SEARCH = "/search";
    public static final String URI_FIND_BY_ID = "/{id}";
    public static final String URI_FIND_ALL = "/all";

    // Auth
    public static final String URI_AUTH = URI_BASE + "/auth";
    public static final String URI_LOGIN = "/login";
    public static final String URI_FP = "/forgot-password";
    public static final String URI_RP = "/reset-password";

    // Product
    public static final String URI_PRODUCT = URI_BASE + "/products";

    // Stock
    public static final String URI_STOCK = URI_BASE + "/stock";
    public static final String URI_STOCK_REPORT = "/report";
    public static final String URI_STOCK_UPDATE_PRICE = "/update-price";

    // Brand
    public static final String URI_BRANDS = URI_BASE + "/brands";

    // Category
    public static final String URI_CATEGORIES = URI_BASE + "/categories";

    // Company
    public static final String URI_COMPANIES = URI_BASE + "/companies";

    // Suppliers
    public static final String URI_SUPPLIERS = URI_BASE + "/suppliers";

    // Units
    public static final String URI_UNITS = URI_BASE + "/units";

    // Users
    public static final String URI_USERS = URI_BASE + "/users";
    public static final String URI_USERS_UPDATE_STATUS = "update-status" + URI_FIND_BY_ID;

}
