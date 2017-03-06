
# 在配置文件中出现的相对路径均是相对于该路径
-basedirectory ./out
# 指定要处理应用程序的jar(或者aars, wars, ears, zips, apks, directories)，这些文件不会包含到输出文件中。一般是指被处理文件所依赖的一些jar包，而那些jar包是不需要被处理以及写入到输出文件的
-libraryjars <java.home>/lib/rt.jar
# 指定处理的jar包（或者aars, wars, ears, zips, apks, directories）等，这个jar包里面的类将会被ProGuard处理并写入到输出的jar包里去
-injars proguard-1.0-SNAPSHOT.jar
# 设置处理完成后的输出文件路径
-outjars out.jar

# 不使用大小写混合类名，注意，windows用户必须为ProGuard指定该选项
# 因为windows对文件的大小写是不敏感的，也就是比如a.java和A.java会认为是同一个文件。如果不这样做并且你的项目中有超过26个类的话，那么ProGuard就会默认混用大小写文件名，导致class文件相互覆盖
-dontusemixedcaseclassnames

# 指定不进行压缩
-dontshrink

# 指定不对输入代码进行优化处理。优化选项是默认打开的
-dontoptimize

## 指定不进行混淆
#-dontobfuscate

## 指定不执行预检
-dontpreverify

# 强制输出，即使输出文件已经是最新状态
-forceprocessing

# 把keep匹配的类和方法输出到文件中，可以用来验证自己设定的规则是否生效
-printseeds seeds.txt

# 把没有使用的代码输出到文件中，方便查看哪些代码被压缩丢弃了
-printusage usage.txt

# 生成map文件，记录混淆前后的名称对应关系，注意，这个比较重要，因为混淆后运行的名称会变得不可读，只有依靠这个map文件来还原
-printmapping map.txt

# 输出当前ProGuard所使用的配置
-printconfiguration configuration.txt

# 指定输出所处理的类的结构
-dump dump.txt

# 把所有信息都输出，而不仅仅是输出出错信息
-verbose

# 忽略library里面非public修饰的类
# 有一种特殊情况：有些人编写的代码与类库中的类在同一个包下，而且对该包的非public类进行了使用，在这种情况下，就不能使用该选项了
-skipnonpubliclibraryclasses

# 不忽略library里面非public修饰的类
-dontskipnonpubliclibraryclasses

# 指定不忽略非public类里面的成员和方法。ProGuard默认会忽略类库里非public类里的成员和方法
-dontskipnonpubliclibraryclassmembers

# 指定被处理class文件所使用的Java版本，可选的有: 1.0, 1.1, 1.2, 1.3, 1.4, 1.5 (or just 5), 1.6 (or just 6), 1.7 (or just 7), or 1.8 (or just 8).
-target 1.7

# 指定Keep类名不被混淆，且Keep的sayHello方法和成员helloStr不被混淆
-keep public class site.hanschen.proguard.Keep {
    public void sayHello();
    private static final java.lang.String helloStr;
}

# 指定Keepclassmembers的sayHello方法不被混淆,注意Keepclassmembers的类名仍然会被混淆
-keepclassmembers public class site.hanschen.proguard.Keepclassmembers {
    public void sayHello();
}

# 通过成员来指定哪些类的类名和成员不被混淆, 指定后，对应类的类名和指定的成员不被混淆
-keepclasseswithmembers public class site.hanschen.proguard.keepclasseswithmembers {
    public static void main(java.lang.String[]);
}

# 如果指定了多条规则，如下，那么必须同时包含 sayHello 和 main 两个方法的类才会被保留
-keepclasseswithmembers public class site.hanschen.proguard.keepclasseswithmembers {
    public static void main(java.lang.String[]);
    public void sayHello();
}

# 指定 keepnames 类名以及 sayHello 方法不被混淆，但需要注意的是，若 sayHello 方法未被使用，会在压缩阶段被移除掉
-keepnames public class site.hanschen.proguard.keepnames {
    public void sayHello();
}

# 在优化阶段移除相关方法的调用
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}