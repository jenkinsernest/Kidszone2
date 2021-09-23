# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#noinspection ShrinkerUnresolvedReference
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
  public *;
}


-keep class com.folioreader.ui.view.FolioSearchView { *; }
-keep public class * extends androidx.appcompat.widget.SearchView { *; }

-keep public class com.playzone.kidszone.MainActivity
-keep public class com.playzone.kidszone.Parent
-keep public class com.playzone.kidszone.service.BackgroundService
-keep public class cn.pedant.SweetAlert.Rotate3dAnimation { *; }

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

#noinspection ShrinkerUnresolvedReference
-keepclasseswithmembers public class com.flutterwave.raveandroid.** { *; }
-dontwarn com.flutterwave.raveandroid.card.CardFragment

-dontwarn android.support.**
