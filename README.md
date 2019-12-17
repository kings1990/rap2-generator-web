# Rap2 Generator Web

## Rap2 Generator Web是啥
以网页操作形式将java类解析录入到rap2管理端

## 组成
* 配置
* 类文件
* 输入
* 输出

## 介绍
### 配置
> sid:  
> rap2 管理端cookie中的koa.sid参数

> sig
> rap2 管理端cookie中的koa.sig参数

> 接口地址  
> 跳转到接口页面对应的链接

> 参数形式  
> Query Params/Body Params

![sig/sid](https://oscimg.oschina.net/oscnet/up-d7c645787582a4a8d8d51c59956d87e5d1e.JPEG)


### 类文件
#### 输入/输出java文件
输入java文件为rap2管理端输入参数  
输出java文件为rap2管理端输出参数

输出java文件只有在***响应result具体类型***为Object的时候才起作用

#### 输出java文件属性介绍  
> 响应result类型  
> 指的是响应result的类型,包含Object、String、Number、Object、Boolean五种类型.如果是Array则响应返回json如下

```
{
  "retCode": null,
  "msg": null,
  "url": null,
  "result": []
  }
}
```

> 响应result具体类型  
> result中的参数类型,包含String、Number、Object、Boolean四种类型

> 输出className  
> 输出参数的java类名,上传文件后会自动填充

> 输出描述  
> 只有在***响应result具体类型***才能输入,作为返回值为String、Number、Boolean的具体介绍


#### 注意事项
java文件上传的第一个为主解析类,意思为如果A继承B、B继承C,那么如果你输出参数为A类就必须先上传A类,再上传其他类,其他情况比如A类中有List<B>这样的属性,道理类似

### 输入
解析后的输入参数,可以对字段进行修改和删除

### 输出
解析后的输出参数,可以对字段进行修改和删除

## 操作
输入必要的表单,上传类文件,然后点击**解析**按钮,待解析成功检查输入和输出参数无误后再点击**录入rap2**按钮,等结果就行