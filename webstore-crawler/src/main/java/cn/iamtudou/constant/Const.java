package cn.iamtudou.constant;

public class Const {

    //url规则集合
    public static final String[] REG_URL = {
            "http://[\\w\\d.]+.163.com/.*?.html$"
    };

    //时间规则集合
    public static final String[] REG_TIME = {
            "\\s*(?<group>.*?)\\s*来源:"
    };

    //版块名规则集合
    public static final String[] REG_PLATE = {
            ">\\s*(?<group>.*?)\\s*>\\s*正文"
    };
}
