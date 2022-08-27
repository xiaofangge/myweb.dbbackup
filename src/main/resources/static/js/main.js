layui.use(["layer", "jquery", "miniAdmin"], function () {
    const layer = layui.layer;
    const $ = layui.jquery;
    var miniAdmin = layui.miniAdmin;

    var options = {
        iniUrl: ctx + "menu",    // 初始化接口
        urlHashLocation: true,      // 是否打开hash定位
        bgColorDefault: false,      // 主题默认配置
        multiModule: true,          // 是否开启多模块
        menuChildOpen: false,       // 是否默认展开菜单
        loadingTime: 0,             // 初始化加载时间
        pageAnim: true,             // iframe窗口动画
        maxTabNum: 20             // 最大的tab打开数量
    };
    miniAdmin.render(options);

    $("#logout").click(function () {
        layer.confirm("确定要退出该系统吗？", {icon: 3, title: "提示", shade: [0.03, "#000"]}, function (index) {
            $.get(ctx + "user/logout", function () {
                layer.close(index);
                window.location.href = "/";
            })
        });
    })


    $("#modify-password").click(function () {
        layer.open({
            type: 2,
            title: "修改密码",
            content: ctx + "modifypwd",
            area: ["520px", "315px"],
            shade: [0.03, "#000"]
        })
    })
})