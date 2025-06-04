CREATE TABLE users
(
    user_id         SERIAL PRIMARY KEY,
    full_name       VARCHAR(50)           NOT NULL,
    password        VARCHAR(100)          NOT NULL,
    email           VARCHAR(100)          NOT NULL UNIQUE,
    profile_picture VARCHAR(255),
    is_admin        BOOLEAN DEFAULT FALSE NOT NULL
);

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
    movie_id   INT NOT NULL,
    cast_id    INT NOT NULL,
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
    total_price NUMERIC(10, 2) NOT NULL,
    show_id     INT            NOT NULL,
    user_id     INT            NOT NULL,
    CONSTRAINT show_fk FOREIGN KEY (show_id) REFERENCES shows (show_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE booking_seat
(
    booking_seat_id SERIAL PRIMARY KEY,
    booking_id      INT NOT NULL,
    seat_id         INT NOT NULL,
    CONSTRAINT bookings_fk FOREIGN KEY (booking_id) REFERENCES bookings (booking_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT seat_fk FOREIGN KEY (seat_id) REFERENCES seats (seat_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE user_favorite_movies
(
    user_id  INT NOT NULL,
    movie_id INT NOT NULL,
    PRIMARY KEY (user_id, movie_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_movie FOREIGN KEY (movie_id) REFERENCES movies (movie_id) ON DELETE CASCADE ON UPDATE CASCADE
);