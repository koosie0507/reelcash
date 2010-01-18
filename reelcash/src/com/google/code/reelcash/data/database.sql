create table if not exists contacts (
    contactid INTEGER PRIMARY KEY ASC,
    name text not null,
    siccode text not null,
    commerceregno text not null,
    address text not null,
    region text not null,
    country text not null,
    iban text not null,
    bank text not null,
    socialcapital text,
    repname text,
    repidtype text,
    repidentification text,
    repaddress text
);

create table if not exists invoices (
    invoiceid INTEGER PRIMARY KEY ASC,
    number text not null,
    series text,
    invoicedate datetime not null,
    duedate datetime not null,
    customer text not null,
    billingaddress text not null,
    region text not null,
    country text not null,
    siccode text not null,
    commerceregno text not null,
    iban text not null,
    bank text not null
);

create table if not exists invoicedetails (
    invoicedetailid INTEGER PRIMARY KEY ASC,
    invoiceid int not null,
    position int not null,
    name text not null,
    unit text not null,
    quantity int not null,
    price double not null,
    vatvalue double not null,
    value double not null
);
