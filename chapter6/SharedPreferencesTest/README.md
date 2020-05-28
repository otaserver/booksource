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
2.1 首先安装PermissionsDispatcher插件。  
2.2 在app的build.gradle中加入依赖。
```
dependencies {    
    //仿照官网自己加入的代码，注意不要使用插件来加入引入。因为插件这部分配置经测试好像不生效。
    implementation 'org.permissionsdispatcher:permissionsdispatcher:4.6.0'
    annotationProcessor "org.permissionsdispatcher:permissionsdispatcher-processor:4.6.0"   
}
```
2.3.在需要权限的方法前，右键Code，Generate，Generate runtime Permission(),然后选中需要的权限，在下方输入你指定的需要权限的方法名。  
2.4 要使用代理生成的带有运行时权限的方法。例如：MainActivityPermissionsDispatcher.xxxWithPermissionCheck(this);而不要直接使用代理方法中回调的 @NeedsPermission方法。否则运行时会报异常。


注意1：如果使用gson格式保存，需要引入
```
dependencies {   
    implementation 'com.google.code.gson:gson:2.8.5'
｝
```
注意2：permissionsdispatcher产生的代码不要改动，因为其是只读的。每次build会重新生成。修改的代码会无效。


