-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Sep 09, 2020 at 10:43 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `onlinerezervacija`
--

-- --------------------------------------------------------

--
-- Table structure for table `bioskop`
--

CREATE TABLE `bioskop` (
  `bioskopId` int(11) NOT NULL,
  `bioskopNaziv` varchar(50) NOT NULL,
  `bioskopAdresa` varchar(50) NOT NULL,
  `bioskopTelefon` varchar(50) NOT NULL,
  `bioskopBanner` varchar(100) NOT NULL,
  `bioskopCena2D` float NOT NULL,
  `bioskopCena3D` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bioskop`
--

INSERT INTO `bioskop` (`bioskopId`, `bioskopNaziv`, `bioskopAdresa`, `bioskopTelefon`, `bioskopBanner`, `bioskopCena2D`, `bioskopCena3D`) VALUES
(2, 'Roda', 'Adresa bioskopa br 2', '+381/65-123-4567', 'images/bioskopi/20200619-195653-roda.jpg', 290, 390),
(3, 'Tuckwood', 'Adresa bioskopa br 3', '+381/65-123-4567', 'images/bioskopi/20200619-195658-tuckwood.jpg', 250, 350);

-- --------------------------------------------------------

--
-- Table structure for table `film`
--

CREATE TABLE `film` (
  `filmId` int(11) NOT NULL,
  `filmNaziv` varchar(50) NOT NULL,
  `filmGodina` varchar(4) NOT NULL,
  `filmZanr` varchar(50) NOT NULL,
  `filmPoster` varchar(100) NOT NULL,
  `filmReziser` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `film`
--

INSERT INTO `film` (`filmId`, `filmNaziv`, `filmGodina`, `filmZanr`, `filmPoster`, `filmReziser`) VALUES
(1, 'Avengers - Endgame', '2019', 'Naucna fantastika, Akcija, Superhero', 'images/filmovi/20200619-151218-Avengers - Endgame.jpg', 'Joe Russo, Anthony Russo'),
(2, 'James Bond - Skyfall', '2012', 'Akcija, Thriller', 'images/filmovi/20200619-151437-James Bond - Skyfall.jpg', 'Sam Mendes'),
(3, 'Deadpool 2', '2018', 'Naucna fantastika, Komedija, Akcija, Superhero', 'images/filmovi/20200619-151451-Deadpool 2.jpg', 'David Leitch'),
(4, 'Jurassic World - Fallen Kingdom', '2018', 'Naucna fantastika, Drama', 'images/filmovi/20200619-151509-Jurassic World - Fallen Kingdom.jpg', 'J. A. Bayona'),
(5, 'The Fate of the Furious', '2017', 'Akcija, Drama', 'images/filmovi/20200619-151525-The Fate of the Furious.jpg', 'F. Gary Gary'),
(6, 'Once Upon a Time in Hollywood', '2019', 'Komedija, Drama', 'images/filmovi/20200619-151532-Once Upon a Time in HollyWood.jpg', 'Quentin Tarantino'),
(7, 'IT chapter 2', '2019', 'Horor, Triler', 'images/filmovi/20200619-151538-IT chapter 2.jpg', 'Andres Muschetti');

-- --------------------------------------------------------

--
-- Table structure for table `korisnik`
--

CREATE TABLE `korisnik` (
  `korisnikId` int(11) NOT NULL,
  `korisnikUsername` varchar(50) NOT NULL,
  `korisnikPassword` longtext NOT NULL,
  `korisnikRole` varchar(15) NOT NULL,
  `bioskopId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `korisnik`
--

INSERT INTO `korisnik` (`korisnikId`, `korisnikUsername`, `korisnikPassword`, `korisnikRole`, `bioskopId`) VALUES
(31, 'pera@gmail.com', '5a8f70e725742ee64204353e700778b29f81b988', 'Administrator', 0),
(32, 'mijat@gmail.com', '9048ead9080d9b27d6b2b6ed363cbf8cce795f7f', 'Menadzer', 2),
(34, 'dejan@gmail.com', '9048ead9080d9b27d6b2b6ed363cbf8cce795f7f', 'Korisnik', 0);

-- --------------------------------------------------------

--
-- Table structure for table `projekcija`
--

CREATE TABLE `projekcija` (
  `projekcijaId` int(11) NOT NULL,
  `salaId` int(11) NOT NULL,
  `filmId` int(11) NOT NULL,
  `projekcijaJe3D` tinyint(1) NOT NULL,
  `projekcijaDatum` date NOT NULL,
  `projekcijaVreme` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `projekcija`
--

INSERT INTO `projekcija` (`projekcijaId`, `salaId`, `filmId`, `projekcijaJe3D`, `projekcijaDatum`, `projekcijaVreme`) VALUES
(4, 4, 2, 0, '2020-07-10', '20:00:00'),
(5, 5, 3, 1, '2020-07-11', '16:15:00'),
(6, 6, 4, 0, '2020-07-12', '15:00:00'),
(7, 7, 5, 1, '2020-07-11', '20:00:00'),
(8, 8, 6, 0, '2020-07-12', '19:00:00'),
(9, 9, 7, 1, '2020-07-13', '22:30:00'),
(10, 10, 1, 0, '2020-07-14', '19:45:00'),
(14, 4, 5, 0, '2020-07-15', '20:00:00'),
(15, 5, 6, 1, '2020-07-15', '13:00:00'),
(16, 6, 7, 0, '2020-07-16', '20:00:00'),
(21, 4, 3, 1, '2020-09-09', '20:00:00'),
(22, 6, 1, 1, '2020-09-09', '20:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `rezervacija`
--

CREATE TABLE `rezervacija` (
  `rezervacijaId` int(11) NOT NULL,
  `rezervacijaCena` float NOT NULL,
  `projekcijaId` int(11) NOT NULL,
  `korisnikId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `rezervacija`
--

INSERT INTO `rezervacija` (`rezervacijaId`, `rezervacijaCena`, `projekcijaId`, `korisnikId`) VALUES
(28, 290, 4, 31),
(29, 290, 6, 31);

-- --------------------------------------------------------

--
-- Table structure for table `rezervisanasedista`
--

CREATE TABLE `rezervisanasedista` (
  `sedisteId` int(11) NOT NULL,
  `sedisteKolona` int(11) NOT NULL,
  `sedisteRed` int(11) NOT NULL,
  `rezervacijaId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `rezervisanasedista`
--

INSERT INTO `rezervisanasedista` (`sedisteId`, `sedisteKolona`, `sedisteRed`, `rezervacijaId`) VALUES
(102, 1, 0, 28),
(103, 1, 1, 29);

-- --------------------------------------------------------

--
-- Table structure for table `sala`
--

CREATE TABLE `sala` (
  `salaId` int(11) NOT NULL,
  `salaNaziv` varchar(10) NOT NULL,
  `salaRedovi` int(11) NOT NULL,
  `salaKolone` int(11) NOT NULL,
  `bioskopId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `sala`
--

INSERT INTO `sala` (`salaId`, `salaNaziv`, `salaRedovi`, `salaKolone`, `bioskopId`) VALUES
(4, 'Sala 1', 6, 7, 2),
(5, 'Sala 2', 5, 8, 2),
(6, 'Sala 3', 8, 8, 2),
(7, 'Sala 1', 7, 10, 3),
(8, 'Sala 2', 9, 9, 3),
(9, 'Sala 3', 7, 7, 3),
(10, 'Sala 4', 8, 6, 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bioskop`
--
ALTER TABLE `bioskop`
  ADD PRIMARY KEY (`bioskopId`);

--
-- Indexes for table `film`
--
ALTER TABLE `film`
  ADD PRIMARY KEY (`filmId`);

--
-- Indexes for table `korisnik`
--
ALTER TABLE `korisnik`
  ADD PRIMARY KEY (`korisnikId`);

--
-- Indexes for table `projekcija`
--
ALTER TABLE `projekcija`
  ADD PRIMARY KEY (`projekcijaId`),
  ADD KEY `salaId` (`salaId`,`filmId`),
  ADD KEY `FK_projekcija_film` (`filmId`);

--
-- Indexes for table `rezervacija`
--
ALTER TABLE `rezervacija`
  ADD PRIMARY KEY (`rezervacijaId`),
  ADD KEY `korisnikId` (`korisnikId`),
  ADD KEY `projekcijaId` (`projekcijaId`);

--
-- Indexes for table `rezervisanasedista`
--
ALTER TABLE `rezervisanasedista`
  ADD PRIMARY KEY (`sedisteId`),
  ADD KEY `rezervacijaId` (`rezervacijaId`);

--
-- Indexes for table `sala`
--
ALTER TABLE `sala`
  ADD PRIMARY KEY (`salaId`),
  ADD KEY `bioskopId` (`bioskopId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bioskop`
--
ALTER TABLE `bioskop`
  MODIFY `bioskopId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `film`
--
ALTER TABLE `film`
  MODIFY `filmId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `korisnik`
--
ALTER TABLE `korisnik`
  MODIFY `korisnikId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `projekcija`
--
ALTER TABLE `projekcija`
  MODIFY `projekcijaId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `rezervacija`
--
ALTER TABLE `rezervacija`
  MODIFY `rezervacijaId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `rezervisanasedista`
--
ALTER TABLE `rezervisanasedista`
  MODIFY `sedisteId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=105;

--
-- AUTO_INCREMENT for table `sala`
--
ALTER TABLE `sala`
  MODIFY `salaId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `projekcija`
--
ALTER TABLE `projekcija`
  ADD CONSTRAINT `FK_projekcija_film` FOREIGN KEY (`filmId`) REFERENCES `film` (`filmId`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_projekcija_sala` FOREIGN KEY (`salaId`) REFERENCES `sala` (`salaId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `rezervacija`
--
ALTER TABLE `rezervacija`
  ADD CONSTRAINT `FK_rezervacija_korisnikId` FOREIGN KEY (`korisnikId`) REFERENCES `korisnik` (`korisnikId`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_rezervacija_projekcijaId` FOREIGN KEY (`projekcijaId`) REFERENCES `projekcija` (`projekcijaId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `rezervisanasedista`
--
ALTER TABLE `rezervisanasedista`
  ADD CONSTRAINT `FK_rezervisanaSedista_rezervacijaId` FOREIGN KEY (`rezervacijaId`) REFERENCES `rezervacija` (`rezervacijaId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `sala`
--
ALTER TABLE `sala`
  ADD CONSTRAINT `FK_salaId_bioskop` FOREIGN KEY (`bioskopId`) REFERENCES `bioskop` (`bioskopId`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
