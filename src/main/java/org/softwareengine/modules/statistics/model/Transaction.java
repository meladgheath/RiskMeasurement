package org.softwareengine.modules.statistics.model;

import org.softwareengine.core.controller.HomeController;
import org.softwareengine.core.model.PaymentMethod;
import org.softwareengine.core.model.TransactionsType;
import org.softwareengine.modules.finance.model.Treasury;
import org.softwareengine.modules.purchases.model.Supplier;
import org.softwareengine.modules.sales.model.Customer;
import org.softwareengine.modules.warehouse.model.Product;
import org.softwareengine.modules.warehouse.model.Store;

import java.util.UUID;

public record Transaction(String id ,

                          int seq,
                          Product product,
                          double debit ,
                          double credit,
                          double quantity,
                          Store store,
                          Treasury treasury,
                          TransactionsType type,
                          int billNum,
                          Customer customer,
                          Supplier supplier,
                          double discount,
                          PaymentMethod paymentMethod,
                          String desc) {
    public Transaction(String id, int seq, Product product, double debit, double credit, double quantity, Store store, Treasury treasury, TransactionsType type, int billNum, Customer customer, Supplier supplier, double discount, PaymentMethod paymentMethod) {
        this(id, seq, product, debit, credit, quantity, store, treasury, type, billNum, customer, supplier, discount, paymentMethod, null);
    }

    public Transaction(String id, double debit, Treasury treasury, TransactionsType type,PaymentMethod p, String desc) {
        this(id, 0, null, debit, 0, 0, null, treasury, type, 0, null, null, 0, p, desc);
    }



    public String getId() {
        return id;
    }


    public int getSeq() {
        return seq;
    }


    public String getProduct() {
        return product.name();
    }
    public double getDebit() {
        return debit;
    }


    public double getCredit() {
        return credit;
    }


    public double getQuantity() {
        return quantity;
    }


    public String getStore() {
        return store.name();
    }
    public String getTreasury() {
        return treasury.name();
    }


    public String getType() {
        return type.getName();
    }


    public int getBillNum() {
        return billNum;
    }


    public String getCustomer() {
        return customer.name();
    }


    public String getSupplier() {
        return supplier.name();
    }


    public double getDiscount() {
        return discount;
    }


    public String getPaymentMethod() {
        return paymentMethod.name();
    }
    public String getDesc() {
        return desc;
    }
}

