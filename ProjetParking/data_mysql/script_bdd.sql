-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  lun. 25 mai 2020 à 12:18
-- Version du serveur :  5.7.24
-- Version de PHP :  7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `parking`
--

-- --------------------------------------------------------

--
-- Structure de la table `association`
--

DROP TABLE IF EXISTS `association`;
CREATE TABLE IF NOT EXISTS `association` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `num_plaque_immatricule` varchar(10) NOT NULL,
  `id_client` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `et_client` (`id_client`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `association`
--

INSERT INTO `association` (`id`, `num_plaque_immatricule`, `id_client`) VALUES
(4, 'AA-000-AA', 7),
(5, 'AA-111-AA', 5),
(6, 'AA-222-AA', 6),
(7, 'AA-333-AA', 4),
(8, 'AA-444-AA', 3),
(9, 'AA-555-AA', 5),
(10, 'AA-666-AA', 5),
(11, 'AA-111-AA', 3),
(12, 'AA-000-AA', 2),
(13, 'BB-666-BB', 2),
(16, 'ZZ-999-ZZ', 2);

-- --------------------------------------------------------

--
-- Structure de la table `associationtemporaire`
--

DROP TABLE IF EXISTS `associationtemporaire`;
CREATE TABLE IF NOT EXISTS `associationtemporaire` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `num_plaque_immatricule` varchar(10) NOT NULL,
  `id_client` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `et_clientTemp` (`id_client`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `adresse` varchar(50) NOT NULL,
  `numero_mobile` varchar(10) NOT NULL,
  `mail` varchar(50) NOT NULL,
  `RIB` varchar(27) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id`, `nom`, `prenom`, `adresse`, `numero_mobile`, `mail`, `RIB`) VALUES
(2, 'Paul', 'Jean', '1 rue de jean paul', '0600000000', 'jeanpaul@email.fr', 'FR0000000000000000000000000'),
(3, 'Marie', 'Anne', '2 rue de anne marie', '0611111111', 'annemarie@email.fr', 'FR1111111111111111111111111'),
(4, 'Gregoire', 'Eric', '2 rue de eric gregoire', '0622222222', 'ericgregoire@email.fr', 'FR2222222222222222222222222'),
(5, 'Dupont', 'Francois', '3 rue de francois dupont', '0633333333', 'francoisdupont@email.fr', 'FR3333333333333333333333333'),
(6, 'Gauthier', 'Lucas', '4 rue de lucas gauthier', '0644444444', 'lucasgauthier@email.fr', 'FR4444444444444444444444444'),
(7, 'Rachadi', 'Amir', '5 rue de amir rachadi', '0655555555', 'amirrachadi@email.fr', 'FR5555555555555555555555555');

-- --------------------------------------------------------

--
-- Structure de la table `facturation`
--

DROP TABLE IF EXISTS `facturation`;
CREATE TABLE IF NOT EXISTS `facturation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_client` int(11) NOT NULL,
  `id_historique_reservation` int(11) NOT NULL,
  `cout_normal` float NOT NULL,
  `cout_depassement` float NOT NULL,
  `cout_remboursement` float NOT NULL,
  `cout_prolongation_attente` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `et_factu_client` (`id_client`),
  KEY `et_factu_histo` (`id_historique_reservation`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `historiquereservation`
--

DROP TABLE IF EXISTS `historiquereservation`;
CREATE TABLE IF NOT EXISTS `historiquereservation` (
  `id` int(11) NOT NULL,
  `id_client` int(11) NOT NULL,
  `id_place` int(11) NOT NULL,
  `date_debut` datetime NOT NULL,
  `date_fin` datetime NOT NULL,
  `duree` int(11) NOT NULL,
  `date_arrive_reel` datetime DEFAULT NULL,
  `date_depart_reel` datetime DEFAULT NULL,
  `delai_attente` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `et_histo_client` (`id_client`),
  KEY `et_histo_place` (`id_place`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `historiquereservation`
--

INSERT INTO `historiquereservation` (`id`, `id_client`, `id_place`, `date_debut`, `date_fin`, `duree`, `date_arrive_reel`, `date_depart_reel`, `delai_attente`) VALUES
(7, 5, 1, '2020-05-21 11:15:00', '2020-05-21 13:15:00', 120, NULL, NULL, 55);

-- --------------------------------------------------------

--
-- Structure de la table `parametresparking`
--

DROP TABLE IF EXISTS `parametresparking`;
CREATE TABLE IF NOT EXISTS `parametresparking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `valeur` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `parametresparking`
--

INSERT INTO `parametresparking` (`id`, `nom`, `valeur`) VALUES
(1, 'tarif_normal', 10),
(2, 'tarif_dépassement', 10),
(3, 'nb_places_surréservation', 5),
(4, 'tarif_prolongation_attente', 2.5),
(5, 'nb_places_surréservation_en_cours', 10),
(6, 'tarif_dépassement_augmentation', 2);

-- --------------------------------------------------------

--
-- Structure de la table `placeparking`
--

DROP TABLE IF EXISTS `placeparking`;
CREATE TABLE IF NOT EXISTS `placeparking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `etat` enum('occupée','disponible','réservée') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `placeparking`
--

INSERT INTO `placeparking` (`id`, `etat`) VALUES
(1, 'disponible'),
(2, 'occupée'),
(3, 'disponible'),
(4, 'disponible'),
(5, 'disponible'),
(6, 'disponible'),
(7, 'disponible'),
(8, 'disponible'),
(9, 'disponible'),
(10, 'réservée'),
(11, 'disponible'),
(12, 'disponible'),
(13, 'disponible'),
(14, 'disponible'),
(15, 'disponible'),
(16, 'disponible'),
(17, 'disponible'),
(18, 'disponible'),
(19, 'disponible'),
(20, 'disponible'),
(21, 'disponible'),
(22, 'disponible'),
(23, 'disponible'),
(24, 'disponible'),
(25, 'disponible'),
(26, 'disponible'),
(27, 'disponible'),
(28, 'disponible'),
(29, 'disponible'),
(30, 'disponible');

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_client` int(11) NOT NULL,
  `id_place` int(11) NOT NULL,
  `date_debut` datetime NOT NULL,
  `date_fin` datetime NOT NULL,
  `duree` int(11) NOT NULL,
  `date_arrive_reel` datetime DEFAULT NULL,
  `date_depart_reel` datetime DEFAULT NULL,
  `delai_attente` int(11) NOT NULL DEFAULT '30',
  PRIMARY KEY (`id`),
  KEY `et_Reserv_client` (`id_client`),
  KEY `et_Reserv_place` (`id_place`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `reservation`
--

INSERT INTO `reservation` (`id`, `id_client`, `id_place`, `date_debut`, `date_fin`, `duree`, `date_arrive_reel`, `date_depart_reel`, `delai_attente`) VALUES
(4, 5, 1, '2020-08-04 16:00:00', '2020-08-04 18:30:00', 150, NULL, NULL, 30);

-- --------------------------------------------------------

--
-- Structure de la table `reservationpermanente`
--

DROP TABLE IF EXISTS `reservationpermanente`;
CREATE TABLE IF NOT EXISTS `reservationpermanente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_client` int(11) NOT NULL,
  `type` enum('journalière','hebdomadaire','mensuelle') NOT NULL,
  `heure_debut` time NOT NULL,
  `duree` time NOT NULL,
  `jour_semaine` int(11) DEFAULT NULL,
  `jour_mois` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `et_reservPermanente_client` (`id_client`)
) ENGINE=InnoDB AUTO_INCREMENT=556 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `reservationpermanente`
--

INSERT INTO `reservationpermanente` (`id`, `id_client`, `type`, `heure_debut`, `duree`, `jour_semaine`, `jour_mois`) VALUES
(2, 3, 'hebdomadaire', '10:10:00', '08:00:00', 2, NULL),
(3, 3, 'journalière', '15:00:00', '05:00:00', NULL, NULL);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `association`
--
ALTER TABLE `association`
  ADD CONSTRAINT `et_client` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`);

--
-- Contraintes pour la table `associationtemporaire`
--
ALTER TABLE `associationtemporaire`
  ADD CONSTRAINT `et_clientTemp` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`);

--
-- Contraintes pour la table `facturation`
--
ALTER TABLE `facturation`
  ADD CONSTRAINT `et_factu_client` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`),
  ADD CONSTRAINT `et_factu_histo` FOREIGN KEY (`id_historique_reservation`) REFERENCES `historiquereservation` (`id`);

DELIMITER $$
--
-- Évènements
--
DROP EVENT IF EXISTS `supprimer_reservation_expiree`$$
CREATE DEFINER=`root`@`localhost` EVENT `supprimer_reservation_expiree` ON SCHEDULE EVERY 1 MINUTE STARTS '2020-05-21 12:04:17' ON COMPLETION NOT PRESERVE ENABLE COMMENT 'Suppression reservation expiree' DO BEGIN

INSERT INTO historiquereservation
        SELECT * FROM reservation
        WHERE DATE_ADD(date_debut,INTERVAL delai_attente MINUTE) < now() AND date_arrive_reel IS NULL;

DELETE from reservation WHERE DATE_ADD(date_debut,INTERVAL delai_attente MINUTE) < now() AND date_arrive_reel IS NULL;

END$$

DELIMITER ;

SET GLOBAL event_scheduler="ON";

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
