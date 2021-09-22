insert into domain (domnid, domctable, domcfield, domcvalue, domnorder) VALUES (1, 'person', 'pesnlegaltype', 'Fisica', 1);
insert into domain (domnid, domctable, domcfield, domcvalue, domnorder) VALUES (2, 'person', 'pesnlegaltype', 'Juridica', 2);
insert into domain (domnid, domctable, domcfield, domcvalue, domnorder) VALUES (3, 'person', 'pesnlegaltype', 'N/A', 3);

insert into domain (domnid, domctable, domcfield, domcvalue, domnorder) VALUES (4, 'person_address', 'pesndomtype', 'Delivery', 1);
insert into domain (domnid, domctable, domcfield, domcvalue, domnorder) VALUES (5, 'person_address', 'pesndomtype', 'Billing', 2);
insert into domain (domnid, domctable, domcfield, domcvalue, domnorder) VALUES (6, 'person_address', 'pesndomtype', 'N/A', 3);

insert into person (pesnid, ts, created_at, active, pescfirstname, pesclastname, pesndomlegaltype, pesbuser) VALUES (-1, current_timestamp, current_timestamp, true, 'admin', 'admin', 3, true);
insert into user (pesnid, ts, created_at, active, usrclogin, usrcpassword) VALUES (-1, current_timestamp, current_timestamp, true, 'admin', '4b36048921195afdef459f3704ff799be6d2d8f7f029ac1f43bae7919cf80c0f');

insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (10000, current_timestamp, current_timestamp, true, 'filter user');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (10001, current_timestamp, current_timestamp, true, 'enable user');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (10002, current_timestamp, current_timestamp, true, 'disable user');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (20000, current_timestamp, current_timestamp, true, 'filter country');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (20001, current_timestamp, current_timestamp, true, 'insert country');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (20002, current_timestamp, current_timestamp, true, 'update country');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (20003, current_timestamp, current_timestamp, true, 'delete country');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (30000, current_timestamp, current_timestamp, true, 'filter address');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (30001, current_timestamp, current_timestamp, true, 'insert address');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (30002, current_timestamp, current_timestamp, true, 'update address');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (30003, current_timestamp, current_timestamp, true, 'delete address');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (30004, current_timestamp, current_timestamp, true, 'filter address by zipcode');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (40000, current_timestamp, current_timestamp, true, 'filter city');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (40001, current_timestamp, current_timestamp, true, 'insert city');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (40002, current_timestamp, current_timestamp, true, 'update city');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (40003, current_timestamp, current_timestamp, true, 'delete city');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (50000, current_timestamp, current_timestamp, true, 'filter address of person');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (50001, current_timestamp, current_timestamp, true, 'insert address of person');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (50002, current_timestamp, current_timestamp, true, 'update address of person');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (50003, current_timestamp, current_timestamp, true, 'delete address of person');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (60000, current_timestamp, current_timestamp, true, 'filter person');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (60001, current_timestamp, current_timestamp, true, 'insert person');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (60002, current_timestamp, current_timestamp, true, 'update person');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (60003, current_timestamp, current_timestamp, true, 'delete person');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (70000, current_timestamp, current_timestamp, true, 'filter state');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (70001, current_timestamp, current_timestamp, true, 'insert state');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (70002, current_timestamp, current_timestamp, true, 'update state');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (70003, current_timestamp, current_timestamp, true, 'delete state');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (80000, current_timestamp, current_timestamp, true, 'filter user resource');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (80001, current_timestamp, current_timestamp, true, 'insert user resource');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (80002, current_timestamp, current_timestamp, true, 'delete user resource');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (90000, current_timestamp, current_timestamp, true, 'filter resource');
insert into resource(rscnid, ts, created_at, active, rsccdescription) VALUES (90001, current_timestamp, current_timestamp, true, 'filter resources not contained in user');

insert into person (pesnid, ts, created_at, active, pescfirstname, pesclastname, pesndomlegaltype, pesbuser) VALUES (-2, current_timestamp, current_timestamp, true, 'admin', 'admin', 3, true);
insert into user (pesnid, ts, created_at, active, usrclogin, usrcpassword) VALUES (-2, current_timestamp, current_timestamp, true, 'admin1', '4b36048921195afdef459f3704ff799be6d2d8f7f029ac1f43bae7919cf80c0f');
insert into user_resource(ucenid, ts, created_at, active, ucenresource, ucenperson) VALUES (1, current_timestamp, current_timestamp, true, 20001, -2);
insert into user_resource(ucenid, ts, created_at, active, ucenresource, ucenperson) VALUES (2, current_timestamp, current_timestamp, true, 90000, -2);
insert into user_resource(ucenid, ts, created_at, active, ucenresource, ucenperson) VALUES (3, current_timestamp, current_timestamp, true, 90001, -2);

insert into country (counid, ts, created_at, active, coucname, coucisocode) VALUES (-1, current_timestamp, current_timestamp, true, 'Teste Pais', 'TT');
insert into state (stanid, ts, created_at, active, stacname, stancountry) VALUES (-1, current_timestamp, current_timestamp, true, 'Teste Sao Paulo', -1);
insert into city (citnid, ts, created_at, active, citcname, citnstate) VALUES (-1, current_timestamp, current_timestamp, true, 'Teste Santos', -1); 
insert into address (addnid, ts, created_at, active, addcline1, addcline2, addccity, addczip, addcneighborhood) VALUES (-1, current_timestamp, current_timestamp, true, 'Rua sai', '', -1, '11070020', 'Marapé'); 

 
