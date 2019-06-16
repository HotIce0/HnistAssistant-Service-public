# HnistAssistant-Service
湖理大助手，服务端

# 现有小程序的前端
![校园大助手](http://chuantu.xyz/t6/702/1560700243x3703728804.jpg)
# 一、项目构建
Maven 构建

# 二、依赖
- lombok
- fastjson
- weixin-java-miniapp 微信小程序开发SDK
- aliyun-sdk-oss 阿里云对象存储SDK（全站的图片存储采用阿里云OSS存储）
- ansj_seg 用于实现分词搜索
- httpclient 用于编写学生认证部分，通过学校网站进行学生信息查询

# 三、实现方案使用组件
- springboot
- mysql
- mybatis
- redis实现的 session & cache

# 四、数据库设计以及SQL备份
在该项目的Github的Issue中

# 五、API接口文档
在该项目的Github的Issue中

# 六、实现细节
## 1.权限控制实现
- 采用Role,Permission方式实现权限控制，角色定义权限，再将角色授予用户
- 代码实现利用Spring的注解以及AOP切面，对Controller层的加上了PermissionAuth注解的函数，进行权限判断。因为权限认证需要频繁读取角色权限，所以将t_role表中的角色缓存至Redis。
- 具体实现查看
    - PermissionAuth注解`com.hotice0.hnist_assistant.annotation.PermissionAuth`
    - PermissionGuardAspect切面`com.hotice0.hnist_assistant.aspect.PermissionGuardAspect`

## 2.登录认证实现
- 登录认证实现的方式也是Spring注解+AOP切面，这样的注解配置的方式，比较Controller继承实现，更加灵活。
- 具体实现查看
    - BasicLoginAuth注解`com.hotice0.hnist_assistant.annotation.BasicLoginAuth`
    - BasicLoginAuthAspect切面`com.hotice0.hnist_assistant.aspect.BasicLoginAuthAspect`

## 3.学生认证实现
- 通过学校的信息查询网站进行认证:http://uia.hnist.cn/main.jsp
- 具体实现代码
    - `com.hotice0.hnist_assistant.service.hnist2_module.Hnist2UserService 类`

## 4.分词搜索商品实现
- 通过开源分词库：ansj_seg 实现
- 实现思路
    - 对请求传递的关键词（例如`笔记本电脑`），使用ansj_seg进行分词，将得到的分词用`|`分隔。得到类似于`笔记本|电脑`这样的字符串.因为`|`在正则匹配中刚好代表或逻辑。
    - 然后将该字符串直接拼接至SQL中，采用MySQL的正则匹配`REGEXP`对需要搜索的区域进行匹配。
- 通过该方式实现由于是全表搜索，会对记录加锁，性能比较低，推荐通过新建立专门的搜索表，在商品创建时进行关键词提取。

# 七、补充说明
1. 权限编号定义在`com.hotice0.hnist_assistant.db.model.BasicPermission中`
2. 角色ID定义在`com.hotice0.hnist_assistant.db.model.BasicRole中`
3. 相关常量一般都定义在相关的model模型中。
4. 错误code和错误消息定义在`com.hotice0.hnist_assistant.exception.error.HAError中`

# 八、部署注意
1. 请编写好定时任务脚本，在固定时间，自动清理图片上传临时文件夹内的内容
