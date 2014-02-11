package com.genymotion.genyd;

interface IGenydService {

    /** Error */

    int getError();
    int getTokenValidity();

    /** Battery */

    int getBatteryLevel();
    void setBatteryLevel(int level);

    int getBatteryMode();
    void setBatteryMode(int mode);

    int getBatteryStatus();
    void setBatteryStatus(int status);

    /** Gps */

    double getGpsAccuracy();
    void setGpsAccuracy(double value);

    double getGpsAltitude();
    void setGpsAltitude(double value);

    double getGpsBearing();
    void setGpsBearing(double value);

    double getGpsLatitude();
    void setGpsLatitude(double value);

    double getGpsLongitude();
    void setGpsLongitude(double value);

    boolean getGpsStatus();
    void setGpsStatus(boolean value);

    /** Android Id */

    String getAndroidId();
    void setAndroidId(String value);
    void setRandomAndroidId();

    String getDeviceId();
    void setDeviceId(String value);
    void setRandomDeviceId();

    /** Orientation */

    double getOrientationAngle();
    void setOrientationAngle(double angle);

}
