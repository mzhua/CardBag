# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/hua/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# 指定不使用大小写区分混淆后的类名
-dontusemixedcaseclassnames
# 指定混淆的时候不忽略jar中非public的类
-dontskipnonpubliclibraryclasses
# 混淆过程中输出更详细的信息
-verbose

# 混淆的时候不执行优化步骤与预验证步骤，Dex自己会做一些相对应的优化
#-dontoptimize
#-dontpreverify

# 确保在jdk5.0以及更高版本能够使用泛型
-keepattributes Signature
# 不混淆异常,让编译器知道方法会抛出哪种异常
-keepattributes Exceptions
#不混淆注解
-keepattributes *Annotation*
#-keepattributes InnerClasses
-keepattributes EnclosingMethod
-keepattributes InnerClasses

# 不混淆本地方法,includedescriptorclasses选项指定不重命名方法返回类型与参数类型
-keepclasseswithmembernames,includedescriptorclasses class * {
    native <methods>;
}

# 确保views的动画能够正常工作
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# 不混淆Activity里面带view参数的方法，确保XML的onclick属性能正常使用
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keep class sun.misc.Unsafe
# 不混淆枚举类型
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


# 对实现了Parcelable接口的所有类的类名不进行混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#不混淆Serializable的子类
# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 不混淆R.java文件
-keepclassmembers class **.R$* {
    public static <fields>;
}

# 忽略support包新版本警告
-keep class android.support.** { *; }
-dontwarn android.support.**

# 不混淆第三方jar包
#-dontshrink
#-dontoptimize

# Keep GSON stuff
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }
# Gson specific classes
-keep class com.google.gson.stream.** { *; }

# 不混淆ILicensingService类，单向的进程间通讯接口，这个接口的许可检查请求来自google play客户端
#-keep public class com.google.vending.licensing.ILicensingService
#-keep public class com.android.vending.licensing.ILicensingService

#-keep class com.wonders.xlab.cardbag.** { *; }
#-dontwarn com.yalantis.ucrop**
#-keep class com.yalantis.ucrop** { *; }
#-keep interface com.yalantis.ucrop** { *; }
#
#-keep class com.android.org.conscrypt.SSLParametersImpl
#-keep  class org.apache.harmony.xnet.provider.jsse.SSLParametersImpl
#-dontwarn okhttp3.**
#-keep class okhttp3.**{*;}
#
-dontwarn okio.**
-keep class okio.**{*;}
#
-keep class com.squareup.** {*;}
-dontwarn com.squareup.**
#
#-keep class cn.bingoogolapple.qrcode.** {*;}