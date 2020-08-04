-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 04, 2020 at 03:50 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bibliofx`
--

-- --------------------------------------------------------

--
-- Table structure for table `adherent`
--

CREATE TABLE `adherent` (
  `id` int(11) NOT NULL,
  `cin` varchar(50) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `adherent`
--

INSERT INTO `adherent` (`id`, `cin`, `nom`, `prenom`) VALUES
(1, 'D130', 'Akhadam', 'Ayoub'),
(2, 'E123', 'Bentaleb', 'Ahmed'),
(3, 'A120', 'ALAWI', 'Youness'),
(4, 'B534', 'Atich', 'Said'),
(5, 'L100', 'Takoua', 'Fatiha'),
(6, 'T321', 'DAHOUARI', 'Khalid'),
(7, 'Q100', 'MAMOUNI', 'Idriss'),
(8, 'O400', 'Ansari', 'Ahmed'),
(9, 'C500', 'BASRI', 'Soufiane'),
(10, 'D200', 'HANINE', 'Chaymae');

-- --------------------------------------------------------

--
-- Table structure for table `auteur`
--

CREATE TABLE `auteur` (
  `id` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `auteur`
--

INSERT INTO `auteur` (`id`, `nom`, `prenom`) VALUES
(1, 'CHAJIA', 'Mohammed'),
(2, 'DUPONT', 'Eric'),
(3, ' DARTE', 'ALAIN'),
(4, 'GENET', 'Ann'),
(5, 'DOUCHET', 'Jean'),
(6, 'PASCALE', 'Blaise'),
(7, 'MONIER', 'Jean-michel'),
(8, 'GILORMINI', 'Claude'),
(9, 'AYRES', 'Fariss'),
(10, 'SIBONY', 'Malusi '),
(11, 'A.LEVINE', 'Peter'),
(12, 'SAMIR', 'Alawi');

-- --------------------------------------------------------

--
-- Table structure for table `emprunt`
--

CREATE TABLE `emprunt` (
  `id` int(11) NOT NULL,
  `reference` varchar(50) NOT NULL,
  `dateEmprunt` varchar(50) NOT NULL,
  `dateRestitutionEffective` varchar(50) NOT NULL,
  `dateRestitutionPrevu` varchar(50) NOT NULL,
  `adherent` int(11) NOT NULL,
  `livre` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `emprunt`
--

INSERT INTO `emprunt` (`id`, `reference`, `dateEmprunt`, `dateRestitutionEffective`, `dateRestitutionPrevu`, `adherent`, `livre`) VALUES
(30, 'Emp 4/1', '4/17/2020', '', '4/23/2020', 4, 1),
(32, 'Emp 6/1', '4/17/2020', '', '4/23/2020', 6, 1),
(82, 'Emp 3/3', '4/16/2020', '4/13/2020', '4/23/2020', 3, 3),
(91, 'Emp 9/54', '4/5/2020', '4/14/2020', '4/12/2020', 9, 54),
(98, 'Emp 1/8', '6/7/2020', '', '6/15/2020', 1, 8),
(99, 'Emp 3/8', '6/7/2020', '', '6/15/2020', 3, 8);

-- --------------------------------------------------------

--
-- Table structure for table `livre`
--

CREATE TABLE `livre` (
  `id` int(11) NOT NULL,
  `isbn` varchar(50) NOT NULL,
  `titre` varchar(50) NOT NULL,
  `langue` varchar(50) NOT NULL,
  `nbrExemplaire` varchar(50) NOT NULL,
  `etat` varchar(25) NOT NULL,
  `auteur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `livre`
--

INSERT INTO `livre` (`id`, `isbn`, `titre`, `langue`, `nbrExemplaire`, `etat`, `auteur`) VALUES
(3, 'LL7', 'TOPOLOGIE', 'Anglais', '11', 'disponible', 11),
(7, 'LL11', 'JAVA', 'Anglais', '50', 'disponible', 4),
(8, 'LL12', 'ELECTRONIQUE', 'Français', '36', 'disponible', 4),
(9, 'LL13', 'PHP', 'Français', '20', ' disponible', 5),
(10, 'LL14', 'ELECTROMAGNETISME', 'Anglais', '6', 'disponible', 2),
(44, 'LL18', 'C', 'Anglais', '19', 'disponible', 7),
(53, 'LL20', 'IHM', 'Anglais', '10', 'disponible', 7),
(54, 'LL30', 'JAVAFX', 'Anglais', '19', 'disponible', 7);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`) VALUES
(1, 'Akhadam', 'akhadam'),
(2, 'admin', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `adherent`
--
ALTER TABLE `adherent`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `auteur`
--
ALTER TABLE `auteur`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `emprunt`
--
ALTER TABLE `emprunt`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Livre` (`livre`) USING BTREE,
  ADD KEY `Adherent` (`adherent`) USING BTREE;

--
-- Indexes for table `livre`
--
ALTER TABLE `livre`
  ADD PRIMARY KEY (`id`),
  ADD KEY `auteur` (`auteur`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `adherent`
--
ALTER TABLE `adherent`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `auteur`
--
ALTER TABLE `auteur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `emprunt`
--
ALTER TABLE `emprunt`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=100;

--
-- AUTO_INCREMENT for table `livre`
--
ALTER TABLE `livre`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
