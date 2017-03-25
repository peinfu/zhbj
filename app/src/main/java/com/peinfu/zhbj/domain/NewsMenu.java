package com.peinfu.zhbj.domain;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/23.
 *
 * 分类信息封装
 *
 * 使用Gson解析时，对象书写技巧：
 * 1，逢{}创建对象，逢[]创建集合（ArrayList）
 * 2，所有字段名称要和json返回字符高度一致
 */

public class NewsMenu {

    public int retcode;
    public ArrayList<Integer> extend;

    public ArrayList<NewsMenuData> data;

    //侧边栏菜单对象
    public class NewsMenuData{
        public int id;
        public String title;
        public int type;

        public ArrayList<NewsTabData> children;

        public String toString() {
            return "NewsMenuData [title=" + title + ", children=" + children + "]";
        }
    }

    //页签的对象
    public  class  NewsTabData{
        public int id;
        public String title;
        public int type;
        public String url;
        public String toString() {
            return "NewsTabData [title=" + title + "]";
        }
    }

    public String toString() {
        return "NewsMenu [data=" + data + "]";
    }


}
