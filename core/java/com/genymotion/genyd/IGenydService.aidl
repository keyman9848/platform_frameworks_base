package com.genymotion.genyd;

interface IGenydService {
    
    /** Error */
    
    int getError();

    /** Battery */
    
    int getBatteryLevel();
    void setBatteryLevel(int value);

    int getBatteryMode();
    void setBatteryMode();
    
    int getBatteryStatus();
    void setBatteryStatus();
    
    /** Gps */

    double getGpsAccuracy();
    void setGpsAccuracy();
    
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
    void setAndroidId(String id);
    void setRandomAndroidId();
    
    /** Orientation */
    
    int getOrientationAngle();
    void setOrientationAngle(int angle);
    
    /** Phone */
    
    void sendSms(String source, String msg);
    void PhoneCall(String source);
    
    /** Radio **/
    
    int getPhoneType();
    void setPhoneType(int type);
    
    String getDeviceId();
    void setDeviceId(String id);
    void setRandomDeviceId();
}
