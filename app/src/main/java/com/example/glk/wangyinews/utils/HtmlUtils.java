package com.example.glk.wangyinews.utils;

/**
 * Created by zgqdg on 2016/11/19.
 */

public class HtmlUtils {
    public static String head1 = "<html lang=\"zh\"><head><meta charset=\"UTF-8\"><title>";

    public static String head2 = "</title><script>window.NRUM=window.NRUM||{},window.NRUM.config={key:\"27e86c0843344caca7ba9ea652d7948d\",clientStart:+new Date},function(){var e=document.getElementsByTagName(\"script\")[0],t=document.createElement(\"script\");t.type=\"text/javascript\",t.async=!0,t.src=\"//nos.netease.com/apmsdk/napm-web-min-1.1.1.js\",e.parentNode.insertBefore(t,e)}()</script><meta id=\"viewport\" name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1\"><meta name=\"format-detection\" content=\"telephone=no\"><link rel=\"stylesheet\" href=\"http://img6.cache.netease.com/utf8/3g-new/css/article.5fdd040ffafdcf2e4e605e5e41a376aa.css\"></head><body ontouchstart=\"\"><script>!function(e,t){var n=e.documentElement,i=\"orientationchange\"in window?\"orientationchange\":\"resize\",d=(/iPad|iPhone|iPod/.test(navigator.userAgent)&&!window.MSStream,function(){var t=n.clientWidth;t&&(t>=750?(t=750,e.body.style.width=\"750px\"):e.body.style.width=t+\"px\",n.style.fontSize=100*(t/750)+\"px\",n.dataset.width=t,n.dataset.percent=100*(t/750))});d(),e.documentElement.classList.add(\"iosx\"+t.devicePixelRatio),e.addEventListener&&t.addEventListener(i,d,!1)}(document,window)</script><iframe id=\"iframe\" style=\"display:none!important\"></iframe><img src=\"http://img6.cache.netease.com/utf8/3g/touch/images/share-logo.png\" style=\"display:none!important\"><div class=\"js-analysis\"><div class=\"g-body-wrap\"><div class=\"m-article-content js-article-content\"><article class=\"text-content js-text-content\"><div class=\"head\"><h1 class=\"article-title\">";

    public static String head3 = "</h1><h2><span class=\"source\">";

    public static String tail1 = "</span> <span class=\"time\">";

    public static String tail2 = "</span></h2><div class=\"article-digest\"></div></div><div class=\"text\"><p></p><div class=\"photoNews\">";

    public static String tail3 = "</div></div></article></div></div></div></body></html>";
}
