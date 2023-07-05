package org.softwareengine.core.model;

public enum TransactionsType {
    PURCHASE("PURCHASE"),
    SALE("SALE"),
    DEBT_BOOK("DEBT_BOOK"),
    EXPENSES("EXPENSES")
    ;

    private final String pp;

    TransactionsType(String p) {
        pp = p;
    }

    public String getName() {
        return pp;
    }
}
