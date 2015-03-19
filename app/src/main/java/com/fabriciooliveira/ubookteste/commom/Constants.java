package com.fabriciooliveira.ubookteste.commom;

/**
 * Created by fabriciooliveira on 3/18/15.
 */
public class Constants {

    public static final String URL_FOR_LIST = "https://api.dribbble.com/v1/shots?";

    public static final String URL_FOR_SHOT = "https://api.dribbble.com/shots/";

    public static final String ACCESS_TOKEN = "04b3ee0419f6e14802cd2610ce71b7c16e5b4bc8883432cb2e01821c2dd0d7f7";

    public static final String MAXIMUM_SHOT_LIST_RESULTS = "&page=1&per_page=30";

    public static final String URL_SHOTS = URL_FOR_LIST + "access_token=" + ACCESS_TOKEN + MAXIMUM_SHOT_LIST_RESULTS;

}
