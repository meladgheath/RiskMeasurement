CREATE TABLE "public"."customer" (
    "id" varchar NOT NULL,
    "name" varchar NOT NULL,
    "phone" varchar,
    "created_at" timestamp DEFAULT CURRENT_TIMESTAMP,
    "updated_at" timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY ("id")
);

CREATE TABLE "public"."debt_book" (
    "id" varchar NOT NULL,
    "customer_id" varchar,
    "debit" numeric NOT NULL,
    "credit" numeric NOT NULL,
    "created_at" timestamp DEFAULT CURRENT_TIMESTAMP,
    "updated_at" timestamp DEFAULT CURRENT_TIMESTAMP,
    "supplier_id" varchar,
    PRIMARY KEY ("id")
);

CREATE TABLE "public"."product" (
    "id" varchar NOT NULL,
    "code" varchar NOT NULL,
    "name" varchar NOT NULL,
    "created_at" timestamp DEFAULT CURRENT_TIMESTAMP,
    "updated_at" timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY ("id")
);

CREATE TABLE "public"."stock" (
    "id" varchar NOT NULL,
    "store_id" varchar NOT NULL,
    "product_id" varchar NOT NULL,
    "purchase_price" numeric,
    "sale_price" numeric,
    "package" int4,
    "quantity" int4,
    "created_at" timestamp DEFAULT CURRENT_TIMESTAMP,
    "updated_at" timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY ("id")
);

CREATE TABLE "public"."store" (
    "id" varchar NOT NULL,
    "name" varchar NOT NULL,
    "created_at" timestamp DEFAULT CURRENT_TIMESTAMP,
    "updated_at" timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY ("id")
);

CREATE TABLE "public"."supplier" (
    "id" varchar NOT NULL,
    "name" varchar NOT NULL,
    "phone" varchar,
    "created_at" timestamp DEFAULT CURRENT_TIMESTAMP,
    "updated_at" timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY ("id")
);

CREATE TABLE "public"."transactions" (
    "id" varchar NOT NULL,
    "product_id" varchar,
    "debit" numeric,
    "credit" numeric,
    "quantity" int4,
    "store_id" varchar,
    "treasury_id" varchar,
    "transactions_type" varchar,
    "bill_num" int4,
    "created_at" timestamp DEFAULT CURRENT_TIMESTAMP,
    "updated_at" timestamp DEFAULT CURRENT_TIMESTAMP,
    "customer_id" varchar,
    "supplier_id" varchar,
    "discount" numeric DEFAULT 0,
    "payment_method" varchar,
    PRIMARY KEY ("id")
);

CREATE TABLE "public"."treasury" (
    "id" varchar NOT NULL,
    "name" varchar,
    "amount" numeric,
    "created_at" timestamp DEFAULT CURRENT_TIMESTAMP,
    "updated_at" timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY ("id")
);

ALTER TABLE "public"."debt_book" ADD FOREIGN KEY ("supplier_id") REFERENCES "public"."supplier"("id");
ALTER TABLE "public"."debt_book" ADD FOREIGN KEY ("customer_id") REFERENCES "public"."customer"("id");
ALTER TABLE "public"."stock" ADD FOREIGN KEY ("product_id") REFERENCES "public"."product"("id");
ALTER TABLE "public"."stock" ADD FOREIGN KEY ("store_id") REFERENCES "public"."store"("id");
ALTER TABLE "public"."transactions" ADD FOREIGN KEY ("product_id") REFERENCES "public"."product"("id");
ALTER TABLE "public"."transactions" ADD FOREIGN KEY ("customer_id") REFERENCES "public"."customer"("id");
ALTER TABLE "public"."transactions" ADD FOREIGN KEY ("supplier_id") REFERENCES "public"."supplier"("id");
ALTER TABLE "public"."transactions" ADD FOREIGN KEY ("store_id") REFERENCES "public"."store"("id");
ALTER TABLE "public"."transactions" ADD FOREIGN KEY ("treasury_id") REFERENCES "public"."treasury"("id");
