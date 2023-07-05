package org.softwareengine.modules.warehouse.model;

public record Stock(
        int seq,
        String id,
        Store store,
        Product product,
        double purchasePrice,
        double salePrice,
        double _package,
        double quantity
) {

    public Stock(String id, Store store, Product product, double purchasePrice, double salePrice, double _package, double quantity) {
        this(0, id, store, product, purchasePrice, salePrice, _package, quantity);
    }


    public int getSeq() {
        return seq;
    }

    public String getId() {
        return id;
    }

    public String getStore() {
        return store.name();
    }

    public String getProduct() {
        return product.name();
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public double get_package() {
        return _package;
    }

    public double getQuantity() {
        return quantity;
    }

}
