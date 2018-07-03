package cn.iamtudou.service;

public interface StaffFactory {

    /**
     * 员工登陆校验
     * @param telephone
     * @param password
     * @return
     */
    String staffLoginJudege(String telephone, String password);
}
