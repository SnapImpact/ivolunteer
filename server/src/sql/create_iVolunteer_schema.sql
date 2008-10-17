CREATE TABLE Events (
	id VARCHAR(36) NOT NULL,
	title VARCHAR(256) NOT NULL,
    description VARCHAR(1024) NOT NULL,
	timestamp TIMESTAMP NOT NULL,
    duration NUMERIC(3),
	location VARCHAR(512),
	latitude VARCHAR(128),
	longitude VARCHAR(128),
	contact VARCHAR(256),
	url VARCHAR(256),
    street VARCHAR(256),
    city VARCHAR(256),
    state VARCHAR(2),
    zip VARCHAR(16),
	phone VARCHAR(64),
	email VARCHAR(256),
	organization_id VARCHAR(36),
    source_id VARCHAR(36),
    source_key VARCHAR(128),
    source_url VARCHAR(256),
	PRIMARY KEY (id)
);

CREATE TABLE Organizations (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(256) NOT NULL,
    description VARCHAR(1024),
    phone VARCHAR(64),
    email VARCHAR(512),
    url VARCHAR(256),
    street VARCHAR(256),
    city VARCHAR(256),
    state VARCHAR(2),
    zip VARCHAR(16),
    organization_type_id VARCHAR(36),
    source_id VARCHAR(36),
    source_key VARCHAR(128),
    source_url VARCHAR(256),
    PRIMARY KEY (id)
);

ALTER TABLE events ADD CONSTRAINT events_organizations_fk FOREIGN KEY (organization_id) REFERENCES organizations(id);

CREATE TABLE Organization_Types (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(128),
    PRIMARY KEY (id)
);

ALTER TABLE organizations ADD CONSTRAINT organization_type_fk FOREIGN KEY (organization_type_id) REFERENCES organization_types(id);

CREATE TABLE Interest_Areas (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(128),
    PRIMARY KEY (id)
);

CREATE TABLE Event_Interest_Areas (
    interest_area_id VARCHAR(36) NOT NULL,
    event_id VARCHAR(36) NOT NULL
);

ALTER TABLE event_interest_areas ADD CONSTRAINT eia_interest_area_fk FOREIGN KEY (interest_area_id) REFERENCES interest_areas(id);
ALTER TABLE event_interest_areas ADD CONSTRAINT eia_events_fk FOREIGN KEY (event_id) REFERENCES events(id);

CREATE TABLE Organization_Interest_Areas (
    interest_area_id VARCHAR(36) NOT NULL,
    organization_id VARCHAR(36) NOT NULL
);

ALTER TABLE organization_interest_areas ADD CONSTRAINT oia_interest_area_fk FOREIGN KEY (interest_area_id) REFERENCES interest_areas(id);
ALTER TABLE organization_interest_areas ADD CONSTRAINT oia_events_fk FOREIGN KEY (organization_id) REFERENCES organizations(id);

CREATE TABLE Sources (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(128),
    etl_class VARCHAR(128),
    PRIMARY KEY (id)
);

ALTER TABLE events ADD CONSTRAINT events_source_fk FOREIGN KEY (source_id) REFERENCES sources(id);
ALTER TABLE organizations ADD CONSTRAINT organization_source_fk FOREIGN KEY (source_id) REFERENCES sources(id);
