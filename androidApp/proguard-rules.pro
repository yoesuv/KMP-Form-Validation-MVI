# R8 rules for release builds (androidApp)

# --- Readable crash traces ---
# Keep line numbers, renamed to "SourceFile" so the mapping file
# (build/outputs/mapping/release/mapping.txt) can de-obfuscate traces.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# --- kotlinx.serialization ---
# Library-embedded rules cover kotlinx.* internals; these keep the
# generated serializers of our own @Serializable classes (navigation routes).
-keepclassmembers class com.yoesuv.kmpformvalidationmvi.** {
    *** Companion;
}
-keepclasseswithmembers class com.yoesuv.kmpformvalidationmvi.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep,includedescriptorclasses class com.yoesuv.kmpformvalidationmvi.**$$serializer { *; }

# --- Compose / MVI ---
# Keep ViewModel instances created via viewModel() / reflection-free factories.
-keepclassmembers class com.yoesuv.kmpformvalidationmvi.** extends androidx.lifecycle.ViewModel {
    <init>(...);
}
