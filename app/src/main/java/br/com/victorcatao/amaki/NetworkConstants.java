package br.com.victorcatao.amaki;

public class NetworkConstants {
    //1xx Informational
    public static final int CODE_WITHOUT_NETWORK = 0;

    //2xx Success
    public static final int CODE_RESPONSE_SUCCESS = 200;

    //3xx Redirection
    public static final int CODE_NOT_FOUND = 340;

    //4xx Client Error
    public static final int CODE_TIMEOUT = 408;
    public static final int CODE_RESPONSE_UNAUTHORIZED = 401;
    public static final int CODE_BAD_REQUEST = 400;
    public static final int CODE_FORBIDDEN = 403;

    //5xx Server Error
    public static final int CODE_UNKNOWN = 500;

    //API URLs
//    public static final String BASE_URL = "http://10.0.2.2:5000/";
    public static final String BASE_URL = "https://amaki.herokuapp.com/";
    public static final String ESTABLISHMENT_URL =  BASE_URL + "establishment/";
    public static final String CATEGORY_URL =  BASE_URL + "category/";
    public static final String CONTACT_URL =  BASE_URL + "contact/";
}
