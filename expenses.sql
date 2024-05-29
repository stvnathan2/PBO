-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.30 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table pemweb.daily_expenses
CREATE TABLE IF NOT EXISTS `daily_expenses` (
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `id` int unsigned NOT NULL DEFAULT '0',
  `amount` decimal(10,2) NOT NULL,
  `type` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `category` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `description` text,
  `payment_method` varchar(50) DEFAULT NULL,
  `account` varchar(50) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  CONSTRAINT `daily_expenses_chk_1` CHECK ((`type` in (_utf8mb4'pemasukan',_utf8mb4'pengeluaran')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table pemweb.daily_expenses: ~77 rows (approximately)
INSERT INTO `daily_expenses` (`username`, `id`, `amount`, `type`, `category`, `date`, `description`, `payment_method`, `account`, `created_at`, `updated_at`) VALUES
	('stvnathan', 1, 500000.00, 'pemasukan', 'gaji', '2024-05-01', 'Gaji bulanan dari perusahaan', 'transfer bank', 'rekening bank', '2024-05-16 13:41:11', '2024-05-29 18:41:28'),
	('stvnathan', 2, 150000.00, 'pengeluaran', 'belanja', '2024-05-02', 'Belanja bulanan di supermarket', 'kartu kredit', 'rekening bank', '2024-05-16 13:41:11', '2024-05-29 18:41:28'),
	('stvnathan', 3, 75000.00, 'pengeluaran', 'hiburan', '2024-05-03', 'Menonton film di bioskop', 'tunai', 'dompet', '2024-05-16 13:41:11', '2024-05-29 18:41:28'),
	('stvnathan', 4, 20000.00, 'pengeluaran', 'transportasi', '2024-05-04', 'Ongkos ojek online', 'dompet digital', 'OVO', '2024-05-16 13:41:11', '2024-05-29 18:41:28'),
	('stvnathan', 5, 300000.00, 'pemasukan', 'gaji', '2024-05-05', 'Penjualan barang bekas', 'tunai', 'dompet', '2024-05-16 13:41:11', '2024-05-29 18:41:28'),
	('stvnathan', 6, 100000.00, 'pengeluaran', 'makan', '2024-05-06', 'Makan malam di restoran', 'kartu debit', 'rekening bank', '2024-05-16 13:41:11', '2024-05-29 18:41:28'),
	('stvnathan', 7, 50000.00, 'pengeluaran', 'kesehatan', '2024-05-07', 'Beli obat di apotek', 'tunai', 'dompet', '2024-05-16 13:41:11', '2024-05-29 18:41:28'),
	('stvnathan', 8, 450000.00, 'pemasukan', 'gaji', '2024-05-08', 'Pembayaran proyek freelance', 'transfer bank', 'rekening bank', '2024-05-16 13:41:11', '2024-05-29 18:41:28'),
	('stvnathan', 9, 120000.00, 'pengeluaran', 'belanja', '2024-05-09', 'Beli pakaian baru', 'kartu kredit', 'rekening bank', '2024-05-16 13:41:11', '2024-05-29 18:41:28'),
	('stvnathan', 10, 80000.00, 'pengeluaran', 'hiburan', '2024-05-10', 'Berlangganan layanan streaming', 'dompet digital', 'GoPay', '2024-05-16 13:41:11', '2024-05-29 18:41:28'),
	('stvnathan', 11, 25000.00, 'pengeluaran', 'transportasi', '2024-05-11', 'Tiket bus kota', 'tunai', 'dompet', '2024-05-16 13:41:11', '2024-05-29 18:41:28'),
	('stvnathan', 12, 200000.00, 'pengeluaran', 'belanja', '2024-05-12', 'Beli bahan makanan', 'kartu debit', 'rekening bank', '2024-05-16 13:41:11', '2024-05-29 18:41:28'),
	('stvnathan', 13, 150000.00, 'pengeluaran', 'makan', '2024-05-13', 'Makan siang di kafe', 'kartu kredit', 'rekening bank', '2024-05-16 13:41:11', '2024-05-29 18:41:28'),
	('stvnathan', 14, 100000.00, 'pemasukan', 'gaji', '2024-05-14', 'Hadiah ulang tahun dari teman', 'tunai', 'dompet', '2024-05-16 13:41:11', '2024-05-29 18:41:28'),
	('stvnathan', 15, 30000.00, 'pengeluaran', 'kesehatan', '2024-05-15', 'Konsultasi dokter online', 'dompet digital', 'DANA', '2024-05-16 13:41:11', '2024-05-29 18:41:28'),
	('stvnathan', 16, 55000.00, 'pengeluaran', 'makan', '2024-05-01', 'Sarapan pagi', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 17, 120000.00, 'pengeluaran', 'belanja', '2024-05-01', 'Beli bahan makanan', 'kartu debit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 18, 800000.00, 'pemasukan', 'gaji', '2024-05-02', 'Gaji bulanan', 'transfer bank', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 19, 70000.00, 'pengeluaran', 'hiburan', '2024-05-02', 'Nonton bioskop', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 20, 50000.00, 'pengeluaran', 'transportasi', '2024-05-03', 'Beli tiket kereta', 'dompet digital', 'OVO', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 21, 45000.00, 'pengeluaran', 'makan', '2024-05-03', 'Makan siang', 'kartu kredit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 22, 30000.00, 'pengeluaran', 'kesehatan', '2024-05-04', 'Beli obat', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 23, 95000.00, 'pengeluaran', 'hiburan', '2024-05-04', 'Langganan musik', 'dompet digital', 'GoPay', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 24, 200000.00, 'pengeluaran', 'belanja', '2024-05-05', 'Beli pakaian', 'kartu debit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 25, 35000.00, 'pengeluaran', 'makan', '2024-05-05', 'Makan malam', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 26, 120000.00, 'pengeluaran', 'makan', '2024-05-06', 'Makan malam di restoran', 'kartu debit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 27, 45000.00, 'pengeluaran', 'hiburan', '2024-05-06', 'Langganan aplikasi streaming', 'kartu kredit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 28, 30000.00, 'pengeluaran', 'transportasi', '2024-05-07', 'Beli tiket bus', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 29, 75000.00, 'pengeluaran', 'makan', '2024-05-07', 'Makan siang di kafe', 'kartu debit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 30, 150000.00, 'pengeluaran', 'belanja', '2024-05-08', 'Beli bahan makanan', 'kartu debit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 31, 60000.00, 'pengeluaran', 'hiburan', '2024-05-08', 'Nonton film online', 'dompet digital', 'GoPay', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 32, 50000.00, 'pengeluaran', 'makan', '2024-05-09', 'Sarapan di luar', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 33, 130000.00, 'pengeluaran', 'belanja', '2024-05-09', 'Beli perlengkapan mandi', 'kartu kredit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 34, 250000.00, 'pengeluaran', 'kesehatan', '2024-05-10', 'Kunjungan dokter', 'kartu debit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 35, 30000.00, 'pengeluaran', 'makan', '2024-05-10', 'Makan siang', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 36, 800000.00, 'pemasukan', 'gaji', '2024-05-11', 'Bonus dari proyek', 'transfer bank', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 37, 60000.00, 'pengeluaran', 'transportasi', '2024-05-11', 'Beli tiket kereta', 'dompet digital', 'DANA', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 38, 100000.00, 'pengeluaran', 'belanja', '2024-05-12', 'Beli buku', 'kartu kredit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 39, 45000.00, 'pengeluaran', 'makan', '2024-05-12', 'Makan malam', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 40, 350000.00, 'pengeluaran', 'hiburan', '2024-05-13', 'Beli game baru', 'kartu kredit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 41, 25000.00, 'pengeluaran', 'makan', '2024-05-13', 'Ngopi di kafe', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 42, 90000.00, 'pengeluaran', 'transportasi', '2024-05-14', 'Beli tiket pesawat', 'kartu debit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 43, 60000.00, 'pengeluaran', 'makan', '2024-05-14', 'Makan siang', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 44, 150000.00, 'pengeluaran', 'belanja', '2024-05-15', 'Beli sepatu', 'kartu kredit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 45, 50000.00, 'pengeluaran', 'hiburan', '2024-05-15', 'Langganan layanan musik', 'dompet digital', 'GoPay', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 46, 100000.00, 'pengeluaran', 'makan', '2024-05-16', 'Makan malam di restoran', 'kartu debit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 47, 30000.00, 'pengeluaran', 'transportasi', '2024-05-16', 'Beli tiket bus', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 48, 80000.00, 'pengeluaran', 'kesehatan', '2024-05-17', 'Beli suplemen', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 49, 70000.00, 'pengeluaran', 'makan', '2024-05-17', 'Makan siang di kafe', 'kartu debit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 50, 200000.00, 'pengeluaran', 'belanja', '2024-05-18', 'Beli gadget', 'kartu kredit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 51, 50000.00, 'pengeluaran', 'transportasi', '2024-05-18', 'Beli tiket kereta', 'dompet digital', 'OVO', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 52, 40000.00, 'pengeluaran', 'makan', '2024-05-19', 'Sarapan di luar', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 53, 70000.00, 'pengeluaran', 'hiburan', '2024-05-19', 'Nonton bioskop', 'kartu debit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 54, 100000.00, 'pengeluaran', 'kesehatan', '2024-05-20', 'Konsultasi dokter', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 55, 30000.00, 'pengeluaran', 'makan', '2024-05-20', 'Makan siang', 'kartu kredit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 56, 150000.00, 'pengeluaran', 'belanja', '2024-05-21', 'Beli bahan makanan', 'kartu debit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 57, 50000.00, 'pengeluaran', 'hiburan', '2024-05-21', 'Langganan aplikasi streaming', 'dompet digital', 'GoPay', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 58, 90000.00, 'pengeluaran', 'makan', '2024-05-22', 'Makan malam di restoran', 'kartu debit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 59, 30000.00, 'pengeluaran', 'transportasi', '2024-05-22', 'Beli tiket bus', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 60, 80000.00, 'pengeluaran', 'kesehatan', '2024-05-23', 'Beli obat', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 61, 70000.00, 'pengeluaran', 'makan', '2024-05-23', 'Makan siang di kafe', 'kartu debit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 62, 150000.00, 'pengeluaran', 'belanja', '2024-05-24', 'Beli pakaian', 'kartu kredit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 63, 60000.00, 'pengeluaran', 'hiburan', '2024-05-24', 'Langganan layanan streaming', 'dompet digital', 'GoPay', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 64, 45000.00, 'pengeluaran', 'makan', '2024-05-25', 'Sarapan di luar', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 65, 80000.00, 'pengeluaran', 'transportasi', '2024-05-25', 'Beli tiket kereta', 'kartu debit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 66, 25000.00, 'pengeluaran', 'kesehatan', '2024-05-26', 'Beli vitamin', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 67, 30000.00, 'pengeluaran', 'makan', '2024-05-26', 'Makan siang', 'kartu kredit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 68, 180000.00, 'pengeluaran', 'belanja', '2024-05-27', 'Beli peralatan rumah tangga', 'kartu debit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 69, 50000.00, 'pengeluaran', 'hiburan', '2024-05-27', 'Langganan layanan musik', 'dompet digital', 'GoPay', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 70, 90000.00, 'pengeluaran', 'makan', '2024-05-28', 'Makan malam di restoran', 'kartu debit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 71, 30000.00, 'pengeluaran', 'transportasi', '2024-05-28', 'Beli tiket bus', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 72, 50000.00, 'pengeluaran', 'kesehatan', '2024-05-29', 'Konsultasi dokter online', 'dompet digital', 'DANA', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 73, 70000.00, 'pengeluaran', 'makan', '2024-05-29', 'Makan siang di kafe', 'kartu debit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 74, 200000.00, 'pengeluaran', 'belanja', '2024-05-30', 'Beli gadget', 'kartu kredit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 75, 60000.00, 'pengeluaran', 'hiburan', '2024-05-30', 'Nonton film online', 'dompet digital', 'GoPay', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 76, 50000.00, 'pengeluaran', 'makan', '2024-05-31', 'Sarapan di luar', 'tunai', 'dompet', '2024-05-18 08:35:01', '2024-05-29 18:41:28'),
	('stvnathan', 77, 120000.00, 'pengeluaran', 'belanja', '2024-05-31', 'Beli bahan makanan', 'kartu debit', 'rekening bank', '2024-05-18 08:35:01', '2024-05-29 18:41:28');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
