create table if not exists `countries` (
	-- country registry
	`id` INTEGER PRIMARY KEY,
	`name` text not null,
	`iso_name` text not null,
	`iso_code2` char(2) not null,
	`iso_code3` char(3) not null,
	constraint uq_iso_name unique(`iso_name`),
	constraint uq_iso2 unique(`iso_code2`),
	constraint uq_iso3 unique(`iso_code3`)
);

create table if not exists `regions` (
	-- regions are the largest administrative regions within a country
	`id` INTEGER PRIMARY KEY,
	`country_id` integer not null,
	`name` text not null,
	`code` text,
	constraint uq_regioncountry unique(`country_id`, `name`),
	constraint fk_regions_country foreign key(`country_id`) references `countries`(`id`)
);

create table if not exists `counties` (
	-- counties are administrative regions within country regions
	`id` INTEGER PRIMARY KEY,
	`region_id` integer not null,
	`name` text not null,
	`code` text,
	constraint uq_countyregion unique(`region_id`, `name`),
	constraint fk_counties_region foreign key(`region_id`) references `regions`(`id`) on delete cascade on update cascade
);

create table if not exists `cities` (
	-- cities may be any communes, villages, towns, cities and whatnot within counties
	`id` INTEGER PRIMARY KEY,
	`county_id` integer not null,
	`name` text not null,
	`code` text,
	constraint uq_citycountycode unique(`county_id`, `name`, `code`),
	constraint fk_cities_county foreign key (`county_id`) references `counties`(`id`) on delete cascade on update cascade
);

create table if not exists `locations` (
	-- locations coordinates within a city area
	-- ultimately 2 locations are the same if they are within the same city
	-- and have the same address and postal code
	`id` INTEGER PRIMARY KEY,
	`address` text not null,
	`postal_code` text not null default(''),
	`city_id` integer not null,
	constraint uq_address_pc_city unique(`address`,`postal_code`,`city_id`),
	constraint fk_locations_city foreign key (`city_id`) references `cities`(`id`) -- can't just go around deleting cities
);

create table if not exists `contacts` (
	-- a contact is a named person ( real person )
	`id` INTEGER PRIMARY KEY,
	`name` text not null,
	`surname` text not null,
        `location_id` integer not null, -- default contact location
	constraint fk_contacts_city foreign key(`location_id`) references `locations`(`id`)
);

create table if not exists `contact_locations` (
	-- a contact may have several locations where it can be reached
	`id` INTEGER PRIMARY KEY,
	`contact_id` integer not null,
	`location_id` integer not null,
	`subdivision` text, -- contacts may reside at the same or at different subdivisions per location
	`priority` integer not null default(1), 
	constraint fk_locations_contact foreign key (`contact_id`) references `contacts`(`id`),
	constraint fk_contacts_location foreign key (`location_id`) references `locations`(`id`)
);

create table if not exists `contact_identities` (
	-- a contact may be identified in several ways (SSN, Passport, etc.)
	`id` INTEGER PRIMARY KEY,
	`contact_id` integer not null,
	`identity_type` text not null,
	`identity_field1` text not null,
	`identity_field2` text not null default(''),
	`identity_field3` text,
	`identity_field4` text,
	constraint uq_identitytype_identity unique(`contact_id`, `identity_type`, `identity_field1`, `identity_field2`),
	constraint fk_identities_contact foreign key (`contact_id`) references `contacts`(`id`) on delete cascade on update cascade
);

create table if not exists `phones` (
	-- phone registry
	`id` INTEGER PRIMARY KEY,
	`phone` text not null,
	`call_charge` text -- a list of some sort with values such as 'low', 'high', 'urban', 'international'
);

create table if not exists `web_addresses` (
	-- web address registry
	`id` INTEGER PRIMARY KEY,
	`address` text not null,
	`address_type` text not null, -- a text such as 'e-mail', 'web' or 'skype'
	`custom_address_denomination` text, -- how do you usually call this address - a 'web' address could be a 'web' link, while a 'skype' address might be a 'skype' 'account'
	constraint uq_addresswithtype unique(`address`, `address_type`) -- don't clog the database with the same address twice
);

create table if not exists `contact_phones` (
	-- m to n relationship table between contacts and phones - m contacts may share a phone, while n phones may belong to a contact
	`id` INTEGER PRIMARY KEY,
	`contact_id` integer not null,
	`phone_id` integer not null,
	`priority` integer not null default(1),
	`category` text,
	`remarks` text,
	constraint uq_contactphone_category unique(`contact_id`, `phone_id`, `category`),
	constraint fk_phones_contact foreign key (`contact_id`) references `contacts`(`id`),
	constraint fk_contacts_phone foreign key (`phone_id`) references `phones`(`id`)
);

create table if not exists `contact_web_addresses` (
	-- m to n relationship table between contacts and web addresses - the same logic as with phones
	`id` INTEGER PRIMARY KEY,
	`contact_id` integer not null,
	`web_address_id` integer not null,
	`priority` integer not null default(1),
	constraint fk_web_addresses_contact foreign key (`contact_id`) references `contacts`(`id`),
	constraint fk_contacts_web_address foreign key (`web_address_id`) references `web_addresses`(`id`)
);

create table if not exists `banks` (
	-- bank registry - banks have a location, a name and a parent bank
	`id` INTEGER PRIMARY KEY,
	`name` text not null,
	`location_id` integer not null,
	`parent_id` INTEGER,
        `allow_currency_exchange` bit not null default(0),
	constraint uq_namelocation unique(`name`,`location_id`),
	constraint fk_parentbank foreign key (`parent_id`) references `banks`(`id`)
);

create table if not exists `bank_accounts` (
	-- accounts open at banks
	`id` INTEGER PRIMARY KEY,
	`bank_id` integer not null,
	`account` text not null,
	constraint fk_accounts_bank foreign key(`bank_id`) references `banks`(`id`)
);

create table if not exists `legal_statuses` (
	-- a legal status stands to represent the legal status of a contracting party
	-- e.g: SRL, PFA, PF are all legal statuses
	`id` INTEGER PRIMARY KEY,
	`code` nchar(3) not null,
	`name` text not null,
	`description` text,
	constraint uq_code unique(`code`),
	constraint uq_name unique(`name`)
);

create table if not exists `businesses` (
	-- a business is an entity capable of signing contracts. not to be confused with contacts.
	`id` INTEGER PRIMARY KEY,
	`name` text not null,
	`location_id` integer not null,
	`bank_account_id` integer not null,
	`legal_status_id` integer not null,
	constraint fk_business_location foreign key (`location_id`) references `locations`(`id`),
	constraint fk_business_bank_account foreign key (`bank_account_id`) references `bank_accounts`(`id`),
	constraint fk_business_legal_status foreign key (`legal_status_id`) references `legal_status`(`id`)
);

create table if not exists `business_representatives` (
	-- specify which contact represents which business here
	`id` INTEGER PRIMARY KEY,
	`business_id` integer not null,
	`contact_id` integer not null,
	`text` not null,
	`description` text,
	constraint uq_business_contact unique(`business_id`, `contact_id`),
	constraint fk_businesses_contact foreign key(`business_id`) references `businesses`(`id`),
	constraint fk_contacts_business foreign key(`contact_id`) references `contacts`(`id`)
);

create table if not exists `fiscal_identification` (
	-- specify identification for the company
	`id` INTEGER PRIMARY KEY,
	`business_id` integer not null,
	`name` text not null,
	`value` text not null,
	constraint uq_identification_name_business unique(`business_id`, `name`),
	constraint fk_fiscal_identification_business foreign key (`business_id`) references `businesses`(`id`)
);

create table if not exists `document_types` (
	-- specify what type of document this is
	`id` INTEGER PRIMARY KEY,
	`name` text not null,
	`description` text,
	constraint uq_legal_document_type_name unique(`name`)
);

create table if not exists `document_attributes` (
	-- attributes which are specific to a document type
	`id` INTEGER PRIMARY KEY,
	`document_type_id` integer not null,
	`name` text not null,
	`value_type` text not null,
	`max_length` integer,
	`description` text,
	`custom_format` text,
	`check_expression` text,
	constraint uq_attribute_name unique (`document_type_id`, `name`),
	constraint fk_attributes_document_type foreign key(`document_type_id`) references `document_types`(`id`)
);

create table if not exists `document_states` (
	-- states in which a document may be
	`id` INTEGER PRIMARY KEY,
	`name` text not null,
	`description` text not null,
	constraint uq_document_state_name unique(`name`)
);

create table if not exists `documents` (
	-- documents
	`id` INTEGER PRIMARY KEY, -- document ID - not displayed
	`number` text not null, -- internal number which is used to identify the document
	`type_id` integer not null, -- type of document
	`issuer_id` integer not null, -- issuer of document
	`recipient_id` integer not null, -- recipient of document
	`state_id` int not null, -- document's current state
	`create_date` datetime not null, -- date/time of record creation in DB
	`date_issued` datetime not null, -- date/time of legal emission of document
	`date_due` datetime, -- date/time document will produce effects
	constraint uq_document_number unique(`number`),
	constraint fk_issuer_business foreign key(`issuer_id`) references `businesses`(`id`),
	constraint fk_recipient_business foreign key(`recipient_id`) references `businesses`(`id`),
	constraint fk_document_state foreign key(`state_id`) references `document_states`(`id`),
	constraint fk_document_type foreign key (`type_id`) references `document_types`(`id`)
);

create table if not exists `custom_document_fields` (
	-- specifies custom fields a document might have
	`id` INTEGER PRIMARY KEY,
	`document_id` integer not null,
	`document_attribute_id` integer not null,
	`value` text not null,
	constraint uq_document_attributes unique (`document_id`, `document_attribute_id`),
	constraint fk_field_document foreign key (`document_id`) references `documents`(`id`),
	constraint fk_field_attribute foreign key (`document_attribute_id`) references `document_attributes`(`id`)
);

create table if not exists `permissions` (
    -- specifies permissions over various operations
    `id` INTEGER PRIMARY KEY,
    `name` text not null,
    `description` text,
    constraint uq_permission_name unique(`name`)
);

create table if not exists `business_permissions` (
    -- assignment of permissions to businesses
    `id` INTEGER PRIMARY KEY,
    `business_id` integer not null,
    `permission_id` integer not null,
    constraint uq_business_permission unique(`business_id`, `permission_id`),
    constraint fk_business_permission_business foreign key (`business_id`) references `businesses`(`id`) on delete cascade on update cascade,
    constraint fk_business_permission_permission foreign key (`permission_id`) references `permissions`(`id`)
);

create table if not exists `currencies` (
    -- contains the currencies known by the application
    `id` INTEGER PRIMARY KEY,
    `code` text not null,
    `name` text,
    `must_exchange` bit not null constraint df_must_exchange default(0),
    constraint uq_currency_code unique(`code`)
);

create trigger if not exists `trig_currency_before_insert`
before insert on `currencies`
when ((NEW.`must_exchange` = 0) and (exists (select * from `currencies` where `must_exchange`=0)))
begin
    select RAISE(ROLLBACK, 'currency_must_exchange');
end;

create trigger if not exists `trig_currency_before_update`
before update of `must_exchange` on `currencies`
when ((NEW.`must_exchange` = 0) and (OLD.`must_exchange`=1) and (exists (select * from `currencies` where `must_exchange`=0)))
begin
    select RAISE(ROLLBACK, 'currency_must_exchange');
end;

create table if not exists `exchange_rates` (
    `id` INTEGER PRIMARY KEY,
    `currency_id` integer not null,
    `bank_id` integer not null,
    `permanent` bit not null default(0),
    `start_date` datetime default(date('now')),
    `end_date` datetime,
    `value` decimal(11,4) not null,
    `exchange_tax_percent` decimal(8,2) not null default(0),
    constraint uq_currency_bank_interval unique(`currency_id`, `bank_id`, `start_date`, `end_date`),
    constraint fk_exchange_rate_currency foreign key (`currency_id`) references `currencies`(`id`),
    constraint fk_exchange_rate_bank foreign key (`bank_id`) references `banks`(`id`),
    constraint ck_exchange_rate_temp check (case permanent when 0 then ((permanent=0) and (start_date is not null) and ((end_date is null) or (end_date is not null) and (end_date >= start_date))) else permanent=1 end)
);

create trigger if not exists `trig_exchange_rates_before_insert`
    before insert on `exchange_rates`
begin
    select RAISE(ROLLBACK, 'exchange_rate_invalid_bank')
    where exists (select * from `banks` where `id`=NEW.`bank_id` and `allow_currency_exchange`=0);

    select RAISE(ROLLBACK, 'exchange_rate_invalid_currency')
    where exists (select * from `currencies` where `id`=NEW.`currency_id` and `must_exchange`=0);

    select RAISE(ROLLBACK, 'exchange_rate_permanent_rate_exists')
    where exists (select * from `exchange_rates` where bank_id=NEW.bank_id and currency_id=NEW.currency_id and permanent=1);

    select RAISE(ROLLBACK, 'exchange_rate_temp_rate_exists')
    where exists (select * from `exchange_rates` where NEW.permanent=1 and bank_id=NEW.bank_id and currency_id=NEW.currency_id and permanent=0);

    select RAISE(ROLLBACK, 'exchange_rate_overlapping_rates')
    where exists (select *
        from `exchange_rates`
        where bank_id=NEW.bank_id
                and currency_id=NEW.currency_id
                and ((NEW.end_date between start_date and end_date) or (NEW.start_date between start_date and end_date)));
end;

create trigger if not exists `trig_exchange_rates_before_update`
    before update on `exchange_rates`
begin
    select RAISE(ROLLBACK, 'exchange_rate_invalid_bank')
    where exists (select * from `banks` where `id`=NEW.`bank_id` and `allow_currency_exchange`=0);

    select RAISE(ROLLBACK, 'exchange_rate_invalid_currency')
    where exists (select * from `currencies` where `id`=NEW.`currency_id` and `must_exchange`=0);

    select RAISE(ROLLBACK, 'exchange_rate_permanent_rate_exists')
    where exists (select * from `exchange_rates` where NEW.permanent=1 and bank_id=NEW.bank_id and currency_id=NEW.currency_id and permanent=1);

    select RAISE(ROLLBACK, 'exchange_rate_temp_rate_exists')
    where exists (select * from `exchange_rates` where NEW.permanent=1 and bank_id=NEW.bank_id and currency_id=NEW.currency_id and permanent=0);

    select RAISE(ROLLBACK, 'exchange_rate_overlapping_rates')
    where exists (select *
        from `exchange_rates`
        where bank_id=NEW.bank_id
                and currency_id=NEW.currency_id
                and id <> NEW.id
                and ((NEW.end_date<>OLD.end_date and NEW.end_date between start_date and end_date) or (NEW.start_date <> OLD.start_date and NEW.start_date between start_date and end_date)));
end;

create table if not exists `units` (
    `id` INTEGER PRIMARY KEY,
    `code` text not null,
    `name` text,
    constraint uq_code unique (`code`)
);

create table if not exists `vat_types` (
    `id` INTEGER PRIMARY KEY,
    `code` text not null,
    `name` text,
    `percent` decimal(3,2) not null,
    `is_default` bit not null default(0),
    constraint uq_code unique(`code`)
);

create table if not exists `tax_types` (
    `id` INTEGER PRIMARY KEY,
    `code` text not null,
    `name` text,
    `is_percent` bit not null default(1),
    `value` decimal(9,2) not null,
    constraint uq_code unique(`code`)
);

create table if not exists `excise_types` (
    `id` INTEGER PRIMARY KEY,
    `code` text not null,
    `name` text,
    `is_percent` bit not null default(1),
    `value` decimal(9,2) not null,
    constraint uq_code unique(`code`)
);

create table if not exists `series_ranges` (
    `id` INTEGER PRIMARY KEY,
    `prefix` text not null,
    `min_value` integer not null default(0),
    `max_value` integer not null default(100),
    `counter` integer not null default(0),
    `inc_step` integer not null default(1), -- 0 means random unoccupied value between min and max
    `suffix` text not null default '',
    constraint ck_min_max check (`max_value`>`min_value`),
    constraint ck_counter check (`max_value`>=`counter` and `counter`>=`min_value`)
);

create table if not exists `goods` (
    `id` INTEGER PRIMARY KEY,
    `code` text not null,
    `name` text not null,
    `description` text, -- some sales description, what it is, what it does
    `vat_type_id` integer not null, -- which type of vat applies to this good
    constraint uq_goods_code unique(`code`),
    constraint fk_goods_vat_type foreign key(`vat_type_id`) references `vat_types`(`id`)
);

create table if not exists `good_excises` (
    `id` INTEGER PRIMARY KEY,
    `good_id` integer not null,
    `excise_type_id` integer not null,
    constraint uq_good_excise unique(`good_id`, `excise_type_id`),
    constraint fk_good_excises_goods foreign key (`good_id`) references `goods`(`id`) on delete restrict on update cascade,
    constraint fk_good_excises_excise_types foreign key (`excise_type_id`) references `excise_types`(`id`) on delete restrict on update cascade
);

create table if not exists `good_taxes` (
    `id` INTEGER PRIMARY KEY,
    `good_id` integer not null,
    `tax_type_id` integer not null,
    constraint uq_good_tax unique(`good_id`, `tax_type_id`),
    constraint fk_good_taxes_goods foreign key (`good_id`) references `goods`(`id`) on delete restrict on update cascade,
    constraint fk_good_taxes_tax_types foreign key (`tax_type_id`) references `tax_types`(`id`) on delete restrict on update cascade
);

create table if not exists `invoices` (
    `id` INTEGER PRIMARY KEY,
    `document_id` integer not null, -- document entry holding basic invoice info
    `currency_id` integer not null, -- invoices are issued with a currency
    `issuer_rep_id` integer, -- the rep who signed for the issuer
    `recipient_rep_id` integer, -- the rep who signed for the recipient
    `exchange_rate_id` integer, -- the exchange rate used, if any
    `total_amount` decimal(15, 4) not null, -- amount due (without vat, excise or taxes)
    `total_excise` decimal(15, 4) not null, -- total excise amount
    `total_taxes` decimal(15, 4) not null, -- total amount of other taxes
    `total_vat` decimal(15, 4) not null, -- total vat = sum((amount+excise+tax)*vat_percent)
    `total` decimal(15, 4) not null, -- total = total_vat + total_amount
    constraint uq_document_id unique(`document_id`), -- only one document/invoice allowed
    constraint fk_invoice_document foreign key(`document_id`) references `documents`(`id`),
    constraint fk_invoice_currency foreign key (`currency_id`) references `currencies`(`id`),
    constraint fk_invoice_issuer_rep foreign key (`issuer_rep_id`) references `business_representatives`(`id`),
    constraint fk_invoice_recipient_rep foreign key (`recipient_rep_id`) references `business_representatives`(`id`),
    constraint fk_invoice_exchange_rate foreign key (`exchange_rate_id`) references `exchange_rates`(`id`)
);

create table if not exists `invoice_details` (
    `id` INTEGER PRIMARY KEY,
    `invoice_id` integer not null, -- invoice
    `position` integer not null, -- position within invoice
    `good_id` integer, -- good we're invoicing - contains detail information
    `detail_text` text, -- alternate detail text
    `unit_id` integer not null, -- measurement unit information
    `quantity` decimal(9,2) not null, -- quantity of units
    `unit_price` decimal(11,4) not null, -- base price
    `tax_amount` decimal(9,2) not null, -- select sum(amount) from invoice_detail_taxes where invoice_detail_id=current_id()
    `excise_amount` decimal(9,2) not null, -- select sum(amount) from invoice_detail_excises where invoice_detail_id=current_id()
    `amount` decimal(15,4) not null, -- (tax_amount + excise_amount + unit_price) * quantity
    `vat_percent` decimal(3,2) not null, -- the selected vat type percent
    `vat_amount` decimal(15,4) not null, -- amount * vat_percent
    `price` decimal (15, 4) not null, -- amount + vat_amount
    constraint uq_position_on_invoice unique(`invoice_id`, `position`),
    constraint fk_invoice_detail_invoice foreign key (`invoice_id`) references `invoices`(`id`) on delete cascade on update cascade,
    constraint fk_invoice_detail_goods foreign key(`good_id`) references `goods`(`id`) on delete restrict on update cascade,
    constraint fk_invoice_detail_unit foreign key (`unit_id`) references `units`(`id`) on delete restrict on update restrict,
    constraint ck_invoice_detail_description check ((not good_id is null) or (not detail_text is null))
);

create trigger if not exists `trig_invoice_details_after_insert`
after insert on `invoice_details`
begin
    update `invoices` set
        total_amount = coalesce((select sum(amount) from `invoice_details` where `invoice_id` = NEW.invoice_id), 0),
        total_excise = coalesce((select sum(excise_amount) from `invoice_details` where `invoice_id` = NEW.invoice_id), 0),
        total_taxes = coalesce((select sum(tax_amount) from `invoice_details` where `invoice_id` = NEW.invoice_id), 0),
        total_vat = coalesce((select sum(vat_amount) from `invoice_details` where `invoice_id` = NEW.invoice_id), 0),
        total = coalesce((select sum(price) from `invoice_details` where `invoice_id` = NEW.invoice_id), 0)
    where `invoices`.`id` = NEW.invoice_id;
end;

create trigger if not exists `trig_invoice_details_after_update`
after update on `invoice_details`
begin
    update `invoices` set
        total_amount = coalesce((select sum(amount) from `invoice_details` where `invoice_id` = NEW.invoice_id), 0),
        total_excise = coalesce((select sum(excise_amount) from `invoice_details` where `invoice_id` = NEW.invoice_id), 0),
        total_taxes = coalesce((select sum(tax_amount) from `invoice_details` where `invoice_id` = NEW.invoice_id), 0),
        total_vat = coalesce((select sum(vat_amount) from `invoice_details` where `invoice_id` = NEW.invoice_id), 0),
        total = coalesce((select sum(price) from `invoice_details` where `invoice_id` = NEW.invoice_id), 0)
    where `invoices`.`id` = NEW.invoice_id;
end;

create trigger if not exists `trig_invoice_details_after_delete`
after delete on `invoice_details`
begin
    update `invoices` set
        total_amount = coalesce((select sum(amount) from `invoice_details` where `invoice_id` = OLD.invoice_id), 0),
        total_excise = coalesce((select sum(excise_amount) from `invoice_details` where `invoice_id` = OLD.invoice_id), 0),
        total_taxes = coalesce((select sum(tax_amount) from `invoice_details` where `invoice_id` = OLD.invoice_id), 0),
        total_vat = coalesce((select sum(vat_amount) from `invoice_details` where `invoice_id` = OLD.invoice_id), 0),
        total = coalesce((select sum(price) from `invoice_details` where `invoice_id` = OLD.invoice_id), 0)
    where `invoices`.`id` = OLD.invoice_id;
end;

-- mustn't use foreign keys with tax types because taxes may change in time
create table if not exists `invoice_detail_taxes` (
    `id` INTEGER PRIMARY KEY,
    `invoice_detail_id` integer not null,
    `tax_code` text not null, -- filled from a given tax type
    `amount` decimal(15, 4) not null, -- computed result based on the selected tax type
    constraint uq_tax_detail unique (`invoice_detail_id`, `tax_code`), -- double imposition is not allowed
    constraint fk_invoice_detail_taxes_detail foreign key (`invoice_detail_id`) references `invoice_details`(`id`) on delete cascade on update cascade
);

-- mustn't use foreign keys with excise types because excises may change
create table if not exists `invoice_detail_excises` (
    `id` INTEGER PRIMARY KEY,
    `invoice_detail_id` integer not null,
    `excise_code` text not null, -- code obtained from excise type
    `amount` decimal(15, 4) not null, -- computed result based on the selected excise type
    constraint uq_excise_detail unique(`invoice_detail_id`, `excise_code`), -- double imposition not allowed
    constraint fk_invoice_detail_excises_detail foreign key (`invoice_detail_id`) references `invoice_details`(`id`) on delete cascade on update cascade
);

create view if not exists `business_addresses`
as
select 
    b1.id as business_id,
    b1.name as name,
    l1.address || ', ' || l1.postal_code as address,
    c1.name as city,
    ct1.name as county,
    r1.name as region,
    co1.name as country,
    l1.address || ', ' || l1.postal_code || ', ' || c1.name || ', ' || ct1.name || ', ' || r1.name || ', ' || co1.name as full_address
from businesses b1
    inner join locations l1 on l1.id=b1.location_id
    inner join cities c1 on c1.id=l1.city_id
    inner join counties ct1 on ct1.id = c1.county_id
    inner join regions r1 on r1.id = ct1.region_id
    inner join countries co1 on co1.id = r1.country_id;

create view if not exists `business_info`
as
select
    `businesses`.`id` as business_id,
    `businesses`.`name` as bname,
    `locations`.`address` as baddress,
    `locations`.`postal_code` as bpostal,
    `cities`.`name` as bcity,
    `counties`.`name` as bcounty,
    `regions`.`name` as bregion,
    `countries`.`name` as bcountry,
    `banks`.`name` as bbank,
    `bank_accounts`.`account` as baccount,
    `legal_statuses`.`code` as blegal
from `businesses`
    inner join `locations` on `locations`.`id` = `businesses`.`location_id`
    inner join `cities` on `cities`.`id` = `locations`.`city_id`
    inner join `counties` on `counties`.`id` = `cities`.`id`
    inner join `regions` on `regions`.`id` = `counties`.`region_id`
    inner join `countries` on `countries`.`id` = `regions`.`country_id`
    inner join `bank_accounts` on `bank_accounts`.`id` = `businesses`.`bank_account_id`
    inner join `banks` on `banks`.`id` = `bank_accounts`.`bank_id`
    inner join `legal_statuses` on `legal_statuses`.`id` = `businesses`.`legal_status_id`;

