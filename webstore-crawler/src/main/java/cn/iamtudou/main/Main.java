package cn.iamtudou.main;

import cn.iamtudou.service.impl.ParseImpl;
import cn.iamtudou.service.Service;

public class Main {

    public static void main(String[] args) {
        Service service = new ParseImpl();
        service.parse();
    }
}
