/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * {@link InspectionState} contains the inspection state of either a RC or a DS
 */
@SuppressWarnings("WeakerAccess")
public class InspectionState
    {
    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    public static final String robotControllerPackage = "com.qualcomm.ftcrobotcontroller";
    public static final String driverStationPackage = "com.qualcomm.ftcdriverstation";

    public static final String NO_VERSION = "";
    public static final int NO_VERSION_CODE = 0;

    //The serialized names save several hundred bytes in the QR code

    @SerializedName("mfr")
    public String manufacturer;
    @SerializedName("mdl")
    public String model;
    @SerializedName("dev")
    public String deviceCodename; // For scoring system to auto-select
    @SerializedName("os")
    public String osVersion; // Android version (e.g. 7.1.1)
    @SerializedName("chOs")
    public String controlHubOsVersion; // Control Hub OS version (e.g. 1.1.1)
    @SerializedName("dhOs")
    public String driverHubOsVersion;
    @SerializedName("chOsNum")
    public int controlHubOsVersionNum;
    @SerializedName("dhOsNum")
    public int driverHubOsVersionNum;
    @SerializedName("fw")
    public String firmwareVersion; // TODO(Noah): The next time we bump Robocol, send a list of firmware versions instead
    @SerializedName("sdk")
    public int sdkInt;
    @SerializedName("am")
    public boolean airplaneModeOn;
    @SerializedName("bt")
    public boolean bluetoothOn;
    @SerializedName("wfEn")
    public boolean wifiEnabled;
    @SerializedName("wfConn")
    public boolean wifiConnected;
    @SerializedName("wfDirEn")
    public boolean wifiDirectEnabled;
    @SerializedName("wfDirConn")
    public boolean wifiDirectConnected;
    @SerializedName("wfc")
    public int wifiChannel;
    @SerializedName("loc")
    public boolean locationEnabled;
    @SerializedName("name")
    public String deviceName;
    @SerializedName("bat")
    public double batteryFraction;
    @SerializedName("rcIn")
    public boolean robotControllerInstalled;
    @SerializedName("dsIn")
    public boolean driverStationInstalled;
    @SerializedName("aVmj")
    public int majorSdkVersion;
    @SerializedName("aVmn")
    public int minorSdkVersion;
    @SerializedName("aVpt")
    public int pointSdkVersion;
    @SerializedName("bldTs")
    public String appBuildTime;
    @QrExclude
    public long    rxDataCount;
    @QrExclude
    public long    txDataCount;
    @QrExclude
    public long    bytesPerSecond;
    @SerializedName("pw")
    public boolean isDefaultPassword;

    //----------------------------------------------------------------------------------------------
    // Construction and initialization
    //----------------------------------------------------------------------------------------------

    public InspectionState()
        {
        // For deserialization, initialize CH OS version to NO_VERSION value.
        // Otherwise, it will be null when the RC is running 5.x
        this.controlHubOsVersion = NO_VERSION;
        }

    //----------------------------------------------------------------------------------------------
    // Serialization
    //----------------------------------------------------------------------------------------------

    public String serialize()
        {
        return SimpleGson.getInstance().toJson(this);
        }

    public static InspectionState deserialize(String serialized)
        {
        return SimpleGson.getInstance().fromJson(serialized, InspectionState.class);
        }


    }
