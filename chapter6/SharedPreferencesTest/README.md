# 一个使用PermissionsDispatcher做运行时授权的例子

## 1.集成方法
1.首先把采集设备信息使用类库com.otaserver.androidUtil.XXX.jar放入libs目录下，在intellj中选择文件，右键选择“add as Library”。
2.在build.gradle中会生成相关如下配置。
```
dependencies {
    implementation fileTree(include: ['*.jar'], dir: 'libs')  
    implementation files('build/libs/com.otaserver.androidUtil.1.0.1.jar')
}
```
如果用户同意授权可以获得手机号码，不同意授权可以获得，自定义的appInstallGuid和androidId属性。

## 2.配置PermissionsDispatcher



