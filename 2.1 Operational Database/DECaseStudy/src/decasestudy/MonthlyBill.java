/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decasestudy;

/**
 *
 * @author alwinmathew
 */
public class MonthlyBill 
{
    String CREDIT_CARD_NO;
    double SUM;
    
    public MonthlyBill(String credit_card_no, double sum)
    {
        this.CREDIT_CARD_NO = credit_card_no;
        this.SUM = sum;
    }
    
    //get and set for CREDIT_CARD_NO
    public String getCreditCardNo()
    {
        return this.CREDIT_CARD_NO;
    }
    public void setCreditCard(String credit_card_no)
    {
        this.CREDIT_CARD_NO = credit_card_no;
    }
    //get and set for SUM
    public double getSum()
    {
        return this.SUM;
    }
    public void setSum(double sum)
    {
        this.SUM = sum;
    }
}
