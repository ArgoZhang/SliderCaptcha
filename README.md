## Slider Captcha

<span>English</span> | <a href="README.zh-CN.md">中文</a>

---

The user completes the verification by dragging the slider to support the PC and mobile terminals. The time, accuracy and sliding trajectory information of user dragging behavior can be sent to the server, and then the background algorithm verification can be carried out.

## Blazor Version

http://blazor.sdgxgz.com/captchas

## Online Demonstration
Single page presentation: http://longbowenterprise.gitee.io/slidercaptcha/  
In-Project Demonstration: https://admin.blazor.zone/ (Open source Admin Control Pannel [[BootstrapAdmin](https://github.com/ArgoZhang/BootstrapAdmin)])  
**Slide captcha appears for the fourth time after three times of incorrect password input**  

## Screenshot
![输入图片说明](https://images.gitee.com/uploads/images/2019/0316/003740_c5175e6b_554725.png "SliderCaptcha.png")
![输入图片说明](https://gitee.com/uploads/images/2019/0410/124955_f9b6d54c_554725.png "Untitled.png")

## Quick Start

### Dependencies 
font-awesome

### CSS

```html
<link href="https://cdn.bootcss.com/font-awesome/5.7.2/css/all.min.css">
<link href="./src/slidercaptcha.css">
```
Copy-paste the stylesheet `<link>` into your `<head>` before all other stylesheets to load our CSS.

### JS

```html
<script src="./src/longbow.slidercaptcha.js"></script>
```

Place the following `<script>`s near the end of your pages, right before the closing `</body>` tag, to enable them. 

## Usage

```html
<div id="captcha"></div>
```

## API

### JavaScript behavior

```html
<div id="captcha"></div>
<script>
    sliderCaptcha({
        id: 'captcha'
    });
</script>   
```

### Options

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
        loadingText: 'Loading...',
        failedText: 'Try again',
        barText: 'Slide right to fill',
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

Name | Type | Default | Description |
---|---|---|---
width | integer | 280 | Background picture width
height | integer | 150 | Background picture height
sliderL | integer | 42 | Puzzle Width
sliderR | integer | 9 | Puzzle Outburst Radius
offset | integer | 5 | Validation of error tolerance deviation. default 5px
loadingText | string | "Loading..." | Text information displayed when images are loaded
failedText | string | "Try again" | Text information displayed when validation fails
barText | integer | "Slide right to fill" | Text information displayed when dragging the slider to prepare for dragging
repeatIcon | string | "fa fa-redo" | Reload icons. dependent on `font-awesome`
setSrc | function | "https://picsum.photos/?image=random" | Setting the Picture Loading Path
onSuccess | function | *null* | Callback this function when validation passes
onFail | function | *null* | Callback this function when validation fails
onRefresh | function | *null* | Callback this function when click on the reload icon
localImages | function | function () { return 'images/Pic' + Math.round(Math.random() * 4) + '.jpg'; } | Call this function when the image loading fails

### Methods

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
reset | captcha.reset() | reset

## Events

None

## Issue
Please go to [Issue](../../issues) page to create issue

## Verify On Server Side
### Client Code Example
#### 1. JavaScript
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

Parameter | Type | Default | Descript |
---|---|---|---
arr | array | object | trails of user dragging slider  | 
url | string | remoteUrl | option.remoteUrl |

sample code
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

### Server Code Example
#### 1. NETCore WebApi
```csharp
/// <summary>
/// slider verify web api
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
You may have precision problems, but you can use BigDecimal optimization
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

## Q&A

[linked issue](https://gitee.com/LongbowEnterprise/SliderCaptcha/issues/I110MF?from=project-issue)  

## Contribution

1. Fork this project
2. Create new Feat_xxx branch
3. Commit 
4. Create Pull Request