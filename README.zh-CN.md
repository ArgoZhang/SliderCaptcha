## 滑块式验证码

<a href="README.md">English</a> | <span>中文</span>

---

用户通过拖动滑块行为来完成校验，支持PC端及移动端。可以将用户拖动行为的时间、精度，滑动轨迹等信息到服务器，然后进行后台算法验证。

##  **特别介绍** 

 **Blazor 版本的滑块验证码**  [传送门](http://www.blazor.zone/captchas)

## 在线演示
单页面演示：http://longbowenterprise.gitee.io/slidercaptcha/  
项目内演示：http://admin.blazor.zone/ (本项目为开源后台管理框架 [[BootstrapAdmin](https://gitee.com/LongbowEnterprise/BootstrapAdmin)])  
**输入三次错误密码后第四次出现滑块式行为验证码**  

## 效果图
![输入图片说明](https://images.gitee.com/uploads/images/2019/0316/003740_c5175e6b_554725.png "SliderCaptcha.png")
![输入图片说明](https://gitee.com/uploads/images/2019/0410/124955_f9b6d54c_554725.png "Untitled.png")

## 快速开始

### 组件依赖 font-awesome

### CSS

```html
<link href="./src/slidercaptcha.css">
```
将引入样式表的 &lt;link&gt; 标签复制并粘贴到 &lt;head&gt; 中，并放在所有其他样式表之前。

### JS

```html
<script src="./src/longbow.slidercaptcha.js"></script>
```

将引入脚本的 &lt;script&gt; 标签复制并粘贴到 &lt;body&gt; 最后面。

## 用法

添加网页Html元素

```html
<div id="captcha"></div>
```

## API

### 通过 javascript 初始化控件

```html
<div id="captcha"></div>
<script>
    sliderCaptcha({
        id: 'captcha'
    });
</script>   
```

### Options

可以根据自己需要设置宽度与高度等配置

```html
<div id="captcha"></div>
<script>
    sliderCaptcha({
        id: 'captcha',
        width: 280,
        height: 150,
        sliderL: 42,
        sliderR: 9,
        offset: 5,
        loadingText: '正在加载中...',
        failedText: '再试一次',
        barText: '向右滑动填充拼图',
        repeatIcon: 'fa fa-redo',
        setSrc: function () {
            
        },
        onSuccess: function () {
            
        },
        onFail: function () {

        },
        onRefresh: function () {
        
        }
    });
</script>   
```

名称 | 类型 | 默认值 | 说明 |
---|---|---|---
width | integer | 280 | 背景图片宽度
height | integer | 150 | 背景图标高度
sliderL | integer | 42 | 拼图宽度
sliderR | integer | 9 | 拼图突出半径
offset | integer | 5 | 验证容错偏差值 默认5个像素偏差即认为验证通过
loadingText | string | "正在加载中..." | 图片加载时显示的文本信息
failedText | string | "再试一次" | 验证失败时显示的文本信息
barText | integer | "向右滑动填充拼图" | 拖动滑块准备拖动时显示的文本信息
repeatIcon | string | "fa fa-redo" | 重新加载图标 需引用 font-awesome
setSrc | function | "https://picsum.photos/?image=random" | 设置图片加载路径
onSuccess | function | *null* | 验证通过时回调此函数
onFail | function | *null* | 验证失败时回调此函数
onRefresh | function | *null* | 点击重新加载图标时回调此函数
localImages | function | function () { return 'images/Pic' + Math.round(Math.random() * 4) + '.jpg'; } | 图床图片加载失败时调用此方法返回本地图片路径
remoteUrl | string | null | 服务器端验证请求地址，请求方式默认为 post 方式
verify	| function | function (arr, url) { return true; }	| 服务器端验证方法 arr 为客户端拖动滑块轨迹，url 为服务器端请求地址，返回值为布尔值

### 方法

```html
<div id="captcha"></div>
<script>
    var captcha = sliderCaptcha({
        id: 'captcha'
    });
    captcha.reset();
</script>   
```

Method | Example | Description
---|---|---
reset | captcha.reset() | 重置控件

## 事件
无

## Issue
请前往 [Issue](../../issues) 页面添加问题

## 服务器端认证
### 客户端代码示例
#### 1. JavaScript
控件配置信息中有 remoteUrl 和 verify 两个配置项，合理正确的设置这两个配置项即可达到想要的服务器端认证逻辑  
remoteUrl 默认值为 null 表示未启用服务器端认证方式，设置请求的 webapi 地址后启用服务器端认证方法  
控件默认请求服务器端方法如下，可适当进行更改
```js
verify: function (arr, url) {
    var ret = false;
    $.ajax({
        url: url,
        data: JSON.stringify(arr),
        async: false,
        cache: false,
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        success: function (result) {
            ret = result;
        }
    });
    return ret;
}
```

参数 | 类型 | 默认值 | 说明 |
---|---|---|---
arr | array | object | 客户端拖动滑块轨迹数组 | 
url | string | remoteUrl | 配置项中的 remoteUrl 参数值 |

完整示例代码  
```js
sliderCaptcha({
    id: 'captcha',
    repeatIcon: 'fa fa-redo',
    setSrc: function () {
        return 'https://imgs.blazor.zone/images/Pic' + Math.round(Math.random() * 136) + '.jpg';
    },
    onSuccess: function () {
        window.location.href = 'https://gitee.com/LongbowEnterprise/SliderCaptcha';
    },
    remoteUrl: "api/Captcha"
});
```

### 服务器端代码示例
#### 1. NETCore WebApi
```csharp
/// <summary>
/// 滑块服务器端验证方法
/// </summary>
[Route("api/[controller]")]
[ApiController]
[AllowAnonymous]
public class CaptchaController : ControllerBase
{
    /// <summary>
    /// 服务器端滑块验证方法
    /// </summary>
    /// <returns></returns>
    [HttpPost]
    public bool Post([FromBody]List<int> datas)
    {
        var sum = datas.Sum();
        var avg = sum * 1.0 / datas.Count;
        var stddev = datas.Select(v => Math.Pow(v - avg, 2)).Sum() / datas.Count;
        return stddev != 0;
    }
}
```

#### 2. JAVA SpringBoot
可能会存在精度问题，采用BigDecimal计算即可
```java
@RestController
@RequestMapping("/sliderCaptcha")
public class SliderCaptchaController {

	@PostMapping("/isVerify")
	public boolean isVerify(List<Integer> datas) {
		int sum = 0;
		for (Integer data : datas) {
			sum += data;
		}
		double avg = sum * 1.0 / datas.size();
		
		double sum2 = 0.0;
		for (Integer data : datas) {
			sum2 += Math.pow(data - avg, 2);
		}
		
		double stddev = sum2 / datas.size();
		return stddev != 0;
	}
	
}
```

## 常见问题

### 服务端验证的返回结果怎么一直都是true  

示例代码中演示的是前端提交用户滑动轨迹到服务器端进行了 Y 轴的平方差校验，为零时才返回 false，否则返回 true，为 true 表示 Y 轴有偏移，简单的认为此操作是人为操作，因为人手拖动过程中的抖动 Y 轴理论上是不可能没有偏移的。因此依据此值进行是否是人为拖动滑块。

[相关问题](https://gitee.com/LongbowEnterprise/SliderCaptcha/issues/I110MF?from=project-issue)  

## 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request