# Reference:
#       http://blog.onyxbits.de/how-to-get-the-google-play-user-agent-for-a-given-device-140/
#
# Run script on a jailbroken Android device via terminal to get the User-Agent information. Use the following steps:
#       $ adb push executable /data/local/tmp
#       $ adb shell
#       $ cd /data/local/tmp
#       $ chmod 755 executable
#       $ ./get_user_agent_details.sh


# potential output "versionName=7.0.17.H-all [0] versionName=4.6.17", select versionName=4.6.17
VERSION_NAME=$(dumpsys package com.android.vending | grep versionName)
# potential output "versionCode=80701700 targetSdk=25 versionCode=80260017 targetSdk=14", select versionCode=80260017
VERSION_CODE=$(dumpsys package com.android.vending | grep versionCode)
SDK=$(getprop ro.build.version.sdk)
DEVICE=$(getprop ro.product.device)
HARDWARE=$(getprop ro.hardware)
PRODUCT=$(getprop ro.build.product)
BUILD="$(getprop ro.build.id):$(getprop ro.build.type)"

echo $VERSION_NAME
echo $VERSION_CODE
echo "sdk=$SDK"
echo "device=$DEVICE"
echo "hardware=$HARDWARE"
echo "product=$PRODUCT"
echo "build=$BUILD"

echo "Android-Finsky/(VERSION_NAME) (versionCode=(VERSION_CODE),sdk=$SDK,device=$DEVICE,hardware=$HARDWARE,product=$PRODUCT,build=$BUILD)"
