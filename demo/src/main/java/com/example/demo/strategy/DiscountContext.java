package com.example.demo.strategy;

public class DiscountContext {
    private DiscountStrategy strategy;

    public void setStrategy(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculate(double originalPrice) {
        if (strategy == null) {
            return originalPrice;
        }
        return strategy.applyDiscount(originalPrice);
    }

    public static DiscountStrategy getStrategyByType(String type) {
        if (type == null) return new NoDiscountStrategy();
        return switch (type.toUpperCase()) {
            case "MEMBER" -> new MemberDiscountStrategy();
            case "SEASONAL" -> new SeasonalSaleStrategy();
            default -> new NoDiscountStrategy();
        };
    }
}