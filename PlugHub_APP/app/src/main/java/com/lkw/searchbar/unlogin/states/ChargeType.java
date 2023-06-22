package com.lkw.searchbar.unlogin.states;

public enum ChargeType {
    ENTIRE(0),             //전체
    B_TYPE(1),             //B타입
    C_TYPE(2),             //C타입
    BC_TYPE_FIV(3),        //BC타입 5핀
    BC_TYPE_SEV(4),        //BC타입 7핀
    DC(5),                 //DC
    AC(6),                 //AC
    DC_COMB(7),            //DC콤보
    DC_TYPE_DC_COMB(8),    //DC타입 + DC콤보
    DC_AC(9),              //DC + AC
    DC_DC_COMB_AC(10);      //DC + DC콤보 + AC
    ChargeType(int num){
        this.num = num;
    }
    private int num;

    public int getNum() {
        return num;
    }
}