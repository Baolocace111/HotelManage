-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 18, 2024 lúc 11:15 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `smarthotel1`
--

DELIMITER $$
--
-- Thủ tục
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `GenerateRooms` ()   BEGIN
  DECLARE i INT DEFAULT 1;
  DECLARE j INT DEFAULT 1;
  DECLARE room_types VARCHAR(100);
  
  WHILE i <= 4 DO
    WHILE j <= 10 DO
      SET room_types = CASE FLOOR(RAND() * 5)
        WHEN 0 THEN 'Single'
        WHEN 1 THEN 'Double'
        WHEN 2 THEN 'Luxury'
        WHEN 3 THEN 'Delux'
        WHEN 4 THEN 'Family'
        ELSE 'Single' END;
      
      INSERT INTO room (room_number, room_type, floor, status)
      VALUES (j, room_types, i, 'Available');
      
      SET j = j + 1;
    END WHILE;
    SET j = 1;
    SET i = i + 1;
  END WHILE;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `checkout`
--

CREATE TABLE `checkout` (
  `id` int(11) NOT NULL,
  `cus_name` varchar(100) NOT NULL,
  `cus_father` varchar(100) NOT NULL,
  `address` varchar(255) NOT NULL,
  `cus_nic` bigint(20) NOT NULL,
  `cus_date` date NOT NULL,
  `out_date` date NOT NULL,
  `phone` bigint(20) NOT NULL,
  `cus_country` varchar(50) NOT NULL,
  `cus_city` varchar(50) NOT NULL,
  `cus_adult` int(11) NOT NULL,
  `cus_child` int(11) NOT NULL,
  `room_type` varchar(100) NOT NULL,
  `room_no` int(11) NOT NULL,
  `room_cost` float NOT NULL,
  `taxes` float NOT NULL,
  `total` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `father` varchar(100) NOT NULL,
  `idcard` bigint(20) NOT NULL,
  `phone` bigint(20) NOT NULL,
  `designation` varchar(100) NOT NULL,
  `salary` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Đang đổ dữ liệu cho bảng `employee`
--

INSERT INTO `employee` (`id`, `name`, `father`, `idcard`, `phone`, `designation`, `salary`) VALUES
(2, 'Khaliq Dad', 'Ghazi Muhammad', 5650311674779, 3322996178, 'IT Officer', 65000),
(3, 'Zeeshan', 'Haji Sab', 5245487554459, 3322665598, 'Manager', 70000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `expenses`
--

CREATE TABLE `expenses` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `nature` varchar(255) NOT NULL,
  `expens` double NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Đang đổ dữ liệu cho bảng `expenses`
--

INSERT INTO `expenses` (`id`, `name`, `nature`, `expens`, `date`) VALUES
(1, 'Khaliq dad', 'tution fee for university', 10000, '2018-11-04');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `guests`
--

CREATE TABLE `guests` (
  `id` int(11) NOT NULL,
  `cus_name` varchar(100) NOT NULL,
  `cus_father` varchar(100) NOT NULL,
  `address` varchar(255) NOT NULL,
  `cus_nic` bigint(20) NOT NULL,
  `cus_date` date NOT NULL,
  `phone` bigint(20) NOT NULL,
  `cus_country` varchar(50) NOT NULL,
  `cus_city` varchar(50) NOT NULL,
  `cus_adult` int(11) NOT NULL,
  `cus_child` int(11) NOT NULL,
  `room_type` varchar(100) NOT NULL,
  `room_no` int(11) NOT NULL,
  `room_cost` float NOT NULL,
  `taxes` float NOT NULL,
  `total` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `login`
--

CREATE TABLE `login` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Đang đổ dữ liệu cho bảng `login`
--

INSERT INTO `login` (`id`, `username`, `password`, `status`) VALUES
(2, 'admin', '03322', 'Administrator'),
(3, 'zeeshan', '03322', 'admin'),
(4, 'aurang', 'aurang', 'admin'),
(5, 'khaliq', '03322afg', 'Admin');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `reservation`
--

CREATE TABLE `reservation` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `phone` bigint(20) NOT NULL,
  `roomtype` varchar(100) NOT NULL,
  `roomno` int(11) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `room`
--

CREATE TABLE `room` (
  `id` int(11) NOT NULL,
  `room_number` int(11) NOT NULL,
  `room_type` varchar(100) NOT NULL,
  `floor` int(11) NOT NULL,
  `status` varchar(50) NOT NULL,
  `cost` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `room`
--

INSERT INTO `room` (`id`, `room_number`, `room_type`, `floor`, `status`, `cost`) VALUES
(1, 101, 'Single', 1, 'Occupied', 100),
(2, 102, 'Double', 1, 'Available', 200),
(3, 103, 'Luxury', 1, 'Available', 500),
(4, 104, 'Delux', 1, 'Available', 1000),
(5, 105, 'Family', 1, 'Available', 750),
(6, 106, 'Single', 1, 'Available', 100),
(7, 107, 'Double', 1, 'Available', 200),
(8, 108, 'Luxury', 1, 'Available', 500),
(9, 109, 'Delux', 1, 'Available', 1000),
(10, 110, 'Family', 1, 'Available', 750),
(11, 201, 'Single', 2, 'Available', 100),
(12, 202, 'Double', 2, 'Available', 200),
(13, 203, 'Luxury', 2, 'Available', 500),
(14, 204, 'Delux', 2, 'Available', 1000),
(15, 205, 'Family', 2, 'Available', 750),
(16, 206, 'Single', 2, 'Available', 100),
(17, 207, 'Double', 2, 'Available', 200),
(18, 208, 'Luxury', 2, 'Available', 500),
(19, 209, 'Delux', 2, 'Available', 1000),
(20, 210, 'Family', 2, 'Available', 750),
(21, 301, 'Single', 3, 'Available', 100),
(22, 302, 'Double', 3, 'Available', 200),
(23, 303, 'Luxury', 3, 'Available', 500),
(24, 304, 'Delux', 3, 'Available', 1000),
(25, 305, 'Family', 3, 'Available', 750),
(26, 306, 'Single', 3, 'Available', 100),
(27, 307, 'Double', 3, 'Available', 200),
(28, 308, 'Luxury', 3, 'Available', 500),
(29, 309, 'Delux', 3, 'Available', 1000),
(30, 310, 'Family', 3, 'Available', 750),
(31, 401, 'Single', 4, 'Available', 100),
(32, 402, 'Double', 4, 'Available', 200),
(33, 403, 'Luxury', 4, 'Available', 500),
(34, 404, 'Delux', 4, 'Available', 1000),
(35, 405, 'Family', 4, 'Available', 750),
(36, 406, 'Single', 4, 'Available', 100),
(37, 407, 'Double', 4, 'Available', 200),
(38, 408, 'Luxury', 4, 'Available', 500),
(39, 409, 'Delux', 4, 'Available', 1000),
(40, 410, 'Family', 4, 'Available', 750);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `checkout`
--
ALTER TABLE `checkout`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_room_checkout` (`room_no`);

--
-- Chỉ mục cho bảng `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `expenses`
--
ALTER TABLE `expenses`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `guests`
--
ALTER TABLE `guests`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_room_guests` (`room_no`);

--
-- Chỉ mục cho bảng `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `checkout`
--
ALTER TABLE `checkout`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT cho bảng `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `expenses`
--
ALTER TABLE `expenses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `guests`
--
ALTER TABLE `guests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT cho bảng `login`
--
ALTER TABLE `login`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `reservation`
--
ALTER TABLE `reservation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `room`
--
ALTER TABLE `room`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `checkout`
--
ALTER TABLE `checkout`
  ADD CONSTRAINT `fk_room_checkout` FOREIGN KEY (`room_no`) REFERENCES `room` (`id`);

--
-- Các ràng buộc cho bảng `guests`
--
ALTER TABLE `guests`
  ADD CONSTRAINT `fk_room_guests` FOREIGN KEY (`room_no`) REFERENCES `room` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
