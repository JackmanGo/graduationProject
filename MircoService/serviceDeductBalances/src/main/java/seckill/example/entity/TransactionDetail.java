package seckill.example.entity;

/**
 * Created by wang on 17-6-26.
 */
public class TransactionDetail {
    private long transactionDetailId;
    private long userPhone;
    private long goodsId;
    private long beforeBalances;
    private long afterBalances;

    public long getTransactionDetailId() {
        return transactionDetailId;
    }

    public void setTransactionDetailId(long transactionDetailId) {
        this.transactionDetailId = transactionDetailId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public long getBeforeBalances() {
        return beforeBalances;
    }

    public void setBeforeBalances(long beforeBalances) {
        this.beforeBalances = beforeBalances;
    }

    public long getAfterBalances() {
        return afterBalances;
    }

    public void setAfterBalances(long afterBalances) {
        this.afterBalances = afterBalances;
    }

    @Override
    public String toString() {
        return "TransactionDetail{" +
                "transactionDetailId=" + transactionDetailId +
                ", userPhone=" + userPhone +
                ", goodsId=" + goodsId +
                ", beforeBalances=" + beforeBalances +
                ", afterBalances=" + afterBalances +
                '}';
    }
}
