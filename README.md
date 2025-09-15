A real-time fraud detection system that monitors credit card transactions and flags suspicious activities using rule-based checks and machine learning models. The system is designed for scalability, explainability, and real-world deployment, offering proactive fraud alerts and an interactive dashboard.

Features
	•	Configurable thresholds & time window (6h, 12h, 24h)
	•	Database integration (PostgreSQL/MySQL) for storing transactions
	•	Real-time transaction streaming with Apache Kafka
	•	REST API (Spring Boot / Express.js) to add transactions & check fraud status
	•	Dashboard & Visualization (React + Chart.js/Recharts) for monitoring
	•	Alerts & Notifications via Email/SMS when fraud is detected
	•	Multiple fraud detection rules
	•	High transaction frequency
	•	Location mismatch
	•	Merchant category anomaly
	•	Machine Learning models (Isolation Forest, Random Forest, Autoencoder) for anomaly detection
	•	Fraud Risk Scoring (0–1) instead of simple binary output
	•	Explainability → shows why a transaction was flagged

Tech Stack

Backend:-
	•	Java  
	•	PostgreSQL/MySQL 
	•	Apache Kafka (working)

Frontend:-
	•	React.js + Recharts/Chart.js 
	•	TailwindCSS
