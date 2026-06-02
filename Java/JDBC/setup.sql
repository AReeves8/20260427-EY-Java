-- ── Tables ───────────────────────────────────────────────────────────────

CREATE TABLE IF NOT EXISTS customer (
    customer_id  INT          NOT NULL GENERATED ALWAYS AS IDENTITY,
    first_name   VARCHAR(40)  NOT NULL,
    last_name    VARCHAR(40)  NOT NULL,
    email        VARCHAR(60)  NOT NULL,
    city         VARCHAR(40),
    country      VARCHAR(40),
    CONSTRAINT customer_pkey PRIMARY KEY (customer_id)
);

CREATE TABLE IF NOT EXISTS invoice (
    invoice_id   INT            NOT NULL GENERATED ALWAYS AS IDENTITY,
    customer_id  INT            NOT NULL,
    total        NUMERIC(10, 2) NOT NULL,
    invoice_date DATE           NOT NULL DEFAULT CURRENT_DATE,
    CONSTRAINT invoice_pkey       PRIMARY KEY (invoice_id),
    CONSTRAINT invoice_customer_fk FOREIGN KEY (customer_id)
        REFERENCES customer (customer_id)
);


-- ── Mock customer data ────────────────────────────────────────────────────

INSERT INTO customer (first_name, last_name, email, city, country) VALUES

-- USA
('Marcus',   'Webb',      'marcus.webb@example.com',      'Atlanta',       'USA'),
('Priya',    'Nair',      'priya.nair@example.com',       'Miami',         'USA'),
('Derek',    'Huang',     'derek.huang@example.com',      'Denver',        'USA'),
('Samantha', 'Torres',    'samantha.torres@example.com',  'New York',      'USA'),
('Ethan',    'Johansson', 'ethan.johansson@example.com',  'Chicago',       'USA'),
('Nadia',    'Okafor',    'nadia.okafor@example.com',     'Houston',       'USA'),
('Blake',    'Fischer',   'blake.fischer@example.com',    'San Francisco', 'USA'),
('Lauren',   'Castillo',  'lauren.castillo@example.com',  'Phoenix',       'USA'),
('Jerome',   'Patel',     'jerome.patel@example.com',     'Seattle',       'USA'),
('Chloe',    'Brennan',   'chloe.brennan@example.com',    'Nashville',     'USA'),

-- Canada
('Ryan',     'Leblanc',   'ryan.leblanc@example.ca',      'Montreal',      'Canada'),
('Aisha',    'Osei',      'aisha.osei@example.ca',        'Toronto',       'Canada'),
('Tobias',   'Nguyen',    'tobias.nguyen@example.ca',     'Vancouver',     'Canada'),
('Grace',    'Munroe',    'grace.munroe@example.ca',      'Edmonton',      'Canada'),

-- United Kingdom
('Oliver',   'Whitfield', 'oliver.whitfield@example.co.uk', 'London',      'United Kingdom'),
('Fiona',    'Drummond',  'fiona.drummond@example.co.uk',   'Glasgow',     'United Kingdom'),
('James',    'Holloway',  'james.holloway@example.co.uk',   'Bristol',     'United Kingdom'),

-- Germany
('Lena',     'Becker',    'lena.becker@example.de',       'Berlin',        'Germany'),
('Markus',   'Schreiber', 'markus.schreiber@example.de',  'Munich',        'Germany'),

-- France
('Camille',  'Renard',    'camille.renard@example.fr',    'Paris',         'France'),
('Antoine',  'Moreau',    'antoine.moreau@example.fr',    'Lyon',          'France'),

-- Brazil
('Isabela',  'Ferreira',  'isabela.ferreira@example.com', 'Sao Paulo',     'Brazil'),
('Rafael',   'Azevedo',   'rafael.azevedo@example.com',   'Rio de Janeiro','Brazil'),

-- Australia
('Sophie',   'Mackay',    'sophie.mackay@example.com.au', 'Sydney',        'Australia'),

-- Japan
('Kenji',    'Yamamoto',  'kenji.yamamoto@example.jp',    'Tokyo',         'Japan');


-- ── Verify ────────────────────────────────────────────────────────────────

SELECT customer_id, first_name || ' ' || last_name AS name, city, country
FROM customer
ORDER BY country, last_name;
