import org.gradle.api.*
import org.jetbrains.kotlin.gradle.dsl.*

fun Project.kotlin(configure: Action<KotlinMultiplatformExtension>): Unit = extensions.configure("kotlin", configure)

val Project.kotlin: KotlinMultiplatformExtension get() = extensions.getByName("kotlin") as KotlinMultiplatformExtension
