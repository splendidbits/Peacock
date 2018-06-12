# Annotations
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

# OkHttp and Okio
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**
-keep interface com.squareup.okhttp3.** { *; }
-dontwarn org.conscrypt.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Picasso
-dontnote okhttp3.internal.Platform
-keep class com.squareup.picasso.**
-dontwarn com.squareup.picasso.**

# Exoplayer
-dontwarn com.google.android.exoplayer2.**

## Google Play Services (GMS)
## https://developer.android.com/google/play-services/setup.html#Proguard ##
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}
-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

# Gson
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keeppackagenames org.jsoup.nodes

# RxJava
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}

# Dagger
# https://github.com/square/dagger
-dontwarn dagger.internal.codegen.**
-keepclassmembers,allowobfuscation class * {
    @javax.inject.* *;
    @dagger.* *;
    <init>();
}
-keep class dagger.* { *; }
-keep class javax.inject.* { *; }
-keep class * extends dagger.internal.Binding
-keep class * extends dagger.internal.ModuleAdapter
-keep class * extends dagger.internal.StaticInjection

# Retrofit 2.X
## https://square.github.io/retrofit/ ##
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Peacock code TODO remove
-keep class * extends androidx.appcompat.app.AppCompatActivity
-keep class * extends androidx.fragment.app.Fragment

# androidx.* libraries
-keep public class com.google.android.material.R$* { *; }

-dontwarn com.google.android.material.**
-keep class com.google.android.material.** { *; }
-keep class androidx.** { *; }
-keep interface androidx.** { *; }
-keep public class androidx.appcompat.** { *; }
-keep public class androidx.recyclerview.widget.** { *; }
-keep public class androidx.core.internal.widget.** { *; }
-keep public class androidx.core.internal.view.menu.** { *; }
-keep public class * extends androidx.core.internal.view.ActionProvider {
    public <init>(android.content.Context);
}
-keep class androidx.recyclerview.widget { *; }