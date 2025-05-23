CREATE TABLE categories
(
    category_id SERIAL PRIMARY KEY,
    name        TEXT UNIQUE NOT NULL
);

CREATE TABLE movies
(
    movie_id      SERIAL PRIMARY KEY,
    title         TEXT    NOT NULL,
    year          INT     NOT NULL,
    duration      INT     NOT NULL,
    rating        DECIMAL NOT NULL,
    overview      TEXT    NOT NULL,
    director_name TEXT    NOT NULL,
    is_favorite   BOOLEAN DEFAULT FALSE,
    poster        TEXT    NOT NULL,
    thriller      TEXT    NOT NULL,
    category_id   INT     NOT NULL,
    CONSTRAINT category_fk FOREIGN KEY (category_id) REFERENCES categories (category_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE cast_members
(
    cast_id SERIAL PRIMARY KEY,
    name    TEXT NOT NULL
);

CREATE TABLE Casting
(
    casting_id SERIAL PRIMARY KEY,
    movie_id INT NOT NULL,
    cast_id  INT NOT NULL,
    CONSTRAINT movie_fk FOREIGN KEY (movie_id) REFERENCES movies (movie_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT cast_members_fk FOREIGN KEY (cast_id) REFERENCES cast_members (cast_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE seats
(
    seat_id SERIAL PRIMARY KEY,
    row     TEXT NOT NULL,
    number  INT  NOT NULL
);

CREATE TABLE shows
(
    show_id          SERIAL PRIMARY KEY,
    show_date        DATE NOT NULL,
    show_time        TEXT NOT NULL,
    number_of_ticket INT  NOT NULL,
    movie_id         INT  NOT NULL,
    CONSTRAINT movie_fk FOREIGN KEY (movie_id) REFERENCES movies (movie_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE bookings
(
    booking_id  SERIAL PRIMARY KEY,
    full_name   TEXT           NOT NULL,
    email       TEXT           NOT NULL,
    total_price NUMERIC(10, 2) NOT NULL,
    show_id     INT            NOT NULL,
    CONSTRAINT show_fk FOREIGN KEY (show_id) REFERENCES shows (show_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE booking_seat
(
    booking_seat_id SERIAL PRIMARY KEY,
    booking_id      INT NOT NULL,
    seat_id         INT NOT NULL,
    CONSTRAINT bookings_fk FOREIGN KEY (booking_id) REFERENCES bookings (booking_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT seat_fk FOREIGN KEY (seat_id) REFERENCES seats (seat_id) ON DELETE CASCADE ON UPDATE CASCADE

);
