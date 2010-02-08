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
	`address` text not null default(''),
	`postal_code` text,
	`city_id` integer not null,
	constraint fk_contacts_city foreign key(`city_id`) references `cities`(`id`) on delete cascade on update cascade
);

create table if not exists `contact_locations` (
	-- a contact may have several locations where it can be reached
	`id` INTEGER PRIMARY KEY,
	`contact_id` integer not null,
	`location_id` integer not null,
	`subdivision` text, -- contacts may reside at the same or at different subdivisions per location
	`priority` integer not null default(1), -- this is the default location for contacting this contact
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
	`county_id` integer not null,
	`location_id` integer not null,
	`bank_account_id` integer not null,
	`legal_status_id` integer not null,
	constraint uq_businesscounty unique (`name`, `county_id`),
	constraint fk_businsesses_county foreign key (`county_id`) references `counties`(`id`),
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
	`id` INTEGER NOT NULL,
	`name` text not null,
	`description` text not null,
	constraint uq_document_state_name unique(`name`)
);

create table if not exists `documents` (
	-- documents
	`id` INTEGER PRIMARY KEY,
	`number` text not null,
	`type_id` integer not null,
	`issuer_id` integer not null,
	`recipient_id` integer not null,
	`state_id` int not null,
	`date_issued` datetime not null,
	`date_finished` datetime not null,
	`date_due` datetime,
	`serial_number` text,
	constraint uq_document_number unique(`number`),
	constraint uq_document_serial unique(`serial_number`),
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

