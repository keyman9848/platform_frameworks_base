package com.genymotion.genyd;

interface IGenydService {

    double getGpsAltitude();
    void setGpsAltitude(double value);
    
    boolean getGpsStatus();
    void setGpsStatus(boolean value);
}
