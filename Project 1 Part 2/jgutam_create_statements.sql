create table customers(customer_id int primary key,
age int,
gender VARCHAR2(1),
zip_code VARCHAR2(10),
first_name varchar2(30),
last_name varchar2(30),
CONSTRAINT gender_check CHECK (gender IN ('M', 'F')));

commit;

create table Transactions(transaction_id int primary key,
transaction_date DATE,
total int,
payment_method VARCHAR2(255),
foreign key (customer_id) references customers(customer_id)
CONSTRAINT payment_method_check CHECK (payment_method IN ('1', '2', '3')));

commit;

create table Products(upc varchar2(30) primary key,
brand VARCHAR2(30),
product_name VARCHAR2(30),
product_description varchar2(30),
category varchar2(30),
marked_price number,
quantity_in_stock number
);

commit;

create table contains(
transaction_id int,
upc varchar2(30) not NULL,
quantity int,
primary key (transaction_id, upc),
foreign key (transaction_id) references transactions(transaction_id),
foreign key (upc) references products(upc)
);

commit;