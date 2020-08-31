CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accountId` varchar(100) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `Token` varchar(36) DEFAULT NULL,
  `gmt_create` bigint DEFAULT NULL,
  `gmt_modified` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

