import org.gradle.api.*
import org.gradle.api.artifacts.*
import org.gradle.api.internal.catalog.*
import org.gradle.api.provider.*
import org.jetbrains.kotlin.gradle.dsl.*

class JvmTarget(
    vararg val jdks: Int
)

enum class JsTarget(
    val supportBrowser: Boolean,
    val supportNodejs: Boolean
) {
    Browser(true, false),
    Nodejs(false, true),
    Both(true, true)
}

enum class NativeTargets {
    Posix,
    Nix,
    Darwin
}

fun Project.library(block: Library.() -> Unit) {
    Library().apply(block).applyTo(kotlin)
}

class Library internal constructor() {
    private var jvm: JvmTarget? = JvmTarget(8, 11, 17)
    private var js: JsTarget? = JsTarget.Both
    private var native: NativeTargets? = NativeTargets.Posix
    private val uses: MutableList<DelegatingProjectDependency> = mutableListOf()
    private val dependencies: MutableList<Provider<MinimalExternalModuleDependency>> = mutableListOf()
    private var explicitApi: ExplicitApiMode = ExplicitApiMode.Strict

    fun explicitApi(value: ExplicitApiMode) {
        explicitApi = value
    }

    fun description(value: String) {}
    fun targets(
        jvm: JvmTarget? = JvmTarget(8, 11, 17),
        js: JsTarget? = JsTarget.Both,
        native: NativeTargets? = NativeTargets.Posix,
    ) {
    }

    fun uses(vararg values: DelegatingProjectDependency) {
        uses += values
    }

    fun dependencies(vararg values: Provider<MinimalExternalModuleDependency>) {
        dependencies += values
    }

    internal fun applyTo(extension: KotlinMultiplatformExtension): Unit = extension.run {
        explicitApi = this@Library.explicitApi

        jvm?.run {
            jvm()

//            jvmToolchain {
//                this as JavaToolchainSpec
//                languageVersion.set(JavaLanguageVersion.of(8))
//            }
        }
        js?.run {
            js {
                nodejs()
            }
        }
        native?.run {
            linuxX64()
        }
        sourceSets.getByName("commonMain").dependencies {
            uses.forEach { api(it) }
            dependencies.forEach { api(it) }
        }
    }
}
