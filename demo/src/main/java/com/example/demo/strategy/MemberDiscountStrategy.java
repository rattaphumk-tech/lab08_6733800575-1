package com.example.demo.strategy;

public class MemberDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double price) {
        return price * 0.90;
    }
}