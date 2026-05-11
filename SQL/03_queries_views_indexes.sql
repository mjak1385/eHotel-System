-- -------
-- INDEXES
-- -------
-- Index 1: To speed up searching for hotels by area
CREATE INDEX idx_hotel_address ON Hotel(address);

-- Index 2: To speed up filtering rooms by price
CREATE INDEX idx_room_price ON Room(price);

-- Index 3: To speed up room availability checks based on dates
CREATE INDEX idx_booking_dates ON Booking(start_date, end_date);


-- -------
-- QUERIES
-- -------
-- Query 1: Aggregation with GROUP BY
SELECT 
    h.stars AS hotel_category_stars,
    COUNT(r.room_id) AS total_rooms,
    ROUND(AVG(r.price), 2) AS average_room_price
FROM Hotel h
JOIN Room r ON h.hotel_id = r.hotel_id
GROUP BY h.stars
ORDER BY h.stars DESC;

-- Query 2: The Nested Query (Subquery)
SELECT 
    full_name, 
    address
FROM Customer
WHERE customer_id IN (
    SELECT b.customer_id
    FROM Booking b
    JOIN Room r ON b.room_id = r.room_id
    WHERE r.price > 200.00
);

-- Query 3: Multi-Table JOIN for Operations
SELECT 
    c.full_name AS customer_name,
    h.address AS hotel_address,
    r.room_id,
    r.view_type,
    e.full_name AS handled_by_employee,
    rent.start_date,
    rent.end_date
FROM Renting rent
JOIN Customer c ON rent.customer_id = c.customer_id
JOIN Room r ON rent.room_id = r.room_id
JOIN Hotel h ON r.hotel_id = h.hotel_id
JOIN Employee e ON rent.employee_id = e.employee_id;

-- Query 4: Aggregation with HAVING Clause
SELECT 
    hc.chain_id,
    hc.office_address,
    SUM(h.num_rooms) AS total_rooms_in_chain
FROM HotelChain hc
JOIN Hotel h ON hc.chain_id = h.chain_id
GROUP BY hc.chain_id, hc.office_address
HAVING SUM(h.num_rooms) > 30;


-- -----
-- VIEWS
-- -----
-- View 1: Number of available rooms per area (assuming availability means 'today')
CREATE OR REPLACE VIEW Available_Rooms_Per_Area AS
SELECT 
    h.address AS area,
    COUNT(r.room_id) AS available_rooms
FROM Hotel h
JOIN Room r ON h.hotel_id = r.hotel_id
WHERE r.room_id NOT IN (
    SELECT room_id FROM Booking WHERE CURRENT_DATE >= start_date AND CURRENT_DATE < end_date
    UNION
    SELECT room_id FROM Renting WHERE CURRENT_DATE >= start_date AND CURRENT_DATE < end_date
)
GROUP BY h.address;

-- View 2: Aggregated capacity of all the rooms of a specific hotel
CREATE OR REPLACE VIEW Hotel_Aggregated_Capacity AS
SELECT 
    h.hotel_id,
    h.address AS hotel_address,
    SUM(r.capacity) AS total_capacity
FROM Hotel h
JOIN Room r ON h.hotel_id = r.hotel_id
GROUP BY h.hotel_id, h.address;