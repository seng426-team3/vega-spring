-- First User admin/pass
-- database root/pass
INSERT INTO users (username, password, enabled) values
    ('admin@venus.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a',1),
    ('paulaguilar@venus.com', '$2a$10$57HgFrJ3eD2N0L01cPltIeUxl0gC/yRzEHP0AySGLp8HE8GX6NuCe',1),
    ('jonoliver@venus.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a',1),
    ('claudinezhang@venus.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a',1),
    ('lovelinkumar@venus.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a',1),
    ('michelkouame@venus.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a',0),
    ('angelinacosta@venus.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a',0),
    ('brijeshgupta@venus.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a',1),
    ('amyfofana@venus.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 1),
    ('testuser@venus.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 0),
    ('testuser2@venus.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 1);



INSERT INTO authorities (username, authority) values
    ('admin@venus.com', 'ROLE_ADMIN'),
    ('paulaguilar@venus.com', 'ROLE_STAFF'),
    ('jonoliver@venus.com', 'ROLE_STAFF'),
    ('claudinezhang@venus.com', 'ROLE_STAFF'),
    ('lovelinkumar@venus.com', 'ROLE_STAFF'),
    ('michelkouame@venus.com', 'ROLE_STAFF'),
    ('angelinacosta@venus.com', 'ROLE_STAFF'),
    ('brijeshgupta@venus.com', 'ROLE_STAFF'),
    ('amyfofana@venus.com', 'ROLE_STAFF'),
    ('testuser@venus.com', 'ROLE_USER'),
    ('testuser2@venus.com', 'ROLE_USER');

INSERT INTO userinfo (username, firstname, lastname) values
    ('admin@venus.com', 'admin' , 'admin'),
    ('paulaguilar@venus.com', 'Paul', 'Aguilar'),
    ('jonoliver@venus.com', 'Jon', 'Oliver'),
    ('claudinezhang@venus.com', 'Claudine', 'Zhang'),
    ('lovelinkumar@venus.com', 'Lovelin', 'Kumar'),
    ('michelkouame@venus.com', 'Michel', 'Kouame'),
    ('angelinacosta@venus.com', 'Angelina', 'Costa'),
    ('brijeshgupta@venus.com', 'Brijesh', 'Gupta'),
    ('amyfofana@venus.com', 'Amy', 'Fofana'),
    ('testuser@venus.com', 'testuser', 'testuser'),
    ('testuser2@venus.com', 'testuser2', 'testuser2');

-- Insert initial secrets
INSERT INTO secrets (secretid, username, secretname, filetype, creationdate, secretdata) values
    ('bfc32014-2096-48ea-9a98-8335e6215438', 'testuser2@venus.com', 'Super Secret', '.txt', '2022-07-04', 0x54686973206973206120746573740A);

-- Insert initial news & events 
INSERT INTO news (newsid, title, bodytext, newsdate, timepublished, author) values
    (1, 'This is a news article!', ' Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras aliquam dolor nisl, at ullamcorper magna viverra nec. Praesent varius mi pellentesque laoreet convallis. Sed et tincidunt est. Mauris fermentum tincidunt quam at fringilla. Praesent mauris ante, viverra ac auctor ut, tincidunt eget erat. Proin odio quam, consequat vitae egestas nec, molestie eget ex. Duis sollicitudin imperdiet orci, quis pellentesque mi ultricies quis. Etiam et enim semper nunc fermentum consectetur ac at urna. Maecenas id diam a nisi imperdiet egestas. Nullam bibendum purus vitae libero facilisis elementum. Curabitur a dolor posuere, luctus ipsum iaculis, malesuada lectus. Praesent iaculis ipsum nec laoreet suscipit.\nSed ullamcorper tortor libero, aliquam condimentum arcu sodales lobortis. Nam lobortis interdum massa, eget euismod metus tempus a. Suspendisse aliquam ultricies nunc, vitae placerat ante pretium ac. Aliquam erat volutpat. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Pellentesque eu dignissim lorem. Duis pellentesque arcu metus, quis congue tellus scelerisque eget. Ut sit amet velit porta, sodales enim et, fringilla libero. Vestibulum nec euismod ipsum. Donec at metus laoreet, laoreet urna sit amet, rhoncus neque. Nunc tempus nunc quis tortor hendrerit, sit amet congue purus fermentum. In lacinia aliquam felis, sed ornare lectus consectetur a. Quisque porta turpis justo. ', '29.5.2022', 1656310883, 'Anton Nikitenko');

-- Insert initial Contact Us
INSERT INTO contactus (user_name, email, message) values
    ('John Oliver', 'johnoliver@venus.com', 'Hello everyone, how are you all doing!');
