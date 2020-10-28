insert into tags (name) values ('spa'), ('rest'), ('winter'), ('food');

insert into certificates (name, description, price, create_date, duration) values
('Spa', 'Gift certificate', 15.00, '2020-01-01', 3), ('Skating', 'Gift certificate', 20.00, '2020-02-02', 5), ('Pizza-bar', 'Gift certificate', 12.99, '2020-03-03', 10);

insert into certificate_tag values(1, 1), (1, 2), (2, 2), (2, 3), (3, 4);