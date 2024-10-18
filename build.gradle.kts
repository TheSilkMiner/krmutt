import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea
    kotlin("jvm") version "1.9.22"
}

group = "net.thesilkminer.krmutt"
version = "0.1.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_1_8.majorVersion))
    }
}

kotlin {
    jvmToolchain(JavaLanguageVersion.of(JavaVersion.VERSION_1_8.majorVersion).asInt())
}

val java9: SourceSet by sourceSets.creating

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation(kotlin("scripting-jvm"))
    implementation(kotlin("scripting-jvm-host"))
    implementation(group = "io.github.oshai", name = "kotlin-logging-jvm", version = "6.0.1")

    java9.implementationConfigurationName(sourceSets.main.get().output)

    runtimeOnly(group = "org.slf4j", name = "slf4j-api", version = "2.0.10")
}

tasks {
    named<JavaCompile>("compileJava9Java") {
        javaCompiler.set(project.javaToolchains.compilerFor {
            languageVersion = JavaLanguageVersion.of(JavaVersion.VERSION_1_9.majorVersion)
        })
    }

    named<KotlinCompile>("compileJava9Kotlin") {
        kotlinJavaToolchain.toolchain.use(project.javaToolchains.launcherFor {
            languageVersion = JavaLanguageVersion.of(JavaVersion.VERSION_1_9.majorVersion)
        })
    }

    withType<Jar> {
        listOf(java9).forEach {
            into("META-INF/versions/${it.name.replace("java", "")}") {
                from(it.output)
            }
        }

        manifest.attributes("Multi-Release" to "true", "Automatic-Module-Name" to project.group)
        archiveBaseName.set("krmutt")
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    withType<KotlinCompile> {
        kotlinOptions.freeCompilerArgs += listOf("-Xjvm-default=all", "-Xlambdas=indy", "-Xcontext-receivers")
    }

    withType<Wrapper> {
        gradleVersion = "8.4"
        distributionType = Wrapper.DistributionType.ALL
    }
}
