package org.softwareengine.modules.finance.model;

import org.softwareengine.modules.purchases.model.Supplier;
import org.softwareengine.modules.sales.model.Customer;

public record DebtBook(String id, Customer customer, Supplier supplier, double debit, double credit) {
    public DebtBook(String id, Customer customer, Supplier supplier, double debit, double credit) {
        this.id = id;
        this.customer = customer;
        this.supplier = supplier;
        this.debit = debit;
        this.credit = credit;
    }

    public DebtBook(String id, Customer customer, double debit, double credit) {
        this(id, customer, null, debit, credit);
    }

    public DebtBook(String id, Supplier supplier, double debit, double credit) {
        this(id, null, supplier, debit, credit);
    }

    public DebtBook(double debit, double credit) {
        this(null, null, null, debit, credit);
    }
}
