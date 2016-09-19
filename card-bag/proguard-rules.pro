# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/hua/android-sdk-macosx/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView get JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontshrink
-keep class com.wonders.xlab.cardbag.CBag { *; }
-keep class com.wonders.xlab.cardbag.db.CBDataSyncHelper { *; }
-keep class com.wonders.xlab.cardbag.data.entity.** { *; }
-keep interface com.wonders.xlab.cardbag.base.BaseContract$Model { *; }
-keep interface com.wonders.xlab.cardbag.base.BaseContract$Model$Callback { *; }
-keep interface com.wonders.xlab.cardbag.ui.cardsearch.CardSearchContract$Model {*;}

-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

-keep class com.squareup.** {*;}