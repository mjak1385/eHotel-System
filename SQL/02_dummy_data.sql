-- 1. Insert 5 Hotel Chains
INSERT INTO HotelChain (office_address, hotel_count) VALUES
('100 Marriott Dr, Bethesda, MD', 8),
('7930 Jones Branch Dr, McLean, VA', 8),
('150 N Riverside Plaza, Chicago, IL', 8),
('Three Ravinia Drive, Atlanta, GA', 8),
('22 Sylvan Way, Parsippany, NJ', 8);

INSERT INTO HotelChain_Email (chain_id, email) 
SELECT chain_id, 'contact@chain' || chain_id || '.com' FROM HotelChain;

INSERT INTO HotelChain_Phone (chain_id, phone) 
SELECT chain_id, '555-010' || chain_id FROM HotelChain;

-- 2. Insert 8 Hotels per chain (Total 40 Hotels)
INSERT INTO Hotel (chain_id, stars, num_rooms, address)
SELECT 
    c.chain_id,
    (s.num % 3) + 3 AS stars,
    5 AS num_rooms,
    CASE 
        WHEN s.num = 1 THEN '100 Sussex Dr, Downtown Ottawa'
        WHEN s.num = 2 THEN '200 Rideau St, Downtown Ottawa'
        ELSE 'Address ' || s.num || ' for Chain ' || c.chain_id 
    END AS address
FROM HotelChain c
CROSS JOIN generate_series(1, 8) AS s(num);

INSERT INTO Hotel_Email (hotel_id, email) 
SELECT hotel_id, 'info@hotel' || hotel_id || '.com' FROM Hotel;

INSERT INTO Hotel_Phone (hotel_id, phone) 
SELECT hotel_id, '555-02' || LPAD(hotel_id::text, 2, '0') FROM Hotel;

-- 3. Insert 5 Rooms per hotel (Total 200 rooms) 
INSERT INTO Room (hotel_id, price, capacity, view_type, amenities, is_extendable, problems_status)
SELECT 
    h.hotel_id,
    (r.cap * 50.00) + 100.00 AS price, 
    r.cap AS capacity, 
    CASE WHEN r.cap % 2 = 0 THEN 'Sea View' ELSE 'Mountain View' END AS view_type,
    'TV, AC, Mini-fridge, Wi-Fi',
    CASE WHEN r.cap > 2 THEN true ELSE false END AS is_extendable,
    'None'
FROM Hotel h
CROSS JOIN generate_series(1, 5) AS r(cap);

-- 4. Users (Customers and Employees)
INSERT INTO Customer (full_name, address, id_type, id_number, registration_date) VALUES
('MZ Player', '800 King Edward Ave', 'Driver License', 'ON-123456', '2025-09-30'),
('Alice Smith', '123 Apple St', 'Passport', 'P123456', '2025-01-15'),
('Bob Johnson', '456 Banana Blvd', 'SSN', '123-456-789', '2026-02-10');

-- Insert 1 Manager per hotel
INSERT INTO Employee (hotel_id, full_name, address, ssn_sin, role)
SELECT hotel_id, 'Manager ' || hotel_id, 'City ' || hotel_id, 'MGR-' || hotel_id, 'Manager' FROM Hotel;
    
-- Insert 1 Receptionist per hotel (Reporting to their respective hotel managers)
INSERT INTO Employee (hotel_id, manager_id, full_name, address, ssn_sin, role)
SELECT e.hotel_id, e.employee_id, 'Receptionist ' || e.hotel_id, 'City ' || e.hotel_id, 'REC-' || e.hotel_id, 'Receptionist' 
FROM Employee e WHERE e.role = 'Manager';

-- 5. Bookings and Rentings
INSERT INTO Booking (customer_id, room_id, start_date, end_date) VALUES
(1, 1, '2026-05-01', '2026-05-05'),
(2, 6, '2026-05-10', '2026-05-15');

-- Transform Booking 1 into a Renting upon check-in
INSERT INTO Renting (booking_id, customer_id, room_id, employee_id, start_date, end_date) VALUES
(1, 1, 1, 41, '2026-05-01', '2026-05-05'); 

INSERT INTO Renting (booking_id, customer_id, room_id, employee_id, start_date, end_date) VALUES
(NULL, 3, 2, 41, '2026-05-06', '2026-05-08');