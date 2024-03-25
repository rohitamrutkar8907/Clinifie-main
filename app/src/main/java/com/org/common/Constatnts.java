package com.org.common;

import java.util.regex.Pattern;

public class  Constatnts {

  public static final int ERROR_DIALOG_REQUEST = 9001;
  public static final int PERMISSIONS_REQUEST_ENABLE_GPS = 9002;
  public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9003;
  public static final int TIMESTAMP_DELAY = 2000;
  public static int SPALSH_SCREEN = 1500;
  public final static int RC_SIGN_IN = 123;
  public static final int PERMISSION_REQUEST_CODE = 9001;
  public final static int REQUEST_LOCATION = 199;
  public static final Pattern CAR_NUMBER_PATTERN = Pattern.compile("^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$");
  public static final int REQUEST_CHECK_SETTINGS = 2200;
  public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
  public static final int PLAY_SERVICES_ERROR_CODE = 708;
  public  static  final  String BASE_URL = "https://fcm.googleapis.com/fcm/";
  public static final int MY_CAMERA_REQUEST_CODE = 100;

}