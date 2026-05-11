-- 1. Create the Payment Table
CREATE TABLE Payment (
    payment_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    renting_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(50) DEFAULT 'Credit Card',
    payment_date DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (renting_id) REFERENCES Renting(renting_id) ON DELETE CASCADE
);

-- 2. Prevent Overlapping Bookings
CREATE OR REPLACE FUNCTION check_booking_overlap()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM Booking
        WHERE room_id = NEW.room_id
          AND booking_id != COALESCE(NEW.booking_id, -1) 
          AND (NEW.start_date < end_date AND NEW.end_date > start_date) 
    ) THEN
        RAISE EXCEPTION 'Constraint Violation: Room % is already booked during these dates.', NEW.room_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER prevent_booking_overlap
BEFORE INSERT OR UPDATE ON Booking
FOR EACH ROW EXECUTE FUNCTION check_booking_overlap();

-- 3. Enforce Employee-Manager Hierarchy Rules
CREATE OR REPLACE FUNCTION check_manager_hotel()
RETURNS TRIGGER AS $$
DECLARE
    mgr_hotel_id INT;
BEGIN
    IF NEW.manager_id IS NOT NULL THEN
        SELECT hotel_id INTO mgr_hotel_id 
        FROM Employee 
        WHERE employee_id = NEW.manager_id;
        
        IF mgr_hotel_id != NEW.hotel_id THEN
            RAISE EXCEPTION 'Constraint Violation: An employee and their manager must belong to the same hotel.';
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER enforce_manager_hotel
BEFORE INSERT OR UPDATE ON Employee
FOR EACH ROW EXECUTE FUNCTION check_manager_hotel();

-- 4. Prevent Overlapping Rentings 
CREATE OR REPLACE FUNCTION check_renting_overlap()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM Renting
        WHERE room_id = NEW.room_id
          AND renting_id != COALESCE(NEW.renting_id, -1)
          AND (NEW.start_date < end_date AND NEW.end_date > start_date)
    ) THEN
        RAISE EXCEPTION 'Constraint Violation: Room % is currently being rented during these dates.', NEW.room_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER prevent_renting_overlap
BEFORE INSERT OR UPDATE ON Renting
FOR EACH ROW EXECUTE FUNCTION check_renting_overlap();