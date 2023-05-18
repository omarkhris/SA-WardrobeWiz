package com.miu.paymentservice.other;

public class Validator {
    public static Boolean isCardNumberValid(String number, String cardType) {
        if (cardType.equals(Constants.CARD_TYPE_VISA) || cardType.equals(Constants.CARD_TYPE_MASTER)) {
            return (number.length() == 16);
        } else if (cardType.equals(Constants.CARD_TYPE_AMERICAN)) {
            return (number.length() == 15);
        } else if (cardType.equals(Constants.CARD_TYPE_PAYPAL)) {
            return (number.length() >= 9);
        }
        return false;
    }
}
