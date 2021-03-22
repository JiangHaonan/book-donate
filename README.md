# 书籍捐赠
## 说明
项目包含功能书籍查询、捐赠下单、物流通知。  
模块分明，业务简单，学习必备。  
(当前仅有后端接口)
## 主要技术栈
* Java8
* springcloud （eureka、ribbon、hystrix、feign、gateway）
## 项目结构
+ common —— 包含各个业务模块所需要的公共资源。  
+ domain&nbsp;&nbsp; —— 业务模块，包含用户、认证、捐赠地区、数据、订单、配送。  
+ platform —— 注册中心、网关。  
<img width="700" height="445" src="https://jianghaonan.github.io/images/book-donate-architecture.png"/>  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;项目架构图

## 中间件
* seata: 保证各个分布式服务的事务一致性
* redis: 缓存书籍查询内容
* rabbitmq: 订单支付成功，投递消息到配送系统，达到削峰解耦的目的
<img width="605" height="405" src="https://jianghaonan.github.io/images/book-donate-middleware.png"/>  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中间件作用图

## 用户流程
<img width="480" height="520" src="https://jianghaonan.github.io/images/book-donate-processor.png"/>  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;流程图


## 项目地址
k8s: https://132.232.64.25:30000/#/login  (namespace: donate)  
k8sToken(请勿操作): eyJhbGciOiJSUzI1NiIsImtpZCI6Iml3LXAzckd1dFB4d3AtRzl1VEtWOWtUMW43dC13Q2p3RzN5aU9QZHlpUmsifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlcm5ldGVzLWRhc2hib2FyZCIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJhZG1pbi11c2VyLXRva2VuLTJudjY0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluLXVzZXIiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiJmY2UzNmU5ZC00M2MxLTRjZGEtYTBhYy02NTNhN2VkOTZjYWYiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6a3ViZXJuZXRlcy1kYXNoYm9hcmQ6YWRtaW4tdXNlciJ9.osn57rdvn8XtwxAEURLlXglB_-3Tq6BwIHFvTbBg9nkeLYJxxu1CvARZQn0C3XP36Te2Kl8YTBwKvSBmzavy_fVXx4eriSntAboqN207mccUs96vx3mAaU2J3dRV_GbiGLKOJyq8zYErngN6sacBJeTbUi4N_RGeKf9sF70viGz5vVKmpFd9VAf4RnwoQMSFQU0GovD1MP8UHTaN7IfVmfXjLjBSCuaYo23-y6A9Zn6M45AiMAGCLNh13MWzvjRSFLM2paq6iCadwk_pGienqyJmy2ng5WpNvKdNacRQr52CuLbvhufVR8aR0x1SYT5zjy6yVlATvTri0qPOXCCjrQ  
swagger1: http://132.232.64.25:31002/swagger-ui.html  
swagger2: http://132.232.64.25:31002/doc.html  

## 联系方式
一起技术交流: 357953710@qq.com
