package seckill.example.entity;

/**
 * Created by wang on 17-6-26.
 */
public class UserInfo {

    private long userInfoId;
    private long userPhone;
    private long balances;

    public long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public long getBalances() {
        return balances;
    }

    public void setBalances(long balances) {
        this.balances = balances;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userInfoId=" + userInfoId +
                ", userPhone=" + userPhone +
                ", balances=" + balances +
                '}';
    }
}
