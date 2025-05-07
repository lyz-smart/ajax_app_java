function post (){
  const submit = document.getElementById("submit");
  submit.addEventListener("click", (e) => {
    // e 表示在发生事件的时候传入的事件对象，因为这个函数中已经设定了一个submit投稿的方法
    // 浏览器中还会有默认的submit方法，就会导致双重提交，所以要把浏览器默认操作关掉
    e.preventDefault();
    const form = document.getElementById("form");
    // formData 类，依据 form 元素的 格式，创建对应的对象
    const formData = new FormData(form);
    // 创建 http 请求对象
    const XHR = new XMLHttpRequest();
    // 对 /posts 进行 post 请求，请求时启动异步处理
    XHR.open("POST", "/posts", true);
    // 指定服务器端相应的格式，目前一般都是 json 了
    XHR.responseType = "json";
    // 将上边各项请求参数确定好之后，发送表单信息
    XHR.send(formData);
  });
};

window.addEventListener('load', post);